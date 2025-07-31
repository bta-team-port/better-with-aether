package teamport.aether.items.accessory;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.items.IAetherAccessories;

import static teamport.aether.items.accessory.SlotAccessory.CAPE_SLOT;

public class ItemInvisibilityCape extends ItemAccessory {

    public ItemInvisibilityCape(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }

    // TODO make the player visible when the item is dragged away from the armor slot
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (
                slotId > player.inventory.mainInventory.length
                && slotId - player.inventory.mainInventory.length == CAPE_SLOT
        ) {
            ((IAetherAccessories)player).aether$setInvisible(true);
            return;
        }
        ((IAetherAccessories)player).aether$setInvisible(false);
    }

    public void onAccessoryAdded(Player player, ItemStack accessory) {
        ((IAetherAccessories)player).aether$setInvisible(true);
    }

    public void onAccessorySwapped(Player player, ItemStack accessory) {
        ((IAetherAccessories)player).aether$setInvisible(false);
    }


}
