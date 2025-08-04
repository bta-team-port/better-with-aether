package teamport.aether.entity.zephyr;

import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;

public class MobRendererZephyr extends MobRenderer<MobZephyr> {
    public MobRendererZephyr() {
        super(new ModelZephyr(), 0.5F);
    }

    public void renderPreview(Tessellator tessellator, MobZephyr mobZephyr, double x, double y, double z, float yaw, float partialTick) {
        GL11.glPushMatrix();
        GL11.glScalef(0.25F, 0.25F, 0.25F);
        super.renderPreview(tessellator, mobZephyr, x, y + 2.5, z, yaw, partialTick);
        GL11.glPopMatrix();
    }

    public void setupScale(MobZephyr mobZephyr, float partialTick) {
        float charge = ((float)mobZephyr.attackChargeO + (float)(mobZephyr.attackCharge - mobZephyr.attackChargeO) * partialTick) / 20.0F;
        if (charge < 0.0F) {
            charge = 0.0F;
        }

        charge = 1.0F / (charge * charge * charge * charge * charge * 2.0F + 1.0F);
        float f2 = (8.0F + charge) / 2.0F;
        float f3 = (8.0F + 1.0F / charge) / 2.0F;
        GL11.glScalef(f3, f2, f3);
    }

}
