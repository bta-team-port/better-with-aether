package teamport.aether.entity.boss.slider;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.tessellator.Tessellator;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererSlider extends MobRenderer<MobBossSlider> {

    public MobRendererSlider(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobBossSlider entity, float brightness, float partialTick, int layer) {
        return null;
    }

    @Override
    public void renderPreview(@NonNull Tessellator tessellator, @NonNull MobBossSlider slider, double x, double y, double z, float yaw, float partialTick) {
        GL11.glPushMatrix();
        GL11.glScalef(0.75F, 0.75F, 0.75F);
        this.bindTexture("/assets/aether/textures/entity/boss_slider/slider_awake.png");
        super.renderPreview(tessellator, slider, x, y + 0.5, z, yaw, partialTick);
        GL11.glPopMatrix();
    }

    public boolean setEyeBrightness(MobBossSlider slider, int renderPass) {
        if (renderPass == 1) {
            if (slider.isAwake() && !slider.doingSlam() && slider.wakeUpTimer <= 0) {
                if (slider.isAngry()) {
                    this.bindTexture("/assets/aether/textures/entity/boss_slider/slider_awake_red_glow.png");
                } else {
                    this.bindTexture("/assets/aether/textures/entity/boss_slider/slider_awake_glow.png");
                }
            } else {
                if (slider.isAngry()) {
                    this.bindTexture("/assets/aether/textures/entity/boss_slider/slider_sleep_red_glow.png");
                } else {
                    this.bindTexture("/assets/aether/textures/entity/boss_slider/slider_sleep_glow.png");
                }
            }
        }

        float f1 = (1.0F - slider.getBrightness(1.0F)) * 0.5F;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
        return true;
    }

//    @Override
//    public void setupScale(MobBossSlider slider, float partialTick) {
//        if (slider.getDeformX() > 0.01F) {
//            GL11.glRotatef(slider.getDeformX() * -30.0F, slider.getDeformY(), 0.0F, slider.getDeformZ());
//        }
//
//    }
//
//    @Override
//    public boolean prepareArmor(MobBossSlider slider, int renderPass, float partialTick) {
//        return this.setEyeBrightness(slider, renderPass);
//    }
}
