package teamport.aether.entity.swet;

import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class MobRendererSwet extends MobRenderer<MobSwet> {
    public final ModelBase scaleAmount;

    public MobRendererSwet(ModelBase modelbase, ModelBase modelbase1, float f) {
        super(modelbase, f);
        this.scaleAmount = modelbase1;
    }

    public boolean renderSlimePassModel(MobSwet entity, int i, float f) {
        if (i == 0) {
            this.setArmorModel(this.scaleAmount);
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

    public void scaleSlime(MobSwet entityslime, float f) {
        float f2 = 1.0F;
        float f1 = 1.0F;
        float f3 = 1.5F;
        GL11.glScalef(f2 * f3, f1 * f3, f2 * f3);
    }

    public void setupScale(MobSwet entity, float partialTick) {
        this.scaleSlime(entity, partialTick);
    }

    public boolean prepareArmor(MobSwet entity, int renderPass, float partialTick) {
        return this.renderSlimePassModel(entity, renderPass, partialTick);
    }
}
