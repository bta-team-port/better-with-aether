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

    public DensityGeneratorAether(World world)
    {
        this.world = world;

        minLimitNoise = new PerlinNoise(world.getRandomSeed(), 16, 0);
        maxLimitNoise = new PerlinNoise(world.getRandomSeed(), 16, 16);
        mainNoise = new PerlinNoise(world.getRandomSeed(), 8, 32);
        scaleNoise = new PerlinNoise(world.getRandomSeed(), 10, 48);
        depthNoise = new PerlinNoise(world.getRandomSeed(), 16, 58);
    }

    @Override
    public double[] generateDensityMap(Chunk chunk)
    {
        int terrainHeight = (world.getWorldType().getMaxY() + 1) - world.getWorldType().getMinY();

        int xSize = 4 + 1;
        int ySize = (terrainHeight / 8) + 1;
        int zSize = 4 + 1;
        int x = chunk.xPosition * 4;
        int y = 0;
        int z = chunk.zPosition * 4;

        double[] densityMapArray = new double[xSize * ySize * zSize];

        final double mainNoiseScaleX = 80;
        final double mainNoiseScaleY = 120;
        final double mainNoiseScaleZ = 80;

        final double coordScale = 684.412D / 4;
        final double heightScale = 684.412D / 2;

        final double upperLimitScale = 128;
        final double lowerLimitScale = 128;

        // uses temp and humidity to alter terrain shape.
        double[] mainNoiseArray = mainNoise.get(null, x, y, z, xSize, ySize, zSize, (coordScale / mainNoiseScaleX), (heightScale / mainNoiseScaleY), (coordScale / mainNoiseScaleZ));
        double[] minLimitArray = minLimitNoise.get(null, x, y, z, xSize, ySize, zSize, coordScale * 5, heightScale * 9, coordScale * 5);
        double[] maxLimitArray = maxLimitNoise.get(null, x, y, z, xSize, ySize, zSize, coordScale * 5, heightScale * 9, coordScale * 5);
        int mainIndex = 0;
        for(int dx = 0; dx < xSize; dx++)
        {
            for(int dz = 0; dz < zSize; dz++)
            {

                for(int dy = 0; dy < ySize; dy++)
                {
                    double density;
                    double minDensity = minLimitArray[mainIndex] / upperLimitScale;
                    double maxDensity = maxLimitArray[mainIndex] / lowerLimitScale;
                    double mainDensity = (mainNoiseArray[mainIndex] / 10D + 1.0D);
                    if(mainDensity < 0.0D)
                    {
                        density = minDensity;
                    }
                    else if(mainDensity > 1.0D)
                    {
                        density = maxDensity;
                    }
                    else
                    {
                        density = minDensity + (maxDensity - minDensity) * mainDensity;
                    }
                    density -= 16D;

                    int upperLowerLimit = 50 + 20;
                    // Upper cutoff
                    if(dy > ySize - upperLowerLimit)
                    {
                        double densityMod = (float)(dy - (ySize - upperLowerLimit)) / ((float)upperLowerLimit - 1.0F);
                        density = density * (1.0D - densityMod) + -30D * densityMod;
                    }
                    upperLowerLimit = 20;
                    // Lower cutoff
                    if(dy < upperLowerLimit)
                    {
                        double densityMod = (float)(upperLowerLimit - dy) / ((float)upperLowerLimit - 1.0F);
                        density = density * (1.0D - densityMod) + -30D * densityMod;
                    }

                    densityMapArray[mainIndex] = density;
                    mainIndex++;
                }

            }
        }

        return densityMapArray;

    }
}
