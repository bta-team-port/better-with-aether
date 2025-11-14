package teamport.aether.gui.guidebook.freezer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.guidebook.GuidebookPage;
import net.minecraft.client.gui.guidebook.SearchableGuidebookSection;
import net.minecraft.core.data.registry.recipe.SearchQuery;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.Pair;
import net.minecraft.core.util.helper.MathHelper;
import teamport.aether.AetherRecipes;
import teamport.aether.recipe.RecipeEntryAetherMachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class GuidebookSectionFreezer extends SearchableGuidebookSection {

    public final List<GuidebookPage> pages = new ArrayList<>();
    private Pair<String, List<GuidebookPage>> filteredPages = null;

    public GuidebookSectionFreezer(String translationKey, ItemStack tabIcon, int bgColor, int fgColor) {
        super(translationKey, tabIcon, bgColor, fgColor);
        this.reloadRecipes();
    }

    @Override
    public List<GuidebookPage> searchPages(SearchQuery query) {
        if (this.filteredPages != null && Objects.equals(this.filteredPages.getLeft(), query.rawQuery)) {
            return this.filteredPages.getRight();
        } else {
            List<RecipeEntryAetherMachine> filteredRecipes = new ArrayList<>();
            List<RecipeEntryAetherMachine> allRecipes = new ArrayList<>(AetherRecipes.FREEZER.getAllRecipes());
            allRecipes.removeIf(Objects::isNull);

            filterRecipe(query, allRecipes, filteredRecipes);
            filteredRecipes = moveRepairablesToBack(filteredRecipes);

            List<GuidebookPage> theFilteredPages = new ArrayList<>();
            int filteredRecipeSize = filteredRecipes.size();
            int filteredPageCount = MathHelper.ceilInt(filteredRecipeSize, 6);

            for (int i = 0; i < filteredPageCount; ++i) {
                int j = i * 6;
                List<RecipeEntryAetherMachine> recipes = new ArrayList<>(filteredRecipes.subList(Math.min(j, filteredRecipeSize), Math.min(j + 6, filteredRecipeSize)));
                if (!recipes.isEmpty()) {
                    theFilteredPages.add(new RecipePageFreezer(this, recipes));
                }
            }

            this.filteredPages = Pair.of(query.rawQuery, theFilteredPages);
            return theFilteredPages;
        }
    }

    public void reloadRecipes() {
        this.pages.clear();
        List<RecipeEntryAetherMachine> allRecipes = new ArrayList<>(AetherRecipes.FREEZER.getAllRecipes());
        allRecipes.removeIf(Objects::isNull);
        int totalRecipes = allRecipes.size();
        int totalPages = MathHelper.ceilInt(totalRecipes, 6);

        allRecipes = moveRepairablesToBack(allRecipes);

        for (int i = 0; i < totalPages; ++i) {
            int j = i * 6;
            List<RecipeEntryAetherMachine> recipes = new ArrayList<>(allRecipes.subList(Math.min(j, totalRecipes), Math.min(j + 6, totalRecipes)));
            this.pages.add(new RecipePageFreezer(this, recipes));
        }

    }

    public static void filterRecipe(SearchQuery query, List<RecipeEntryAetherMachine> allRecipes, List<RecipeEntryAetherMachine> filteredRecipes) {
        for (RecipeEntryAetherMachine recipe : allRecipes) {
            if (recipe.matchesQueryIgnoreExceptions(query)) {
                filteredRecipes.add(recipe);
            }
        }
    }

    public static List<RecipeEntryAetherMachine> moveRepairablesToBack(List<RecipeEntryAetherMachine> recipes) {
        List<RecipeEntryAetherMachine> newRecipes = new ArrayList<>(recipes.size());
        List<RecipeEntryAetherMachine> repairable = new ArrayList<>();
        for (RecipeEntryAetherMachine recipe : recipes) {
            ItemStack input = recipe.getInput().getStack();
            ItemStack output = recipe.getOutput();
            if (
                input != null
                    && output != null
                    && input.isItemStackDamageable()
                    && output.isItemStackDamageable()
                    && output.itemID == input.itemID
            ) {
                repairable.add(recipe);
            } else {
                newRecipes.add(recipe);
            }
        }
        repairable.sort(Comparator.comparing(self -> self.getInput().getStack().getDisplayName()));
        newRecipes.addAll(repairable);
        return newRecipes;
    }

    @Override
    public List<GuidebookPage> getPages() {
        return this.pages;
    }

    @Override
    public List<Index> getIndices() {
        return Collections.emptyList();
    }
}

