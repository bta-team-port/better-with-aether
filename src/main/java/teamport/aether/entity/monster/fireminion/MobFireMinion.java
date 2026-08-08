package teamport.aether.entity.monster.fireminion;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.LightIndexHelper;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.MobUtil;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.helper.ParticleMaker;

import static teamport.aether.entity.DamageInstance.inst;

public class MobFireMinion extends MobMonsterAether implements Enemy, AetherDeathMessage {

    public MobFireMinion(@NonNull World world) {
        super(world);
        this.setTextureIdentifier("aether", "fire_minion");
        this.moveSpeed = 4.0F;
        this.attackStrength = 10;
        this.fireImmune = true;
        this.maxFireTicks = 20;
        this.scoreValue = 5000;
        setSize(1.0f, 2.0f);
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
    public byte getLightIndex(float partialTick) {
        byte light = super.getLightIndex(partialTick);
        light = LightIndexHelper.setSkyLight(light, 15);
        return LightIndexHelper.setBlockLight(light, 15);
    }

    @Override
    public int getMaxHealth() {
        return 40;
    }

    @Override
    protected void attackEntity(@NonNull Entity target, float distance) {
        if (this.attackTime <= 0 && distance < 2.0F && target.bb.maxY > this.bb.minY && target.bb.minY < this.bb.maxY) {
            this.attackTime = 20;
            MobUtil.multiHit(this, target,
                inst(this.attackStrength, DamageType.FIRE),
                inst(this.attackStrength / 2, DamageType.COMBAT)
            );
            target.remainingFireTicks = 300;
            target.maxFireTicks = 300;
        }

    }

    @Override
    protected Entity findPlayerToAttack() {
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
        return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().hasHostileMobs() ? entityplayer : null;
    }

    @Override
    public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (type == DamageType.FIRE) {
            return false;
        }
        return super.hurt(attacker, damage, type);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getHealth() > 0) {
            this.maxFireTicks = 20;
            ParticleMaker.spawnWhirlyParticles(world, this, 4, "fire");
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
        this.world.playSoundAtEntity(null, this, this.getHurtSound(), 0.5f, (this.random.nextFloat() + this.random.nextFloat()) * 1.5F + 0.25F);
    }

    @Override
    public void playDeathSound() {
        this.world.playSoundAtEntity(null, this, this.getDeathSound(), 0.5f, (this.random.nextFloat() + this.random.nextFloat()) * 1.5F + 0.25F);
    }

    @Override
    public boolean canSpawnHere() {
        return this.world.getDifficulty().canHostileMobsSpawn() && this.world.checkIfAABBIsClear(this.bb) && this.world.getCubes(this, this.bb).isEmpty();
    }
}
