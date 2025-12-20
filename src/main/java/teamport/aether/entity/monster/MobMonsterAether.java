package teamport.aether.entity.monster;

import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;

public abstract class MobMonsterAether extends MobMonster implements Enemy {

    protected MobMonsterAether(@Nullable World world) {
        super(world);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 4;
    }

    @Override
    public boolean canSpawnHere() {
        int blockX = MathHelper.floor(this.x);
        int blockY = MathHelper.floor(this.bb.minY);
        int blockZ = MathHelper.floor(this.z);

        if (this.world == null || this.world.getSavedLightValue(LightLayer.Block, blockX, blockY, blockZ) > 7) {
            return false;
        } else if (this.world.getSavedLightValue(LightLayer.Sky, blockX, blockY, blockZ) > this.random.nextInt(32)) {
            return false;
        } else {
            int blockLight = this.world.getBlockLightValue(blockX, blockY, blockZ);
            if (this.world.getCurrentWeather() != null && this.world.getCurrentWeather().doMobsSpawnInDaylight) {
                blockLight /= 2;
            }

            return blockLight <= 4 && super.canSpawnHere();
        }
    }
}

