package teamport.aether.mixins.mixin.fix;

import org.spongepowered.asm.mixin.Mixin;
import turniplabs.halplibe.helper.network.NetworkHandler;

@Mixin(NetworkHandler.class)
public abstract class NetworkHandlerMixinFixNativePackets {
    //Why does this exist?
}
