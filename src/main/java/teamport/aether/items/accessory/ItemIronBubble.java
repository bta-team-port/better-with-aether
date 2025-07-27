package teamport.aether.items.accessory;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;

public class ItemIronBubble extends ItemAccessory implements TickableWhileWorn {
    public ItemIronBubble(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }

    public ItemStack tickWhileWorn(Player player, ItemStack itemstack, int slot) {
        player.airSupply = 300;
        return itemstack;

    }

}
