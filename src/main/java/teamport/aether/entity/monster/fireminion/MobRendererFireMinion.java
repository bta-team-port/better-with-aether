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
        StaticEntityModel model = this.getModel("main");
        model.resetBones();

        float bodyYaw = this.getBodyYaw(entity, partialTick);
        float headYaw = this.getHeadYaw(entity, partialTick) - bodyYaw;
        float headPitch = this.getHeadPitch(entity, partialTick);
        BoneTransform head = model.getTransform("head");
        head.rotY = headYaw;
        head.rotX = headPitch;

        return model;
    }

}

