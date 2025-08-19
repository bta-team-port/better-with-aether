package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProjectileElementBase extends Projectile{
    public int bounceCount = 0;
    public float initialSpeed = 0.5F;
    public int maxBounces = 20;

    public String[] particles = {"explode"};

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

    public ProjectileElementBase(World world, double v, double y, double v1, Mob ep) {
        super(world);
    }


    @Override
    public void initProjectile() {
        super.initProjectile();
        this.damage = 2;
        this.defaultGravity = 0.0F;
        this.defaultProjectileSpeed = 1.0F;
        this.setSize(0.5F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.world.isClientSide &&  bounceCount >= maxBounces) {
            doExplosion();
            this.remove();
        }
    }

    public void doExplosion() {
        for (int particle = 0; particle < 12; particle++) {
            double XParticle = x + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double YParticle = y + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double ZParticle = z + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);

            world.spawnParticle(particles[world.rand.nextInt(particles.length)], XParticle, YParticle, ZParticle, 0,0,0,0);
        }

        world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x, y, z, "random.explode", 0.25F, (1.3F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (!this.world.isClientSide) {
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
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("bounceCount", this.bounceCount);
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
                    bounceCount = 19;
                }
                return true;
            }
        }

        return false;
    }

    public static Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag compoundTag) {
        ProjectileElementBase elementBase = new ProjectileElementBase(world, x, y, z);
        if (hasVelocity) elementBase.setHeading(xd, yd, zd, 1, 0);
        if (owner instanceof Mob) elementBase.owner = (Mob) owner;
        return elementBase;
    }
}