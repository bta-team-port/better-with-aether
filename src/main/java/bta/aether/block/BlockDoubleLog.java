package bta.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockAxisAligned;
import net.minecraft.core.block.BlockLeavesBase;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.enums.PlacementMode;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;
import net.minecraft.core.world.World;

public class BlockDoubleLog extends BlockAetherDouble {
    public BlockDoubleLog(String key, int id, Class<?> toolClass) {
        super(key, id, Material.wood, toolClass);
    }
    @Override
    public void onBlockPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight) {
        int meta = world.getBlockMetadata(x, y, z);
        Axis axis = entity.getPlacementDirection(side, PlacementMode.SIDE).getAxis();
        world.setBlockMetadataWithNotify(x, y, z, BlockAxisAligned.axisToMeta(axis) | (meta & 0b1000_0000));
    }
    @Override
    public void onBlockRemoved(World world, int x, int y, int z, int data) {
        int byte0 = 4;
        int l = byte0 + 1;
        if (world.areBlocksLoaded(x - l, y - l, z - l, x + l, y + l, z + l)) {
            for (int i1 = -byte0; i1 <= byte0; ++i1) {
                for (int j1 = -byte0; j1 <= byte0; ++j1) {
                    for (int k1 = -byte0; k1 <= byte0; ++k1) {
                        int i2;
                        int l1 = world.getBlockId(x + i1, y + j1, z + k1);
                        if (!(Block.blocksList[l1] instanceof BlockLeavesBase) || ((i2 = world.getBlockMetadata(x + i1, y + j1, z + k1)) & 8) != 0) continue;
                        world.setBlockMetadata(x + i1, y + j1, z + k1, i2 | 8);
                    }
                }
            }
        }
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(Side side, int data) {
        if (6 * (data & 3) + side.getId() >= Sides.orientationLookUpXYZAligned.length) {
            return 0;
        }
        return this.atlasIndices[Sides.orientationLookUpXYZAligned[6 * (data & 3) + side.getId()]];
    }

    public static int axisToMeta(Axis axis) {
        if (axis == Axis.X) {
            return 2;
        }
        if (axis == Axis.Z) {
            return 1;
        }
        return 0;
    }

    public static Axis metaToAxis(int meta) {
        if (meta == 2) {
            return Axis.X;
        }
        if (meta == 1) {
            return Axis.Z;
        }
        return Axis.Y;
    }
}
