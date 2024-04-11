package bta.aether.entity.renderer;

import bta.aether.entity.EntityZephyr;
import bta.aether.entity.model.ModelZephyr;
import net.minecraft.client.render.entity.LivingRenderer;
import org.lwjgl.opengl.GL11;

public class ZephyrRenderer extends LivingRenderer<EntityZephyr> {
    public ZephyrRenderer() {
        super(new ModelZephyr(), 0.5F);
    }
    protected void func_4014_a(EntityZephyr entityzephyr, float f) {
        float f1 = ((float) entityzephyr.prevAttackCounter + (float) (entityzephyr.attackCounter - entityzephyr.prevAttackCounter) * f) / 20.0F;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        f1 = 1.0F / (f1 * f1 * f1 * f1 * f1 * 2.0F + 1.0F);
        float f2 = (8.0F + f1) / 2.0F;
        float f3 = (8.0F + 1.0F / f1) / 2.0F;
        GL11.glScalef(f3, f2, f3);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void doRenderPreview(EntityZephyr entity, double x, double y, double z, float yaw, float partialTick) {
        GL11.glScalef(6.0F, 6.0F, 6.0F);
    }

    protected void preRenderCallback(EntityZephyr entityliving, float f) {
        this.func_4014_a(entityliving, f);
    }
}
