package teamport.aether.entity.monster;

import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.jspecify.annotations.NonNull;

public abstract class MobMonsterAether extends MobMonster implements Enemy {

    protected MobMonsterAether(@NonNull World world) {
        super(world);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 4;
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

