package teamport.aether.mixin.fix;

import org.spongepowered.asm.mixin.Mixin;
import turniplabs.halplibe.helper.network.NetworkHandler;

@Mixin(value = NetworkHandler.class, remap = false)
public abstract class NetworkHandlerMixinFixNativePackets {
}
