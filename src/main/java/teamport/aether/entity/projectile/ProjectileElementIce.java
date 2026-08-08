package teamport.aether.entity.projectile;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.BlockParticleHelper;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.joml.Vector3dc;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.helper.ParticleMaker;

public class ProjectileElementIce extends ProjectileElementBase implements AetherProjectileDeathMessages {
    private static final String[] PARTICLES = {"block", "snowshovel"};

    public static @NonNull Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner) {
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
        for (int j = 0; j < 2; j++) {
            if (random.nextInt(5) == 0) {
                ParticleMaker.spawnParticle(world, "snowflake", this.x, this.y + 0.5, this.z, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0);
            }
        }

        super.tick();
    }

    @Override
    public void bounceSound() {
        this.world.playSoundAtEntity(null, this, "step.permafrost", 2.0F, 1.0F);
    }

    @Override
    public void doExplosion() {
        int iceData = BlockParticleHelper.encodeBlockData(Blocks.ICE.id(), 0, Side.TOP);
        for (int i = 0; i < 16; i++) {
            double px = this.x + (world.rand.nextDouble()) - (world.rand.nextDouble() * 0.375);
            double py = this.y + 0.5 + (world.rand.nextDouble()) - (world.rand.nextDouble() * 0.375);
            double pz = this.z + (world.rand.nextDouble()) - (world.rand.nextDouble() * 0.375);
            String key = PARTICLES[world.rand.nextInt(PARTICLES.length)];
            int data = "block".equals(key) ? iceData : 0;
            ParticleMaker.spawnParticle(world, key, px, py, pz, 0, 0, 0, data);
        }
        world.playBlockSoundEffect(null, this.x, this.y, this.z, Blocks.ICE, net.minecraft.core.enums.EnumBlockSoundEffectType.MINE);
    }

    @Override
    public void onHit(@NonNull HitResult hitResult) {
        Entity hitEntity = hitResult instanceof HitResult.Entity entity ? entity.entity : null;
        if (!this.world.isClientSide && hitEntity != null && !(hitEntity instanceof ProjectileElementBase)
        ) {
            if (hitEntity instanceof MobBossSunspirit) {
                if (this.owner instanceof Player) {
                    // The sunspirit only takes damage from ice projectiles, so, we set this here directly.
                    // This is jank btw. I know.
                    hitEntity.hurt(this, this.damage, DamageType.GENERIC);

                    doExplosion();
                    this.remove();
                    return;
                }

                super.onHit(hitResult);
                return;
            } else if (hitEntity instanceof MobFireMinion) {
                if (this.owner instanceof Player) {
                    hitEntity.hurt(this, 100, DamageType.GENERIC);

                    doExplosion();
                    this.remove();
                    return;
                }

                super.onHit(hitResult);
                return;
            } else if (hitEntity instanceof Mob) {
                hitEntity.hurt(this.owner, this.damage, DamageType.GENERIC);
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
            if (entity instanceof Player player) {
                this.owner = player;
            }

            Vector3dc lookAngle = entity.getViewVector(1.0F);
            if (lookAngle != null) {
                this.setHeading(lookAngle.x(), lookAngle.y(), lookAngle.z(), 0.5f, 0.0F);
                bounceCount = 18;
            }

            return true;
        } else {
            return false;
        }
    }

}
