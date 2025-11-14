package teamport.aether.entity.animal.phow;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelQuadruped;

@Environment(EnvType.CLIENT)
public class ModelPhow extends ModelQuadruped {
    private final Cube udders;
    private final Cube horn1;
    private final Cube horn2;

    private final Cube leftWingInner;
    private final Cube leftWingOuter;
    private final Cube rightWingInner;
    private final Cube rightWingOuter;

    public ModelPhow() {
        super(12, 0.0F);
        this.head = new Cube(0, 0, 128, 64);
        this.head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
        this.head.setRotationPoint(0.0F, 4.0F, -8.0F);

        this.horn1 = new Cube(22, 0, 128, 64);
        this.horn1.addBox(-5.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.horn1.setRotationPoint(0.0F, 4.0F, -8.0F);

        this.horn2 = new Cube(22, 0, 128, 64);
        this.horn2.addBox(4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.horn2.setRotationPoint(0.0F, 4.0F, -8.0F);

        this.udders = new Cube(52, 0, 128, 64);
        this.udders.addBox(-2.0F, -3.0F, 0.0F, 4, 6, 2, 0.0F);
        this.udders.setRotationPoint(0.0F, 14.0F, 6.0F);
        this.udders.xRot = 1.5707964F;

        this.body = new Cube(18, 4, 128, 64);
        this.body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);

        this.leg1 = new Cube(0, 16, 128, 64);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0f);
        this.leg1.setRotationPoint(-3.0F, 12, 7.0F);
        this.leg2 = new Cube(0, 16, 128, 64);
        this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0f);
        this.leg2.setRotationPoint(3.0F, 12, 7.0F);
        this.leg3 = new Cube(0, 16, 128, 64);
        this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0f);
        this.leg3.setRotationPoint(-3.0F, 12, -5.0F);
        this.leg4 = new Cube(0, 16, 128, 64);
        this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0f);
        this.leg4.setRotationPoint(3.0F, 12, -5.0F);

        leftWingInner = new Cube(0, 32, 128, 64);
        leftWingInner.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);

        leftWingOuter = new Cube(20, 32, 128, 64);
        leftWingOuter.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);

        rightWingInner = new Cube(0, 32, 128, 64);
        rightWingInner.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);

        rightWingOuter = new Cube(40, 32, 128, 64);
        rightWingOuter.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);

        rightWingOuter.yRot = 3.1415927F;

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

    @Override
    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.render(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.horn1.render(scale);
        this.horn2.render(scale);
        this.udders.render(scale);
        leftWingInner.render(scale);
        rightWingInner.render(scale);
        leftWingOuter.render(scale);
        rightWingOuter.render(scale);
    }

    @Override
    public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.horn1.yRot = this.head.yRot;
        this.horn1.xRot = this.head.xRot;
        this.horn2.yRot = this.head.yRot;
        this.horn2.xRot = this.head.xRot;
    }
    public Cube getLeftWingInner() {
        return leftWingInner;
    }
    public Cube getLeftWingOuter() {
        return leftWingOuter;
    }
    public Cube getRightWingInner() {
        return rightWingInner;
    }
    public Cube getRightWingOuter() {
        return rightWingOuter;
    }
}
