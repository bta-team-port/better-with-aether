package bta.aether.mixin;

import bta.aether.block.IPortalExtras;
import bta.aether.catalyst.effects.AetherEffects;
import bta.aether.gui.GuiBossBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.render.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockPortal;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;

@Mixin(value = GuiIngame.class, remap = false)
public class GuiIngameMixin {

    @Shadow
    protected Minecraft mc;
    @Unique
    GuiBossBar guiBossBar;

    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;)V", at = @At(value ="TAIL"))
    void constructor(Minecraft minecraft, CallbackInfo ci) {
         guiBossBar = new GuiBossBar(minecraft);
    }


    @Inject(method = "renderGameOverlay(FZII)V", at = @At(value ="TAIL"))
    public void endRenderGameOverlay(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {
        int width = this.mc.resolution.scaledWidth;
        int height = this.mc.resolution.scaledHeight;
        for (EffectStack effectStack : ((IHasEffects)mc.thePlayer).getContainer().getEffects()) {
            if (effectStack.getEffect() == AetherEffects.poisonEffect && effectStack.isActive()) {
                float alpha = 0.35f + ((float) effectStack.getTimeLeft() / (float) effectStack.getDuration())/3.0f;
                drawRect(0, 0, width, height, alpha, 0x9A009A);
            } else if (effectStack.getEffect() == AetherEffects.remedyEffect && effectStack.isActive()) {
                float alpha = 0.35f + ((float) effectStack.getTimeLeft() / (float) effectStack.getDuration())/3.0f;
                drawRect(0, 0, width, height, alpha, 0x99FF99);
            }
        }
    }
    @Redirect(method = "renderPortalOverlay(FII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Block;texCoordToIndex(II)I"))
    private int customPortalOverlays(int x, int y){
        BlockPortal portal = (BlockPortal) Block.blocksList[mc.thePlayer.portalID];
        if (portal instanceof IPortalExtras){
            IPortalExtras dimensionSound = (IPortalExtras) portal;
            return dimensionSound.portalOverlayIndex();
        }
        return Block.texCoordToIndex(x,y);
    }

    @Unique
    void drawRect(int minX, int minY, int maxX, int maxY, float alpha, int rgb) {
        float a = alpha;
        float r = (float)(rgb >> 16 & 0xFF) / 255.0f;
        float g = (float)(rgb >> 8 & 0xFF) / 255.0f;
        float b = (float)(rgb & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(r, g, b, a);
        tessellator.startDrawingQuads();
        tessellator.addVertex(minX, maxY, 0.0);
        tessellator.addVertex(maxX, maxY, 0.0);
        tessellator.addVertex(maxX, minY, 0.0);
        tessellator.addVertex(minX, minY, 0.0);
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
}
