package teamport.aether.effect.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import teamport.aether.gui.IHudVisibility;

public class PoisonEffectRenderer implements EffectRenderer {
    public void drawEffect(int width, int height, EffectStack effectStack, IHudVisibility effect) {
        Tessellator tessellator = Tessellator.instance;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings.vignette.value) {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            mc.textureManager.bindTexture(mc.textureManager.loadTexture("/assets/aether/textures/other/poisonvignette.png"));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, calcAlpha(effectStack));

            Tessellator t = Tessellator.instance;
            int z = -1000;
            t.startDrawingQuads();
            t.addVertexWithUV(0, 0, z, 0, 0);
            t.addVertexWithUV(0, height, z, 0, 1);
            t.addVertexWithUV(width, height, z, 1, 1);
            t.addVertexWithUV(width, 0, z, 1, 0);
            t.draw();
            GL11.glPopMatrix();

        } else {
            float alpha = calcAlpha(effectStack);
            int tint = effect.getTint();
            float r = (float)(tint >> 16 & 0xFF) / 255.0f;
            float g = (float)(tint >> 8 & 0xFF) / 255.0f;
            float b = (float)(tint & 0xFF) / 255.0f;

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
    }

    private static float calcAlpha(EffectStack effectStack) {
        float currentAmount = (float) effectStack.getDuration() * (effectStack.getAmount() - 1);
        float totalTime = (float) effectStack.getDuration() * effectStack.getEffect().getMaxStack();
        float percent = (currentAmount + effectStack.getTimeLeft()) / totalTime;
        return 0.35F +  percent / 3.0F;
    }
}
