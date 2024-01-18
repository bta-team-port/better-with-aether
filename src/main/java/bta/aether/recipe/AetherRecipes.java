package bta.aether.recipe;

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

import static net.minecraft.core.block.BlockMoss.stoneToMossMap;

public class AetherRecipes implements RecipeEntrypoint {
    public static final RecipeNamespace AETHER = new RecipeNamespace();
    public static final RecipeGroup<RecipeEntryCrafting<?, ?>> WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.workbench)));
    public static final RecipeGroup<RecipeEntryFurnace> FURNACE = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.furnaceStoneIdle)));
    public static final RecipeGroup<RecipeEntryTrommel> TROMMEL = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.trommelActive)));
    public static final RecipeGroupAether ENCHANTER = new RecipeGroupAether(new RecipeSymbol(new ItemStack(AetherBlocks.enchanter)));
    public static final RecipeGroupAether FREEZER = new RecipeGroupAether(new RecipeSymbol(new ItemStack(AetherBlocks.freezer)));
    @Override
    public void onRecipesReady() {
        AETHER.register("workbench", WORKBENCH);
        AETHER.register("furnace", FURNACE);
        AETHER.register("trommel", TROMMEL);
        AETHER.register("enchanter",ENCHANTER);
        AETHER.register("freezer",FREEZER);
        Registries.RECIPES.register("aether", AETHER);
        Registries.RECIPE_TYPES.register("aether:machine",RecipeEntryAetherMachine.class);

        Registries.ITEM_GROUPS.register("aether:ambrosium_ores", Registries.stackListOf(new ItemStack(AetherBlocks.oreAmbrosiumHolystone, 1)));
        Registries.ITEM_GROUPS.register("aether:zanite_ores", Registries.stackListOf(new ItemStack(AetherBlocks.oreZaniteHolystone, 1)));
        Registries.ITEM_GROUPS.register("aether:gravitite_ores", Registries.stackListOf(new ItemStack(AetherBlocks.oreGravititeHolystone, 1)));

        Registries.ITEM_GROUPS.register("aether:blue_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 11), new ItemStack(Block.wool, 1, 3), new ItemStack(Block.wool, 1, 9)));
        Registries.ITEM_GROUPS.register("aether:white_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 0), new ItemStack(Block.wool, 1, 8)));
        Registries.ITEM_GROUPS.register("aether:red_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 14), new ItemStack(Block.wool, 1, 6)));
        Registries.ITEM_GROUPS.register("aether:green_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 5), new ItemStack(Block.wool, 1, 13)));
        Registries.ITEM_GROUPS.register("aether:purple_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 2), new ItemStack(Block.wool, 1, 10)));
        Registries.ITEM_GROUPS.register("aether:orange_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 1), new ItemStack(Block.wool, 1, 4), new ItemStack(Block.wool, 1, 12)));
        Registries.ITEM_GROUPS.register("aether:black_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 7), new ItemStack(Block.wool, 1, 15)));

        Registries.ITEM_GROUPS.getItem("minecraft:logs").add(AetherBlocks.logSkyroot.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:logs").add(AetherBlocks.logOakGolden.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:leaves").add(AetherBlocks.leavesSkyroot.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:leaves").add(AetherBlocks.leavesOakGolden.getDefaultStack());

        DataLoader.loadRecipes("/assets/aether/recipes/workbench.json");
        DataLoader.loadRecipes("/assets/aether/recipes/workbench-shapeless.json");
        DataLoader.loadRecipes("/assets/aether/recipes/workbench-tools.json");
        DataLoader.loadRecipes("/assets/aether/recipes/workbench-armor.json");
        DataLoader.loadRecipes("/assets/aether/recipes/furnace.json");
        DataLoader.loadRecipes("/assets/aether/recipes/trommel.json");
        DataLoader.loadRecipes("/assets/aether/recipes/enchanter.json");
        DataLoader.loadRecipes("/assets/aether/recipes/freezer.json");

        stoneToMossMap.put(AetherBlocks.holystone, AetherBlocks.holystoneMossy);

    }
}
