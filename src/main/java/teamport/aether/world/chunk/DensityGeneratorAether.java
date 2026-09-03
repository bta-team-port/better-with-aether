package teamport.aether.world.chunk;

import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.perlin.DensityGenerator;
import net.minecraft.core.world.noise.FractalNoise3D;
import net.minecraft.core.world.noise.ImprovedPerlinNoise;
import org.jspecify.annotations.NonNull;

public class DensityGeneratorAether implements DensityGenerator {
    private final World world;

    private final FractalNoise3D<ImprovedPerlinNoise> minLimitNoise;
    private final FractalNoise3D<ImprovedPerlinNoise> maxLimitNoise;
    private final FractalNoise3D<ImprovedPerlinNoise> mainNoise;
    private double[] mainNoiseBuffer;
    private double[] minLimitBuffer;
    private double[] maxLimitBuffer;

    public DensityGeneratorAether(@NonNull World world) {
        this.world = world;

        this.minLimitNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 16, 0));
        this.maxLimitNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 16, 16));
        this.mainNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 8, 16));
    }

    @Override
    public double @NonNull [] generateDensityMap(@NonNull Chunk chunk) {
        int terrainHeight = (this.world.getWorldType().getMaxY(this.world) + 1) - this.world.getWorldType().getMinY(this.world);

        int xSize = 5;
        int ySize = (terrainHeight / 8) + 1;
        int zSize = 5;
        int noiseSize = xSize * ySize * zSize;

        int x = chunk.pos.x * 4;
        int y = 0;
        int z = chunk.pos.z * 4;

        if (this.mainNoiseBuffer == null || this.mainNoiseBuffer.length != noiseSize) {
            this.mainNoiseBuffer = new double[noiseSize];
            this.minLimitBuffer = new double[noiseSize];
            this.maxLimitBuffer = new double[noiseSize];
        }

        double[] densityMapArray = new double[noiseSize];

        double mainNoiseScaleX = 80.0;
        double mainNoiseScaleY = 80.0;
        double mainNoiseScaleZ = 80.0;

        final double coordScale = 684.412 / 4.0;
        final double heightScale = 684.412 / 2.0;

        double upperLimitScale = 128.0;
        double lowerLimitScale = 128.0;

        // Generate noise arrays
        this.mainNoise.getRegion(this.mainNoiseBuffer, x, y, z, xSize, ySize, zSize, (coordScale / mainNoiseScaleX), (heightScale / mainNoiseScaleY), (coordScale / mainNoiseScaleZ));
        this.minLimitNoise.getRegion(this.minLimitBuffer, x, y, z, xSize, ySize, zSize, coordScale * 5.0, heightScale * 9.0, coordScale * 5.0);
        this.maxLimitNoise.getRegion(this.maxLimitBuffer, x, y, z, xSize, ySize, zSize, coordScale * 5.0, heightScale * 9.0, coordScale * 5.0);

        int mainIndex = 0;
        for (int dx = 0; dx < xSize; dx++) {
            for (int dz = 0; dz < zSize; dz++) {
                for (int dy = 0; dy < ySize; dy++) {

                    double minDensity = this.minLimitBuffer[mainIndex] / upperLimitScale;
                    double maxDensity = this.maxLimitBuffer[mainIndex] / lowerLimitScale;
                    double mainDensity = (this.mainNoiseBuffer[mainIndex] / 10.0 + 1.0) / 2.0;

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
                    double yFactor = (double) dy / (ySize - 1.0);
                    yFactor = Math.sin(yFactor * Math.PI);
                    density *= yFactor * 0.8 + 0.4; // Scale density: 1.0 at bottom, 0.5 at top

                    int upperLowerLimit = 6;
                    if (dy > ySize - upperLowerLimit) {
                        double densityMod = (double) (dy - (ySize - upperLowerLimit)) / (upperLowerLimit - 1.0);
                        density = density * (1.0 - densityMod) + (-30.0 * densityMod);
                    }

                    int bottomLowerLimit = 4;
                    if (dy < bottomLowerLimit) {
                        double densityMod = (double) (bottomLowerLimit - dy) / (bottomLowerLimit - 1.0);
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
