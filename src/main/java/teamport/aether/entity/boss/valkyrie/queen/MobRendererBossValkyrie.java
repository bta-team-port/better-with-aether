package teamport.aether.entity.boss.valkyrie.queen;

import net.minecraft.client.render.entity.MobRendererBiped;
import net.minecraft.client.render.model.ModelBiped;
import teamport.aether.entity.monster.valkyrie.MobValkyrie;
import teamport.aether.entity.monster.valkyrie.ModelValkyrie;

public class MobRendererBossValkyrie extends MobRendererBiped<MobBossValkyrie> {
    protected ModelValkyrie modelValkyrie;

    public MobRendererBossValkyrie(ModelBiped model, float shadowSize) {
        super(model, shadowSize);
        this.modelValkyrie = (ModelValkyrie) model;
    }

    @Override
    public void setupScale(MobBossValkyrie entity, float partialTick) {
        this.modelValkyrie.wingSpeed = entity.wingSpeed;
        this.modelValkyrie.isRiding = entity.onGround;
    }

}
