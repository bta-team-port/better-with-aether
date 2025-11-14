package teamport.aether.recipe;

import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.*;
import net.minecraft.core.data.registry.recipe.adapter.RecipeJsonAdapter;
import net.minecraft.core.item.ItemStack;

import java.util.List;
import java.util.Objects;

public class RecipeEntryAetherMachine extends RecipeEntryBase<RecipeSymbol, ItemStack, Integer> implements HasJsonAdapter {

    public RecipeEntryAetherMachine(RecipeSymbol input, ItemStack output, Integer time) {
        super(input, output, time);
    }

    @SuppressWarnings("unused")
    public RecipeEntryAetherMachine() {}


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

    public boolean matchesQuery(SearchQuery query) {
        switch (query.mode) {
            case ALL: {
                if ((matchesRecipe(query) || matchesUsage(query)) && matchesScope(query)) return true;
                break;
            }
            case RECIPE: {
                if (matchesRecipe(query) && matchesScope(query)) return true;
                break;
            }
            case USAGE: {
                if (matchesUsage(query) && matchesScope(query)) return true;
                break;
            }
        }
        return false;
    }

    public boolean matchesScope(SearchQuery query) {
        if (query.scope.getLeft() == SearchQuery.SearchScope.NONE) return true;
        if (query.scope.getLeft() == SearchQuery.SearchScope.NAMESPACE) {
            RecipeNamespace namespace = Registries.RECIPES.getItem(query.scope.getRight());
            return namespace == parent.getParent();
        } else if (query.scope.getLeft() == SearchQuery.SearchScope.NAMESPACE_GROUP) {
            RecipeGroup<?> group;
            try {
                group = Registries.RECIPES.getGroupFromKey(query.scope.getRight());
            } catch (IllegalArgumentException e) {
                group = null;
            }
            return group == parent;
        }
        return false;
    }

    public boolean matchesRecipe(SearchQuery query) {
        if (query.query.getLeft() == SearchQuery.QueryType.NAME) {
            if (query.strict && this.getOutput().getDisplayName().equalsIgnoreCase(query.query.getRight())) {
                return true;
            }
            return !query.strict && this.getOutput().getDisplayName().toLowerCase().contains(query.query.getRight().toLowerCase());
        } else if (query.query.getLeft() == SearchQuery.QueryType.GROUP && !Objects.equals(query.query.getRight(), "")) {
            List<ItemStack> groupStacks = new RecipeSymbol(query.query.getRight()).resolve();
            if (groupStacks == null) return false;
            return groupStacks.contains(getOutput());
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
            } else if (query.query.getLeft() == SearchQuery.QueryType.GROUP && !Objects.equals(query.query.getRight(), "")) {
                List<ItemStack> groupStacks = new RecipeSymbol(query.query.getRight()).resolve();
                if (groupStacks == null) return false;
                return groupStacks.contains(getOutput());
            }
        }
        return false;
    }


    @Override
    public RecipeJsonAdapter<?> getAdapter() {
        return new RecipeAetherMachineJsonAdapter();
    }

}
