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

        if(rand.nextInt(5) == 0) {
            for (dx = x; dx < x + 16; dx++) {
                for (dy = 0; dy < 256; dy++) {
                    for (dz = z; dz < z + 16; dz++) {
                        if (world.getBlockId(dx, dy, dz) == 0 && world.getBlockId(dx, dy + 1, dz) == AetherBlocks.grassAether.id) {
                            new WorldFeatureQuicksoil(AetherBlocks.quicksoil.id, 4).generate(world, rand, dx, dy, dz);
                        }
                    }
                }
            }
        }
        return null;
    }

    private static final int[] cloudIDs = {AetherBlocks.aercloudWhite.id, AetherBlocks.aercloudBlue.id, AetherBlocks.aercloudGold.id};

    public static Void generateClouds(Parameters parameters) {
        int x = parameters.chunk.xPosition * 16;
        int z = parameters.chunk.zPosition * 16;
        World world = parameters.chunk.world;
        Random rand = new Random();
        int dx = x + rand.nextInt(16);
        int dy = rand.nextInt(256);
        int dz = z + rand.nextInt(16);

        int cloudSize = 10 + rand.nextInt(16);
        if (rand.nextInt(15) == 0 && world.getBlockId(dx, dy, dz) == 0)
            (new WorldFeatureClouds(cloudSize, cloudIDs[rand.nextInt(cloudIDs.length)])).generate(world, rand, dx, dy, dz);
        return null;
    }
}
