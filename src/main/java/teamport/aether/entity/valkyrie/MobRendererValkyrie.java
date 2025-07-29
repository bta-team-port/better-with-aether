package teamport.aether.entity.valkyrie;

import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class MobRendererValkyrie extends MobRenderer<MobValkyrie> {
    public MobRendererValkyrie(ModelBase model, float shadowSize) {
        super(model, shadowSize);
        this.setArmorModel(new ModelValkyrie(0.01F));
    }

    protected boolean setHaloBrightness(MobValkyrie valkyrie, int renderPass) {
        if (renderPass == 0) {
            this.bindTexture("/assets/aether/textures/entity/valkyrie/halo.png");
            float brightness = valkyrie.getBrightness(1.0F);
            if (LightmapHelper.isLightmapEnabled()) {
                LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(15, 15));
            }

            float f1 = (1.0F - brightness) * 0.5F;
            GL11.glEnable(3042);
            GL11.glDisable(3008);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
            return true;
        } else {
            return false;
        }
    }

    protected boolean prepareArmor(MobValkyrie valkyrie, int renderPass, float partialTick) {
        return this.setHaloBrightness(valkyrie, renderPass);
    }

}
