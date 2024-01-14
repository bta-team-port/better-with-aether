package bta.aether.entity.renderer;

import bta.aether.entity.AetherEntities;
import bta.aether.entity.EntitySwet;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class SwetRenderer extends LivingRenderer<EntitySwet> {

    public SwetRenderer(ModelBase model, float shadowSize) {
        super(model, shadowSize);
        this.setRenderPassModel(AetherEntities.modelSwet);
    }

    @Override
    protected void preRenderCallback(EntitySwet entity, float f) {
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
}
