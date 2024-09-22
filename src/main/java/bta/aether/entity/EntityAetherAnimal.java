package bta.aether.entity;

import bta.aether.AetherBlockTags;
import bta.aether.block.AetherBlocks;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.EntityPathfinder;
import net.minecraft.core.entity.animal.IAnimal;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

public class EntityAetherAnimal extends EntityPathfinder implements IAnimal {
    protected String skinName;

    public EntityAetherAnimal(World world) {
        super(world);
    }

    public int getMaxSpawnedInChunk() {
        return 120;
    }

    protected float getBlockPathWeight(int x, int y, int z) {
        return this.world.getBlockId(x, y - 1, z) == AetherBlocks.grassAether.id ? 10.0F : this.world.getLightBrightness(x, y, z) - 0.5F;
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
    }

    public boolean getCanSpawnHere() {
        int x = MathHelper.floor_double(this.x);
        int y = MathHelper.floor_double(this.bb.minY);
        int z = MathHelper.floor_double(this.z);
        int id = this.world.getBlockId(x, y - 1, z);
        if (Block.blocksList[id] == null) {
            return false;
        } else {
            return Block.blocksList[id].hasTag(AetherBlockTags.PASSIVE_MOBS_SPAWN) && this.world.getFullBlockLightValue(x, y, z) > 8 && super.getCanSpawnHere();
        }
    }

    public int getTalkInterval() {
        return 120;
    }

}
