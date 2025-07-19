package teamport.aether.mixin.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.gui.IAetherScreens;
import teamport.aether.gui.machine.enchanter.ScreenEnchanter;
import teamport.aether.gui.machine.freezer.ScreenFreezer;
import teamport.aether.gui.machine.incubator.ScreenIncubator;
import teamport.aether.tile.TileEntityEnchanter;
import teamport.aether.tile.TileEntityFreezer;
import teamport.aether.tile.TileEntityIncubator;

@Mixin(value = PlayerLocal.class, remap = false)
public abstract class PlayerLocalMixinAetherScreens extends Player implements IAetherScreens {

    @Shadow protected Minecraft mc;

    public PlayerLocalMixinAetherScreens(World world) {super(world);}

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
}
