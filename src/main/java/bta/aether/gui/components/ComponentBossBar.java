package bta.aether.gui.components;

import bta.aether.Aether;
import bta.aether.entity.EntityAetherBossBase;
import bta.aether.entity.IPlayerBossList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiHudDesigner;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import net.minecraft.core.lang.I18n;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ComponentBossBar extends MovableHudComponent {
    private static final int barWidth = 256;
    private static final int barHeight = 16;
    private static final int textOffset = -10;
    private static final int spacing = 13;
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
        return (barHeight + spacing) * barAmount + spacing;
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
        this.barAmount = 3;
        this.gui = gui;
        xScreenSize = i;
        yScreenSize = j;

        for (int offset = 0; offset < 3; offset++) {
            int barX = getLayout().getComponentX(mc, this, xScreenSize);
            int barY = getLayout().getComponentY(mc, this, yScreenSize) + (barHeight + spacing) * offset + spacing;
            int textX = barX + barWidth / 2;
            int textY = barY + textOffset;

            drawProgressBar(barX, barY, 100, 100);
            gui.drawStringCentered(this.mc.fontRenderer, I18n.getInstance().translateKey(Aether.MOD_ID+".menu.boss_bar.preview_name"), textX, textY, 0xFFFFFFFF);
        }
    }
    public void drawBossBars(List<EntityAetherBossBase> bosses) {
        int i = 0;
        for (EntityAetherBossBase boss : bosses) {
            if (i+1 <= 3 && getDistanceFrom(boss.x, boss.y, boss.z, mc.thePlayer.x, mc.thePlayer.y, mc.thePlayer.z) < 100) drawBossBar(boss, i++);
        }
        barAmount = Math.min(i, 3);
    }
    void drawBossBar(EntityAetherBossBase boss, int offset) {
        int barX = getLayout().getComponentX(mc, this, xScreenSize);
        int barY = getLayout().getComponentY(mc, this, yScreenSize) + (barHeight + spacing) * offset + spacing;
        int textX = barX + barWidth/2;
        int textY = barY + textOffset;

        drawProgressBar(barX, barY, boss.health, boss.getMaxHealth());
        gui.drawStringCentered(this.mc.fontRenderer, boss.getBossTitle(), textX, textY, 0xFFFFFFFF);
    }
    private void drawProgressBar(int barX, int barY, int health, int maxHealth) {
        float progress = (float)health/(float)maxHealth;
        int progressWidth = (int)(barWidth*progress);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/assets/aether/gui/bossHPBar.png"));
        gui.drawTexturedModalRect(barX, barY, 0, 16, barWidth, barHeight); // Background
        gui.drawTexturedModalRect(barX, barY, 0, 0, progressWidth, barHeight); // LifeBar
    }

    private double getDistanceFrom(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.abs(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2)));
    }
}
