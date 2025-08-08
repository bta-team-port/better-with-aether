package teamport.aether.entity.vehicle.parachute;

import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

public class ModelParachute extends ModelBase {
    Cube body;

    public ModelParachute() {
        this.body = new Cube(0, 0);
        this.body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
        Cube height = this.body;
        height.y += (float)(-12);
    }

    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.body.render(scale);
    }

}
