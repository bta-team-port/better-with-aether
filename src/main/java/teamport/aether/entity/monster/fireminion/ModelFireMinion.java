package teamport.aether.entity.monster.fireminion;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;

@Environment(EnvType.CLIENT)
public class ModelFireMinion extends ModelBase {
    private final Cube bodyBottom;
    private final Cube bodyBrace;
    private final Cube bipedRightArm2;
    private final Cube bipedLeftArm2;
    private final Cube armRightBrace;
    private final Cube armLeftBrace;
    private final Cube head;
    private final Cube hair;
    private final Cube body;
    private final Cube armRight;
    private final Cube armLeft;

    public ModelFireMinion() {
        this.head = new Cube(0, 0);
        this.head.addBox(-4.0F, -9.0F, -3.0F, 8, 5, 7, 0.0F);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hair = new Cube(32, 0);
        this.hair.addBox(-4.0F, -4.0F, -4.0F, 8, 3, 8, 0.0F);
        this.hair.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body = new Cube(0, 12);
        this.body.addBox(-5.0F, -1.0F, -2.5F, 10, 6, 5, 0.0F);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bodyBottom = new Cube(0, 23);
        this.bodyBottom.addBox(-4.5F, 5.0F, -2.0F, 9, 5, 4, 0.0F);
        this.bodyBottom.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.bodyBrace = new Cube(27, 27);
        this.bodyBrace.addBox(-4.5F, 10.0F, -2.0F, 9, 1, 4, 0.5F);
        this.bodyBrace.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.armRight = new Cube(30, 11);
        this.armRight.addBox(-2.5F, -3.5F, -2.5F, 5, 5, 5, 0.5F);
        this.armRight.setRotationPoint(-8.0F, 2.0F, 0.0F);
        this.bipedRightArm2 = new Cube(30, 11);
        this.bipedRightArm2.addBox(-2.5F, 1.5F, -2.5F, 5, 10, 5, 0.0F);
        this.bipedRightArm2.setRotationPoint(-8.0F, 2.0F, 0.0F);
        this.armRightBrace = new Cube(30, 26);
        this.armRightBrace.addBox(-2.5F, 6.5F, -2.5F, 5, 1, 5, 0.25F);
        this.armRightBrace.setRotationPoint(-8.0F, 2.0F, 0.0F);
        this.armLeft = new Cube(30, 11);
        this.armLeft.mirror = true;
        this.armLeft.addBox(-2.5F, -3.5F, -2.5F, 5, 5, 5, 0.5F);
        this.armLeft.setRotationPoint(8.0F, 2.0F, 0.0F);
        this.bipedLeftArm2 = new Cube(30, 11);
        this.bipedLeftArm2.mirror = true;
        this.bipedLeftArm2.addBox(-2.5F, 1.5F, -2.5F, 5, 10, 5, 0.0F);
        this.bipedLeftArm2.setRotationPoint(8.0F, 2.0F, 0.0F);
        this.armLeftBrace = new Cube(30, 26);
        this.armLeftBrace.mirror = true;
        this.armLeftBrace.addBox(-2.5F, 6.5F, -2.5F, 5, 1, 5, 0.25F);
        this.armLeftBrace.setRotationPoint(8.0F, 2.0F, 0.0F);
    }

    @Override
    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.head.render(scale);
        this.hair.render(scale);
        this.body.render(scale);
        this.bodyBottom.render(scale);
        this.bodyBrace.render(scale);
        this.armRight.render(scale);
        this.bipedRightArm2.render(scale);
        this.armRightBrace.render(scale);
        this.armLeft.render(scale);
        this.bipedLeftArm2.render(scale);
        this.armLeftBrace.render(scale);
    }

    @Override
    public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.head.yRot = headYaw / 57.29578F;
        this.head.xRot = headPitch / 57.29578F;
        this.hair.yRot = this.head.yRot;
        this.hair.xRot = this.head.xRot;
        this.armRight.xRot = 0.0F;
        this.armLeft.xRot = 0.0F;
        this.armRight.zRot = 0.0F;
        this.armLeft.zRot = 0.0F;

        this.armRight.yRot = 0.0F;
        this.armLeft.yRot = 0.0F;
        Cube var10000;
        if (this.onGround > -9990.0F) {
            float f6 = this.onGround;
            this.body.yRot = MathHelper.sin(MathHelper.sqrt(f6) * 3.141593F * 2.0F) * 0.2F;
            var10000 = this.armRight;
            var10000.yRot += this.body.yRot;
            var10000 = this.armLeft;
            var10000.yRot += this.body.yRot;
            var10000.xRot += this.body.xRot;
            f6 = 1.0F - this.onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            float f7 = MathHelper.sin(f6 * 3.141593F);
            float f8 = MathHelper.sin(this.onGround * 3.141593F) * -(this.head.xRot - 0.7F) * 0.75F;
            var10000 = this.armRight;
            var10000.xRot = (float) (var10000.xRot - (f7 * 1.2 + f8));
            var10000.yRot += this.body.yRot * 2.0F;
            this.armRight.zRot = MathHelper.sin(this.onGround * 3.141593F) * -0.4F;
        }

        var10000 = this.armRight;
        var10000.zRot += MathHelper.cos(limbPitch * 0.09F) * 0.05F + 0.05F;
        var10000 = this.armLeft;
        var10000.zRot -= MathHelper.cos(limbPitch * 0.09F) * 0.05F + 0.05F;
        var10000 = this.armRight;
        var10000.xRot += MathHelper.sin(limbPitch * 0.067F) * 0.05F;
        var10000 = this.armLeft;
        var10000.xRot -= MathHelper.sin(limbPitch * 0.067F) * 0.05F;
        this.bodyBrace.xRot = this.bodyBottom.xRot = this.body.xRot;
        this.bodyBrace.yRot = this.bodyBottom.yRot = this.body.yRot;
        this.armLeftBrace.xRot = this.bipedLeftArm2.xRot = this.armLeft.xRot;
        this.armLeftBrace.yRot = this.bipedLeftArm2.yRot = this.armLeft.yRot;
        this.armLeftBrace.zRot = this.bipedLeftArm2.zRot = this.armLeft.zRot;
        this.armRightBrace.xRot = this.bipedRightArm2.xRot = this.armRight.xRot;
        this.armRightBrace.yRot = this.bipedRightArm2.yRot = this.armRight.yRot;
        this.armRightBrace.zRot = this.bipedRightArm2.zRot = this.armRight.zRot;
    }

}
