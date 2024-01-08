package bta.aether.api.impl.guidebookpp.handler;

import bta.aether.Aether;
import bta.aether.api.impl.guidebookpp.container.ContainerGuidebookEnchanterRecipe;
import bta.aether.tile.recipes.EnchanterRecipes;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeBase;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IRecipeHandlerBase;
import sunsetsatellite.guidebookpp.RecipeGroup;
import sunsetsatellite.guidebookpp.RecipeRegistry;
import sunsetsatellite.guidebookpp.recipes.RecipeSimple;

import java.util.ArrayList;

public class RecipeHandlerEnchanter implements IRecipeHandlerBase {
    @Override
    public ContainerGuidebookRecipeBase getContainer(Object o) {
        RecipeSimple recipeSimple = (RecipeSimple) o;
        return new ContainerGuidebookEnchanterRecipe(new ItemStack(Aether.enchanter), recipeSimple);
    }

    @Override
    public void addRecipes() {
        GuidebookPlusPlus.LOGGER.info("Adding recipes for: " + this.getClass().getSimpleName());
        ArrayList<RecipeSimple> recipes = new ArrayList<>();
        EnchanterRecipes.getRecipeList().forEach((I,O)->{
            recipes.add(new RecipeSimple(I, O.getLeft()));
        });
        RecipeGroup group = new RecipeGroup(Aether.MOD_ID,Aether.enchanter,this,recipes);
        RecipeRegistry.groups.add(group);
    }
}
