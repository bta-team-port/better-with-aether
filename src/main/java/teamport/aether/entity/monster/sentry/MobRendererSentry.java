package teamport.aether.entity.monster.sentry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapHelper;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererSentry extends MobRenderer<MobSentry> {

    public MobRendererSentry(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobSentry entity, float brightness, float partialTick, int layer) {
        if (layer == 1) {
            this.bindTexture("/assets/aether/textures/entity/sentry/glow/" + entity.getTextureReference() + ".png");
            if (LightmapHelper.isLightmapEnabled()) {
                LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(15, 15));
            }
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, (1.0F - entity.getBrightness(partialTick)) * 0.5F);
        }
        StaticEntityModel model = this.getModel("main");

        GL11.glScalef(1.75F, 1.75F, 1.75F);

        model.resetBones();

        return model;
    }

}
