package teamport.aether.entity.boss.slider;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class ModelSlider extends ModelBase {
    private final Cube head;


    public ModelSlider() {
        this.head = new Cube(0, 0);
        this.head.addBox(-8.0F, -16.0F, -8.0F, 16, 16, 16, 0.0F);
        this.head.setRotationPoint(0.0F, 12.0623F, 0.0F);
    }

    @Override
    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        GL11.glPushMatrix();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        this.head.render(scale);
        GL11.glPopMatrix();
    }

    @Override
    public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.head.yRot = 0.0F;
        this.head.xRot = 0.0F;
    }
}
