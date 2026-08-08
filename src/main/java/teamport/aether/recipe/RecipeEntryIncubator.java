package teamport.aether.recipe;

import net.minecraft.core.data.registry.recipe.HasJsonAdapter;
import net.minecraft.core.data.registry.recipe.RecipeEntryBase;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.SearchQuery;
import net.minecraft.core.data.registry.recipe.adapter.RecipeJsonAdapter;
import net.minecraft.core.item.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class RecipeEntryIncubator extends RecipeEntryBase<RecipeSymbol, RecipeEntity, Integer> implements HasJsonAdapter {
    public RecipeEntryIncubator(RecipeSymbol input, RecipeEntity output, int time) {
        super(input, output, time);
    }

    @SuppressWarnings("unused")
    public RecipeEntryIncubator() {}

    public boolean matchesQueryIgnoreExceptions(SearchQuery query) {
        try {
            return this.matchesQuery(query);
        } catch (IllegalArgumentException | NullPointerException var3) {
            return false;
        }
    }

    public boolean matches(ItemStack stack) {
        return this.getInput().matches(stack);
    }

    public boolean matchesQuery(@NonNull SearchQuery query) {
        switch (query.mode) {
            case ALL: {
                if ((matchesRecipe(query) || matchesUsage(query))) return true;
                break;
            }
            case RECIPE: {
                if (matchesRecipe(query)) return true;
                break;
            }
            case USAGE: {
                if (matchesUsage(query)) return true;
                break;
            }
        }
        return false;
    }


    public boolean matchesRecipe(@NonNull SearchQuery query) {
        if (query.query.getLeft() == SearchQuery.QueryType.NAME) {
            if (query.strict && this.getOutput().getEntity().equalsIgnoreCase(query.query.getRight())) {
                return true;
            }
            return !query.strict && this.getOutput().getEntity().toLowerCase().contains(query.query.getRight().toLowerCase());
        }
        return false;
    }

    public boolean matchesUsage(SearchQuery query) {
        List<ItemStack> stacks = getInput().resolve();
        for (ItemStack stack : stacks) {
            if (stack == null) continue;
            if (query.query.getLeft() == SearchQuery.QueryType.NAME) {
                if (query.strict && stack.getDisplayName().equalsIgnoreCase(query.query.getRight())) {
                    return true;
                }
                if (!query.strict && stack.getDisplayName().toLowerCase().contains(query.query.getRight().toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public RecipeJsonAdapter<?> getAdapter() {
        return new RecipeIncubatorJsonAdapter();
    }
}
