package bta.aether.entity.renderer;

import bta.aether.entity.AetherEntities;
import bta.aether.entity.EntityZephyr;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class ZephyrRenderer extends LivingRenderer<EntityZephyr> {

    public ZephyrRenderer(ModelBase model, float shadowSize) {
        super(model, 0.5f);
        this.setRenderPassModel(AetherEntities.modelZephyr);
    }

    @Override
    protected void preRenderCallback(EntityZephyr entity, float f) {
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
}
