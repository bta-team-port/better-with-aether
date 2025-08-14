package teamport.aether.entity.monster.zephyr;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

@Environment(EnvType.CLIENT)
public class ModelZephyr extends ModelBase {
    public Cube body;

    public ModelZephyr() {
        this.body = new Cube(0, 0);
        this.body.addBox(-7.5F, -5.0F, -8.5F, 15, 12, 17);
        this.body.setRotationPoint(0.0f, 1.0f, 0.0f);
        Cube var10000 = this.body;
        var10000.y += (float)(16);
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
        this.body.render(scale);
    }
}
