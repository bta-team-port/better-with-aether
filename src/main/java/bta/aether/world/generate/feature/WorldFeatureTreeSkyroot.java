package bta.aether.world.generate.feature;

import bta.aether.block.AetherBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureTreeSkyroot extends WorldFeature {
    int heightMod;

    public WorldFeatureTreeSkyroot(int heightMod) {
        this.heightMod = heightMod;
    }

    private void placeLeaves(World world, int x, int y, int z, Random rand) {
        world.setBlockWithNotify(x, y, z, AetherBlocks.leavesSkyroot.id);
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        if (y > 0 && y < world.getHeightBlocks()) {
            if (world.getBlock(x, y - 1, z) == AetherBlocks.grassAether || world.getBlock(x, y - 1, z) == AetherBlocks.dirtAether) {
                int treeHeight = random.nextInt(3) + this.heightMod;

                for(int k1 = y - 3 + treeHeight; k1 <= y + treeHeight; ++k1) {
                    int j2 = k1 - (y + treeHeight);
                    int i3 = 1 - j2 / 2;

                    for(int k3 = x - i3; k3 <= x + i3; ++k3) {
                        int l3 = k3 - x;

                        for(int i4 = z - i3; i4 <= z + i3; ++i4) {
                            int j4 = i4 - z;
                            if ((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0)
                                    && !Block.solid[world.getBlockId(k3, k1, i4)]) {
                                this.placeLeaves(world, k3, k1, i4, random);
                            }
                        }
                    }
                }

                for(int logHeight = 0; logHeight < treeHeight; ++logHeight) {
                    int id = world.getBlockId(x, y + logHeight, z);
                    if (id == 0 ||id == AetherBlocks.leavesSkyroot.id) {
                        world.setBlockWithNotify(x, y + logHeight, z, AetherBlocks.logSkyroot.id);
                    }
                }

                return true;
            }
        }

        return false;
    }
}
