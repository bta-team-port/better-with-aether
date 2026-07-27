package teamport.aether.effect.api;

import net.minecraft.core.entity.player.Player;

public final class HealthHelper {
    private HealthHelper() { }
    public static int getExtraHealth(Player player) { return player.bonusHealth; }
    public static void setExtraHealth(Player player, int amount) { player.bonusHealth = Math.max(0, amount); }
    public static void addExtraHealth(Player player, int amount) { setExtraHealth(player, getExtraHealth(player) + amount); }
}
