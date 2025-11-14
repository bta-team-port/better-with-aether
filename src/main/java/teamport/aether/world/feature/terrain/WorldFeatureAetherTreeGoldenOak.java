package teamport.aether.world.feature.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.blocks.AetherBlocks;

import java.util.Random;

public class WorldFeatureAetherTreeGoldenOak extends WorldFeature {
    public WorldFeatureAetherTreeGoldenOak() {}

    private void branch(World world, Random random, int i, int j, int k, int slant) {
        int directionX = random.nextInt(3) - 1;
        int directionZ = random.nextInt(3) - 1;

        for (int n = 0; n < random.nextInt(2) + 1; ++n) {
            i += directionX;
            j += slant;
            k += directionZ;
            if (world.getBlockId(i, j, k) == AetherBlocks.LEAVES_OAK_GOLDEN.id()) {
                world.setBlockAndMetadataWithNotify(i, j, k, AetherBlocks.LOG_OAK_GOLDEN.id(), 0);
            }
        }

    }

    @Override
    public boolean place(World world, Random random, int i, int j, int k) {
        if (world.getBlockId(i, j - 1, k) != AetherBlocks.GRASS_AETHER.id() &&
            world.getBlockId(i, j - 1, k) != AetherBlocks.DIRT_AETHER.id() &&
            world.getBlockId(i, j - 1, k) != Blocks.GRASS.id() &&
            world.getBlockId(i, j - 1, k) != Blocks.GRASS_RETRO.id() &&
            world.getBlockId(i, j - 1, k) != Blocks.DIRT.id() &&
            world.getBlockId(i, j - 1, k) != Blocks.GRASS_SCORCHED.id() &&
            world.getBlockId(i, j - 1, k) != Blocks.DIRT_SCORCHED.id()) {
            return false;
        } else {
            int height = random.nextInt(5) + 6;
            onTreeGrown(world, i, j, k);
            int x;
            for (x = i - 3; x < i + 4; ++x) {
                for (int y = j + 5; y < j + 12; ++y) {
                    for (int z = k - 3; z < k + 4; ++z) {
                        if ((x - i) * (x - i) + (y - j - 8) * (y - j - 8) + (z - k) * (z - k) < 12 + random.nextInt(5) && world.getBlockId(x, y, z) == 0) {
                            world.setBlockWithNotify(x, y, z, AetherBlocks.LEAVES_OAK_GOLDEN.id());
                        }
                    }
                }
            }

            for (x = 0; x < height; ++x) {
                if (x > 4 && random.nextInt(3) > 0) {
                    this.branch(world, random, i, j + x, k, x / 4 - 1);
                }

                world.setBlockAndMetadataWithNotify(i, j + x, k, AetherBlocks.LOG_OAK_GOLDEN.id(), 0);
            }

            return true;
        }
    }

    private static void onTreeGrown(World world, int x, int y, int z) {
        Block<?> dirt = getDirtForGrass(world.getBlockId(x, y - 1, z));
        if (dirt != null) {
            world.setBlockWithNotify(x, y - 1, z, dirt.id());
        }

    }

    @SuppressWarnings("java:S3358")
    private static Block<?> getDirtForGrass(int id) {
        if (id != Blocks.GRASS.id() && id != Blocks.GRASS_RETRO.id()) {
            return id == Blocks.GRASS_SCORCHED.id() ? Blocks.DIRT_SCORCHED : id == AetherBlocks.GRASS_AETHER.id() ? AetherBlocks.DIRT_AETHER : null;
        } else {
            return Blocks.DIRT;
        }
    }
}
