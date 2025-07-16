package teamport.aether.entity.sheepuff;

import net.minecraft.client.render.LightmapHelper;
import net.minecraft.core.Global;
import net.minecraft.core.entity.Mob;
import org.lwjgl.opengl.GL11;

public class ModelSheepuffOverlay extends ModelSheepuff {
    Mob sheepuff;

    public ModelSheepuffOverlay() {
    }

    public void setLivingAnimations(Mob mob, float limbSwing, float limbYaw, float partialTick) {
        super.setLivingAnimations(mob, limbSwing, limbYaw, partialTick);
        this.sheepuff = mob;
        float brightness = this.sheepuff.getBrightness(limbSwing);
        if (Global.accessor.isFullbrightEnabled() || LightmapHelper.isLightmapEnabled()) {
            brightness = 1.0F;
        }

        int j = ((MobSheepuff)this.sheepuff).getFleeceColor().blockMeta;
        GL11.glColor3f(brightness * MobSheepuff.FLEECE_COLOR_TABLE[j][0], brightness * MobSheepuff.FLEECE_COLOR_TABLE[j][1], brightness * MobSheepuff.FLEECE_COLOR_TABLE[j][2]);
    }

    public void onUnload() {
        this.sheepuff = null;
    }

    public void render(float limbSwing, float limbYaw, float ticksExisted, float headYaw, float headPitch, float scale) {
        super.render(limbSwing, limbYaw, ticksExisted, headYaw, headPitch, scale);
    }
}
