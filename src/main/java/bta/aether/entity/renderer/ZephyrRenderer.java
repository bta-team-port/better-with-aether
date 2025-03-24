package bta.aether.entity.renderer;

import bta.aether.entity.EntityZephyr;
import bta.aether.entity.model.ModelZephyr;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.LivingRenderer;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class ZephyrRenderer extends LivingRenderer<EntityZephyr> {
    public ZephyrRenderer() {
        super(new ModelZephyr(), 4.0f);
    }

    protected void puff(EntityZephyr entityzephyr, float f) {
        float counter = ((float) entityzephyr.prevAttackCounter + (float) (entityzephyr.attackCounter - entityzephyr.prevAttackCounter) * f) / 20.0F;
        if (counter < 0.0F) {
            counter = 0.0F;
        }

        counter = 1.0F / (counter * counter * counter * counter * counter * 2.0F + 1.0F);
        float scaleY = (8.0F + counter) / 2.0F;
        float scaleXZ = (8.0F + 1.0F / counter) / 2.0F;
        GL11.glScalef(scaleXZ, scaleY, scaleXZ);
    }

    public void doRenderPreview(EntityZephyr entity, double x, double y, double z, float yaw, float partialTick) {
        GL11.glScalef(6.0F, 6.0F, 6.0F);
    }

    protected void preRenderCallback(EntityZephyr entity, float f) {
        this.puff(entity, f);
    }
}
