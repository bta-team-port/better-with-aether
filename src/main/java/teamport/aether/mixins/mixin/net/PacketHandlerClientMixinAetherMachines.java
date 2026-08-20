package teamport.aether.mixins.mixin.net;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.net.handler.PacketHandlerClient;
import net.minecraft.core.net.packet.PacketContainerOpen;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherConfig;
import teamport.aether.block.entity.TileEntityEnchanter;
import teamport.aether.block.entity.TileEntityFreezer;
import teamport.aether.block.entity.TileEntityIncubator;
import teamport.aether.gui.AetherScreens;

@Environment(EnvType.CLIENT)
@Mixin(PacketHandlerClient.class)
public abstract class PacketHandlerClientMixinAetherMachines {
    @Final
    @Shadow
    private Minecraft mc;
    @Inject(method = "handleContainerOpen(Lnet/minecraft/core/net/packet/PacketContainerOpen;)V", at = @At("TAIL"))
    private void handleAetherMachines(@NonNull PacketContainerOpen packetContainerOpen, CallbackInfo ci) {
        PlayerLocal playerLocal = this.mc.thePlayer;
        AetherScreens playerScreen = (AetherScreens) playerLocal;
        if (packetContainerOpen.inventoryType == AetherConfig.ENCHANTER_SCREEN_ID) {
            TileEntityEnchanter machine = new TileEntityEnchanter();
            playerScreen.aether$displayEnchanterScreen(machine);
            playerLocal.containerMenu.containerId = packetContainerOpen.windowId;
        }
        if (packetContainerOpen.inventoryType == AetherConfig.FREEZER_SCREEN_ID) {
            TileEntityFreezer machine = new TileEntityFreezer();
            playerScreen.aether$displayFreezerScreen(machine);
            playerLocal.containerMenu.containerId = packetContainerOpen.windowId;
        }
        if (packetContainerOpen.inventoryType == AetherConfig.INCUBATOR_SCREEN_ID) {
            TileEntityIncubator machine = new TileEntityIncubator();
            playerScreen.aether$displayIncubatorScreen(machine);
            playerLocal.containerMenu.containerId = packetContainerOpen.windowId;
        }
    }
}
