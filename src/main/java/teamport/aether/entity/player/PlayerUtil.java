package teamport.aether.entity.player;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.effect.AetherEffects;
import teamport.aether.item.AetherItems;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static teamport.aether.item.accessory.SlotAccessory.CAPE_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.GLOVES_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

public class PlayerUtil {
    private PlayerUtil() {/* no need to initiate*/}

    public enum InventoryType {
        HOLD, MAIN, ARMOR
    }

    ///  Count the armor pieces of a specific material.
    public static int countArmorPiecesOfMaterial(ContainerInventory inventory, ArmorMaterial material) {
        int count = 0;
        for (int i = 0; i < inventory.armorInventory.length; ++i) {
            ItemStack itemStack = inventory.armorInventory[i];
            if (itemStack == null || !(itemStack.getItem() instanceof IArmorItem)) {
                continue;
            }
            IArmorItem armor = (IArmorItem) itemStack.getItem();
            if (armor.getArmorShape().getSlotIndex() != i) {
                continue;
            }
            if (hasArmorMaterial(armor, material)) count++;
        }

        ItemStack[] accessories = ((IContainerInventoryAether) inventory).aether$getAccessoryInventory();
        for (int logicalSlot = GLOVES_SLOT; logicalSlot <= CAPE_SLOT; logicalSlot++) {
            ItemStack itemStack = accessories[logicalSlot - GLOVES_SLOT];
            if (itemStack != null && itemStack.getItem() instanceof IArmorItem armor && hasArmorMaterial(armor, material)) {
                count++;
            }
        }
        return count;
    }

    /// Counts the accessories of a specific material.
    public static int countAccessoriesOfMaterial(ContainerInventory inventory, ArmorMaterial material) {
        int count = 0;
        ItemStack[] accessories = ((IContainerInventoryAether) inventory).aether$getAccessoryInventory();
        for (int slot = TRINKET_1_SLOT - GLOVES_SLOT; slot <= TRINKET_2_SLOT - GLOVES_SLOT; slot++) {
            ItemStack itemStack = accessories[slot];
            if (itemStack == null || !(itemStack.getItem() instanceof IArmorItem)) {
                continue;
            }
            IArmorItem armor = (IArmorItem) itemStack.getItem();
            if (hasArmorMaterial(armor, material)) count++;
        }
        return count;
    }

    private static boolean hasArmorMaterial(IArmorItem armor, ArmorMaterial material) {
        ArmorMaterial armorMaterial = armor.getArmorMaterial();
        return armorMaterial != null && armorMaterial.equals(material);
    }

