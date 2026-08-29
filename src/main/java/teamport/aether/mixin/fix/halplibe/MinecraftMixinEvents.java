package teamport.aether.mixin.fix.halplibe;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherClient;
import teamport.aether.AetherMod;

/**
 * @deprecated Will be deprecated in the next HalpLibe release (6.2.1).
 */
@Deprecated(forRemoval = true)
@Mixin(Minecraft.class)
public class MinecraftMixinEvents {

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/net/command/util/CommandHelper;init()V"))
    public void hudInitializationEntrypoint(CallbackInfo ci) {
        AetherClient.HUD_INIT.emit(Runnable::run);
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/Dimension;init()V", shift = At.Shift.AFTER))
    public void dimensionRegistry(CallbackInfo ci) {
        AetherMod.DIMENSION_REGISTRY.emit(Runnable::run);
    }

}
