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

    @Override
    public float limbSway(MobMoaBlue moa, float partialTick) {
        float flap = moa.getOFlap() + (moa.getFlap() - moa.getOFlap()) * partialTick;
        float flapSpeed = moa.getOFlapSpeed() + (moa.getFlapSpeed() - moa.getOFlapSpeed()) * partialTick;
        return (MathHelper.sin(flap) + 1.0F) * flapSpeed;
    }

    private boolean renderSaddledMoa(MobMoaBlue moa, int renderPass) {
        this.bindTexture("/assets/aether/textures/entity/moa_blue/moa_saddle.png");
        return renderPass == 0 && moa != null && moa.getSaddled();
    }

    @Override
    public void setupScale(MobMoaBlue moa, float partialTick) {
        GL11.glScalef(1.8f, 1.8f, 1.8f);
    }

    @Override
    public boolean prepareArmor(MobMoaBlue moa, int renderPass, float partialTick) {
        return this.renderSaddledMoa(moa, renderPass);
    }
}
