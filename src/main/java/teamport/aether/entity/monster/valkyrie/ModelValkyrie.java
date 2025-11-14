package teamport.aether.entity.monster.valkyrie;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.core.util.helper.MathHelper;

@Environment(EnvType.CLIENT)
public class ModelValkyrie extends ModelBiped {
    private final Cube bipedBody2;
    private final Cube bipedRightArm2;
    private final Cube bipedLeftArm2;
    private final Cube wingLeft;
    private final Cube wingRight;
    private final Cube[] skirt;
    private final Cube[] strand;
    private final Cube halo;

    public ModelValkyrie() {
        this(0.0F);
    }

    public ModelValkyrie(float expandAmount) {
        this.holdingLeftHand = false;
        this.holdingRightHand = false;
        this.sneaking = false;
        this.head = new Cube(0, 0);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, expandAmount);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body = new Cube(12, 16);
        this.body.addBox(-3.0F, 0.0F, -1.5F, 6, 12, 3, expandAmount);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody2 = new Cube(12, 16);
        this.bipedBody2.addBox(-3.0F, 0.5F, -1.25F, 6, 5, 3, expandAmount + 0.75F);
        this.bipedBody2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.armRight = new Cube(30, 16);
        this.armRight.addBox(-2.0F, -1.5F, -1.5F, 3, 12, 3, expandAmount);
        this.armRight.setRotationPoint(-4.0F, 1.5F, 0.0F);
        this.armLeft = new Cube(30, 16);
        this.armLeft.mirror = true;
        this.armLeft.addBox(-1.0F, -1.5F, -1.5F, 3, 12, 3, expandAmount);
        this.armLeft.setRotationPoint(5.0F, 1.5F, 0.0F);
        this.bipedRightArm2 = new Cube(52, 11);
        this.bipedRightArm2.addBox(-3.0F, -1.5F, -1.5F, 3, 3, 3, expandAmount + 0.75F);
        this.bipedRightArm2.setRotationPoint(-4.0F, 1.5F, 0.0F);
        this.bipedLeftArm2 = new Cube(52, 11);
        this.bipedLeftArm2.mirror = true;
        this.bipedLeftArm2.addBox(-1.0F, -1.5F, -1.5F, 3, 3, 3, expandAmount + 0.75F);
        this.bipedLeftArm2.setRotationPoint(5.0F, 1.5F, 0.0F);
        this.legRight = new Cube(0, 16);
        this.legRight.addBox(-2.0F, 0.0F, -1.5F, 3, 12, 3, expandAmount);
        this.legRight.setRotationPoint(-1.0F, 12.0F, 0.0F);
        this.legLeft = new Cube(0, 16);
        this.legLeft.mirror = true;
        this.legLeft.addBox(-2.0F, 0.0F, -1.5F, 3, 12, 3, expandAmount);
        this.legLeft.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.wingLeft = new Cube(24, 31);
        this.wingLeft.addBox(0.0F, -4.5F, 0.0F, 19, 8, 1, expandAmount);
        this.wingLeft.setRotationPoint(0.5F, 1.5F, 2.625F);
        this.wingRight = new Cube(24, 31);
        this.wingRight.mirror = true;
        this.wingRight.addBox(-19.0F, -4.5F, 0.0F, 19, 8, 1, expandAmount);
        this.wingRight.setRotationPoint(-0.5F, 1.5F, 2.625F);


        // POSITIONS REFER TO VALKYRIE POV

        // FRONT RIGHT
        this.skirt = new Cube[6];
        this.skirt[0] = new Cube(0, 0);
        this.skirt[0].addBox(0.0F, 0.0F, -1.0F, 3, 6, 0, expandAmount);
        this.skirt[0].setRotationPoint(-3.0F, 9.0F, -0.5F);

        // FRONT LEFT
        this.skirt[1] = new Cube(0, 0);
        this.skirt[1].addBox(0.0F, 0.0F, -1.0F, 3, 6, 0, expandAmount);
        this.skirt[1].setRotationPoint(0.0F, 9.0F, -0.5F);

        // BACK RIGHT
        this.skirt[2] = new Cube(0, 0);
        this.skirt[2].addBox(0.0F, 0.0F, 0.0F, 3, 6, 0, expandAmount);
        this.skirt[2].setRotationPoint(-3.0F, 9.0F, 1.5F);

