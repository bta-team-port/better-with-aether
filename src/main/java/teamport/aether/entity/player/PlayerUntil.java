package teamport.aether.entity.player;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import teamport.aether.item.AetherItems;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

public class PlayerUntil {
    private PlayerUntil() {/* no need to initiate*/}

    public enum InventoryType {
        HOLD, MAIN, ARMOR
    }

    public static boolean isSilkTouch(Player player) {
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        return trinketOne != null && trinketOne.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id
            || trinketTwo != null && trinketTwo.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id;
    }

    /// Y pos on the server counted from the player's foot height but on the client it is counted from the player's head height
    /// We want count the player pos from his feet
    public static double getY(Player player) {
        if (EnvironmentHelper.isSinglePlayer()) {
            return player.y - player.bbHeight;
        }
        return player.y;
    }

    public static double getHeadY(Player player) {
        if (EnvironmentHelper.isSinglePlayer()) {
            return player.y;
        }
        return player.y - player.bbHeight;
    }

    /// The normal damageItem does not destroy the item if the item durability hits zero.
    public static void damageItem(Player player, ItemStack stack, int itemDamage, InventoryType type, int index) {
        stack.damageItem(itemDamage, player);
        if (stack.stackSize <= 0) {
            switch (type) {
                case HOLD:
                    player.destroyCurrentEquippedItem();
                    return;
                case MAIN:
                    player.inventory.mainInventory[index] = null;
                    return;
                case ARMOR:
                    player.inventory.armorInventory[index] = null;
                    return;
                default:
                    --stack.stackSize;
            }
        }
    }


    /// The normal damageItem does not destroy the item if the item durability hits zero. This target an item to destroy
    /// in the player main inventory at an index.
    public static void damageItemMain(Player player, int itemDamage, ItemStack stack, int index) {
        stack.damageItem(itemDamage, player);
        if (stack.stackSize <= 0) {
            player.inventory.mainInventory[index] = null;
        }
    }

    ///  Tool taking only 1 damage is quite common.
    public static void damageItemMain(Player player, ItemStack stack, int index) {
        damageItemMain(player, 1, stack, index);
    }

    /// The normal damageItem does not destroy the item if the item durability hits zero. This target an item to destroy
    /// in the player armor inventory at an index.
    public static void damageItemArmor(Player player, int itemDamage, ItemStack stack, int index) {
        stack.damageItem(itemDamage, player);
        if (stack.stackSize <= 0) {
            player.inventory.armorInventory[index] = null;
        }
    }

    ///  Tool taking only 1 damage is quite common.
    public static void damageItemArmor(Player player, ItemStack stack, int index) {
        damageItemArmor(player, 1, stack, index);
    }
}
