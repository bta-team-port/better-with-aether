package teamport.aether.entity.animal;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.animal.Creature;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.AetherDeathMessage;

public abstract class MobAetherAnimal extends MobAnimal implements Creature, AetherDeathMessage {

    protected MobAetherAnimal(World world) {
        super(world);
        this.scoreValue = 10;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }

    @Override
    protected float getBlockPathWeight(@NonNull TilePosc blockPos) {
        if (!this.world.isBlockLoaded(blockPos)) {
            return 0.0F;
        } else {
            return this.world.getBlockType(blockPos.down(new TilePos())) == AetherBlocks.GRASS_AETHER ? 10.0F : this.world.getLightBrightness(blockPos) - 0.5F;
        }
    }

    @Override
    public boolean canSpawnHere() {
        TilePos blockPos = new TilePos(this.x, this.bb.minY, this.z);

        int id = this.world.getBlockData(blockPos.down());
        Block<?> block = Blocks.blocksList[id];
        if (block == null) return false;
        return block.hasTag(AetherBlockTags.PASSIVE_MOBS_SPAWN) && this.world.getFullBlockLightValue(blockPos) > 8;
    }
}
