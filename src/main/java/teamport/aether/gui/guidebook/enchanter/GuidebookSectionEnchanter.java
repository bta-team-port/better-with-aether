package teamport.aether.gui.guidebook.enchanter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.guidebook.GuidebookPage;
import net.minecraft.client.gui.guidebook.GuidebookSection;
import net.minecraft.client.gui.guidebook.SearchableGuidebookSection;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.SearchQuery;
import net.minecraft.core.item.*;
import net.minecraft.core.util.collection.Pair;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.NotNull;
import teamport.aether.AetherRecipes;
import teamport.aether.recipe.RecipeEntryAetherMachine;

import java.util.*;

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
        }
        List<RecipeEntryAetherMachine> filteredRecipes = new ArrayList<>();
        List<RecipeEntryAetherMachine> allRecipes = new ArrayList<>(AetherRecipes.ENCHANTER.getAllRecipes());
        allRecipes.removeIf(Objects::isNull);

        filterRecipe(query, allRecipes, filteredRecipes);
        filteredRecipes = moveRepairablesToBack(filteredRecipes);


        List<GuidebookPage> filteredPages = new ArrayList<>();
        int filteredRecipeSize = filteredRecipes.size();
        int filteredPageCount = MathHelper.ceilInt(filteredRecipeSize, 3);

        for (int i = 0; i < filteredPageCount; ++i) {
            int j = i * 6;
            List<RecipeEntryAetherMachine> recipes = new ArrayList<>(filteredRecipes.subList(Math.min(j, filteredRecipeSize), Math.min(j + 6, filteredRecipeSize)));
            if (!recipes.isEmpty()) {
                filteredPages.add(new RecipePageEnchanting(this, recipes));
            }
        }
        this.filteredPages = Pair.of(query.rawQuery, filteredPages);
        return filteredPages;
    }


    public static List<RecipeEntryAetherMachine> moveRepairablesToBack(List<RecipeEntryAetherMachine> recipes) {
        List<RecipeEntryAetherMachine> new_recipes = new ArrayList<>(recipes.size());
        List<RecipeEntryAetherMachine> repairable = new ArrayList<>();

        // collect all repairable to separate list
        for(RecipeEntryAetherMachine recipe : recipes){
            ItemStack input = recipe.getInput().getStack();
            ItemStack output = recipe.getOutput();
            if(
                    input != null
                    && output != null
                    && input.isItemStackDamageable()
                    && output.isItemStackDamageable()
                    && output.itemID == input.itemID
            ){
//                recipe = new RecipeEntryAetherMachine(getDamagedVariety(recipe), recipe.getOutput(), recipe.getData());
                repairable.add(recipe);
            }else{
                new_recipes.add(recipe);
            }
        }

        // sort the recipe
        repairable.sort(new Comparator<RecipeEntryAetherMachine>() {
            @Override
            public int compare(RecipeEntryAetherMachine self, RecipeEntryAetherMachine other) {
                return self.getInput().getStack().getDisplayName().compareTo(other.getInput().getStack().getDisplayName());
            }
        });

        new_recipes.addAll(repairable);
        return new_recipes;
    }

    public void reloadRecipes() {
        this.pages.clear();
        List<RecipeEntryAetherMachine> allRecipes = new ArrayList<>(AetherRecipes.ENCHANTER.getAllRecipes());
        allRecipes.removeIf(Objects::isNull);

        allRecipes = moveRepairablesToBack(allRecipes);

        int totalRecipes = allRecipes.size();
        int totalPages = MathHelper.ceilInt(totalRecipes, 6);
        for (int i = 0; i < totalPages; ++i) {
            int j = i * 6;
            ArrayList<RecipeEntryAetherMachine> recipes = new ArrayList<>(allRecipes.subList(Math.min(j, totalRecipes), Math.min(j + 6, totalRecipes)));
            this.pages.add(new RecipePageEnchanting(this, recipes));
        }

    }


    public static void filterRecipe(SearchQuery query, List<RecipeEntryAetherMachine> allRecipes, List<RecipeEntryAetherMachine> filteredRecipes) {
        for (RecipeEntryAetherMachine recipe : allRecipes) {
            if (recipe.matchesQueryIgnoreExceptions(query)) {
                filteredRecipes.add(recipe);
            }
        }
    }

    public List<GuidebookPage> getPages() {
        return this.pages;
    }
    public List<GuidebookSection.Index> getIndices() {
        return null;
    }
}

