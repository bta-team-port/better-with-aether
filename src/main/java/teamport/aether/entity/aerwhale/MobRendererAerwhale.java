package teamport.aether.entity.aerwhale;

import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class MobRendererAerwhale extends MobRenderer<MobAerwhale> {

    public MobRendererAerwhale(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    public void setupScale(MobAerwhale entity, float partialTick) {
        GL11.glScalef(10.0F, 10.0F, 10.0F);
    }

}
