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
                EntityDispatcher.EntityDispatcherEntry<?> entry = EntityDispatcher.getInstance().entryForId(recipe.getOutput().getEntity());
                return entry != null ? entry.entityClass : null;
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
}
