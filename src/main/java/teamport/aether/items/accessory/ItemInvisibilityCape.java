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

    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (
                slotId < player.inventory.mainInventory.length
                && slotId - player.inventory.mainInventory.length != CAPE_SLOT
        ) {
            return;
        }
    }

    @Override
    public void onAccessoryAdded(Player player, ItemStack accessory) {
        ((IAetherAccessories)player).aether$setInvisible(true);
    }

    @Override
    public void onAccessoryRemoved(Player player, ItemStack accessory) {
        ((IAetherAccessories)player).aether$setInvisible(false);
    }

}