        // BACK LEFT
        this.skirt[3] = new Cube(0, 0);
        this.skirt[3].addBox(0.0F, 0.0F, 0.0F, 3, 6, 0, expandAmount);
        this.skirt[3].setRotationPoint(0.0F, 9.0F, 1.5F);

        // RIGHT
        this.skirt[4] = new Cube(55, 19);
        this.skirt[4].addBox(-1.0F, 0.0F, 0.0F, 0, 6, 3, expandAmount);
        this.skirt[4].setRotationPoint(-2.0F, 9.0F, -1.5F);

        // LEFT
        this.skirt[5] = new Cube(55, 19);
        this.skirt[5].addBox(0.0F, 0.0F, 0.0F, 0, 6, 3, expandAmount);
        this.skirt[5].setRotationPoint(3.0F, 9.0F, -1.5F);


        this.strand = new Cube[22];

        for (int i = 0; i < 22; ++i) {
            this.strand[i] = new Cube(42 + i % 7, 17);
        }

        this.strand[0].addBox(-5.0F, -7.0F, -4.0F, 1, 3, 1, expandAmount);
        this.strand[0].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[1].addBox(4.0F, -7.0F, -4.0F, 1, 3, 1, expandAmount);
        this.strand[1].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[2].addBox(-5.0F, -7.0F, -3.0F, 1, 4, 1, expandAmount);
        this.strand[2].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[3].addBox(4.0F, -7.0F, -3.0F, 1, 4, 1, expandAmount);
        this.strand[3].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[4].addBox(-5.0F, -7.0F, -2.0F, 1, 4, 1, expandAmount);
        this.strand[4].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[5].addBox(4.0F, -7.0F, -2.0F, 1, 4, 1, expandAmount);
        this.strand[5].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[6].addBox(-5.0F, -7.0F, -1.0F, 1, 5, 1, expandAmount);
        this.strand[6].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[7].addBox(4.0F, -7.0F, -1.0F, 1, 5, 1, expandAmount);
        this.strand[7].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[8].addBox(-5.0F, -7.0F, 0.0F, 1, 5, 1, expandAmount);
        this.strand[8].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[9].addBox(4.0F, -7.0F, 0.0F, 1, 5, 1, expandAmount);
        this.strand[9].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[10].addBox(-5.0F, -7.0F, 1.0F, 1, 6, 1, expandAmount);
        this.strand[10].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[11].addBox(4.0F, -7.0F, 1.0F, 1, 6, 1, expandAmount);
        this.strand[11].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[12].addBox(-5.0F, -7.0F, 2.0F, 1, 7, 1, expandAmount);
        this.strand[12].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[13].addBox(4.0F, -7.0F, 2.0F, 1, 7, 1, expandAmount);
        this.strand[13].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[14].addBox(-5.0F, -7.0F, 3.0F, 1, 8, 1, expandAmount);
        this.strand[14].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[15].addBox(4.0F, -7.0F, 3.0F, 1, 8, 1, expandAmount);
        this.strand[15].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[16].addBox(-4.0F, -7.0F, 4.0F, 1, 9, 1, expandAmount);
        this.strand[16].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[17].addBox(3.0F, -7.0F, 4.0F, 1, 9, 1, expandAmount);
        this.strand[17].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[18] = new Cube(42, 17);
        this.strand[18].addBox(-3.0F, -7.0F, 4.0F, 3, 10, 1, expandAmount);
        this.strand[18].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[19] = new Cube(43, 17);
        this.strand[19].addBox(0.0F, -7.0F, 4.0F, 3, 10, 1, expandAmount);
        this.strand[19].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[20].addBox(-1.0F, -7.0F, -5.0F, 1, 2, 1, expandAmount);
        this.strand[20].setRotationPoint(0.0F, 0.0F, 0.0F);
        this.strand[21].addBox(0.0F, -7.0F, -5.0F, 1, 3, 1, expandAmount);
        this.strand[21].setRotationPoint(0.0F, 0.0F, 0.0F);

