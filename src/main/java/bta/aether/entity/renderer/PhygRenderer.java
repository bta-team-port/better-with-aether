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
    protected boolean shouldRenderPass(EntityPhyg entity, int renderPass, float partialTick)
    {
        if (renderPass == 0){
            loadTexture("/assets/aether/mobs/PhygSaddle.png");
            return entity != null && entity.getSaddled();
        } else if (renderPass == 1) {
            loadTexture("/assets/aether/mobs/Wings.png");
            ModelPhyg2.pig = entity;
            return true;
        }
        return false;
    }
}
