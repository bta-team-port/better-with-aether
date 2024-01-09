package bta.aether.recipe;

import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.*;
import net.minecraft.core.data.registry.recipe.adapter.RecipeJsonAdapter;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;

import java.util.List;
import java.util.Objects;

//the integer data parameter is the time it takes for an item to process
public class RecipeEntryAetherMachine extends RecipeEntryBase<RecipeSymbol, ItemStack, Integer> implements HasJsonAdapter {

    public RecipeEntryAetherMachine(RecipeSymbol input, ItemStack output, Integer data) {
        super(input, output, data);
    }

    public RecipeEntryAetherMachine() {
    }
    public boolean matches(ItemStack stack) {
        return getInput().matches(stack);
    }
    public boolean matchesQuery(SearchQuery query) {
        switch (query.mode){
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
        if(query.scope.getLeft() == SearchQuery.SearchScope.NONE) return true;
        if(query.scope.getLeft() == SearchQuery.SearchScope.NAMESPACE) {
            RecipeNamespace namespace = Registries.RECIPES.getItem(query.scope.getRight());
            if(namespace == parent.getParent()){
                return true;
            }
        } else if (query.scope.getLeft() == SearchQuery.SearchScope.NAMESPACE_GROUP) {
            RecipeGroup<?> group;
            try {
                group = Registries.RECIPES.getGroupFromKey(query.scope.getRight());
            } catch (IllegalArgumentException e){
                group = null;
            }
            if(group == parent){
                return true;
            }
        }
        return false;
    }

    public boolean matchesRecipe(SearchQuery query) {
        if(query.query.getLeft() == SearchQuery.QueryType.NAME) {
            if(query.strict && getOutput().getDisplayName().equalsIgnoreCase(query.query.getRight())){
                return true;
            } else if(!query.strict &&getOutput().getDisplayName().toLowerCase().contains(query.query.getRight().toLowerCase())){
                return true;
            }
        } else if (query.query.getLeft() == SearchQuery.QueryType.GROUP && !Objects.equals(query.query.getRight(), "")) {
            List<ItemStack> groupStacks = new RecipeSymbol(query.query.getRight()).resolve();
            if(groupStacks == null) return false;
            return groupStacks.contains(getOutput());
        }
        return false;
    }

    public boolean matchesUsage(SearchQuery query) {
        List<ItemStack> stacks = getInput().resolve();
        for (ItemStack stack : stacks) {
            if (stack == null) continue;
            if(query.query.getLeft() == SearchQuery.QueryType.NAME) {
                if(query.strict && stack.getDisplayName().equalsIgnoreCase(query.query.getRight())){
                    return true;
                } else if(!query.strict && stack.getDisplayName().toLowerCase().contains(query.query.getRight().toLowerCase())){
                    return true;
                }
            } else if (query.query.getLeft() == SearchQuery.QueryType.GROUP && !Objects.equals(query.query.getRight(), "")) {
                List<ItemStack> groupStacks = new RecipeSymbol(query.query.getRight()).resolve();
                if(groupStacks == null) return false;
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
