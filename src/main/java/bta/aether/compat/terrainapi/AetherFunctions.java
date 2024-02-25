package bta.aether.compat.terrainapi;

import bta.aether.block.AetherBlocks;
import bta.aether.world.generate.feature.WorldFeatureClouds;
import bta.aether.world.generate.feature.WorldFeatureQuicksoil;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureLake;
import org.jetbrains.annotations.Nullable;
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
            treeDensity = treeDensity == null ? treeDensityOffset : treeDensity + noiseValue + treeDensityOffset;
            return (int) (treeDensity * parameters.chunk.humidity[0]);
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

        if(rand.nextInt(8) == 0) {
            for (dx = x; dx < x + 16; dx++) {
                for (dy = 0; dy < 256; dy++) {
                    for (dz = z; dz < z + 16; dz++) {
                        if (world.getBlockId(dx, dy, dz) == 0 && world.getBlockId(dx, dy + 1, dz) == AetherBlocks.grassAether.id && world.getBlockId(dx, dy - 1, dz) != Block.fluidWaterStill.id) {
                            new WorldFeatureQuicksoil(AetherBlocks.quicksoil.id, 3).generate(world, rand, dx, dy, dz);
                        }
                    }
                }
            }
        }
        return null;
    }
    public static WorldFeature getNormalClouds(Parameters parameters){
        Random rand = parameters.random;

        int cloudID;
        if (rand.nextInt(100) > 70) {
            cloudID = AetherBlocks.aercloudBlue.id;
        } else {
            cloudID = AetherBlocks.aercloudWhite.id;
        }

        return new WorldFeatureClouds(6 + rand.nextInt(10), cloudID, false);
    }
    public static WorldFeature getYellowClouds(Parameters parameters){
        return new WorldFeatureClouds(3 + parameters.random.nextInt(5), AetherBlocks.aercloudGold.id, false);
    }
    public static WorldFeature getFlatClouds(Parameters parameters){
        return new WorldFeatureClouds(20 + parameters.random.nextInt(25), AetherBlocks.aercloudWhite.id, true);
    }
    @Nullable
    public static Void generateLakeFeature(Parameters parameters) {
        int lakeChance = ChunkDecoratorAetherAPI.aetherConfig.getLakeDensity(parameters.biome, ChunkDecoratorAetherAPI.aetherConfig.defaultLakeDensity);
        int x = parameters.chunk.xPosition * 16;
        int z = parameters.chunk.zPosition * 16;
        if (lakeChance != 0 && parameters.random.nextInt(lakeChance) == 0) {
            int fluid = Block.fluidWaterStill.id;
            if (parameters.biome.hasSurfaceSnow()) {
                fluid = Block.ice.id;
            }

            int xf = x + parameters.random.nextInt(16) + 8;
            int yf = parameters.decorator.minY + parameters.random.nextInt(parameters.decorator.rangeY);
            int zf = z + parameters.random.nextInt(16) + 8;
            new WorldFeatureLake(fluid).generate(parameters.decorator.world, parameters.random, xf, yf, zf);
        }

        return null;
    }
}
