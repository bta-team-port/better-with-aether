package bta.aether.entity.renderer;

import bta.aether.entity.AetherEntities;
import bta.aether.entity.EntityMimic;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;

public class MimicRenderer extends LivingRenderer<EntityMimic> {
    public MimicRenderer(ModelBase model, float shadowSize) {
        super(model, shadowSize);
        this.setRenderPassModel(AetherEntities.modelMimic);
    }
}
