package teamport.aether.entity.sentry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class MobRendererSentry extends MobRenderer<MobSentry> {

    public MobRendererSentry(ModelBase model, float shadowSize) {
        super(model, shadowSize);
        this.setArmorModel(model);
    }

    public boolean setEyeBrightness(MobSentry sentry, int renderPass) {
        if (renderPass == 0 && sentry.activated) {
            this.bindTexture("/assets/aether/textures/entity/sentry/sentry_eye.png");
            this.overlayTexture = "/assets/aether/textures/entity/sentry/sentry_eye.png";
            float brightness = sentry.getBrightness(15.0F);
            if (Minecraft.getMinecraft().fullbright) {
                brightness = 1.0f;
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

    public void scaleSentry() {
        GL11.glScalef(1.75F, 1.75F, 1.75F);
    }

    public void setupScale(MobSentry entity, float partialTick) {
        this.scaleSentry();
    }

    @Override
    public boolean prepareArmor(MobSentry sentry, int renderPass, float partialTick) {
        return this.setEyeBrightness(sentry, renderPass);
    }
}
