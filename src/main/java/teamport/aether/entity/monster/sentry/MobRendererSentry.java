package teamport.aether.entity.monster.sentry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class MobRendererSentry extends MobRenderer<MobSentry> {

    public MobRendererSentry(ModelBase model, float shadowSize) {
        super(model, shadowSize);
        this.setArmorModel(model);
    }

    public boolean setEyeBrightness(MobSentry sentry, int renderPass) {
        if (renderPass == 0 && sentry.isActivated()) {
            this.bindTexture("/assets/aether/textures/entity/sentry/sentry_eye.png");
            if (LightmapHelper.isLightmapEnabled()) {
                LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(15, 15));
            }

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 15.0f);
            return true;
        } else {
            return false;
        }
    }

    public void scaleSentry() {
        GL11.glScalef(1.75F, 1.75F, 1.75F);
    }

    @Override
    public void setupScale(MobSentry entity, float partialTick) {
        this.scaleSentry();
    }

    @Override
    public boolean prepareArmor(MobSentry sentry, int renderPass, float partialTick) {
        return this.setEyeBrightness(sentry, renderPass);
    }
}
