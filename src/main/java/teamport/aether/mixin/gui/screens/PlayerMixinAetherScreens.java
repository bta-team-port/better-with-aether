package teamport.aether.mixin.gui.screens;

import net.minecraft.core.block.entity.TileEntitySign;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.block.entity.TileEntityEnchanter;
import teamport.aether.block.entity.TileEntityFreezer;
import teamport.aether.block.entity.TileEntityIncubator;
import teamport.aether.gui.AetherScreens;

@Mixin(value = Player.class)
public abstract class PlayerMixinAetherScreens implements AetherScreens {
    @Override
    public void aether$displayEnchanterScreen(TileEntityEnchanter tileEntity) {}
    @Override
    public void aether$displayFreezerScreen(TileEntityFreezer tileEntity) {}
    @Override
    public void aether$displayIncubatorScreen(TileEntityIncubator tileEntity) {}
    @Override
    public void aether$displaySignSkyrootEditorScreen(TileEntitySign tileEntity) {}
}
