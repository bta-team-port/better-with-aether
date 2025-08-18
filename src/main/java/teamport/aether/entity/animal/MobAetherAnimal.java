package teamport.aether.entity.animal;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.animal.Creature;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.AetherTranslatableDeathMessage;


public abstract class MobAetherAnimal extends MobAnimal implements Creature, AetherTranslatableDeathMessage {

    public MobAetherAnimal(World world) {
        super(world);
        this.scoreValue = 10;
    }

    public int getMaxSpawnedInChunk() {
        return 8;
    }

    public float getBlockPathWeight(int x, int y, int z) {
        return this.world.getBlockId(x, y - 1, z) == AetherBlocks.GRASS_AETHER.id() ? 10.0F : this.world.getLightBrightness(x, y, z) - 0.5F;
    }

    public boolean canSpawnHere() {
        int x = MathHelper.floor(this.x);
        int y = MathHelper.floor(this.bb.minY);
        int z = MathHelper.floor(this.z);
        int id = this.world.getBlockId(x, y - 1, z);
        if (Blocks.blocksList[id] == null) {
            return false;
        } else {
            return Blocks.blocksList[id].hasTag(AetherBlockTags.PASSIVE_MOBS_SPAWN) && this.world.getFullBlockLightValue(x, y, z) > 8;
        }
    }

}