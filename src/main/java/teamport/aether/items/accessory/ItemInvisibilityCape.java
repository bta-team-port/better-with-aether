package teamport.aether.items.accessory;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import teamport.aether.items.IAetherAccessories;

public class ItemInvisibilityCape extends ItemAccessory {

    public ItemInvisibilityCape(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
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
