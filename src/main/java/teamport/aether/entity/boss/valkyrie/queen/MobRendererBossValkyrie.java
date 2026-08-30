package teamport.aether.entity.boss.valkyrie.queen;

import net.minecraft.client.render.entity.MobRendererBiped;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import teamport.aether.AetherGlobals;
import teamport.aether.entity.monster.valkyrie.MobRendererValkyrie;

public class MobRendererBossValkyrie extends MobRendererBiped<MobBossValkyrie> {
    public MobRendererBossValkyrie(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @NonNull StaticEntityModel getActiveModel(@NonNull MobBossValkyrie entity) {
        return this.getModel("main");
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobBossValkyrie entity, float brightness, float partialTick, int layer) {
        if (layer > 1) {
            return null;
        }
        StaticEntityModel model = this.setupAnimations(entity, this.getModel(layer == 0 ? "main" : "halo"), partialTick, layer);
        if (model == null) {
            AetherGlobals.LOGGER.error("Skip rendering the rest because Valkyrie Queen's model is null.");
            return null;
        }
        MobRendererValkyrie.setupValkyrieAnimation(model, entity.wingSpeed(), entity.onGround);
        if (layer == 0) {
            model.getTransform("halo").visible = false;
            model.getTransform("headOverlay").visible = false;
            return model;
        }
        MobRendererValkyrie.hideAllExceptHalo(model);
        model.getTransform("waist").visible = true;
        model.getTransform("halo").visible = true;
        GLRenderer.setLightmapCoord2i(15, 15);
        GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
        GLRenderer.enableState(State.BLEND);
        return model;
    }

    @Override
    protected int maxRenderLayer(@NonNull MobBossValkyrie entity) {
        return 1;
    }
}
