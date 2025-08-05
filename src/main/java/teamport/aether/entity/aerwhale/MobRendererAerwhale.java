package teamport.aether.entity.aerwhale;

import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;

public class MobRendererAerwhale extends MobRenderer<MobAerwhale> {

    public MobRendererAerwhale(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    public void render(Tessellator tessellator, MobAerwhale aerwhale, double x, double y, double z, float yaw, float partialTick) {
        GL11.glPushMatrix();
        this.bindTexture("/assets/aether/textures/entity/aerwhale/0.png");
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotatef(90.0F - aerwhale.yRot, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F - aerwhale.xRot, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        this.mainModel.render(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 1.0f);
        GL11.glPopMatrix();
    }

}
