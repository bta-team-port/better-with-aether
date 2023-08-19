package bta.aether.world.generate.chunk.perlin.aether;

import bta.aether.Aether;
import bta.aether.world.generate.feature.WorldFeatureClouds;
import net.minecraft.core.block.BlockSand;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
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
        int l7;
        int k17;
        int k4;

        if (rand.nextInt(50) == 0) {
            k4 = x + rand.nextInt(16);
            l7 = rand.nextInt(32) + 96;
            k17 = z + rand.nextInt(16);
            (new WorldFeatureClouds(Aether.aercloudGold.id, 4, false)).generate(this.world, rand, k4, l7, k17);
        }
        if (rand.nextInt(50) == 0) {
            k4 = x + rand.nextInt(16);
            l7 = rand.nextInt(64) + 32;
            k17 = z + rand.nextInt(16);
            (new WorldFeatureClouds(Aether.aercloudBlue.id, 8, false)).generate(this.world, rand, k4, l7, k17);
        }
        if (rand.nextInt(50) == 0) {
            k4 = x + rand.nextInt(16);
            l7 = rand.nextInt(64) + 32;
            k17 = z + rand.nextInt(16);
            (new WorldFeatureClouds(Aether.aercloudWhite.id, 16, false)).generate(this.world, rand, k4, l7, k17);
        }
        if (rand.nextInt(50) == 0) {
            k4 = x + rand.nextInt(16);
            l7 = rand.nextInt(32);
            k17 = z + rand.nextInt(16);
            (new WorldFeatureClouds(Aether.aercloudWhite.id, 64, false)).generate(this.world, rand, k4, l7, k17);
        }
    }
}
