package teamport.aether.entity.animal.aerwhale;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelAerwhale extends ModelBase {
    public Cube frontBody;
    public Cube frontPleats;

    public Cube middleBody;
    public Cube middlePleats;

    public Cube backBody;
    public Cube backPleats;

    public Cube topFin;

    public Cube backRightFin;
    public Cube backLeftFin;

    public Cube frontLeftFin;
    public Cube frontRightFin;

    public ModelAerwhale() {
        this.frontBody = new Cube(20, 0);
        this.frontBody.addBox(-3.5F, -3.5F, -12.5F, 7, 6, 10);

        this.middleBody = new Cube(0, 0);
        this.middleBody.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5);

        this.backBody = new Cube(0, 10);
        this.backBody.addBox(-1.5F, -1.5F, 2.5F, 3, 3, 4);

        this.topFin = new Cube(4, 17);
        this.topFin.addBox(-0.5F, -4.25F, -1.5F, 1, 2, 3);
        this.topFin.setRotationAngle(-0.125f, 0.0f, 0.0f);

        this.backLeftFin = new Cube(4, 18);
        this.backLeftFin.addBox(1.5F, -0.5F, 2.49F, 5, 1, 3);
        this.backLeftFin.setRotationAngle(0.0f, -0.25f, 0.0f);
        this.backRightFin = new Cube(4, 18);
        this.backRightFin.addBox(-6.5F, -0.5F, 2.49F, 5, 1, 3);
        this.backRightFin.setRotationAngle(0.0f, 0.25f, 0.0f);

        this.frontLeftFin = new Cube(6, 19);
        this.frontLeftFin.addBox(-5.5F, 0.5F, -6.0F, 4, 1, 2);
        this.frontLeftFin.setRotationAngle(0.0f, 0.25f, 0.0f);
        this.frontRightFin = new Cube(6, 19);
        this.frontRightFin.addBox(1.5F, 0.5F, -6.0F, 4, 1, 2);
        this.frontRightFin.setRotationAngle(0.0f, -0.25f, 0.0f);
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.frontBody.render(scale);
        this.middleBody.render(scale);
        this.backBody.render(scale);

        this.topFin.render(scale);

        this.backRightFin.render(scale);
        this.backLeftFin.render(scale);

        this.frontLeftFin.render(scale);
        this.frontRightFin.render(scale);
    }
}
