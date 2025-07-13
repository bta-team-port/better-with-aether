package teamport.aether.entity.mimic;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelMimic extends ModelBase {
    Cube box;
    Cube boxLid;
    Cube leftLeg;
    Cube rightLeg;

    public ModelMimic() {

        //Positive +X = RIGHT
        //Negative -X = LEFT

        //Positive +Y = DOWN
        //Negative -Y = UP

        //Positive +Z = BACK
        //Negative -Z = FORWARD

        this.box = new Cube(0, 19);
        this.box.addBox(0.0F, 25.0F, 0.0F, 8, 5, 8);
        this.box.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.boxLid = new Cube(24, 0);
        this.boxLid.addBox(0.0F, -20.0F, 0.0F, 8, 3, 8);
        this.boxLid.setRotationPoint(-8.0F, -24.0F, 8.0F);
        this.leftLeg = new Cube(0, 0);
        this.leftLeg.addBox(2.0F, 22.0F, 0.0F, 3, 7, 3);
        this.leftLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightLeg = new Cube(0, 0);
        this.rightLeg.addBox(-2.0F, 22.0F, 0.0F, 3, 7, 3);
        this.rightLeg.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.leftLeg.x -= 1.0f;
        this.leftLeg.x += 1.0f;
        this.rightLeg.z += 0.0f;
        this.rightLeg.z += 0.0f;
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.box.render(scale);
        this.boxLid.render(scale);
        this.rightLeg.render(scale);
        this.leftLeg.render(scale);
    }

    public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.boxLid.yRot = this.box.yRot;
        this.boxLid.xRot = this.box.xRot;
    }

}
