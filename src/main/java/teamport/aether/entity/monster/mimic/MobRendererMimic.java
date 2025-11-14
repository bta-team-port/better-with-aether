package teamport.aether.entity.monster.mimic;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class MobRendererMimic extends MobRenderer<MobMimic> {
    public MobRendererMimic() {
        super(new ModelMimic(), 0.7F);
    }

    @Override
    public void setupScale(MobMimic entity, float partialTick) {
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
}
