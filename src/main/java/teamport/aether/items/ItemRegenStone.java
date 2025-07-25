package teamport.aether.items;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.items.accessory.ItemAccessory;

public class ItemRegenStone extends ItemAccessory {

    public ItemRegenStone(String translationKey, String namespaceId, int id, String name, int accessorySlot) {
        super(translationKey, namespaceId, id, name, accessorySlot);
    }

    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        itemstack.setMetadata(itemstack.getMetadata() + 1);
        if (itemstack.getMetadata() > 100) {
            itemstack.setMetadata(0);
            // also don't heal when player has max health, since that causes hearts flash
            // Not sure if this is supposed to heal life crystal hearts, so I added that. - Cookie
        }
    }

}
