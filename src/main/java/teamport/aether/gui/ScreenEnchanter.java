package teamport.aether.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.container.ScreenContainerAbstract;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.lwjgl.opengl.GL11;
import teamport.aether.player.MenuEnchanter;
import teamport.aether.tile.TileEntityEnchanter;

@Environment(EnvType.CLIENT)
public class ScreenEnchanter extends ScreenContainerAbstract {

    public final TileEntityEnchanter enchantInventory;

    public ScreenEnchanter(ContainerInventory inventory, TileEntityEnchanter tileEntityEnchanter) {
        super(new MenuEnchanter(inventory, tileEntityEnchanter));
        this.enchantInventory = tileEntityEnchanter;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f) {
        this.mc.textureManager.loadTexture("/assets/aether/textures/gui/container/enchanter.png").bind();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
        if (this.enchantInventory.isProcessing()) {
            int fireHeight = this.enchantInventory.getEnergyTimeRemainingScaled(12);
            this.drawTexturedModalRect(j + 56, k + 36 + 12 - fireHeight, 176, 12 - fireHeight, 14, fireHeight + 2);
            int arrowWidth = this.enchantInventory.getProcessProgressScaled(24);
            this.drawTexturedModalRect(j + 79, k + 34, 176, 14, arrowWidth + 1, 16);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer() {
        I18n i18n = I18n.getInstance();
        this.font.drawString(i18n.translateKey("aether.gui.enchanter.title"), 60, 6, 0xFF404040);
        this.font.drawString(i18n.translateKey("gui.furnace.label.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
}
