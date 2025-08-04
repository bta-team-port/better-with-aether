package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

public class ProjectileWindball extends Projectile implements ProjectileAether {

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

    public ProjectileWindball(World world, double x, double y, double z) {
        super(world);
        this.setSize(1.0F, 1.0F);
        this.setPos(x, y, z);
        this.heightOffset = 0.0F;
    }

    public ProjectileWindball(World world) {
        super(world);
        this.setSize(1.0F, 1.0F);
        this.setPos(x, y, z);
        this.heightOffset = 0.0F;
    }

    public void setVelocity(double vX, double vY, double vZ) {
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
    }

    public void onHit(HitResult result) {

        if (!this.world.isClientSide) {
            if (result.entity != null) {
                result.entity.fling(xd * 4, yd * 0, zd * 4, 0.5F);
            }
        }
        this.remove();
    }

    public void afterTick() {
        super.afterTick();
    }

    public boolean isPickable() {
        return true;
    }

    public float getPickRadius() {
        return 1.0F;
    }

    public boolean hurt(Entity entity, int i, DamageType type) {
        this.markHurt();
        return false;
    }

    public void lerpMotion(double xd, double yd, double zd) {
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
    }

    public static Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag compoundTag) {
        ProjectileWindball windBall = new ProjectileWindball(world, x, y, z);
        if (hasVelocity) windBall.setHeading(xd, yd, zd, 1, 0);
        if (owner instanceof Mob) windBall.owner = (Mob) owner;
        return windBall;
    }
}
