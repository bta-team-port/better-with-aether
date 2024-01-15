package bta.aether.compat.terrainapi;

import bta.aether.block.AetherBlocks;
import bta.aether.world.generate.feature.WorldFeatureClouds;
import bta.aether.world.generate.feature.WorldFeatureQuicksoil;
import net.minecraft.core.world.World;
import useless.terrainapi.generation.Parameters;

import java.util.Random;

public class AetherFunctions {

    public static int getTreeDensity(Parameters parameters) {
        TerrainAetherConfig aetherConfig = ChunkDecoratorAetherAPI.aetherConfig;
        ChunkDecoratorAetherAPI decorator = (ChunkDecoratorAetherAPI)parameters.decorator;

        Integer treeDensity = aetherConfig.getTreeDensity(parameters.biome);
        if (decorator.treeDensityOverride != -1) {
            return decorator.treeDensityOverride;
        } else if (treeDensity != null && treeDensity == -1000) {
            return 0;
        } else {
            int x = parameters.chunk.xPosition * 16;
            int z = parameters.chunk.zPosition * 16;
            double d = 0.5;
            int noiseValue = (int)((decorator.treeDensityNoise.get((double)x * d, (double)z * d) / 8.0 + parameters.random.nextDouble() * 4.0 + 4.0) / 3.0);
            int treeDensityOffset = 0;
            if (parameters.random.nextInt(10) == 0) {
                ++treeDensityOffset;
            }

            return treeDensity == null ? treeDensityOffset : treeDensity + noiseValue + treeDensityOffset;
        }
    }

    public static Void generateQuickSoil(Parameters parameters) {

        int x = parameters.chunk.xPosition * 16;
        int z = parameters.chunk.zPosition * 16;
        World world = parameters.chunk.world;
        Random rand = new Random();
        int dx;
        int dy;
        int dz;

        if(rand.nextInt(3) == 0) {
            for (dx = x; dx < x + 16; dx++) {
                for (dy = 0; dy < 256; dy++) {
                    for (dz = z; dz < z + 16; dz++) {
                        if (world.getBlockId(dx, dy, dz) == 0 && world.getBlockId(dx, dy + 1, dz) == AetherBlocks.grassAether.id) {
                            new WorldFeatureQuicksoil(AetherBlocks.quicksoil.id, 3).generate(world, rand, dx, dy, dz);
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Void generateClouds(Parameters parameters) {
        Random rand = new Random();
        World world = parameters.chunk.world;
        int x = parameters.chunk.xPosition * 16;
        int z = parameters.chunk.zPosition * 16;

        // Normal clouds
        int dx = x + rand.nextInt(16);
        int dy = 20 + rand.nextInt(200);
        int dz = z + rand.nextInt(16);

        int cloudID = 0;
        int choice = rand.nextInt(20);
        if (choice == 0)  cloudID = AetherBlocks.aercloudGold.id;
        if (choice > 15)  cloudID = AetherBlocks.aercloudBlue.id;
        if (choice >= 1 && choice <= 15)  cloudID = AetherBlocks.aercloudWhite.id;


        int cloudSize = 6 + rand.nextInt(10);
        if (rand.nextInt(4) == 0) {
            (new WorldFeatureClouds(cloudSize, cloudID, false)).generate(world, rand, dx, dy, dz);
        }

        // Yellow clouds
        dx = x + rand.nextInt(16);
        dy = 210 + rand.nextInt(30);
        dz = z + rand.nextInt(16);

        cloudSize = 6 + rand.nextInt(10);
        if (rand.nextInt(15) == 0) {
            (new WorldFeatureClouds(cloudSize, AetherBlocks.aercloudGold.id, false)).generate(world, rand, dx, dy, dz);
        }

        // Flat clouds
        dx = x + rand.nextInt(16);
        dy = 10 + rand.nextInt(20);
        dz = z + rand.nextInt(16);

        cloudSize = 10 + rand.nextInt(20);
        if (rand.nextInt(30) == 0) {
            (new WorldFeatureClouds(cloudSize, AetherBlocks.aercloudWhite.id, true)).generate(world, rand, dx, dy, dz);
        }

        return null;
    }
}
