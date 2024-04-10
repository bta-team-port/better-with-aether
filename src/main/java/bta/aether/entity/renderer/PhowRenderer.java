package bta.aether.entity.renderer;

import bta.aether.entity.EntityPhow;
import bta.aether.entity.model.ModelPhow2;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;

public class PhowRenderer extends LivingRenderer<EntityPhow> {
    public PhowRenderer(ModelBase modelbase, ModelBase modelbase1, float f) {
        super(modelbase, f);
        this.setRenderPassModel(modelbase1);
    }

    protected boolean setWoolColorAndRender(EntityPhow cow, int i, float f) {
        if (i == 0) {
            this.loadTexture("/assets/aether/mobs/Wings.png");
            ModelPhow2.cow = cow;
            return true;
        } else {
            return false;
        }
    }

    protected boolean shouldRenderPass(EntityPhow entity, int renderPass, float partialTick) {
        return this.setWoolColorAndRender(entity, renderPass, partialTick);
    }

}
