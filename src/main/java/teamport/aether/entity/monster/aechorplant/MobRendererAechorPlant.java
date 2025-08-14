package teamport.aether.entity.monster.aechorplant;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.entity.MobRenderer;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class MobRendererAechorPlant extends MobRenderer<MobAechorPlant> {
    public ModelAechorPlant setArmorModel;

    public MobRendererAechorPlant(ModelAechorPlant modelBase, float shadowSize) {
        super(modelBase, shadowSize);
        this.setArmorModel(modelBase);
        this.setArmorModel = modelBase;
    }

    public void setupScale(MobAechorPlant aechorPlant, float partialTick) {
        float f1 = (float) Math.sin(aechorPlant.sinage);
        float f3;
        if (aechorPlant.hurtTime > 0) {
            f1 *= 0.45F;
            f1 -= 0.125F;
            f3 = 1.75F + (float) Math.sin(aechorPlant.sinage + 2.0F) * 1.5F;
        } else if (aechorPlant.hasTarget) {
            f1 *= 0.25F;
            f3 = 1.75F + (float) Math.sin(aechorPlant.sinage + 2.0F) * 1.5F;
        } else {
            f1 *= 0.125F;
            f3 = 1.75F;
        }

        this.setArmorModel.sinage = f1;
        this.setArmorModel.sinage2 = f3;
        float f2 = 0.625F + aechorPlant.footSize / 6.0F;
        this.setArmorModel.size = f2;
        this.shadowSize = f2 - 0.25F;
    }

    public boolean setGlow(MobAechorPlant aechorPlant, int renderPass) {
        if (renderPass == 0) {
            this.bindTexture("/assets/aether/textures/entity/aechorplant/glow.png");
            if (LightmapHelper.isLightmapEnabled()) {
                LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(15, 15));
            }

            GL11.glEnable(3042);
            GL11.glDisable(3008);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 15.0f);
            return true;
        } else {
            return false;
        }
    }

    public boolean bindTexture(MobAechorPlant aechorPlant, int i, float f) {
        return this.setGlow(aechorPlant, i);
    }
}