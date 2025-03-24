package bta.aether.entity.renderer;

import bta.aether.entity.EntityValk;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;

@Environment(EnvType.CLIENT)
public class ValkRenderer extends LivingRenderer<EntityValk> {
    public ValkRenderer(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    @Override
    public void doRender(EntityValk entity, double d, double e, double f, float g, float h) {

    }
}
