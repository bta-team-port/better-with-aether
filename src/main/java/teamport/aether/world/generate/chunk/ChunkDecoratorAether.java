package teamport.aether.world.generate.chunk;

import net.minecraft.core.block.BlockLogicSand;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import net.minecraft.core.world.generate.feature.WorldFeatureFlowers;
import net.minecraft.core.world.generate.feature.WorldFeatureLake;
import net.minecraft.core.world.generate.feature.WorldFeatureTallGrass;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.terrain.BlockLogicOreAmbrosium;
import teamport.aether.blocks.terrain.BlockLogicOreGravitite;
import teamport.aether.blocks.terrain.BlockLogicOreZanite;
import teamport.aether.noise.Worley;
import teamport.aether.world.generate.feature.*;

import java.util.Random;

import static teamport.aether.AetherMod.*;
import static teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherBronzeDungeon.bronzeDungeon;
import static teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherGoldDungeon.goldDungeon;
import static teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherSilverDungeon.silverDungeon;

public class ChunkDecoratorAether implements ChunkDecorator {
    private final World world;

    protected ChunkDecoratorAether(World world) {
        this.world = world;
    }

    public void decorate(Chunk chunk) {
        this.world.scheduledUpdatesAreImmediate = true;
        BlockLogicSand.fallInstantly = true;

        int minY = this.world.getWorldType().getMinY();
        int maxY = this.world.getWorldType().getMaxY();
        int worldX = chunk.xPosition * 16;
        int worldZ = chunk.zPosition * 16;
        Random rand = deriveRandomFromWorld(chunk, this.world.getRandomSeed());

        decorateWithClouds(rand, worldX, worldZ);

        if (   (chunk.xPosition & 1) == 0
                && (chunk.zPosition&1) == 0) {
            decorateWithDungeons(chunk, rand);
        }

        decorateWithFoliage(rand, minY, maxY, worldX, worldZ);
        decorateWithOres(rand, minY, maxY, worldX, worldZ);
        decorateWithQuickSoil(rand, worldX, worldZ);
        decorateWithLakesAndTrees(rand, minY, maxY, worldX, worldZ);

        BlockLogicSand.fallInstantly = false;
        this.world.scheduledUpdatesAreImmediate = false;
    }

    private static Random deriveRandomFromWorld(Chunk chunk, long seed) {
        Random rand = new Random(seed);

        long l1 = rand.nextLong() / 2L * 2L + 1L;
        long l2 = rand.nextLong() / 2L * 2L + 1L;

        rand.setSeed((long) chunk.xPosition * l1 + (long) chunk.zPosition * l2 ^ seed);
        return rand;
    }

    public static final WorldFeatureAetherQuicksoil QUICKSOIL = new WorldFeatureAetherQuicksoil(AetherBlocks.QUICKSOIL.id());

    public void decorateWithQuickSoil(Random rand, int worldX, int worldZ) {
        int yPosition;
        int zPosition;
        int xPosition;

        if (rand.nextInt(5) == 0) {
            yLoop:
            for (yPosition = 0; yPosition < 192; ++yPosition) {
                for (xPosition = worldX; xPosition < worldX + 16; ++xPosition) {
                    for (zPosition = worldZ; zPosition < worldZ + 16; ++zPosition) {
                        if (
                                this.world.getBlockId(xPosition, yPosition, zPosition) == 0
                                        && this.world.getBlockId(xPosition, yPosition + 1, zPosition) == AetherBlocks.GRASS_AETHER.id()
                                        && this.world.getBlockId(xPosition, yPosition + 2, zPosition) == 0
                        ) {
                            QUICKSOIL.place(this.world, rand, xPosition, yPosition, zPosition);
                            continue yLoop;
                        }
                    }
                }
            }
        }
    }

