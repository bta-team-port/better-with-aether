package bta.aether.gui;

import bta.aether.container.ContainerIncubator;
import bta.aether.tile.TileEntityIncubator;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiIncubator extends GuiContainer {

    private final String texture = "/Jar/aether/gui/incubator.png";
    private final int progressBarWidth = 15;
    private final int progressBarHeight = 58;
    private final int flameWidth = 14;
    private final int flameHeight = 14;

    private final TileEntityIncubator tile;

    public GuiIncubator(InventoryPlayer inventoryPlayer, TileEntityIncubator tile) {
        super(new ContainerIncubator(inventoryPlayer, tile));
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(mc.renderEngine.getTexture(this.texture));

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        if (this.tile.isBurning()) {
            int tileFlameScaled = this.tile.getBurnTimeRemainingScaled(this.flameHeight);
            this.drawTexturedModalRect(x + 73, y + 35 + this.flameHeight - tileFlameScaled, 176, this.flameHeight - tileFlameScaled, flameWidth, tileFlameScaled);
        }

        int tileProgressHeightScaled = this.tile.getProgressScaled(progressBarHeight);
        this.drawTexturedModalRect(x + 100, y + flameHeight + progressBarHeight - tileProgressHeightScaled, 176, this.progressBarHeight - tileProgressHeightScaled + flameHeight, this.progressBarWidth, tileProgressHeightScaled);
    }
    @Override
    protected void drawGuiContainerForegroundLayer()
    {
        super.drawGuiContainerForegroundLayer();
        fontRenderer.drawString(I18n.getInstance().translateKey("aether.gui.incubator.title"), 64, 6, 0xFF404040);
    }
}
