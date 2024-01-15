package bta.aether.entity.renderer;

import bta.aether.entity.AetherEntities;
import bta.aether.entity.EntityMoa;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class MoaRenderer extends LivingRenderer<EntityMoa> {

    public MoaRenderer(ModelBase model, float shadowSize) {
        super(model, shadowSize);
        this.setRenderPassModel(AetherEntities.modelMoa);
    }

    @Override
    protected void preRenderCallback(EntityMoa entity, float f) {
        GL11.glScalef(1.0f, 1.0f, 1.0f);
    }

}
