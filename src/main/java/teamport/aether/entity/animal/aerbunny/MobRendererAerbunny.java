package teamport.aether.entity.animal.aerbunny;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class MobRendererAerbunny extends MobRenderer<MobAerbunny> {
    private final ModelAerbunny model;

    public MobRendererAerbunny(ModelBase model, float shadowSize) {
        super(model, shadowSize);
        this.model = (ModelAerbunny) model;
    }

    public void rotateAerbunny(MobAerbunny entity) {
        if (!entity.onGround && entity.vehicle == null) {
            if (entity.yd > 0.5) {
                GL11.glRotatef(15.0F, -1.0F, 0.0F, 0.0F);
            } else if (entity.yd < -0.5) {
                GL11.glRotatef(-15.0F, -1.0F, 0.0F, 0.0F);
            } else {
                GL11.glRotatef((float) (entity.yd * 30.0), -1.0F, 0.0F, 0.0F);
            }
        }

        this.model.setPuffiness(entity.getPuffiness());
    }

    @Override
    public void setupScale(MobAerbunny entity, float partialTick) {
        this.rotateAerbunny(entity);
    }

}
