package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.helper.ParticleMaker;

public class ProjectileElementIce extends ProjectileElementBase implements AetherProjectileDeathMessages<ProjectileElementIce> {

    public static Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag compoundTag) {
        return getEntity(ProjectileElementIce.class, world, x, y, z, meta, hasVelocity, xd, yd, zd, owner, compoundTag);
    }

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
        for (int particle = 0; particle < 16; particle++) {
            double XParticle = x + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double YParticle = y + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double ZParticle = z + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);

            ParticleMaker.spawnParticle(world, "block", XParticle, YParticle, ZParticle, 0, 0, 0, Blocks.PERMAICE.id());
            ParticleMaker.spawnParticle(world, "snowshovel", XParticle, YParticle, ZParticle, 0, 0, 0, 0);

        }

        world.playBlockSoundEffect(null, x, y, z, Blocks.ICE, EnumBlockSoundEffectType.MINE);
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (!this.world.isClientSide
                && hitResult.entity != null
                && !(hitResult.entity instanceof ProjectileElementBase)
        ) {
            if (hitResult.entity instanceof MobBossSunspirit) {
                if (this.owner != null && this.owner instanceof Player) {
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
                if (this.owner != null && this.owner instanceof Player) {
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
        if (!this.world.isClientSide) {
            if (entity != null) {
                if (entity instanceof Player) {
                    this.owner = (Player) entity;
                }

                Vec3 lookAngle = entity.getLookAngle();
                if (lookAngle == null) {
                    this.owner = null;
                    return false;
                }

                this.setHeading(lookAngle.x, lookAngle.y, lookAngle.z, 0.5f, 0.0F);
                bounceCount = 18;

                return true;
            }
        }

        return false;
    }

}
