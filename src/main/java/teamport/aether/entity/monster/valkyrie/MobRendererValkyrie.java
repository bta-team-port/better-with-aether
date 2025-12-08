package teamport.aether.entity.monster.valkyrie;

import net.minecraft.client.render.entity.MobRendererBiped;
import net.minecraft.client.render.model.ModelBiped;

public class MobRendererValkyrie extends MobRendererBiped<MobValkyrie> {
    protected ModelValkyrie modelValkyrie;

    public MobRendererValkyrie(ModelBiped model, float shadowSize) {
        super(model, shadowSize);
        this.modelValkyrie = (ModelValkyrie) model;
    }

    @Override
    public void setupScale(MobValkyrie entity, float partialTick) {
        this.modelValkyrie.wingSpeed = entity.wingSpeed;
        this.modelValkyrie.isRiding = entity.onGround;
    }

}
