package teamport.aether.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.HudIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import teamport.aether.effect.render.EffectRenderer;
import teamport.aether.gui.IHudVisibility;

@Mixin(value = HudIngame.class, remap = false)
public class HudMixinEffect {

    @Shadow
    protected Minecraft mc;

    @Inject(method = "renderGameOverlay(FZII)V", at = @At(value ="TAIL"))
    public void endRenderGameOverlay(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {
        int width = this.mc.resolution.getScaledWidthScreenCoords();
        int height = this.mc.resolution.getHeightScreenCoords();
        for (EffectStack effectStack : ((IHasEffects)mc.thePlayer).getContainer().getEffects()) {
            if (!(effectStack.getEffect() instanceof IHudVisibility) || !effectStack.isActive()) {
                continue;
            }
            EffectRenderer renderer = ((IHudVisibility)effectStack.getEffect()).getRenderer();
            renderer.drawEffect(width,height,effectStack, (IHudVisibility) effectStack.getEffect());
        }
    }
}
