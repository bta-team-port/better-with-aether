package teamport.aether.world.chunk;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import net.minecraft.core.world.generate.chunk.perlin.SurfaceGenerator;
import net.minecraft.core.world.noise.FractalNoise3D;
import net.minecraft.core.world.noise.ImprovedPerlinNoise;
import net.minecraft.core.world.pos.ChunkTilePos;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.world.biome.AetherBiomes;

import java.util.Random;

public class SurfaceGeneratorAether implements SurfaceGenerator {
    private final @NonNull World world;
    private final @NonNull FractalNoise3D<ImprovedPerlinNoise> soilNoise;
    private final double[] soilThicknessBuffer = new double[256];
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
        int chunkWorldX = chunkX * 16;
        int chunkWorldZ = chunkZ * 16;
        int worldFillBlock = this.world.getWorldType().getFillerBlockId();

        Random rand = new Random((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);
        double beachScale = 0.03125;

        double[] soilThicknessNoise = this.soilNoise.getRegion(
            this.soilThicknessBuffer,
            chunkWorldX,
            chunkWorldZ,
            0.0,
            16, 16, 1,
            beachScale * 2.0,
            beachScale * 2.0,
            beachScale * 2.0
        );

        ChunkTilePos biomeQueryPos = new ChunkTilePos();

        for (int z = 0; z < 16; ++z) {
            for (int x = 0; x < 16; ++x) {
                int noiseIndex = z + x * 16;
                int soilThickness = (int) (soilThicknessNoise[noiseIndex] / 3.0 + 3.0 + (rand.nextDouble() * 0.25));

                int currentLayerDepth = -1;
                short cachedTopBlock = -1;
                short cachedFillerBlock = -1;

                int worldX = chunkWorldX + x;
                int worldZ = chunkWorldZ + z;

                Biome lastBiome = null;
                Biome biome = null;
                int lastBiomeCellY = Integer.MIN_VALUE;

                for (int y = maxY; y >= minY; --y) {
                    int biomeCellY = y >> 3;
                    if (biomeCellY != lastBiomeCellY) {
                        lastBiomeCellY = biomeCellY;
                        biome = chunk.getBlockBiome(biomeQueryPos.set(x, y, z));
                        if (biome == null) {
                            biome = this.world.getBiomeProvider().getBiome(worldX, biomeCellY, worldZ);
                        }
                    }

                    if (biome != lastBiome) {
                        cachedTopBlock = (short) biome.getSurfaceProperties().getTopBlock().id();
                        cachedFillerBlock = (short) biome.getSurfaceProperties().getFillerBlock().id();
                        lastBiome = biome;
                    }

                    int block = result.getBlock(x, y, z);

                    if (block == Blocks.AIR.id()) {
                        currentLayerDepth = -1;
                        continue;
                    }

                    if (block != worldFillBlock) continue;

                    if (currentLayerDepth == -1) {
                        currentLayerDepth = soilThickness;
                        result.setBlock(x, y, z, cachedTopBlock);
                    } else if (currentLayerDepth > 0) {
                        --currentLayerDepth;
                        result.setBlock(x, y, z, cachedFillerBlock);
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
