package teamport.aether.blocks.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicDoorPainted;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import java.util.function.Supplier;

public class BlockLogicPaintedDoor extends BlockLogicDoorPainted {
    protected final int unpaintedDoorBlockBottomID;
    protected final int unpaintedDoorBlockTopID;
    private final Supplier<Item> paintedDoorItem;

    public BlockLogicPaintedDoor(
        Block<?> block, Material material, boolean isTop,
        int unpaintedDoorBlockTopID, int unpaintedDoorBlockBottomID,
        Supplier<Item> paintedDoorItem
    ) {
        super(block, material, isTop);
        this.unpaintedDoorBlockTopID = unpaintedDoorBlockTopID;
        this.unpaintedDoorBlockBottomID = unpaintedDoorBlockBottomID;
        this.paintedDoorItem = paintedDoorItem;
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        return new ItemStack[]{new ItemStack(paintedDoorItem.get(), 1, 15 - (meta >> 4 & 15))};
    }

    @Override
    public void removeDye(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadataWithNotify(x, y, z, this.isTop ? unpaintedDoorBlockTopID : unpaintedDoorBlockBottomID, meta & 15);
        if (this.isTop) {
            world.setBlockAndMetadataWithNotify(x, y - 1, z, unpaintedDoorBlockBottomID, meta & 15);
        } else {
            world.setBlockAndMetadataWithNotify(x, y + 1, z, unpaintedDoorBlockTopID, meta & 15);
        }
    }
}
