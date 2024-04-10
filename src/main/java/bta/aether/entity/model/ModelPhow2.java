package bta.aether.entity.model;

import bta.aether.entity.EntityPhow;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelPhow2 extends ModelBase {
    private final Cube leftWingInner = new Cube(0, 0);
    private final Cube leftWingOuter = new Cube(20, 0);
    private final Cube rightWingInner = new Cube(0, 0);
    private final Cube rightWingOuter = new Cube(40, 0);
    public static EntityPhow flyingcow;

    public ModelPhow2() {
        this.leftWingInner.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.leftWingOuter.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.rightWingInner.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.rightWingOuter.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.rightWingOuter.rotationPointY = 3.1415927F;
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        float wingBend = -((float) Math.acos(flyingcow.wingFold));
        float x = 32.0F * flyingcow.wingFold / 4.0F;
        float y = -32.0F * (float) Math.sqrt(1.0F - flyingcow.wingFold * flyingcow.wingFold) / 4.0F;
        float z = 0.0F;
        float x2 = x * (float) Math.cos(flyingcow.wingAngle) - y * (float) Math.sin(flyingcow.wingAngle);
        float y2 = x * (float) Math.sin(flyingcow.wingAngle) + y * (float) Math.cos(flyingcow.wingAngle);
        this.leftWingInner.setRotationPoint(4.0F + x2, y2 + 6.0F, z);
        this.rightWingInner.setRotationPoint(-4.0F - x2, y2 + 6.0F, z);
        x *= 3.0F;
        x2 = x * (float)Math.cos(flyingcow.wingAngle) - y * (float)Math.sin(flyingcow.wingAngle);
        y2 = x * (float)Math.sin(flyingcow.wingAngle) + y * (float)Math.cos(flyingcow.wingAngle);
        this.leftWingOuter.setRotationPoint(4.0F + x2, y2 + 6.0F, z);
        this.rightWingOuter.setRotationPoint(-4.0F - x2, y2 + 6.0F, z);
        this.leftWingInner.rotationPointZ = flyingcow.wingAngle + wingBend + 1.5707964F;
        this.leftWingOuter.rotationPointZ = flyingcow.wingAngle - wingBend + 1.5707964F;
        this.rightWingInner.rotationPointZ = -(flyingcow.wingAngle + wingBend - 1.5707964F);
        this.rightWingOuter.rotationPointZ = -(flyingcow.wingAngle - wingBend + 1.5707964F);
        this.leftWingOuter.render(scale);
        this.leftWingInner.render(scale);
        this.rightWingOuter.render(scale);
        this.rightWingInner.render(scale);
    }

    public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
    }
}