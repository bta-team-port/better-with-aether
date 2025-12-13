package teamport.aether.entity.animal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererQuadruped<T extends Mob> extends MobRenderer<T> {
    public MobRendererQuadruped(float shadowSize) {
        super(shadowSize);
    }

    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NotNull T entity, float brightness, float partialTick, int layer) {
        StaticEntityModel model = this.getModel("main");
        model.resetBones();
        BoneTransform head = model.getTransform("head");
        BoneTransform leg0 = model.getTransform("leg0");
        BoneTransform leg1 = model.getTransform("leg1");
        BoneTransform leg2 = model.getTransform("leg2");
        BoneTransform leg3 = model.getTransform("leg3");
        float bodyYaw = this.getBodyYaw(entity, partialTick);
        float headYaw = this.getHeadYaw(entity, partialTick) - bodyYaw;
        float headPitch = this.getHeadPitch(entity, partialTick);
        float limbSwing = this.getLimbSwing(entity, partialTick);
        float limbYaw = this.getLimbYaw(entity, partialTick);
        head.rotX = headPitch;
        head.rotY = headYaw;
        leg0.rotX = (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw);
        leg1.rotX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbYaw);
        leg2.rotX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbYaw);
        leg3.rotX = (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw);
        return model;
    }
}
