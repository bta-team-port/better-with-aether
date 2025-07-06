package teamport.aether.accessory.api;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;

/**
 * Implement this for an accessory or armor which should receive ticks while worn
 */
public interface TickableWhileWorn {
    /**
     * Called every tick while an accessory implementing this interface is worn
     * @param player EntityPlayer who is wearing this accessory
     * @param stack The ItemStack that is worn
     * @param slot  index into armor inventory where it is equipped
     * @return The new ItemStack
     */
    default ItemStack tickWhileWorn(Player player, ItemStack stack, int slot) {
        return stack;
    }
}
