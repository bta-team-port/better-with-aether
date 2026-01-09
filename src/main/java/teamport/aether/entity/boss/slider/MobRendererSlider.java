package teamport.aether.entity.boss.slider;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class MobRendererSlider extends MobRenderer<MobBossSlider> {

    public MobRendererSlider(ModelBase model, float shadowSize) {
        super(model, shadowSize);
        this.setArmorModel(model);
        this.shadowSize = 0.0F;
    }

    @Override
    public void renderPreview(Tessellator tessellator, MobBossSlider slider, double x, double y, double z, float yaw, float partialTick) {
        GL11.glPushMatrix();
        GL11.glScalef(0.75F, 0.75F, 0.75F);
        this.bindTexture("/assets/aether/textures/entity/boss_slider/slider_awake.png");
        super.renderPreview(tessellator, slider, x, y + 0.5, z, yaw, partialTick);
        GL11.glPopMatrix();
    }

    public boolean setEyeBrightness(MobBossSlider slider, int renderPass) {
        if (renderPass == 0) {
            if (slider.isAwake() && !slider.doingSlam()) {
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
            if (LightmapHelper.isLightmapEnabled()) {
                LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(15, 15));
            }

            GL11.glEnable(3042);
            GL11.glDisable(3008);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setupScale(MobBossSlider slider, float partialTick) {
        if (slider.getDeformX() > 0.01F) {
            GL11.glRotatef(slider.getDeformX() * -30.0F, slider.getDeformY(), 0.0F, slider.getDeformZ());
        }

    }

    @Override
    public boolean prepareArmor(MobBossSlider slider, int renderPass, float partialTick) {
        return this.setEyeBrightness(slider, renderPass);
    }
}
