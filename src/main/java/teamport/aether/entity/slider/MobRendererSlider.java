package teamport.aether.entity.slider;

import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class MobRendererSlider extends MobRenderer<MobBossSlider> {

    public MobRendererSlider(ModelBase model, float shadowSize) {
        super(model, shadowSize);
        this.setArmorModel(model);
    }

    public boolean setEyeBrightness(MobBossSlider slider, int renderPass) {
        if (renderPass != 0) {
            return false;
        } else {
            if (slider.awake) {
                if (slider.criticalCondition()) {
                    this.bindTexture("/assets/aether/textures/entity/boss_slider/slider_awake_red_glow.png");
                } else {
                    this.bindTexture("/assets/aether/textures/entity/boss_slider/slider_awake_glow.png");
                }
            } else {
                if (slider.criticalCondition()) {
                    this.bindTexture("/assets/aether/textures/entity/boss_slider/slider_sleep_red_glow.png");
                } else {
                    this.bindTexture("/assets/aether/textures/entity/boss_slider/slider_sleep_glow.png");
                }
            }

            float f1 = (1.0F - slider.getBrightness(1.0F)) * 0.5F;
            GL11.glEnable(3042);
            GL11.glDisable(3008);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
            return true;
        }
    }

    public void setupScale(MobBossSlider entity, float partialTick) {
        if (entity.harvey > 0.01F) {
            GL11.glRotatef(entity.harvey * -30.0F, (float) entity.rennis, 0.0F, (float) entity.dennis);
        }

    }

    @Override
    public boolean prepareArmor(MobBossSlider slider, int renderPass, float partialTick) {
        return this.setEyeBrightness(slider, renderPass);
    }
}