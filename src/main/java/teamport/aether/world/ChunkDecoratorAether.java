package teamport.aether.world;

import net.minecraft.core.block.BlockLogicSand;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import net.minecraft.core.world.generate.feature.*;
import net.minecraft.core.world.noise.PerlinNoise;
import teamport.aether.AetherMod;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.BlockLogicOreAmbrosium;
import teamport.aether.blocks.BlockLogicOreGravitite;
import teamport.aether.blocks.BlockLogicOreZanite;
import teamport.aether.noise.Worley;
import teamport.aether.world.generate.feature.*;

import java.util.Random;

public class ChunkDecoratorAether implements ChunkDecorator {
    public final World world;
    public final PerlinNoise treeDensityNoise;
    public static int gumCount;

    public ChunkDecoratorAether(World world) {
        this.world = world;
        this.treeDensityNoise = new PerlinNoise(world.getRandomSeed(), 8, 74);
    }

    public static final WorldFeatureAetherDungeonBase[] dungeons = new WorldFeatureAetherDungeonBase[]{
            new WorldFeatureAetherDungeonGold(),
            new WorldFeatureAetherDungeonSilver(),
            new WorldFeatureAetherDungeonBronze()
    };

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

        int k7;
        int k4;
        int treeDensity;

        long worldSeed = this.world.getRandomSeed();
        int transformedSeed = Worley.mix((int) (worldSeed >>> 32), (int) (worldSeed & 0xFFFFFFFFL), 0);
        int dungeonType = Worley.isSeed(chunkX, chunkZ, 16, transformedSeed, 3);
        //System.out.println(transformedSeed);
        if (dungeonType > -1) {
            int dungeonX = x + rand.nextInt(16);
            int dungeonZ = z + rand.nextInt(16);
            if (dungeons[dungeonType].place(this.world, rand, dungeonX, 128, dungeonZ)) {
                AetherMod.LOGGER.info("/teleport " + dungeonX + " " + 128 + " " + dungeonZ);
            }else {
                AetherMod.LOGGER.info("Failed " + dungeonX + " " + 128 + " " + dungeonZ);
            }
        }

        int j4;
        if (rand.nextInt(3) == 0) {
            j4 = x + rand.nextInt(16) + 8;
            k7 = rand.nextInt(256);
            k4 = z + rand.nextInt(16) + 8;
            (new WorldFeatureLake(Blocks.FLUID_WATER_STILL.id())).place(this.world, rand, j4, k7, k4);
        }

        for (j4 = 0; j4 < 20; ++j4) {
            k7 = x + rand.nextInt(16);
            k4 = rand.nextInt(256);
            treeDensity = z + rand.nextInt(16);
            (new WorldFeatureOreAether(AetherBlocks.DIRT_AETHER.id(), 32)).place(this.world, rand, k7, k4, treeDensity);
        }

        for (j4 = 0; j4 < 2; ++j4) {
            k7 = x + rand.nextInt(16) + 8;
            k4 = rand.nextInt(256);
            treeDensity = z + rand.nextInt(16) + 8;
            (new WorldFeatureFlowers(AetherBlocks.FLOWER_WHITE.id(), 64, true)).place(this.world, rand, k7, k4, treeDensity);
        }

        for (j4 = 0; j4 < 2; ++j4) {
            k7 = x + rand.nextInt(16) + 8;
            k4 = rand.nextInt(256);
            treeDensity = z + rand.nextInt(16) + 8;
            (new WorldFeatureTallGrass(AetherBlocks.TALLGRASS_AETHER.id())).place(this.world, rand, k7, k4, treeDensity);
        }

        for (j4 = 0; j4 < 2; ++j4) {
            if (rand.nextInt(2) == 0) {
                k7 = x + rand.nextInt(16) + 8;
                k4 = rand.nextInt(256);
                treeDensity = z + rand.nextInt(16) + 8;
                (new WorldFeatureFlowers(AetherBlocks.FLOWER_PURPLE.id(), 64, true)).place(this.world, rand, k7, k4, treeDensity);
            }
        }

        for (j4 = 0; j4 < 20; ++j4) {
            k7 = x + rand.nextInt(16);
            k4 = rand.nextInt(256);
            treeDensity = z + rand.nextInt(16);
            (new WorldFeatureOreAether(AetherBlocks.ICESTONE.id(), 32)).place(this.world, rand, k7, k4, treeDensity);
        }

        for (j4 = 0; j4 < 20; ++j4) {
            k7 = x + rand.nextInt(16);
            k4 = rand.nextInt(256);
            treeDensity = z + rand.nextInt(16);
            (new WorldFeatureOreAether(BlockLogicOreAmbrosium.variantMap, 16)).place(this.world, rand, k7, k4, treeDensity);
        }

