package bta.aether.entity.model;

import bta.aether.entity.EntityPhyg;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelPhyg2 extends ModelBase {
    public Cube leftWingInner = new Cube(0, 0);
    public Cube leftWingOuter = new Cube(20, 0);
    public Cube rightWingInner = new Cube(0, 0);
    public Cube rightWingOuter = new Cube(40, 0);
    public static EntityPhyg pig;

    public ModelPhyg2() {
        this.leftWingInner.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.leftWingOuter.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.rightWingInner.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.rightWingOuter.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.rightWingOuter.rotateAngleY = 3.1415927F;
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        float wingBend = -((float)Math.acos(pig.wingFold));
        float x = 32.0F * pig.wingFold / 4.0F;
        float y = -32.0F * (float)Math.sqrt(1.0F - pig.wingFold * pig.wingFold) / 4.0F;
        float z = 0.0F;
        float x2 = x * (float)Math.cos(pig.wingAngle) - y * (float)Math.sin(pig.wingAngle);
        float y2 = x * (float)Math.sin(pig.wingAngle) + y * (float)Math.cos(pig.wingAngle);
        this.leftWingInner.setRotationPoint(4.0F + x2, y2 + 12.0F, z);
        this.rightWingInner.setRotationPoint(-4.0F - x2, y2 + 12.0F, z);
        x *= 3.0F;
        x2 = x * (float)Math.cos(pig.wingAngle) - y * (float)Math.sin(pig.wingAngle);
        y2 = x * (float)Math.sin(pig.wingAngle) + y * (float)Math.cos(pig.wingAngle);
        this.leftWingOuter.setRotationPoint(4.0F + x2, y2 + 12.0F, z);
        this.rightWingOuter.setRotationPoint(-4.0F - x2, y2 + 12.0F, z);
        this.leftWingInner.rotateAngleZ = pig.wingAngle + wingBend + 1.5707964F;
        this.leftWingOuter.rotateAngleZ = pig.wingAngle - wingBend + 1.5707964F;
        this.rightWingInner.rotateAngleZ = -(pig.wingAngle + wingBend - 1.5707964F);
        this.rightWingOuter.rotateAngleZ = -(pig.wingAngle - wingBend + 1.5707964F);
        this.leftWingOuter.renderWithRotation(scale);
        this.leftWingInner.renderWithRotation(scale);
        this.rightWingOuter.renderWithRotation(scale);
        this.rightWingInner.renderWithRotation(scale);
    }

    public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
    }
}