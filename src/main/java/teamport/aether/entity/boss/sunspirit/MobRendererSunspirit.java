package teamport.aether.entity.boss.sunspirit;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRendererBiped;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;

@Environment(EnvType.CLIENT)
public class MobRendererSunspirit extends MobRendererBiped<MobBossSunspirit> {
    public MobRendererSunspirit() {
        super(0.8f);
    }

    @Override
    public void renderPreview(TessellatorGeneral tessellator, MobBossSunspirit sunspirit, double x, double y, double z, float yaw, float partialTick) {
        GLRenderer.pushFrame();
        GLRenderer.modelM4f().scale(0.5F, 0.5F, 0.5F);
        super.renderPreview(tessellator, sunspirit, x, y + 1, z, yaw, partialTick);
        GLRenderer.popFrame();
    }

    @Override
    protected void preRenderTransform(MobBossSunspirit sunspirit, double x, double y, double z, float yaw, float partialTick) {
        super.preRenderTransform(sunspirit, x, y, z, yaw, partialTick);
        GLRenderer.modelM4f().scale(2.25F, 2.25F, 2.25F).translate(0.0F, -10.0F, 0.0F);
    }

    @Override
    protected @NonNull StaticEntityModel getActiveModel(@NonNull MobBossSunspirit entity) {
        return this.getModel("main");
    }

    @Override
    protected @NonNull StaticEntityModel getAndSetupModelForLayer(@NonNull MobBossSunspirit entity,
                                                                    float brightness, float partialTick, int layer) {
        StaticEntityModel model = this.getModel("main");
        model.resetBones();

        BoneTransform head = model.getTransform("head");
        BoneTransform body = model.getTransform("body");
        BoneTransform rightArm = model.getTransform("rightArm");
        BoneTransform leftArm = model.getTransform("leftArm");
        float limbPitch = this.getLimbPitch(entity, partialTick);
        float swing = entity.getSwingProgress(partialTick);

        head.rotX = this.getHeadPitch(entity, partialTick);
        head.rotY = this.getHeadYaw(entity, partialTick) - this.getBodyYaw(entity, partialTick);

        body.rotY = MathHelper.sin(MathHelper.sqrt(swing) * (float) Math.PI * 2.0F) * 0.2F;
        rightArm.rotY = body.rotY * 3.0F;
        leftArm.rotY = body.rotY;

        float swingCurve = 1.0F - swing;
        swingCurve *= swingCurve;
        swingCurve *= swingCurve;
        swingCurve = 1.0F - swingCurve;
        float swingReach = MathHelper.sin(swingCurve * (float) Math.PI);
        float headCorrection = MathHelper.sin(swing * (float) Math.PI) * -((float) head.rotX - 0.7F) * 0.75F;
        rightArm.rotX -= swingReach * 1.2F + headCorrection;
        rightArm.rotZ = MathHelper.sin(swing * (float) Math.PI) * -0.4F;

        rightArm.rotZ += MathHelper.cos(limbPitch * 0.09F) * 0.05F + 0.05F;
        leftArm.rotZ -= MathHelper.cos(limbPitch * 0.09F) * 0.05F + 0.05F;
        rightArm.rotX += MathHelper.sin(limbPitch * 0.067F) * 0.05F;
        leftArm.rotX -= MathHelper.sin(limbPitch * 0.067F) * 0.05F;
        return model;
    }
}
