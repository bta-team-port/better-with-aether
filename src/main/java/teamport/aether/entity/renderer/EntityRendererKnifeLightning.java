package teamport.aether.entity.renderer;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;
import teamport.aether.entity.projectile.ProjectileKnifeLightning;

public class EntityRendererKnifeLightning extends EntityRenderer<ProjectileKnifeLightning> {
    public EntityRendererKnifeLightning() {
    }

    public void render(Tessellator tessellator, ProjectileKnifeLightning knife, double x, double y, double z, float yaw, float partialTick) {
        this.doRenderKnife(knife, x, y, z, yaw, partialTick);
    }

    public void doRenderKnife(ProjectileKnifeLightning knife, double x, double y, double z, float yaw, float partialTick) {
        float texMinX = 0;
        float texMaxX = 1;
        float texMinY = 0;
        float texMaxY = 1;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-(knife.xRotO + (knife.xRot - knife.xRotO) * partialTick), 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);

        this.bindTexture("/assets/aether/textures/item/tool_knife_lightning.png");
        Tessellator tessellator = Tessellator.instance;
        float f4 = 1.0F;
        GL11.glEnable(32826);
        float f8 = 0.0625F;
        GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, texMaxX, texMaxY);
        tessellator.addVertexWithUV(f4, 0.0, 0.0, texMinX, texMaxY);
        tessellator.addVertexWithUV(f4, 0.0, 1.0, texMinX, texMinY);
        tessellator.addVertexWithUV(0.0, 0.0, 1.0, texMaxX, texMinY);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        tessellator.addVertexWithUV(0.0, 0.0F - f8, 1.0, texMaxX, texMinY);
        tessellator.addVertexWithUV(f4, 0.0F - f8, 1.0, texMinX, texMinY);
        tessellator.addVertexWithUV(f4, 0.0F - f8, 0.0, texMinX, texMaxY);
        tessellator.addVertexWithUV(0.0, 0.0F - f8, 0.0, texMaxX, texMaxY);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);

        int l;
        float f12;
        float f16;
        float f20;
        for (l = 0; l < 16; ++l) {
            f12 = (float) l / 16.0F;
            f16 = texMaxX + (texMinX - texMaxX) * f12 - 0.001953125F;
            f20 = f4 * f12;
            tessellator.addVertexWithUV(f20, 0.0F - f8, 0.0, f16, texMaxY);
            tessellator.addVertexWithUV(f20, 0.0, 0.0, f16, texMaxY);
            tessellator.addVertexWithUV(f20, 0.0, 1.0, f16, texMinY);
            tessellator.addVertexWithUV(f20, 0.0F - f8, 1.0, f16, texMinY);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);

        for (l = 0; l < 16; ++l) {
            f12 = (float) l / 16.0F;
            f16 = texMaxX + (texMinX - texMaxX) * f12 - 0.001953125F;
            f20 = f4 * f12 + 0.0625F;
            tessellator.addVertexWithUV(f20, 0.0F - f8, 1.0, f16, texMinY);
            tessellator.addVertexWithUV(f20, 0.0, 1.0, f16, texMinY);
            tessellator.addVertexWithUV(f20, 0.0, 0.0, f16, texMaxY);
            tessellator.addVertexWithUV(f20, 0.0F - f8, 0.0, f16, texMaxY);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);

        for (l = 0; l < 16; ++l) {
            f12 = (float) l / 16.0F;
            f16 = texMaxY + (texMinY - texMaxY) * f12 - 0.001953125F;
            f20 = f4 * f12 + 0.0625F;
            tessellator.addVertexWithUV(0.0, 0.0, f20, texMaxX, f16);
            tessellator.addVertexWithUV(f4, 0.0, f20, texMinX, f16);
            tessellator.addVertexWithUV(f4, 0.0F - f8, f20, texMinX, f16);
            tessellator.addVertexWithUV(0.0, 0.0F - f8, f20, texMaxX, f16);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);

        for (l = 0; l < 16; ++l) {
            f12 = (float) l / 16.0F;
            f16 = texMaxY + (texMinY - texMaxY) * f12 - 0.001953125F;
            f20 = f4 * f12;
            tessellator.addVertexWithUV(f4, 0.0, f20, texMinX, f16);
            tessellator.addVertexWithUV(0.0, 0.0, f20, texMaxX, f16);
            tessellator.addVertexWithUV(0.0, 0.0F - f8, f20, texMaxX, f16);
            tessellator.addVertexWithUV(f4, 0.0F - f8, f20, texMinX, f16);
        }

        tessellator.draw();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
}
