package bta.aether.block;

import bta.aether.entity.tileEntity.TileEntityChestLocked;
import bta.aether.item.AetherItems;
import net.minecraft.core.block.BlockChest;
import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class BlockChestLocked extends BlockTileEntityRotatable {
    protected static boolean keepChestInventory = false;

    protected BlockChestLocked(String key, int id, Material material) {
        super(key, id, material);
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityChestLocked();
    }

    @Override
    public void onBlockRemoved(World world, int x, int y, int z, int data) {
        if (!keepChestInventory){
            if (!((TileEntityChestLocked) world.getBlockTileEntity(x, y, z)).getlocked()){
                BlockChest.dropChestContent(world, x, y, z);
            }
            world.removeBlockTileEntity(x, y, z);
        }
    }

    private void swapBlock(World world, int x, int y, int z, int blockID, int meta, TileEntityChestLocked entityChestLocked) {
        keepChestInventory = true;
        world.setBlockWithNotify(x, y, z, blockID);
        world.setBlockMetadataWithNotify(x, y, z, meta);
        entityChestLocked.validate();
        world.setBlockTileEntity(x, y, z, entityChestLocked);
        keepChestInventory = false;
    }

    /*
     * When generating a key, you should attach to them a nbt string called "password". that can be any random String.
     * A locked chest with no password cannot be open unless the game assigns a matching key to it.
     * A locked chest can not have items inserted into it.
     * Locked chests are unlocked by default, be mindful of that when writing generation code.
     */

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        ItemStack item = player.inventory.mainInventory[player.inventory.currentItem];
        TileEntityChestLocked chest = (TileEntityChestLocked) world.getBlockTileEntity(x, y, z);

        if (!chest.getlocked()) {
            if (item != null && item.getItem().hasTag(AetherItems.aetherdungeonKey)) {

                if (item.getData().getString("password").isEmpty()) {
                    item.getData().putString("password", String.valueOf(world.rand.nextInt(100_000_000)));
                    item.setCustomName(item.getData().getString("password"));
                }

                chest.setLocked(true);
                chest.setPassword(item.getData().getString("password"));
                world.playSoundEffectForPlayer(player, 1003, x, y, z, 0);
                swapBlock(world, x, y, z, AetherBlocks.dungeonChestLocked.id, world.getBlockMetadata(x,y,z) , chest);
                return true;
            }

            player.displayGUIChest(BlockChest.getInventory(world, x, y, z));
            return true;
        }

        if (item != null && ( item.getData().getString("password").equals(chest.getPassword()))){
            chest.setLocked(false);
            chest.setPassword("DEFAULT");
            world.playSoundEffectForPlayer(player, 1003, x, y, z, 0);
            swapBlock(world, x, y, z, AetherBlocks.dungeonChest.id, world.getBlockMetadata(x,y,z), chest);
            return true;
        }
        player.addChatMessage("Despite all of your might, this chest does not bulge.");
        return false;
        }
}
