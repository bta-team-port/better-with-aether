package teamport.aether.entity.animal.aerbunny;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class ModelAerbunny extends ModelBase {
    private final Cube head;
    private final Cube body;
    private final Cube tail;
    private final Cube cloudBody;
    private final Cube e1;
    private final Cube e2;
    private final Cube ff1;
    private final Cube ff2;
    private final Cube g;
    private final Cube g2;
    private final Cube h;
    private final Cube h2;
    private float puffiness;

    public ModelAerbunny() {
        byte byte0 = 19;
        this.head = new Cube(0, 0);
        this.head.addBox(-2.0F, -1.0F, -4.0F, 4, 4, 6, 0.0F);
        this.head.setRotationPoint(0.0F, (-1 + byte0), -4.0F);
        this.g = new Cube(14, 0);
        this.g.addBox(-2.0F, -5.0F, -3.0F, 1, 4, 2, 0.0F);
        this.g.setRotationPoint(0.0F, (-1 + byte0), -4.0F);
        this.g2 = new Cube(14, 0);
        this.g2.addBox(1.0F, -5.0F, -3.0F, 1, 4, 2, 0.0F);
        this.g2.setRotationPoint(0.0F, (-1 + byte0), -4.0F);
        this.h = new Cube(20, 0);
        this.h.addBox(-4.0F, 0.0F, -3.0F, 2, 3, 2, 0.0F);
        this.h.setRotationPoint(0.0F, (-1 + byte0), -4.0F);
        this.h2 = new Cube(20, 0);
        this.h2.addBox(2.0F, 0.0F, -3.0F, 2, 3, 2, 0.0F);
        this.h2.setRotationPoint(0.0F, (-1 + byte0), -4.0F);
        this.body = new Cube(0, 10);
        this.body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
        this.body.setRotationPoint(0.0F, byte0, 0.0F);
        this.tail = new Cube(0, 24);
        this.tail.addBox(-2.0F, 4.0F, -2.0F, 4, 3, 4, 0.0F);
        this.tail.setRotationPoint(0.0F, byte0, 0.0F);
        this.cloudBody = new Cube(29, 0);
        this.cloudBody.addBox(-3.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F);
        this.cloudBody.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.e1 = new Cube(24, 16);
        this.e1.addBox(-2.0F, 0.0F, -1.0F, 2, 2, 2);
        this.e1.setRotationPoint(3.0F, (3 + byte0), -3.0F);
        this.e2 = new Cube(24, 16);
        this.e2.addBox(0.0F, 0.0F, -1.0F, 2, 2, 2);
        this.e2.setRotationPoint(-3.0F, (3 + byte0), -3.0F);
        this.ff1 = new Cube(16, 24);
        this.ff1.addBox(-2.0F, 0.0F, -4.0F, 2, 2, 4);
        this.ff1.setRotationPoint(3.0F, (3 + byte0), 4.0F);
        this.ff2 = new Cube(16, 24);
        this.ff2.addBox(0.0F, 0.0F, -4.0F, 2, 2, 4);
        this.ff2.setRotationPoint(-3.0F, (3 + byte0), 4.0F);
    }

    @Override
    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.head.render(scale);
        this.g.render(scale);
        this.g2.render(scale);
        this.h.render(scale);
        this.h2.render(scale);
        this.body.render(scale);
        this.tail.render(scale);
        GL11.glPushMatrix();
        float a = 1.0F + this.puffiness * 0.5F;
        GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        GL11.glScalef(a, a, a);
        this.cloudBody.render(scale);
        GL11.glPopMatrix();
        this.e1.render(scale);
        this.e2.render(scale);
        this.ff1.render(scale);
        this.ff2.render(scale);
    }

    @Override
    public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.head.xRot = headPitch / 57.29578F;
        this.head.yRot = headYaw / 57.29578F;
        this.g.xRot = this.head.xRot;
        this.g.yRot = this.head.yRot;
        this.g2.xRot = this.head.xRot;
        this.g2.yRot = this.head.yRot;
        this.h.xRot = this.head.xRot;
        this.h.yRot = this.head.yRot;
        this.h2.xRot = this.head.xRot;
        this.h2.yRot = this.head.yRot;
        this.body.xRot = 1.570796F;
        this.tail.xRot = 1.570796F;
        this.cloudBody.xRot = 1.570796F;
        this.e1.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbYaw;
        this.ff1.xRot = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.2F * limbYaw;
        this.e2.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbYaw;
        this.ff2.xRot = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.2F * limbYaw;
    }

    public void setPuffiness(float puffiness) {
        this.puffiness = puffiness;
    }
}
