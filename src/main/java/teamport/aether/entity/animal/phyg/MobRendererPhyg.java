package teamport.aether.entity.animal.phyg;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererPhyg extends MobRenderer<MobPhyg> {
    public MobRendererPhyg(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NotNull MobPhyg entity, float brightness, float partialTick, int layer) {
        StaticEntityModel model;
        if (layer == 1) {
            this.bindTexture("/assets/aether/textures/entity/phyg/saddle.png");
            model = this.getModel("saddle");
        } else {
            model = this.getModel("main");
        }

        model.resetBones();
        BoneTransform head = model.getTransform("head");
        BoneTransform leg0 = model.getTransform("leg0");
        BoneTransform leg1 = model.getTransform("leg1");
        BoneTransform leg2 = model.getTransform("leg2");
        BoneTransform leg3 = model.getTransform("leg3");
        BoneTransform wingLeftInner = model.getTransform("wingLeftInner");
        BoneTransform wingLeftOuter = model.getTransform("wingLeftOuter");
        BoneTransform wingRightInner = model.getTransform("wingRightInner");
        BoneTransform wingRightOuter = model.getTransform("wingRightOuter");
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
        wingLeftInner.rotX = (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw);
        return model;
    }

    @Override
    protected int maxRenderLayer(@NotNull MobPhyg entity) {
        return entity.getSaddled() ? 1 : 0;
    }
}
