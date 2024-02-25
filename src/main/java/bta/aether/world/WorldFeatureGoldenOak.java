package bta.aether.world;

import bta.aether.block.AetherBlocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureGoldenOak extends WorldFeature {

    public WorldFeatureGoldenOak(int leavesID, int logID) {
        this(leavesID, logID, 0);
    }

    public WorldFeatureGoldenOak(int leavesID, int logID, int heightMod) {
    }
    public void branch(World world, Random random, int i, int j, int k, int slant) {
        int directionX = random.nextInt(3) - 1;
        int directionZ = random.nextInt(3) - 1;

        for(int n = 0; n < random.nextInt(2) + 1; ++n) {
            i += directionX;
            j += slant;
            k += directionZ;
            if (world.getBlockId(i, j, k) == AetherBlocks.leavesOakGolden.id) {
                world.setBlock(i, j, k, AetherBlocks.logOakGolden.id);
            }
        }

    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        if (world.getBlockId(i, j - 1, k) != AetherBlocks.grassAether.id && world.getBlockId(i, j - 1, k) != AetherBlocks.dirtAether.id) {
            return false;
        } else {
            int heightMod = random.nextInt(5) + 6;

            int x;
            for(x = i - 3; x < i + 4; ++x) {
                for(int y = j + 5; y < j + 12; ++y) {
                    for(int z = k - 3; z < k + 4; ++z) {
                        if ((x - i) * (x - i) + (y - j - 8) * (y - j - 8) + (z - k) * (z - k) < 12 + random.nextInt(5) && world.getBlockId(x, y, z) == 0) {
                            world.setBlock(x, y, z, AetherBlocks.leavesOakGolden.id);
                        }
                    }
                }
            }

            for(x = 0; x < heightMod; ++x) {
                if (x > 4 && random.nextInt(3) > 0) {
                    branch(world, random, i, j + x, k, x / 4 - 1);
                }

                world.setBlock(i, j + x, k, AetherBlocks.logOakGolden.id);
            }

            return true;
        }
    }
}
