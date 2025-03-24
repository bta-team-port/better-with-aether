package bta.aether.entity.renderer;

import bta.aether.entity.EntityBossSlider;
import bta.aether.entity.model.ModelSlider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class SliderRenderer extends LivingRenderer<EntityBossSlider> {
    public SliderRenderer(ModelBase ms, float f) {
        super(new ModelSlider(), 1.0F);
        this.setRenderPassModel(new ModelSlider());
    }

    protected boolean setSliderEyeBrightness(EntityBossSlider slider, int i, float f) {
        if (i != 0) {
            return false;
        } else {
            if (slider.isAwake() && !slider.doingSlam()) {
                if (slider.isAngry()) {
                    this.loadTexture("/assets/aether/mobs/slider/slider_awake_red_glow.png");
                } else {
                    this.loadTexture("/assets/aether/mobs/slider/slider_awake_glow.png");
                }
            } else {
                this.loadTexture("/assets/aether/mobs/slider/slider_sleep_glow.png");
            }

            float f1 = (1.0F - slider.getBrightness(1.0f)) * 0.5F;
            GL11.glEnable(3042);
            GL11.glDisable(3008);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
            return true;
        }
    }

    protected boolean shouldRenderPass(EntityBossSlider entity, int renderPass, float partialTick) {
        return this.setSliderEyeBrightness(entity, renderPass, partialTick);
    }

}
