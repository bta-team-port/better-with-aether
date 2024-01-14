package bta.aether.world.generate.chunk.perlin.aether;

import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.perlin.DensityGenerator;
import net.minecraft.core.world.noise.PerlinNoise;

public class DensityGeneratorAether implements DensityGenerator {
    private final World world;

    private final PerlinNoise minLimitNoise;

    private final PerlinNoise maxLimitNoise;

    private final PerlinNoise mainNoise;

    private final PerlinNoise scaleNoise;

    private final PerlinNoise depthNoise;

    public DensityGeneratorAether(World world) {
        this.world = world;
        this.minLimitNoise = new PerlinNoise(world.getRandomSeed(), 16, 0);
        this.maxLimitNoise = new PerlinNoise(world.getRandomSeed(), 16, 16);
        this.mainNoise = new PerlinNoise(world.getRandomSeed(), 8, 32);
        this.scaleNoise = new PerlinNoise(world.getRandomSeed(), 10, 48);
        this.depthNoise = new PerlinNoise(world.getRandomSeed(), 16, 58);
    }

    public double[] generateDensityMap(Chunk chunk) {
        int terrainHeight = this.world.getWorldType().getMaxY() + 1 - this.world.getWorldType().getMinY();
        int xSize = 5;
        int ySize = terrainHeight / 8 + 1;
        int zSize = 5;
        int x = chunk.xPosition * 4;
        int y = 0;
        int z = chunk.zPosition * 4;
        double[] densityMapArray = new double[xSize * ySize * zSize];
        double mainNoiseScaleX = 80.0D;
        double mainNoiseScaleY = 120.0D;
        double mainNoiseScaleZ = 80.0D;
        double scaleNoiseScaleX = 1.121D;
        double scaleNoiseScaleZ = 1.121D;
        double depthNoiseScaleX = 200.0D;
        double depthNoiseScaleZ = 200.0D;
        double depthBaseSize = terrainHeight / 16.0D + 0.5D;
        double coordScale = 171.103D;
        double heightScale = 342.206D;
        double heightStretch = 4.0D;
        double upperLimitScale = 512.0D;
        double lowerLimitScale = 512.0D;
        double[] scaleArray = this.scaleNoise.get(null, x, z, xSize, zSize, 1.121D, 1.121D);
        double[] depthArray = this.depthNoise.get(null, x, z, xSize, zSize, 200.0D, 200.0D);
        double[] mainNoiseArray = this.mainNoise.get(null, x, y, z, xSize, ySize, zSize, 2.1387875000000003D, 2.851716666666667D, 2.1387875000000003D);
        double[] minLimitArray = this.minLimitNoise.get(null, x, y, z, xSize, ySize, zSize, 171.103D, 342.206D, 171.103D);
        double[] maxLimitArray = this.maxLimitNoise.get(null, x, y, z, xSize, ySize, zSize, 171.103D, 342.206D, 171.103D);
        int mainIndex = 0;
        int depthScaleIndex = 0;
        int xSizeScale = 16 / xSize;
        for (int dx = 0; dx < xSize; dx++) {
            int ix = dx * xSizeScale + xSizeScale / 2;
            for (int dz = 0; dz < zSize; dz++) {
                int iz = dz * xSizeScale + xSizeScale / 2;
                double temperature = chunk.temperature[ix * 16 + iz];
                double humidity = chunk.humidity[ix * 16 + iz] * temperature;
                humidity = 1.0D - humidity;
                humidity *= humidity;
                humidity *= humidity;
                humidity = 1.0D - humidity;
                double scale = (scaleArray[depthScaleIndex] + 256.0D) / 512.0D;
                scale *= humidity;
                if (scale > 1.0D)
                    scale = 1.0D;
                double depth = depthArray[depthScaleIndex] / 8000.0D;
                if (depth < 0.0D)
                    depth = -depth * 0.3D;
                depth = depth * 3.0D - 2.0D;
                if (depth > 1.0D)
                    depth = 1.0D;
                depth /= 8.0D;
                depth = 0.0D;
                if (scale < 0.0D)
                    scale = 0.0D;
                scale += 0.5D;
                depth = depth * depthBaseSize * 2.0D / 16.0D;
                double offsetY = ySize / 2.0D;
                depthScaleIndex++;
                for (int dy = 0; dy < ySize; dy++) {
                    double density = 0.0D;
                    double densityOffset = (dy - offsetY) * 4.0D / scale;
                    if (densityOffset < 0.0D)
                        densityOffset *= -1.0D;
                    double minDensity = minLimitArray[mainIndex] / 512.0D;
                    double maxDensity = maxLimitArray[mainIndex] / 512.0D;
                    double mainDensity = (mainNoiseArray[mainIndex] / 10.0D + 1.0D) / 2.0D;
                    if (mainDensity < 0.0D) {
                        density = minDensity;
                    } else if (mainDensity > 1.0D) {
                        density = maxDensity;
                    } else {
                        density = minDensity + (maxDensity - minDensity) * mainDensity;
                    }
                    density -= 8.0D;
                    int upperLowerLimit = 8;
                    if (dy > ySize - upperLowerLimit) {
                        double densityMod = ((dy - ySize - upperLowerLimit) / (upperLowerLimit - 1.0F));
                        density = density * (1.0D - densityMod) + -30.0D * densityMod;
                    }
                    upperLowerLimit = 2;
                    if (dy < upperLowerLimit) {
                        double densityMod = ((upperLowerLimit - dy) / (upperLowerLimit - 1.0F));
                        density = density * (1.0D - densityMod) + -30.0D * densityMod;
                    }
                    densityMapArray[mainIndex] = density;
                    mainIndex++;
                }
            }
        }
        return densityMapArray;
    }
}
