package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.entity.sunspirit.MobBossSunspirit;

public class ProjectileFireElement extends Projectile {
    public int bounceCount = 0;
    public float initialSpeed = 0.5F;
    public int ticksLived = 0;

    public ProjectileFireElement(World world) {
        super(world);
        this.initProjectile();
    }

    public ProjectileFireElement(World world, Mob owner) {
        super(world, owner);
        this.initProjectile();
    }

    public ProjectileFireElement(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.initProjectile();
    }

    @Override
    protected void initProjectile() {
        super.initProjectile();
        this.damage = 2;
        this.defaultGravity = 0.0F;
        this.defaultProjectileSpeed = 1.0F;
        this.setSize(1.0F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        ticksLived++;
        this.remainingFireTicks = 10;

        int maxBounces = 10;
        int maxTicks = 600;
        if (!this.world.isClientSide && (ticksLived > maxTicks || bounceCount >= maxBounces)) {
            this.remove();
            return;
        }

        if (this.world.isClientSide) {
            this.world.spawnParticle("flame", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
        }
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (!this.world.isClientSide) {
            if (hitResult.entity instanceof Mob) {
                if (hitResult.entity instanceof MobBossSunspirit) {
                    this.remove();
                }
                hitResult.entity.hurt(this.owner, this.damage, DamageType.FIRE);
                hitResult.entity.remainingFireTicks = 10;
                this.remove();
                return;
            }

            if (hitResult.side != null) {
                switch (hitResult.side) {
                    case BOTTOM:
                    case TOP:
                        this.yd = -this.yd * 1.0F;
                        break;
                    case NORTH:
                    case SOUTH:
                        this.zd = -this.zd * 1.0F;
                        break;
                    case WEST:
                    case EAST:
                        this.xd = -this.xd * 1.0F;
                        break;
                }
                bounceCount++;
            }
        }
    }

    @Override
    public void setHeading(double newMotionX, double newMotionY, double newMotionZ, float speed, float randomness) {
        super.setHeading(newMotionX, newMotionY, newMotionZ, initialSpeed, randomness);
    }

    @Override
    public void afterTick() {
        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;
        float f = MathHelper.sqrt(this.xd * this.xd + this.zd * this.zd);
        this.yRot = (float)(Math.atan2(this.xd, this.zd) * 180.0 / Math.PI);

        for(this.xRot = (float)(Math.atan2(this.yd, f) * 180.0 / Math.PI); this.xRot - this.xRotO < -180.0F; this.xRotO -= 360.0F) {
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

        if (this.isInWater()) {
            this.waterTick();
        }


        this.setPos(this.x, this.y, this.z);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.bounceCount = tag.getInteger("bounceCount");
        this.ticksLived = tag.getInteger("ticksLived");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("bounceCount", this.bounceCount);
        tag.putInt("ticksLived", this.ticksLived);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 1.0F;
    }

    @Override
    public boolean hurt(Entity entity, int damage, DamageType type) {
        if (!this.world.isClientSide) {
            if (entity != null) {
                if (entity instanceof Mob) {
                    this.owner = (Mob)entity;
                }
                Vec3 lookAngle = entity.getLookAngle();
                if (lookAngle != null) {
                    this.setHeading(lookAngle.x, lookAngle.y, lookAngle.z, initialSpeed, 0.0F);
                    bounceCount++;
                }
                return true;
            }
        }
        return false;
    }
}