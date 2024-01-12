package bta.aether.item;

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

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        LootTable[] tables = {
                new LootTable("/assets/aether/lootTables/bronze-normal.json"),
                new LootTable("/assets/aether/lootTables/bronze-rare.json"),
                new LootTable("/assets/aether/lootTables/silver-normal.json"),
                new LootTable("/assets/aether/lootTables/silver-rare.json"),
                new LootTable("/assets/aether/lootTables/gold-rare.json")
                };
        ItemStack[] items = tables[itemRand.nextInt(tables.length)].generateLoot(16);
        for (int item = 0; item < items.length; item++) {
            if (items[item] == null) { continue; }
            world.dropItem(blockX, blockY + 1, blockZ, items[item]);
        }
        return true;
    }
}
