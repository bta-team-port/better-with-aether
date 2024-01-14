package bta.aether.gui;

import bta.aether.entity.EntityAetherBossBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.render.Tessellator;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GuiBossBar extends GuiScreen {
    private final int barWidth = 200;
    private final int barHeight = 25;

    public GuiBossBar(Minecraft mc) {
        this.mc = mc;
    }


    public void drawBossBars(List<EntityAetherBossBase> bosses) {
        int i = 0;
        for (EntityAetherBossBase boss : bosses) {
            if (i+1 <= 3) drawBossBar(boss, i++);
        }
    }

    void drawBossBar(EntityAetherBossBase boss, int offset) {
        int baseY = 10;
        int textHeight = 10;
        int offsetY = this.barHeight+ textHeight;
        int textX = this.mc.resolution.scaledWidth/2;
        int textY = baseY +(offsetY*offset);
        int barY = textY+ textHeight;
        int barX = textX-this.barWidth/2;

        this.drawStringCentered(this.mc.fontRenderer, boss.getBossTitle(), textX, textY, 0xFFFFFFFF);
        drawProgressBar(barX, barY, boss.health, boss.getMaxHealth());
    }

    private void drawProgressBar(int barX, int barY, int health, int maxHealth) {
        float progress = (float)health/(float)maxHealth;
        int progressWidth = (int)(barWidth*progress);

        int i = mc.renderEngine.getTexture("/assets/aether/gui/boss_bar_bg.png");
        int j = mc.renderEngine.getTexture("/assets/aether/gui/boss_bar_health.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        drawTexturedModalRect(barX, barY, 0, 0, barWidth, barHeight, barWidth, 1.0f/barWidth); // Background
        mc.renderEngine.bindTexture(j);
        drawTexturedModalRect(barX, barY, 0, 0, progressWidth, barHeight, progressWidth, barWidth, 1.0f/barWidth); // LifeBar
        //drawTexturedModalRect(barX, barY, 0, 0, barWidth, barHeight);
    }

    private void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, int uvWidth, int uvHeight, float scale) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((x + 0), (y + height), this.zLevel, ((float)(u + 0) * scale), ((float)(v + uvHeight) * scale));
        tessellator.addVertexWithUV((x + width), (y + height), this.zLevel, ((float)(u + uvWidth) * scale), ((float)(v + uvHeight) * scale));
        tessellator.addVertexWithUV((x + width), (y + 0), this.zLevel, ((float)(u + uvWidth) * scale), ((float)(v + 0) * scale));
        tessellator.addVertexWithUV((x + 0), (y + 0), this.zLevel, ((float)(u + 0) * scale), ((float)(v + 0) * scale));
        tessellator.draw();
    }
}
