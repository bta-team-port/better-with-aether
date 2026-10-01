package teamport.aether.world.feature.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLog;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class WorldFeatureAetherTreeGoldenOak extends WorldFeatureAetherTree implements WorldFeatureInterface {

    public WorldFeatureAetherTreeGoldenOak(Block<?> leaves, Block<?> log, int heightMod) {
        super(leaves, log, heightMod);
    }

    @Override
    protected int setTreeHeight(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc) {
        return random.nextInt(5) + this.heightMod;
    }

    @Override
    public boolean placeFeature(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc) {
        int x = tilePosc.x();
        int y = tilePosc.y();
        int z = tilePosc.z();
        this.changeDirtBlockBelow(world, new TilePos(tilePosc).down());
        this.placeLeaves(world, random, x, y, z);
        this.placeTrunk(world, random, x, y, z);
        return true;
    }

    @Override
    public boolean canPlace(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc) {
        return this.canPlaceTree(world, tilePosc) && this.canPlace(world, tilePosc);
    }

    private boolean canPlace(@NotNull World world, TilePosc tilePosc) {
        for (int iy = tilePosc.y(); iy < tilePosc.y() + 12; ++iy) {
            int radius = this.adjustRadius(tilePosc, iy);
            boolean leanCheck = iy - tilePosc.y() >= 7 && iy - tilePosc.y() < 10;
            for (int ix = tilePosc.x() - radius; ix < tilePosc.x() + radius + 1; ix++) {
                for (int iz = tilePosc.z() - radius; iz < tilePosc.z() + radius + 1; iz++) {
                    Block<?> block = world.getBlockType(new TilePos(ix, iy, iz));
                    if (this.canLeavesBePlaced(block)
                        || leanCheck && block.getLogic() instanceof BlockLogicLog
                    ) {
                        continue;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private int adjustRadius(TilePosc tilePosc, int iy) {
        int height = iy - tilePosc.y();
        if (height < 5) {
            return 0;
        }
        if (height < 7) {
            return 1;
        }
        if (height < 10) {
            return 2;
        }
        if (height < 12) {
            return 1;
        }
        return 0;
    }

    private void placeLeaves(@NotNull World world, @NotNull Random random, int x, int y, int z) {
        for (int leafX = x - 3; leafX < x + 4; ++leafX) {
            for (int leafY = y + 5; leafY < y + 12; ++leafY) {
                for (int leafZ = z - 3; leafZ < z + 4; ++leafZ) {
                    int relativeX = leafX - x;
                    int relativeY = leafY - y - 8;
                    int relativeZ = leafZ - z;
                    int distanceSquared = relativeX * relativeX + relativeY * relativeY + relativeZ * relativeZ;
                    TilePos tilePos = new TilePos(leafX, leafY, leafZ);
                    if (distanceSquared < 12 + random.nextInt(5) && world.isAirBlock(tilePos)) {
                        world.setBlockTypeNotify(tilePos, this.leaves);
                    }
                }
            }
        }
    }

    private void placeTrunk(@NotNull World world, @NotNull Random random, int x, int y, int z) {
        for (int trunkY = 0; trunkY < this.treeHeight; ++trunkY) {
            TilePos tilePos = new TilePos(x, y + trunkY, z);
            Block<?> block = world.getBlockType(tilePos);
            if(this.canTrunkBePlaced(block)){
                if (trunkY > 4 && random.nextInt(3) > 0) {
                    this.branch(world, random, x, y + trunkY, z, trunkY / 4 - 1);
                }
                world.setBlockTypeDataNotify(tilePos, this.log, 0);
            }
        }
    }

    private void branch(@NotNull World world, @NotNull Random random, int x, int y, int z, int verticalStep) {
        int directionX = random.nextInt(3) - 1;
        int directionZ = random.nextInt(3) - 1;
        int branchLength = random.nextInt(2) + 1;
        for (int step = 0; step < branchLength; ++step) {
            x += directionX;
            y += verticalStep;
            z += directionZ;
            TilePos tilePos = new TilePos(x, y, z);
            if (this.canLeavesBePlaced(world.getBlockType(tilePos))) {
                world.setBlockTypeDataNotify(tilePos, this.leaves, 0);
            }
        }
    }

}
