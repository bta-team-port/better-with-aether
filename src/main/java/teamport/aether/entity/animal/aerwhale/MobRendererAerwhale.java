package teamport.aether.entity.animal.aerwhale;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class MobRendererAerwhale extends MobRenderer<MobAerwhale> {

    public MobRendererAerwhale(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    @Override
    public void render(Tessellator tessellator, MobAerwhale aerwhale, double x, double y, double z, float yaw, float partialTick) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        this.loadEntityTexture(aerwhale);
        float yRot = MathHelper.lerp(aerwhale.yRotO, aerwhale.yRot, partialTick);
        float xRot = MathHelper.lerp(aerwhale.xRotO, aerwhale.xRot, partialTick);
        GL11.glRotatef(90.0F - yRot, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F - xRot, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        this.mainModel.render(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 1.0f);
        GL11.glPopMatrix();
    }

    @Override
    public void renderPreview(Tessellator tessellator, MobAerwhale aerwhale, double x, double y, double z, float yaw, float partialTick) {
        GL11.glPushMatrix();
        GL11.glScalef(0.1F, 0.1F, 0.1F);
        super.renderPreview(tessellator, aerwhale, x - 2, y + 10, z, yaw, partialTick);
        GL11.glPopMatrix();
    }

}
