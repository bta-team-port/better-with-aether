package teamport.aether.entity.animal.moa;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.primitives.AABBd;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import teamport.aether.item.AetherItemTags;

@Environment(EnvType.CLIENT)
public class MobRendererMoa extends MobRenderer<MobMoa> {
    private static final float LOOK_FADE_TARGET = 0.2F;
    private static final float LOOK_FADE_RATE = 16.0F;

    @Override
    protected void renderSpecials(@NonNull TessellatorGeneral tessellator, @NonNull MobMoa entity, double x, double y, double z) {
        if (entity.passenger == null) {
            super.renderSpecials(tessellator, entity, x, y, z);
        }
    }

    public MobRendererMoa(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected float getRenderAlpha(@NonNull MobMoa entity, float partialTick) {
        long now = System.nanoTime();
        float dt = entity.lookFadeLastRenderNanos == 0L ? 0.0F : (float)(now - entity.lookFadeLastRenderNanos) / 1.0E9F;
        entity.lookFadeLastRenderNanos = now;
        dt = MathHelper.clamp(dt, 0.0F, 0.1F);
        if (!this.isRiderLookingAtOwnPig(entity, partialTick)) {
            entity.lookFadeAlpha = 1.0F;
            return 1.0F;
        } else {
            float t = 1.0F - (float)Math.exp(-LOOK_FADE_RATE * dt);
            entity.lookFadeAlpha += (LOOK_FADE_TARGET - entity.lookFadeAlpha) * t;
            if (Math.abs(entity.lookFadeAlpha - LOOK_FADE_TARGET) < 0.01F) {
                entity.lookFadeAlpha = LOOK_FADE_TARGET;
            }

            return entity.lookFadeAlpha;
        }
    }

    @Override
    public float getShadowSize(@NonNull MobMoa entity) {
        return entity.lookFadeAlpha < 0.9F ? 0.0F : super.getShadowSize(entity);
    }

    private boolean isRiderLookingAtOwnPig(@NonNull MobMoa entity, float partialTick) {
        if (GameSettings.THIRD_PERSON_VIEW.value != 0) {
            return false;
        } else {
            Minecraft mc = Minecraft.getMinecraft();
            Player player = mc.thePlayer;
            if (player != null && entity == player.vehicle) {
                ItemStack held = player.inventory.getCurrentItem();
                if (held == null || held.getItem().hasTag(AetherItemTags.MOAS_FAVOURITE_ITEM)) {
                    Vector3dc eye = player.getPosition(partialTick, true);
                    Vector3dc look = player.getViewVector(partialTick);
                    if (look == null) {
                        return false;
                    } else {
                        double reach = (double)player.getGamemode().getEntityReachDistance() + (double)1.0F;
                        Vector3d end = (new Vector3d(look)).mul(reach).add(eye);
                        double offX = (entity.xo - entity.x) * ((double)1.0F - (double)partialTick);
                        double offY = (entity.yo - entity.y) * ((double)1.0F - (double)partialTick);
                        double offZ = (entity.zo - entity.z) * ((double)1.0F - (double)partialTick);
                        AABBd renderedBox = entity.bb.translate(offX, offY, offZ, new AABBd());
                        return MathHelper.aabbClip(renderedBox, eye, end) != null;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    @Override
    protected void preRenderTransform(@NonNull MobMoa entity, double x, double y, double z, float yaw, float partialTick) {
        super.preRenderTransform(entity, x, y, z, yaw, partialTick);
        GLRenderer.modelM4f().scale(0.85F, 0.85F, 0.85F);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobMoa entity, float brightness, float partialTick, int layer) {
        StaticEntityModel model;
        if (layer == 1) {
            this.bindTexture(entity.getSaddleTexturePath());
            model = this.getModel("saddle");
        } else {
            model = this.getModel("main");
        }

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
    protected float getLimbPitch(@NonNull MobMoa entity, float partialTick) {
        float flap = MathHelper.lerp(entity.getOFlap(), entity.getFlap(), partialTick);
        float flapSpeed = MathHelper.lerp(entity.getOFlapSpeed(), entity.getFlapSpeed(), partialTick);
        return (MathHelper.sin(flap) + 1.0F) * flapSpeed;
    }

    @Override
    protected int maxRenderLayer(@NonNull MobMoa entity) {
        return entity.getSaddled() ? 1 : 0;
    }

}
