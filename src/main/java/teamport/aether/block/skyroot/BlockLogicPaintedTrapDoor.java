package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTrapDoorPainted;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

public class BlockLogicPaintedTrapDoor extends BlockLogicTrapDoorPainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedTrapDoor(Block<?> block, Material material, int unpaintedBlockID) {
        super(block, material);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    @Override
    public void removeDye(@NonNull World world, @NonNull TilePosc pos) {
        int meta = world.getBlockData(pos);
        world.setBlockTypeDataNotify(pos, Blocks.getBlock(unpaintedBlockID), meta & 15);
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        return new ItemStack[]{new ItemStack(this, 1, (meta >> 4 & 15) << 4)};
    }
}
