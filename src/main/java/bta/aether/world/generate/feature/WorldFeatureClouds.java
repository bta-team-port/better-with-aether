package bta.aether.world.generate.feature;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureClouds extends WorldFeature {
    private final int cloudBlockId;
    private final int numberOfBlocks;
    private final boolean flat;

    public WorldFeatureClouds(int cloudBlockId, int numberOfBlocks, boolean flat) {
        this.cloudBlockId = cloudBlockId;

        this.numberOfBlocks = numberOfBlocks;
        this.flat = flat;
    }
    @Override
    public boolean generate(World world, Random random, int xOrigin, int yOrigin, int zOrigin) {
        int x = xOrigin;
        int y = yOrigin;
        int z = zOrigin;
        int xTendency = random.nextInt(3) - 1;
        int zTendency = random.nextInt(3) - 1;
        for(int n = 0; n < this.numberOfBlocks; ++n) {
            x += random.nextInt(3) - 1 + xTendency;
            if (random.nextBoolean() && !this.flat || this.flat && random.nextInt(10) == 0) {
                y += random.nextInt(3) - 1;
            }
            z += random.nextInt(3) - 1 + zTendency;
            for(int x1 = x; x1 < x + random.nextInt(4) + 3 * (this.flat ? 3 : 1); ++x1) {
                for(int y1 = y; y1 < y + random.nextInt(1) + 2; ++y1) {
                    for(int z1 = z; z1 < z + random.nextInt(4) + 3 * (this.flat ? 3 : 1); ++z1) {
                        if (world.getBlockId(x1, y1, z1) == 0 && Math.abs(x1 - x) + Math.abs(y1 - y) + Math.abs(z1 - z) < 4 * (this.flat ? 3 : 1) + random.nextInt(2)) {
                            world.setBlock(x1, y1, z1, this.cloudBlockId);
                        }
                    }
                }
            }
        }
        return true;
    }
}
