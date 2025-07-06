package teamport.aether.accessory.api;

import net.minecraft.core.entity.player.Player;

public class HealthHelper {

    /**
     * Get the amount of extra health in half hearts the player has, on top of the 20 they start with
     * @param player the player
     * @return the amount of extra health
     */
    public static int getExtraHealth(Player player) {
        return ((VariableHealthPlayer) player).better_with_aether$getExtraHP();
    }

    /**
     * Set the amount of extra health the player has in half hearts, on top of the 20 they start with.
     * @param player the player
     * @param amount the amount of extra health they should have
     */
    public static void setExtraHealth(Player player, int amount) {
        ((VariableHealthPlayer) player).better_with_aether$setExtraHP(amount);
    }

    /**
     * Add to the amount of extra health the player has in half hearts, on top of the 20 they start with.
     * @param player the player
     * @param amount the amount of extra health to add, on top of the amount they already have
     */
    public static void addExtraHealth(Player player, int amount) {
        ((VariableHealthPlayer) player).better_with_aether$addExtraHP(amount);
    }

    /**
     * Get the total/max health of a player (20 + extra health)
     * @param player the player
     * @return total health including base 20 + extra health
     */
    public static int getTotalHealth(Player player) {
        return 20 + getExtraHealth(player);
    }
}