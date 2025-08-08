package teamport.aether.mixin.screens;

import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.gui.IAetherScreens;
import teamport.aether.tile.TileEntityEnchanter;
import teamport.aether.tile.TileEntityFreezer;
import teamport.aether.tile.TileEntityIncubator;
import teamport.aether.tile.TileEntitySignSkyroot;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinAetherScreens implements IAetherScreens {
    public void aether$displayEnchanterScreen(TileEntityEnchanter tileEntity) {}
    public void aether$displayFreezerScreen(TileEntityFreezer tileEntity) {}
    public void aether$displayIncubatorScreen(TileEntityIncubator tileEntity) {}
    public void aether$displaySignSkyrootEditorScreen(TileEntitySignSkyroot tileEntity) {}

}
