package teamport.aether.entity.animal.sheepuff;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.core.Global;
import net.minecraft.core.entity.Mob;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class ModelSheepuffOverlay extends ModelSheepuff {
    public ModelSheepuffOverlay() {}

    @Override
    public void setLivingAnimations(Mob mob, float limbSwing, float limbYaw, float partialTick) {
        super.setLivingAnimations(mob, limbSwing, limbYaw, partialTick);
        MobSheepuff sheepuff = (MobSheepuff) mob;
        float brightness = sheepuff.getBrightness(limbSwing);
        if (Global.accessor.isFullbrightEnabled() || LightmapHelper.isLightmapEnabled()) {
            brightness = 1.0F;
        }

        int j = sheepuff.getFleeceColor().blockMeta;
        GL11.glColor3f(brightness * MobSheepuff.FLEECE_COLOR_TABLE[j][0], brightness * MobSheepuff.FLEECE_COLOR_TABLE[j][1], brightness * MobSheepuff.FLEECE_COLOR_TABLE[j][2]);
    }
}
