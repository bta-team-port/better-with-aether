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

    @Override
    protected boolean shouldRenderPass(EntityPhyg entity, int renderPass, float partialTick) {

        // ModelPhyg2.pig has to be put outside of the renderpass for some reason,
        // else we get a second, untextured pair of wings. -Cookie
        ModelPhyg2.pig = entity;

        if (renderPass == 0) {
            loadTexture("/assets/aether/mobs/phyg/phyg_saddle.png");
            return entity != null && entity.getSaddled();
        } else if (renderPass == 1) {
            loadTexture("/assets/aether/mobs/Wings.png");
            return true;
        }
        return false;
    }
}
