package bta.aether.entity.model;

import bta.aether.entity.EntityMoa;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.MathHelper;
import useless.dragonfly.model.entity.BenchEntityModel;
import useless.dragonfly.model.entity.processor.BenchEntityBones;

public class ModelMoa extends BenchEntityModel {

    EntityMoa moa;

    @Override
    public void setLivingAnimations(EntityLiving entityliving, float limbSwing, float limbYaw, float partialTick) {
        super.setLivingAnimations(entityliving, limbSwing, limbYaw, partialTick);
        if (entityliving instanceof EntityMoa) {
            moa = (EntityMoa) entityliving;
        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        if (!this.getIndexBones().isEmpty()) {
            BenchEntityBones head = this.getIndexBones().get("head");
            BenchEntityBones right_leg = this.getIndexBones().get("right_leg");
            BenchEntityBones left_leg = this.getIndexBones().get("left_leg");
            BenchEntityBones right_wing = this.getIndexBones().get("right_wing");
            BenchEntityBones left_wing = this.getIndexBones().get("left_wing");
            if (head != null) {
                head.rotateAngleX = (float) Math.toRadians(headPitch);
                head.rotateAngleY = (float) Math.toRadians(headYaw);
            }

            float f6 = 3.141593F;

            right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw;
            left_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbYaw;


            if (!moa.onGround) {
                right_wing.rotateAngleY = -limbPitch - MathHelper.PI / 3;
                left_wing.rotateAngleY = limbPitch + MathHelper.PI / 3;
                right_wing.rotateAngleX = MathHelper.PI / 2;
                left_wing.rotateAngleX = MathHelper.PI / 2;
                right_leg.rotateAngleX = 0.6F;
                left_leg.rotateAngleX = 0.6F;
            } else {
                right_wing.rotateAngleX = 0;
                left_wing.rotateAngleX = 0;
                right_wing.rotateAngleY = 0;
                left_wing.rotateAngleY = 0;
            }


        }


    }
}
