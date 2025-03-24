package bta.aether.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

@Environment(EnvType.CLIENT)
public class ModelZephyr extends ModelBase {
    Cube body;

    public ModelZephyr() {
        byte byte0 = -16;

        body = new Cube(0, 0);
        body.addBox(-5.0F, 6f, -6.0F, 10, 7, 12);
        body.setRotationPoint(0, 0, 0);

        Cube bodyPoint = body;
        bodyPoint.rotationPointY += (float) (24 + byte0);
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

        body.render(scale);
    }
}

