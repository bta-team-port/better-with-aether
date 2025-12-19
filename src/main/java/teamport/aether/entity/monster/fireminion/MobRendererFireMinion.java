package teamport.aether.entity.monster.fireminion;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;
import teamport.aether.entity.monster.tempest.MobTempest;

@Environment(EnvType.CLIENT)
public class MobRendererFireMinion extends MobRenderer<MobFireMinion> {
    public MobRendererFireMinion(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobFireMinion whirly, float brightness, float partialTick, int layer) {
        StaticEntityModel model;
        if (layer == 1) {
            model = this.getModel("fire");
            this.bindTexture("/assets/aether/textures/entity/fire_minion/fire.png");
            float ticks = whirly.tickCount + partialTick;
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glLoadIdentity();
            float offsetX = ticks * 0.01F;
            float offsetY = ticks * 0.01F;
            GL11.glTranslatef(offsetX, offsetY, 0.0F);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        } else if (layer == 2) {
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            return null;
        } else {
            model = this.getModel("main");
        }

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
    protected int maxRenderLayer(@NonNull MobFireMinion entity) {
        return 2;
    }
}

