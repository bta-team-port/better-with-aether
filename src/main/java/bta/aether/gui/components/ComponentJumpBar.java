package bta.aether.gui.components;

import bta.aether.entity.IPlayerJumpAmount;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiHudDesigner;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import org.lwjgl.opengl.GL11;

public class ComponentJumpBar extends MovableHudComponent {

    private static final String texture = "/assets/aether/gui/aetherIcons.png";
    private static final int iconWidth = 9;
    private static final int iconHeight = 9;
    private static final int rowLength = 10;
    private static final int spacingX = -1;
    private static final int spacingY = -1;


    private Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
    private int xScreenSize;
    private int yScreenSize;
    private Gui gui;

    private int rowAmount;

    public ComponentJumpBar(String key, Layout layout) {
        super(key, iconWidth*9, iconHeight, layout);
    }

    @Override
    public int getYSize(Minecraft mc) {
        if (!(mc.currentScreen instanceof GuiHudDesigner) && !this.isVisible(mc)) {
            return 0;
        }
        return (iconHeight - spacingY) * rowAmount      ;
    }

    @Override
    public int getXSize(Minecraft mc) {
        return (iconWidth + spacingX) * rowLength - spacingX;
    }

    @Override
    public int getAnchorY(ComponentAnchor anchor) {
        return (int)(anchor.yPosition * getYSize(mc));
    }

    @Override
    public int getAnchorX(ComponentAnchor anchor) {
        return (int)(anchor.xPosition * getXSize(mc));
    }

    @Override
    public boolean isVisible(Minecraft minecraft) {
        return ((IPlayerJumpAmount)mc.thePlayer).aether$getJumpMaxAmount() >= 1;
    }

    @Override
    public void render(Minecraft minecraft, GuiIngame gui, int xScreenSize, int yScreenSize, float f) {
        mc = minecraft;
        this.gui = gui;
        this.xScreenSize = xScreenSize;
        this.yScreenSize = yScreenSize;
        drawJumpBar(((IPlayerJumpAmount)mc.thePlayer).aether$getJumpMaxAmount(), ((IPlayerJumpAmount)mc.thePlayer).aether$getJumpAmount());
    }

    @Override
    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        mc = minecraft;
        this.gui = gui;
        this.xScreenSize = xScreenSize;
        this.yScreenSize = yScreenSize;
        drawJumpBar(3,2);
    }

    public void drawJumpBar(int jumpMaxAmount, int jumpAmount){
        rowAmount = getRows(jumpMaxAmount);

        int barX = getLayout().getComponentX(mc, this, xScreenSize);
        int barY = getLayout().getComponentY(mc, this, yScreenSize) + ((iconHeight - spacingY) * (rowAmount - 1));

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(mc.renderEngine.getTexture(texture));
        drawRowsOfIcons(barX, barY, iconWidth, 0, jumpMaxAmount);
        drawRowsOfIcons(barX, barY, 0,0 ,jumpAmount);
    }

    public int getRows(int amount) {
        return (amount%rowLength) <= 0 ? amount/rowLength : (amount/rowLength) + 1;
    }

    public void drawRowsOfIcons(int screenX, int screenY, int U, int V, int iconAmount){
        int iconsToDraw = iconAmount;
        for (int row = 0; row < getRows(iconAmount); row++) {
            for (int collumn = 0; collumn < Math.min(rowLength, iconsToDraw); collumn++) {
                int currentX = screenX + (iconWidth * collumn) + (spacingX * collumn);
                int currentY = screenY - (iconHeight * row) + (spacingY * row);

                gui.drawTexturedModalRect(currentX, currentY, U, V, iconWidth, iconHeight);
            }
            iconsToDraw -= rowLength;
        }
    }
}
