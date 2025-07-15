package teamport.aether.mixin.screens;

import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.gui.IAetherScreens;
import teamport.aether.tile.TileEntityEnchanter;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinAetherScreens implements IAetherScreens {

    public void aether$displayEnchanterScreen(TileEntityEnchanter tile) {}
}
