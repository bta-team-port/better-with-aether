package teamport.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicDeadBush;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.item.AetherItems;

public class BlockLogicDeadBushAether extends BlockLogicDeadBush {
    public BlockLogicDeadBushAether(Block<?> block) {
        super(block);
    }

    @Override
    public boolean mayPlaceOn(Block<?> block) {
        if (block == null) return false;
        int blockId = block.id();
        return blockId == Blocks.SAND.id()
            || blockId == Blocks.DIRT_SCORCHED.id()
            || blockId == AetherBlocks.QUICKSOIL.id()
            || BlockTags.GROWS_FLOWERS.appliesTo(block)
            || AetherBlockTags.GROWS_AETHER_FLOWERS.appliesTo(block);
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        int quantity = world.rand.nextInt(3);
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
                return new ItemStack[]{new ItemStack(this)};
            default:
                return quantity == 0 ? null : new ItemStack[]{new ItemStack(AetherItems.STICK_SKYROOT, quantity)};
        }
    }
}
