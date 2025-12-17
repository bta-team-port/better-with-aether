package teamport.aether.entity.animal.moa;

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
public class MobRendererMoa extends MobRenderer<MobMoa> {

    public MobRendererMoa(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobMoa entity, float brightness, float partialTick, int layer) {
        StaticEntityModel model;
        if (layer == 1) {
            this.bindTexture(entity.getSaddleTexturePath());
            model = this.getModel("saddle");
            GL11.glScalef(1.0f, 1.0f, 1.0f);
        } else {
            model = this.getModel("main");
            GL11.glScalef(0.85f, 0.85f, 0.85f);
        }

        model.resetBones();
        float limbSwing = this.getLimbSwing(entity, partialTick);
        float limbYaw = this.getLimbYaw(entity, partialTick);
        float limbPitch = this.getLimbPitch(entity, partialTick);
        float bodyYaw = this.getBodyYaw(entity, partialTick);
        float headYaw = this.getHeadYaw(entity, partialTick) - bodyYaw;
        float headPitch = this.getHeadPitch(entity, partialTick);

        BoneTransform head = model.getTransform("head");
        head.rotX = headPitch;
        head.rotY = headYaw;
        BoneTransform neck = model.getTransform("neck");
        neck.rotY = headYaw;

        BoneTransform leg0 = model.getTransform("leg0");
        BoneTransform leg1 = model.getTransform("leg1");
        leg0.rotX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw;
        leg1.rotX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbYaw;

        BoneTransform wing0 = model.getTransform("wing0");
        BoneTransform wing1 = model.getTransform("wing1");


        if (limbPitch <= 0.0000000001F) {
            wing0.rotX = (float) Math.PI / 2F;
            wing1.rotX = (float) Math.PI / 2F;
            wing0.posZ -= 8;
            wing1.posZ -= 8;
        } else {
            wing0.rotX = 0;
            wing1.rotX = 0;
            wing0.rotZ = limbPitch;
            wing1.rotZ = -limbPitch;

            leg0.rotX = 0.6F;
            leg1.rotX = 0.6F;
        }

        return model;
    }

    @Override
    protected float getLimbPitch(@NonNull MobMoa entity, float partialTick) {
        float flap = MathHelper.lerp(entity.getOFlap(), entity.getFlap(), partialTick);
        float flapSpeed = MathHelper.lerp(entity.getOFlapSpeed(), entity.getFlapSpeed(), partialTick);
        return (MathHelper.sin(flap) + 1.0F) * flapSpeed;
    }

    @Override
    protected int maxRenderLayer(@NonNull MobMoa entity) {
        return entity.getSaddled() ? 1 : 0;
    }

}
