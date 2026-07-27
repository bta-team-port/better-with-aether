package teamport.aether.entity.vehicle.parachute;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;

@Environment(EnvType.CLIENT)
public class EntityRendererParachute extends EntityRenderer<EntityParachute> {
    public EntityRendererParachute() {
        super(0.0F);
    }

    public void render(TessellatorGeneral tessellator, EntityParachute entity, double x, double y, double z, float yaw, float partialTick) {
        GLRenderer.pushFrame();
        GLRenderer.modelM4f().translate((float) x, (float) y, (float) z);

        float f4 = 0.75F;
        GLRenderer.modelM4f().scale(f4, f4, f4);
        GLRenderer.modelM4f().scale(1.0F / f4, 1.0F / f4, 1.0F / f4);
        this.bindTexture("/assets/aether/textures/entity/parachute.png");

        GLRenderer.enableState(State.DEPTH_TEST);
        GLRenderer.enableState(State.BLEND);
        GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
        GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, .75F);

        GLRenderer.modelM4f().scale(-1.0F, -1.0F, 1.0F);
        ParachuteGeometry.render(tessellator);
        GLRenderer.popFrame();
    }
}
