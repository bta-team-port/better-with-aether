package teamport.aether.entity.monster.whirly;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
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
//        if (layer == 1) {
//            model = this.getModel("wind");
//            this.bindTexture(whirly.getEvil() ? "/assets/aether/textures/armor/wind_evil.png" : "/assets/aether/textures/armor/wind.png");
//            float ticks = whirly.tickCount + partialTick;
//
//            GL11.glMatrixMode(GL11.GL_TEXTURE);
//            GL11.glLoadIdentity();
//            float translateX = ticks * 0.01F;
//            float translateY = ticks * 0.01F;
//            GL11.glTranslatef(translateX, translateY, 0.0F);
//            GL11.glMatrixMode(GL11.GL_MODELVIEW);
//            GL11.glEnable(3042);
//            GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
//            GL11.glDisable(2896);
//            GL11.glBlendFunc(1, 1);
//
//        } else {
//            if (layer == 2) {
//                GL11.glMatrixMode(5890);
//                GL11.glLoadIdentity();
//                GL11.glMatrixMode(5888);
//                GL11.glEnable(2896);
//                GL11.glDisable(3042);
//            }

        model = this.getModel("main");
//        }

        model.resetBones();

        float bodyYaw = this.getBodyYaw(whirly, partialTick);
        float headYaw = this.getHeadYaw(whirly, partialTick) - bodyYaw;
        float headPitch = this.getHeadPitch(whirly, partialTick);

        BoneTransform head = model.getTransform("head");
        head.rotY = headYaw;
        head.rotX = headPitch;

        return model;
    }

    @Override
    protected int maxRenderLayer(@NonNull MobWhirly entity) {
        return 2;
    }
}
