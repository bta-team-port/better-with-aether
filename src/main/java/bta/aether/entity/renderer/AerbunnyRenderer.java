package bta.aether.entity.renderer;

import bta.aether.entity.EntityAerbunny;
import bta.aether.entity.model.ModelAerbunny;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.EntityLiving;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class AerbunnyRenderer extends LivingRenderer<EntityAerbunny> {
    public ModelAerbunny mb;
    public AerbunnyRenderer(ModelBase modelbase, float shadowSize) {
        super(modelbase, shadowSize);
        this.mb = (ModelAerbunny) modelbase;
    }

    protected void rotAerbunny(EntityAerbunny entitybunny) {
        if (!entitybunny.onGround && entitybunny.vehicle == null) {
            if (entitybunny.yd > 0.5) {
                GL11.glRotatef(15.0F, -1.0F, 0.0F, 0.0F);
            } else if (entitybunny.yd < -0.5) {
                GL11.glRotatef(-15.0F, -1.0F, 0.0F, 0.0F);
            } else {
                GL11.glRotatef((float) (entitybunny.yd * 30.0), -1.0F, 0.0F, 0.0F);
            }
        }

        this.mb.puffiness = entitybunny.puffiness;
    }

    protected void method_823(EntityLiving entityliving, float f) {
        this.rotAerbunny((EntityAerbunny) entityliving);
    }
}
