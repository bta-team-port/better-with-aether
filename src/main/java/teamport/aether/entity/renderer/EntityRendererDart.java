package teamport.aether.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.projectile.ProjectileDart;

@Environment(EnvType.CLIENT)
public class EntityRendererDart extends EntityRenderer<ProjectileDart> {
    public EntityRendererDart() {}

    @Override
    public void render(@NonNull TessellatorGeneral tessellator, @NonNull ProjectileDart dart, double x, double y, double z, float yaw, float partialTick) {
        this.bindTexture("/assets/aether/textures/entity/darts.png");
        GLRenderer.pushFrame();
        GLRenderer.modelM4f().translate((float) x, (float) y, (float) z);
        GLRenderer.modelM4f().rotate((float) Math.toRadians(dart.yRotO + (dart.yRot - dart.yRotO) * partialTick - 90.0F), 0.0F, 1.0F, 0.0F);
        GLRenderer.modelM4f().rotate((float) Math.toRadians(dart.xRotO + (dart.xRot - dart.xRotO) * partialTick), 0.0F, 0.0F, 1.0F);
        int dartType = dart.getDartType();
        float bodyMinU = 0.0F;
        float bodyMaxU = 0.5F;
        float bodyMinV = (dartType * 10) / 32.0F;
        float bodyMaxV = (5 + dartType * 10) / 32.0F;
        float tailMinU = 0.0F;
        float tailMaxU = 0.15625F;
        float tailMinV = (5 + dartType * 10) / 32.0F;
        float tailMaxV = (10 + dartType * 10) / 32.0F;
        float scale = 0.05625F;
        float shakeAmount = dart.getShake() - partialTick;
        if (shakeAmount > 0.0F) {
            float shakeAngle = -MathHelper.sin(shakeAmount * 3.0F) * shakeAmount;
            GLRenderer.modelM4f().rotate((float) Math.toRadians(shakeAngle), 0.0F, 0.0F, 1.0F);
        }

        GLRenderer.modelM4f().rotate((float) Math.toRadians(45.0F), 1.0F, 0.0F, 0.0F);
        GLRenderer.modelM4f().scale(scale, scale, scale);
        GLRenderer.modelM4f().translate(-4.0F, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(scale, 0.0F, 0.0F);
        tessellator.addVertexWithUV(-7.0, -2.0, -2.0, tailMinU, tailMinV);
        tessellator.addVertexWithUV(-7.0, -2.0, 2.0, tailMaxU, tailMinV);
        tessellator.addVertexWithUV(-7.0, 2.0, 2.0, tailMaxU, tailMaxV);
        tessellator.addVertexWithUV(-7.0, 2.0, -2.0, tailMinU, tailMaxV);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-scale, 0.0F, 0.0F);
        tessellator.addVertexWithUV(-7.0, 2.0, -2.0, tailMinU, tailMinV);
        tessellator.addVertexWithUV(-7.0, 2.0, 2.0, tailMaxU, tailMinV);
        tessellator.addVertexWithUV(-7.0, -2.0, 2.0, tailMaxU, tailMaxV);
        tessellator.addVertexWithUV(-7.0, -2.0, -2.0, tailMinU, tailMaxV);
        tessellator.draw();


        for (int i = 0; i < 8; ++i) {
            GLRenderer.modelM4f().rotate((float) Math.toRadians(45.0F), 1.0F, 0.0F, 0.0F);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, scale);
            tessellator.addVertexWithUV(-8.0, -2.0, 0.0, bodyMinU, bodyMinV);
            tessellator.addVertexWithUV(8.0, -2.0, 0.0, bodyMaxU, bodyMinV);
            tessellator.addVertexWithUV(8.0, 2.0, 0.0, bodyMaxU, bodyMaxV);
            tessellator.addVertexWithUV(-8.0, 2.0, 0.0, bodyMinU, bodyMaxV);
            tessellator.draw();
        }

        GLRenderer.popFrame();
    }
}
