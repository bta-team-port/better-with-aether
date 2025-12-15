package teamport.aether.entity.monster.whirly;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
        StaticEntityModel model = this.getModel("main");
        model.resetBones();

        float bodyYaw = this.getBodyYaw(whirly, partialTick);
        float headYaw = this.getHeadYaw(whirly, partialTick) - bodyYaw;
        float headPitch = this.getHeadPitch(whirly, partialTick);

        BoneTransform head = model.getTransform("head");
        head.rotY = headYaw;
        head.rotX = headPitch;

        if (layer == 1) {
            this.bindTexture("/assets/aether/textures/armor/wind.png");

            float ticks = whirly.tickCount + partialTick;

            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glLoadIdentity();
            float translateX = ticks * 0.01F;
            float translateY = ticks * 0.01F;
            GL11.glTranslatef(translateX, translateY, 0.0F);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            GL11.glColor4f(brightness * 0.8F, brightness * 0.9F, brightness, 1.0F);
        } else if (layer == 2) {
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);

            return null;
        }

        return model;
    }

    @Override
    protected int maxRenderLayer(@NonNull MobWhirly entity) {
        return 2;
    }
}
