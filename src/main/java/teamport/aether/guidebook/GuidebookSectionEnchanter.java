package teamport.aether.guidebook;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.guidebook.GuidebookPage;
import net.minecraft.client.gui.guidebook.GuidebookSection;
import net.minecraft.client.gui.guidebook.SearchableGuidebookSection;
import net.minecraft.client.gui.guidebook.SlotGuidebook;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.SearchQuery;
import net.minecraft.core.item.*;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.util.collection.Pair;
import net.minecraft.core.util.helper.MathHelper;
import teamport.aether.AetherRecipes;
import teamport.aether.recipe.RecipeEntryAetherMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Environment(EnvType.CLIENT)
public class GuidebookSectionEnchanter extends SearchableGuidebookSection {

    public final List<GuidebookPage> pages = new ArrayList<>();
    public Pair<String, List<GuidebookPage>> filteredPages = null;

    public GuidebookSectionEnchanter(String translationKey, ItemStack tabIcon, int bgColor, int fgColor) {
        super(translationKey, tabIcon, bgColor, fgColor);
        this.reloadRecipes();
    }

    public List<GuidebookPage> searchPages(SearchQuery query) {
        if (this.filteredPages != null && Objects.equals(this.filteredPages.getLeft(), query.rawQuery)) {
            return this.filteredPages.getRight();
        } else {
            ArrayList<RecipeEntryAetherMachine> filteredRecipes = new ArrayList<>();
            List<RecipeEntryAetherMachine> allRecipes = new ArrayList<>(AetherRecipes.ENCHANTER.getAllRecipes());
            allRecipes.removeIf(Objects::isNull);

            for (RecipeEntryAetherMachine recipe : allRecipes) {
                if (recipe.matchesQueryIgnoreExceptions(query)) {
                    filteredRecipes.add(recipe);
                }
            }

            ArrayList<GuidebookPage> filteredPages = new ArrayList<>();
            int filteredRecipeSize = filteredRecipes.size();
            int filteredPageCount = MathHelper.ceilInt(filteredRecipeSize, 3);

            for (int i = 0; i < filteredPageCount; ++i) {
                int j = i * 6;
                ArrayList<RecipeEntryAetherMachine> recipes = new ArrayList<>(filteredRecipes.subList(Math.min(j, filteredRecipeSize), Math.min(j + 6, filteredRecipeSize)));
                if (!recipes.isEmpty()) {
                    filteredPages.add(new RecipePageEnchanting(this, recipes));
                }
            }

            this.filteredPages = Pair.of(query.rawQuery, filteredPages);
            return filteredPages;
        }
    }

    public void reloadRecipes() {
        this.pages.clear();
        List<RecipeEntryAetherMachine> allRecipes = new ArrayList<>(AetherRecipes.ENCHANTER.getAllRecipes());
        allRecipes.removeIf(Objects::isNull);
        int totalRecipes = allRecipes.size();
        int totalPages = MathHelper.ceilInt(totalRecipes, 6);

        // TODO collect all repairs to one recipe somehow
//        for(int i = 0; i < totalRecipes; i++){
//            RecipeEntryAetherMachine recipe = allRecipes.get(i);
//            ItemStack input = recipe.getInput().getStack();
//            ItemStack output = recipe.getOutput();
//            if(isRepairable(input)
//               && isRepairable(output)
//               && input.itemID == output.itemID
//            ){
//
//            }
//        }

        for (int i = 0; i < totalPages; ++i) {
            int j = i * 6;
            ArrayList<RecipeEntryAetherMachine> recipes = new ArrayList<>(allRecipes.subList(Math.min(j, totalRecipes), Math.min(j + 6, totalRecipes)));
            this.pages.add(new RecipePageEnchanting(this, recipes));
        }

    }

    public List<GuidebookPage> getPages() {
        return this.pages;
    }

    public List<GuidebookSection.Index> getIndices() {
        return null;
    }

    public static boolean isRepairable(ItemStack toProcess) {
        Item item = toProcess.getItem();
        return item instanceof ItemTool
                || item instanceof ItemArmor
                || item instanceof ItemFireStriker
                || item instanceof ItemBow;
    }

//    ItemStack input = recipe.getInput().getStack();
//    ItemStack output = recipe.getOutput();
//            if (
//    isRepairable(input)
//                 && isRepairable(output)
//                 && output.itemID == input.itemID
//            )
//    {
//        Random rand = new Random();
//        int damage = Math.round(input.getMaxDamage() * rand.nextFloat());
//        input.setMetadata(damage);
//        recipeSlots.add(new SlotGuidebook(0, 47, 32 * (this.map.size() + 1) - 16, new RecipeSymbol(input), false, recipe));
//        recipeSlots.add(new SlotGuidebook(1, 103, 32 * (this.map.size() + 1) - 16, new RecipeSymbol((ItemStack) output), false, recipe));
//        this.map.put(recipe, recipeSlots);

//        this.slots.addAll(recipeSlots);


}

