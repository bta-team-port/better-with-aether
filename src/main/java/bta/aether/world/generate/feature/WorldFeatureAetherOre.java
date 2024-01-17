package bta.aether.world.generate.feature;

import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Map;
import java.util.Random;

public class WorldFeatureAetherOre extends WorldFeature {
    private final int numberOfBlocks;
    private final Map<Integer, Integer> blockMap;

    public WorldFeatureAetherOre(int numberOfBlocks, Map<Integer, Integer> blockMap) {
        this.numberOfBlocks = numberOfBlocks;
        this.blockMap = blockMap;
    }

    @Override
    public boolean generate(World world, Random random, int xStart, int yStart, int zStart) {
        float f = random.nextFloat() * 3.141593f;
        double xMax = (float)(xStart + 8) + MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0f;
        double xMin = (float)(xStart + 8) - MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0f;
        double zMax = (float)(zStart + 8) + MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0f;
        double zMin = (float)(zStart + 8) - MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0f;
        double yMax = yStart + random.nextInt(3) + 2;
        double yMin = yStart - random.nextInt(3) + 2;
        for (int l = 0; l <= this.numberOfBlocks; ++l) {
            double d6 = xMax + (xMin - xMax) * (double)l / (double)this.numberOfBlocks;
            double d7 = yMax + (yMin - yMax) * (double)l / (double)this.numberOfBlocks;
            double d8 = zMax + (zMin - zMax) * (double)l / (double)this.numberOfBlocks;
            double d9 = random.nextDouble() * (double)this.numberOfBlocks / 16.0;
            double d10 = (double)(MathHelper.sin((float)l * 3.141593f / (float)this.numberOfBlocks) + 1.0f) * d9 + 1.0;
            double d11 = (double)(MathHelper.sin((float)l * 3.141593f / (float)this.numberOfBlocks) + 1.0f) * d9 + 1.0;
            int xVeinStart = MathHelper.floor_double(d6 - d10 / 2.0);
            int yVeinStart = MathHelper.floor_double(d7 - d11 / 2.0);
            int zVeinStart = MathHelper.floor_double(d8 - d10 / 2.0);
            int xVeinEnd = MathHelper.floor_double(d6 + d10 / 2.0);
            int yVeinEnd = MathHelper.floor_double(d7 + d11 / 2.0);
            int zVeinEnd = MathHelper.floor_double(d8 + d10 / 2.0);
            for (int x = xVeinStart; x <= xVeinEnd; ++x) {
                double d12 = ((double)x + 0.5 - d6) / (d10 / 2.0);
                if (d12 * d12 >= 1.0) continue;
                for (int y = yVeinStart; y <= yVeinEnd; ++y) {
                    double d13 = ((double)y + 0.5 - d7) / (d11 / 2.0);
                    if (d12 * d12 + d13 * d13 >= 1.0) continue;
                    for (int z = zVeinStart; z <= zVeinEnd; ++z) {
                        double d14 = ((double)z + 0.5 - d8) / (d10 / 2.0);
                        if (!(d12 * d12 + d13 * d13 + d14 * d14 < 1.0)) continue;
                        int blockId = world.getBlockId(x, y, z);
                        if (!blockMap.containsKey(blockId)) continue;
                        world.setBlock(x, y, z, blockMap.get(blockId));
                    }
                }
            }
        }
        return true;
    }
}
