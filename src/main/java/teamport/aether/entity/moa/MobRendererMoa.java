package teamport.aether.entity.moa;

import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;

public class MobRendererMoa extends MobRenderer<MobMoa> {
    public MobRendererMoa(ModelBase modelbase, float f) {
        super(modelbase, f);
        this.setArmorModel(modelbase);
    }

    public float limbSway(MobMoa entity, float partialTick) {
        float flap = entity.oFlap + (entity.flap - entity.oFlap) * partialTick;
        float flapSpeed = entity.oFlapSpeed + (entity.flapSpeed - entity.oFlapSpeed) * partialTick;
        return (MathHelper.sin(flap) + 1.0F) * flapSpeed;
    }

    public boolean renderSaddledMoa(MobMoa entity, int i, float f) {
        this.bindTexture("/assets/aether/textures/entity/moa/moa_saddle.png");
        return i == 0 && entity != null && entity.getSaddled();
    }

    public void setupScale(MobMoa entity, float partialTick) {
        GL11.glScalef(1.8f , 1.8f, 1.8f);
    }

    public boolean prepareArmor(MobMoa entity, int renderPass, float partialTick) {
        return this.renderSaddledMoa(entity, renderPass, partialTick);
    }
}
