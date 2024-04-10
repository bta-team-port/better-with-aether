package bta.aether.entity.renderer;

import bta.aether.entity.EntityPhyg;
import bta.aether.entity.model.ModelPhyg2;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;

public class PhygRenderer extends LivingRenderer<EntityPhyg> {
    public PhygRenderer(ModelBase modelbase, ModelBase modelbase1, float f) {
        super(modelbase, f);
        this.setRenderPassModel(modelbase1);
    }

    protected boolean setWoolColorAndRender(EntityPhyg pig, int i, float f) {
        if (i == 0) {
            this.loadTexture("/assets/aether/mobs/Wings.png");
            ModelPhyg2.pig = pig;
            return true;
        } else {
            return false;
        }
    }

    protected boolean shouldRenderPass(EntityPhyg entity, int renderPass, float partialTick) {
        return this.setWoolColorAndRender(entity, renderPass, partialTick);
    }
}
