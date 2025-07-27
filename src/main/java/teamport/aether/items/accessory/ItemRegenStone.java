package teamport.aether.items.accessory;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;

public class ItemRegenStone extends ItemAccessory implements TickableWhileWorn {

    public ItemRegenStone(String translationKey, String namespaceId, int id, String name, int accessorySlot) {
        super(translationKey, namespaceId, id, name, accessorySlot);
    }

    @Override
    public void onAccessoryAdded(Player player, ItemStack accessory) {
        accessory.setMetadata(0);
    }

    @Override
    public void onAccessoryRemoved(Player player, ItemStack accessory) {
        accessory.setMetadata(0);
    }

    public ItemStack tickWhileWorn(Player player, ItemStack itemstack, int slot) {
        itemstack.setMetadata(itemstack.getMetadata() + 1);
        if (itemstack.getMetadata() > 150) {
            itemstack.setMetadata(0);
            // only heal if it's the first equipped, since in the OG mod effect doesn't stack.
            // also don't heal when player has max health, since that causes hearts flash
            // Not sure if this is supposed to heal life crystal hearts, so I added that. - Cookie
            if (player.getHealth() < player.getMaxHealth()) {
                player.heal(1);
            }
        }
        return itemstack;
    }

}
