package teamport.aether.entity.animal.moa;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class MobRendererMoa extends MobRenderer<MobMoaBlue> {

    public MobRendererMoa(ModelBase modelbase, float shadowSize) {
        super(modelbase, shadowSize);
        this.setArmorModel(modelbase);
    }

    public float limbSway(MobMoaBlue entity, float partialTick) {
        float flap = entity.oFlap + (entity.flap - entity.oFlap) * partialTick;
        float flapSpeed = entity.oFlapSpeed + (entity.flapSpeed - entity.oFlapSpeed) * partialTick;
        return (MathHelper.sin(flap) + 1.0F) * flapSpeed;
    }

    public boolean renderSaddledMoa(MobMoaBlue entity, int i, float f) {
        this.bindTexture("/assets/aether/textures/entity/moa_blue/moa_saddle.png");
        return i == 0 && entity != null && entity.getSaddled();
    }

    public void setupScale(MobMoaBlue entity, float partialTick) {
        GL11.glScalef(1.8f , 1.8f, 1.8f);
    }

    public boolean prepareArmor(MobMoaBlue entity, int renderPass, float partialTick) {
        return this.renderSaddledMoa(entity, renderPass, partialTick);
    }
}
