package bta.aether.item;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.EntityDevBoss;
import bta.aether.entity.tileEntity.TileEntityChestLocked;
import bta.aether.world.AetherDimension;
import bta.aether.world.LootTable;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class ItemDevStick extends Item {
    public ItemDevStick(int id) {
        super(id);
    }

    protected ItemStack makeTreasureChest(int quantity, World world, int x, int y, int z){

        world.setBlock(x, y, z, AetherBlocks.dungeonChestLocked.id);
        TileEntityChestLocked chest = (TileEntityChestLocked)world.getBlockTileEntity(x, y, z);
        ItemStack[] items = new LootTable("/assets/aether/lootTables/bronze-normal.json").generateLoot(quantity);

        for (int item = 0; item < items.length; item++) {
            if (items[item] == null) { continue; }
            chest.setInventorySlotContents(itemRand.nextInt(chest.getSizeInventory()), items[item]);
        }

        String password = String.valueOf(itemRand.nextInt(100_000_000));
        chest.setLocked(true);
        chest.setPassword(password);

        ItemStack key = new ItemStack(AetherItems.keyBronze);
        key.getData().putString("password", password);

        return key;
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        blockY += 12;
        int dungeon = AetherDimension.registerDungeonToMap(blockX, blockY, blockZ);

        for (int bx = -12; bx < 12; bx++) {
            for (int bz = -12; bz < 12; bz++) {
                world.setBlockWithNotify(blockX + bx, blockY - 2, blockZ + bz, AetherBlocks.stoneCarvedLocked.id);
            }
        }

        for (int bx = -1; bx < 2; bx++) {
            for (int bz = -1; bz < 2; bz++) {
                world.setBlockWithNotify(blockX + bx, blockY - 1, blockZ + bz, AetherBlocks.stoneCarvedLocked.id);
            }
        }

        EntityDevBoss boss = new EntityDevBoss(world);
        boss.spawnInit();
        boss.moveTo(blockX, blockY, blockZ, 0.0F, 0.0F);
        boss.keySlot = makeTreasureChest(itemRand.nextInt(10) + 5, world, blockX, blockY, blockZ);
        boss.belongsTo = dungeon;
        world.entityJoinedWorld(boss);


        return true;
    }
}
