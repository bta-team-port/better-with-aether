package bta.aether.entity;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;

public class GoldenDartRenderer extends EntityRenderer<EntityArrowFlaming> {
    @Override
    public void doRender(EntityArrowFlaming arrow, double x, double y, double z, float yaw, float renderPartialTicks) {
        if (arrow.yRotO != 0.0F || arrow.xRotO != 0.0F) {
            this.loadTexture("/assets/aether/mobs/entitygoldendart.png");
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y, (float)z);
            GL11.glRotatef(arrow.yRotO + (arrow.yRot - arrow.yRotO) * renderPartialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(arrow.xRotO + (arrow.xRot - arrow.xRotO) * renderPartialTicks, 0.0F, 0.0F, 1.0F);
            Tessellator tessellator = Tessellator.instance;
            float bodyMinU = 0.0F;
            float bodyMaxU = 0.5F;
            float bodyMinV = 0;
            float bodyMaxV = 5/ 32.0F;
            float tailMinU = 0.0F;
            float tailMaxU = 0.15625F;
            float tailMinV = 5/ 32.0F;
            float tailMaxV = 10 / 32.0F;
            float scale = 0.05625F;
            GL11.glEnable(32826);
            float shakeAmount = (float)arrow.arrowShake - renderPartialTicks;
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

            for(int i = 0; i < 4; ++i) {
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
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
}
