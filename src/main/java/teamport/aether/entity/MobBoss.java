package teamport.aether.entity;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.IntTag;
import com.mojang.nbt.tags.StringTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.helper.BlockCoordinate;

import java.util.ArrayList;
import java.util.List;

public class MobBoss extends MobMonster implements EnemyBoss {

    @Nullable
    protected Integer dungeonID = null;
    protected String bossName = "Nullius Primus";

    @Nullable
    protected BlockCoordinate returnPoint = null;

    @Nullable
    protected ItemStack trophy = null;

    protected List<BlockCoordinate> blocksDestroyOnDeath = new ArrayList<>();


    public MobBoss(@Nullable World world) {
        super(world);
    }

    @Override
    public boolean canFight() {
        return isAlive();
    }

    @Override
    public void setTrophy(ItemStack itemStack) {
        trophy = itemStack;
    }

    @Override
    public ItemStack getTrophy() {
        return trophy;
    }

    @Override
    public void addDestroyOnDeathBlock(BlockCoordinate coord) {
        blocksDestroyOnDeath.add(coord);
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
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void onDeath(Entity entityKilledBy) {
        super.onDeath(entityKilledBy);

        if (trophy != null) {
            world.dropItem((int) x, (int) y, (int) z, trophy);
        }

        if (blocksDestroyOnDeath != null) {
            world.playBlockEvent(null, 1003, (int) x, (int) y, (int) z, 0);

            for (BlockCoordinate coordinate : blocksDestroyOnDeath) {
                world.spawnParticle("smoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0,0);
                world.spawnParticle("largesmoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0,0);
                world.setBlockAndMetadataWithNotify(coordinate.x, coordinate.y, coordinate.z, 0, 0);
            }
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        returnPoint = BlockCoordinate.fromCompoundTag(tag.getCompound("returnPoint"));
        dungeonID = tag.getInteger("dungeonID");
        bossName = tag.getString("bossName");

        CompoundTag blockListNBT = tag.getCompound("blocksDestroyOnDeath");
        if (blockListNBT != null) {
            List<BlockCoordinate> list = new ArrayList<>();
            for (int i = 0; i < blockListNBT.getInteger("length"); i++) {
                CompoundTag blockNBT = blockListNBT.getCompound(String.valueOf(i));
                list.add(BlockCoordinate.fromCompoundTag(blockNBT));
            }

            blocksDestroyOnDeath = list;
        }

        CompoundTag trophyNBT = tag.getCompound("trophy");
        if (trophyNBT != null) {
            trophy = ItemStack.readItemStackFromNbt(trophyNBT);
        }

        super.readAdditionalSaveData(tag);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        tag.put("bossName", new StringTag(bossName));

        if (dungeonID != null) {
            tag.put("dungeonID", new IntTag(dungeonID));
        }

        if (returnPoint != null) {
            tag.put("returnPoint", returnPoint.toCompoundTag());
        }

        if (trophy != null) {
            CompoundTag trophyNBT = new CompoundTag();
            trophy.writeToNBT(trophyNBT);
            tag.put("trophy", trophyNBT);
        }

        if (blocksDestroyOnDeath != null && !blocksDestroyOnDeath.isEmpty()) {
            CompoundTag blockList = new CompoundTag();
            int idx = 0;
            for (BlockCoordinate block : blocksDestroyOnDeath) {
                blockList.put(String.valueOf(idx++), block.toCompoundTag());
            }
            blockList.put("length", new IntTag(idx));
            tag.put("blocksDestroyOnDeath", blockList);
        }

        super.addAdditionalSaveData(tag);
    }

    @Override
    public void returnToHome() {
        if (returnPoint == null) return;
        moveTo(returnPoint.x, returnPoint.y, returnPoint.z, 0, 0);
    }

    @Override
    public void setReturnPoint(@Nullable BlockCoordinate returnPoint) {
        this.returnPoint = returnPoint;
    }
}
