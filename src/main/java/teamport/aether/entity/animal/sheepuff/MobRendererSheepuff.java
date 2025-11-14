package teamport.aether.entity.animal.sheepuff;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.Global;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class MobRendererSheepuff extends MobRenderer<MobSheepuff> {
    private final ModelBase wool;
    private final ModelBase puffed;

    public MobRendererSheepuff(ModelBase body, ModelBase wool, ModelBase puffed, ModelBase overlay, float f) {
        super(body, f);
        this.setArmorModel(body);
        this.wool = wool;
        this.puffed = puffed;
        this.setOverlayModel(overlay, "/assets/aether/textures/entity/sheepuff/sheepuff_overlay.png");
    }

    public boolean setWoolColorAndRender(MobSheepuff entitysheep, int i, float f) {
        if (i == 0 && !entitysheep.getSheared()) {
            if (entitysheep.getPuffed()) {
                this.setArmorModel(this.puffed);
                this.bindTexture("/assets/aether/textures/entity/sheepuff/sheepuff_fur.png");
            } else {
                this.setArmorModel(this.wool);
                this.bindTexture("/assets/aether/textures/entity/sheepuff/sheepuff_fur.png");
            }

            float brightness = 1.0F;
            if (!LightmapHelper.isLightmapEnabled() && !Global.accessor.isFullbrightEnabled()) {
                brightness = entitysheep.getBrightness(f);
            }

            int j = entitysheep.getFleeceColor().blockMeta;
            GL11.glColor3f(brightness * MobSheepuff.FLEECE_COLOR_TABLE[j][0], brightness * MobSheepuff.FLEECE_COLOR_TABLE[j][1], brightness * MobSheepuff.FLEECE_COLOR_TABLE[j][2]);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onUnload() {
        this.overlayModel.onUnload();
    }

    @Override
    public boolean prepareArmor(MobSheepuff entity, int renderPass, float partialTick) {
        return this.setWoolColorAndRender(entity, renderPass, partialTick);
    }
}
