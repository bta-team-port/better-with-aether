package teamport.aether.guidebook;

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
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class GuidebookSectionFreezer extends SearchableGuidebookSection {

    public final List<GuidebookPage> pages = new ArrayList<>();
    public Pair<String, List<GuidebookPage>> filteredPages = null;

    public GuidebookSectionFreezer(String translationKey, ItemStack tabIcon, int bgColor, int fgColor) {
        super(translationKey, tabIcon, bgColor, fgColor);
        this.reloadRecipes();
    }

    public List<GuidebookPage> searchPages(SearchQuery query) {
        if (this.filteredPages != null && Objects.equals(this.filteredPages.getLeft(), query.rawQuery)) {
            return (List) this.filteredPages.getRight();
        } else {
            ArrayList<RecipeEntryAetherMachine> filteredRecipes = new ArrayList<>();
            List<RecipeEntryAetherMachine> allRecipes = new ArrayList<>(AetherRecipes.FREEZER.getAllRecipes());
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
                    filteredPages.add(new RecipePageFreezer(this, recipes));
                }
            }

            this.filteredPages = Pair.of(query.rawQuery, filteredPages);
            return filteredPages;
        }
    }

    public void reloadRecipes() {
        this.pages.clear();
        List<RecipeEntryAetherMachine> allRecipes = new ArrayList<>(AetherRecipes.FREEZER.getAllRecipes());
        allRecipes.removeIf(Objects::isNull);
        int totalRecipes = allRecipes.size();
        int totalPages = MathHelper.ceilInt(totalRecipes, 6);

        for (int i = 0; i < totalPages; ++i) {
            int j = i * 6;
            ArrayList<RecipeEntryAetherMachine> recipes = new ArrayList<>(allRecipes.subList(Math.min(j, totalRecipes), Math.min(j + 6, totalRecipes)));
            this.pages.add(new RecipePageFreezer(this, recipes));
        }

    }

    public List<GuidebookPage> getPages() {
        return this.pages;
    }

    public List<Index> getIndices() {
        return null;
    }
}

