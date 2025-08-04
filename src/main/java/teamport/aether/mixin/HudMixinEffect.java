package teamport.aether.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.HudIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import teamport.aether.effect.AetherEffects;
import teamport.aether.effect.render.EffectRenderer;
import teamport.aether.gui.IHudVisibility;

@Mixin(value = HudIngame.class, remap = false)
public abstract class HudMixinEffect {

    @Shadow
    protected Minecraft mc;

    @Inject(method = "renderGameOverlay(FZII)V", at = @At(value ="TAIL"))
    public void endRenderGameOverlay(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {
        int width = this.mc.resolution.getScaledWidthScreenCoords();
        int height = this.mc.resolution.getScaledHeightScreenCoords();
        EffectStack stack = AetherEffects.resolveDominantEffect(mc.thePlayer);
        if(stack != null && stack.getEffect() instanceof IHudVisibility){
            EffectRenderer renderer = ((IHudVisibility)stack.getEffect()).getRenderer();
            renderer.drawEffect(width, height,stack, (IHudVisibility) stack.getEffect());
        }

    }
}
