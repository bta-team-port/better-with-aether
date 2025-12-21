package teamport.aether.entity.player;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.helper.unboxed.PriorityEntry;
import teamport.aether.item.AetherItems;
import teamport.aether.item.accessory.AetherInvisibility;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.PriorityQueue;

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
        PlayerUntil.damageItemMain(player, 1, stack, index);
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
        PlayerUntil.damageItemArmor(player, 1, stack, index);
    }

    /// The default way of finding player does not account for invisible player. The default is used by other function
    /// aside mobs and as such cannot be changed. Please use these function to search for closest player in mobs.
    public static Player getClosestPlayerToEntity(World world, Entity entity, double radius) {
        return getClosestPlayerToEntity(world, entity.x, entity.y, entity.z, radius);
    }

    public static @Nullable Player getClosestPlayerToEntity(World world, double x, double y, double z, double radius) {
        PriorityQueue<PriorityEntry<Player>> playerHeap = new PriorityQueue<>();
        for (Player currentPlayer : world.players) {
            playerHeap.add(PriorityEntry.pEntry(currentPlayer.distanceToSqr(x, y, z), currentPlayer));
        }
        if (radius < 0.0F) {
            PriorityEntry<Player> playerEntry = playerHeap.poll();
            if(playerEntry == null){
                return null;
            }
            return playerEntry.getData();
        }
        return PlayerUntil.returnClosestPlayer(playerHeap, radius);
    }

    @SuppressWarnings("java:S135")
    private static @Nullable Player returnClosestPlayer(PriorityQueue<PriorityEntry<Player>> playerHeap, double radius) {
        double rSquared = radius * radius;
        while(!playerHeap.isEmpty()){
            PriorityEntry<Player> playerEntry = playerHeap.poll();
            Player player = playerEntry.getData();
            double distance = playerEntry.getWeight();
            if (distance < rSquared){
                continue;
            }
            if (player instanceof AetherInvisibility) {
                AetherInvisibility potentialInvisiblePlayer = (AetherInvisibility) player;
                if (potentialInvisiblePlayer.aether$isInvisible() && playerEntry.getWeight() > 2) {
                    continue;
                }
                return player;
            }
            return player;
        }
        return null;
    }
}
