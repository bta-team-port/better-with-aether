package teamport.aether.entity.minicloud;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelMinicloud extends ModelBase {
    public Cube[] head;

    public ModelMinicloud() {
        this(0.0F);
    }

    public ModelMinicloud(float f) {
        this(f, 0.0F);
    }

    public ModelMinicloud(float f, float f1) {
        this.head = new Cube[5];
        this.head[0] = new Cube(0, 0);
        this.head[1] = new Cube(36, 0);
        this.head[2] = new Cube(36, 0);
        this.head[3] = new Cube(36, 8);
        this.head[4] = new Cube(36, 8);
        this.head[0].addBox(-4.5F, -4.5F, -4.5F, 9, 9, 9, f);
        this.head[0].setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.head[1].addBox(-3.5F, -3.5F, -5.5F, 7, 7, 1, f);
        this.head[1].setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.head[2].addBox(-3.5F, -3.5F, 4.5F, 7, 7, 1, f);
        this.head[2].setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.head[3].addBox(-5.5F, -3.5F, -3.5F, 1, 7, 7, f);
        this.head[3].setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.head[4].addBox(4.5F, -3.5F, -3.5F, 1, 7, 7, f);
        this.head[4].setRotationPoint(0.0F, 0.0F + f1, 0.0F);
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

        for (int i = 0; i < 5; ++i) {
            this.head[i].render(scale);
        }

    }

    public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {

        for (int i = 0; i < 5; ++i) {
            this.head[i].yRot = headYaw / 57.29578F;
            this.head[i].xRot = headPitch / 57.29578F;
        }

    }
}
