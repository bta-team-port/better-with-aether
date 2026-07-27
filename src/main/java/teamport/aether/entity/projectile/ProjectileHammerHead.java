package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import teamport.aether.helper.ParticleMaker;

public class ProjectileHammerHead extends Projectile implements ProjectileAether, AetherProjectileDeathMessages {

    @SuppressWarnings("unused")
    public ProjectileHammerHead(World world) {
        super(world);
        initProjectile();
    }

    public ProjectileHammerHead(World world, Mob owner) {
        super(world, owner);
        initProjectile();
    }

    @Override
    public void initProjectile() {
        this.damage = 10;
        this.defaultGravity = 0.0F;
        this.defaultProjectileSpeed = 0.99F;
        this.setSize(2.0F, 2.0F);
    }

    public ProjectileHammerHead(World world, double x, double y, double z) {
        super(world);
        this.setPos(x, y, z);
    }

    @Override
    public void tick() {
        super.tick();
        if (ticksInAir > 200) {
            doEffect();
            remove();
        }
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (hitResult instanceof HitResult.Entity) {
            Entity hitEntity = ((HitResult.Entity) hitResult).entity;
            hitEntity.hurt(this.owner, this.damage, DamageType.COMBAT);
            doEffect();
            this.remove();
        }

        if (hitResult instanceof HitResult.Tile) {
            doEffect();
            this.remove();
        }

    }

    public void doEffect() {
        if (this.world == null) return;
        world.playSoundAtEntity(null, this, "random.explode", 0.5F, 0.5F / (this.world.rand.nextFloat() * 0.4F + 0.8F));
        for (int j = 0; j < 8; ++j) {
            ParticleMaker.spawnParticle(this.world, "explode", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
            ParticleMaker.spawnParticle(this.world, "smoke", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
            ParticleMaker.spawnParticle(this.world, "largesmoke", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
            ParticleMaker.spawnParticle(this.world, "flame", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
        }
    }

    @SuppressWarnings("unused")
    public static Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner) {
        ProjectileHammerHead hammer = new ProjectileHammerHead(world, x, y, z);
        if (hasVelocity) hammer.setHeading(xd, yd, zd, 1, 0);
        if (owner instanceof Mob) hammer.owner = (Mob) owner;
        return hammer;
    }
}
