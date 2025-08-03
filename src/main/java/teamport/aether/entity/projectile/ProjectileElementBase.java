package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;

public class ProjectileElementBase extends Projectile {
    public int bounceCount = 0;
    public float initialSpeed = 0.5F;
    public int ticksLived = 0;

    public ProjectileElementBase(World world) {
        super(world);
        this.initProjectile();
    }

    public ProjectileElementBase(World world, Mob owner) {
        super(world, owner);
        this.initProjectile();
    }

    public ProjectileElementBase(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.initProjectile();
    }


    @Override
    protected void initProjectile() {
        super.initProjectile();
        this.damage = 2;
        this.defaultGravity = 0.0F;
        this.defaultProjectileSpeed = 1.0F;
        this.setSize(0.5F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        ticksLived++;

        int maxBounces = 20;
        int maxTicks = 1200;
        if (!this.world.isClientSide && (ticksLived > maxTicks || bounceCount >= maxBounces)) {
            this.remove();
        }
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (!this.world.isClientSide) {
            if (hitResult.entity instanceof Mob) {
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
        this.yRot = (float)(Math.atan2(this.xd, this.zd) * 180.0 / Math.PI);

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
                    bounceCount = 16;
                }
                return true;
            }
        }
        return false;
    }
}