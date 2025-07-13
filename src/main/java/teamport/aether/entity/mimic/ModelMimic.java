package teamport.aether.entity.mimic;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBiped;

public class ModelMimic extends ModelBiped {
    Cube box;
    Cube boxLid;
    Cube legLeft;
    Cube legRight;

    public ModelMimic() {

        //Positive +X = RIGHT
        //Negative -X = LEFT

        //Positive +Y = DOWN
        //Negative -Y = UP

        //Positive +Z = BACK
        //Negative -Z = FORWARD

        this.box = new Cube(0, 19);
        this.box.addBox(-8.0F, 0.0F, -8.0F, 8, 5, 8);
        this.box.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.boxLid = new Cube(24, 0);
        this.boxLid.addBox(0.0F, 0.0F, 0.0F, 8, 3, 8);
        this.boxLid.setRotationPoint(-8.0F, -24.0F, 8.0F);
        this.legLeft = new Cube(0, 0);
        this.legLeft.addBox(-3.0F, 0.0F, -3.0F, 3, 7, 3);
        this.legLeft.setRotationPoint(-4.0F, -15.0F, 0.0F);
        this.legRight = new Cube(0, 0);
        this.legRight.addBox(-3.0F, 0.0F, -3.0F, 3, 7, 3);
        this.legRight.setRotationPoint(4.0F, -15.0F, 0.0F);

        this.legLeft.x -= 1.0f;
        this.legLeft.x += 1.0f;
        this.legRight.z += 0.0f;
        this.legRight.z += 0.0f;
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.box.render(scale);
        this.boxLid.render(scale);
        this.legRight.render(scale);
        this.legLeft.render(scale);
    }

    public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        super.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.boxLid.yRot = this.box.yRot;
        this.boxLid.xRot = this.box.xRot;
    }

}
