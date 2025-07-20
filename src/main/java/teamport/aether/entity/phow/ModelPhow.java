package teamport.aether.entity.phow;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelQuadruped;

public class ModelPhow extends ModelQuadruped {
    Cube udders;
    Cube horn1;
    Cube horn2;

    public ModelPhow() {
        super(12, 0.0F);
        this.head = new Cube(0, 0);
        this.head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
        this.head.setRotationPoint(0.0F, 4.0F, -8.0F);
        this.horn1 = new Cube(22, 0);
        this.horn1.addBox(-5.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.horn1.setRotationPoint(0.0F, 4.0F, -8.0F);
        this.horn2 = new Cube(22, 0);
        this.horn2.addBox(4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.horn2.setRotationPoint(0.0F, 4.0F, -8.0F);
        this.udders = new Cube(52, 0);
        this.udders.addBox(-2.0F, -3.0F, 0.0F, 4, 6, 2, 0.0F);
        this.udders.setRotationPoint(0.0F, 14.0F, 6.0F);
        this.udders.xRot = 1.5707964F;
        this.body = new Cube(18, 4);
        this.body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
        --this.leg1.x;
        ++this.leg2.x;
        Cube var10000 = this.leg1;
        var10000.z += 0.0F;
        var10000 = this.leg2;
        var10000.z += 0.0F;
        --this.leg3.x;
        ++this.leg4.x;
        --this.leg3.z;
        --this.leg4.z;
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.render(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.horn1.render(scale);
        this.horn2.render(scale);
        this.udders.render(scale);
    }

    public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.horn1.yRot = this.head.yRot;
        this.horn1.xRot = this.head.xRot;
        this.horn2.yRot = this.head.yRot;
        this.horn2.xRot = this.head.xRot;
    }
}
