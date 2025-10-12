package teamport.aether.mixin.gui.screens;

import net.minecraft.client.render.LoadingScreenRenderer;
import net.minecraft.core.world.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;

@Mixin(value = LoadingScreenRenderer.class, remap = false)
public abstract class PortalLoadingScreenMixin {
    @Shadow
    private String backgroundPath;

    @Inject(method = "updateLoadingBackground(Lnet/minecraft/core/world/Dimension;)V", at = @At("HEAD"), cancellable = true)
    public void customBackground(Dimension dimension, CallbackInfo ci) {
        if (dimension == AetherDimension.AETHER) {
            this.backgroundPath = "/assets/aether/textures/gui/background-loading-aether.png";
            ci.cancel();
        }
    }
}
