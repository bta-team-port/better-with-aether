package teamport.aether.entity.monster.tempest;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;

@Environment(EnvType.CLIENT)
public class MobRendererTempest extends MobRenderer<MobTempest> {
    public MobRendererTempest(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobTempest tempest, float brightness, float partialTick, int layer) {
        StaticEntityModel model;
        if (layer == 2) {
            this.bindTexture("/assets/aether/textures/entity/tempest/eyes/" + tempest.getTextureReference() + ".png");
            GLRenderer.setLightmapCoord2i(15, 15);
            GLRenderer.enableState(State.BLEND);
            GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
        } else if (layer == 1) {
            model = this.getModel("wind");
            model.resetBones();

            this.bindTexture("/assets/aether/textures/entity/tempest/wind.png");

            float time = tempest.tickCount + partialTick;
            float spinSpeed = 0.375F;
            float wobbleSpeed = 0.9F;
            float wobbleStrength = 0.12F;
            float wobble = MathHelper.sin(time * wobbleSpeed) * wobbleStrength;

            GLRenderer.enableState(State.BLEND);

            BoneTransform wind = model.getTransform("wind");
            wind.rotY = time * spinSpeed;
            wind.rotX = wobble;

            BoneTransform wind2 = model.getTransform("wind2");
            wind2.rotY = wind.rotY;
            wind2.rotX = wobble * 0.35F;

            BoneTransform wind3 = model.getTransform("wind3");
            wind3.rotY = wind2.rotY;
            wind3.rotX = wobble * 0.35F;

            BoneTransform wind4 = model.getTransform("wind4");
            wind4.rotY = wind3.rotY;
            wind4.rotX = wobble * 0.35F;

            BoneTransform wind5 = model.getTransform("wind5");
            wind5.rotY = wind3.rotY;
            wind5.rotX = wobble * 0.35F;

            return model;
        }
        model = this.getModel("main");
        model.resetBones();

        float bodyYaw = this.getBodyYaw(tempest, partialTick);
        float headYaw = this.getHeadYaw(tempest, partialTick) - bodyYaw;
        float headPitch = this.getHeadPitch(tempest, partialTick);
        BoneTransform head = model.getTransform("head");
        head.rotY = headYaw;
        head.rotX = headPitch;

        return model;
    }

    @Override
    protected int maxRenderLayer(@NonNull MobTempest entity) {
        return 2;
    }
}
