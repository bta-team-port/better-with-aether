package bta.aether.api.impl.guidebookpp.container;

import bta.aether.Aether;
import bta.aether.tile.recipes.EnchanterRecipes;
import bta.aether.tile.recipes.FreezerRecipes;
import net.minecraft.client.gui.GuiGuidebook;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeBase;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.player.inventory.slot.SlotGuidebook;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IContainerRecipeBase;
import sunsetsatellite.guidebookpp.recipes.RecipeBase;
import sunsetsatellite.guidebookpp.recipes.RecipeSimple;

import java.util.List;

public class ContainerGuidebookFreezerRecipe extends ContainerGuidebookRecipeBase implements IContainerRecipeBase {

    ItemStack machine;

    public ContainerGuidebookFreezerRecipe(ItemStack stack, RecipeSimple recipe){
        machine = stack;
        this.addSlot(new SlotGuidebook(0, 9, 1, recipe.input, false));
        this.addSlot(new SlotGuidebook(1, 69, 19, recipe.output, false));
        this.addSlot(new SlotGuidebook(2, 9, 37, new ItemStack(Aether.icestone, Math.max(FreezerRecipes.getTime(recipe.input)/500,1)), false));
        this.addSlot(new SlotGuidebook(3,36,18,stack,true));
    }

    @Override
    public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public void drawContainer(GuiGuidebook guiGuidebook, int xSize, int ySize, int index, RecipeBase recipeBase) {
        ItemEntityRenderer itemRenderer = new ItemEntityRenderer();
        int i = GuidebookPlusPlus.mc.renderEngine.getTexture("/assets/aether/gui/enchanter_recipe.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuidebookPlusPlus.mc.renderEngine.bindTexture(i);
        int j = (guiGuidebook.width - xSize) / 2;
        int k = (guiGuidebook.height - ySize) / 2;
        int xPos = j + 29 + 158 * (index / 3);
        int yPos = k + 30 + 62 * (index % 3);
        int yOffset = 0;
        guiGuidebook.drawTexturedModalRect(xPos - 20, yPos, 138, yOffset, 121, 54);
    }
}
