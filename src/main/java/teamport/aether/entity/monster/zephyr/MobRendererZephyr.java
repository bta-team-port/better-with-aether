package teamport.aether.entity.monster.zephyr;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

@Environment(EnvType.CLIENT)
public class MobRendererZephyr extends MobRenderer<MobZephyr> {
    public MobRendererZephyr(float shadowSize) {
        super(shadowSize);
    }

    @Override
    protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobZephyr entity, float brightness, float partialTick, int layer) {
        StaticEntityModel model = this.getModel("main");
        model.resetBones();
        BoneTransform body = model.getTransform("body");
        float charge = MathHelper.lerp(entity.getAttackChargeO(), entity.getAttackCharge(), partialTick) / 20.0F;
        if (charge < 0.0F) {
            charge = 0.0F;
        }

        charge = 1.0F / (charge * charge * charge * charge * charge * 2.0F + 1.0F);
        float sHeight = (8.0F + charge) / 2.0F;
        float sWidth = (8.0F + 1.0F / charge) / 2.0F;
        body.scaleX = sWidth;
        body.scaleY = sHeight;
        body.scaleZ = sWidth;
        return model;
    }

    @Override
    public void renderPreview(@NonNull Tessellator tessellator, @NonNull MobZephyr mobZephyr, double x, double y, double z, float yaw, float partialTick) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        GL11.glScalef(0.25F, 0.25F, 0.25F);
        super.renderPreview(tessellator, mobZephyr, x, y, z, yaw, partialTick);
        GL11.glPopMatrix();
    }
}
