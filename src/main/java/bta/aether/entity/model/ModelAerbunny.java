package bta.aether.entity.model;

import bta.aether.entity.EntityAerbunny;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.MathHelper;
import useless.dragonfly.model.entity.BenchEntityModel;
import useless.dragonfly.model.entity.processor.BenchEntityBones;

public class ModelAerbunny extends BenchEntityModel {
    EntityAerbunny aerbunny;
    private float puffiness;

    @Override
    public void setLivingAnimations(EntityLiving entityliving, float limbSwing, float limbYaw, float partialTick) {
        super.setLivingAnimations(entityliving, limbSwing, limbYaw, partialTick);
        if (aerbunny instanceof EntityAerbunny) {
            aerbunny = (EntityAerbunny) entityliving;
            this.puffiness = MathHelper.lerp(partialTick, aerbunny.getPuffiness(), aerbunny.getPuffiness() - aerbunny.getPuffSubtract()) / 20.0F;

        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        if (!this.getIndexBones().isEmpty()) {
            BenchEntityBones head = this.getIndexBones().get("head");
            BenchEntityBones body = this.getIndexBones().get("body");
            BenchEntityBones right_front_leg = this.getIndexBones().get("right_front_leg");
            BenchEntityBones left_front_leg = this.getIndexBones().get("left_front_leg");
            BenchEntityBones right_back_leg = this.getIndexBones().get("right_back_leg");
            BenchEntityBones left_back_leg = this.getIndexBones().get("left_back_leg");
            BenchEntityBones puff = this.getIndexBones().get("puff");
            if (head != null) {
                head.rotateAngleX = (float) Math.toRadians(headPitch);
                head.rotateAngleY = (float) Math.toRadians(headYaw);
            }
            if (body != null) {
                if (right_front_leg != null && right_back_leg != null && left_front_leg != null && left_back_leg != null) {
                    right_front_leg.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbYaw) - body.rotateAngleX;
                    left_front_leg.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbYaw) - body.rotateAngleX;
                    right_back_leg.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F + MathHelper.PI) * 1.2F * limbYaw) - body.rotateAngleX;
                    left_back_leg.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F + MathHelper.PI) * 1.2F * limbYaw) - body.rotateAngleX;
                }
            }
            if (puff != null) {
                float a = 1.0F + this.puffiness * 0.5F;
                puff.scaleX = a;
                puff.scaleY = a;
                puff.scaleZ = a;
            }
        }


    }
}
