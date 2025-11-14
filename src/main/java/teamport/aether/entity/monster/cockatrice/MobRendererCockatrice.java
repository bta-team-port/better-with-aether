package teamport.aether.entity.monster.cockatrice;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class MobRendererCockatrice extends MobRenderer<MobCockatrice> {
    public MobRendererCockatrice(ModelBase modelbase, float shadowSize) {
        super(modelbase, shadowSize);
        this.setArmorModel(modelbase);
    }

    @Override
    public float limbSway(MobCockatrice entity, float partialTick) {
        float flap = entity.getOFlap() + (entity.getFlap() - entity.getOFlap()) * partialTick;
        float flapSpeed = entity.getOFlapSpeed() + (entity.getFlapSpeed() - entity.getOFlapSpeed()) * partialTick;
        return (MathHelper.sin(flap) + 1.0F) * flapSpeed;
    }

    private boolean setCockatriceGlowBrightness(MobCockatrice entity, int renderPass) {
        if (renderPass == 0) {
            this.bindTexture("/assets/aether/textures/entity/cockatrice/glow/" + entity.getTextureReference() + ".png");
            float brightness = entity.getBrightness(1.0F);
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

    @Override
    public void setupScale(MobCockatrice entity, float partialTick) {
        GL11.glScalef(1.8f, 1.8f, 1.8f);
    }

    @Override
    public boolean prepareArmor(MobCockatrice entity, int renderPass, float partialTick) {
        return this.setCockatriceGlowBrightness(entity, renderPass);
    }
}
