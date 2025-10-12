package teamport.aether.recipe;

import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.item.ItemStack;

public class RecipeGroupAetherMachine extends RecipeGroup<RecipeEntryAetherMachine> {
    public RecipeGroupAetherMachine(RecipeSymbol machine) {
        super(machine);
    }

    public ItemStack findOutput(ItemStack stack) {
        for (RecipeEntryAetherMachine recipe : getAllRecipes()) {
            if (recipe.matches(stack)) {
                return recipe.getOutput().copy();
            }
        }
        return null;
    }

    public RecipeEntryAetherMachine findRecipe(ItemStack stack) {
        for (RecipeEntryAetherMachine recipe : getAllRecipes()) {
            if (recipe.matches(stack)) {
                return recipe;
            }
        }
        return null;
    }
}

