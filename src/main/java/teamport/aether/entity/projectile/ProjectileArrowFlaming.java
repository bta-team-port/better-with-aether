package teamport.aether.entity.projectile;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.ProjectileArrow;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;

public class ProjectileArrowFlaming extends ProjectileArrow {
    public ProjectileArrowFlaming(World world, Mob entityliving, boolean doesArrowBelongToPlayer, int arrowType) {
        super(world, entityliving, doesArrowBelongToPlayer, arrowType);
    }

    public void tick() {
        if (this.shake > 0) {
            --this.shake;
        }

        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = MathHelper.sqrt(this.xd * this.xd + this.zd * this.zd);
            this.yRotO = this.yRot = (float)(Math.atan2(this.xd, this.zd) * 180.0 / Math.PI);
            this.xRotO = this.xRot = (float)(Math.atan2(this.yd, f) * 180.0 / Math.PI);
        }

        Block<?> block = this.world.getBlock(this.xTile, this.yTile, this.zTile);
        if (block != null) {
            AABB aabb = block.getCollisionBoundingBoxFromPool(this.world, this.xTile, this.yTile, this.zTile);
            if (aabb != null && aabb.contains(Vec3.getTempVec3(this.x, this.y, this.z))) {
                this.setGrounded(true);
            }
        }

        if (this.isGrounded()) {
            int id = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
            int meta = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            if (id == this.inTile && meta == this.inData) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.remove();
                }

            } else {
                this.setGrounded(false);
                this.xd *= (double)this.random.nextFloat() * 0.2;
                this.yd *= (double)this.random.nextFloat() * 0.2;
                this.zd *= (double)this.random.nextFloat() * 0.2;
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {
            this.world.spawnParticle("flame", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);this.world.spawnParticle("flame", this.x + this.xd * 0.5, this.y + this.yd * 0.5, this.z + this.zd * 0.5, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);
            this.world.spawnParticle("smoke", this.x, this.y, this.z, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);
            this.world.spawnParticle("smoke", this.x + this.xd * 0.5, this.y + this.yd * 0.5, this.z + this.zd * 0.5, this.xd * 0.05, this.yd * 0.05 - 0.1, this.zd * 0.05, 0);

            super.tick();
        }
    }

    public void onHit(HitResult hitResult) {
        if (hitResult.entity != null) {
            if (hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT)) {
                if (this.isOnFire()) {
                    hitResult.entity.fireHurt();
                }

                if (!this.world.isClientSide) {
                    this.world.playSoundAtEntity(null, this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                    hitResult.entity.maxFireTicks = 30 * 20;
                    hitResult.entity.remainingFireTicks = 30 * 20;
                    this.remove();
                }
            } else {
                this.xTile = hitResult.x;
                this.yTile = hitResult.y;
                this.zTile = hitResult.z;
                this.inTile = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
                this.inData = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                this.xd = (float) (hitResult.location.x - this.x);
                this.yd = (float) (hitResult.location.y - this.y);
                this.zd = (float) (hitResult.location.z - this.z);
                float f1 = MathHelper.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
                this.x -= this.xd / (double) f1 * 0.05;
                this.y -= this.yd / (double) f1 * 0.05;
                this.z -= this.zd / (double) f1 * 0.05;
                this.inGroundAction();
            }

        }
    }

    public void inGroundAction() {
        this.world.playSoundAtEntity(null, this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));

        for(int j = 0; j < 4; ++j) {
            this.world.spawnParticle("item", this.x, this.y, this.z, 0.0, 0.0, 0.0, Items.AMMO_FIREBALL.id);
        }

        if (world.getBlock(xTile, yTile + 1, zTile) == null && Blocks.FIRE.canPlaceBlockAt(this.world, xTile, yTile + 1, zTile)) {
            world.setBlockWithNotify(this.xTile, this.yTile + 1, this.zTile, Blocks.FIRE.id());
        }
        this.remove();
    }

}
