package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.helper.ParticleMaker;

public class ProjectileElementIce extends ProjectileElementBase implements AetherProjectileDeathMessages {
    private static final String[] PARTICLES = {"block", "snowshovel"};

    public static Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner) {
        return getEntity(ProjectileElementIce.class, world, x, y, z, meta, hasVelocity, xd, yd, zd, owner);
    }

    @SuppressWarnings("unused")
    public ProjectileElementIce(World world) {
        super(world);
        this.initProjectile();
    }

    public ProjectileElementIce(World world, Mob owner) {
        super(world, owner);
        this.initProjectile();
    }

    @Override
    public void tick() {
        if (this.world == null) return;
        for (int j = 0; j < 2; j++) {
            if (random.nextInt(5) == 0) {
                ParticleMaker.spawnParticle(world, "snowflake", this.x, this.y + 0.5, this.z, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0);
            }
        }

        super.tick();
    }

    @Override
    public void bounceSound() {
        if (this.world != null) this.world.playSoundAtEntity(null, this, "step.permafrost", 2.0F, 1.0F);
    }

    @Override
    public void doExplosion() {
        doExplosionHelper(world, this, PARTICLES, null, null, null, 0.25F);
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (this.world != null && !this.world.isClientSide
            && hitResult.entity != null
            && !(hitResult.entity instanceof ProjectileElementBase)
        ) {
            if (hitResult.entity instanceof MobBossSunspirit) {
                if (this.owner instanceof Player) {
                    // The sunspirit only takes damage from ice projectiles, so, we set this here directly.
                    // This is jank btw. I know.
                    hitResult.entity.hurt(this, this.damage, DamageType.GENERIC);

                    doExplosion();
                    this.remove();
                    return;
                }

                super.onHit(hitResult);
                return;
            } else if (hitResult.entity instanceof MobFireMinion) {
                if (this.owner instanceof Player) {
                    hitResult.entity.hurt(this, 100, DamageType.GENERIC);

                    doExplosion();
                    this.remove();
                    return;
                }

                super.onHit(hitResult);
                return;
            } else if (hitResult.entity instanceof Mob) {
                hitResult.entity.hurt(this.owner, this.damage, DamageType.GENERIC);
                this.remove();

                return;
            }
        }

        super.onHit(hitResult);
    }

    @Override
    public boolean hurt(Entity entity, int damage, DamageType type) {
        this.markHurt();
        if (entity != null) {
            if (entity instanceof Player) {
                this.owner = (Player) entity;
            }

            Vec3 lookAngle = entity.getLookAngle();
            if (lookAngle != null) {
                this.setHeading(lookAngle.x, lookAngle.y, lookAngle.z, 0.5f, 0.0F);
                bounceCount = 18;
            }

            return true;
        } else {
            return false;
        }
    }

}
