package teamport.aether.world.feature.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.terrain.BlockLogicOreGravitite;

import java.util.Random;

public class WorldFeatureAetherTree extends WorldFeature {
    private final int leavesId;
    private final int logId;
    private final int heightMod;

    public WorldFeatureAetherTree(int leavesId, int logId, int heightMod) {
        this.leavesId = leavesId;
        this.logId = logId;
        this.heightMod = heightMod;
    }

    @Override
    public boolean place(World world, Random random, int x, int y, int z) {
        int treeHeight = random.nextInt(3) + this.heightMod;
        boolean canSpawn = true;
        if (y >= 1 && y + treeHeight + 1 <= world.getHeightBlocks()) {
            int iy;
            int l1;
            int j2;
            int i3;
            int ix;
            for (iy = y; iy <= y + 1 + treeHeight; ++iy) {
                l1 = 1;
                if (iy == y) {
                    l1 = 0;
                }

                if (iy >= y + 1 + treeHeight - 2) {
                    l1 = 2;
                }

                for (j2 = x - l1; j2 <= x + l1 && canSpawn; ++j2) {
                    for (i3 = z - l1; i3 <= z + l1 && canSpawn; ++i3) {
                        if (iy >= 0 && iy < world.getHeightBlocks()) {
                            ix = world.getBlockId(j2, iy, i3);
                            if (ix != 0 && ix != this.leavesId) {
                                canSpawn = false;
                            }
                        } else {
                            canSpawn = false;
                        }
                    }
                }
            }

            if (!canSpawn) {
                return false;
            } else {
                iy = world.getBlockId(x, y - 1, z);
                if (Blocks.hasTag(iy, BlockTags.GROWS_TREES) || (Blocks.hasTag(iy, AetherBlockTags.GROWS_AETHER_TREES) && y < world.getHeightBlocks() - treeHeight - 1)) {
                    onTreeGrown(world, x, y, z);

                    for (l1 = y - 3 + treeHeight; l1 <= y + treeHeight; ++l1) {
                        j2 = l1 - (y + treeHeight);
                        i3 = 1 - j2 / 2;

                        for (ix = x - i3; ix <= x + i3; ++ix) {
                            int l3 = ix - x;

                            for (int iz = z - i3; iz <= z + i3; ++iz) {
                                int j4 = iz - z;
                                if ((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && canLeavesReplace(world, ix, l1, iz)) {
                                    this.placeLeaves(world, ix, l1, iz);
                                }
                            }
                        }
                    }

                    for (l1 = 0; l1 < treeHeight; ++l1) {
                        j2 = world.getBlockId(x, y + l1, z);
                        if (j2 == 0 || this.isLeaf(j2)) {
                            world.setBlockWithNotify(x, y + l1, z, this.logId);
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public void placeLeaves(World world, int x, int y, int z) {
        world.setBlockWithNotify(x, y, z, this.leavesId);
    }

    public boolean isLeaf(int id) {
        return id == this.leavesId;
    }

    public static void onTreeGrown(World world, int x, int y, int z) {
        Block<?> dirt = getDirtForGrass(world.getBlockId(x, y - 1, z));
        if (dirt != null) {
            world.setBlockWithNotify(x, y - 1, z, dirt.id());
        }

    }

    public static Block<?> getDirtForGrass(int id) {
        if (id != Blocks.GRASS.id() && id != Blocks.GRASS_RETRO.id()) {
            return id == Blocks.GRASS_SCORCHED.id() ? Blocks.DIRT_SCORCHED : id == AetherBlocks.GRASS_AETHER.id() ? AetherBlocks.DIRT_AETHER : null;
        } else {
            return Blocks.DIRT;
        }
    }

    public static boolean canLeavesReplace(World world, int x, int y, int z) {
        return BlockLogicOreGravitite.canFallAbove(world, x, y, z);
    }
}
