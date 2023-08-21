package bta.aether.entity;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;

public class ArrowFlamingRenderer extends EntityRenderer<EntityArrowFlaming> {
    public ArrowFlamingRenderer() {
    }

    @Override
    public void doRender(EntityArrowFlaming entity, double d, double e, double f, float g, float h) {

    }

    public void renderArrowFlaming(EntityArrow arrow, double x, double y, double z, float yaw, float renderPartialTicks) {
        if (arrow.yRotO != 0.0F || arrow.xRotO != 0.0F) {
            this.loadTexture("/assets/aether/other/FlamingArrows.png");
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y, (float)z);
            GL11.glRotatef(arrow.yRotO + (arrow.yRot - arrow.yRotO) * renderPartialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(arrow.xRotO + (arrow.xRot - arrow.xRotO) * renderPartialTicks, 0.0F, 0.0F, 1.0F);
            Tessellator tessellator = Tessellator.instance;
            byte arrowType;
            if (arrow.getArrowType() == 4) {
                arrowType = 4;
            } else if (arrow.getArrowType() == 4) {
                arrowType = 4;
            } else {
                arrowType = 4;
            }

            float bodyMinU = 0.0F;
            float bodyMaxU = 0.5F;
            float bodyMinV = (float)(arrowType * 10) / 32.0F;
            float bodyMaxV = (float)(5 + arrowType * 10) / 32.0F;
            float tailMinU = 0.0F;
            float tailMaxU = 0.15625F;
            float tailMinV = (float)(5 + arrowType * 10) / 32.0F;
            float tailMaxV = (float)(10 + arrowType * 10) / 32.0F;
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
            tessellator.addVertexWithUV(-7.0, -2.0, -2.0, (double)tailMinU, (double)tailMinV);
            tessellator.addVertexWithUV(-7.0, -2.0, 2.0, (double)tailMaxU, (double)tailMinV);
            tessellator.addVertexWithUV(-7.0, 2.0, 2.0, (double)tailMaxU, (double)tailMaxV);
            tessellator.addVertexWithUV(-7.0, 2.0, -2.0, (double)tailMinU, (double)tailMaxV);
            tessellator.draw();
            GL11.glNormal3f(-scale, 0.0F, 0.0F);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-7.0, 2.0, -2.0, (double)tailMinU, (double)tailMinV);
            tessellator.addVertexWithUV(-7.0, 2.0, 2.0, (double)tailMaxU, (double)tailMinV);
            tessellator.addVertexWithUV(-7.0, -2.0, 2.0, (double)tailMaxU, (double)tailMaxV);
            tessellator.addVertexWithUV(-7.0, -2.0, -2.0, (double)tailMinU, (double)tailMaxV);
            tessellator.draw();

            for(int i = 0; i < 4; ++i) {
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glNormal3f(0.0F, 0.0F, scale);
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(-8.0, -2.0, 0.0, (double)bodyMinU, (double)bodyMinV);
                tessellator.addVertexWithUV(8.0, -2.0, 0.0, (double)bodyMaxU, (double)bodyMinV);
                tessellator.addVertexWithUV(8.0, 2.0, 0.0, (double)bodyMaxU, (double)bodyMaxV);
                tessellator.addVertexWithUV(-8.0, 2.0, 0.0, (double)bodyMinU, (double)bodyMaxV);
                tessellator.draw();
            }

            GL11.glDisable(32826);
            GL11.glPopMatrix();
        }
    }

    public void doRender(EntityArrow entity, double x, double y, double z, float yaw, float renderPartialTicks) {
        this.renderArrowFlaming(entity, x, y, z, yaw, renderPartialTicks);
    }
}

