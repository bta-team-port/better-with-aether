package teamport.aether.items;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.items.accessory.ItemAccessory;

import static teamport.aether.items.accessory.SlotAccessory.CAPE_SLOT;

public class ItemAgilityCape extends ItemAccessory {

    public ItemAgilityCape(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }

    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (
                slotId > player.inventory.mainInventory.length
                        && slotId - player.inventory.mainInventory.length == CAPE_SLOT
        ) {
            player.footSize = 1.0f;
            return;
        }
        player.footSize = 0.5f;
    }

}
