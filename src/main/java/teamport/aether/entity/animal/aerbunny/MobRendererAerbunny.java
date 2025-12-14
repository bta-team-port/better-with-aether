package teamport.aether.entity.animal.aerbunny;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererAerbunny extends MobRenderer<MobAerbunny> {

    public MobRendererAerbunny(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobAerbunny entity, float brightness, float partialTick, int layer) {
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
        leg0.rotX = (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbYaw);
        leg2.rotX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbYaw);
        leg3.rotX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbYaw);
        leg1.rotX = (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbYaw);

        BoneTransform puff = model.getTransform("puff");

        float puffiness = 1.0F + entity.getPuffiness() * 0.5F;
        puff.scaleX = puffiness;
        puff.scaleY = puffiness;
        puff.scaleZ = puffiness;

        if (!entity.onGround && entity.vehicle == null) {
            if (entity.yd > 0.5) {
                GL11.glRotatef(15.0F, -1.0F, 0.0F, 0.0F);
            } else if (entity.yd < -0.5) {
                GL11.glRotatef(-15.0F, -1.0F, 0.0F, 0.0F);
            } else {
                GL11.glRotatef((float) (entity.yd * 30.0), -1.0F, 0.0F, 0.0F);
            }
        }

        return model;
    }

}
