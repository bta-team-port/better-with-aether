package teamport.aether.effect.render;

import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import teamport.aether.gui.IHudVisibility;

public class RemedyEffectRenderer implements EffectRenderer {

    // TODO a circle that cleans the screen from center to edge of the effect
    @Override
    public void drawEffect(int width, int height, EffectStack effectStack, IHudVisibility effect) {
        float alpha = calcAlpha(effectStack);
        int tint = effect.getTint();
        float r = (float)(tint >> 16 & 0xFF) / 255.0f;
        float g = (float)(tint >> 8 & 0xFF) / 255.0f;
        float b = (float)(tint & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(r, g, b, alpha);
        tessellator.startDrawingQuads();
        tessellator.addVertex(0, height, 0.0);
        tessellator.addVertex(width, height, 0.0);
        tessellator.addVertex(width, 0, 0.0);
        tessellator.addVertex(0, 0, 0.0);
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    public void drawEffectK(int width, int height, EffectStack effectStack, IHudVisibility effect) {
        float alpha = 0.65F;
        int tint = effect.getTint();
        int centerX = width / 2;
        int centerY = height / 2;
        float percent = 1 - (float) effectStack.getTimeLeft() / (float) (effectStack.getDuration());
        float radius = percent * width;
        float r = (float) (tint >> 16 & 0xFF) / 255.0f;
        float g = (float) (tint >> 8 & 0xFF) / 255.0f;
        float b = (float) (tint & 0xFF) / 255.0f;

        // TODO a filter with a circle hole that widens during the effect util it covers the screen
    }

    private static float calcAlpha(EffectStack effectStack) {
        float percent = (float)effectStack.getTimeLeft() / (float)(effectStack.getDuration());
        return 0.65F * percent * percent;
    }
}
