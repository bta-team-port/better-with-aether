package bta.aether.entity.projectiles;

import net.minecraft.core.HitResult;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityProjectile;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

public class EntityZephyrSnowball extends EntityProjectile {
    public double accelX = 0.0;
    public double accelY = 0.0;
    public double accelZ = 0.0;

    public EntityZephyrSnowball(World world) {
        super(world);
        this.setSize(1.0F, 1.0F);
    }

    public EntityZephyrSnowball(World world, double x, double y, double z, double vX, double vY, double vZ) {
        super(world);
        this.setSize(1.0F, 1.0F);
        this.moveTo(x, y, z, this.yRot, this.xRot);
        this.setPos(x, y, z);
        this.xd = this.yd = this.zd = 0.0;
        this.setAccel(vX, vY, vZ);
    }

    public EntityZephyrSnowball(World world, EntityLiving entityliving, double vX, double vY, double vZ) {
        super(world);
        this.setSize(1.0F, 1.0F);
        this.moveTo(entityliving.x, entityliving.y, entityliving.z, entityliving.yRot, entityliving.xRot);
        this.setPos(this.x, this.y, this.z);
        this.heightOffset = 0.0F;
        this.xd = this.yd = this.zd = 0.0;
        vX += this.random.nextGaussian() * 0.4;
        vY += this.random.nextGaussian() * 0.4;
        vZ += this.random.nextGaussian() * 0.4;
        this.setAccel(vX, vY, vZ);
    }

    private void setAccel(double vX, double vY, double vZ) {
        double velocity = MathHelper.sqrt_double(vX * vX + vY * vY + vZ * vZ);
        if (velocity != 0.0) {
            this.accelX = vX / velocity * 0.1;
            this.accelY = vY / velocity * 0.1;
            this.accelZ = vZ / velocity * 0.1;
        } else {
            this.accelX = 0.0;
            this.accelY = 0.0;
            this.accelZ = 0.0;
        }

    }

    protected void init() {
        super.init();
        this.damage = 0;
        this.defaultGravity = 0.0F;
        this.defaultProjectileSpeed = 1.0F;
    }

    public void tick() {
        super.tick();
    }

    public void onHit(HitResult result) {
        if (this.tickCount > 5) {
            if (result.entity != null) {
                result.entity.push(this.xd * 1.75, 0, this.zd * 1.75);
                this.remove();
            }
            this.remove();
        }
    }

    public void afterTick() {
        super.afterTick();
        this.xd += this.accelX;
        this.yd += this.accelY;
        this.zd += this.accelZ;
        this.world.spawnParticle("snowballpoof", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
    }

    public void inGroundAction() {
        this.remove();
    }

    public boolean isPickable() {
        return true;
    }

    public float getPickRadius() {
        return 1.0F;
    }

    public void lerpMotion(double xd, double yd, double zd) {
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
    }
}
