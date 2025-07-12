package teamport.aether.entity.zephyr;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelZephyr extends ModelBase {
    Cube body;

    public ModelZephyr() {
        this.body = new Cube(0, 0);
        this.body.addBox(-8.0F, -4.0F, -8.0F, 15, 12, 17);
        this.body.setRotationPoint(-0.5F, 2F, 0.5F);
        Cube var10000 = this.body;
        var10000.y += (float)(16);
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.body.render(scale);
    }
}
