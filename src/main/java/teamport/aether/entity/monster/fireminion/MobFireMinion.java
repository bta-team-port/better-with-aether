package teamport.aether.entity.monster.fireminion;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.helper.ParticleMaker;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class MobFireMinion extends MobMonsterAether implements Enemy, AetherDeathMessage {

    public MobFireMinion(@Nullable World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "fire_minion");
        this.moveSpeed = 4.0F;
        this.attackStrength = 10;
        this.fireImmune = true;
        this.maxFireTicks = 20;
        this.scoreValue = 5000;
        setSize(1.0f, 2.5f);
        this.canBreatheUnderwater();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public float getBrightness(float partialTick) {
        return 1.0F;
    }

    @Override
    public int getLightmapCoord(float partialTick) {
        return this.world == null ? super.getLightmapCoord(partialTick) : this.world.getLightmapCoord(15, 15);
    }

    @Override
    public int getMaxHealth() {
        return 40;
    }

    @Override
    protected void attackEntity(@NonNull Entity entity, float distance) {
        if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
            this.attackTime = 20;
            entity.hurt(this, this.attackStrength, DamageType.FIRE);
            entity.hurt(this, this.attackStrength / 2, DamageType.COMBAT);
            entity.remainingFireTicks = 300;
            entity.maxFireTicks = 300;
        }

    }

    @Override
    protected Entity findPlayerToAttack() {
        if (this.world == null) return super.findPlayerToAttack();
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
        return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile() ? entityplayer : null;
    }

    @Override
    public boolean hurt(Entity attacker, int i, DamageType type) {
        if (type == DamageType.FIRE) {
            return false;
        }
        return super.hurt(attacker, i, type);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getHealth() > 0) {
            this.maxFireTicks = 20;
            for (int j = 0; j < 4; ++j) {
                double a = this.random.nextFloat() - 0.5F;
                double b = this.random.nextFloat();
                double c = this.random.nextFloat() - 0.5F;
                double d = this.x + a * b;
                double e = this.bb.minY + b - 0.5;
                double f = this.z + c * b;
                if (!EnvironmentHelper.isServerEnvironment()) {
                    ParticleMaker.spawnParticle(world, "flame", d, e, f, 0.0, -0.07500000298023224, 0.0, 0);
                }
            }
        }
    }

    @Override
    public String getLivingSound() {
        return null;
    }

    @Override
    public String getHurtSound() {
        return "aether:mob.sunspirit.hurt";
    }

    @Override
    public String getDeathSound() {
        return "aether:mob.sunspirit.death";
    }

    @Override
    public void playHurtSound() {
        if (this.world == null) {
            super.playHurtSound();
            return;
        }
        this.world.playSoundAtEntity(null, this, this.getHurtSound(), 0.5f, (this.random.nextFloat() + this.random.nextFloat()) * 1.5F + 0.25F);
    }

    @Override
    public void playDeathSound() {
        if (this.world == null) {
            super.playDeathSound();
            return;
        }
        this.world.playSoundAtEntity(null, this, this.getDeathSound(), 0.5f, (this.random.nextFloat() + this.random.nextFloat()) * 1.5F + 0.25F);
    }
}
