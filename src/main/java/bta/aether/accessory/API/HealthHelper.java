package bta.aether.accessory.API;

import net.minecraft.core.entity.player.EntityPlayer;

public class HealthHelper {

	/**
	 * Get the amount of extra health in half hearts the player has, on top of the 20 they start with
	 * @param player the player
	 * @return the amount of extra health
	 */
	public static int getExtraHealth(EntityPlayer player) {
		return ((VariableHealthPlayer) player).getExtraHP();
	}

	/**
	 * Set the amount of extra health the player has in half hearts, on top of the 20 they start with.
	 * @param player the player
	 * @param amount the amount of extra health they should have
	 */
	public static void setExtraHealth(EntityPlayer player, int amount) {
		((VariableHealthPlayer) player).setExtraHP(amount);
	}

	/**
	 * Add to the amount of extra health the player has in half hearts, on top of the 20 they start with.
	 * @param player the player
	 * @param amount the amount of extra health to add, on top of the amount they already have
	 */
	public static void addExtraHealth(EntityPlayer player, int amount) {
		((VariableHealthPlayer) player).addExtraHP(amount);
	}

	/**
	 * Get the total/max health of a player (20 + extra health)
	 * @param player the player
	 * @return total health including base 20 + extra health
	 */
	public static int getTotalHealth(EntityPlayer player) {
		return 20 + getExtraHealth(player);
	}
}
