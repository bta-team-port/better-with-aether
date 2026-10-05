package teamport.aether.entity.monster.swet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;

@Environment(EnvType.CLIENT)
public class MobRendererSwet extends MobRenderer<MobSwet> {
    public MobRendererSwet(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobSwet entity, float brightness, float partialTick, int layer) {
        if (layer != 0) return null;

        StaticEntityModel model = this.getModel("main");

        GLRenderer.enableState(State.BLEND);
        GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);

        model.resetBones();

        float baseScale = 1.5F;
        if (entity.passenger != null) {
            baseScale = 1.5F + (entity.passenger.bbWidth + entity.passenger.bbHeight) * 0.75F;
        }
        baseScale = MathHelper.clamp(baseScale, 0.1F, 10.0F);

        float squishStrength = (float) (MathHelper.lerp(entity.getYdO(), entity.yd, partialTick) / (baseScale * 0.5F + 1.0F));
        squishStrength = MathHelper.clamp(squishStrength, -0.5F, 0.5F);
        float scale = 1.0F / (squishStrength + 1.0F);

        BoneTransform cube = model.getTransform("cube");
        cube.scaleX = scale * baseScale;
        cube.scaleY = 1.0F / scale * baseScale;
        cube.scaleZ = scale * baseScale;

        return model;
    }
}
