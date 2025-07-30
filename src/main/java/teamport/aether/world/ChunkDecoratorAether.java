package teamport.aether.world;

import net.minecraft.core.block.BlockLogicSand;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import net.minecraft.core.world.generate.feature.*;
import net.minecraft.core.world.noise.PerlinNoise;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.BlockLogicOreAmbrosium;
import teamport.aether.blocks.BlockLogicOreGravitite;
import teamport.aether.blocks.BlockLogicOreZanite;
import teamport.aether.gen.feature.*;

import java.util.Random;

public class ChunkDecoratorAether implements ChunkDecorator {
    public final World world;
    public final PerlinNoise treeDensityNoise;
    public static int gumCount;

    public ChunkDecoratorAether(World world) {
        this.world = world;
        this.treeDensityNoise = new PerlinNoise(world.getRandomSeed(), 8, 74);
    }


    public void decorate(Chunk chunk) {
        BlockLogicSand.fallInstantly = true;
        int chunkX = chunk.xPosition;
        int chunkZ = chunk.zPosition;
        int k = chunkX * 16;
        int l = chunkZ * 16;
        int y = this.world.getHeightValue(k + 16, l + 16);
        Biome biome = this.world.getBlockBiome(k + 16, y, l + 16);
        Random rand = new Random(this.world.getRandomSeed());
        rand.setSeed(this.world.getRandomSeed());
        long l1 = rand.nextLong() / 2L * 2L + 1L;
        long l2 = rand.nextLong() / 2L * 2L + 1L;
        rand.setSeed((long) chunkX * l1 + (long) chunkZ * l2 ^ this.world.getRandomSeed());

        double d = 0.25;
        int l7;
        int k17;
        int j20;
//        if (gumCount < 800) {
//            ++gumCount;
//        } else if (rand.nextInt(32) == 0) {
//            boolean flag;
//            l7 = k + rand.nextInt(16) + 8;
//            k17 = rand.nextInt(64) + 32;
//            j20 = l + rand.nextInt(16) + 8;
//            flag = (new AetherGenGumdrop()).place(this.world, rand, l7, k17, j20);
//            if (flag) {
//                gumCount = 0;
//            }
//        }

        int k4;
        if (rand.nextInt(3) == 0) {
            k4 = k + rand.nextInt(16) + 8;
            l7 = rand.nextInt(256);
            k17 = l + rand.nextInt(16) + 8;
            (new WorldFeatureLake(Blocks.FLUID_WATER_STILL.id())).place(this.world, rand, k4, l7, k17);
        }

        for (k4 = 0; k4 < 20; ++k4) {
            l7 = k + rand.nextInt(16);
            k17 = rand.nextInt(256);
            j20 = l + rand.nextInt(16);
            (new WorldFeatureOre(AetherBlocks.DIRT_AETHER.id(), 32)).place(this.world, rand, l7, k17, j20);
        }

        for (k4 = 0; k4 < 2; ++k4) {
            l7 = k + rand.nextInt(16) + 8;
            k17 = rand.nextInt(256);
            j20 = l + rand.nextInt(16) + 8;
            (new WorldFeatureFlowers(AetherBlocks.FLOWER_WHITE.id(), 64, true)).place(this.world, rand, l7, k17, j20);
        }

        for (k4 = 0; k4 < 2; ++k4) {
            l7 = k + rand.nextInt(16) + 8;
            k17 = rand.nextInt(256);
            j20 = l + rand.nextInt(16) + 8;
            (new WorldFeatureTallGrass(AetherBlocks.TALLGRASS_AETHER.id())).place(this.world, rand, l7, k17, j20);
        }

        for (k4 = 0; k4 < 2; ++k4) {
            if (rand.nextInt(2) == 0) {
                l7 = k + rand.nextInt(16) + 8;
                k17 = rand.nextInt(256);
                j20 = l + rand.nextInt(16) + 8;
                (new WorldFeatureFlowers(AetherBlocks.FLOWER_PURPLE.id(), 64, true)).place(this.world, rand, l7, k17, j20);
            }
        }

        for (k4 = 0; k4 < 10; ++k4) {
            l7 = k + rand.nextInt(16);
            k17 = rand.nextInt(256);
            j20 = l + rand.nextInt(16);
            (new WorldFeatureOre(AetherBlocks.ICESTONE.id(), 32)).place(this.world, rand, l7, k17, j20);
        }

        for (k4 = 0; k4 < 20; ++k4) {
            l7 = k + rand.nextInt(16);
            k17 = rand.nextInt(256);
            j20 = l + rand.nextInt(16);
            (new WorldFeatureOre(BlockLogicOreAmbrosium.variantMap, 16)).place(this.world, rand, l7, k17, j20);
        }

        for (k4 = 0; k4 < 15; ++k4) {
            l7 = k + rand.nextInt(16);
            k17 = rand.nextInt(192);
            j20 = l + rand.nextInt(16);
            (new WorldFeatureOre(BlockLogicOreZanite.variantMap, 8)).place(this.world, rand, l7, k17, j20);
        }

        for (k4 = 0; k4 < 8; ++k4) {
            l7 = k + rand.nextInt(16);
            k17 = rand.nextInt(128);
            j20 = l + rand.nextInt(16);
            (new WorldFeatureOre(BlockLogicOreGravitite.variantMap, 7)).place(this.world, rand, l7, k17, j20);
        }

        if (rand.nextInt(50) == 0) {
            k4 = k + rand.nextInt(16);
            l7 = rand.nextInt(32) + 224;
            k17 = l + rand.nextInt(16);
            (new WorldFeatureClouds(AetherBlocks.AERCLOUD_GOLD.id(), 4, false)).place(this.world, rand, k4, l7, k17);
        }

        if (rand.nextInt(13) == 0) {
            k4 = k + rand.nextInt(16);
            l7 = rand.nextInt(64) + 128;
            k17 = l + rand.nextInt(16);
            (new WorldFeatureClouds(AetherBlocks.AERCLOUD_BLUE.id(), 8, false)).place(this.world, rand, k4, l7, k17);
        }

        if (rand.nextInt(7) == 0) {
            k4 = k + rand.nextInt(16);
            l7 = rand.nextInt(192) + 32;
            k17 = l + rand.nextInt(16);
            (new WorldFeatureClouds(AetherBlocks.AERCLOUD_WHITE.id(), 16, false)).place(this.world, rand, k4, l7, k17);
        }

        if (rand.nextInt(50) == 0) {
            k4 = k + rand.nextInt(16);
            l7 = rand.nextInt(64) + 8;
            k17 = l + rand.nextInt(16);
            (new WorldFeatureClouds(AetherBlocks.AERCLOUD_WHITE.id(), 64, true)).place(this.world, rand, k4, l7, k17);
        }

//        for (k4 = 0; k4 < 2; ++k4) {
//            l7 = k + rand.nextInt(16);
//            k17 = 32 + rand.nextInt(64);
//            j20 = l + rand.nextInt(16);
//            (new AetherGenDungeonBronze(AetherBlocks.LockedDungeonStone.id, AetherBlocks.LockedLightDungeonStone.id, AetherBlocks.DungeonStone.id, AetherBlocks.LightDungeonStone.id, AetherBlocks.Holystone.id, 2, AetherBlocks.Holystone.id, 0, 16, true)).place(this.world, rand, l7, k17, j20);
//        }

        


        if (rand.nextInt(500) == 0) {
            k4 = k + rand.nextInt(16);
            l7 = rand.nextInt(128) + 64;
           k17 = l + rand.nextInt(16);
            (new WorldFeatureAetherDungeonSilver()).place(this.world, rand, k4, l7, k17);
        }

        if (rand.nextInt(5) == 0) {
            for (k4 = k; k4 < k + 16; ++k4) {
                for (l7 = l; l7 < l + 16; ++l7) {
                    for (k17 = 0; k17 < 192; ++k17) {
                        if (this.world.getBlockId(k4, k17, l7) == 0 && this.world.getBlockId(k4, k17 + 1, l7) == AetherBlocks.GRASS_AETHER.id() && this.world.getBlockId(k4, k17 + 2, l7) == 0) {
                            (new WorldFeatureQuicksoil(AetherBlocks.QUICKSOIL.id())).place(this.world, rand, k4, k17, l7);
                            k17 = 256;
                        }
                    }
                }
            }
        }

        d = 0.5;
        k4 = (int)((this.treeDensityNoise.get((double)k * d, (double)l * d) / 8.0 + rand.nextDouble() * 4.0 + 4.0) / 3.0);
        l7 = 0;
        if (rand.nextInt(10) == 0) {
            ++l7;
        }

        if (biome == Biomes.OVERWORLD_FOREST) {
            l7 += k4 + 5;
        }

        if (biome == Biomes.OVERWORLD_RAINFOREST) {
            l7 += k4 + 5;
        }

        if (biome == Biomes.OVERWORLD_SEASONAL_FOREST) {
            l7 += k4 + 2;
        }

        if (biome == Biomes.OVERWORLD_TAIGA) {
            l7 += k4 + 5;
        }

        if (biome == Biomes.OVERWORLD_DESERT) {
            l7 -= 20;
        }

        if (biome == Biomes.OVERWORLD_TUNDRA) {
            l7 -= 20;
        }

        if (biome == Biomes.OVERWORLD_PLAINS) {
            l7 -= 20;
        }

        l7 += k4;

        int l21;
        for (k17 = 0; k17 < l7; ++k17) {
            j20 = k + rand.nextInt(16) + 8;
            l21 = l + rand.nextInt(16) + 8;
            WorldFeature worldgenerator = rand.nextInt(100) == 0 ? new WorldFeatureTreeGoldenOak(AetherBlocks.LEAVES_OAK_GOLDEN.id(), AetherBlocks.LOG_OAK_GOLDEN.id()) : new WorldFeatureTreeAether(AetherBlocks.LEAVES_SKYROOT.id(), AetherBlocks.LOG_SKYROOT.id(), 4);;
            worldgenerator.init(1.0, 1.0, 1.0);
            worldgenerator.place(this.world, rand, j20, this.world.getHeightValue(j20, l21), l21);
        }

        for (k17 = 0; k17 < 50; ++k17) {
            j20 = k + rand.nextInt(16) + 8;
            l21 = rand.nextInt(rand.nextInt(248) + 8);
            int l22 = l + rand.nextInt(16) + 8;
            (new WorldFeatureLiquid(Blocks.FLUID_WATER_FLOWING.id())).place(this.world, rand, j20, l21, l22);
        }

        BlockLogicSand.fallInstantly = false;
    }
}
