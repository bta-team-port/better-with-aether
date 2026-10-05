package teamport.aether.entity.animal;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.MobPathfinder;
import net.minecraft.core.entity.animal.Creature;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;

public abstract class MobAetherAnimal extends MobPathfinder implements Creature {
    public Player closestPlayer;

    public MobAetherAnimal(World world) {
        super(world);
        this.scoreValue = 10;
    }

    @Override
    protected void updateAI() {
        super.updateAI();
        this.checkForPlayerHoldingItem();
    }

    protected void checkForPlayerHoldingItem() {
        if (this.target == null) {
            this.closestPlayer = this.world.getClosestPlayer(this.x, this.y, this.z, 10.0F);
        }

        if (this.closestPlayer != null) {
            if (this.isFavouriteItem(this.closestPlayer.getHeldItem())) {
                this.setTarget(this.closestPlayer);
            } else {
                this.setTarget(null);
                this.closestPlayer = null;
            }
        }

        if (this.target != null) {
            float distanceToEntity = this.target.distanceTo(this);
            if (distanceToEntity < 3.0F) {
                this.moveForward = 0.0F;
            }
        }

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
        TilePosc blockPos = new TilePos(MathHelper.floor(this.x), MathHelper.floor(this.bb.minY), MathHelper.floor(this.z));
        Block<?> block = this.world.getBlockType(blockPos.down(new TilePos()));

        return block.hasTag(AetherBlockTags.PASSIVE_MOBS_SPAWN) && this.world.getFullBlockLightValue(blockPos) > 8 && super.canSpawnHere();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 120;
    }

    public boolean isFavouriteItem(ItemStack itemStack) {
        return false;
    }
}