    public static final WorldFeatureAetherTreeGoldenOak TREE_GOLDEN = new WorldFeatureAetherTreeGoldenOak(AetherBlocks.LEAVES_OAK_GOLDEN.id(), AetherBlocks.LOG_OAK_GOLDEN.id());
    public static final WorldFeatureAetherTree TREE_SKYROOT = new WorldFeatureAetherTree(AetherBlocks.LEAVES_SKYROOT.id(), AetherBlocks.LOG_SKYROOT.id(), 4);
    public static final WorldFeatureAetherLiquid LAKE = new WorldFeatureAetherLiquid(Blocks.FLUID_WATER_FLOWING.id());

    public void decorateWithLakesAndTrees(Random rand, int minY, int maxY, int x, int z) {
        int rangeY = maxY + 1 - minY;

        int y;
        x += rand.nextInt(16);
        z += rand.nextInt(16);

        int generateChance;
        if (rand.nextInt(8) == 0) {
            y = rand.nextInt(rangeY);
            (new WorldFeatureLake(Blocks.FLUID_WATER_STILL.id())).place(this.world, rand, x, y, z);
        }

        for (generateChance = 0; generateChance < 2; ++generateChance) {
            (rand.nextInt(18) == 0 ? TREE_GOLDEN : TREE_SKYROOT)
                    .place(this.world, rand, x, this.world.getHeightValue(x, z), z);
        }

        for (generateChance = 0; generateChance < 50; ++generateChance) {
            y = rand.nextInt(rangeY - 1);
            LAKE.place(this.world, rand, x, y, z);
        }
    }

    public static final WorldFeatureFlowers WHITE_FLOWERS = new WorldFeatureFlowers(AetherBlocks.FLOWER_WHITE.id(), 64, true);
    public static final WorldFeatureFlowers WHITE_PURPLE= new WorldFeatureFlowers(AetherBlocks.FLOWER_PURPLE.id(), 64, true);
    public static final WorldFeatureTallGrass TALL_GRASS = new WorldFeatureTallGrass(AetherBlocks.TALLGRASS_AETHER.id());

    public void decorateWithFoliage(Random rand, int minY, int maxY, int worldX, int worldZ) {
        int generateChance;

        int xPosition;
        int yPosition;
        int zPosition;

        int rangeY = maxY + 1 - minY;

        for (generateChance = 0; generateChance < 2; ++generateChance) {

            xPosition = worldX + rand.nextInt(16) + 8;
            yPosition = minY + rand.nextInt(rangeY);
            zPosition = worldZ + rand.nextInt(16) + 8;

            WHITE_FLOWERS.place(this.world, rand, xPosition, yPosition, zPosition);
        }

        for (generateChance = 0; generateChance < 2; ++generateChance) {
            if (rand.nextInt(2) == 0) {

                xPosition = worldX + rand.nextInt(16) + 8;
                yPosition = minY + rand.nextInt(rangeY);
                zPosition = worldZ + rand.nextInt(16) + 8;

                WHITE_PURPLE.place(this.world, rand, xPosition, yPosition, zPosition);
            }
        }

        for (generateChance = 0; generateChance < 2; ++generateChance) {
            if (rand.nextInt(2) == 0) {

                xPosition = worldX + rand.nextInt(16) + 8;
                yPosition = minY + rand.nextInt(rangeY);
                zPosition = worldZ + rand.nextInt(16) + 8;

                TALL_GRASS.place(this.world, rand, xPosition, yPosition, zPosition);
            }
        }
    }

