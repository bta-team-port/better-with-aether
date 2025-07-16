package teamport.aether.data;

import net.minecraft.core.data.registry.recipe.HasJsonAdapter;
import net.minecraft.core.data.registry.recipe.RecipeEntryBase;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.adapter.RecipeJsonAdapter;
import net.minecraft.core.item.ItemStack;

// TODO implement the RecipeEntry for enchanter
public class RecipeEntryEnchanter extends RecipeEntryBase<RecipeSymbol, ItemStack, Void> implements HasJsonAdapter {

    public RecipeEntryEnchanter(){}

    @Override
    public RecipeJsonAdapter<?> getAdapter() {
        return null;
    }
}
