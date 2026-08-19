package teamport.aether.entity.animal.aerwhale;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;

@Environment(EnvType.CLIENT)
public class MobRendererAerwhale extends MobRenderer<MobAerwhale> {

    public MobRendererAerwhale(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected void preRenderTransform(@NonNull MobAerwhale entity, double x, double y, double z, float yaw, float partialTick) {
        super.preRenderTransform(entity, x, y, z, yaw, partialTick);

        float scale = 5.0f * entity.getAerwhaleScale();
        GLRenderer.modelM4f().scale(scale, scale, scale);

        GLRenderer.modelM4f().rotateX(-entity.getRenderPitch(partialTick) * MathHelper.DEG_TO_RAD);

    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobAerwhale entity, float brightness, float partialTick, int layer) {
        StaticEntityModel model = this.getModel("main");
        model.resetBones();

        BoneTransform head = model.getTransform("head");
        BoneTransform pleatHeadLeft = model.getTransform("pleatHeadLeft");
        BoneTransform pleatHeadRight = model.getTransform("pleatHeadRight");

        BoneTransform body = model.getTransform("body");

        BoneTransform tail = model.getTransform("tail");
        BoneTransform pleatTailLeft = model.getTransform("pleatTailLeft");
        BoneTransform pleatTailRight = model.getTransform("pleatTailRight");

        float time = (entity.tickCount + partialTick) * 0.06F;
        float bodyYaw = this.getBodyYaw(entity, partialTick);
        float headYaw = this.getHeadYaw(entity, partialTick) - bodyYaw;
        float headPitch = this.getHeadPitch(entity, partialTick);
        float limbYaw = this.getLimbYaw(entity, partialTick);

        float amplitudeMultiplier = (1.0F + limbYaw * 0.2F) * 0.1F;

        float headWave = MathHelper.sin(time) * amplitudeMultiplier;
        float bodyWave = MathHelper.sin(time - 0.7F) * amplitudeMultiplier * 1.5F;
        float tailWave = MathHelper.sin(time - 1.4F) * amplitudeMultiplier * 2.8F;

        head.rotX = headPitch + headWave;
        head.rotY = headYaw;

        pleatHeadLeft.rotZ = -bodyWave;
        pleatHeadRight.rotZ = bodyWave;

        body.rotX = bodyWave;

        tail.rotX = tailWave;

        pleatTailLeft.rotZ = -tailWave;
        pleatTailRight.rotZ = tailWave;


        return model;
    }

    @Override
    protected float getBodyYaw(@NonNull MobAerwhale entity, float partialTick) {
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
