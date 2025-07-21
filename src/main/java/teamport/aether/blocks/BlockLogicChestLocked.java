package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.aether.tile.TileEntityChestLocked;

public class BlockLogicChestLocked extends BlockLogicRotatable {
    protected ItemStack key;

    public BlockLogicChestLocked(Block<BlockLogic> block, ItemStack key) {
        super(block, Material.stone);
        this.key = key;
        block.withEntity(TileEntityChestLocked::new);
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced) {
        TileEntityChestLocked chest = (TileEntityChestLocked) world.getTileEntity(x, y, z);

        if (chest.isLocked() && !player.gamemode.isPlayerInvulnerable()) {
            ItemStack item = player.getHeldItem();
            if(item != null && item.itemID == key.itemID){
                item.consumeItem(player);
                chest.setLocked(false);
                // some sound effect
//                world.playSoundEffect(player, 1003, x, y, z, 0);
//                swapBlock(world, x, y, z, AetherBlocks.dungeonChest.id, world.getBlockMetadata(x,y,z), chest);
                return true;
            }
            return true;
        }
        player.displayChestScreen(BlockLogicChest.getInventory(world, x, y, z), (double)x, (double)y, (double)z);
        return true;
    }

    @Override
    public float blockStrength(World world, int x, int y, int z, Side side, Player player) {
        if (this.block.blockHardness < 0.0F) {
            return 0.0F;
        } else {
            return !player.canHarvestBlock(this.block) ? 1.0F / this.block.blockHardness / 100.0F : player.getCurrentPlayerStrVsBlock(this.block) / this.block.blockHardness / 30.0F;
        }
    }

}
