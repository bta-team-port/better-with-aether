package bta.aether.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class ItemBlockAetherDouble extends ItemBlock {
    public ItemBlockAetherDouble(Block block) {
        super(block);
    }
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        if (stack.stackSize <= 0) {
            return false;
        }
        if (!world.canPlaceInsideBlock(blockX, blockY, blockZ)) {
            blockX += side.getOffsetX();
            blockY += side.getOffsetY();
            blockZ += side.getOffsetZ();
        }
        if (blockY < 0 || blockY >= world.getHeightBlocks()) {
            return false;
        }
        if (world.canBlockBePlacedAt(this.blockID, blockX, blockY, blockZ, false, side) && stack.consumeItem(player)) {
            Block block = Block.blocksList[this.blockID];
            if (world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, this.blockID, ((this.getPlacedBlockMetadata(1) & 0b0111_1111) + 0b1000_0000))) { // Delete the highest bit and force it on to signified player placed
                block.onBlockPlaced(world, blockX, blockY, blockZ, side, player, yPlaced);
                world.playBlockSoundEffect((float)blockX + 0.5f, (float)blockY + 0.5f, (float)blockZ + 0.5f, block, EnumBlockSoundEffectType.PLACE);
            }
            return true;
        }
        return false;
    }
}
