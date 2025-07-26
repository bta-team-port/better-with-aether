package teamport.aether.items;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import teamport.aether.accessory.api.TickableWhileWorn;
import teamport.aether.items.accessory.ItemAccessory;

public class ItemIronBubble extends ItemAccessory implements TickableWhileWorn {
    public ItemIronBubble(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }

    public ItemStack tickWhileWorn(Player player, ItemStack itemstack, int slot) {
        player.airSupply = 300;
        return itemstack;

    }

}
