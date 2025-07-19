package teamport.aether.gui.machine.incubator;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.lwjgl.opengl.GL11;
import teamport.aether.gui.machine.ScreenAetherMachine;
import teamport.aether.tile.TileEntityIncubator;

@Environment(EnvType.CLIENT)
public class ScreenIncubator extends ScreenAetherMachine {

    public final TileEntityIncubator incubator;

    public ScreenIncubator(ContainerInventory inventory, TileEntityIncubator tileEntityIncubator) {
        super(new MenuIncubator(inventory, tileEntityIncubator));
        this.incubator = tileEntityIncubator;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f) {
        // TODO maybe at some point add a custom incubator inventory
        this.mc.textureManager.loadTexture("/assets/aether/textures/gui/container/incubator.png").bind();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
        if (this.incubator.isProcessing()) {
            int fireHeight = this.incubator.getEnergyTimeRemainingScaled(12);
            this.drawTexturedModalRect(j + 56, k + 36 + 12 - fireHeight, 176, 12 - fireHeight, 14, fireHeight + 2);
            int arrowWidth = this.incubator.getProcessProgressScaled(24);
            this.drawTexturedModalRect(j + 79, k + 34, 176, 14, arrowWidth + 1, 16);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer() {
        I18n i18n = I18n.getInstance();
        this.font.drawString(i18n.translateKey("aether.gui.incubator.title"), 60, 6, 0xFF404040);
        this.font.drawString(i18n.translateKey("gui.furnace.label.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    // TODO implement the function once recipe are in place
    @Override
    public int getTargetSlot(ItemStack stackInSlot, int clickedItemId) {
//        boolean isIngredient = AetherRecipes.FREEZER.findRecipe(stackInSlot) != null;
//        boolean isFuel = LookupFuelFreezer.instance.getFuelYield(clickedItemId) > 0;
//        if (isIngredient) {
//            return 1;
//        }
//        if (isFuel) {
//            return 2;
//        }
        return 0;
    }
}
