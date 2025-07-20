package teamport.aether.entity.phow;

import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;

public class MobRendererPhow extends MobRenderer<MobPhow> {
    public MobRendererPhow(ModelBase modelbase, ModelBase modelwings, float f) {
        super(modelbase, f);
        this.setArmorModel(modelwings);
    }

    public boolean setWingsAndRender(MobPhow phow, int i, float f) {
        if (i == 0) {
            this.bindTexture("/assets/aether/textures/entity/Wings.png");
            ModelPhowWings.cow = phow;
            return true;
        } else {
            return false;
        }
    }

    public boolean prepareArmor(MobPhow entity, int renderPass, float partialTick) {
        return this.setWingsAndRender(entity, renderPass, partialTick);
    }
}
