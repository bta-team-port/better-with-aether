package teamport.aether.world.feature.terrain;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class WorldFeatureAetherClouds extends WorldFeature {
    private final int numBlocks;
    private final int blockToPlace;

    public WorldFeatureAetherClouds(int blockToPlace, int numBlocks) {
        this.numBlocks = numBlocks;
        this.blockToPlace = blockToPlace;
    }

    @Override
    public boolean place(World world, @NonNull Random random, int x, int y, int z) {
        int xOffset = random.nextInt(3) - 1;
        int zOffset = random.nextInt(3) - 1;

        for (int block = 0; block < numBlocks; block++) {
            x += random.nextInt(3) - 1 + xOffset;
            z += random.nextInt(3) - 1 + zOffset;

            if (random.nextInt(10) == 0) {
                y += random.nextInt(3) - 1;
            }

            for (int x1 = x; x1 < x + random.nextInt(4) + 3; x1++) {
                for (int z1 = z; z1 < z + random.nextInt(4) + 3; z1++) {
                    for (int y1 = y; y1 < y + random.nextInt(1) + 2; y1++) {
                        if (Math.abs(x1 - x) + Math.abs(y1 - y) + Math.abs(z1 - z) < 4 + random.nextInt(2) && world.getBlockId(x1, y1, z1) == 0) {
                            world.setBlock(x1, y1, z1, blockToPlace);
                        }
                    }
                }
            }
        }

        return true;
    }
}
