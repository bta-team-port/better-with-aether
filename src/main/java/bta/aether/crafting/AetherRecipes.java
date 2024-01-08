package bta.aether.crafting;

import bta.aether.block.AetherBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryFurnace;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryTrommel;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class AetherRecipes implements RecipeEntrypoint {
    public static final RecipeNamespace AETHER = new RecipeNamespace();
    public static final RecipeGroup<RecipeEntryCrafting<?, ?>> WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.workbench)));
    public static final RecipeGroup<RecipeEntryFurnace> FURNACE = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.furnaceStoneIdle)));
    public static final RecipeGroup<RecipeEntryTrommel> TROMMEL = new RecipeGroup(new RecipeSymbol(new ItemStack(Block.trommelActive)));
    @Override
    public void onRecipesReady() {
        AETHER.register("workbench", WORKBENCH);
        AETHER.register("furnace", FURNACE);
        AETHER.register("trommel", TROMMEL);
        Registries.RECIPES.register("aether", AETHER);
        Registries.ITEM_GROUPS.register("aether:blue_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 11), new ItemStack(Block.wool, 1, 3), new ItemStack(Block.wool, 1, 9)));
        Registries.ITEM_GROUPS.getItem("minecraft:logs").add(AetherBlocks.logSkyroot.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:logs").add(AetherBlocks.logOakGolden.getDefaultStack());
        DataLoader.loadRecipes("/assets/aether/recipes/workbench.json");
        DataLoader.loadRecipes("/assets/aether/recipes/workbench-shapeless.json");
        DataLoader.loadRecipes("/assets/aether/recipes/workbench-tools.json");
        DataLoader.loadRecipes("/assets/aether/recipes/workbench-armor.json");
        DataLoader.loadRecipes("/assets/aether/recipes/furnace.json");
        DataLoader.loadRecipes("/assets/aether/recipes/trommel.json");
    }
}
