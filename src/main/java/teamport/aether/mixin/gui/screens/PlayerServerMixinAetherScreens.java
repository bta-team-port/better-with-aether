package teamport.aether.mixin.gui.screens;

import net.minecraft.core.net.packet.PacketContainerOpen;
import net.minecraft.server.entity.player.PlayerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import teamport.aether.AetherConfig;
import teamport.aether.entity.tile.TileEntityEnchanter;
import teamport.aether.entity.tile.TileEntityFreezer;
import teamport.aether.entity.tile.TileEntityIncubator;
import teamport.aether.entity.tile.TileEntitySignSkyroot;
import teamport.aether.gui.AetherScreens;
import teamport.aether.gui.machine.enchanter.MenuEnchanter;
import teamport.aether.gui.machine.freezer.MenuFreezer;
import teamport.aether.gui.machine.incubator.MenuIncubator;

@Mixin(value = PlayerServer.class, remap = false)
public abstract class PlayerServerMixinAetherScreens implements AetherScreens {

    @Shadow
    private int currentWindowId;

    @Shadow
    protected abstract void getNextWindowId();

    @Unique
    private final PlayerServer thisAs = (PlayerServer) (Object) this;

    @Override
    public void aether$displayEnchanterScreen(TileEntityEnchanter tileEntity) {
        this.getNextWindowId();
        thisAs.playerNetServerHandler.sendPacket(new PacketContainerOpen(this.currentWindowId, AetherConfig.ENCHANTER_SCREEN_ID, tileEntity.getNameTranslationKey(), tileEntity.getContainerSize()));
        thisAs.craftingInventory.onCraftGuiClosed(thisAs);
        thisAs.craftingInventory = new MenuEnchanter(thisAs.inventory, tileEntity);
        thisAs.craftingInventory.containerId = this.currentWindowId;
        thisAs.craftingInventory.addSlotListener(thisAs);
    }

    @Override
    public void aether$displayFreezerScreen(TileEntityFreezer tileEntity) {
        this.getNextWindowId();
        thisAs.playerNetServerHandler.sendPacket(new PacketContainerOpen(this.currentWindowId, AetherConfig.FREEZER_SCREEN_ID, tileEntity.getNameTranslationKey(), tileEntity.getContainerSize()));
        thisAs.craftingInventory.onCraftGuiClosed(thisAs);
        thisAs.craftingInventory = new MenuFreezer(thisAs.inventory, tileEntity);
        thisAs.craftingInventory.containerId = this.currentWindowId;
        thisAs.craftingInventory.addSlotListener(thisAs);

    }

    @Override
    public void aether$displayIncubatorScreen(TileEntityIncubator tileEntity) {
        this.getNextWindowId();
        thisAs.playerNetServerHandler.sendPacket(new PacketContainerOpen(this.currentWindowId, AetherConfig.INCUBATOR_SCREEN_ID, tileEntity.getNameTranslationKey(), tileEntity.getContainerSize()));
        thisAs.craftingInventory.onCraftGuiClosed(thisAs);
        thisAs.craftingInventory = new MenuIncubator(thisAs.inventory, tileEntity);
        thisAs.craftingInventory.containerId = this.currentWindowId;
        thisAs.craftingInventory.addSlotListener(thisAs);

    }

    // this might be misplaced
    @Override
    public void aether$displaySignSkyrootEditorScreen(TileEntitySignSkyroot tileEntity) {
    }
}
