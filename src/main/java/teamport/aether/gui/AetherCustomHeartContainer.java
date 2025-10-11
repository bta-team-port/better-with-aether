package teamport.aether.gui;

import net.minecraft.core.entity.player.Player;
import teamport.aether.effect.HeartContainer;

/**
 * Implement if you effect hearts to change when effect is applied
 * or player screen to be affected.
 * */
public interface AetherCustomHeartContainer {
    HeartContainer getCustomContainer(Player player);
}
