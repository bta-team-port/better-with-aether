package bta.aether.entity.model;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelZephyr extends ModelBase {
    Cube body;

    public ModelZephyr() {
        byte byte0 = -16;
        this.body = new Cube(0, 0);
        this.body.addBox(-8.0F, -4.0F, -8.0F, 10, 7, 12);
        Cube var10000 = this.body;
        var10000.rotationPointY += (float) (24 + byte0);
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.body.render(scale);
    }
}

