package teamport.aether.items.accessory;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;

public interface IAccessoryEffects {
    /**
     * Called when accessory is removed from a slot.
     *
     * @param player    EntityPlayer who removed the accessory
     * @param accessory accessory which was removed
     */
    default void removeEffect(Player player, ItemStack accessory) {
    }
}
