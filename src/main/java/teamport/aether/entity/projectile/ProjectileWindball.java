package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.entity.AetherMobFallingToOverworld;
import teamport.aether.helper.MobUtil;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.items.AetherItems;

public class ProjectileWindball extends Projectile implements ProjectileAether, AetherMobFallingToOverworld {

    public ProjectileWindball(World world) {
        super(world);
        this.setSize(1.0F, 1.0F);
    }

    @Override
    public boolean canFallToOverworld() {
        return false;
    }

    public ProjectileWindball(World world, double x, double y, double z, double vX, double vY, double vZ) {
        super(world);
        this.setSize(1.0F, 1.0F);
        this.moveTo(x, y, z, this.yRot, this.xRot);
        this.setPos(x, y, z);
        this.setVelocity(vX, vY, vZ);
    }

    public ProjectileWindball(World world, Mob owner, double vX, double vY, double vZ) {
        super(world);
        this.setSize(1.0F, 1.0F);
        this.moveTo(owner.x, owner.y, owner.z, owner.yRot, owner.xRot);
        this.setPos(this.x, this.y, this.z);
        this.owner = owner;
        this.heightOffset = 0.0F;
        vX += (this.random.nextGaussian() - this.random.nextGaussian()) * 0.8;
        vY += this.random.nextGaussian() * 0.4;
        vZ += (this.random.nextGaussian() - this.random.nextGaussian()) * 0.8;
        this.setVelocity(vX, vY, vZ);
    }

    private void setVelocity(double vX, double vY, double vZ) {
        double velocity = MathHelper.sqrt(vX * vX + vY * vY + vZ * vZ);
        if (velocity != 0.0) {
            this.xd = vX / velocity;
            this.yd = vY / velocity;
            this.zd = vZ / velocity;
        } else {
            this.xd = 0.0;
            this.yd = 0.0;
            this.zd = 0.0;
        }

    }

    public void initProjectile() {
        this.damage = 0;
        this.defaultGravity = 0.0F;
        this.defaultProjectileSpeed = 1.0F;
    }

    public void tick() {
        super.tick();
        ++this.ticksInAir;
        if (ticksInAir > 500) {
            remove();
        }

        if (this.isInWater()) {
            double speed = Math.abs(Math.sqrt(this.xd * this.xd + this.zd * this.zd + this.yd * this.yd));
            if (speed < 0.05) {
                this.remove();
            }
        }
    }

    public void onHit(HitResult result) {
        if (this.tickCount > 5) {
            if (!this.world.isClientSide) {
                if (result.entity != null) {
                    if (!(result.entity instanceof Projectile)) {
                        MobUtil.knockback(result.entity, this, 4.0f, 0.0f);
                    }
                }
            }
        }
        this.remove();
    }

    public void remove() {
        this.world.playSoundAtEntity(null, this, "aether:mob.zephyr.shoot", 0.3F, 2.0F);
        for (int l = 0; l < 8; ++l) {
            double angle = Math.toRadians(l * 45);
            ParticleMaker.spawnParticle(world, "snowshovel", this.x, this.y + 0.5, this.z, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, 0);
            ParticleMaker.spawnParticle(world, "snowshovel", this.x, this.y + 0.5, this.z, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, 0);
            ParticleMaker.spawnParticle(world, "item", this.x, this.y + 0.5, this.z, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, AetherItems.AMMO_WINDBALL.id);
        }
        this.removed = true;
    }

    public boolean isPickable() {
        return true;
    }

    public float getPickRadius() {
        return 1.0F;
    }

    public boolean hurt(Entity entity, int i, DamageType type) {
        return false;
    }

    public void lerpMotion(double xd, double yd, double zd) {
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
    }

    public static Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag compoundTag) {
        ProjectileWindball windBall = new ProjectileWindball(world, x, y, z, xd, yd, zd);
        if (hasVelocity) windBall.setHeading(xd, yd, zd, 1, 0);
        if (owner instanceof Mob) windBall.owner = (Mob) owner;
        return windBall;
    }
}
