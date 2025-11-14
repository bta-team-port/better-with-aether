package teamport.aether.entity.vehicle.parachute;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class EntityRendererParachuteGold extends EntityRenderer<EntityParachuteGold> {
    private final ModelBase modelCloud;

    public EntityRendererParachuteGold() {
        this.shadowSize = 0.0F;
        this.modelCloud = new ModelParachute();
    }

    public void render(Tessellator tessellator, EntityParachuteGold entity, double x, double y, double z, float yaw, float partialTick) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotatef(0.0F, 0.0F, 0.0F, 0.0F);

        float f4 = 0.75F;
        GL11.glScalef(f4, f4, f4);
        GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
        this.bindTexture("/assets/aether/textures/entity/parachute_gold.png");

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, .75F);

        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.modelCloud.render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
}
