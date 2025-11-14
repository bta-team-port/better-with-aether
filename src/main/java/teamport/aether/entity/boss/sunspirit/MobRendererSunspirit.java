package teamport.aether.entity.boss.sunspirit;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRendererBiped;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class MobRendererSunspirit extends MobRendererBiped<MobBossSunspirit> {
    public MobRendererSunspirit() {
        super(new ModelSunspirit(), 0.8f);
    }

    @Override
    public void renderPreview(Tessellator tessellator, MobBossSunspirit sunspirit, double x, double y, double z, float yaw, float partialTick) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        super.renderPreview(tessellator, sunspirit, x, y + 1, z, yaw, partialTick);
        GL11.glPopMatrix();
    }
}
