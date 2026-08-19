package teamport.aether.entity.monster;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobPathfinder;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.player.PlayerUtil;

public abstract class MobMonsterAether extends MobPathfinder implements Enemy {
    protected int attackStrength = 2;

    public MobMonsterAether(@NonNull World world) {
        super(world);
    }

    @Override
    public int getMaxHealth() {
        return 20;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isClientSide && !this.world.getDifficulty().canHostileMobsSpawn()) {
            this.remove();
        }

    }

    @Override
    protected Entity findPlayerToAttack() {
        Player entityplayer = PlayerUtil.getClosestNonInvisPlayerToEntity(this.world, this, 16.0F);
        return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().hasHostileMobs() ? entityplayer : null;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 4;
    }

    @Override
    public boolean hurt(Entity attacker, int i, DamageType type) {
        if (super.hurt(attacker, i, type)) {
            if (this.passenger != attacker && this.vehicle != attacker && attacker != this) {
                if (attacker instanceof Player player && !player.gamemode.hasHostileMobs()) {
                    return true;
                }
                this.target = attacker;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void attackEntity(@NonNull Entity entity, float distance) {
        if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
            this.attackTime = 20;
            entity.hurt(this, this.attackStrength, DamageType.COMBAT);
        }

    }

    @Override
    protected float getBlockPathWeight(@NonNull TilePosc blockPos) {
        return !this.world.isBlockLoaded(blockPos) ? 0.0F : 0.5F - this.world.getLightBrightness(blockPos);
    }

    @Override
    public boolean canSpawnHere() {
        TilePos blockPos = new TilePos(this.x, this.bb.minY, this.z);
        if (this.world.getSavedLightValue(LightLayer.Block, blockPos) > 7) {
            return false;
        } else if (this.world.getSavedLightValue(LightLayer.Sky, blockPos) > this.random.nextInt(32)) {
            return false;
        } else {
            int blockLight = this.world.getBlockLightValue(blockPos);
            if (this.world.getCurrentWeather() != null && this.world.getCurrentWeather().isMobDaylightSpawnAllowed()) {
                blockLight /= 2;
            }

            return blockLight <= 4 && super.canSpawnHere();
        }
    }
}

