package teamport.aether.entity.animal.phow;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.render.entity.MobRendererQuadruped;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.block.Blocks;
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

@Environment(EnvType.CLIENT)
public class MobRendererPhow extends MobRendererQuadruped<MobPhow> {

    @Override
    protected void renderSpecials(@NonNull TessellatorGeneral tessellator, @NonNull MobPhow entity, double x, double y, double z) {
        if (entity.passenger == null) {
            super.renderSpecials(tessellator, entity, x, y, z);
        }
    }

    public MobRendererPhow(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected float getRenderAlpha(@NonNull MobPhow entity, float partialTick) {
        long now = System.nanoTime();
        float dt = entity.lookFadeLastRenderNanos == 0L ? 0.0F : (float) (now - entity.lookFadeLastRenderNanos) / 1.0E9F;
        entity.lookFadeLastRenderNanos = now;
        dt = MathHelper.clamp(dt, 0.0F, 0.1F);
        if (!this.isRiderLookingAtOwnPig(entity, partialTick)) {
            entity.lookFadeAlpha = 1.0F;
            return 1.0F;
        } else {
            float t = 1.0F - (float) Math.exp(-16.0F * dt);
            entity.lookFadeAlpha += (0.2F - entity.lookFadeAlpha) * t;
            if (Math.abs(entity.lookFadeAlpha - 0.2F) < 0.01F) {
                entity.lookFadeAlpha = 0.2F;
            }

            return entity.lookFadeAlpha;
        }
    }

    @Override
    public float getShadowSize(@NonNull MobPhow entity) {
        return entity.lookFadeAlpha < 0.9F ? 0.0F : super.getShadowSize(entity);
    }

    private boolean isRiderLookingAtOwnPig(@NonNull MobPhow entity, float partialTick) {
        if (GameSettings.THIRD_PERSON_VIEW.value != 0) {
            return false;
        } else {
            Minecraft mc = Minecraft.getMinecraft();
            Player player = mc.thePlayer;
            if (player != null && entity == player.vehicle) {
                ItemStack held = player.inventory.getCurrentItem();
                if (held == null || held.itemID != Blocks.MUSHROOM_BROWN.id() && held.itemID != Blocks.MUSHROOM_RED.id()) {
                    Vector3dc eye = player.getPosition(partialTick, true);
                    Vector3dc look = player.getViewVector(partialTick);
                    if (look == null) {
                        return false;
                    } else {
                        double reach = (double) player.getGamemode().getEntityReachDistance() + (double) 1.0F;
                        Vector3d end = (new Vector3d(look)).mul(reach).add(eye);
                        double offX = (entity.xo - entity.x) * ((double) 1.0F - (double) partialTick);
                        double offY = (entity.yo - entity.y) * ((double) 1.0F - (double) partialTick);
                        double offZ = (entity.zo - entity.z) * ((double) 1.0F - (double) partialTick);
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
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobPhow entity, float brightness, float partialTick, int layer) {
        boolean sitting = entity.getSitting();
        StaticEntityModel model;
        if (layer == 1) {
            this.bindTexture("/assets/aether/textures/entity/phow/saddle.png");
            model = this.getModel("saddle");
        } else {
            model = this.getModel("main");
        }

        model.resetBones();

        BoneTransform body = model.getTransform("body");
        BoneTransform head = model.getTransform("head");
        BoneTransform leg0 = model.getTransform("leg0");
        BoneTransform leg1 = model.getTransform("leg1");
        BoneTransform leg2 = model.getTransform("leg2");
        BoneTransform leg3 = model.getTransform("leg3");
        BoneTransform wingLeftInner = model.getTransform("wingLeftInner");
        BoneTransform wingLeftOuter = model.getTransform("wingLeftOuter");
        BoneTransform wingRightInner = model.getTransform("wingRightInner");
        BoneTransform wingRightOuter = model.getTransform("wingRightOuter");

        float bodyYaw = this.getBodyYaw(entity, partialTick);
        float headYaw = this.getHeadYaw(entity, partialTick) - bodyYaw;
        float headPitch = this.getHeadPitch(entity, partialTick);
        float limbSwing = this.getLimbSwing(entity, partialTick);
        float limbYaw = this.getLimbYaw(entity, partialTick);

        float wingFold = MathHelper.lerp(entity.getWingFoldO(), entity.getWingFold(), partialTick);
        float wingAngle = MathHelper.lerp(entity.getWingAngleO(), entity.getWingAngle(), partialTick);

        float wingBend = -((float) Math.acos(wingFold));
        float baseRot = (float) Math.toRadians(90);

        if (sitting && entity.onGround) {
            body.rotX = (double) -15.5F * (double) MathHelper.DEG_TO_RAD;
            body.posY = -10.0F;
            body.posZ = 1.0F;

            wingLeftInner.posY = -6.0F;
            wingLeftInner.posZ = 2.0F;
            wingLeftInner.rotX = -0.3F;
            wingRightInner.posY = -6.0F;
            wingRightInner.posZ = 2.0F;
            wingRightInner.rotX = -0.3F;

            wingLeftOuter.rotZ = -2.0F * wingBend;
            wingRightOuter.rotZ = 2.0F * wingBend;

            leg0.rotX = -90.0F * MathHelper.DEG_TO_RAD;
            leg0.rotY = 15.0F * MathHelper.DEG_TO_RAD;
            leg0.posX = -1.0F;
            leg0.posY = -10.0F;
            leg1.rotX = -90.0F * MathHelper.DEG_TO_RAD;
            leg1.rotY = -15.0F * MathHelper.DEG_TO_RAD;
            leg1.posX = 1.0F;
            leg1.posY = -10.0F;

            leg2.rotX = 90.0F * MathHelper.DEG_TO_RAD;
            leg2.rotY = 15.0F * MathHelper.DEG_TO_RAD;
            leg2.posX = -1.0F;
            leg2.posY = -11.0F;
            leg2.posZ = -5.0F;

            leg3.rotX = 90.0F * MathHelper.DEG_TO_RAD;
            leg3.rotY = -15.0F * MathHelper.DEG_TO_RAD;
            leg3.posX = 1.0F;
            leg3.posY = -11.0F;
            leg3.posZ = -5.0F;

            head.posY = -7.0F;
            head.posZ = 1.0F;
            head.rotX += headPitch;
            head.rotY += headYaw;
        } else {
            head.rotX = headPitch;
            head.rotY = headYaw;
            leg0.rotX = (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw);
            leg1.rotX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbYaw);
            leg2.rotX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbYaw);
            leg3.rotX = (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw);
        }
        wingLeftInner.rotZ = wingAngle + wingBend + baseRot;
        wingRightInner.rotZ = -(wingAngle + wingBend) - baseRot;

        wingLeftOuter.rotZ = -2.0F * wingBend;
        wingRightOuter.rotZ = 2.0F * wingBend;

        return model;
    }

    @Override
    protected int maxRenderLayer(@NonNull MobPhow entity) {
        return entity.getSaddled() ? 1 : 0;
    }
}