    /// Checks if player is wearing gold pendants
    public static boolean isSilkTouchPendant(Player player) {
        ItemStack trinketOne = getArmorOrAccessoryItem(player, TRINKET_1_SLOT);
        ItemStack trinketTwo = getArmorOrAccessoryItem(player, TRINKET_2_SLOT);
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

    public static void damageItem(Player player, ItemStack stack, InventoryType type, int index) {
        PlayerUtil.damageItem(player, 1, stack, type, index);
    }

    /// The normal damageItem does not destroy the item if the item durability hits zero.
    public static void damageItem(Player player, int itemDamage, ItemStack stack, InventoryType type, int index) {
        stack.damageItem(itemDamage, player);
        if (stack.stackSize <= 0) {
            switch (type) {
                case HOLD:
                    player.destroyCurrentEquippedItem();
                    return;
                case ARMOR:
                    PlayerUtil.damageItemArmor(player, itemDamage, stack, index);
                    return;
                case MAIN:
                default:
                    PlayerUtil.damageItemMain(player, itemDamage, stack, index);
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
        PlayerUtil.damageItemMain(player, 1, stack, index);
    }

    /// The normal damageItem does not destroy the item if the item durability hits zero. This target an item to destroy
    /// in the player armor inventory at an index.
    public static void damageItemArmor(Player player, int itemDamage, ItemStack stack, int index) {
        stack.damageItem(itemDamage, player);
        if (stack.stackSize <= 0) {
            if (index < player.inventory.armorInventory.length) {
                player.inventory.armorInventory[index] = null;
            } else {
                ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory()[index - GLOVES_SLOT] = null;
            }
        }
    }

    public static ItemStack getArmorOrAccessoryItem(Player player, int armorSlot) {
        if (armorSlot < player.inventory.armorInventory.length) {
            return player.inventory.armorInventory[armorSlot];
        }
        ItemStack[] accessories = ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory();
        int accessorySlot = armorSlot - GLOVES_SLOT;
        return accessorySlot >= 0 && accessorySlot < accessories.length ? accessories[accessorySlot] : null;
    }

    public static void clearArmorOrAccessoryItem(Player player, int armorSlot) {
        if (armorSlot < player.inventory.armorInventory.length) {
            player.inventory.armorInventory[armorSlot] = null;
            return;
        }
        ItemStack[] accessories = ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory();
        int accessorySlot = armorSlot - GLOVES_SLOT;
        if (accessorySlot >= 0 && accessorySlot < accessories.length) {
            accessories[accessorySlot] = null;
        }
    }

    public static ItemStack getActiveQuiver(Player player) {
        int slot = getActiveQuiverSlot(player);
        return slot >= 0 ? getArmorOrAccessoryItem(player, slot) : null;
    }

    public static int getActiveQuiverSlot(Player player) {
        int chestSlot = HumanArmorShape.CHEST.getSlotIndex();
        if (isUsableQuiver(getArmorOrAccessoryItem(player, chestSlot))) return chestSlot;
        if (isUsableQuiver(getArmorOrAccessoryItem(player, CAPE_SLOT))) return CAPE_SLOT;
        return -1;
    }

    public static boolean isUsableQuiver(ItemStack stack) {
        if (stack == null) return false;
        if (stack.itemID == Items.ARMOR_QUIVER_GOLD.id) return true;
        return stack.itemID == Items.ARMOR_QUIVER.id && stack.getMetadata() < stack.getMaxDamage();
    }

    ///  Tool taking only 1 damage is quite common.
    public static void damageItemArmor(Player player, ItemStack stack, int index) {
        PlayerUtil.damageItemArmor(player, 1, stack, index);
    }

    @FunctionalInterface
    public interface PlayerStatus {
        boolean test(Player player, Double distance);
    }


    /// The default way of finding player does not account for invisible player. The default is used by other function
    /// aside mobs and as such cannot be changed. Please use these function to search for closest player in mobs.
    public static Player getClosestNonInvisPlayerToEntity(World world, Entity entity, double radius) {
        return getClosestPlayerToEntity(world, entity, radius, PlayerUtil::isInvisible);
    }

    /// The default way of finding player does not account for invisible player. The default is used by other function
    /// aside mobs and as such cannot be changed. Please use these function to search for closest player in mobs.
    public static Player getClosestPlayerToEntity(World world, Entity entity, double radius, PlayerStatus... playerStatus) {
        if (radius < 0.0F || playerStatus.length == 0) {
            return world.getClosestPlayerToEntity(entity, radius);
        }
        return PlayerUtil.getClosestPlayerToEntity(world, entity.x, entity.y, entity.z, radius, playerStatus);
    }

    public static @Nullable Player getClosestPlayerToEntity(World world, double x, double y, double z, double radius, PlayerStatus[] playerStatus) {
        double closestDistance = Double.POSITIVE_INFINITY;
        Player returnPlayer = null;
        for (Player currentPlayer : world.players) {
            double currentDistance = currentPlayer.distanceToSqr(x, y, z);
            if (currentDistance > radius * radius
                || PlayerUtil.test(playerStatus, currentPlayer, currentDistance)
                || currentDistance >= closestDistance
            ) {
                continue;
            }
            closestDistance = currentDistance;
            returnPlayer = currentPlayer;
        }
        return returnPlayer;
    }

    private static boolean test(PlayerStatus[] playerStatus, Player player, double distance) {
        boolean acc = false;
        for (PlayerStatus status : playerStatus) {
            acc |= status.test(player, distance);
        }
        return acc;
    }

    ///  To check if the player can be attacked by Swets
    public static boolean isSwetty(Entity entity, double distance){
        return PlayerUtil.isSwetty(entity);
    }

    ///  The interface requires the distance but either call is fine, as long it not used to figure out targeting
    public static boolean isSwetty(Entity entity) {
        return entity instanceof IHasEffects
            &&((IHasEffects<?>) entity).getContainer().hasEffect(AetherEffects.swetty);
    }

    ///  To check if the player is Invisible for targeting
    public static boolean isInvisible(Entity entity, double distance){
        return PlayerUtil.isInvisible(entity) && distance > 2.0f;
    }

    ///  To check if the player is Invisible
    public static boolean isInvisible(Entity entity) {
        return entity instanceof IHasEffects
            &&((IHasEffects<?>) entity).getContainer().hasEffect(AetherEffects.invisibility);
    }
}
