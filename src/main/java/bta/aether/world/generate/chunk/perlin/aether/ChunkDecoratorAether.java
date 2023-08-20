package bta.aether.world.generate.chunk.perlin.aether;

import bta.aether.Aether;
import bta.aether.world.generate.feature.WorldFeatureClouds;
import bta.aether.world.generate.feature.WorldFeatureQuicksoil;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockSand;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureOre;
import net.minecraft.core.world.generate.feature.WorldFeatureTallGrass;
import net.minecraft.core.world.noise.PerlinNoise;

import java.util.Random;

public class ChunkDecoratorAether implements ChunkDecorator {
    private final World world;
    private final PerlinNoise treeDensityNoise;
    private final int treeDensityOverride;

    protected ChunkDecoratorAether(World world, int treeDensityOverride) {
        this.world = world;
        this.treeDensityOverride = treeDensityOverride;
        this.treeDensityNoise = new PerlinNoise(world.getRandomSeed(), 8, 74);
    }

    public ChunkDecoratorAether(World world) {
        this(world, -1);
    }

    @Override
    public void decorate(Chunk chunk) {
        int chunkX = chunk.xPosition;
        int chunkZ = chunk.zPosition;
        int x = chunkX * 16;
        int z = chunkZ * 16;
        BlockSand.fallInstantly = true;
        Random rand = new Random(this.world.getRandomSeed());
        long l1 = rand.nextLong() / 2L * 2L + 1L;
        long l2 = rand.nextLong() / 2L * 2L + 1L;
        rand.setSeed((long)chunkX * l1 + (long)chunkZ * l2 ^ this.world.getRandomSeed());
        int dx;
        int dy;
        int dz;

        dx = x + rand.nextInt(16);
        dy = rand.nextInt(128);
        dz = z + rand.nextInt(16);
        (new WorldFeatureClouds(16)).generate(this.world, rand, dx, dy, dz);

        if(rand.nextInt(5) == 0) {
            for (dx = x; dx < x + 16; dx++) {
                for (dy = 0; dy < 256; dy++) {
                    for (dz = z; dz < z + 16; dz++) {
                        if (world.getBlockId(dx, dy, dz) == 0 && world.getBlockId(dx, dy + 1, dz) == Aether.grassAether.id) {
                            (new WorldFeatureQuicksoil(Aether.quicksoil.id, 4)).generate(this.world, rand, dx, dy, dz);
                        }
                    }
                }
            }
        }
    }
}
