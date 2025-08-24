package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.monster.fireminion.MobFireMinion;

public class ProjectileElementFire extends ProjectileElementBase implements AetherProjectileDeathMessages<ProjectileElementFire> {

    public String[] particles = {"explode", "flame", "lava"};

    public ProjectileElementFire(World world) {
        super(world);
        this.initProjectile();
    }

    public ProjectileElementFire(World world, Mob owner) {
        super(world, owner);
        this.initProjectile();
    }

    @Override
    public void tick() {
        for (int j = 0; j < 1; j++) {
            world.spawnParticle("flame", this.x, this.y + 0.5, this.z, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0);
        }

        super.tick();
    }

    @Override
    public void doExplosion() {
        for (int particle = 0; particle < 16; particle++) {
            double XParticle = x + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double YParticle = y + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
            double ZParticle = z + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);

            world.spawnParticle(particles[world.rand.nextInt(particles.length)], XParticle, YParticle, ZParticle, 0, 0, 0, 0);
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

    public boolean hurt(Entity entity, int damage, DamageType type) {
        return false;
    }

}
