package bta.aether.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.HitResult;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

import java.util.List;

public class EntityLightningKnife extends Entity {
    private int xTileKnife = -1;
    private int yTileKnife = -1;
    private int zTileKnife = -1;
    private int inTileKnife = 0;
    private boolean inGroundKnife = false;
    public int shakeKnife = 0;
    public EntityLiving thrower;
    private int ticksInGroundKnife;
    private int ticksInAirKnife = 0;
    public int damage = 0;

    public EntityLightningKnife(World world) {
        super(world);
        this.setSize(0.25F, 0.25F);
    }
    public EntityLightningKnife(World world, double d, double d1, double d2) {
        super(world);
        this.ticksInGroundKnife = 0;
        this.setSize(0.25F, 0.25F);
        this.setPos(d, d1, d2);
        this.heightOffset = 0.0F;
    }
    public EntityLightningKnife(World world, EntityLiving entityliving) {
        super(world);
        this.thrower = entityliving;
        this.setSize(0.25F, 0.25F);
        this.moveTo(entityliving.x, entityliving.y + (double)entityliving.getHeadHeight(), entityliving.z, entityliving.yRot, entityliving.xRot);
        this.x -= MathHelper.cos(this.yRot / 180.0F * 3.141593F) * 0.16F;
        this.y -= 0.10000000149011612;
        this.z -= MathHelper.sin(this.yRot / 180.0F * 3.141593F) * 0.16F;
        this.setPos(this.x, this.y, this.z);
        this.heightOffset = 0.0F;
        float f = 0.4F;
        this.xd = -MathHelper.sin(this.yRot / 180.0F * 3.141593F) * MathHelper.cos(this.xRot / 180.0F * 3.141593F) * f;
        this.zd = MathHelper.cos(this.yRot / 180.0F * 3.141593F) * MathHelper.cos(this.xRot / 180.0F * 3.141593F) * f;
        this.yd = -MathHelper.sin(this.xRot / 180.0F * 3.141593F) * f;
        this.setKnifeHeading(this.xd, this.yd, this.zd, 1.5F, 1.0F);
    }
    @Override
    protected void init() {
    }
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d1 = this.bb.getAverageEdgeLength() * 4.0;
        d1 *= 64.0;
        return distance < d1 * d1;
    }
    public void setKnifeHeading(double d, double d1, double d2, float f, float f1) {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += this.random.nextGaussian() * 0.007499999832361937 * (double)f1;
        d1 += this.random.nextGaussian() * 0.007499999832361937 * (double)f1;
        d2 += this.random.nextGaussian() * 0.007499999832361937 * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        this.xd = d;
        this.yd = d1;
        this.zd = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        this.yRotO = this.yRot = (float)(Math.atan2(d, d2) * 180.0 / 3.1415927410125732);
        this.xRotO = this.xRot = (float)(Math.atan2(d1, f3) * 180.0 / 3.1415927410125732);
        this.ticksInGroundKnife = 0;
    }

    public void setKnifeHeadingPrecise(double d, double d1, double d2, float f, float f1) {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d *= f;
        d1 *= f;
        d2 *= f;
        this.xd = d;
        this.yd = d1;
        this.zd = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        this.yRotO = this.yRot = (float)(Math.atan2(d, d2) * 180.0 / 3.1415927410125732);
        this.xRotO = this.xRot = (float)(Math.atan2(d1, f3) * 180.0 / 3.1415927410125732);
        this.ticksInGroundKnife = 0;
    }
    @Override
    public void lerpMotion(double xd, double yd, double zd) {
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = MathHelper.sqrt_double(xd * xd + zd * zd);
            this.yRotO = this.yRot = (float)(Math.atan2(xd, zd) * 180.0 / 3.1415927410125732);
            this.xRotO = this.xRot = (float)(Math.atan2(yd, f) * 180.0 / 3.1415927410125732);
        }

    }
    @Override
    public void tick() {
        this.xOld = this.x;
        this.yOld = this.y;
        this.zOld = this.z;
        super.tick();
        if (this.shakeKnife > 0) {
            --this.shakeKnife;
        }

        if (this.inGroundKnife) {
            int i = this.world.getBlockId(this.xTileKnife, this.yTileKnife, this.zTileKnife);
            if (i == this.inTileKnife) {
                ++this.ticksInGroundKnife;
                if (this.ticksInGroundKnife == 1200) {
                    this.remove();
                }

                return;
            }

            this.inGroundKnife = false;
            this.xd *= this.random.nextFloat() * 0.2F;
            this.yd *= this.random.nextFloat() * 0.2F;
            this.zd *= this.random.nextFloat() * 0.2F;
            this.ticksInGroundKnife = 0;
            this.ticksInAirKnife = 0;
        } else {
            ++this.ticksInAirKnife;
        }

        Vec3d vec3d = Vec3d.createVector(this.x, this.y, this.z);
        Vec3d vec3d1 = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
        HitResult movingobjectposition = this.world.checkBlockCollisionBetweenPoints(vec3d, vec3d1);
        vec3d = Vec3d.createVector(this.x, this.y, this.z);
        vec3d1 = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
        if (movingobjectposition != null) {
            vec3d1 = Vec3d.createVector(movingobjectposition.location.xCoord, movingobjectposition.location.yCoord, movingobjectposition.location.zCoord);
        }

        if (!this.world.isClientSide) {
            Entity entity = null;
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.addCoord(this.xd, this.yd, this.zd).expand(1.0, 1.0, 1.0));
            double d = 0.0;

            for (Entity ent : list) {
                if (ent.isPickable() && (ent != this.thrower || this.ticksInAirKnife >= 5)) {
                    float f4 = 0.3F;
                    AABB axisalignedbb = ent.bb.expand(f4, f4, f4);
                    HitResult movingobjectposition1 = axisalignedbb.func_1169_a(vec3d, vec3d1);
                    if (movingobjectposition1 != null) {
                        double d1 = vec3d.distanceTo(movingobjectposition1.location);
                        if (d1 < d || d == 0.0) {
                            entity = ent;
                            d = d1;
                        }
                    }
                }
            }

            if (entity != null) {
                movingobjectposition = new HitResult(entity);
            }
        }

        if (movingobjectposition != null) {
            if (movingobjectposition.entity != null) {
                movingobjectposition.entity.hurt(this.thrower, this.damage, DamageType.COMBAT);
            }

            for(int j = 0; j < 8; ++j) {
                this.world.spawnParticle("snowballpoof", this.x, this.y, this.z, 0.0, 0.0, 0.0);
            }

            this.remove();
        }

        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;
        float f = MathHelper.sqrt_double(this.xd * this.xd + this.zd * this.zd);
        this.yRot = (float)(Math.atan2(this.xd, this.zd) * 180.0 / 3.1415927410125732);

        this.xRot = (float)(Math.atan2(this.yd, f) * 180.0 / 3.1415927410125732);
        while (this.xRot - this.xRotO < -180.0F) {
            this.xRotO -= 360.0F;
        }

        while(this.xRot - this.xRotO >= 180.0F) {
            this.xRotO += 360.0F;
        }

        while(this.yRot - this.yRotO < -180.0F) {
            this.yRotO -= 360.0F;
        }

        while(this.yRot - this.yRotO >= 180.0F) {
            this.yRotO += 360.0F;
        }

        this.xRot = this.xRotO + (this.xRot - this.xRotO) * 0.2F;
        this.yRot = this.yRotO + (this.yRot - this.yRotO) * 0.2F;
        float f1 = 0.99F;
        float f2 = 0.03F;
        if (this.isInWater()) {
            for(int k = 0; k < 4; ++k) {
                float f3 = 0.25F;
                this.world.spawnParticle("bubble", this.x - this.xd * (double)f3, this.y - this.yd * (double)f3, this.z - this.zd * (double)f3, this.xd, this.yd, this.zd);
            }

            f1 = 0.8F;
        }

        this.xd *= f1;
        this.yd *= f1;
        this.zd *= f1;
        this.yd -= f2;
        this.setPos(this.x, this.y, this.z);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putShort("xTile", (short)this.xTileKnife);
        tag.putShort("yTile", (short)this.yTileKnife);
        tag.putShort("zTile", (short)this.zTileKnife);
        tag.putShort("inTile", (short)this.inTileKnife);
        tag.putByte("shake", (byte)this.shakeKnife);
        tag.putByte("inGround", (byte)(this.inGroundKnife ? 1 : 0));
    }

    @Override
    public String getEntityTexture() {
        return this.entityData.getByte(1) != 1 ? super.getEntityTexture() : "/assets/aether/item/LightningKnife.png";
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.xTileKnife = tag.getShort("xTile");
        this.yTileKnife = tag.getShort("yTile");
        this.zTileKnife = tag.getShort("zTile");
        this.inTileKnife = tag.getShort("inTile") & 16383;
        this.shakeKnife = tag.getByte("shake") & 255;
        this.inGroundKnife = tag.getByte("inGround") == 1;
    }
    @Override
    public float getShadowHeightOffs() {
        return 0.0F;
    }
}
