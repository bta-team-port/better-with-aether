package teamport.aether.entity.animal.sheepuff;

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
public class MobRendererSheepuff extends MobRenderer<MobSheepuff> {
    public MobRendererSheepuff(float shadowSize) {
        super(shadowSize);
    }

    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobSheepuff entity, float brightness, float partialTick, int layer) {
        StaticEntityModel model = null;
        int colorIndex = entity.getFleeceColor().blockMeta;

        switch (layer) {
            case 1:
                GL11.glColor3f(brightness * MobSheepuff.FLEECE_COLOR_TABLE[colorIndex][0], brightness * MobSheepuff.FLEECE_COLOR_TABLE[colorIndex][1], brightness * MobSheepuff.FLEECE_COLOR_TABLE[colorIndex][2]);
                model = this.getModel("main");
                this.bindTexture("/assets/aether/textures/entity/sheepuff/wool_overlay.png");
                break;

            case 2:
                if (!entity.getSheared() && !entity.getPuffed()) {
                    GL11.glColor3f(brightness * MobSheepuff.FLEECE_COLOR_TABLE[colorIndex][0], brightness * MobSheepuff.FLEECE_COLOR_TABLE[colorIndex][1], brightness * MobSheepuff.FLEECE_COLOR_TABLE[colorIndex][2]);
                    model = this.getModel("wool");
                    this.bindTexture("/assets/aether/textures/entity/sheepuff/wool_overlay.png");
                }
                break;
            case 3:
                if (entity.getPuffed()) {
                    GL11.glColor3f(brightness * MobSheepuff.FLEECE_COLOR_TABLE[colorIndex][0], brightness * MobSheepuff.FLEECE_COLOR_TABLE[colorIndex][1], brightness * MobSheepuff.FLEECE_COLOR_TABLE[colorIndex][2]);
                    model = this.getModel("puffed");
                    this.bindTexture("/assets/aether/textures/entity/sheepuff/wool_overlay.png");
                }
                break;
            default:
                model = this.getModel("main");
        }

        if (model != null) {
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

            leg0.rotX = (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw);
            leg1.rotX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbYaw);
            leg2.rotX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbYaw);
            leg3.rotX = (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw);

            float headBobTime = MathHelper.lerp(entity.getPrevTimeSheepEating(), entity.getTimeSheepEating(), partialTick);
            if (entity.getIsSheepEating()) {
                if (headBobTime < 5.0F) {
                    float partPercentage = headBobTime / 5.0F;
                    head.rotX = (-(headPitch * (1.0F - partPercentage) + 60.0F * partPercentage) * MathHelper.DEG_TO_RAD);
                    head.rotY = (headYaw * (1.0F - partPercentage) * MathHelper.DEG_TO_RAD);
                    head.posY = (-2.0F * headBobTime);
                } else if (headBobTime < 35.0F) {
                    head.rotX = (60.0F * MathHelper.DEG_TO_RAD);
                    head.rotY = 0.0F;
                    head.posY = (-10.0F + MathHelper.sin(headBobTime * 0.05F * 30.5F) / 3.0F);
                } else if (headBobTime < 40.0F) {
                    float partPercentage = (headBobTime - 35.0F) / 5.0F;
                    head.rotX = ((headPitch * partPercentage + 60.0F * (1.0F - partPercentage)) * MathHelper.DEG_TO_RAD);
                    head.rotY = (headYaw * partPercentage * MathHelper.DEG_TO_RAD);
                    head.posY = (-10.0F + 2.0F * (headBobTime - 34.0F));
                }
            } else {
                head.rotX = (headPitch * MathHelper.DEG_TO_RAD);
                head.rotY = (headYaw * MathHelper.DEG_TO_RAD);
            }
        }
        return model;
    }

    @Override
    protected int maxRenderLayer(@NonNull MobSheepuff entity) {
        if (entity.getPuffed()) {
            return 3;
        } else if (!entity.getSheared()) {
            return 2;
        } else {
            return 1;
        }
    }
}
