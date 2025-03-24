package bta.aether.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class ModelSlider extends ModelBase {
    public Cube head;

    public ModelSlider() {
        this(0.0F);
    }

    public ModelSlider(float f) {
        this(f, 0.0F);
    }

    public ModelSlider(float f, float f1) {
        this.head = new Cube(0, 0);
        this.head.addBox(-8.0F, -4f, -8.0F, 16, 16, 16, f);
        this.head.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        GL11.glPushMatrix();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        this.head.render(scale);
        GL11.glPopMatrix();
    }

    public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.head.rotateAngleY = 0.0F;
        this.head.rotateAngleX = 0.0F;
    }
}
