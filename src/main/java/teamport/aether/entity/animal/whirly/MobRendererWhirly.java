package teamport.aether.entity.animal.whirly;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererWhirly extends MobRenderer<MobWhirly> {
    public MobRendererWhirly(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobWhirly whirly, float brightness, float partialTick, int layer) {
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
            this.bindTexture("/assets/aether/textures/entity/whirly/wind.png");

            float time = whirly.tickCount + partialTick;
            float scroll = time * 0.1F;
            float spinSpeed = 0.375F;
            float wobbleSpeed = 0.9F;
            float wobbleStrength = 0.12F;
            float wobble = MathHelper.sin(time * wobbleSpeed) * wobbleStrength;

            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(-scroll, 0.0F, 0.0F);

            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glEnable(GL11.GL_BLEND);

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

        } else if (layer == 2) {
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPopMatrix();
            GL11.glLoadIdentity();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glDisable(GL11.GL_BLEND);
        } else {
            float bodyYaw = this.getBodyYaw(whirly, partialTick);
            float headYaw = this.getHeadYaw(whirly, partialTick) - bodyYaw;
            float headPitch = this.getHeadPitch(whirly, partialTick);
            BoneTransform head = model.getTransform("head");
            head.rotY = headYaw;
            head.rotX = headPitch;
        }

        return model;
    }

    @Override
    protected int maxRenderLayer(@NonNull MobWhirly entity) {
        return 2;
    }
}
