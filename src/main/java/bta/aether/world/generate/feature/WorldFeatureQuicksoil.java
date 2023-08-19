package bta.aether.world.generate.feature;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureQuicksoil extends WorldFeature {
    private int blockId;

    public WorldFeatureQuicksoil(int blockId) {
        this.blockId = blockId;
    }

    @Override
    public boolean generate(World world, Random random, int xOrigin, int yOrigin, int zOrigin) {
        for(int x = xOrigin - 3; x < xOrigin + 4; ++x) {
            for(int z = zOrigin - 3; z < zOrigin + 4; ++z) {
                if (world.getBlockId(x, yOrigin, z) == 0 && (x - xOrigin) * (x - xOrigin) + (z - zOrigin) * (z - zOrigin) < 12) {
                    world.setBlock(x, yOrigin, z, this.blockId);
                }
            }
        }

        return true;
    }
}
