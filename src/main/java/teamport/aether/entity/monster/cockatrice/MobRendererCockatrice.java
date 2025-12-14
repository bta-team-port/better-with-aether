package teamport.aether.entity.monster.cockatrice;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererCockatrice extends MobRenderer<MobCockatrice> {

    public MobRendererCockatrice(float shadowSize) {
        super(shadowSize);
    }

    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobCockatrice entity, float brightness, float partialTick, int layer) {
        if (layer == 1) {
            this.bindTexture("/assets/aether/textures/entity/cockatrice/glow/" + entity.getTextureReference() + ".png");
            if (LightmapHelper.isLightmapEnabled()) {
                LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(15, 15));
            }
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, (1.0F - entity.getBrightness(partialTick)) * 0.5F);
        }

        StaticEntityModel model = this.getModel("main");
        model.resetBones();
        float limbSwing = this.getLimbSwing(entity, partialTick);
        float limbYaw = this.getLimbYaw(entity, partialTick);
        float limbPitch = this.getLimbPitch(entity, partialTick);
        float bodyYaw = this.getBodyYaw(entity, partialTick);
        float headYaw = this.getHeadYaw(entity, partialTick) - bodyYaw;
        float headPitch = this.getHeadPitch(entity, partialTick);

        BoneTransform head = model.getTransform("head");
        head.rotX = headPitch;
        head.rotY = headYaw;
        BoneTransform neck = model.getTransform("neck");
        neck.rotY = headYaw;

        BoneTransform leg0 = model.getTransform("leg0");
        BoneTransform leg1 = model.getTransform("leg1");
        leg0.rotX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw;
        leg1.rotX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbYaw;

        BoneTransform wing0 = model.getTransform("wing0");
        BoneTransform wing1 = model.getTransform("wing1");

        if (limbPitch <= 0.0000000001F) {
            wing0.rotX = (float) Math.PI / 2F;
            wing1.rotX = (float) Math.PI / 2F;
            wing0.posZ -= 8;
            wing1.posZ -= 8;
        } else {
            wing0.rotX = 0;
            wing1.rotX = 0;
            wing0.rotZ = limbPitch;
            wing1.rotZ = -limbPitch;

            leg0.rotX = 0.6F;
            leg1.rotX = 0.6F;
        }

        return model;
    }

    @Override
    protected int maxRenderLayer(@NotNull MobCockatrice entity) {
        return 1;
    }

    @Override
    protected float getLimbPitch(@NonNull MobCockatrice entity, float partialTick) {
        float flap = MathHelper.lerp(entity.getOFlap(), entity.getFlap(), partialTick);
        float flapSpeed = MathHelper.lerp(entity.getOFlapSpeed(), entity.getFlapSpeed(), partialTick);
        return (MathHelper.sin(flap) + 1.0F) * flapSpeed;
    }

}