        for (j4 = 0; j4 < 15; ++j4) {
            k7 = x + rand.nextInt(16);
            k4 = rand.nextInt(192);
            treeDensity = z + rand.nextInt(16);
            (new WorldFeatureOreAether(BlockLogicOreZanite.variantMap, 8)).place(this.world, rand, k7, k4, treeDensity);
        }

        for (j4 = 0; j4 < 8; ++j4) {
            k7 = x + rand.nextInt(16);
            k4 = rand.nextInt(128);
            treeDensity = z + rand.nextInt(16);
            (new WorldFeatureOreAether(BlockLogicOreGravitite.variantMap, 7)).place(this.world, rand, k7, k4, treeDensity);
        }

        if (rand.nextInt(12) == 0) {
            j4 = x + rand.nextInt(16);
            k7 = rand.nextInt(32) + 224;
            k4 = z + rand.nextInt(16);
            (new WorldFeatureClouds(AetherBlocks.AERCLOUD_GOLD.id(), 4, false)).place(this.world, rand, j4, k7, k4);
        }

        if (rand.nextInt(12) == 0) {
            j4 = x + rand.nextInt(16);
            k7 = rand.nextInt(64) + 128;
            k4 = z + rand.nextInt(16);
            (new WorldFeatureClouds(AetherBlocks.AERCLOUD_BLUE.id(), 8, false)).place(this.world, rand, j4, k7, k4);
        }

        if (rand.nextInt(6) == 0) {
            j4 = x + rand.nextInt(16);
            k7 = rand.nextInt(192) + 32;
            k4 = z + rand.nextInt(16);
            (new WorldFeatureClouds(AetherBlocks.AERCLOUD_WHITE.id(), 16, false)).place(this.world, rand, j4, k7, k4);
        }

        if (rand.nextInt(18) == 0) {
            j4 = x + rand.nextInt(16);
            k7 = rand.nextInt(64) + 8;
            k4 = z + rand.nextInt(16);
            (new WorldFeatureClouds(AetherBlocks.AERCLOUD_WHITE.id(), 64, true)).place(this.world, rand, j4, k7, k4);
        }

        //TODO remove
        /*for (j4 = 0; j4 < 2; ++j4) {
            k7 = x + rand.nextInt(16);
            k4 = 32 + rand.nextInt(64);
            treeDensity = z + rand.nextInt(16);
            (new WorldFeatureAetherDungeonBronze()).place(this.world, rand, k7, k4, treeDensity);
        }

        if (rand.nextInt(750) == 0) {
            j4 = x + rand.nextInt(16);
            k7 = rand.nextInt(128) + 64;
           k4 = z + rand.nextInt(16);
            (new WorldFeatureAetherDungeonSilver()).place(this.world, rand, j4, k7, k4);
        }*/

        if (rand.nextInt(5) == 0) {
            for (j4 = x; j4 < x + 16; ++j4) {
                for (k7 = z; k7 < z + 16; ++k7) {
                    for (k4 = 0; k4 < 192; ++k4) {
                        if (this.world.getBlockId(j4, k4, k7) == 0 && this.world.getBlockId(j4, k4 + 1, k7) == AetherBlocks.GRASS_AETHER.id() && this.world.getBlockId(j4, k4 + 2, k7) == 0) {
                            (new WorldFeatureQuicksoil(AetherBlocks.QUICKSOIL.id())).place(this.world, rand, j4, k4, k7);
                            k4 = 256;
                        }
                    }
                }
            }
        }

        k7 = 0;
        if (rand.nextInt(10) == 0) {
            ++k7;
        }

        int l21;
        for (k4 = 0; k4 < 2; ++k4) {
            treeDensity = x + rand.nextInt(16) + 8;
            l21 = z + rand.nextInt(16) + 8;
            WorldFeature worldFeature = rand.nextInt(18) == 0 ? new WorldFeatureTreeGoldenOak(AetherBlocks.LEAVES_OAK_GOLDEN.id(), AetherBlocks.LOG_OAK_GOLDEN.id()) : new WorldFeatureTreeAether(AetherBlocks.LEAVES_SKYROOT.id(), AetherBlocks.LOG_SKYROOT.id(), 4);;
            worldFeature.init(1.0, 1.0, 1.0);
            worldFeature.place(this.world, rand, treeDensity, this.world.getHeightValue(treeDensity, l21), l21);
        }

        for (k4 = 0; k4 < 50; ++k4) {
            treeDensity = x + rand.nextInt(16) + 8;
            l21 = rand.nextInt(rand.nextInt(248) + 8);
            int l22 = z + rand.nextInt(16) + 8;
            (new WorldFeatureLiquidAether(Blocks.FLUID_WATER_FLOWING.id())).place(this.world, rand, treeDensity, l21, l22);
        }

        BlockLogicSand.fallInstantly = false;
    }
}
