package bta.aether.entity.renderer;

import bta.aether.entity.EntitySwet;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class SwetRenderer extends LivingRenderer<EntitySwet> {
    private final ModelBase scaleAmount;

    public SwetRenderer(ModelBase modelbase, ModelBase modelbase1, float f) {
        super(modelbase, f);
        this.scaleAmount = modelbase1;
    }

    protected boolean renderSlimePassModel(EntitySwet entity, int i, float f) {
        if (i == 0) {
            this.setRenderPassModel(this.scaleAmount);
            GL11.glEnable(2977);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            return true;
        } else {
            if (i == 1) {
                GL11.glDisable(3042);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }

            return false;
        }
    }

    protected void scaleSlime(EntitySwet entityswets, float f) {
        float f2 = 1.0F;
        float f1 = 1.0F;
        float f3 = 1.5F;
        if (!entityswets.onGround) {
            if (entityswets.yd > 0.8500000238418579) {
                f1 = 1.425F;
                f2 = 0.575F;
            } else if (entityswets.yd < -0.8500000238418579) {
                f1 = 0.575F;
                f2 = 1.425F;
            } else {
                float f4 = (float) entityswets.yd * 0.5F;
                f1 += f4;
                f2 -= f4;
            }
        }

        if (entityswets.passenger != null) {
            f3 = 1.5F + (entityswets.passenger.bbWidth + entityswets.passenger.bbHeight) * 0.75F;
        }

        GL11.glScalef(f2 * f3, f1 * f3, f2 * f3);
    }

    protected void preRenderCallback(EntitySwet entity, float f) {
        this.scaleSlime(entity, f);
    }

    protected boolean shouldRenderPass(EntitySwet entity, int renderPass, float partialTick) {
        return this.renderSlimePassModel(entity, renderPass, partialTick);
    }
}
