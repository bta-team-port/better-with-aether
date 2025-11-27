package teamport.aether.mixin.gui.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.entity.tile.*;
import teamport.aether.gui.AetherScreens;
import teamport.aether.gui.MenuLorebook;
import teamport.aether.gui.ScreenLorebook;
import teamport.aether.gui.machine.enchanter.ScreenEnchanter;
import teamport.aether.gui.machine.freezer.ScreenFreezer;
import teamport.aether.gui.machine.incubator.ScreenIncubator;

@Environment(EnvType.CLIENT)
@Mixin(value = PlayerLocal.class, remap = false)
public abstract class PlayerLocalMixinAetherScreens extends Player implements AetherScreens {
    protected PlayerLocalMixinAetherScreens(World world) {
        super(world);
    }
    @Shadow
    protected Minecraft mc;
    @Override
    public void aether$displayEnchanterScreen(TileEntityEnchanter tileEntity) {
        this.mc.displayScreen(new ScreenEnchanter(this.inventory, tileEntity));
    }
    @Override
    public void aether$displayFreezerScreen(TileEntityFreezer tileEntity) {
        this.mc.displayScreen(new ScreenFreezer(this.inventory, tileEntity));
    }
    @Override
    public void aether$displayIncubatorScreen(TileEntityIncubator tileEntity) {
        this.mc.displayScreen(new ScreenIncubator(this.inventory, tileEntity));
    }
    @Override
    public void aether$displaySignSkyrootEditorScreen(TileEntitySignSkyroot tileEntity) {
        this.mc.displayScreen(new ScreenSignSkyrootEditor(tileEntity));
    }
    @Override
    public void aether$displayLorebookScreen(String loreID) {
        MenuLorebook menu = new MenuLorebook(this.inventory, loreID);
        this.mc.displayScreen(new ScreenLorebook(menu, loreID));
    }
}
