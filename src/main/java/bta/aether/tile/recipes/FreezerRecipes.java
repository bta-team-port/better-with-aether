package bta.aether.tile.recipes;

import bta.aether.Aether;
import bta.aether.block.AetherBlocks;
import bta.aether.item.AetherItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.Pair;

import java.util.HashMap;
import java.util.Map;

public class FreezerRecipes {
    protected static final HashMap<ItemStack, Pair<ItemStack,Integer>> recipeList = new HashMap<>();

    public static HashMap<ItemStack, Pair<ItemStack,Integer>> getRecipeList() {
        return recipeList;
    }

    public static void addRecipe(ItemStack input, ItemStack output, int time) {
        recipeList.put(input,Pair.of(output,time));
    }

    public static ItemStack getResult(ItemStack input) {
        for (Map.Entry<ItemStack, Pair<ItemStack, Integer>> entry : recipeList.entrySet()) {
            ItemStack K = entry.getKey();
            ItemStack V = entry.getValue().getLeft();
            if (K.itemID == input.itemID) {
                return V.copy();
            }
        }
        return null;
    }

    public static int getTime(ItemStack input){
        for (Map.Entry<ItemStack, Pair<ItemStack, Integer>> entry : recipeList.entrySet()) {
            ItemStack K = entry.getKey();
            int V = entry.getValue().getRight();
            if (K.itemID == input.itemID) {
                return V;
            }
        }
        return 0;
    }

    static {
        addRecipe(new ItemStack(Item.bucketWater,1),new ItemStack(Block.ice,5),500);
        addRecipe(new ItemStack(AetherItems.bucketSkyrootWater,1),new ItemStack(Block.ice,5),500);
        addRecipe(new ItemStack(Item.bucketLava,1),new ItemStack(Block.obsidian,2),500);
        addRecipe(new ItemStack(AetherBlocks.aercloudWhite,1),new ItemStack(AetherBlocks.aercloudBlue,1),50);
        addRecipe(new ItemStack(AetherItems.armorPendantGold,1),new ItemStack(AetherItems.armorPendantIce,1),2500);
        addRecipe(new ItemStack(AetherItems.armorRingGold,1),new ItemStack(AetherItems.armorRingIce,1),1500);
        addRecipe(new ItemStack(AetherItems.armorRingIron,1),new ItemStack(AetherItems.armorPendantIce,1),1500);
        addRecipe(new ItemStack(AetherItems.armorPendantIron,1),new ItemStack(AetherItems.armorPendantIce,1),2500);
        addRecipe(new ItemStack(Block.stone,1),new ItemStack(Block.permafrost,1),500);
    }
}
