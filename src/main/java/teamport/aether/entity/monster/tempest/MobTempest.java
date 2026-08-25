package teamport.aether.entity.monster.tempest;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.AetherMod;
import teamport.aether.entity.projectile.AetherDeathMessage;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.entity.projectile.ProjectileElementLightning;
import teamport.aether.helper.ParticleMaker;

@SuppressWarnings("java:S110")
public class MobTempest extends MobMonsterAether implements Enemy {
    private int cooldown;
    private final int maxLifetime;

    public MobTempest(World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.setTextureIdentifier("aether", "tempest");
        this.maxLifetime = this.random.nextInt(1024) + 1024;
        this.scoreValue = 400;
        this.footSize = 1.0f;
        this.fireImmune = true;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getHealth() > 0) {
            ParticleMaker.spawnWhirlyParticles(world, this, 8, "tempest");
            ParticleMaker.spawnParticle(world, "lightning", this.x, this.y + world.rand.nextDouble(), this.z,
                world.rand.nextDouble() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), world.rand.nextDouble() * 0.2F, world.rand.nextDouble() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0, 72);
        }
    }

    @Override
    public void attackEntity(@NonNull Entity entity, float distance) {
        if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
            this.attackTime = 20;
            world.playSoundEffect(null, SoundCategory.ENTITY_SOUNDS, entity.x, entity.y, entity.z, "aether:zap", 0.5F, (1.3F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
            entity.hurt(this, this.attackStrength, AetherMod.LIGHTNING);
        }
    }

    @Override
    @SuppressWarnings("java:S1192")
    public void updateAI() {
        super.updateAI();
        if (this.isInWaterOrRain() || (this.entityAge >= this.maxLifetime && !this.hadNicknameSet)) {
            for (int l = 0; l < 16; ++l) {
                double angle = Math.toRadians(l * 45.0);
                ParticleMaker.spawnParticle(world, "largesmoke", x, y, z, -Math.cos(angle) / 15.0, 0.03, -Math.sin(angle) / 15.0, 0);
            }
            world.playSoundAtEntity(null, this, "random.whoose.out", 0.3F, 1.0F / (random.nextFloat() * -0.2F - 0.4F));
            this.remove();
        }

        if (this.target != null) {
            ++this.cooldown;
        }

        if (this.cooldown >= 64 && this.target != null) {
            ProjectileElementLightning elementLightning = new ProjectileElementLightning(this.world, this);
            elementLightning.setHeading(world.rand.nextDouble(), this.getViewVector(1.0F).y() + 5, world.rand.nextDouble(), 0.5f, 0.0f);
            this.world.playSoundAtEntity(null, this, "mob.ghast.fireball", this.getSoundVolume(), (this.random.nextFloat() + this.random.nextFloat()) * 1.2F + 1.0F);
            this.world.entityJoinedWorld(elementLightning);
            this.cooldown = 0;
        }
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (type == AetherMod.LIGHTNING) {
            return false;
        }
        return super.hurt(attacker, damage, type);
    }

    @Override
    public void causeFallDamage(float distance) {/* dont take fall damage*/}

    @Override
    @SuppressWarnings("java:S131")
    public boolean collidesWith(Entity entity) {
        float launchSpeed = 0.75F;
        if (!(entity instanceof MobTempest)) {
            float launchHeightSpeed = launchSpeed / 2.0f;
            entity.fling(world.rand.nextGaussian(), launchHeightSpeed, world.rand.nextGaussian(), 0);
            return false;
        }
        return true;
    }

    @Override
    public String getHurtSound() {
        return "random.whoose.out";
    }

    @Override
    public String getDeathSound() {
        return "random.whoose.in";
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public boolean makeStepSound() {
        return false;
    }

    @Override
    public boolean canClimb() {
        return false;
    }

    @Override
    protected void jump() {/* looks weird if it jumps */}
}
