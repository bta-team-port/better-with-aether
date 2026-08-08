package teamport.aether.entity.monster.sentry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.renderer.GLRenderer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import net.minecraft.client.render.entity.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererSentry extends MobRenderer<MobSentry> {

    public MobRendererSentry(float shadowSize) {
        super(shadowSize);
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
        BoneTransform head = model.getTransform("head");

        head.scaleX = 1.75F;
        head.scaleY = 1.75F;
        head.scaleZ = 1.75F;

        return model;
    }

    @Override
    protected int maxRenderLayer(@NonNull MobSentry entity) {
        return 1;
    }

}
