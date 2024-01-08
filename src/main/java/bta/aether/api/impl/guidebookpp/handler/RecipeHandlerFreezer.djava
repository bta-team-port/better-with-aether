package bta.aether.api.impl.guidebookpp.handler;

import bta.aether.Aether;
import bta.aether.api.impl.guidebookpp.container.ContainerGuidebookEnchanterRecipe;
import bta.aether.api.impl.guidebookpp.container.ContainerGuidebookFreezerRecipe;
import bta.aether.tile.recipes.EnchanterRecipes;
import bta.aether.tile.recipes.FreezerRecipes;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeBase;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IRecipeHandlerBase;
import sunsetsatellite.guidebookpp.RecipeGroup;
import sunsetsatellite.guidebookpp.RecipeRegistry;
import sunsetsatellite.guidebookpp.recipes.RecipeSimple;

import java.util.ArrayList;

public class RecipeHandlerFreezer implements IRecipeHandlerBase {

    @Override
    public ContainerGuidebookRecipeBase getContainer(Object o) {
        RecipeSimple recipeSimple = (RecipeSimple) o;
        return new ContainerGuidebookFreezerRecipe(new ItemStack(Aether.freezer), recipeSimple);
    }

    @Override
    public void addRecipes() {
        GuidebookPlusPlus.LOGGER.info("Adding recipes for: " + this.getClass().getSimpleName());
        ArrayList<RecipeSimple> recipes = new ArrayList<>();
        FreezerRecipes.getRecipeList().forEach((I, O)->{
            recipes.add(new RecipeSimple(I, O.getLeft()));
        });
        RecipeGroup group = new RecipeGroup(Aether.MOD_ID,Aether.freezer,this,recipes);
        RecipeRegistry.groups.add(group);
    }
}
