package teamport.aether.entity.monster.swet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class MobRendererSwet extends MobRenderer<MobSwet> {
    public final ModelBase scaleAmount;

    public MobRendererSwet(ModelBase modelbase, ModelBase modelbase1, float shadowsize) {
        super(modelbase, shadowsize);
        this.scaleAmount = modelbase1;
    }

    private boolean renderSlimePassModel(int renderPass) {
        if (renderPass == 0) {
            this.setArmorModel(this.scaleAmount);
            GL11.glEnable(2977);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            return true;
        } else if (renderPass == 1) {
            GL11.glDisable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            return false;
        }
        return false;
    }

    public void scaleSlime(MobSwet entityswets, float partialTick) {
        float f2 = 1.0F;
        float f1 = 1.0F;
        float f3 = 1.5F;
        double yd = MathHelper.lerp(entityswets.getYdO(), entityswets.yd, partialTick);
        if (!entityswets.onGround) {
            if (yd > 0.85) {
                f1 = 1.425F;
                f2 = 0.575F;
            } else if (yd < -0.85) {
                f1 = 0.575F;
                f2 = 1.425F;
            } else {
                float f4 = (float) yd * 0.5F;
                f1 += f4;
                f2 -= f4;
            }
        }

        if (entityswets.passenger != null) {
            f3 = 1.5F + (entityswets.passenger.bbWidth + entityswets.passenger.bbHeight) * 0.75F;
        }

        f1 = MathHelper.clamp(f1, 0.1F, 10.0F);
        f2 = MathHelper.clamp(f2, 0.1F, 10.0F);
        f3 = MathHelper.clamp(f3, 0.1F, 10.0F);

        GL11.glScalef(f2 * f3, f1 * f3, f2 * f3);
    }

    @Override
    public void setupScale(MobSwet entity, float partialTick) {
        this.scaleSlime(entity, partialTick);
    }

    @Override
    public boolean prepareArmor(MobSwet entity, int renderPass, float partialTick) {
        return this.renderSlimePassModel(renderPass);
    }
}