        this.halo = new Cube(25, 9);
        this.halo.addBox(-3.5F, -11.0F, -3.5F, 7, 0, 7, expandAmount);
        this.halo.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(float limbSwing, float limbyRot, float limbxRot, float headyRot, float headxRot, float scale) {
        this.setupAnimation(limbSwing, limbyRot, limbxRot, headyRot, headxRot, scale);
        this.head.render(scale);
        this.body.render(scale);
        this.armRight.render(scale);
        this.armLeft.render(scale);
        this.legRight.render(scale);
        this.legLeft.render(scale);
        this.bipedBody2.render(scale);
        this.bipedRightArm2.render(scale);
        this.bipedLeftArm2.render(scale);
        this.wingLeft.render(scale);
        this.wingRight.render(scale);

        int i;

        for (i = 0; i < 6; ++i) {
            this.skirt[i].render(scale);
        }

        for (i = 0; i < 22; ++i) {
            this.strand[i].render(scale);
        }

        if (LightmapHelper.isLightmapEnabled()) {
            LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(15, 15));
        }

        this.halo.render(scale);
    }

    @Override
    public void setupAnimation(float limbSwing, float limbyRot, float limbxRot, float headyRot, float headxRot, float scale) {
        super.setupAnimation(limbSwing, limbyRot, limbxRot, headyRot, headxRot, scale);
        this.head.yRot = headyRot / 57.29578F;
        this.head.xRot = headxRot / 57.29578F;
        this.armRight.xRot = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 2.0F * limbyRot * 0.5F;
        this.armLeft.xRot = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbyRot * 0.5F;
        this.armRight.zRot = 0.05F;
        this.armLeft.zRot = -0.05F;
        this.legRight.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbyRot;
        this.legLeft.xRot = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbyRot;
        this.legRight.yRot = 0.0F;
        this.legLeft.yRot = 0.0F;

        int i;
        for (i = 0; i < 22; ++i) {
            this.strand[i].yRot = this.head.yRot;
            this.strand[i].xRot = this.head.xRot;
        }

        this.halo.yRot = this.head.yRot;
        this.halo.xRot = this.head.xRot;

        Cube var10000;

        if (this.holdingLeftHand) {
            this.armLeft.xRot = this.armLeft.xRot * 0.5F - 0.3141593F;
        }

        if (this.holdingRightHand) {
            this.armRight.xRot = this.armRight.xRot * 0.5F - 0.3141593F;
        }

        this.armRight.yRot = 0.0F;
        this.armLeft.yRot = 0.0F;
        if (this.onGround > -9990.0F) {
            float f6 = this.onGround;
            this.bipedBody2.yRot = this.body.yRot = MathHelper.sin(MathHelper.sqrt(f6) * 3.141593F * 2.0F) * 0.2F;
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
        var10000.zRot += MathHelper.cos(limbxRot * 0.09F) * 0.05F + 0.05F;
        var10000 = this.armLeft;
        var10000.zRot -= MathHelper.cos(limbxRot * 0.09F) * 0.05F + 0.05F;
        var10000 = this.armRight;
        var10000.xRot += MathHelper.sin(limbxRot * 0.067F) * 0.05F;
        var10000 = this.armLeft;
        var10000.xRot -= MathHelper.sin(limbxRot * 0.067F) * 0.05F;

        this.bipedRightArm2.zRot = this.armRight.zRot;
        this.bipedRightArm2.yRot = this.armRight.yRot;
        this.bipedRightArm2.xRot = this.armRight.xRot;
        this.bipedLeftArm2.zRot = this.armLeft.zRot;
        this.bipedLeftArm2.xRot = this.armLeft.xRot;
        this.wingLeft.yRot = -0.2F;
        this.wingRight.yRot = 0.2F;
        this.wingLeft.zRot = -0.125F;
        this.wingRight.zRot = 0.125F;
        this.skirt[0].xRot = -0.2F;
        this.skirt[1].xRot = -0.2F;
        this.skirt[2].xRot = 0.2F;
        this.skirt[3].xRot = 0.2F;
        this.skirt[4].zRot = 0.2F;
        this.skirt[5].zRot = -0.2F;
        if (this.legLeft.xRot < -0.3F) {
            var10000 = this.skirt[1];
            var10000.xRot += this.legLeft.xRot + 0.3F;
            var10000 = this.skirt[2];
            var10000.xRot -= this.legLeft.xRot + 0.3F;
        }

        if (this.legLeft.xRot > 0.3F) {
            var10000 = this.skirt[3];
            var10000.xRot += this.legLeft.xRot - 0.3F;
            var10000 = this.skirt[0];
            var10000.xRot -= this.legLeft.xRot - 0.3F;
        }
    }

}
