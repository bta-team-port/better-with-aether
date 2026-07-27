package teamport.aether.entity.monster.tempest;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import net.minecraft.client.render.entity.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererTempest extends MobRenderer<MobTempest> {
    public MobRendererTempest(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobTempest tempest, float brightness, float partialTick, int layer) {
        StaticEntityModel model;
        if (layer == 1) {
            model = this.getModel("wind");
            model.resetBones();
        } else if (layer == 0) {
            model = this.getModel("main");
            model.resetBones();
        } else {
            model = null;
        }

        if (layer == 1) {
            this.bindTexture("/assets/aether/textures/entity/tempest/wind.png");

            float time = tempest.tickCount + partialTick;
            float scroll = time * 0.1F;
            float spinSpeed = 0.375F;
            float wobbleSpeed = 0.9F;
            float wobbleStrength = 0.12F;
            float wobble = MathHelper.sin(time * wobbleSpeed) * wobbleStrength;

            GLRenderer.textureM4f().identity().translate(-scroll, 0.0F, 0.0F);
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

        } else {
            float bodyYaw = this.getBodyYaw(tempest, partialTick);
            float headYaw = this.getHeadYaw(tempest, partialTick) - bodyYaw;
            float headPitch = this.getHeadPitch(tempest, partialTick);
            BoneTransform head = model.getTransform("head");
            head.rotY = headYaw;
            head.rotX = headPitch;
        }

        return model;
    }

    @Override
    protected int maxRenderLayer(@NonNull MobTempest entity) {
        return 1;
    }
}
