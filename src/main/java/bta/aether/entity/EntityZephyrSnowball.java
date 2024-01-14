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

public class EntityZephyrSnowball extends Entity {
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile = 0;
    private boolean inGround = false;
    public int shake = 0;
    public EntityLiving owner;
    private int field_9396_k;
    private int ticksInAir = 0;
    public double field_9405_b;
    public double field_9404_c;
    public double field_9403_d;

    public EntityZephyrSnowball(World world) {
        super(world);
        this.setSize(1.0f, 1.0f);
    }

    @Override
    protected void init() {
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d1 = this.bb.getAverageEdgeLength() * 4.0;
        return distance < (d1 *= 64.0) * d1;
    }

    public EntityZephyrSnowball(World world, double d, double d1, double d2, double d3, double d4, double d5) {
        super(world);
        this.setSize(1.0f, 1.0f);
        this.moveTo(d, d1, d2, this.yRot, this.xRot);
        this.setPos(d, d1, d2);
        double d6 = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
        this.field_9405_b = d3 / d6 * 0.1;
        this.field_9404_c = d4 / d6 * 0.1;
        this.field_9403_d = d5 / d6 * 0.1;
    }

    public EntityZephyrSnowball(World world, EntityLiving entityliving, double d, double d1, double d2) {
        super(world);
        this.owner = entityliving;
        this.setSize(1.0f, 1.0f);
        this.moveTo(entityliving.x, entityliving.y, entityliving.z, entityliving.yRot, entityliving.xRot);
        this.setPos(this.x, this.y, this.z);
        this.heightOffset = 0.0f;
        this.zd = 0.0;
        this.yd = 0.0;
        this.xd = 0.0;
        double d3 = MathHelper.sqrt_double((d += this.random.nextGaussian() * 0.4) * d + (d1 += this.random.nextGaussian() * 0.4) * d1 + (d2 += this.random.nextGaussian() * 0.4) * d2);
        this.field_9405_b = d / d3 * 0.1;
        this.field_9404_c = d1 / d3 * 0.1;
        this.field_9403_d = d2 / d3 * 0.1;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void tick() {
        block17: {
            super.tick();
            this.remainingFireTicks = 0;
            if (this.shake > 0) {
                --this.shake;
            }
            if (this.inGround) {
                int i = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
                if (i != this.inTile) {
                    this.inGround = false;
                    this.xd *= (double)(this.random.nextFloat() * 0.5f);
                    this.yd *= (double)(this.random.nextFloat() * 0.5f);
                    this.zd *= (double)(this.random.nextFloat() * 0.5f);
                    this.field_9396_k = 0;
                    this.ticksInAir = 0;
                    break block17;
                } else {
                    ++this.field_9396_k;
                    if (this.field_9396_k == 1200) {
                        this.remove();
                    }
                    return;
                }
            }
            ++this.ticksInAir;
        }
        Vec3d vec3d = Vec3d.createVector(this.x, this.y, this.z);
        Vec3d vec3d1 = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
        HitResult movingobjectposition = this.world.checkBlockCollisionBetweenPoints(vec3d, vec3d1);
        vec3d = Vec3d.createVector(this.x, this.y, this.z);
        vec3d1 = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
        if (movingobjectposition != null) {
            vec3d1 = Vec3d.createVector(movingobjectposition.location.xCoord, movingobjectposition.location.yCoord, movingobjectposition.location.zCoord);
        }
        Entity entity = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.addCoord(this.xd, this.yd, this.zd).expand(1.0, 1.0, 1.0));
        double d = 0.0;
        for (int j = 0; j < list.size(); ++j) {
            double d1;
            float f2;
            AABB axisalignedbb;
            HitResult movingobjectposition1;
            Entity entity1 = list.get(j);
            if (!entity1.isPickable() || entity1 == this.owner && this.ticksInAir < 25 || (movingobjectposition1 = (axisalignedbb = entity1.bb.expand(f2 = 0.3f, f2, f2)).func_1169_a(vec3d, vec3d1)) == null || !((d1 = vec3d.distanceTo(movingobjectposition1.location)) < d) && d != 0.0) continue;
            entity = entity1;
            d = d1;
        }
        if (entity != null) {
            movingobjectposition = new HitResult(entity);
        }
        if (movingobjectposition != null) {
            if (!this.world.isClientSide) {
                if (movingobjectposition.entity == null || !movingobjectposition.entity.hurt(this.owner, 0, DamageType.COMBAT)) {
                    // empty if block
                }
                this.world.newExplosion(this, this.x, this.y, this.z, 1.5f, true, false);
            }
            this.remove();
        }
        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;
        float f = MathHelper.sqrt_double(this.xd * this.xd + this.zd * this.zd);
        this.yRot = (float)(Math.atan2(this.xd, this.zd) * 180.0 / 3.1415927410125732);
        this.xRot = (float)(Math.atan2(this.yd, f) * 180.0 / 3.1415927410125732);
        while (this.xRot - this.xRotO < -180.0f) {
            this.xRotO -= 360.0f;
        }
        while (this.xRot - this.xRotO >= 180.0f) {
            this.xRotO += 360.0f;
        }
        while (this.yRot - this.yRotO < -180.0f) {
            this.yRotO -= 360.0f;
        }
        while (this.yRot - this.yRotO >= 180.0f) {
            this.yRotO += 360.0f;
        }
        this.xRot = this.xRotO + (this.xRot - this.xRotO) * 0.2f;
        this.yRot = this.yRotO + (this.yRot - this.yRotO) * 0.2f;
        float f1 = 1.0f;
        if (this.isInWater()) {
            for (int k = 0; k < 4; ++k) {
                float f3 = 0.25f;
                this.world.spawnParticle("bubble", this.x - this.xd * (double)f3, this.y - this.yd * (double)f3, this.z - this.zd * (double)f3, this.xd, this.yd, this.zd);
            }
            f1 = 0.8f;
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putShort("xTile", (short)this.xTile);
        tag.putShort("yTile", (short)this.yTile);
        tag.putShort("zTile", (short)this.zTile);
        tag.putShort("inTile", (short)this.inTile);
        tag.putByte("shake", (byte)this.shake);
        tag.putByte("inGround", (byte)(this.inGround ? 1 : 0));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.xTile = tag.getShort("xTile");
        this.yTile = tag.getShort("yTile");
        this.zTile = tag.getShort("zTile");
        this.inTile = tag.getShort("inTile") & 0x3FFF;
        this.shake = tag.getByte("shake") & 0xFF;
        this.inGround = tag.getByte("inGround") == 1;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 1.0f;
    }

    @Override
    public float getShadowHeightOffs() {
        return 0.0f;
    }
}
