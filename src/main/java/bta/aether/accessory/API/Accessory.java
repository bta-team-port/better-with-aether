package bta.aether.accessory.API;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;

public interface Accessory {
	/**
	 * Determines what accessory slots your item goes to
	 *
	 * @param item The instance being accessed specifically.
	 * @return Accessory Type String array
	 */
	String[] getAccessoryTypes(ItemStack item);

	/**
	 * Called when accessory is inserted into a slot, or the player who had it in a slot is just loaded
	 * @param player EntityPlayer who added the accessory
	 * @param accessory accessory which was added
	 */
	default void onAccessoryAdded(EntityPlayer player, ItemStack accessory) {
	}

	/**
	 * Called when accessory is removed from a slot.
	 * @param player EntityPlayer who removed the accessory
	 * @param accessory accessory which was removed
	 */
	default void onAccessoryRemoved(EntityPlayer player, ItemStack accessory) {
	}
}
