package bta.aether.entity.projectiles;

import bta.aether.world.AetherDimension;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockTNT;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.monster.EntityCreeper;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.world.World;

public class EntityArrowFlaming extends EntityArrow {
    public EntityArrowFlaming(World world) {
        this(world, 4);
    }

    public EntityArrowFlaming(World world, int arrowType) {
        super(world, arrowType);
    }

    public EntityArrowFlaming(World world, double d, double d1, double d2, int arrowType) {
        super(world, d, d1, d2, arrowType);
    }

    public EntityArrowFlaming(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer, int arrowType) {
        super(world, entityliving, doesArrowBelongToPlayer, arrowType);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.inGround) {
            if (world.dimension.id != AetherDimension.AetherDimensionID) this.world.spawnParticle("flame", this.x + this.xd * 0.5, this.y + this.yd * 0.5, this.z + this.zd * 0.5, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);
            else this.world.spawnParticle("snowshovel", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
            this.world.spawnParticle("smoke", this.x + this.xd * 0.5, this.y + this.yd * 0.5, this.z + this.zd * 0.5, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);
        }
    }

    @Override
    public void onHit(HitResult hitResult) {
        super.onHit(hitResult);

        if (hitResult.entity != null) {
            if (hitResult.entity instanceof EntityCreeper) {
                EntityCreeper entityCreeper = (EntityCreeper) hitResult.entity;
                entityCreeper.setTarget(entityCreeper);
                return;
            }
            hitResult.entity.fireHurt();
        }

        if (world.getBlockId(this.xTile, this.yTile, this.zTile) == Block.tnt.id) {
            ((BlockTNT)world.getBlock(this.xTile, this.yTile, this.zTile)).ignite(world, this.xTile, this.yTile, this.zTile, true);
            this.remove();
            return;
        }

        if (world.dimension.id != AetherDimension.AetherDimensionID && hitResult.side != null) {
            int x = this.xTile + hitResult.side.getOffsetX();
            int y = this.yTile + hitResult.side.getOffsetY();
            int z = this.zTile + hitResult.side.getOffsetZ();
            if (world.getBlockId(x, y, z) == 0 && Block.fire.canPlaceBlockAt(this.world, x, y, z)) {
                world.setBlockWithNotify(x, y, z, Block.fire.id);
            }
        }
    }

    @Override
    public String getEntityTexture() {
        return this.entityData.getByte(1) != 1 ? super.getEntityTexture() : "/assets/aether/other/FlamingArrows.png";
    }
}

