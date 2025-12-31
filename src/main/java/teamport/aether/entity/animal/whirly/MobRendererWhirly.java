package teamport.aether.entity.animal.whirly;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererWhirly extends MobRenderer<MobWhirly> {
    public MobRendererWhirly(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobWhirly whirly, float brightness, float partialTick, int layer) {
        StaticEntityModel model;
        if (layer == 1) {
            model = this.getModel("wind");
        } else {
            model = this.getModel("main");
        }
        model.resetBones();

        if (layer == 1) {
            this.bindTexture("/assets/aether/textures/entity/whirly/wind.png");
            float spinSpeed = 0.25F;

            BoneTransform wind = model.getTransform("wind");
            wind.rotY = (whirly.tickCount + partialTick) * (spinSpeed);

            BoneTransform wind2 = model.getTransform("wind2");
            wind2.rotY = (whirly.tickCount + partialTick) * (spinSpeed + 0.025);

            BoneTransform wind3 = model.getTransform("wind3");
            wind3.rotY = (whirly.tickCount + partialTick) * (spinSpeed + 0.025);

            BoneTransform wind4 = model.getTransform("wind4");
            wind4.rotY = (whirly.tickCount + partialTick) * (spinSpeed + 0.025);

        } else if (layer == 2) {
            return null;
        } else {
            float bodyYaw = this.getBodyYaw(whirly, partialTick);
            float headYaw = this.getHeadYaw(whirly, partialTick) - bodyYaw;
            float headPitch = this.getHeadPitch(whirly, partialTick);
            BoneTransform head = model.getTransform("head");
            head.rotY = headYaw;
            head.rotX = headPitch;
        }

        return model;
    }

    @Override
    protected int maxRenderLayer(@NonNull MobWhirly entity) {
        return 2;
    }
}
