package teamport.aether.effect.render;

import net.minecraft.core.entity.player.Player;

/**
 * Implement if you effect hearts to change when effect is applied
 * or player screen to be affected.
 */
public interface AetherCustomHeartContainer {
    HeartContainer getCustomContainer(Player player);
}
