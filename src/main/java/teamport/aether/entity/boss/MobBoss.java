package teamport.aether.entity.boss;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.IntTag;
import com.mojang.nbt.tags.StringTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.MobPathfinder;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.AetherMod;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.world.feature.util.WorldFeaturePoint;
import teamport.aether.world.feature.util.map.DungeonMap;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static net.minecraft.core.net.command.TextFormatting.*;
import static teamport.aether.AetherMod.TRANSLATOR;
import static teamport.aether.world.feature.util.map.DungeonMap.runWithDungeon;

public abstract class MobBoss extends MobPathfinder implements EnemyBoss, AetherDeathMessage {

    @Nullable
    protected Integer dungeonID = null;
    private String bossName = NameGenerator.getRandomName();

    @Nullable
    protected WorldFeaturePoint returnPoint = null;
    private boolean hasHadReturnPointSet = false;

    @Nullable
    public ItemStack trophy = null;

    protected MobBoss(@Nullable World world) {
        super(world);
    }

    @Override
    public boolean canFight() {
        return isAlive();
    }

    @Override
    public void setTrophy(@Nullable ItemStack itemStack) {
        trophy = itemStack;
    }

    @Override
    public @Nullable ItemStack getTrophy() {
        return trophy;
    }

    @Override
    public void setDungeonID(int id) {
        dungeonID = id;
    }

    @Override
    public String getBossTitle() {
        final String translationKey = EntityDispatcher.getInstance().entryForClass(this.getClass()).nameKey;
        return String.format(TRANSLATOR.translateKey(translationKey + ".title"), getBossName());
    }

    @Override
    public String getBossName() {
        return bossName;
    }

    @Override
    public boolean canDespawn() {
        return false;
    }

    @Override
    public void onDeath(Entity entityKilledBy) {
        if (this.world == null) return;
        AetherMod.LOGGER.info("{} of ID {} has been slain!", bossName, dungeonID);


        if (trophy != null) {
            if (!EnvironmentHelper.isClientWorld()) world.dropItem((int) x, (int) y, (int) z, trophy);
            world.playBlockEvent(null, 1003, (int) x, (int) y, (int) z, 0);
        }

        if (dungeonID != null) {
            DungeonMap.runWithDungeon(dungeonID, d -> d.notifyBossDead(this));
        }

        this.world.players.stream()
            .filter(p -> p.distanceTo(this) < 128)
            .forEach(p -> ((AetherBossList) p).aether$removeFromBossList(this));

        // try triggering the propagate on dungeon blocks.
        for (int x1 = -3; x1 < 3; x1++) {
            for (int z1 = -3; z1 < 3; z1++) {
                for (int y1 = -3; y1 < 3; y1++) {
                    world.notifyBlockChange((int) x + x1, (int) y + y1, (int) z + z1, world.getBlockId((int) x + x1, (int) y + y1, (int) z + z1));
                }
            }
        }

        super.onDeath(entityKilledBy);
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        dungeonID = tag.getInteger("dungeonID");
        bossName = tag.getString("bossName");

        CompoundTag trophyNBT = tag.getCompound("trophy");
        if (trophyNBT != null) {
            trophy = ItemStack.readItemStackFromNbt(trophyNBT);
        }

        if (tag.getBoolean("hasHadReturnPointSet")) {
            CompoundTag returnPointNBT = tag.getCompound("returnPoint");
            returnPoint = WorldFeaturePoint.fromCompoundTag(returnPointNBT);
            hasHadReturnPointSet = true;
        } else returnPoint = null;

        super.readAdditionalSaveData(tag);
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        tag.put("bossName", new StringTag(bossName));

        if (dungeonID != null) {
            tag.put("dungeonID", new IntTag(dungeonID));
        }

        if (returnPoint != null) {
            tag.put("returnPoint", returnPoint.toCompoundTag());
            tag.putBoolean("hasHadReturnPointSet", true);
        }

        if (trophy != null) {
            CompoundTag trophyNBT = new CompoundTag();
            trophy.writeToNBT(trophyNBT);
            tag.put("trophy", trophyNBT);
        }

        super.addAdditionalSaveData(tag);
    }

    @Override
    public void returnToHome() {
        if (returnPoint == null || !hasHadReturnPointSet) return;
        moveTo(returnPoint.getX(), returnPoint.getY(), returnPoint.getZ(), 0, 0);
    }

    @Override
    public void setReturnPoint(@Nullable WorldFeaturePoint returnPoint) {
        this.returnPoint = returnPoint;
        this.hasHadReturnPointSet = true;
    }

    @Override
    public String deathMessage(Player player) {
        String key = EntityDispatcher.getInstance().entryForClass(this.getClass()).nameKey + ".death_message";
        String name = key + "_" + random.nextInt(9);

        String theBossName = BOLD.toString() + TextFormatting.get(this.chatColor).toString() + this.getBossTitle() + RESET + RED;
        String playerName = player.getDisplayName() + RESET + RED;

        String deathMessage = TRANSLATOR.translateKey(name)
            .replace("[PLAYER]", playerName)
            .replace("[BOSS]", theBossName);

        return RED + deathMessage;
    }

    public void returnToOriginalState() {
        this.target = null;
        returnToHome();
        runWithDungeon(dungeonID, d -> d.unlock(world));
        this.setHealthRaw(this.getMaxHealth());
    }

}
