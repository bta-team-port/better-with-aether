package teamport.aether.entity.vehicle.parachute;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;

@Environment(EnvType.CLIENT)
public class ModelParachute extends ModelBase {
    private final Cube body;

    public ModelParachute() {
        this.body = new Cube(0, 0);
        this.body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
        this.body.y -= 12.0F;
    }

    @Override
    public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
        this.body.render(scale);
    }

}
