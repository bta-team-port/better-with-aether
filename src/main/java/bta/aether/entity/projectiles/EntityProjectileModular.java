package bta.aether.entity.projectiles;

import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

import java.util.List;

public abstract class EntityProjectileModular extends EntityArrow {

    public EntityProjectileModular(World world) {
        this(world, 11);
    }

    public EntityProjectileModular(World world, int arrowType) {
        super(world, arrowType);
    }

    public EntityProjectileModular(World world, double d, double d1, double d2, int arrowType) {
        super(world, d, d1, d2, arrowType);
    }

    public EntityProjectileModular(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer, int arrowType) {
        super(world, entityliving, doesArrowBelongToPlayer, arrowType);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = MathHelper.sqrt_double(this.xd * this.xd + this.zd * this.zd);
            this.yRotO = this.yRot = (float) (Math.atan2(this.xd, this.zd) * 180.0 / 3.1415927410125732);
            this.xRotO = this.xRot = (float) (Math.atan2(this.yd, f) * 180.0 / 3.1415927410125732);
        }

        int i = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
        if (i > 0) {
            Block.blocksList[i].setBlockBoundsBasedOnState(this.world, this.xTile, this.yTile, this.zTile);
            AABB axisalignedbb = Block.blocksList[i].getCollisionBoundingBoxFromPool(this.world, this.xTile, this.yTile, this.zTile);
            if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3d.createVector(this.x, this.y, this.z))) {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0) {
            --this.arrowShake;
        }

        if (this.inGround) {
            int j = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
            int k = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            if (j == this.inTile && k == this.field_28019_h) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.remove();
                }

            } else {
                this.inGround = false;
                this.xd *= this.random.nextFloat() * 0.2F;
                this.yd *= this.random.nextFloat() * 0.2F;
                this.zd *= this.random.nextFloat() * 0.2F;
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {
            spawnParticles();

            ++this.ticksInAir;
            Vec3d oldPos = Vec3d.createVector(this.x, this.y, this.z);
            Vec3d newPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
            HitResult movingobjectposition = this.world.checkBlockCollisionBetweenPoints(oldPos, newPos, false, true);
            oldPos = Vec3d.createVector(this.x, this.y, this.z);
            newPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
            if (movingobjectposition != null) {
                newPos = Vec3d.createVector(movingobjectposition.location.xCoord, movingobjectposition.location.yCoord, movingobjectposition.location.zCoord);
            }

            Entity entity = null;
            List list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.addCoord(this.xd, this.yd, this.zd).expand(1.0, 1.0, 1.0));
            double d = 0.0;

            float f5;
            for (int l = 0; l < list.size(); ++l) {
                Entity entity1 = (Entity) list.get(l);
                if (entity1.isPickable() && (entity1 != this.owner || this.ticksInAir >= 5)) {
                    f5 = 0.3F;
                    AABB axisalignedbb1 = entity1.bb.expand((double) f5, (double) f5, (double) f5);
                    HitResult movingobjectposition1 = axisalignedbb1.func_1169_a(oldPos, newPos);
                    if (movingobjectposition1 != null) {
                        double d1 = oldPos.distanceTo(movingobjectposition1.location);
                        if (d1 < d || d == 0.0) {
                            entity = entity1;
                            d = d1;
                        }
                    }
                }
            }

            if (entity != null) {
                movingobjectposition = new HitResult(entity);
            }

            float f1;
            if (movingobjectposition != null && movingobjectposition.entity != null) {

                if (hurtEntity(movingobjectposition.entity)) {
                    playHitSound();

                } else {
                    this.xTile = movingobjectposition.x;
                    this.yTile = movingobjectposition.y;
                    this.zTile = movingobjectposition.z;
                    this.inTile = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
                    this.field_28019_h = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                    this.xd = (float) (movingobjectposition.location.xCoord - this.x);
                    this.yd = (float) (movingobjectposition.location.yCoord - this.y);
                    this.zd = (float) (movingobjectposition.location.zCoord - this.z);
                    f1 = MathHelper.sqrt_double(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
                    this.x -= this.xd / (double) f1 * 0.05000000074505806;
                    this.y -= this.yd / (double) f1 * 0.05000000074505806;
                    this.z -= this.zd / (double) f1 * 0.05000000074505806;
                    this.inGroundAction();
                }

                onHit(movingobjectposition);
            }

                this.x += this.xd;
                this.y += this.yd;
                this.z += this.zd;
                f1 = MathHelper.sqrt_double(this.xd * this.xd + this.zd * this.zd);
                this.yRot = (float) (Math.atan2(this.xd, this.zd) * 180.0 / 3.1415927410125732);

                for (this.xRot = (float) (Math.atan2(this.yd, f1) * 180.0 / 3.1415927410125732); this.xRot - this.xRotO < -180.0F; this.xRotO -= 360.0F) {
                }

                while (this.xRot - this.xRotO >= 180.0F) {
                    this.xRotO += 360.0F;
                }

                while (this.yRot - this.yRotO < -180.0F) {
                    this.yRotO -= 360.0F;
                }

                while (this.yRot - this.yRotO >= 180.0F) {
                    this.yRotO += 360.0F;
                }

                this.xRot = this.xRotO + (this.xRot - this.xRotO) * 0.2F;
                this.yRot = this.yRotO + (this.yRot - this.yRotO) * 0.2F;
                float f3 = this.arrowSpeed;
                f5 = this.arrowGravity;

                if (this.isInWater()) {
                    for (int i1 = 0; i1 < 4; ++i1) {
                        float f6 = 0.25F;
                        this.world.spawnParticle("bubble", this.x - this.xd * (double) f6, this.y - this.yd * (double) f6, this.z - this.zd * (double) f6, this.xd, this.yd, this.zd);
                    }
                    f3 = 0.8F;
                }

                this.xd *= f3;
                this.yd *= f3;
                this.zd *= f3;
                this.yd -= f5;
                this.setPos(this.x, this.y, this.z);

        }
    }

    protected void onHit(HitResult movingobjectposition) {
    }

    protected void playHitSound() {
        this.world.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }

    protected void playCollectedSound() {
        this.world.playSoundAtEntity(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
    }

    @Override
    protected void inGroundAction() {
        playHitSound();
        this.inGround = true;
        this.arrowShake = 7;
    }

    protected void spawnParticles(){
    }

    protected Boolean hurtEntity(Entity entity) {
        if (this.isOnFire()) entity.fireHurt();
        return entity.hurt(this.owner, this.arrowDamage, DamageType.COMBAT);
    }

    @Override
    public void playerTouch(EntityPlayer player) {
        if (!this.world.isClientSide) {
            if (this.inGround && this.doesArrowBelongToPlayer && this.arrowShake <= 0) {
                player.inventory.insertItem(this.stack, true);
                if (this.stack.stackSize <= 0) {
                    this.playCollectedSound();
                    player.onItemPickup(this, stack.itemID);
                    this.remove();
                }
            }
        }
    }

}
