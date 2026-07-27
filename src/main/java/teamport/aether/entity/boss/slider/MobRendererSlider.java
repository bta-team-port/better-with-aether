package teamport.aether.entity.boss.slider;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.StaticEntityModel;

@Environment(EnvType.CLIENT)
public class MobRendererSlider extends MobRenderer<MobBossSlider> {
    public MobRendererSlider(float shadowSize) {
        super(shadowSize);
    }

    @Override
    public void renderPreview(TessellatorGeneral tessellator, MobBossSlider slider, double x, double y, double z,
                              float yaw, float partialTick) {
        GLRenderer.pushFrame();
        GLRenderer.modelM4f().scale(0.75F, 0.75F, 0.75F);
        this.bindTexture("/assets/aether/textures/entity/boss_slider/slider_awake.png");
        super.renderPreview(tessellator, slider, x, y + 0.5, z, yaw, partialTick);
        GLRenderer.popFrame();
    }

    private void bindGlowTexture(MobBossSlider slider) {
        String state = slider.isAwake() && !slider.doingSlam() ? "awake" : "sleep";
        String anger = slider.isAngry() ? "_red" : "";
        this.bindTexture("/assets/aether/textures/entity/boss_slider/slider_" + state + anger + "_glow.png");
    }

    @Override
    protected int maxRenderLayer(@NonNull MobBossSlider slider) {
        return 2;
    }

    @Override
    protected void preRenderTransform(@NonNull MobBossSlider slider, double x, double y, double z,
                                      float yaw, float partialTick) {
        super.preRenderTransform(slider, x, y, z, yaw, partialTick);
        if (slider.getDeformX() > 0.01F) {
            GLRenderer.modelM4f().rotate(
                (float) Math.toRadians(slider.getDeformX() * -30.0F),
                slider.getDeformY(), 0.0F, slider.getDeformZ()
            );
        }
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobBossSlider slider,
                                                                    float brightness, float partialTick, int layer) {
        StaticEntityModel model = this.getModel("main");
        model.resetBones();

        if (layer == 2) {
            GLRenderer.disableState(State.BLEND);
            return null;
        }

        if (layer == 1) {
            this.bindGlowTexture(slider);
            GLRenderer.setLightmapCoord2i(15, 15);
            GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
            GLRenderer.enableState(State.BLEND);
        }

        return model;
    }
}
