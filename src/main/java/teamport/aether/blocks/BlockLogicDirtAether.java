package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IBonemealable;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class BlockLogicDirtAether extends BlockLogic implements IBonemealable {

    public BlockLogicDirtAether(Block<?> block) {
        super(block, Material.dirt);
        block.setTicking(true);
    }

    @Override
    public boolean onBonemealUsed(ItemStack itemStack, Player player, World world, int i, int j, int k, Side side, double d, double e) {
        int j1;
            if (!world.isClientSide && Blocks.lightBlock[world.getBlockId(i, j + 1, k)] <= 2) {
                j1 = AetherBlocks.GRASS_AETHER.id();

                world.setBlockWithNotify(i, j, k, j1);
                if (player == null || player.getGamemode().consumeBlocks()) {
                    --itemStack.stackSize;
                    player.swingItem();
                }
            return true;
        }
        return false;
    }
}
