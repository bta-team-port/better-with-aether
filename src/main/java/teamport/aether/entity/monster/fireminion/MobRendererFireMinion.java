package teamport.aether.entity.monster.fireminion;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class MobRendererFireMinion extends MobRenderer<MobFireMinion> {
    public MobRendererFireMinion(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    public void renderPreview(Tessellator tessellator, MobFireMinion fireMinion, double x, double y, double z, float yaw, float partialTick) {
        GL11.glPushMatrix();
        super.renderPreview(tessellator, fireMinion, x, y - 1, z, yaw, partialTick);
        GL11.glPopMatrix();
    }

}

