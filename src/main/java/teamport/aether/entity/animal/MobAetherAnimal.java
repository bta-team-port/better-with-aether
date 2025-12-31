package teamport.aether.entity.animal;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.animal.Creature;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
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
    public float getBlockPathWeight(int x, int y, int z) {
        if (this.world == null) return super.getBlockPathWeight(x, y, z);
        return this.world.getBlockId(x, y - 1, z) == AetherBlocks.GRASS_AETHER.id() ? 10.0F : this.world.getLightBrightness(x, y, z) - 0.5F;
    }

    @Override
    public boolean canSpawnHere() {
        if (this.world == null) return false;
        int x = MathHelper.floor(this.x);
        int y = MathHelper.floor(this.bb.minY);
        int z = MathHelper.floor(this.z);
        int id = this.world.getBlockId(x, y - 1, z);
        Block<?> block = Blocks.blocksList[id];
        if (block == null) return false;
        return block.hasTag(AetherBlockTags.PASSIVE_MOBS_SPAWN) && this.world.getFullBlockLightValue(x, y, z) > 8;
    }
}
