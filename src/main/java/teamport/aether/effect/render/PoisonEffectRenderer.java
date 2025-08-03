package teamport.aether.effect.render;

import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import teamport.aether.gui.IHudVisibility;

public class PoisonEffectRenderer implements EffectRenderer {
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

    private static float calcAlpha(EffectStack effectStack) {
        float currentAmount = (float) effectStack.getDuration() * (effectStack.getAmount() - 1);
        float totalTime = (float) effectStack.getDuration() * effectStack.getEffect().getMaxStack();
        float percent = (currentAmount + effectStack.getTimeLeft()) / totalTime;
        return 0.35F +  percent / 3.0F;
    }
}
