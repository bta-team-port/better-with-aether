package teamport.aether.item.item_tool.item_tool_phoenix;

import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryBlastFurnace;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryFurnace;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import teamport.aether.AetherRecipes;

import java.util.ArrayList;
import java.util.List;

public class PhoenixHelper {

	private PhoenixHelper(){}

	public static ItemStack[] smeltItemResults(
		Player player, ItemStack[] drops
	) {
		ItemStack heldItem = player.getHeldItem();
		if(heldItem == null || drops == null || drops.length == 0){
			return drops;
		}
		int durabilityDamage = 0;
		int durabilityLeft = heldItem.getMetadata();
		List<ItemStack> results = new ArrayList<>();
		for(ItemStack currentDrop: drops){
			if(durabilityLeft > durabilityDamage){
				ItemStack result = matchRecipe(currentDrop);
				if(result.itemID != currentDrop.itemID){
					durabilityDamage += result.stackSize;
				}
				results.add(result);
			}else{
				results.add(currentDrop);
			}
		}
		return results.toArray(new ItemStack[0]);
	}

	private static ItemStack matchRecipe(ItemStack currentDrop) {
		List<RecipeEntryFurnace> furnaceRecipes = Registries.RECIPES.getAllFurnaceRecipes();
		for(RecipeEntryFurnace recipeEntryBase : furnaceRecipes) {
			if (recipeEntryBase != null && recipeEntryBase.matches(currentDrop)) {
				return recipeEntryBase.getOutput().copy();
			}
		}

		List<RecipeEntryBlastFurnace> blastFurnaceRecipes = Registries.RECIPES.getAllBlastFurnaceRecipes();
		for(RecipeEntryBlastFurnace recipeEntryBase : blastFurnaceRecipes) {
			if (recipeEntryBase != null && recipeEntryBase.matches(currentDrop, null)) {
				return recipeEntryBase.getOutput().copy();
			}
		}

		ItemStack resultStack = AetherRecipes.ENCHANTER.findOutput(currentDrop);
		return resultStack == null ? currentDrop: resultStack;
	}
}
