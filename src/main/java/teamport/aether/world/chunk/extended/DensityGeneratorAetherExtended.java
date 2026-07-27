package teamport.aether.world.chunk.extended;

import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.perlin.DensityGenerator;
import net.minecraft.core.world.noise.FractalNoise3D;
import net.minecraft.core.world.noise.ImprovedPerlinNoise;

public class DensityGeneratorAetherExtended implements DensityGenerator {
    private final World world;

    private final FractalNoise3D<ImprovedPerlinNoise> minLimitNoise;
    private final FractalNoise3D<ImprovedPerlinNoise> maxLimitNoise;
    private final FractalNoise3D<ImprovedPerlinNoise> mainNoise;

    public DensityGeneratorAetherExtended(World world) {
        this.world = world;

        minLimitNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 16, 0));
        maxLimitNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 16, 16));
        mainNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 8, 16));
    }

    @Override
    public double[] generateDensityMap(Chunk chunk) {
        int terrainHeight = (world.getWorldType().getMaxY(world) + 1) - world.getWorldType().getMinY(world);

        int xSize = 5;
        int ySize = (terrainHeight / 8) + 1;
        int zSize = 5;
        int x = chunk.pos.x * 4;
        int y = 0;
        int z = chunk.pos.z * 4;

        double[] densityMapArray = new double[xSize * ySize * zSize];

        double mainNoiseScaleX = 80.0;
        double mainNoiseScaleY = 100.0;
        double mainNoiseScaleZ = 80.0;

        final double coordScale = 684.412D / 4;
        final double heightScale = 684.412D / 2;

        double upperLimitScale = 128.0;
        double lowerLimitScale = 128.0;

        // Generate noise arrays
        int noiseSize = xSize * ySize * zSize;
        double[] mainNoiseArray = mainNoise.getRegion(new double[noiseSize], x, y, z, xSize, ySize, zSize, (coordScale / mainNoiseScaleX), (heightScale / mainNoiseScaleY), (coordScale / mainNoiseScaleZ));
        double[] minLimitArray = minLimitNoise.getRegion(new double[noiseSize], x, y, z, xSize, ySize, zSize, coordScale * 5, heightScale * 9, coordScale * 5);
        double[] maxLimitArray = maxLimitNoise.getRegion(new double[noiseSize], x, y, z, xSize, ySize, zSize, coordScale * 5, heightScale * 9, coordScale * 5);

        int mainIndex = 0;
        for (int dx = 0; dx < xSize; dx++) {
            for (int dz = 0; dz < zSize; dz++) {
                for (int dy = 0; dy < ySize; dy++) {
                    int absoluteY = world.getWorldType().getMinY(world) + (dy * 8);

                    double minDensity = minLimitArray[mainIndex] / upperLimitScale;
                    double maxDensity = maxLimitArray[mainIndex] / lowerLimitScale;
                    double mainDensity = (mainNoiseArray[mainIndex] / 10.0 + 1.0);

                    double density;
                    if (mainDensity < 0.0) {
                        density = minDensity;
                    } else if (mainDensity > 1.0) {
                        density = maxDensity;
                    } else {
                        density = minDensity + (maxDensity - minDensity) * mainDensity;
                    }
                    density -= 16.0;

                    // Modulate density based on Y level to make islands smaller and thinner higher up
                    // Higher Y reduces density, making islands sparser and smaller
                    double yFactor = 1.0 - ((double) (absoluteY - world.getWorldType().getMinY(world)) / terrainHeight);
                    yFactor = Math.max(0.0, Math.min(1.0, yFactor));
                    density *= yFactor * 0.8 + 0.4; // Scale density: 1.0 at bottom, 0.5 at top

                    int upperLowerLimit = 70;
                    if (dy > ySize - upperLowerLimit) {
                        double densityMod = (dy - (ySize - upperLowerLimit)) / (upperLowerLimit - 1.0F);
                        density = density * (1.0 - densityMod) + (-30.0 * densityMod);
                    }

                    upperLowerLimit = 15;
                    if (dy < upperLowerLimit) {
                        double densityMod = (upperLowerLimit - dy) / (upperLowerLimit - 1.0F);
                        density = density * (1.0 - densityMod) + (-30.0 * densityMod);
                    }

                    densityMapArray[mainIndex] = density;
                    mainIndex++;
                }
            }
        }
        return densityMapArray;
    }
}
