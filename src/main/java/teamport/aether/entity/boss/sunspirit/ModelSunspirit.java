package teamport.aether.entity.boss.sunspirit;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class ModelSunspirit extends ModelBiped {
    private final Cube bipedBody2;
    private final Cube bipedBody3;
    private final Cube bipedRightArm2;
    private final Cube bipedLeftArm2;
    private final Cube bipedRightArm3;
    private final Cube bipedLeftArm3;

    public ModelSunspirit() {
        this(0.0F);
    }

    public ModelSunspirit(float f) {
        this(f, 0.0F);
    }

    public ModelSunspirit(float f, float f1) {
        this.holdingLeftHand = false;
        this.holdingRightHand = false;
        this.sneaking = false;
        this.head = new Cube(0, 0);
        this.head.addBox(-4.0F, -8.0F, -3.0F, 8, 5, 7, f);
        this.head.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.hair = new Cube(32, 0);
        this.hair.addBox(-4.0F, -3.0F, -4.0F, 8, 3, 8, f);
        this.hair.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.body = new Cube(0, 12);
        this.body.addBox(-5.0F, 0.0F, -2.5F, 10, 6, 5, f);
        this.body.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.bipedBody2 = new Cube(0, 23);
        this.bipedBody2.addBox(-4.5F, 6.0F, -2.0F, 9, 5, 4, f);
        this.bipedBody2.setRotationPoint(0.0F, 0.0F + f1, 0.0F);

        this.bipedBody3 = new Cube(27, 27);
        this.bipedBody3.addBox(-4.5F, 11.0F, -2.0F, 9, 1, 4, f + 0.5F);
        this.bipedBody3.setRotationPoint(0.0F, 0.0F + f1, 0.0F);

        this.armRight = new Cube(30, 11);
        this.armRight.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, f + 0.5F);
        this.armRight.setRotationPoint(-8.0F, 2.0F + f1, 0.0F);
        this.bipedRightArm2 = new Cube(30, 11);
        this.bipedRightArm2.addBox(-2.5F, 2.5F, -2.5F, 5, 10, 5, f);
        this.bipedRightArm2.setRotationPoint(-8.0F, 2.0F + f1, 0.0F);
        this.bipedRightArm3 = new Cube(30, 26);
        this.bipedRightArm3.addBox(-2.5F, 7.5F, -2.5F, 5, 1, 5, f + 0.25F);
        this.bipedRightArm3.setRotationPoint(-8.0F, 2.0F + f1, 0.0F);
        this.armLeft = new Cube(30, 11);
        this.armLeft.mirror = true;
        this.armLeft.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, f + 0.5F);
        this.armLeft.setRotationPoint(8.0F, 2.0F + f1, 0.0F);
        this.bipedLeftArm2 = new Cube(30, 11);
        this.bipedLeftArm2.mirror = true;
        this.bipedLeftArm2.addBox(-2.5F, 2.5F, -2.5F, 5, 10, 5, f);
        this.bipedLeftArm2.setRotationPoint(8.0F, 2.0F + f1, 0.0F);
        this.bipedLeftArm3 = new Cube(30, 26);
        this.bipedLeftArm3.mirror = true;
        this.bipedLeftArm3.addBox(-2.5F, 7.5F, -2.5F, 5, 1, 5, f + 0.25F);
        this.bipedLeftArm3.setRotationPoint(8.0F, 2.0F + f1, 0.0F);
    }

    @Override
    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glScalef(2.25F, 2.25F, 2.25F);
        GL11.glTranslatef(0.0F, -0.25F, 0.0F);
        this.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.head.render(scale);
        this.hair.render(scale);
        this.body.render(scale);
        this.bipedBody2.render(scale);
        this.bipedBody3.render(scale);
        this.armRight.render(scale);
        this.bipedRightArm2.render(scale);
        this.bipedRightArm3.render(scale);
        this.armLeft.render(scale);
        this.bipedLeftArm2.render(scale);
        this.bipedLeftArm3.render(scale);
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
        if (this.holdingLeftHand) {
            this.armLeft.xRot = this.armLeft.xRot * 0.5F - 0.3141593F;
        }

        if (this.holdingRightHand) {
            this.armRight.xRot = this.armRight.xRot * 0.5F - 0.3141593F;
        }

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
        this.bipedBody3.xRot = this.bipedBody2.xRot = this.body.xRot;
        this.bipedBody3.yRot = this.bipedBody2.yRot = this.body.yRot;
        this.bipedLeftArm3.xRot = this.bipedLeftArm2.xRot = this.armLeft.xRot;
        this.bipedLeftArm3.yRot = this.bipedLeftArm2.yRot = this.armLeft.yRot;
        this.bipedLeftArm3.zRot = this.bipedLeftArm2.zRot = this.armLeft.zRot;
        this.bipedRightArm3.xRot = this.bipedRightArm2.xRot = this.armRight.xRot;
        this.bipedRightArm3.yRot = this.bipedRightArm2.yRot = this.armRight.yRot;
        this.bipedRightArm3.zRot = this.bipedRightArm2.zRot = this.armRight.zRot;
    }

}
