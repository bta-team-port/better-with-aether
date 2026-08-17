package teamport.aether.world.chunk;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import net.minecraft.core.world.generate.chunk.perlin.SurfaceGenerator;
import net.minecraft.core.world.noise.FractalNoise3D;
import net.minecraft.core.world.noise.ImprovedPerlinNoise;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.world.biome.AetherBiomes;

import java.util.Random;

public class SurfaceGeneratorAether implements SurfaceGenerator {
    private final @NonNull World world;
    private final @NonNull FractalNoise3D<ImprovedPerlinNoise> soilNoise;
    private final short cobbleHolystoneId;
    private final short holystoneId;

    public SurfaceGeneratorAether(@NonNull World world) {
        super();
        this.world = world;
        this.soilNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 4, 44));
        this.cobbleHolystoneId = (short) AetherBlocks.COBBLE_HOLYSTONE.id();
        this.holystoneId = (short) AetherBlocks.HOLYSTONE.id();
    }

    @Override
    public void generateSurface(@NonNull Chunk chunk, @NonNull ChunkGeneratorResult result) {
        int minY = this.world.getWorldType().getMinY(world);
        int maxY = this.world.getWorldType().getMaxY(world);

        int chunkX = chunk.pos.x;
        int chunkZ = chunk.pos.z;
        int worldFillBlock = this.world.getWorldType().getFillerBlockId();

        Random rand = new Random(chunkX * 341873128712L + chunkZ * 132897987541L);
        double beachScale = 0.03125;

        double[] soilThicknessNoise = this.soilNoise.getRegion(
            new double[16 * 16],
            chunkX * 16.0,
            chunkZ * 16.0,
            0.0,
            16, 16, 1,
            beachScale * 2.0,
            beachScale * 2.0,
            beachScale * 2.0
        );

        for (int z = 0; z < 16; ++z) {
            for (int x = 0; x < 16; ++x) {
                int soilThickness = (int) (soilThicknessNoise[z + x * 16] / 3.0 + 3.0 + (rand.nextDouble() * 0.25));
                int currentLayerDepth = -1;
                int topBlock = -1;
                int fillerBlock = -1;
                Biome lastBiome = null;

                for (int y = maxY; y >= minY; --y) {
                    Biome biome = chunk.getBlockBiome(x, y, z);

                    if (biome == null) {
                        biome = this.world.getBiomeProvider().getBiome(chunkX * 16 + x, y >> 3, chunkZ * 16 + z);
                    }

                    int block = result.getBlock(x, y, z);

                    if ((biome != lastBiome || topBlock == -1 || fillerBlock == -1) && block == Blocks.AIR.id()) {
                        topBlock = biome.getSurfaceProperties().getTopBlock().id();
                        fillerBlock = biome.getSurfaceProperties().getFillerBlock().id();
                    }

                    lastBiome = biome;

                    if (block == Blocks.AIR.id()) {
                        currentLayerDepth = -1;
                        continue;
                    }

                    if (block != worldFillBlock) continue;

                    if (currentLayerDepth == -1) {
                        currentLayerDepth = soilThickness;
                        result.setBlock(x, y, z, topBlock);
                        continue;
                    }

                    if (currentLayerDepth > 0) {
                        --currentLayerDepth;
                        result.setBlock(x, y, z, fillerBlock);
                    } else {
                        int stoneBlockId = this.getStoneBlockForBiome(biome, rand);
                        result.setBlock(x, y, z, stoneBlockId);
                    }
                }
            }
        }
    }

    private int getStoneBlockForBiome(Biome biome, Random rand) {
        if (biome == AetherBiomes.AETHER_PLAINS) {
            return rand.nextInt(2) == 0 ? holystoneId : cobbleHolystoneId;
        }


        return rand.nextInt(2) == 0 ? this.holystoneId : this.cobbleHolystoneId;
    }

}
