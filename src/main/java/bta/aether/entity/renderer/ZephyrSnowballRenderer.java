package bta.aether.entity.renderer;

import bta.aether.entity.projectiles.EntityZephyrSnowball;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.Global;
import net.minecraft.core.item.Item;
import org.lwjgl.opengl.GL11;

public class ZephyrSnowballRenderer extends EntityRenderer<EntityZephyrSnowball> {
    public ZephyrSnowballRenderer() {
    }

    @Override
    public void doRender(Tessellator tessellator, EntityZephyrSnowball entity, double x, double y, double z, float yaw, float partTicks) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glDisable(2896);
        GL11.glEnable(32826);
        float f2 = 2.0F;
        GL11.glScalef(f2, f2, f2);

        IconCoordinate tex = TextureRegistry.getTexture("minecraft:item/snowball");
//      this.loadTexture("/gui/items.png");
        double f3 = tex.getIconUMin();
        double f4 = tex.getIconUMax();
        double f5 = tex.getIconVMin();
        double f6 = tex.getIconVMax();
        double f7 = 1.0F;
        double f8 = 0.5F;
        double f9 = 0.25F;
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

}
