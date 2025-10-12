package teamport.aether.world.generate.chunk;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import net.minecraft.core.world.generate.chunk.perlin.SurfaceGenerator;
import net.minecraft.core.world.noise.BasePerlinNoise;
import net.minecraft.core.world.noise.PerlinNoise;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.blocks.AetherBlocks;

import java.util.Random;

public class SurfaceGeneratorAether implements SurfaceGenerator {
    public final World world;
    public final BasePerlinNoise<?> soilNoise;
    public final BasePerlinNoise<?> mainNoise;

    public final BasePerlinNoise<?> flowerNoise;

    public static final WeightedRandomBag<Integer> flowerBag = new WeightedRandomBag<>();

    static {
        flowerBag.addEntry(AetherBlocks.TALLGRASS_AETHER.id(), 75F);
        flowerBag.addEntry(AetherBlocks.FLOWER_PURPLE.id(), 12.5F);
        flowerBag.addEntry(AetherBlocks.FLOWER_WHITE.id(), 12.5F);
    }

    public SurfaceGeneratorAether(World world, BasePerlinNoise<?> soilNoise, BasePerlinNoise<?> mainNoise) {
        this.world = world;
        this.soilNoise = soilNoise;
        this.mainNoise = mainNoise;

        this.flowerNoise = new PerlinNoise(world.getRandomSeed(), 4, 44);
    }

    public SurfaceGeneratorAether(World world) {
        this(world, new PerlinNoise(world.getRandomSeed(), 4, 44), new PerlinNoise(world.getRandomSeed(), 8, 32));
    }

    @Override
    public void generateSurface(Chunk chunk, ChunkGeneratorResult result) {
        int minY = this.world.getWorldType().getMinY();
        int maxY = this.world.getWorldType().getMaxY();

        int chunkX = chunk.xPosition;
        int chunkZ = chunk.zPosition;
        int worldFillBlock = this.world.getWorldType().getFillerBlockId();

        Random rand = new Random((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);
        double beachScale = 0.03125;

        double[] soilThicknessNoise = this.soilNoise.get(
                null,
                chunkX * 16,
                chunkZ * 16,
                0.0,
                16, 16, 1,
                beachScale * 2.0,
                beachScale * 2.0,
                beachScale * 2.0
        );

        double[] flowerNoise = this.flowerNoise.get(
                null,
                chunkX * 16,
                chunkZ * 16,
                0.0,
                8, 8, 1,
                beachScale * 2.0,
                beachScale * 2.0,
                beachScale * 2.0
        );

        for (int z = 0; z < 16; ++z) {
            for (int x = 0; x < 16; ++x) {

                int flowerDensity = 9 - (int) Math.min(Math.abs(flowerNoise[z/2 + (x/2)*8] * 8), 8);

                int soilThickness = (int) (soilThicknessNoise[z + x * 16] / 3.0 + 3.0 + rand.nextDouble() * 0.25);
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

                    if ((   biome != lastBiome
                            || topBlock == -1
                            || fillerBlock == -1
                        )
                        && block == 0
                    ) {
                        topBlock = biome.topBlock;
                        fillerBlock = biome.fillerBlock;
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

                        {
                            Block<?> blk = Blocks.getBlock(topBlock);
                            if (
                                blk != null
                                && blk.hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS)
                                && soilThickness > 0
                                && rand.nextInt(16 * flowerDensity) == 0
                            ) {
                                result.setBlock(x, y+1, z, flowerBag.getRandom(rand));
                            }
                        }


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
