package bta.aether.entity.renderer;

import bta.aether.entity.EntityAerwhale;
import bta.aether.entity.model.ModelAerwhale;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class AerwhaleRenderer extends LivingRenderer<EntityAerwhale> {
    private final ModelBase model = new ModelAerwhale();

    public AerwhaleRenderer(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }


    public void doRender(EntityAerwhale entity, double d, double e, double f, float g, float h) {
        GL11.glPushMatrix();
        this.loadTexture("/assets/aether/mobs/aerwhale/0.png");
        GL11.glTranslatef((float) d, (float) e, (float) f);
        GL11.glRotatef(90.0F - entity.yRot, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F - entity.xRot, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(10.0F, 10.0F, 10.0F);
        this.model.render(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
}
