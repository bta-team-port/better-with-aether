package teamport.aether.entity.monster.fireminion;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererFireMinion extends MobRenderer<MobFireMinion> {
    public MobRendererFireMinion(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobFireMinion entity, float brightness, float partialTick, int layer) {
        StaticEntityModel model;
        if (layer == 1) {
            model = this.getModel("fire");
        } else {
            model = this.getModel("main");
        }
        model.resetBones();

        if (layer == 1) {
            this.bindTexture("/assets/aether/textures/entity/fire_minion/fire.png");
            float spinSpeed = 0.15F;

            BoneTransform wind2 = model.getTransform("wind2");
            wind2.rotY = (entity.tickCount + partialTick) * (spinSpeed + 0.025);

            BoneTransform wind3 = model.getTransform("wind3");
            wind3.rotY = (entity.tickCount + partialTick) * (spinSpeed + 0.025);

            BoneTransform wind4 = model.getTransform("wind4");
            wind4.rotY = (entity.tickCount + partialTick) * (spinSpeed + 0.025);

        } else if (layer == 2) {
            return null;
        } else {
            float bodyYaw = this.getBodyYaw(entity, partialTick);
            float headYaw = this.getHeadYaw(entity, partialTick) - bodyYaw;
            float headPitch = this.getHeadPitch(entity, partialTick);
            BoneTransform head = model.getTransform("head");
            head.rotY = headYaw;
            head.rotX = headPitch;
        }

        return model;
    }

    @Override
    protected int maxRenderLayer(@NonNull MobFireMinion entity) {
        return 2;
    }
}

