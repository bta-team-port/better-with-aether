package teamport.aether.gui.machine.incubator;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.lwjgl.opengl.GL11;
import teamport.aether.AetherRecipes;
import teamport.aether.gui.machine.ScreenAetherMachine;
import teamport.aether.lookup.LookupFuelIncubator;
import teamport.aether.entity.tile.TileEntityIncubator;

@Environment(EnvType.CLIENT)
public class ScreenIncubator extends ScreenAetherMachine {

    public static final int progressBarHeight = 58;
    public static final int flameHeight = 14;
    public final TileEntityIncubator incubator;

    public ScreenIncubator(ContainerInventory inventory, TileEntityIncubator tileEntityIncubator) {
        super(new MenuIncubator(inventory, tileEntityIncubator));
        this.incubator = tileEntityIncubator;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f) {
        this.mc.textureManager.loadTexture("/assets/aether/textures/gui/container/incubator.png").bind();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        if (this.incubator.isProcessing()) {
            int fireHeight = this.incubator.getEnergyTimeRemainingScaled(12);
            this.drawTexturedModalRect(x + 73, y + 36 + 12 - fireHeight, 176, 12 - fireHeight, flameHeight, fireHeight + 2);
        }
        int tileProgressHeightScaled = this.incubator.getProcessProgressScaled(progressBarHeight);
        this.drawTexturedModalRect(x + 100, y + flameHeight + progressBarHeight - tileProgressHeightScaled, 176, progressBarHeight - tileProgressHeightScaled + flameHeight, flameHeight, tileProgressHeightScaled);

    }

    @Override
    public void drawGuiContainerForegroundLayer() {
        I18n i18n = I18n.getInstance();
        this.font.drawString(i18n.translateKey("aether.gui.incubator.title"), 60, 6, 0xFF404040);
        this.font.drawString(i18n.translateKey("gui.furnace.label.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    public int getTargetSlot(ItemStack stackInSlot, int clickedItemId) {
        if(stackInSlot == null) return 0;
        boolean isIngredient = AetherRecipes.INCUBATOR.findRecipe(stackInSlot) != null;
        boolean isFuel = LookupFuelIncubator.instance.getFuelYield(clickedItemId) > 0;
        if (isIngredient) {
            return 1;
        }
        if (isFuel) {
            return 2;
        }
        return 0;
    }
}
