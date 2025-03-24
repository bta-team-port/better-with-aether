package bta.aether.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class ModelAerbunny extends ModelBase {
    public Cube a;
    public Cube b;
    public Cube b2;
    public Cube b3;
    public Cube leg1;
    public Cube leg2;
    public Cube leg3;
    public Cube leg4;
    public Cube g;
    public Cube g2;
    public Cube h;
    public Cube h2;
    public float puffiness;

    public ModelAerbunny() {
        byte byte0 = 16;
        this.a = new Cube(0, 0);
        this.a.addBox(-2.0F, -1.0F, -4.0F, 4, 4, 6, 0.0F);
        this.a.setRotationPoint(0.0F, (float) (-1 + byte0), -4.0F);
        this.g = new Cube(14, 0);
        this.g.addBox(-2.0F, -5.0F, -3.0F, 1, 4, 2, 0.0F);
        this.g.setRotationPoint(0.0F, (float) (-1 + byte0), -4.0F);
        this.g2 = new Cube(14, 0);
        this.g2.addBox(1.0F, -5.0F, -3.0F, 1, 4, 2, 0.0F);
        this.g2.setRotationPoint(0.0F, (float) (-1 + byte0), -4.0F);
        this.h = new Cube(20, 0);
        this.h.addBox(-4.0F, 0.0F, -3.0F, 2, 3, 2, 0.0F);
        this.h.setRotationPoint(0.0F, (float) (-1 + byte0), -4.0F);
        this.h2 = new Cube(20, 0);
        this.h2.addBox(2.0F, 0.0F, -3.0F, 2, 3, 2, 0.0F);
        this.h2.setRotationPoint(0.0F, (float) (-1 + byte0), -4.0F);
        this.b = new Cube(0, 10);
        this.b.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
        this.b.setRotationPoint(0.0F, byte0, 0.0F);
        this.b2 = new Cube(0, 24);
        this.b2.addBox(-2.0F, 4.0F, -2.0F, 4, 3, 4, 0.0F);
        this.b2.setRotationPoint(0.0F, byte0, 0.0F);
        this.b3 = new Cube(29, 0);
        this.b3.addBox(-3.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F);
        this.b3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leg1 = new Cube(24, 16);
        this.leg1.addBox(-2.0F, 0.0F, -1.0F, 2, 2, 2);
        this.leg1.setRotationPoint(3.0F, (float) (3 + byte0), -3.0F);
        this.leg2 = new Cube(24, 16);
        this.leg2.addBox(0.0F, 0.0F, -1.0F, 2, 2, 2);
        this.leg2.setRotationPoint(-3.0F, (float) (3 + byte0), -3.0F);
        this.leg3 = new Cube(16, 24);
        this.leg3.addBox(-2.0F, 0.0F, -4.0F, 2, 2, 4);
        this.leg3.setRotationPoint(3.0F, (float) (3 + byte0), 4.0F);
        this.leg4 = new Cube(16, 24);
        this.leg4.addBox(0.0F, 0.0F, -4.0F, 2, 2, 4);
        this.leg4.setRotationPoint(-3.0F, (float) (3 + byte0), 4.0F);
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.a.render(scale);
        this.g.render(scale);
        this.g2.render(scale);
        this.h.render(scale);
        this.h2.render(scale);
        this.b.render(scale);
        this.b2.render(scale);
        GL11.glPushMatrix();
        float a = 1.0F + this.puffiness * 0.5F;
        GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        GL11.glScalef(a, a, a);
        this.b3.render(scale);
        GL11.glPopMatrix();
        this.leg1.render(scale);
        this.leg2.render(scale);
        this.leg3.render(scale);
        this.leg4.render(scale);
    }

    public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.a.rotateAngleX = (headPitch / 57.29578F);
        this.a.rotateAngleY = headYaw / 57.29578F;
        this.g.rotateAngleX = this.a.rotateAngleX;
        this.g.rotateAngleY = this.a.rotateAngleY;
        this.g2.rotateAngleX = this.a.rotateAngleX;
        this.g2.rotateAngleY = this.a.rotateAngleY;
        this.h.rotateAngleX = this.a.rotateAngleX;
        this.h.rotateAngleY = this.a.rotateAngleY;
        this.h2.rotateAngleX = this.a.rotateAngleX;
        this.h2.rotateAngleY = this.a.rotateAngleY;
        this.b.rotateAngleX = 1.570796F;
        this.b2.rotateAngleX = 1.570796F;
        this.b3.rotateAngleX = 1.570796F;
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbYaw;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.2F * limbYaw;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbYaw;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.2F * limbYaw;
    }
}
