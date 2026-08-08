package teamport.aether.entity.monster.swet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.StaticEntityModel;

@Environment(EnvType.CLIENT)
public class MobRendererSwet extends MobRenderer<MobSwet> {
    public MobRendererSwet(float shadowSize) {
        super(shadowSize);
    }

    public void scaleSlime(@NonNull MobSwet swet, float partialTick) {
        float f2 = 1.0F;
        float f1 = 1.0F;
        float f3 = 1.5F;
        double yd = MathHelper.lerp(swet.getYdO(), swet.yd, partialTick);
        if (!swet.onGround) {
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

        if (swet.passenger != null) {
            f3 = 1.5F + (swet.passenger.bbWidth + swet.passenger.bbHeight) * 0.75F;
        }

        f1 = MathHelper.clamp(f1, 0.1F, 10.0F);
        f2 = MathHelper.clamp(f2, 0.1F, 10.0F);
        f3 = MathHelper.clamp(f3, 0.1F, 10.0F);

        GLRenderer.modelM4f().scale(f2 * f3, f1 * f3, f2 * f3);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobSwet entity, float brightness, float partialTick, int layer) {
        if (layer != 0) return null;
        this.scaleSlime(entity, partialTick);
        return this.getModel("main");
    }
}
