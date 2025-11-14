package teamport.aether.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScreenHudDesigner;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.core.entity.Entity;
import org.lwjgl.opengl.GL11;
import teamport.aether.entity.AetherJumpAmount;


public class ComponentJumpBar extends HudComponentMovable {

    private static final String TEXTURE = "/assets/aether/textures/gui/jumpbar.png";
    private static final int ICON_WIDTH = 9;
    private static final int ICON_HEIGHT = 9;
    private static final int ROW_LENGTH = 10;
    private static final int SPACING_X = -1;
    private static final int SPACING_Y = -1;


    private Minecraft mc = Minecraft.getMinecraft();
    private int xScreenSize;
    private int yScreenSize;
    private Gui gui;

    private int rowAmount;

    public ComponentJumpBar(String key, Layout layout) {
        super(key, ICON_WIDTH * 9, ICON_HEIGHT, layout);
    }

    @Override
    public int getYSize(Minecraft mc) {
        if (!(mc.currentScreen instanceof ScreenHudDesigner) && !this.isVisible(mc)) {
            return 0;
        }
        return (ICON_HEIGHT - SPACING_Y) * rowAmount;
    }

    @Override
    public int getXSize(Minecraft mc) {
        return (ICON_WIDTH + SPACING_X) * ROW_LENGTH - SPACING_X;
    }

    @Override
    public int getAnchorY(ComponentAnchor anchor) {
        return (int) (anchor.yPosition * getYSize(mc));
    }

    @Override
    public int getAnchorX(ComponentAnchor anchor) {
        return (int) (anchor.xPosition * getXSize(mc));
    }

    @Override
    public boolean isVisible(Minecraft minecraft) {
        return mc.thePlayer.vehicle instanceof AetherJumpAmount && mc.gameSettings.immersiveMode.drawHotbar();
    }

    @Override
    public void render(Minecraft minecraft, HudIngame gui, int xScreenSize, int yScreenSize, float f) {
        mc = minecraft;
        this.gui = gui;
        this.xScreenSize = xScreenSize;
        this.yScreenSize = yScreenSize;

        if (mc.thePlayer.isPassenger()) {
            Entity vehicle = (Entity) mc.thePlayer.vehicle;

            if (vehicle instanceof AetherJumpAmount) {
                drawJumpBar(((AetherJumpAmount) vehicle).getJumpMaxAmount(), ((AetherJumpAmount) vehicle).getJumpAmount());
            }
        }
    }

    @Override
    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        mc = minecraft;
        this.gui = gui;
        this.xScreenSize = xScreenSize;
        this.yScreenSize = yScreenSize;

        drawJumpBar(3, 2);
    }

    public void drawJumpBar(int jumpMaxAmount, int jumpAmount) {
        rowAmount = getRows(jumpMaxAmount);

        int barX = getLayout().getComponentX(mc, this, xScreenSize);
        int barY = getLayout().getComponentY(mc, this, yScreenSize) + ((ICON_HEIGHT - SPACING_Y) * (rowAmount - 1));

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        //mc.textureManager.loadTexture(texture).bind();
        mc.textureManager.bindTexture(mc.textureManager.loadTexture(TEXTURE));

        drawRowsOfIcons(barX, barY, ICON_WIDTH, 0, jumpMaxAmount);
        drawRowsOfIcons(barX, barY, 0, 0, jumpAmount);
    }

    public int getRows(int amount) {
        return (amount % ROW_LENGTH) <= 0 ? amount / ROW_LENGTH : (amount / ROW_LENGTH) + 1;
    }

    public void drawRowsOfIcons(int screenX, int screenY, int u, int v, int iconAmount) {
        int iconsToDraw = iconAmount;
        for (int row = 0; row < getRows(iconAmount); row++) {
            for (int collumn = 0; collumn < Math.min(ROW_LENGTH, iconsToDraw); collumn++) {
                int currentX = screenX + (ICON_WIDTH * collumn) + (SPACING_X * collumn);
                int currentY = screenY - (ICON_HEIGHT * row) + (SPACING_Y * row);

                gui.drawTexturedModalRect(currentX, currentY, u, v, ICON_WIDTH, ICON_HEIGHT);
            }
            iconsToDraw -= ROW_LENGTH;
        }
    }
}
