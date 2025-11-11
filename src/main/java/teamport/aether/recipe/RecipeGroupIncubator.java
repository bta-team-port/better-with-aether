package teamport.aether.recipe;

import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class RecipeGroupIncubator extends RecipeGroup<RecipeEntryIncubator> {
    public RecipeGroupIncubator(RecipeSymbol machine) {
        super(machine);
    }

    public @Nullable Class<? extends Entity> findOutput(ItemStack stack) {
        for (RecipeEntryIncubator recipe : getAllRecipes()) {
            if (recipe.matches(stack)) {
                return EntityDispatcher.classForId(recipe.getOutput().getEntity());
            }
        }
        return null;
    }

    public RecipeEntryIncubator findRecipe(ItemStack stack) {
        for (RecipeEntryIncubator recipe : getAllRecipes()) {
            if (recipe.matches(stack)) {
                return recipe;
            }
        }
        return null;
    }

    public boolean isOutput(Class<? extends Entity> aClass) {
        for (RecipeEntryIncubator recipe : getAllRecipes()) {
            String entityName = recipe.getOutput().getEntity();
            Class<? extends Entity> entity = EntityDispatcher.classForId(entityName);
            if (entity == null) continue;
            if (entity.equals(aClass)) return true;
        }
        return false;
    }
}
