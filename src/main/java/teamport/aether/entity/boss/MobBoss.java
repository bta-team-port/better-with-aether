package teamport.aether.entity.boss;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.IntTag;
import com.mojang.nbt.tags.StringTag;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.MobPathfinder;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.AetherMod;
import teamport.aether.world.feature.util.WorldFeaturePoint;
import teamport.aether.world.feature.util.map.DungeonMap;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static teamport.aether.world.feature.util.map.DungeonMap.runWithDungeon;

public abstract class MobBoss extends MobPathfinder implements EnemyBoss {

    @Nullable
    protected Integer dungeonID = null;
    private String bossName = NameGenerator.getRandomName();

    @Nullable
    protected WorldFeaturePoint returnPoint = null;
    private boolean hasHadReturnPointSet = false;

    @Nullable
    public ItemStack trophy = null;

    protected MobBoss(@NonNull World world) {
        super(world);
    }

    @Override
    public boolean canFight() {
        return isAlive();
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void push(@NonNull Entity entity) {
    }

    @Override
    public void push(double x, double y, double z) {
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
    public String getTranslatedBossTitle() {
        return String.format(I18n.getInstance().translateKey(this.getBossTitleKey()), getBossName());
    }

    @Override
    public byte getBossColor() {
        return this.chatColor;
    }

    @Override
    public String getBossTitleKey() {
        EntityDispatcher.EntityDispatcherEntry<? extends MobBoss> entityDispatcherEntry =
            EntityDispatcher.getInstance().entryForClass(this.getClass());
        if(entityDispatcherEntry == null){
            return "no.boss.yes.boss";
        }
        return entityDispatcherEntry.nameKey + ".title";
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
        AetherMod.LOGGER.info("{} of ID {} has been slain!", bossName, dungeonID);


        if (trophy != null) {
            if (!EnvironmentHelper.isMultiplayerClient()) world.dropItem((int) x, (int) y, (int) z, trophy);
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
        trophy = ItemStack.readItemStackFromNbt(trophyNBT);

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

    public void returnToOriginalState() {
        this.target = null;
        returnToHome();
        runWithDungeon(dungeonID, d -> d.unlock(world));
        this.setHealthRaw(this.getMaxHealth());
    }

    @Environment(EnvType.CLIENT)
    public static void stop() {
        Minecraft.getMinecraft().sndManager.stopMusic();
    }

    @Environment(EnvType.CLIENT)
    public static void play(String sound, double x, double y, double z) {
        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.sndManager.stopMusic();
        minecraft.sndManager.playMusic(sound, (float) x, (float) y, (float) z, 1.0F, 1.0F);
    }

}
