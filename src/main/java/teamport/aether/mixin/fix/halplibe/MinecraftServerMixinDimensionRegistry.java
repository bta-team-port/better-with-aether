package teamport.aether.mixin.fix.halplibe;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.AetherMod;

/**
 * @deprecated Will be deprecated in the next HalpLibe release (6.2.1).
 */
@Deprecated(forRemoval = true)
@Mixin(MinecraftServer.class)
public class MinecraftServerMixinDimensionRegistry {

    @Inject(method = "startServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/Dimension;init()V", shift = At.Shift.AFTER))
    public void dimensionRegistry(CallbackInfoReturnable<Boolean> cir) {
        AetherMod.DIMENSION_REGISTRY.emit(Runnable::run);
    }
}
