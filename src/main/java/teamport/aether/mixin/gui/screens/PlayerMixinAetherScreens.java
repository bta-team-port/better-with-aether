package teamport.aether.mixin.gui.screens;

import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.entity.tile.TileEntityEnchanter;
import teamport.aether.entity.tile.TileEntityFreezer;
import teamport.aether.entity.tile.TileEntityIncubator;
import teamport.aether.entity.tile.TileEntitySignSkyroot;
import teamport.aether.gui.AetherScreens;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinAetherScreens implements AetherScreens {
    public void aether$displayEnchanterScreen(TileEntityEnchanter tileEntity) {
    }

    public void aether$displayFreezerScreen(TileEntityFreezer tileEntity) {
    }

    public void aether$displayIncubatorScreen(TileEntityIncubator tileEntity) {
    }

    public void aether$displaySignSkyrootEditorScreen(TileEntitySignSkyroot tileEntity) {
    }

}
