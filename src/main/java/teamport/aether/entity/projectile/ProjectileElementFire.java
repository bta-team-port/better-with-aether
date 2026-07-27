package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.helper.ParticleMaker;

public class ProjectileElementFire extends ProjectileElementBase implements AetherProjectileDeathMessages {
    private static final String[] PARTICLES = {"explode", "flame", "lava"};

    public static Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner) {
        return getEntity(ProjectileElementFire.class, world, x, y, z, meta, hasVelocity, xd, yd, zd, owner);
    }

    @SuppressWarnings("unused")
    public ProjectileElementFire(World world) {
        super(world);
        this.initProjectile();
        this.projectileSpeed = 0.25f;
    }

    public ProjectileElementFire(World world, Mob owner) {
        super(world, owner);
        this.initProjectile();
        this.projectileSpeed = 0.25f;
    }

    @Override
    public void tick() {
        if (this.world == null) return;
        ParticleMaker.spawnParticle(world, "flame", this.x, this.y + 0.5, this.z, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0);

        super.tick();
    }

    @Override
    public void doExplosion() {
        doExplosionHelper(world, this, PARTICLES, "mob.ghast.fireball", null, SoundCategory.WORLD_SOUNDS, 0.25F);
    }

    @Override
    public void onHit(HitResult hitResult) {
        Entity hitEntity = hitResult instanceof HitResult.Entity ? ((HitResult.Entity) hitResult).entity : null;
        if (this.world != null && !this.world.isClientSide && !(hitEntity instanceof MobBossSunspirit || hitEntity instanceof ProjectileElementBase || hitEntity instanceof MobFireMinion) && hitEntity instanceof Mob) {
            hitEntity.hurt(this.owner, this.damage, DamageType.FIRE);
            hitEntity.maxFireTicks = 200;
            hitEntity.remainingFireTicks = 200;
            this.remove();
            return;
        }
        super.onHit(hitResult);
    }

}
