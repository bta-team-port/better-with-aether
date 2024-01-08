package bta.aether.crafting;

import bta.aether.block.AetherBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class AetherRecipes implements RecipeEntrypoint {
    public static final RecipeNamespace AETHER = new RecipeNamespace();
    public static final RecipeGroup<RecipeEntryCrafting<?, ?>> WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.workbench)));
    @Override
    public void onRecipesReady() {
        AETHER.register("workbench", WORKBENCH);
        Registries.RECIPES.register("aether", AETHER);
//        Registries.ITEM_GROUPS.getItem("minecraft:planks").add(AetherBlocks.planksSkyroot.getDefaultStack()); Changes Recipes too much
        DataLoader.loadRecipes("/assets/aether/recipes/workbench.json");
        DataLoader.loadRecipes("/assets/aether/recipes/workbench-shapeless.json");
        DataLoader.loadRecipes("/assets/aether/recipes/workbench-tools.json");
        DataLoader.loadRecipes("/assets/aether/recipes/workbench-armor.json");
    }
}
