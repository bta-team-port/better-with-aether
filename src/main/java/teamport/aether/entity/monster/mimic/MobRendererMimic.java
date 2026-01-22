package teamport.aether.entity.monster.mimic;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererMimic extends MobRenderer<MobMimic> {
    public MobRendererMimic(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobMimic entity, float brightness, float partialTick, int layer) {
        StaticEntityModel model = this.getModel("main");
        model.resetBones();
        float limbSwing = this.getLimbSwing(entity, partialTick);
        float limbYaw = this.getLimbYaw(entity, partialTick);

        BoneTransform head = model.getTransform("head");

        float flapRotation = -0.8F + (MathHelper.cos(limbSwing * 0.6662F) * (limbYaw * 1.4f));
        float minRotation = -1.6F;
        float maxRotation = 0.0F;

        head.rotX = MathHelper.clamp(flapRotation, minRotation, maxRotation);

        BoneTransform leg0 = model.getTransform("leg0");
        BoneTransform leg1 = model.getTransform("leg1");
        leg0.rotX = MathHelper.cos(limbSwing * 0.6662F) * 1.1F * limbYaw;
        leg1.rotX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.1F * limbYaw;

        return model;
    }
}
