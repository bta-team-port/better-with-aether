package teamport.aether.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.lang.I18n;
import org.lwjgl.opengl.GL11;
import teamport.aether.entity.boss.AetherBossList;
import teamport.aether.entity.boss.EnemyBoss;

import java.util.List;

public class ComponentBossBar extends HudComponentMovable {
    public static final int barWidth = 256;
    public static final int barHeight = 16;
    public static final int textOffset = -10;
    public static final int spacing = 13;

    public static final int barAmountLimit = 3;

    //

    public static int height;

    public ComponentBossBar(String key, Layout layout) {
        super(key, barWidth, barHeight, layout);
    }

    @Override
    public boolean isVisible(Minecraft minecraft) {
        return !getBossesFromPlayer(minecraft).isEmpty();
    }

    @Override
    public int getAnchorY(ComponentAnchor anchor) {
        return (int)(anchor.yPosition * height);
    }

    @Override
    public int getYSize(Minecraft mc) {
        return height;
    }

    public List<Mob> getBossesFromPlayer(Minecraft mc) {
        List<Mob> bossList = ((AetherBossList) mc.thePlayer).aether$getBossList();

        return bossList.subList(0, Math.min(bossList.size(), barAmountLimit));
    }

    @Override
    public void render(Minecraft mc, HudIngame hudIngame, int xSizeScreen, int ySizeScreen, float f) {
        int i = 0;

        List<Mob> mobList = getBossesFromPlayer(mc);
        height = mobList.isEmpty() ? 0 : (barHeight + spacing) * mobList.size() + spacing;

        for (Mob mob : mobList) {
            drawBossBar(mc, hudIngame, mob, i++, xSizeScreen, ySizeScreen);
        }
    }

    @Override
    public void renderPreview(Minecraft mc, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen) {
        height = (barHeight + spacing) * 3 + spacing;

        for (int offset = 0; offset < 3; offset++) {
            int barX = getLayout().getComponentX(mc, this, xSizeScreen);
            int barY = getLayout().getComponentY(mc, this, ySizeScreen) + (barHeight + spacing) * offset + spacing;
            int textX = barX + barWidth / 2;
            int textY = barY + textOffset;

            drawProgressBar(mc, gui, barX, barY, 100, 100);
            String title = I18n.getInstance().translateKey("aether.menu.boss_bar.preview_name");
            gui.drawStringCentered(mc.font, title, textX, textY, 0xFFFFFFFF);
        }
    }

    void drawBossBar(Minecraft mc, Gui gui, Mob mob, int offset, int xSizeScreen, int ySizeScreen) {
        int barX = getLayout().getComponentX(mc, this, xSizeScreen);
        int barY = getLayout().getComponentY(mc, this, ySizeScreen) + (barHeight + spacing) * offset + spacing;
        int textX = barX + barWidth/2;
        int textY = barY + textOffset;

        drawProgressBar(mc, gui, barX, barY, mob.getHealth(), mob.getMaxHealth());
        String entityName = (mob instanceof EnemyBoss) ? ((EnemyBoss) mob).getBossTitle() : mob.getDisplayName();
        gui.drawStringCentered(mc.font, entityName, textX, textY, 0xFFFFFFFF);
    }

    public void drawProgressBar(Minecraft mc, Gui gui, int barX, int barY, int health, int maxHealth) {
        float progress = (float)health/(float)maxHealth;
        int progressWidth = (int)(barWidth*progress);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.textureManager.bindTexture(mc.textureManager.loadTexture("/assets/aether/textures/gui/boss_healthbar.png"));
        gui.drawTexturedModalRect(barX, barY, 0, 16, barWidth, barHeight); // Background
        gui.drawTexturedModalRect(barX, barY, 0, 0, progressWidth, barHeight); // LifeBar
    }
}
