package bta.aether.entity.renderer;

import bta.aether.entity.projectiles.EntityZephyrSnowball;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.core.Global;
import net.minecraft.core.item.Item;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class ZephyrSnowballRenderer extends EntityRenderer<EntityZephyrSnowball> {
    public ZephyrSnowballRenderer() {
    }

    public void renderSnowball(EntityZephyrSnowball entity, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glDisable(2896);
        GL11.glEnable(32826);
        float f2 = 2.0F;
        GL11.glScalef(f2, f2, f2);
        int i = Item.ammoSnowball.getIconFromDamage(0);
        this.loadTexture("/gui/items.png");
        Tessellator tessellator = Tessellator.instance;
        float f3 = (float)(i % Global.TEXTURE_ATLAS_WIDTH_TILES) / (float)Global.TEXTURE_ATLAS_WIDTH_TILES;
        float f4 = f3 + 1.0F / (float)Global.TEXTURE_ATLAS_WIDTH_TILES;
        float f5 = (float)(i / Global.TEXTURE_ATLAS_WIDTH_TILES) / (float)Global.TEXTURE_ATLAS_WIDTH_TILES;
        float f6 = f5 + 1.0F / (float)Global.TEXTURE_ATLAS_WIDTH_TILES;
        float f7 = 1.0F;
        float f8 = 0.5F;
        float f9 = 0.25F;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glRotatef(180.0F - this.renderDispatcher.viewLerpYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderDispatcher.viewLerpPitch, 1.0F, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 0.0F);
        tessellator.addVertexWithUV(0.0F - f8, 0.0F - f9, 0.0, f3, f6);
        tessellator.addVertexWithUV(f7 - f8, 0.0F - f9, 0.0, f4, f6);
        tessellator.addVertexWithUV(f7 - f8, 1.0F - f9, 0.0, f4, f5);
        tessellator.addVertexWithUV(0.0F - f8, 1.0F - f9, 0.0, f3, f5);
        tessellator.draw();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
        GL11.glEnable(2896);
    }

    public void doRender(EntityZephyrSnowball entity, double x, double y, double z, float yaw, float partialTick) {
        this.renderSnowball(entity, x, y, z, yaw, partialTick);
    }
}
