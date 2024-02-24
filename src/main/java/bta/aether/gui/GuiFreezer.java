package bta.aether.gui;

import bta.aether.container.ContainerFreezer;
import bta.aether.tile.TileEntityFreezer;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiFreezer extends GuiContainer {

    private TileEntityFreezer tile;

    public GuiFreezer(InventoryPlayer inventoryPlayer, TileEntityFreezer tile) {
        super(new ContainerFreezer(inventoryPlayer, tile));
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f)
    {
        int i = mc.renderEngine.getTexture("/assets/aether/gui/enchanter.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        if (this.tile.isBurning()) {
            int l = this.tile.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(j + 56, k + 36 + 12 - l, 176, 12 - l, 14, l + 2);
        }
        int i1 = this.tile.getProgressScaled(24);
        this.drawTexturedModalRect(j + 79, k + 34, 176, 14, i1 + 1, 16);

    }
    @Override
    protected void drawGuiContainerForegroundLayer()
    {
        super.drawGuiContainerForegroundLayer();
        fontRenderer.drawString(I18n.getInstance().translateKey("aether.gui.freezer.title"), 64, 6, 0xFF404040);
    }
}
