package teamport.aether.entity.animal.aerbunny;

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
public class MobRendererAerbunny extends MobRenderer<MobAerbunny> {

    public MobRendererAerbunny(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected int maxRenderLayer(@NonNull MobAerbunny entity) {
        return entity.isDevil() ? 1 : 0;
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

        BoneTransform eyeGlow = model.getTransform("eye_glow");
        eyeGlow.rotX = headPitch;
        eyeGlow.rotY = headYaw;
        if (layer == 0) {
            eyeGlow.visible = false;
        } else {
            hideAllExceptEyeGlow(model);
            eyeGlow.visible = true;
            GLRenderer.setLightmapCoord2i(15, 15);
            GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
            GLRenderer.enableState(State.BLEND);
        }

        return model;
    }

    private static void hideAllExceptEyeGlow(StaticEntityModel model) {
        String[] bones = {"body", "tail", "puff", "head", "ear", "leg0", "leg1", "leg2", "leg3"};
        for (String name : bones) {
            BoneTransform t = model.getTransform(name);
            t.visible = false;
        }
    }
}
