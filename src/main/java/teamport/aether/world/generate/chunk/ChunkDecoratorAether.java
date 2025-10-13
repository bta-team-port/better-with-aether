package teamport.aether.world.generate.chunk;

import net.minecraft.core.block.BlockLogicSand;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureFlowers;
import net.minecraft.core.world.generate.feature.WorldFeatureLake;
import net.minecraft.core.world.generate.feature.WorldFeatureTallGrass;
import net.minecraft.core.world.noise.PerlinNoise;
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
    public final World world;
    public final PerlinNoise treeDensityNoise;

    public ChunkDecoratorAether(World world) {
        this.world = world;
        this.treeDensityNoise = new PerlinNoise(world.getRandomSeed(), 8, 74);
    }

    public void decorate(Chunk chunk) {
        BlockLogicSand.fallInstantly = true;
        int chunkX = chunk.xPosition;
        int chunkZ = chunk.zPosition;
        int x = chunkX * 16;
        int z = chunkZ * 16;
        Random rand = new Random(this.world.getRandomSeed());
        rand.setSeed(this.world.getRandomSeed());
        long l1 = rand.nextLong() / 2L * 2L + 1L;
        long l2 = rand.nextLong() / 2L * 2L + 1L;
        rand.setSeed((long) chunkX * l1 + (long) chunkZ * l2 ^ this.world.getRandomSeed());
        int minY = this.world.getWorldType().getMinY();
        int maxY = this.world.getWorldType().getMaxY();
        int rangeY = maxY + 1 - minY;
        float oreHeightModifier = (float) rangeY / 128.0F;

        int xPosition = x + rand.nextInt(16);
        int yPosition;
        int zPosition = z + rand.nextInt(16);
        int generateChance;

        //Aercloud Generation
        if (rand.nextInt(12) == 0) {
            yPosition = rand.nextInt(32) + 224;
            (new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_GOLD.id(), 4)).place(this.world, rand, x + 8, yPosition, z + 8);
        }
        if (rand.nextInt(12) == 0) {
            yPosition = rand.nextInt(64) + 128;
            (new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_BLUE.id(), 8)).place(this.world, rand, x + 8, yPosition, z + 8);
        }
        if (rand.nextInt(6) == 0) {
            yPosition = rand.nextInt(192) + 32;
            (new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_WHITE.id(), 16)).place(this.world, rand, x + 8, yPosition, z + 8);
        }
        //Bottom Flat Clouds
        if (rand.nextInt(24) == 0) {
            yPosition = rand.nextInt(64) + 8;
            (new WorldFeatureAetherCloudsFlat(AetherBlocks.AERCLOUD_WHITE.id(), 48)).place(this.world, rand, x + 8, yPosition, z + 8);
        }


        //Dungeon Generation
        if ((chunkX & 1) == 0 && (chunkZ & 1) == 0) {

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
            } else if (silverSeed > -1) {
                int dungeonX = x - 15;
                int dungeonY = 200 + rand.nextInt(30);
                int dungeonZ = z + 28;
                silverDungeon(rand).place(this.world, rand, dungeonX, dungeonY, dungeonZ);
            } else if (bronzeSeed > -1) {
                int dungeonX = x + rand.nextInt(16);
                int dungeonZ = z + rand.nextInt(16);
                int max = 0;
                maxY = 0;

                int counter = 0;
                int startY = 0;

                for (int i = this.world.worldType.getMinY(); i < this.world.worldType.getMaxY(); i++) {
                    if (world.getBlockId(dungeonX, i, dungeonZ) != 0) {
                        counter++;
                    } else {
                        counter = 0;
                        startY = i;
                    }
                    if (counter > max) {
                        max = counter;
                        maxY = startY;
                    }
                }
                int dungeonY = Math.max(0, (maxY + max / 2) - 5);
                bronzeDungeon(rand).place(this.world, rand, dungeonX, dungeonY, dungeonZ);
            }
        }


        //Flowers/Foliage
        for (generateChance = 0; generateChance < 2; ++generateChance) {
            xPosition = x + rand.nextInt(16) + 8;
            yPosition = minY + rand.nextInt(rangeY);
            zPosition = z + rand.nextInt(16) + 8;
            (new WorldFeatureFlowers(AetherBlocks.FLOWER_WHITE.id(), 64, true)).place(this.world, rand, xPosition, yPosition, zPosition);
        }

        for (generateChance = 0; generateChance < 2; ++generateChance) {
            if (rand.nextInt(2) == 0) {
                xPosition = x + rand.nextInt(16) + 8;
                yPosition = minY + rand.nextInt(rangeY);
                zPosition = z + rand.nextInt(16) + 8;
                (new WorldFeatureFlowers(AetherBlocks.FLOWER_PURPLE.id(), 64, true)).place(this.world, rand, xPosition, yPosition, zPosition);
            }
        }

        for (generateChance = 0; generateChance < 2; ++generateChance) {
            xPosition = x + rand.nextInt(16) + 8;
            yPosition = minY + rand.nextInt(rangeY);
            zPosition = z + rand.nextInt(16) + 8;
            if (rand.nextInt(2) == 0) {
                (new WorldFeatureTallGrass(AetherBlocks.TALLGRASS_AETHER.id())).place(this.world, rand, xPosition, yPosition, zPosition);
            }
        }


        // Ores/Features
        for (generateChance = 0; generateChance < 20; ++generateChance) {
            yPosition = rand.nextInt(rangeY);
            (new WorldFeatureAetherOre(AetherBlocks.DIRT_AETHER.id(), 32)).place(this.world, rand, xPosition, yPosition, zPosition);
        }

        for (generateChance = 0; generateChance < 20; ++generateChance) {
            yPosition = rand.nextInt(rangeY);
            (new WorldFeatureAetherOre(AetherBlocks.ICESTONE.id(), 32)).place(this.world, rand, xPosition, yPosition, zPosition);
        }

        //Ambrosium 0-256
        for (generateChance = 0; (float) generateChance < 25.0F * oreHeightModifier; ++generateChance) {
            yPosition = rand.nextInt(rangeY);
            (new WorldFeatureAetherOre(BlockLogicOreAmbrosium.variantMap, 16)).place(this.world, rand, xPosition, yPosition, zPosition);
        }

        //Zanite 0-192
        for (generateChance = 0; (float) generateChance < 25.0F * oreHeightModifier; ++generateChance) {
            yPosition = rand.nextInt(192);
            (new WorldFeatureAetherOre(BlockLogicOreZanite.variantMap, 8)).place(this.world, rand, xPosition, yPosition, zPosition);
        }

        //Gravitite 0-128
        for (generateChance = 0; (float) generateChance < 15.0F * oreHeightModifier; ++generateChance) {
            yPosition = rand.nextInt(rangeY / 2);
            (new WorldFeatureAetherOre(BlockLogicOreGravitite.variantMap, 7)).place(this.world, rand, xPosition, yPosition, zPosition);
        }

        if (rand.nextInt(5) == 0) {
            yLoop:
            for (yPosition = 0; yPosition < 192; ++yPosition) {
                for (xPosition = x; xPosition < x + 16; ++xPosition) {
                    for (zPosition = z; zPosition < z + 16; ++zPosition) {
                        if (this.world.getBlockId(xPosition, yPosition, zPosition) == 0 && this.world.getBlockId(xPosition, yPosition + 1, zPosition) == AetherBlocks.GRASS_AETHER.id() && this.world.getBlockId(xPosition, yPosition + 2, zPosition) == 0) {
                            (new WorldFeatureAetherQuicksoil(AetherBlocks.QUICKSOIL.id())).place(this.world, rand, xPosition, yPosition, zPosition);
                            continue yLoop;
                        }
                    }
                }
            }
        }

        //Lakes/Waterfalls and Trees
        if (rand.nextInt(8) == 0) {
            yPosition = rand.nextInt(rangeY);
            (new WorldFeatureLake(Blocks.FLUID_WATER_STILL.id())).place(this.world, rand, xPosition, yPosition, zPosition);
        }

        for (generateChance = 0; generateChance < 2; ++generateChance) {
            WorldFeature worldFeature = rand.nextInt(18) == 0 ? new WorldFeatureAetherTreeGoldenOak(AetherBlocks.LEAVES_OAK_GOLDEN.id(), AetherBlocks.LOG_OAK_GOLDEN.id()) : new WorldFeatureAetherTree(AetherBlocks.LEAVES_SKYROOT.id(), AetherBlocks.LOG_SKYROOT.id(), 4);
            worldFeature.init(1.0, 1.0, 1.0);
            worldFeature.place(this.world, rand, xPosition, this.world.getHeightValue(xPosition, zPosition), zPosition);
        }

        for (generateChance = 0; generateChance < 50; ++generateChance) {
            yPosition = rand.nextInt(rangeY - 1);
            (new WorldFeatureAetherLiquid(Blocks.FLUID_WATER_FLOWING.id())).place(this.world, rand, xPosition, yPosition, zPosition);
        }

        BlockLogicSand.fallInstantly = false;
    }
}
