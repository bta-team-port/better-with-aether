package teamport.aether.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;
import teamport.aether.entity.projectile.ProjectileNeedle;

@Environment(EnvType.CLIENT)
public class EntityRendererNeedle extends EntityRenderer<ProjectileNeedle> {
    public EntityRendererNeedle() {}

    @Override
    public void render(Tessellator tessellator, ProjectileNeedle needle, double x, double y, double z, float yaw, float partialTick) {
        this.bindTexture("/assets/aether/textures/entity/needle.png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotatef(needle.yRotO + (needle.yRot - needle.yRotO) * partialTick - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(needle.xRotO + (needle.xRot - needle.xRotO) * partialTick, 0.0F, 0.0F, 1.0F);
        float bodyMinU = 0.0F;
        float bodyMaxU = 0.5F;
        float bodyMinV = 0.0f / 32.0F;
        float bodyMaxV = 5.0F / 32.0F;
        float tailMinU = 0.0F;
        float tailMaxU = 0.15625F;
        float tailMinV = 5.0F / 32.0F;
        float tailMaxV = 10.0F / 32.0F;
        float scale = 0.05625F;
        GL11.glEnable(32826);
        float shakeAmount = (float) needle.getShake() - partialTick;
        if (shakeAmount > 0.0F) {
            float shakeAngle = -MathHelper.sin(shakeAmount * 3.0F) * shakeAmount;
            GL11.glRotatef(shakeAngle, 0.0F, 0.0F, 1.0F);
        }

        GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
        GL11.glNormal3f(scale, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-7.0, -2.0, -2.0, tailMinU, tailMinV);
        tessellator.addVertexWithUV(-7.0, -2.0, 2.0, tailMaxU, tailMinV);
        tessellator.addVertexWithUV(-7.0, 2.0, 2.0, tailMaxU, tailMaxV);
        tessellator.addVertexWithUV(-7.0, 2.0, -2.0, tailMinU, tailMaxV);
        tessellator.draw();
        GL11.glNormal3f(-scale, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-7.0, 2.0, -2.0, tailMinU, tailMinV);
        tessellator.addVertexWithUV(-7.0, 2.0, 2.0, tailMaxU, tailMinV);
        tessellator.addVertexWithUV(-7.0, -2.0, 2.0, tailMaxU, tailMaxV);
        tessellator.addVertexWithUV(-7.0, -2.0, -2.0, tailMinU, tailMaxV);
        tessellator.draw();


        for (int i = 0; i < 8; ++i) {
            GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, scale);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-8.0, -2.0, 0.0, bodyMinU, bodyMinV);
            tessellator.addVertexWithUV(8.0, -2.0, 0.0, bodyMaxU, bodyMinV);
            tessellator.addVertexWithUV(8.0, 2.0, 0.0, bodyMaxU, bodyMaxV);
            tessellator.addVertexWithUV(-8.0, 2.0, 0.0, bodyMinU, bodyMaxV);
            tessellator.draw();
        }

        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
}
