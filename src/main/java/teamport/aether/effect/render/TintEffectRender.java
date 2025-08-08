package teamport.aether.effect.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import teamport.aether.gui.IHudVisibility;

public class TintEffectRender implements EffectRenderer{

    private final String vignette;
    private final int tint;

    TintEffectRender(String vignette, int tint){
        this.vignette = vignette;
        this.tint = tint;
    }

    public void drawEffect(int width, int height, EffectStack effectStack, IHudVisibility effect) {
        float alpha = calcAlpha(effectStack);
        if (Minecraft.getMinecraft().gameSettings.vignette.value) {
            drawVignette(width, height, vignette, alpha);
        }else{
            drawTint(width, height, tint, alpha);
        }
    }

    private void drawTint(int width, int height, int tint, float alpha) {
        Tessellator tessellator = Tessellator.instance;
        float r = (float)(tint >> 16 & 0xFF) / 255.0f;
        float g = (float)(tint >> 8 & 0xFF) / 255.0f;
        float b = (float)(tint & 0xFF) / 255.0f;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(r, g, b, alpha);
        GL11.glTranslated(0, 0, -1);
        tessellator.startDrawingQuads();
        tessellator.addVertex(0, height, 0.0);
        tessellator.addVertex(width, height, 0.0);
        tessellator.addVertex(width, 0, 0.0);
        tessellator.addVertex(0, 0, 0.0);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void drawVignette(int width, int height, String vignette, float alpha) {
        TextureManager textureManager = Minecraft.getMinecraft().textureManager;
        Tessellator tessellator = Tessellator.instance;
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        textureManager.bindTexture(textureManager.loadTexture(vignette));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
        int z = -1000;;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0, 0, z, 0, 0);
        tessellator.addVertexWithUV(0, height, z, 0, 1);
        tessellator.addVertexWithUV(width, height, z, 1, 1);
        tessellator.addVertexWithUV(width, 0, z, 1, 0);
        tessellator.draw();
        GL11.glPopMatrix();

    }

    public float calcAlpha(EffectStack effectStack) {
        float currentAmount = (float) effectStack.getDuration() * (effectStack.getAmount() - 1);
        float totalTime = (float) effectStack.getDuration() * effectStack.getEffect().getMaxStack();
        return (currentAmount + effectStack.getTimeLeft()) / totalTime;
    }
}
