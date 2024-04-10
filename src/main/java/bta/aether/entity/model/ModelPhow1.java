package bta.aether.entity.model;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelQuadruped;

public class ModelPhow1 extends ModelQuadruped {
    Cube udders;
    Cube horn1;
    Cube horn2;

    public ModelPhow1() {
        super(12, 0.0F);
        this.head = new Cube(0, 0);
        this.head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
        this.head.setRotationPoint(0.0F, 4.0F, -8.0F);
        this.horn1 = new Cube(22, 0);
        this.horn1.addBox(-4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.horn1.setRotationPoint(0.0F, 3.0F, -7.0F);
        this.horn2 = new Cube(22, 0);
        this.horn2.addBox(3.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.horn2.setRotationPoint(0.0F, 3.0F, -7.0F);
        this.udders = new Cube(52, 0);
        this.udders.addBox(-2.0F, -3.0F, 0.0F, 4, 6, 2, 0.0F);
        this.udders.setRotationPoint(0.0F, 14.0F, 6.0F);
        this.udders.rotateAngleY = 1.570796F;
        this.body = new Cube(18, 4);
        this.body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
        --this.leg1.rotationPointX;
        ++this.leg2.rotationPointX;
        Cube var10000 = this.leg1;
        var10000.rotationPointZ += 0.0F;
        var10000 = this.leg2;
        var10000.rotationPointZ += 0.0F;
        --this.leg3.rotationPointX;
        ++this.leg4.rotationPointX;
        --this.leg3.rotationPointZ;
        --this.leg4.rotationPointZ;
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(f, f1, f2, f3, f4, f5);
        this.horn1.render(f5);
        this.horn2.render(f5);
        this.udders.render(f5);
    }

    public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.horn1.rotateAngleZ = this.head.rotateAngleZ;
        this.horn1.rotateAngleY = this.head.rotateAngleY;
        this.horn2.rotateAngleZ = this.head.rotateAngleZ;
        this.horn2.rotateAngleY = this.head.rotateAngleY;
    }
}