    public static final WorldFeatureAetherClouds AERCLOUD_WHITE = new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_WHITE.id(), 16);
    public static final WorldFeatureAetherClouds AERCLOUD_BLUE = new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_BLUE.id(), 8);
    public static final WorldFeatureAetherClouds AERCLOUD_GOLD = new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_GOLD.id(), 4);
    public static final WorldFeatureAetherCloudsFlat AERCLOUD_FLAT = new WorldFeatureAetherCloudsFlat(AetherBlocks.AERCLOUD_WHITE.id(), 48);

    public void decorateWithClouds(Random rand, int worldX, int worldZ) {

        int yPosition;
        if (rand.nextInt(12) == 0) {
            yPosition = rand.nextInt(32) + 224;
            AERCLOUD_GOLD.place(this.world, rand, worldX + 8, yPosition, worldZ + 8);
        }

        if (rand.nextInt(12) == 0) {
            yPosition = rand.nextInt(64) + 128;
            AERCLOUD_BLUE.place(this.world, rand, worldX + 8, yPosition, worldZ + 8);
        }

        if (rand.nextInt(6) == 0) {
            yPosition = rand.nextInt(192) + 32;
            AERCLOUD_WHITE.place(this.world, rand, worldX + 8, yPosition, worldZ + 8);
        }

        if (rand.nextInt(24) == 0) {
            yPosition = rand.nextInt(64) + 8;
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
        float oreHeightModifier = (float) rangeY / 128.0F;

        int x;
        int y;
        int z;
        int generateChance;
        for (generateChance = 0; generateChance < 20; ++generateChance) {
            y = rand.nextInt(rangeY);
            x = worldX + rand.nextInt(16);
            z = worldZ + rand.nextInt(16);
            ORE_DIRT.place(this.world, rand, x, y, z);
        }

        for (generateChance = 0; generateChance < 20; ++generateChance) {
            y = rand.nextInt(rangeY);
            x = worldX + rand.nextInt(16);
            z = worldZ + rand.nextInt(16);
            ORE_ICESTONE.place(this.world, rand, x, y, z);
        }

        //Ambrosium 0-256
        for (generateChance = 0; (float) generateChance < 25.0F * oreHeightModifier; ++generateChance) {
            y = rand.nextInt(rangeY);
            x = worldX + rand.nextInt(16);
            z = worldZ + rand.nextInt(16);
            ORE_AMBROSIUM.place(this.world, rand, x, y, z);
        }

        //Zanite 0-192
        for (generateChance = 0; (float) generateChance < 25.0F * oreHeightModifier; ++generateChance) {
            y = rand.nextInt(192);
            x = worldX + rand.nextInt(16);
            z = worldZ + rand.nextInt(16);
            ORE_ZANITE.place(this.world, rand, x, y, z);
        }

        //Gravitite 0-128
        for (generateChance = 0; (float) generateChance < 15.0F * oreHeightModifier; ++generateChance) {
            y = rand.nextInt(rangeY / 2);
            x = worldX + rand.nextInt(16);
            z = worldZ + rand.nextInt(16);
            ORE_GRAVITITE.place(this.world, rand, x, y, z);
        }
    }

    public void decorateWithDungeons(Chunk chunk, Random rand) {
        int chunkX = chunk.xPosition;
        int chunkZ = chunk.zPosition;

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
            int dungeonX = x + rand.nextInt(16);
            int dungeonY = 60 + rand.nextInt(90);
            int dungeonZ = z + rand.nextInt(16);
            goldDungeon(rand).place(this.world, rand, dungeonX, dungeonY, dungeonZ);
        }

        else if (silverSeed > -1) {
            int dungeonX = x - 15;
            int dungeonY = 200 + rand.nextInt(30);
            int dungeonZ = z + 28;
            silverDungeon(rand).place(this.world, rand, dungeonX, dungeonY, dungeonZ);
        }

        else if (bronzeSeed > -1) {
            int dungeonX = x + rand.nextInt(16);
            int dungeonZ = z + rand.nextInt(16);

            // find the deepest section in the current chunk.
            int maxDepth = 0;
            int maxDepthStart = 0;

            int currentDepth = 0;
            int currentStart = 0;

            for (int i = this.world.worldType.getMinY(); i < this.world.worldType.getMaxY(); i++) {
                if (world.getBlockId(dungeonX, i, dungeonZ) != 0) {
                    currentDepth++;
                }
                else {
                    currentDepth = 0;
                    currentStart = i;
                }

                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                    maxDepthStart = currentStart;
                }
            }

            int dungeonY = Math.max(0, (maxDepthStart + maxDepth / 2) - 5);
            bronzeDungeon(rand).place(this.world, rand, dungeonX, dungeonY, dungeonZ);
        }
    }
}