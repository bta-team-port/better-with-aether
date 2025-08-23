package teamport.aether.entity.projectile;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;

public class ProjectileElementIce extends ProjectileElementBase implements AetherProjectileDeathMessages<ProjectileElementIce> {
    public boolean hasBeenHitByPlayer = false;

    public ProjectileElementIce(World world) {
        super(world);
    }

    public ProjectileElementIce(World world, Mob owner) {
        super(world, owner);
        this.initProjectile();
        this.initialSpeed = 0.25F;
    }

    @Override
    public boolean hurt(Entity entity, int damage, DamageType type) {
        if (!this.world.isClientSide) {
            if (entity instanceof Player) {
                Vec3 lookAngle = entity.getLookAngle();
                this.setHeading(lookAngle.x, lookAngle.y, lookAngle.z, 2.0f, 0.0F);
                hasBeenHitByPlayer = true;
            }
        }

        return super.hurt(entity, damage, type);
    }

    @Override
    public void tick() {
        for (int j = 0; j < 2; j++) {
            if (random.nextInt(5) == 0) {
                world.spawnParticle("snowflake", this.x, this.y + 0.5, this.z, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0);
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

            world.spawnParticle("block", XParticle, YParticle, ZParticle, 0, 0, 0, Blocks.PERMAICE.id());
            world.spawnParticle("snowshovel", XParticle, YParticle, ZParticle, 0, 0, 0, 0);

        }

        world.playBlockSoundEffect(null, x, y, z, Blocks.ICE, EnumBlockSoundEffectType.MINE);
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (!this.world.isClientSide) {
            if (!(hitResult.entity instanceof ProjectileElementBase)) {
                if (hitResult.entity instanceof MobBossSunspirit) {
                    if (hasBeenHitByPlayer) {
                        hitResult.entity.hurt(this, this.damage, DamageType.GENERIC);
                        this.remove();
                    } else {
                        super.onHit(hitResult);
                    }

                } else if (hitResult.entity instanceof Mob) {
                    hitResult.entity.hurt(this, this.damage, DamageType.GENERIC);
                    this.remove();
                }
            }
        }

        super.onHit(hitResult);
    }

}
