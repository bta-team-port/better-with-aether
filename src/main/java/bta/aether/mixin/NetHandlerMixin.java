package bta.aether.mixin;

import bta.aether.api.IAetherPacket;
import bta.aether.packet.PuffAerBunnyPacket;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = NetHandler.class, remap = false)
public class NetHandlerMixin implements IAetherPacket {

    public void aether$handlePuffBunny(PuffAerBunnyPacket packet) {
        this.handleInvalidPacket(packet);
    }

    @Shadow
    public void handleInvalidPacket(Packet packet) {
    }
}