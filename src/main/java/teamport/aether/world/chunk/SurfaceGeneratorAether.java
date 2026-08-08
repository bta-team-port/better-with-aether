package teamport.aether.world.chunk;

import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.world.biome.BiomeAether;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import net.minecraft.core.world.generate.chunk.perlin.SurfaceGenerator;
import net.minecraft.core.world.noise.FractalNoise3D;
import net.minecraft.core.world.noise.ImprovedPerlinNoise;
import net.minecraft.core.world.noise.Noise3D;

import java.util.Random;

public class SurfaceGeneratorAether implements SurfaceGenerator {
    private final World world;
    private final Noise3D soilNoise;

    public SurfaceGeneratorAether(World world, Noise3D soilNoise) {
        this.world = world;
        this.soilNoise = soilNoise;
    }

    public SurfaceGeneratorAether(World world) {
        this(world, new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 4, 44)));
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
                int soilThickness = (int)(soilThicknessNoise[z + x * 16] / 3.0 + 3.0 + (rand.nextInt(2500) / 10000.0));
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

                    if ((biome != lastBiome
                        || topBlock == -1
                        || fillerBlock == -1
                    )
                        && block == 0
                    ) {
                        if (biome instanceof BiomeAether) {
                            topBlock = AetherBlocks.GRASS_AETHER.id();
                            fillerBlock = AetherBlocks.DIRT_AETHER.id();
                        } else {
                            topBlock = biome.getSurfaceProperties().getTopBlock().id();
                            fillerBlock = biome.getSurfaceProperties().getFillerBlock().id();
                        }
                    }

                    lastBiome = biome;

                    if (block == 0) {
                        currentLayerDepth = -1;
                        continue;
                    }

                    if (block != worldFillBlock) continue;

                    if (currentLayerDepth == -1) {
                        if (soilThickness <= 0) {
                            topBlock = 0;
                            fillerBlock = (short) worldFillBlock;
                        }

                        currentLayerDepth = soilThickness;

                        result.setBlock(x, y, z, topBlock);
                        continue;
                    }

                    if (currentLayerDepth > 0) {
                        --currentLayerDepth;
                        result.setBlock(x, y, z, fillerBlock);
                    }
                }
            }
        }
    }
}
