package teamport.aether.entity.boss.sunspirit;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.IntTag;
import com.mojang.nbt.tags.StringTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.MobFlying;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.AetherMod;
import teamport.aether.entity.boss.EnemyBoss;
import teamport.aether.helper.NameGenerator;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.feature.util.map.DungeonMap;
import teamport.aether.world.feature.util.WorldFeaturePoint;
import teamport.aether.world.feature.util.map.DungeonLogic;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.function.Consumer;

public class MobBossFlying extends MobFlying implements EnemyBoss {

    @Nullable
    public Integer dungeonID = null;
    public String bossName = NameGenerator.getRandomName();

    @Nullable
    public WorldFeaturePoint returnPoint = null;
    protected boolean hasHadReturnPointSet = false;

    @Nullable
    public ItemStack trophy = null;


    public MobBossFlying(@Nullable World world) {
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
        final String translationKey = EntityDispatcher.nameKeyForClass(this.getClass());
        return String.format(I18n.getInstance().translateKey(translationKey + ".title"), getBossName());
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
        assert world != null;
        AetherMod.LOGGER.info(bossName + " of ID " + dungeonID + " has been slain!");

        if (trophy != null) {
            if (!EnvironmentHelper.isClientWorld()) world.dropItem((int) x, (int) y, (int) z, trophy);
            world.playBlockEvent(null, 1003, (int) x, (int) y, (int) z, 0);
        }

        if (dungeonID != null) {
            DungeonMap.runWithDungeon(dungeonID, d -> d.notifyBossDead(this));
        }

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
        moveTo(returnPoint.x, returnPoint.y, returnPoint.z, 0, 0);
    }

    @Override
    public void setReturnPoint(@Nullable WorldFeaturePoint returnPoint) {
        this.returnPoint = returnPoint;
        this.hasHadReturnPointSet = true;
    }
}
