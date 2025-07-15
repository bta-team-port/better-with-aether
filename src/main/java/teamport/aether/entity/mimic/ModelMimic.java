package teamport.aether.entity.mimic;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelMimic extends ModelBase {
    public Cube box;
    public Cube boxLid;
    public Cube legLeft;
    public Cube legRight;

    public ModelMimic() {
            this.box = new Cube(0, 19);
            this.box.addBox(-4.0F, 5.0F, -4.0F, 8, 5, 8);

            this.boxLid = new Cube(12, 0);
            this.boxLid.addBox(-4.0F, 7.0F, -8, 8, 3, 8);

            this.legLeft = new Cube(0, 0);
            this.legLeft.addBox(-1.5F, 10.0F, -1.5F, 3, 7, 3);

            this.legRight = new Cube(0, 0);
            this.legRight.addBox(-1.5F, 10.0F, -1.5F, 3, 7, 3);

            this.box.setRotationPoint(0, 2.0F, 0);
            this.boxLid.setRotationPoint(0, -3.0F, 4F);

            this.legLeft.setRotationPoint(-2.5F, 2.0F, 0);
            this.legRight.setRotationPoint(2.5F, 2.0F, 0);

            boxLid.setRotationAngle(-0.3F, 0, 0);
        }

        @Override
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
