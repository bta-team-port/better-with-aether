package teamport.aether.items;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import teamport.aether.accessory.api.TickableWhileWorn;
import teamport.aether.items.accessory.ItemAccessory;

public class ItemGoldenFeather extends ItemAccessory implements TickableWhileWorn {
    public ItemGoldenFeather(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }

    public ItemStack tickWhileWorn(Player player, ItemStack itemstack, int slot) {
        player.fallDistance = 0.0f;
        if (!player.onGround && !player.isInWater() && !player.isInLava() && player.yd < 0.0) {
            player.yd *= 0.8f;
        }
        return itemstack;

    }

}
