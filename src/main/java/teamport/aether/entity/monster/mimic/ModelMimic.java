package teamport.aether.entity.monster.mimic;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;

@Environment(EnvType.CLIENT)
public class ModelMimic extends ModelBase {
    private final Cube box;
    private final Cube boxLid;
    private final Cube leftLeg;
    private final Cube rightLeg;

    public ModelMimic() {
        this.box = new Cube(0, 19);
        this.box.addBox(-4.0F, -5.0F, -4.0F, 8, 5, 8);

        this.boxLid = new Cube(12, 0);
        this.boxLid.addBox(-4.0F, -3.0F, -8, 8, 3, 8);

        this.leftLeg = new Cube(0, 0);
        this.leftLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 7, 3);

        this.rightLeg = new Cube(0, 0);
        this.rightLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 7, 3);

        this.box.setRotationPoint(0, 17.0F, 0);
        this.boxLid.setRotationPoint(0, 12.0F, 4.0F);

        this.leftLeg.setRotationPoint(-2.0F, 17.0F, 0);
        this.rightLeg.setRotationPoint(2.0F, 17.0F, 0);

    }

    @Override
    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.box.render(scale);
        this.boxLid.render(scale);
        this.rightLeg.render(scale);
        this.leftLeg.render(scale);
    }

    @Override
    public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

        this.boxLid.xRot = -0.8F + (MathHelper.cos(limbSwing * 0.6662F) * (limbYaw * 1.4f));

        this.rightLeg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.1F * limbYaw;
        this.leftLeg.xRot = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.1F * limbYaw;
    }
}
