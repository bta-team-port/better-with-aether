package teamport.aether.world.feature.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class WorldFeatureSkyrootTree extends WorldFeatureAetherTree implements WorldFeatureInterface {
    Direction[] directions = new Direction[]{Direction.NONE, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    public WorldFeatureSkyrootTree(Block<?> leaves, Block<?> log, int heightMod) {
        super(leaves, log, heightMod);
    }

    protected int setTreeHeight(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc){
        return random.nextInt(3) + this.heightMod;
    }

    @Override
    public boolean placeFeature(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc) {
        this.changeDirtBlockBelow(world, new TilePos(tilePosc).down());
        this.placeLeaves(world, random, tilePosc);
        this.placeTrunk(world, random, tilePosc);
        return true;
    }

    @Override
    public boolean canPlace(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc) {
        return this.canPlaceTree(world, tilePosc) && this.canPlace(world, tilePosc);
    }

    private void placeLeaves(@NotNull World world, @NotNull Random random, TilePosc tilePosc) {
        int x = tilePosc.x();
        int y = tilePosc.y();
        int z = tilePosc.z();
        for (int leafY = y - 3 + this.treeHeight; leafY <= y + treeHeight; ++leafY) {
            int relativeY = leafY - (y + this.treeHeight);
            int leafRadius = 1 - relativeY / 2;
            for (int leafX = x - leafRadius; leafX <= x + leafRadius; ++leafX) {
                int relativeX = leafX - x;
                for (int leafZ = z - leafRadius; leafZ <= z + leafRadius; ++leafZ) {
                    int relativeZ = leafZ - z;
                    TilePos tilePos = new TilePos(leafX, leafY, leafZ);
                    Block<?> block = world.getBlockType(tilePos);
                    if (this.canPlaceLeave(block, random, leafRadius, relativeX, relativeY, relativeZ)) {
                        continue;
                    }
                    world.setBlockTypeNotify(tilePos, this.leaves);
                }
            }
        }
    }

    private boolean canPlace(@NotNull World world, TilePosc tilePosc){
        for (int yOff = 0; yOff <= this.treeHeight; ++yOff) {
            for(Direction direction : directions){
                int x = tilePosc.x() + direction.offsetX();
                int y = tilePosc.y() + yOff;
                int z = tilePosc.z() + direction.offsetZ();
                Block<?> block1 = world.getBlockType(new TilePos(x, y, z));
                if (block1 != Blocks.AIR && !(block1.getLogic() instanceof BlockLogicLeavesBase)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void placeTrunk(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc) {
        TilePos tilePos = new TilePos(tilePosc).down();
        for (int l1 = 0; l1 < this.treeHeight; ++l1) {
            tilePos.up();
            Block<?> block = world.getBlockType(tilePos);
            if (block == Blocks.AIR || block.getLogic() instanceof BlockLogicLeavesBase) {
                world.setBlockTypeNotify(tilePos, this.log);
            }
        }
    }

    private boolean canPlaceLeave(Block<?> block, @NotNull Random random, int leafRadius, int relativeX, int relativeY, int relativeZ) {
        return (Math.abs(relativeX) == leafRadius && Math.abs(relativeZ) == leafRadius
            && (random.nextInt(2) == 0 || relativeY == 0))
            || !block.hasTag(BlockTags.PLACE_OVERWRITES);
    }

    // This kept for now, in case the skyroot tree need adjustments
    private boolean oldCanPlace(@NotNull World world, TilePosc tilePosc) {
        int x = tilePosc.x();
        int y = tilePosc.y();
        int z = tilePosc.z();
        int maxY = y + this.treeHeight + 1;
        for (int curY = y; curY <= maxY; ++curY) {
            byte treeRadius = 1;
            if (curY == y) {
                treeRadius = 0;
            }
            if (curY >= maxY - 2) {
                treeRadius = 2;
            }
            for (int ix = x - treeRadius; ix <= x + treeRadius; ++ix) {
                for (int iz = z - treeRadius; iz <= z + treeRadius; ++iz) {
                    Block<?> block = world.getBlockType(new TilePos(ix, curY, iz));
                    if (block != Blocks.AIR && !(block.getLogic() instanceof BlockLogicLeavesBase)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
