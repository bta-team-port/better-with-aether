package teamport.aether.entity.sentry;

import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class MobRendererSentry extends MobRenderer<MobSentry> {

    public MobRendererSentry(ModelBase model, float shadowSize) {
        super(model, shadowSize);
        GL11.glScalef(1.75F, 1.75F, 1.75F);
        this.setArmorModel(model);
        this.overlayTexture = "/assets/aether/textures/entity/sentry/sentry_eye.png/";
    }

    public boolean setEyeBrightness(MobSentry sentry, int renderPass) {
        if (renderPass == 0 && sentry.activated) {
            this.bindTexture("/assets/aether/textures/entity/sentry/sentry_eye.png/");
            this.overlayTexture = "/assets/aether/textures/entity/sentry/sentry_eye.png/";
            float brightness = sentry.getBrightness(2.0F);
            if (LightmapHelper.isLightmapEnabled()) {
                LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(15, 15));
            }

            float f1 = (1.0F - brightness) * 0.5F;
            GL11.glEnable(3042);
            GL11.glDisable(3008);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean prepareArmor(MobSentry sentry, int renderPass, float partialTick) {
        return this.setEyeBrightness(sentry, renderPass);
    }
}
