package teamport.aether.entity.monster.sentry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.renderer.GLRenderer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.StaticEntityModel;

@Environment(EnvType.CLIENT)
public class MobRendererSentry extends MobRenderer<MobSentry> {

    public MobRendererSentry(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected void preRenderTransform(@NonNull MobSentry entity, double x, double y, double z, float yaw, float partialTick) {
        super.preRenderTransform(entity, x, y, z, yaw, partialTick);
        GLRenderer.modelM4f().scale(1.75F, 1.75F, 1.75F);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobSentry entity, float brightness, float partialTick, int layer) {
        if (layer == 1 && entity.isActivated()) {
            this.bindTexture("/assets/aether/textures/entity/sentry/glow/" + entity.getTextureReference() + ".png");
            GLRenderer.setLightmapCoord2i(15, 15);
        } else if (layer == 1) {
            return null;
        }

        StaticEntityModel model = this.getModel("main");
        model.resetBones();

        return model;
    }

    @Override
    protected int maxRenderLayer(@NonNull MobSentry entity) {
        return 1;
    }

}
