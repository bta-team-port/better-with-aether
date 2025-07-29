package teamport.aether.world;

import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.block.BlockLogicSand;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import net.minecraft.core.world.generate.feature.*;
import net.minecraft.core.world.noise.PerlinNoise;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.BlockLogicOreAmbrosium;
import teamport.aether.blocks.BlockLogicOreGravitite;
import teamport.aether.blocks.BlockLogicOreZanite;

import java.util.Random;

public class ChunkDecoratorAether implements ChunkDecorator {
    private final World world;
    private final PerlinNoise treeDensityNoise;

    protected ChunkDecoratorAether(World world) {
        this.world = world;
        this.treeDensityNoise = new PerlinNoise(world.getRandomSeed(), 8, 74);
    }

    @Override
    public void decorate(Chunk chunk) {
        this.world.scheduledUpdatesAreImmediate = true;
        int chunkX = chunk.xPosition;
        int chunkZ = chunk.zPosition;
        int minY = this.world.getWorldType().getMinY();
        int maxY = this.world.getWorldType().getMaxY();
        int rangeY = maxY + 1 - minY;
        float oreHeightModifier = (float)rangeY / 128.0F;
        BlockLogicSand.fallInstantly = true;
        int x = chunkX * 16;
        int z = chunkZ * 16;
        int y = this.world.getHeightValue(x + 16, z + 16);
        Biome biome = this.world.getBlockBiome(x + 16, y, z + 16);
        Random rand = new Random(this.world.getRandomSeed());
        long l1 = rand.nextLong() / 2L * 2L + 1L;
        long l2 = rand.nextLong() / 2L * 2L + 1L;
        rand.setSeed((long)chunkX * l1 + (long)chunkZ * l2 ^ this.world.getRandomSeed());
        int j4;
        int k7;
        int k4;
        int i11;
        int i12;
        int l14;
        int l15;
        int i14;

        int lakeChance = 4;

        int treeDensity;
        if (rand.nextInt(lakeChance) == 0) {
            j4 = Blocks.FLUID_WATER_STILL.id();

            k7 = x + rand.nextInt(16) + 8;
            k4 = minY + rand.nextInt(rangeY);
            treeDensity = z + rand.nextInt(16) + 8;
            (new WorldFeatureLake(j4)).place(this.world, rand, k7, k4, treeDensity);
        }

        for(j4 = 0; (float)j4 < 20.0F * oreHeightModifier; ++j4) {
            k7 = x + rand.nextInt(16);
            k4 = minY + rand.nextInt(rangeY);
            treeDensity = z + rand.nextInt(16);
            (new WorldFeatureOre(AetherBlocks.DIRT_AETHER.id(), 32)).place(this.world, rand, k7, k4, treeDensity);
        }

        for(j4 = 0; (float)j4 < 10.0F * oreHeightModifier; ++j4) {
            k7 = x + rand.nextInt(16);
            k4 = minY + rand.nextInt(rangeY);
            treeDensity = z + rand.nextInt(16);
            (new WorldFeatureOre(AetherBlocks.ICESTONE.id(), 32)).place(this.world, rand, k7, k4, treeDensity);
        }

        for(j4 = 0; (float)j4 < 20.0F * oreHeightModifier; ++j4) {
            k7 = x + rand.nextInt(16);
            k4 = minY + rand.nextInt(rangeY);
            treeDensity = z + rand.nextInt(16);
            (new WorldFeatureOre(BlockLogicOreAmbrosium.variantMap, 16)).place(this.world, rand, k7, k4, treeDensity);
        }

        for(j4 = 0; (float)j4 < 20.0F * oreHeightModifier; ++j4) {
            k7 = x + rand.nextInt(16);
            k4 = minY + rand.nextInt(rangeY / 2);
            treeDensity = z + rand.nextInt(16);
            (new WorldFeatureOre(BlockLogicOreZanite.variantMap, 8)).place(this.world, rand, k7, k4, treeDensity);
        }

        for(j4 = 0; (float)j4 < oreHeightModifier; ++j4) {
            k7 = x + rand.nextInt(16);
            k4 = minY + rand.nextInt(rangeY / 8);
            treeDensity = z + rand.nextInt(16);
            (new WorldFeatureOre(BlockLogicOreGravitite.variantMap, 7)).place(this.world, rand, k7, k4, treeDensity);
        }

        double d = 0.5;
        k4 = (int)((this.treeDensityNoise.get((double)x * d, (double)z * d) / 8.0 + rand.nextDouble() * 4.0 + 4.0) / 3.0);
        treeDensity = 0;
        if (rand.nextInt(10) == 0) {
            ++treeDensity;
        }

        if (biome == AetherDimension.AETHER_AETHER) {
            treeDensity += k4 + 5;
        }

        try {
            BlockLogicLeavesBase.enableDecay = false;

            for(i11 = 0; i11 < treeDensity; ++i11) {
                i11 = x + rand.nextInt(16) + 8;
                l14 = z + rand.nextInt(16) + 8;
                WorldFeature feature = biome.getRandomWorldGenForTrees(rand);
                feature.init(1.0, 1.0, 1.0);
                feature.place(this.world, rand, i11, this.world.getHeightValue(i11, l14), l14);
            }

        } finally {
            BlockLogicLeavesBase.enableDecay = true;
        }

        int k16;
        int oceanY;

        byte byte0 = 0;
        if (biome == AetherDimension.AETHER_AETHER) {
            byte0 = 2;
        }

        int dx;
        for(i14 = 0; i14 < byte0; ++i14) {
            k16 = x + rand.nextInt(16) + 8;
            oceanY = minY + rand.nextInt(rangeY);
            dx = z + rand.nextInt(16) + 8;
            (new WorldFeatureFlowers(AetherBlocks.FLOWER_WHITE.id(), 64, true)).place(this.world, rand, k16, oceanY, dx);
        }

        byte byte1 = 0;
        if (biome == AetherDimension.AETHER_AETHER) {
            byte1 = 2;
        }

        int dz;
        int dy;
        for(k16 = 0; k16 < byte1; ++k16) {
            oceanY = AetherBlocks.TALLGRASS_AETHER.id();

            dx = x + rand.nextInt(16) + 8;
            dz = minY + rand.nextInt(rangeY);
            dy = z + rand.nextInt(16) + 8;
            (new WorldFeatureTallGrass(oceanY)).place(this.world, rand, dx, dz, dy);
        }


        if (rand.nextInt(2) == 0) {
            k16 = x + rand.nextInt(16) + 8;
            oceanY = minY + rand.nextInt(rangeY);
            dx = z + rand.nextInt(16) + 8;
            (new WorldFeatureFlowers(AetherBlocks.FLOWER_PURPLE.id(), 64, true)).place(this.world, rand, k16, oceanY, dx);
        }


        for(oceanY = 0; oceanY < 50; ++oceanY) {
            dx = x + rand.nextInt(16) + 8;
            dz = minY + rand.nextInt(rand.nextInt(rangeY - rangeY / 16) + rangeY / 16);
            dy = z + rand.nextInt(16) + 8;
            (new WorldFeatureLiquid(Blocks.FLUID_WATER_FLOWING.id())).place(this.world, rand, dx, dz, dy);
        }

        BlockLogicSand.fallInstantly = false;
        this.world.scheduledUpdatesAreImmediate = false;
    }
}
