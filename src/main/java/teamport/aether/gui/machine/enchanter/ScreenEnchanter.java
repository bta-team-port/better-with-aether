package teamport.aether.gui.machine.enchanter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.lwjgl.opengl.GL11;
import teamport.aether.AetherRecipes;
import teamport.aether.entity.tile.TileEntityEnchanter;
import teamport.aether.gui.machine.ScreenAetherMachine;
import teamport.aether.lookup.LookupFuelEnchanter;

@Environment(EnvType.CLIENT)
public class ScreenEnchanter extends ScreenAetherMachine {

    private final TileEntityEnchanter enchanter;

    public ScreenEnchanter(ContainerInventory inventory, TileEntityEnchanter tileEntityEnchanter) {
        super(new MenuEnchanter(inventory, tileEntityEnchanter));
        this.enchanter = tileEntityEnchanter;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f) {
        this.mc.textureManager.loadTexture("/assets/aether/textures/gui/container/enchanter.png").bind();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
        if (this.enchanter.isProcessing()) {
            int fireHeight = this.enchanter.getEnergyTimeRemainingScaled(12);
            this.drawTexturedModalRect(j + 56, k + 36 + 12 - fireHeight, 176, 12 - fireHeight, 14, fireHeight + 2);
            int arrowWidth = this.enchanter.getProcessProgressScaled(24);
            this.drawTexturedModalRect(j + 79, k + 34, 176, 14, arrowWidth + 1, 16);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer() {
        I18n i18n = I18n.getInstance();
        this.font.drawString(i18n.translateKey("aether.gui.enchanter.title"), 60, 6, 0xFF404040);
        this.font.drawString(i18n.translateKey("gui.furnace.label.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    public int getTargetSlot(ItemStack stackInSlot, int clickedItemId) {
        boolean isIngredient = AetherRecipes.ENCHANTER.findRecipe(stackInSlot) != null;
        boolean isFuel = LookupFuelEnchanter.INSTANCE.getFuelYield(clickedItemId) > 0;
        if (isIngredient) {
            return 1;
        }
        if (isFuel) {
            return 2;
        }
        return 0;
    }
}
