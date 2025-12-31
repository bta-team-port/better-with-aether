package teamport.aether.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import teamport.aether.entity.projectile.ProjectileKnifeLightning;

@Environment(EnvType.CLIENT)
public class EntityRendererKnifeLightning extends EntityRenderer<ProjectileKnifeLightning> {
    public EntityRendererKnifeLightning() {}

    @Override
    public void render(Tessellator tessellator, ProjectileKnifeLightning knife, double x, double y, double z, float yaw, float partialTick) {
        this.doRenderKnife(knife, x, y, z, yaw, partialTick);
    }

    public void doRenderKnife(ProjectileKnifeLightning knife, double x, double y, double z, float yaw, float partialTick) {
        float texMinX = 0.0F;
        float texMaxX = 1.0F;
        float texMinY = 0.0F;
        float texMaxY = 1.0F;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-(knife.xRotO + (knife.xRot - knife.xRotO) * partialTick), 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);

        this.bindTexture("/assets/aether/textures/item/tool_knife_lightning.png");
        Tessellator tessellator = Tessellator.instance;
        float size = 1.0F;
        float thickness = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
        tessellator.startDrawingQuads();

        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        tessellator.addVertexWithUV(0.0, 0.0, 1.0, texMaxX, texMinY);
        tessellator.addVertexWithUV(size, 0.0, 1.0, texMinX, texMinY);
        tessellator.addVertexWithUV(size, 0.0, 0.0, texMinX, texMaxY);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, texMaxX, texMaxY);

        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        tessellator.addVertexWithUV(0.0, 0.0F - thickness, 0.0, texMaxX, texMaxY);
        tessellator.addVertexWithUV(size, 0.0F - thickness, 0.0, texMinX, texMaxY);
        tessellator.addVertexWithUV(size, 0.0F - thickness, 1.0, texMinX, texMinY);
        tessellator.addVertexWithUV(0.0, 0.0F - thickness, 1.0, texMaxX, texMinY);

        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        for (int i = 0; i < 16; ++i) {
            float t = i / 16.0F;
            float u = texMaxX + (texMinX - texMaxX) * t - 0.001953125F;
            float xPos = size * t;
            tessellator.addVertexWithUV(xPos, 0.0F - thickness, 1.0, u, texMinY);
            tessellator.addVertexWithUV(xPos, 0.0, 1.0, u, texMinY);
            tessellator.addVertexWithUV(xPos, 0.0, 0.0, u, texMaxY);
            tessellator.addVertexWithUV(xPos, 0.0F - thickness, 0.0, u, texMaxY);
        }

        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        for (int i = 0; i < 16; ++i) {
            float t = i / 16.0F;
            float u = texMaxX + (texMinX - texMaxX) * t - 0.001953125F;
            float xPos = size * t + 0.0625F;
            tessellator.addVertexWithUV(xPos, 0.0F - thickness, 0.0, u, texMaxY);
            tessellator.addVertexWithUV(xPos, 0.0, 0.0, u, texMaxY);
            tessellator.addVertexWithUV(xPos, 0.0, 1.0, u, texMinY);
            tessellator.addVertexWithUV(xPos, 0.0F - thickness, 1.0, u, texMinY);
        }

        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        for (int i = 0; i < 16; ++i) {
            float t = i / 16.0F;
            float v = texMaxY + (texMinY - texMaxY) * t - 0.001953125F;
            float zPos = size * t + 0.0625F;
            tessellator.addVertexWithUV(0.0, 0.0F - thickness, zPos, texMaxX, v);
            tessellator.addVertexWithUV(size, 0.0F - thickness, zPos, texMinX, v);
            tessellator.addVertexWithUV(size, 0.0, zPos, texMinX, v);
            tessellator.addVertexWithUV(0.0, 0.0, zPos, texMaxX, v);
        }

        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        for (int i = 0; i < 16; ++i) {
            float t = i / 16.0F;
            float v = texMaxY + (texMinY - texMaxY) * t - 0.001953125F;
            float zPos = size * t;
            tessellator.addVertexWithUV(size, 0.0F - thickness, zPos, texMinX, v);
            tessellator.addVertexWithUV(0.0, 0.0F - thickness, zPos, texMaxX, v);
            tessellator.addVertexWithUV(0.0, 0.0, zPos, texMaxX, v);
            tessellator.addVertexWithUV(size, 0.0, zPos, texMinX, v);
        }

        tessellator.draw();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

}
