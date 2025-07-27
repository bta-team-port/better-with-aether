package teamport.aether;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;

public class AccessoryHelper {

    /**
     * Check if a player has equipped an accessory
     * @param player   The player you are checking.
     * @param accessory The item you are looking for.
     * @return Whether the player has any items that match the provided item type.
     */
    public static boolean hasAccessory(Player player, Item accessory) {
        for (int i = 0; i < player.inventory.armorInventory.length; i++) {
            ItemStack stack = player.inventory.armorInventory[i];
            if (stack != null && stack.getItem().id == accessory.id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Find the first slot index with an accessory
     * Useful for items which don't stack effects.
     * @param player the player
     * @param accessory accessory we are looking for
     * @return the index into the armor inventory, where the first instance of this accessory is equipped or -1 if none equipped
     */
    public static int firstSlotWithAccessory(Player player, Item accessory) {
        for (int i = 0; i < player.inventory.armorInventory.length; i++) {
            ItemStack stack = player.inventory.armorInventory[i];
            if (stack != null && stack.getItem().id == accessory.id) {
                return i;
            }
        }
        return -1;
    }
}
