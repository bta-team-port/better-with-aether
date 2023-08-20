package bta.aether.tile.recipes;

import bta.aether.Aether;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.Pair;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class EnchanterRecipes {
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
        addRecipe(new ItemStack(Aether.oreGravititeHolystone,1),new ItemStack(Aether.gravititeEnchanted),1000);
    }
}
