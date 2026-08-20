package teamport.aether.mixins.mixin.gui.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.net.packet.PacketContainerOpen;
import net.minecraft.server.entity.player.PlayerServer;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.AetherConfig;
import teamport.aether.block.entity.TileEntityEnchanter;
import teamport.aether.block.entity.TileEntityFreezer;
import teamport.aether.block.entity.TileEntityIncubator;
import teamport.aether.gui.AetherScreens;
import teamport.aether.gui.machine.enchanter.MenuEnchanter;
import teamport.aether.gui.machine.freezer.MenuFreezer;
import teamport.aether.gui.machine.incubator.MenuIncubator;

@Environment(EnvType.SERVER)
@Mixin(PlayerServer.class)
public abstract class PlayerServerMixinAetherScreens implements AetherScreens {
    @Shadow
    private int currentWindowId;
    @Shadow
    protected abstract void getNextWindowId();
    @Override
    public void aether$displayEnchanterScreen(@NonNull TileEntityEnchanter tileEntity) {
        this.getNextWindowId();
        PlayerServer playerServer = (PlayerServer) (Object) this;
        playerServer.playerNetServerHandler.sendPacket(new PacketContainerOpen(this.currentWindowId, AetherConfig.ENCHANTER_SCREEN_ID, tileEntity.getNameTranslationKey(), tileEntity.getContainerSize()));
        playerServer.containerMenu.onCraftGuiClosed(playerServer);
        playerServer.containerMenu = new MenuEnchanter(playerServer.inventory, tileEntity);
        playerServer.containerMenu.containerId = this.currentWindowId;
        playerServer.containerMenu.addSlotListener(playerServer);
    }
    @Override
    public void aether$displayFreezerScreen(@NonNull TileEntityFreezer tileEntity) {
        this.getNextWindowId();
        PlayerServer playerServer = (PlayerServer) (Object) this;
        playerServer.playerNetServerHandler.sendPacket(new PacketContainerOpen(this.currentWindowId, AetherConfig.FREEZER_SCREEN_ID, tileEntity.getNameTranslationKey(), tileEntity.getContainerSize()));
        playerServer.containerMenu.onCraftGuiClosed(playerServer);
        playerServer.containerMenu = new MenuFreezer(playerServer.inventory, tileEntity);
        playerServer.containerMenu.containerId = this.currentWindowId;
        playerServer.containerMenu.addSlotListener(playerServer);
    }
    @Override
    public void aether$displayIncubatorScreen(@NonNull TileEntityIncubator tileEntity) {
        this.getNextWindowId();
        PlayerServer playerServer = (PlayerServer) (Object) this;
        playerServer.playerNetServerHandler.sendPacket(new PacketContainerOpen(this.currentWindowId, AetherConfig.INCUBATOR_SCREEN_ID, tileEntity.getNameTranslationKey(), tileEntity.getContainerSize()));
        playerServer.containerMenu.onCraftGuiClosed(playerServer);
        playerServer.containerMenu = new MenuIncubator(playerServer.inventory, tileEntity);
        playerServer.containerMenu.containerId = this.currentWindowId;
        playerServer.containerMenu.addSlotListener(playerServer);
    }
}
