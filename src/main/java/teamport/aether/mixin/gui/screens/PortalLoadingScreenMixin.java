package teamport.aether.mixin.gui.screens;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.LoadingScreenRenderer;
import net.minecraft.core.world.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.world.AetherDimension;

@Mixin(value = LoadingScreenRenderer.class, remap = false)
public abstract class PortalLoadingScreenMixin {
    @Expression("'/assets/minecraft/textures/gui/background.png'")
    @ModifyExpressionValue(method = "updateLoadingBackground(Lnet/minecraft/core/world/Dimension;)V", at = @At("MIXINEXTRAS:EXPRESSION"))
    public String customBackground(String original, Dimension dimension) {
        if (dimension == AetherDimension.getAether()) return "/assets/aether/textures/gui/background-loading-aether.png";
        return original;
    }
}
