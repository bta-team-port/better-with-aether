package bta.aether.entity.model;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelQuadruped;

public class ModelPhyg1 extends ModelQuadruped {
    public Cube nose;
    public ModelPhyg1() {
        super(6, 0.0F);
        this.nose = new Cube(16, 16);
        this.nose.addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1);
        this.nose.setRotationPoint(0.0F, 12.0F, -6.0F);
    }

    public ModelPhyg1(float f) {
        super(6, f);
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.render(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.nose.render(scale);
    }

    public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.nose.rotateAngleX = headPitch / 57.29578F;
        this.nose.rotateAngleY = headYaw / 57.29578F;
    }

}
