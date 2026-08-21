package teamport.aether.world.chunk;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFallingBlock;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import net.minecraft.core.world.generate.feature.WorldFeatureLake;
import net.minecraft.core.world.noise.*;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.type.tag.WorldTypeTags;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.terrain.BlockLogicOreAmbrosium;
import teamport.aether.block.terrain.BlockLogicOreGravitite;
import teamport.aether.block.terrain.BlockLogicOreZanite;
import teamport.aether.noise.Worley;
import teamport.aether.world.feature.dungeon.gold.WorldFeatureAetherGoldDungeon;
import teamport.aether.world.feature.dungeon.silver.WorldFeatureAetherSilverDungeon;
import teamport.aether.world.feature.terrain.*;
import teamport.aether.world.type.AetherWorldTypes;

import java.util.Random;

import static teamport.aether.AetherMod.*;

public class ChunkDecoratorAether implements ChunkDecorator {
    private final World world;

    private final Noise3D flowerVeinNoise;
    private final Noise3D flowerDensityNoise;
    private final Noise2D cloudNoise;
    private final Noise3D cloudNoise2;

    public ChunkDecoratorAether(@NonNull World world) {
        this.world = world;

        this.flowerVeinNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 4, 44));
        this.flowerDensityNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed() * 31 ^ 13, 4, 44));

        this.cloudNoise = new FractalNoise2D<>(SimplexNoise.genOctaves(world.getRandomSeed(), 4));
        this.cloudNoise2 = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed() * 31 ^ 7, 4, 44));
    }

    private static @NonNull Random deriveRandomFromWorld(@NonNull Chunk chunk, long seed) {
        Random rand = new Random(seed);

        long l1 = rand.nextLong() / 2L * 2L + 1L;
        long l2 = rand.nextLong() / 2L * 2L + 1L;

        rand.setSeed(chunk.pos.x * l1 + chunk.pos.z * l2 ^ seed);
        return rand;
    }

    @Override
    public void decorate(@NonNull Chunk chunk) {
        this.world.scheduledUpdatesAreImmediate = true;
        BlockLogicFallingBlock.fallInstantly = true;

        int minY = this.world.getWorldType().getMinY(world);
        int maxY = this.world.getWorldType().getMaxY(world);
        int worldX = chunk.pos.x * 16;
        int worldZ = chunk.pos.z * 16;
        Random rand = deriveRandomFromWorld(chunk, this.world.getRandomSeed());

        decorateWithClouds(rand, minY, maxY, worldX, worldZ);

        if (world.getWorldType() == AetherWorldTypes.AETHER_EXTENDED) {
            decorateWithFlatClouds(chunk);
        }

        decorateWithDungeons(chunk, rand, minY, maxY);

        decorateWithFlowers(chunk, rand);
        decorateWithQuickSoil(rand, worldX, worldZ, minY, maxY);
        decorateWithLakesAndTrees(rand, minY, maxY, worldX, worldZ);

        decorateWithOres(rand, minY, maxY, worldX, worldZ);

        BlockLogicFallingBlock.fallInstantly = false;
        this.world.scheduledUpdatesAreImmediate = false;
    }

    private final double[] CLOUD_NOISE_BUFFER = new double[16 * 16];
    private final double[] CLOUD_NOISE_2_BUFFER = new double[16 * 16];
    private final double[] CLOUD_NOISE_TOP_BUFFER = new double[16 * 16];
    private final double[] CLOUD_NOISE_TOP_2_BUFFER = new double[16 * 16];

    public void decorateWithFlatClouds(@NonNull Chunk chunk) {
        double scale = 0.38;
        double chunkX = chunk.pos.x * 16.0;
        double chunkZ = chunk.pos.z * 16.0;

        this.cloudNoise.getRegion(
            CLOUD_NOISE_BUFFER,
            chunkX, chunkZ,
            16, 16,
            scale * 0.627,
            scale * 2
        );

        this.cloudNoise2.getRegion(
            CLOUD_NOISE_2_BUFFER,
            chunkX, chunkZ - 32.0,
            0.0,
            16, 16, 1,
            scale * 0.627,
            scale * 0.627,
            scale * 2
        );

        this.cloudNoise.getRegion(
            CLOUD_NOISE_TOP_BUFFER,
            chunkX + 32.0, chunkZ + 32.0,
            16, 16,
            scale * 0.627,
            scale * 2
        );

        this.cloudNoise2.getRegion(
            CLOUD_NOISE_TOP_2_BUFFER,
            chunkX + 48.0, chunkZ + 32.0,
            0.0,
            16, 16, 1,
            scale * 0.627,
            scale * 0.627,
            scale * 2
        );

        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                int index = z + x * 16;

                int cloudDensity = (int) Math.min(
                    Math.abs(CLOUD_NOISE_2_BUFFER[index] + CLOUD_NOISE_BUFFER[index]) * 6 - 32, 2
                );

                for (int y = 0; y < cloudDensity; y++) {
                    if (chunk.getBlockID(x, 24 + y, z) == Blocks.AIR.id()) {
                        chunk.setBlockID(x, 24 + y, z, AetherBlocks.AERCLOUD_WHITE.id());
                    }
                }

                int cloudDensity2 = (int) Math.min(
                    Math.abs(CLOUD_NOISE_TOP_2_BUFFER[index] + CLOUD_NOISE_TOP_BUFFER[index]) * 6 - 40, 1
                );

                for (int y = 0; y < cloudDensity2; y++) {
                    if (chunk.getBlockID(x, 14 + y, z) == Blocks.AIR.id()) {
                        chunk.setBlockID(x, 14 + y, z, AetherBlocks.AERCLOUD_WHITE.id());
                    }
                }
            }
        }
    }

    public static final int[] FLOWERS = new int[]{
        AetherBlocks.FLOWER_WHITE.id(),
        AetherBlocks.FLOWER_PURPLE.id()
    };

    private static final int[] META_ID = new int[]{
        0, 32, 64, 96
    };

    private final double[] FLOWER_DENSITY_NOISE_BUFFER = new double[16 * 16];
    private final double[] FLOWER_VEIN_NOISE_BUFFER = new double[8 * 8];

    public void decorateWithFlowers(@NonNull Chunk chunk, Random rand) {
        double beachScale = 0.03125;
        int chunkX = chunk.pos.x * 16;
        int chunkZ = chunk.pos.z * 16;

        this.flowerDensityNoise.getRegion(
            FLOWER_DENSITY_NOISE_BUFFER,
            chunkX,
            chunkZ,
            0.0,
            16, 16, 1,
            beachScale * 1.5,
            beachScale * 1.5,
            beachScale * 1.5
        );

        this.flowerVeinNoise.getRegion(
            FLOWER_VEIN_NOISE_BUFFER,
            chunkX,
            chunkZ,
            0.0,
            8, 8, 1,
            beachScale * 0.627,
            beachScale * 0.627,
            beachScale * 0.627
        );

        boolean isRetro = world.getWorldType().hasTag(WorldTypeTags.RETRO);
        int minY = world.getWorldType().getMinY(world);
        int maxY = world.getWorldType().getMaxY(world);
        double clumpRadius = 64.0;

        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                double noise = MathHelper.clamp(
                    Math.abs(FLOWER_DENSITY_NOISE_BUFFER[z + x * 8]) / 16.0,
                    0.0, 1.0
                );

                double influence = MathHelper.clamp(
                    Worley.sampleAt(
                        chunkX + x, chunkZ + z,
                        16,
                        Worley.mix((int) (world.getRandomSeed() >>> 32), (int) (world.getRandomSeed() & 0xFFFFFFFFL), 0)
                    ),
                    -clumpRadius, clumpRadius
                ) / clumpRadius;

                double flowerDensityFloat = ((noise * -1) / influence) * -1;
                int flowerDensity = (int) (MathHelper.clamp(flowerDensityFloat, 0, 1) * 16);
                flowerDensity -= 8;

                int blockY = chunk.getHeightValue(x, z);
                if (blockY <= minY + 1 || blockY >= maxY) continue;

                Block<?> blk = Blocks.getBlock(chunk.getBlockID(x, blockY - 1, z));
                if (!blk.hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS)) continue;

                if (flowerDensity < 0) {
                    if (rand.nextInt(128) == 0) {
                        chunk.setBlockIDWithMetadataRaw(x, blockY, z, FLOWERS[rand.nextInt(FLOWERS.length)], META_ID[rand.nextInt(META_ID.length)]);
                    }

                    if (!isRetro && rand.nextInt(16) == 0) {
                        chunk.setBlockID(x, blockY, z, AetherBlocks.TALLGRASS_AETHER.id());
                    }

                    continue;
                }

                if (rand.nextInt(3 * (9 - flowerDensity)) == 0) {
                    int flowerID;
                    int flowerMeta;

                    if (rand.nextInt(2) == 0) {
                        flowerID = FLOWERS[(int) Math.abs(FLOWER_VEIN_NOISE_BUFFER[z / 2 + (x / 2) * 8] * 8) % FLOWERS.length];
                        flowerMeta = META_ID[rand.nextInt(META_ID.length)];
                    } else {
                        flowerID = !isRetro ? AetherBlocks.TALLGRASS_AETHER.id() : 0;
                        flowerMeta = 0;
                    }

                    if (flowerID != 0) {
                        chunk.setBlockIDWithMetadataRaw(x, blockY, z, flowerID, flowerMeta);
                    }
                }
            }
        }
    }

    public static final WorldFeatureAetherQuicksoil QUICKSOIL = new WorldFeatureAetherQuicksoil(AetherBlocks.QUICKSOIL.id());

    @SuppressWarnings("java:S1119")
    public void decorateWithQuickSoil(@NonNull Random rand, int worldX, int worldZ, int minY, int maxY) {
        int rangeY = maxY + 1 - minY;

        int yPosition;
        int zPosition;
        int xPosition;

        if (rand.nextInt(5) == 0) {
            yLoop:
            for (yPosition = minY + (rangeY / 8); yPosition < minY + (int) (192.0F / 256.0F * rangeY); ++yPosition) {
                for (xPosition = worldX; xPosition < worldX + 16; ++xPosition) {
                    for (zPosition = worldZ; zPosition < worldZ + 16; ++zPosition) {
                        if (
                            this.world.getBlockId(xPosition, yPosition, zPosition) == Blocks.AIR.id()
                                && this.world.getBlockId(xPosition, yPosition + 1, zPosition) == AetherBlocks.GRASS_AETHER.id()
                                && this.world.getBlockId(xPosition, yPosition + 2, zPosition) == Blocks.AIR.id()
                        ) {
                            QUICKSOIL.place(this.world, rand, xPosition, yPosition, zPosition);
                            continue yLoop;
                        }
                    }
                }
            }
        }
    }

    public static final WorldFeatureAetherTreeGoldenOak TREE_GOLDEN = new WorldFeatureAetherTreeGoldenOak();
    public static final WorldFeatureAetherTree TREE_SKYROOT = new WorldFeatureAetherTree(AetherBlocks.LEAVES_SKYROOT.id(), AetherBlocks.LOG_SKYROOT.id(), 4);
    public static final WorldFeatureAetherLiquid WATERFALL = new WorldFeatureAetherLiquid(Blocks.FLUID_WATER_FLOWING.id());
    public static final WorldFeatureLake LAKE_WATER = new WorldFeatureLake(Blocks.FLUID_WATER_STILL.id());

    public void decorateWithLakesAndTrees(@NonNull Random rand, int minY, int maxY, int chunkX, int chunkZ) {
        int rangeY = maxY + 1 - minY;

        if (rand.nextInt(8) == 0) {
            int x = chunkX + rand.nextInt(16);
            int z = chunkZ + rand.nextInt(16);
            int y = rand.nextInt(rangeY);
            LAKE_WATER.place(this.world, rand, new TilePos(x, y, z));
        }

        for (int generateChance = 0; generateChance < 2; ++generateChance) {
            int x = chunkX + rand.nextInt(16);
            int z = chunkZ + rand.nextInt(16);
            int y = this.world.getHeightValue(x, z);
            (rand.nextInt(18) == 0 ? TREE_GOLDEN : TREE_SKYROOT)
                .place(this.world, rand, new TilePos(x, y, z));
        }

        for (int generateChance = 0; generateChance < 50; ++generateChance) {
            int x = chunkX + rand.nextInt(16);
            int z = chunkZ + rand.nextInt(16);
            int y = rand.nextInt(rangeY - 1);
            WATERFALL.place(this.world, rand, new TilePos(x, y, z));
        }
    }

    public static final WorldFeatureAetherClouds AERCLOUD_WHITE = new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_WHITE.id(), 16, false);
    public static final WorldFeatureAetherClouds AERCLOUD_BLUE = new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_BLUE.id(), 8, false);
    public static final WorldFeatureAetherClouds AERCLOUD_GOLD = new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_GOLD.id(), 4, false);
    public static final WorldFeatureAetherClouds AERCLOUD_FLAT = new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_WHITE.id(), 48, true);

    public void decorateWithClouds(@NonNull Random rand, int minY, int maxY, int worldX, int worldZ) {
        int rangeY = maxY + 1 - minY;
        float heightModifier = rangeY / 128.0F;
        int yPosition;

        if (rand.nextInt(12) == 0) {
            int base = rand.nextInt(16) + 112;
            yPosition = minY + Math.round(base * heightModifier);
            AERCLOUD_GOLD.place(this.world, rand, worldX + 8, yPosition, worldZ + 8);
        }

        if (rand.nextInt(12) == 0) {
            int base = rand.nextInt(32) + 64;
            yPosition = minY + Math.round(base * heightModifier);
            AERCLOUD_BLUE.place(this.world, rand, worldX + 8, yPosition, worldZ + 8);
        }

        if (rand.nextInt(6) == 0) {
            int base = rand.nextInt(96) + 16;
            yPosition = minY + Math.round(base * heightModifier);
            AERCLOUD_WHITE.place(this.world, rand, worldX + 8, yPosition, worldZ + 8);
        }

        if ((world.getWorldType() == AetherWorldTypes.AETHER_DEFAULT || world.getWorldType() == AetherWorldTypes.AETHER_RETRO) && rand.nextInt(24) == 0) {
            yPosition = rand.nextInt(32) + 4;
            AERCLOUD_FLAT.place(this.world, rand, worldX + 8, yPosition, worldZ + 8);
        }
    }

    public static final WorldFeatureAetherOre ORE_DIRT = new WorldFeatureAetherOre(AetherBlocks.DIRT_AETHER.id(), 32);
    public static final WorldFeatureAetherOre ORE_ICESTONE = new WorldFeatureAetherOre(AetherBlocks.ICESTONE.id(), 32);
    public static final WorldFeatureAetherOre ORE_AMBROSIUM = new WorldFeatureAetherOre(BlockLogicOreAmbrosium.variantMap, 16);
    public static final WorldFeatureAetherOre ORE_ZANITE = new WorldFeatureAetherOre(BlockLogicOreZanite.variantMap, 8);
    public static final WorldFeatureAetherOre ORE_GRAVITITE = new WorldFeatureAetherOre(BlockLogicOreGravitite.variantMap, 7);

    public void decorateWithOres(Random rand, int minY, int maxY, int worldX, int worldZ) {
        int rangeY = maxY + 1 - minY;
        float oreHeightModifier = rangeY / 128.0F;

        int x;
        int y;
        int z;
        int generateChance;
        for (generateChance = 0; generateChance < 10 * oreHeightModifier; ++generateChance) {
            y = rand.nextInt(rangeY);
            x = worldX + rand.nextInt(16);
            z = worldZ + rand.nextInt(16);
            ORE_DIRT.place(this.world, rand, new TilePos(x, y, z));
        }

        for (generateChance = 0; generateChance < 10 * oreHeightModifier; ++generateChance) {
            y = rand.nextInt(rangeY);
            x = worldX + rand.nextInt(16);
            z = worldZ + rand.nextInt(16);
            ORE_ICESTONE.place(this.world, rand, new TilePos(x, y, z));
        }

        //Ambrosium 0-256
        for (generateChance = 0; generateChance < 20.0F * oreHeightModifier; ++generateChance) {
            y = rand.nextInt(rangeY);
            x = worldX + rand.nextInt(16);
            z = worldZ + rand.nextInt(16);
            ORE_AMBROSIUM.place(this.world, rand, new TilePos(x, y, z));
        }

        //Zanite 0-192
        for (generateChance = 0; generateChance < 15.0F * oreHeightModifier; ++generateChance) {
            y = rand.nextInt(rangeY / 2);
            x = worldX + rand.nextInt(16);
            z = worldZ + rand.nextInt(16);
            ORE_ZANITE.place(this.world, rand, new TilePos(x, y, z));
        }

        //Gravitite 0-128
        for (generateChance = 0; generateChance < 8.0f * oreHeightModifier; ++generateChance) {
            y = rand.nextInt(rangeY / 3);
            x = worldX + rand.nextInt(16);
            z = worldZ + rand.nextInt(16);
            ORE_GRAVITITE.place(this.world, rand, new TilePos(x, y, z));
        }
    }

    public void decorateWithDungeons(@NonNull Chunk chunk, Random rand, int minY, int maxY) {
        int chunkX = chunk.pos.x;
        int chunkZ = chunk.pos.z;

        int rangeY = maxY + 1 - minY;

        int x = chunkX * 16;
        int z = chunkZ * 16;

        int gridX = MathHelper.floor(chunkX / 2.0F);
        int gridZ = MathHelper.floor(chunkZ / 2.0F);

        long worldSeed = this.world.getRandomSeed();
        int transformedSeed = Worley.mix((int) (worldSeed >>> 32), (int) (worldSeed & 0xFFFFFFFFL), 0);
        int goldSeed = Worley.isSeed(gridX, gridZ, GOLD_CHANCES, transformedSeed, 1, 1); // 22 - 2
        int silverSeed = Worley.isSeed(gridX, gridZ, SILVER_CHANCES, transformedSeed, 1, 1); // 16 - 2
        int bronzeSeed = Worley.isSeed(gridX, gridZ, BRONZE_CHANCES, transformedSeed, 1, 0); // 8 - 0

        if (goldSeed > -1) {
            int dungeonX = x + 8;
            int dungeonY = (rangeY / 2) + rand.nextInt(rangeY / 8);
            int dungeonZ = z + 8;

            WorldFeatureAetherGoldDungeon goldDungeon = new WorldFeatureAetherGoldDungeon(rand);

            if (goldDungeon.canPlace(world, dungeonX, dungeonY, dungeonZ)) {
                goldDungeon.register(world, worldSeed ^ rand.nextLong(), dungeonX, dungeonY, dungeonZ);
            }

        } else if (silverSeed > -1) {
            int dungeonX = x - 15;
            int dungeonY = (int) ((rangeY - (rangeY / 4.5)) + rand.nextInt(rangeY / 8));
            int dungeonZ = z + 28;

            WorldFeatureAetherSilverDungeon silverDungeon = new WorldFeatureAetherSilverDungeon(rand);
            if (silverDungeon.canPlace(world, dungeonX, dungeonY, dungeonZ)) {
                silverDungeon.register(this.world, worldSeed ^ rand.nextLong(), dungeonX, dungeonY, dungeonZ);
            }
        } else if (bronzeSeed > -1) {
            int dungeonX = x + rand.nextInt(16);
            int dungeonZ = z + rand.nextInt(16);

            // find the deepest section in the current chunk.
            int maxDepth = 0;
            int maxDepthStart = 0;

            int currentDepth = 0;
            int currentStart = 0;

            for (int i = this.world.getWorldType().getMinY(this.world); i < this.world.getWorldType().getMaxY(this.world); i++) {
                if (world.getBlockId(dungeonX, i, dungeonZ) != 0) {
                    currentDepth++;
                } else {
                    currentDepth = 0;
                    currentStart = i;
                }

                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                    maxDepthStart = currentStart;
                }
            }

            int dungeonY = Math.max(0, (maxDepthStart + maxDepth / 2) - (int) (5.0F / 256.0F * rangeY));
//            new WorldFeatureAetherBronzeDungeon().place(this.world, rand, dungeonX, dungeonY, dungeonZ);
        }
    }
}
