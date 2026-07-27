package teamport.aether.entity.animal.aerwhale;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import net.minecraft.client.render.entity.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererAerwhale extends MobRenderer<MobAerwhale> {

    public MobRendererAerwhale(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobAerwhale entity, float brightness, float partialTick, int layer) {
        StaticEntityModel model = this.getModel("main");

        model.resetBones();

        BoneTransform head = model.getTransform("head");
        head.rotY = 0.0F;
        head.rotX = -entity.getRenderPitch(partialTick) * MathHelper.DEG_TO_RAD;

        head.scaleX = 5.0f;
        head.scaleY = 5.0f;
        head.scaleZ = 5.0f;

        return model;
    }

    @Override
    protected float getBodyYaw(MobAerwhale entity, float partialTick) {
        return entity.getRenderYaw(partialTick) * MathHelper.DEG_TO_RAD;
    }

    @Override
    public void renderPreview(@NonNull TessellatorGeneral tessellator, @NonNull MobAerwhale aerwhale, double x, double y, double z, float yaw, float partialTick) {
        GLRenderer.pushFrame();
        GLRenderer.modelM4f().scale(0.1F, 0.1F, 0.1F);
        super.renderPreview(tessellator, aerwhale, x - 2, y + 10, z, yaw, partialTick);
        GLRenderer.popFrame();
    }

}
