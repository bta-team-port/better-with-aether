package teamport.aether.entity.vehicle.parachute;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import org.joml.Math;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class EntityRendererParachute extends EntityRenderer<EntityParachute> {
    public EntityRendererParachute() {
        super(0.0F);
    }

    @Override
    public void render(@NonNull TessellatorGeneral tessellator, @NonNull EntityParachute entity, double x, double y, double z, float yaw, float partialTick) {
        GLRenderer.pushFrame();
        GLRenderer.modelM4f().translate((float) x, (float) y, (float) z);
        GLRenderer.modelM4f().rotateY(Math.toRadians(180.0F - yaw));

        GLRenderer.enableState(State.BLEND);
        GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);

        TextureRegistry.worldAtlas.bind();
        GLRenderer.modelM4f().scale(0.75F, 0.75F, 0.75F);
        GLRenderer.modelM4f().scale(1.3333334F, 1.3333334F, 1.3333334F);

        this.bindTexture(entity.getEntityTexture());

        GLRenderer.modelM4f().scale(0.0625F, 0.0625F, -0.0625F);
        this.getModel("main").render();
        GLRenderer.popFrame();
    }
}
