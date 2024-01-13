package bta.aether.mixin;

import bta.aether.world.AetherDimension;
import net.minecraft.client.render.LoadingScreenRenderer;
import net.minecraft.core.world.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LoadingScreenRenderer.class, remap = false)
public class LoadingScreenRendererMixin {
    @Shadow private String backgroundPath;

    @Inject(method = "updateLoadingBackground(Lnet/minecraft/core/world/Dimension;)V", at = @At("HEAD"), cancellable = true)
    private void customBackground(Dimension dimension, CallbackInfo ci){
        if (dimension == AetherDimension.dimensionAether){
            this.backgroundPath = "/assets/aether/block/Holystone.png";
            ci.cancel();
        }
    }
}
