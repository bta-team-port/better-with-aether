package bta.aether.entity.renderer;

import bta.aether.entity.EntityPhyg;
import bta.aether.entity.model.ModelPhyg2;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.Entity;

public class PhygRenderer extends LivingRenderer {
    private ModelBase wingmodel;

    public PhygRenderer(ModelBase modelbase, ModelBase modelbase1, float f) {
        super(modelbase, f);
        this.setRenderPassModel(modelbase1);
        this.wingmodel = modelbase1;
    }

    protected boolean setWoolColorAndRender(EntityPhyg pig, int i, float f) {
        if (i == 0) {
            this.loadTexture("/assets/aether/mob/Wings.png");
            ModelPhyg2.pig = pig;
            return true;
        } else {
            return false;
        }
    }

    protected boolean render(Entity entityliving, int i, float f) {
        return this.setWoolColorAndRender((EntityPhyg) entityliving, i, f);
    }
}
