package bta.aether.entity.renderer;

import bta.aether.entity.EntitySentry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class SentryRenderer extends LivingRenderer<EntitySentry> {

    public SentryRenderer(ModelBase model, float shadowSize) {
        super(model, shadowSize);
        this.setRenderPassModel(model);
    }

    @Override
    protected void preRenderCallback(EntitySentry entity, float f) {
        GL11.glScalef(1.75F, 1.75F, 1.75F);
    }

    protected boolean setEyeBrightness(EntitySentry sentry, int i) {
        if (i == 0 && sentry.activated) {
            this.loadTexture("assets/aether/mobs/sentry/sentry_eye.png");
            float brightness = sentry.getBrightness(1.0f);
            if (Minecraft.getMinecraft(this).fullbright) {
                brightness = 1.0f;
            }
            float f1 = (1.0f - brightness) * 0.5f;
            GL11.glEnable(3042);
            GL11.glDisable(3008);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, f1);
            return true;
        }
        return false;
    }

    @Override
    protected boolean shouldRenderPass(EntitySentry entity, int renderPass, float partialTick) {
        return this.setEyeBrightness(entity, renderPass);
    }
}
