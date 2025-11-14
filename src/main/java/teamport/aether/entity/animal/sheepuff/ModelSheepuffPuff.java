package teamport.aether.entity.animal.sheepuff;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelQuadruped;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.MathHelper;

@Environment(EnvType.CLIENT)
public class ModelSheepuffPuff extends ModelQuadruped {
    private boolean isEatingAnimPlaying;
    private float headBobTime;

    public ModelSheepuffPuff() {
        super(12, 0.0F);
        this.head = new Cube(0, 0);
        this.head.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
        this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
        this.body = new Cube(28, 8);
        this.body.addBox(-4.0F, -8.0F, -7.0F, 8, 16, 6, 3.75F);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
        float f = 0.5F;
        this.leg1 = new Cube(0, 16);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg1.setRotationPoint(-3.0F, 12.0F, 7.0F);
        this.leg2 = new Cube(0, 16);
        this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg2.setRotationPoint(3.0F, 12.0F, 7.0F);
        this.leg3 = new Cube(0, 16);
        this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg3.setRotationPoint(-3.0F, 12.0F, -5.0F);
        this.leg4 = new Cube(0, 16);
        this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg4.setRotationPoint(3.0F, 12.0F, -5.0F);
    }

    @Override
    public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        float desiredRotateAngleX = headPitch / 57.29578F;
        float desiredRotateAngleY = headYaw / 57.29578F;
        if (this.isEatingAnimPlaying) {
            float partPercentage;
            if (this.headBobTime < 5.0F) {
                partPercentage = this.headBobTime / 5.0F;
                this.head.xRot = desiredRotateAngleX * (1.0F - partPercentage) + 1.0472F * partPercentage;
                this.head.yRot = desiredRotateAngleY * (1.0F - partPercentage);
            } else if (this.headBobTime < 35.0F) {
                this.head.xRot = 1.0472F;
                this.head.yRot = 0.0F;
            } else if (this.headBobTime < 40.0F) {
                partPercentage = (this.headBobTime - 35.0F) / 5.0F;
                this.head.xRot = desiredRotateAngleX * partPercentage + 1.0472F * (1.0F - partPercentage);
                this.head.yRot = desiredRotateAngleY * partPercentage;
            }
        } else {
            this.head.xRot = desiredRotateAngleX;
            this.head.yRot = desiredRotateAngleY;
        }

    }

    @Override
    public void setLivingAnimations(Mob mob, float limbSwing, float limbYaw, float partialTick) {
        MobSheepuff entitySheep = (MobSheepuff) mob;
        this.head.y = 6.0F;
        this.isEatingAnimPlaying = false;
        if (entitySheep.getIsSheepEating()) {
            this.headBobTime = (float) entitySheep.getPrevTimeSheepEating() + (float) (entitySheep.getTimeSheepEating() - entitySheep.getPrevTimeSheepEating()) * partialTick;
            this.isEatingAnimPlaying = true;
            if (this.headBobTime < 5.0F) {
                this.head.y = 6.0F + 2.0F * (this.headBobTime + 1.0F);
            } else if (this.headBobTime < 35.0F) {
                this.head.y = 16.0F + MathHelper.sin(this.headBobTime * 0.05F * 30.5F) / 3.0F;
            } else if (this.headBobTime < 40.0F) {
                this.head.y = 16.0F - 2.0F * (this.headBobTime - 34.0F);
            }
        } else {
            this.headBobTime = 0.0F;
        }

    }

}
