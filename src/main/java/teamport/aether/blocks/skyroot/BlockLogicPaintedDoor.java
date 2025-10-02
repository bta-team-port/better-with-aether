package teamport.aether.blocks.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicDoor;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;

import java.util.function.Supplier;

public class BlockLogicPaintedDoor extends BlockLogicDoor implements IPainted {
    protected final int unpaintedDoorBlockBottomID;
    protected final int unpaintedDoorBlockTopID;
    private final Supplier<Item> paintedDoorItem;

    public BlockLogicPaintedDoor(
            Block<?> block, Material material, boolean isTop,
            int unpaintedDoorBlockTopID, int unpaintedDoorBlockBottomID,
            Supplier<Item> paintedDoorItem
    ) {
        super(block, material, isTop, false, null);
        this.unpaintedDoorBlockTopID = unpaintedDoorBlockTopID;
        this.unpaintedDoorBlockBottomID = unpaintedDoorBlockBottomID;
        this.paintedDoorItem = paintedDoorItem;
    }

    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        return new ItemStack[]{new ItemStack(paintedDoorItem.get(), 1, 15 - (meta >> 4 & 15))};
    }

    public DyeColor fromMetadata(int meta) {
        return DyeColor.colorFromBlockMeta(meta >> 4 & 15);
    }

    public int toMetadata(DyeColor color) {
        return color.blockMeta << 4;
    }

    public int stripColorFromMetadata(int meta) {
        return meta & 15;
    }

    public void removeDye(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadataWithNotify(x, y, z, this.isTop ? unpaintedDoorBlockTopID : unpaintedDoorBlockBottomID, meta & 15);
        if (this.isTop) {
            world.setBlockAndMetadataWithNotify(x, y - 1, z, unpaintedDoorBlockBottomID, meta & 15);
        } else {
            world.setBlockAndMetadataWithNotify(x, y + 1, z, unpaintedDoorBlockTopID, meta & 15);
        }
    }

    @Override
    public boolean canBePainted() {
        return true;
    }

    @Override
    public void setColor(World world, int x, int y, int z, DyeColor color) {
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, meta & 15 | this.toMetadata(color));
        if (this.isTop) {
            world.setBlockMetadataWithNotify(x, y - 1, z, meta & 15 | this.toMetadata(color));
        } else {
            world.setBlockMetadataWithNotify(x, y + 1, z, meta & 15 | this.toMetadata(color));
        }
    }
}
