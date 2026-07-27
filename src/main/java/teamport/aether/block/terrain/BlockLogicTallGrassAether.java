package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFlower;
import net.minecraft.core.block.BlockLogicMoss;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.block.AetherBlockTags;

public class BlockLogicTallGrassAether extends BlockLogicFlower {
    public BlockLogicTallGrassAether(Block<?> block) {
        super(block);
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0, 0.5F - f, 0.5F + f, 0.800000011920929, 0.5F + f);
    }

    @Override
    public boolean mayPlaceOn(Block<?> block) {
        return block != null && (block.getLogic() instanceof BlockLogicMoss || block.hasTag(BlockTags.GROWS_FLOWERS) || block.hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS) || super.mayPlaceOn(block));
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case PICK_BLOCK:
            case SILK_TOUCH:
                return new ItemStack[]{new ItemStack(this)};
            default:
                return null;
        }
    }
}
