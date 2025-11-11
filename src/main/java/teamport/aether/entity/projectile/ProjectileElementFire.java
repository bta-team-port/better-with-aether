package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.helper.ParticleMaker;

public class ProjectileElementFire extends ProjectileElementBase implements AetherProjectileDeathMessages<ProjectileElementFire> {

    public String[] particles = {"explode", "flame", "lava"};

    public static Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag compoundTag) {
        return getEntity(ProjectileElementFire.class, world, x, y, z, meta, hasVelocity, xd, yd, zd, owner, compoundTag);
    }

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
        for (int j = 0; j < 1; j++) {
            ParticleMaker.spawnParticle(world, "flame", this.x, this.y + 0.5, this.z, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0);
        }

        super.tick();
    }

    @Override
    public void doExplosion() {
        for (int particle = 0; particle < 16; particle++) {
            double XParticle = x + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double YParticle = y + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double ZParticle = z + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);

            ParticleMaker.spawnParticle(world, particles[world.rand.nextInt(particles.length)], XParticle, YParticle, ZParticle, 0, 0, 0, 0);
        }

        world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x, y, z, "mob.ghast.fireball", 0.25F, (1.3F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (!this.world.isClientSide) {
            if (!(hitResult.entity instanceof MobBossSunspirit || hitResult.entity instanceof ProjectileElementBase || hitResult.entity instanceof MobFireMinion)) {
                if (hitResult.entity instanceof Mob) {
                    hitResult.entity.hurt(this.owner, this.damage, DamageType.FIRE);
                    hitResult.entity.maxFireTicks = 200;
                    hitResult.entity.remainingFireTicks = 200;
                    this.remove();
                    return;
                }
            }
        }

        super.onHit(hitResult);
    }

}
