package teamport.aether.entity.mimic;

import net.minecraft.client.render.entity.MobRenderer;
import org.lwjgl.opengl.GL11;

public class MobRendererMimic extends MobRenderer<MobMimic> {
    public MobRendererMimic() {
        super(new ModelMimic(), 1.0F);
    }

    protected void setupScale(MobMimic entity, float partialTick) {
        GL11.glScalef(2.0f , 2.0f, 2.0f);
    }

}
