package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicDoorPainted;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;

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
    public void removeDye(World world, TilePosc pos) {
        int meta = world.getBlockData(pos);
        TilePos otherPos = this.isTop ? pos.down(new TilePos()) : pos.up(new TilePos());
        world.setBlockTypeData(pos, Blocks.getBlock(this.isTop ? unpaintedDoorBlockTopID : unpaintedDoorBlockBottomID), meta & 15);
        if (this.isTop) {
            world.setBlockTypeDataNotify(otherPos, Blocks.getBlock(unpaintedDoorBlockBottomID), meta & 15);
        } else {
            world.setBlockTypeDataNotify(otherPos, Blocks.getBlock(unpaintedDoorBlockTopID), meta & 15);
        }
        world.notifyBlockChange(pos, this.block);
    }
}
