package bta.aether.entity.model;

import bta.aether.entity.EntityMimic;
import bta.aether.entity.EntityMoa;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.MathHelper;
import useless.dragonfly.model.entity.BenchEntityModel;
import useless.dragonfly.model.entity.processor.BenchEntityBones;

@Environment(EnvType.CLIENT)
public class ModelMimic extends BenchEntityModel {

    EntityMimic mimic;

    @Override
    public void setLivingAnimations(EntityLiving entityliving, float limbSwing, float limbYaw, float partialTick) {
        super.setLivingAnimations(entityliving, limbSwing, limbYaw, partialTick);
        if (entityliving instanceof EntityMimic) {
            mimic = (EntityMimic) entityliving;
        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        if (!this.getIndexBones().isEmpty()) {
            BenchEntityBones jaw = this.getIndexBones().get("jaw");
            BenchEntityBones right_leg = this.getIndexBones().get("right_leg");
            BenchEntityBones left_leg = this.getIndexBones().get("left_leg");

            jaw.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbYaw;
            right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbYaw;
            left_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 0.8F   * limbYaw;
        }
    }
}
