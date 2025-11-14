package teamport.aether.entity.animal.moa;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;

@Environment(EnvType.CLIENT)
public class ModelMoa extends ModelBase {
    private final Cube head;
    private final Cube palate;
    private final Cube body;
    private final Cube legs;
    private final Cube legs2;
    private final Cube wings;
    private final Cube wings2;
    private final Cube jaw;
    private final Cube neck;
    private final Cube feather1;
    private final Cube feather2;
    private final Cube feather3;

    public ModelMoa() {
        byte byte0 = 16;
        this.head = new Cube(0, 13);
        this.head.addBox(-2.0F, -4.0F, -6.0F, 4, 4, 8, 0.0F);
        this.head.setRotationPoint(0.0F, (-8 + byte0), -4.0F);

        this.palate = new Cube(24, 13);
        this.palate.addBox(-2.0F, -1.0F, -6.0F, 4, 0, 8, -0.1F);
        this.palate.setRotationPoint(0.0F, (-8 + byte0), -4.0F);

        this.jaw = new Cube(24, 13);
        this.jaw.addBox(-2.0F, -1.0F, -6.0F, 4, 1, 8, -0.1F);
        this.jaw.setRotationPoint(0.0F, (-8 + byte0), -4.0F);
        this.body = new Cube(0, 0);
        this.body.addBox(-3.0F, -3.0F, 0.0F, 6, 8, 5, 0.0F);
        this.body.setRotationPoint(0.0F, byte0, 0.0F);
        this.legs = new Cube(22, 0);
        this.legs.addBox(-1.0F, -1.0F, -1.0F, 2, 9, 2);
        this.legs.setRotationPoint(-2.0F, byte0, 1.0F);
        this.legs2 = new Cube(22, 0);
        this.legs2.addBox(-1.0F, -1.0F, -1.0F, 2, 9, 2);
        this.legs2.setRotationPoint(2.0F, byte0, 1.0F);

        this.wings = new Cube(52, 0);
        this.wings.addBox(-1.01F, -0.0F, -1.0F, 1, 8, 4);
        this.wings.setRotationPoint(-3.0F, (-4 + byte0), 0.0F);
        this.wings2 = new Cube(52, 0);
        this.wings2.addBox(0.01F, -0.0F, -1.0F, 1, 8, 4);
        this.wings2.setRotationPoint(3.0F, (-4 + byte0), 0.0F);

        this.neck = new Cube(44, 0);
        this.neck.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2);
        this.neck.setRotationPoint(0.0F, (-2 + byte0), -4.0F);
        this.feather1 = new Cube(30, 0);
        this.feather1.addBox(-1.0F, -5.0F, 5.0F, 2, 1, 5, -0.3F);
        this.feather1.setRotationPoint(0.0F, (1 + byte0), 1.0F);
        this.feather2 = new Cube(30, 0);
        this.feather2.addBox(-1.0F, -5.0F, 5.0F, 2, 1, 5, -0.3F);
        this.feather2.setRotationPoint(0.0F, (1 + byte0), 1.0F);
        this.feather3 = new Cube(30, 0);
        this.feather3.addBox(-1.0F, -5.0F, 5.0F, 2, 1, 5, -0.3F);
        this.feather3.setRotationPoint(0.0F, (1 + byte0), 1.0F);
        Cube var10000 = this.feather1;
        var10000.yRot += 0.5F;
        var10000 = this.feather2;
        var10000.yRot += 0.5F;
        var10000 = this.feather3;
        var10000.yRot += 0.5F;
    }

    @Override
    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.render(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.head.render(scale);
        this.palate.render(scale);
        this.jaw.render(scale);
        this.body.render(scale);
        this.legs.render(scale);
        this.legs2.render(scale);
        this.wings.render(scale);
        this.wings2.render(scale);
        this.neck.render(scale);
        this.feather1.render(scale);
        this.feather2.render(scale);
        this.feather3.render(scale);
    }

    @Override
    public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        float f6 = 3.141592653589793238462643383279F; // Pi
        this.head.xRot = headPitch / 57.29578F;
        this.head.yRot = headYaw / 57.29578F;
        this.palate.xRot = this.head.xRot;
        this.palate.yRot = this.head.yRot;
        this.jaw.xRot = this.head.xRot;
        this.jaw.yRot = this.head.yRot;
        this.body.xRot = 1.5707964F;
        this.legs.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw;
        this.legs2.xRot = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbYaw;
        if (limbPitch > 0.001F) {
            this.wings.z = -1.0F;
            this.wings2.z = -1.0F;
            this.wings.y = 12.0F;
            this.wings2.y = 12.0F;
            this.wings.xRot = 0.0F;
            this.wings2.xRot = 0.0F;
            this.wings.zRot = limbPitch;
            this.wings2.zRot = -limbPitch;
            this.legs.xRot = 0.6F;
            this.legs2.xRot = 0.6F;
        } else {
            this.wings.z = -3.0F;
            this.wings2.z = -3.0F;
            this.wings.y = 14.0F;
            this.wings2.y = 14.0F;
            this.wings.xRot = f6 / 2.0F;
            this.wings2.xRot = f6 / 2.0F;
            this.wings.zRot = 0.0F;
            this.wings2.zRot = 0.0F;
        }
        this.feather1.yRot = -0.375F;
        this.feather2.yRot = 0.0F;
        this.feather3.yRot = 0.375F;
        this.feather1.xRot = 0.25F;
        this.feather2.xRot = 0.25F;
        this.feather3.xRot = 0.25F;
        this.neck.xRot = 0.0F;
        this.neck.yRot = this.head.yRot;
        this.jaw.xRot += 0.35F;
    }
}
