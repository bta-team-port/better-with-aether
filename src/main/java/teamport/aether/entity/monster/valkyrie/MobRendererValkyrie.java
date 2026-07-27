package teamport.aether.entity.monster.valkyrie;

import net.minecraft.client.render.entity.MobRendererBiped;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;

public class MobRendererValkyrie extends MobRendererBiped<MobValkyrie> {
    public MobRendererValkyrie(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @NonNull StaticEntityModel getActiveModel(@NonNull MobValkyrie entity) {
        return this.getModel("main");
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobValkyrie entity, float brightness, float partialTick, int layer) {
        if (layer > 1) {
            return null;
        }

        StaticEntityModel model = this.setupAnimations(entity, this.getModel(layer == 0 ? "main" : "halo"), partialTick, layer);
        setupValkyrieAnimation(model, entity.wingSpeed, entity.onGround);
        if (layer == 0) {
            model.getTransform("halo").visible = false;
            model.getTransform("headOverlay").visible = false;
            model.getTransform("hair").visible = true;
        } else {
            hideAllExceptHalo(model);
            model.getTransform("waist").visible = true;
            model.getTransform("halo").visible = true;
            GLRenderer.setLightmapCoord2i(15, 15);
            GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
            GLRenderer.enableState(State.BLEND);
        }
        return model;
    }

    public static void hideAllExceptHalo(StaticEntityModel model) {
        String[] bones = {
            "waist", "torso", "body", "wingLeft", "wingRight",
            "rightArm", "rightItem", "leftArm", "leftItem",
            "headMesh", "headOverlay", "hair",
            "rightLeg", "rightArmorFront", "rightArmorSide", "rightArmorBack",
            "leftLeg", "leftArmorFront", "leftArmorSide", "leftArmorBack"
        };
        for (String boneName : bones) {
            model.getTransform(boneName).visible = false;
        }
    }

    public static void setupValkyrieAnimation(StaticEntityModel model, float wingSpeed, boolean onGround) {
        BoneTransform wingLeft = model.getTransform("wingLeft");
        BoneTransform wingRight = model.getTransform("wingRight");
        double wingDivisor = onGround ? 8.0 : 3.0;
        wingLeft.rotY = -0.2 + Math.sin(wingSpeed) / 6.0;
        wingRight.rotY = 0.2 - Math.sin(wingSpeed) / 6.0;
        wingLeft.rotZ = -0.125 + Math.cos(wingSpeed) / wingDivisor;
        wingRight.rotZ = 0.125 - Math.cos(wingSpeed) / wingDivisor;

        double leftLegX = model.getTransform("leftLeg").rotX;
        if (leftLegX < -0.3) {
            model.getTransform("leftArmorFront").rotX += leftLegX + 0.3;
            model.getTransform("rightArmorBack").rotX -= leftLegX + 0.3;
        } else if (leftLegX > 0.3) {
            model.getTransform("leftArmorBack").rotX += leftLegX - 0.3;
            model.getTransform("rightArmorFront").rotX -= leftLegX - 0.3;
        }
    }

    @Override
    protected int maxRenderLayer(@NonNull MobValkyrie entity) {
        return 1;
    }
}
