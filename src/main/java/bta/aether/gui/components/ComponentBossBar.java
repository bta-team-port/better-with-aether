package bta.aether.gui.components;

import bta.aether.entity.EntityAetherBossBase;
import bta.aether.entity.EntityDevBoss;
import bta.aether.entity.IPlayerBossList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiHudDesigner;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import net.minecraft.client.render.Tessellator;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ComponentBossBar extends MovableHudComponent {
    private static final int barWidth = 200;
    private static final int barHeight = 25;
    private static final int textHeight = 10;
    private static final int baseY = 10;
    private Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
    private int xScreenSize;
    private int yScreenSize;
    private int barAmount;
    private Gui gui;
    public ComponentBossBar(String key, Layout layout) {
        super(key, barWidth, barHeight, layout);
    }

    @Override
    public boolean isVisible(Minecraft minecraft) {
        return !((IPlayerBossList)minecraft.thePlayer).aether$getBossList().isEmpty();
    }
    @Override
    public int getAnchorY(ComponentAnchor anchor) {
        return (int)(anchor.yPosition * getYSize(mc));
    }
    @Override
    public int getYSize(Minecraft mc) {
        if (!(mc.currentScreen instanceof GuiHudDesigner) && !this.isVisible(mc)) {
            return 0;
        }
        return height();
    }
    public int height(){
        return (barHeight + baseY) * barAmount + baseY;
    }

    @Override
    public void render(Minecraft minecraft, GuiIngame guiIngame, int i, int j, float f) {
        mc = minecraft;
        gui = guiIngame;
        xScreenSize = i;
        yScreenSize = j;
        drawBossBars(((IPlayerBossList)minecraft.thePlayer).aether$getBossList());
    }

    @Override
    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int i, int j) {
        mc = minecraft;
        this.gui = gui;
        xScreenSize = i;
        yScreenSize = j;
        List<EntityAetherBossBase> bossBases = new ArrayList<>();
        bossBases.add(new EntityDevBoss(minecraft.theWorld));
        bossBases.add(new EntityDevBoss(minecraft.theWorld));
        drawBossBars(bossBases);
    }
    public void drawBossBars(List<EntityAetherBossBase> bosses) {
        int i = 0;
        for (EntityAetherBossBase boss : bosses) {
            if (i+1 <= 3) drawBossBar(boss, i++);
        }
        barAmount = Math.min(i, 3);
    }

    void drawBossBar(EntityAetherBossBase boss, int offset) {
        int barX = getLayout().getComponentX(mc, this, xScreenSize);
        int barY = getLayout().getComponentY(mc, this, yScreenSize) + (barHeight + baseY) * offset + baseY;
        int textX = barX + barWidth/2;
        int textY = barY + textHeight;

        drawProgressBar(barX, barY, boss.health, boss.maxHealth);
        gui.drawStringCentered(this.mc.fontRenderer, boss.getBossTitle(), textX, textY, 0xFFFFFFFF);
    }

    private void drawProgressBar(int barX, int barY, int health, int maxHealth) {
        float progress = (float)health/(float)maxHealth;
        int progressWidth = (int)(barWidth*progress);

        int i = mc.renderEngine.getTexture("/assets/aether/gui/boss_bar_bg.png");
        int j = mc.renderEngine.getTexture("/assets/aether/gui/boss_bar_health.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        gui.drawTexturedModalRect(barX, barY, 0, 0, barWidth, barHeight, barWidth, 1.0f/barWidth); // Background
        mc.renderEngine.bindTexture(j);
        drawTexturedModalRect(barX, barY, 0, 0, progressWidth, barHeight, progressWidth, barWidth, 1.0f/barWidth); // LifeBar
        //drawTexturedModalRect(barX, barY, 0, 0, barWidth, barHeight);
    }

    private void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, int uvWidth, int uvHeight, float scale) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((x), (y + height), gui.zLevel, ((float)(u) * scale), ((float)(v + uvHeight) * scale));
        tessellator.addVertexWithUV((x + width), (y + height), gui.zLevel, ((float)(u + uvWidth) * scale), ((float)(v + uvHeight) * scale));
        tessellator.addVertexWithUV((x + width), (y), gui.zLevel, ((float)(u + uvWidth) * scale), ((float)(v) * scale));
        tessellator.addVertexWithUV((x), (y), gui.zLevel, ((float)(u) * scale), ((float)(v) * scale));
        tessellator.draw();
    }
}
