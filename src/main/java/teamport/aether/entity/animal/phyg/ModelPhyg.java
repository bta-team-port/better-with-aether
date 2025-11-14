package teamport.aether.entity.animal.phyg;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelQuadruped;

@Environment(EnvType.CLIENT)
public class ModelPhyg extends ModelQuadruped {
    private final Cube nose;
    private final Cube leftWingInner;
    private final Cube leftWingOuter;
    private final Cube rightWingInner;
    private final Cube rightWingOuter;

    public ModelPhyg() {
        this(0.0F);
    }

    public ModelPhyg(float f) {
        super(6, f);
        this.head = new Cube(0, 0, 128, 64);
        this.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, f);
        this.head.setRotationPoint(0.0F, (18 - 6), -6.0F);
        this.body = new Cube(28, 8, 128, 64);
        this.body.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, f);
        this.body.setRotationPoint(0.0F, (17 - 6), 2.0F);
        this.leg1 = new Cube(0, 16, 128, 64);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg1.setRotationPoint(-3.0F, (24 - 6), 7.0F);
        this.leg2 = new Cube(0, 16, 128, 64);
        this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg2.setRotationPoint(3.0F, (24 - 6), 7.0F);
        this.leg3 = new Cube(0, 16, 128, 64);
        this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg3.setRotationPoint(-3.0F, (24 - 6), -5.0F);
        this.leg4 = new Cube(0, 16, 128, 64);
        this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg4.setRotationPoint(3.0F, (24 - 6), -5.0F);
        this.nose = new Cube(16, 16, 128, 64);
        this.nose.addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1);
        this.nose.setRotationPoint(0.0F, 12.0F, -6.0F);

        leftWingInner = new Cube(0, 32, 128, 64);
        leftWingInner.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);

        leftWingOuter = new Cube(20, 32, 128, 64);
        leftWingOuter.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);

        rightWingInner = new Cube(0, 32, 128, 64);
        rightWingInner.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);

        rightWingOuter = new Cube(40, 32, 128, 64);
        rightWingOuter.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);

        rightWingOuter.yRot = 3.1415927F;
    }

    @Override
    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.render(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.nose.render(scale);
        leftWingInner.render(scale);
        rightWingInner.render(scale);
        leftWingOuter.render(scale);
        rightWingOuter.render(scale);
    }

    @Override
    public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.nose.xRot = headPitch / 57.29578F;
        this.nose.yRot = headYaw / 57.29578F;
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
