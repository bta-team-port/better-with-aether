package teamport.aether.gui.guidebook.incubator;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.guidebook.GuidebookPage;
import net.minecraft.client.gui.guidebook.SearchableGuidebookSection;
import net.minecraft.core.data.registry.recipe.SearchQuery;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.Pair;
import net.minecraft.core.util.helper.MathHelper;
import teamport.aether.AetherRecipes;
import teamport.aether.recipe.RecipeEntryIncubator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class GuidebookIncubatorSection extends SearchableGuidebookSection {
    public final List<GuidebookPage> pages = new ArrayList<>();
    private Pair<String, List<GuidebookPage>> filteredPages = null;
    private static final int ENTRY_PER_PAGE = 3;

    public GuidebookIncubatorSection(String translationKey, ItemStack tabIcon, int bgColor, int fgColor) {
        super(translationKey, tabIcon, bgColor, fgColor);
        this.reloadRecipes();
    }

    public void reloadRecipes() {
        this.pages.clear();
        List<RecipeEntryIncubator> allRecipes = new ArrayList<>(AetherRecipes.INCUBATOR.getAllRecipes());
        allRecipes.removeIf(Objects::isNull);
        int totalRecipes = allRecipes.size();
        int totalPages = MathHelper.ceilInt(totalRecipes, ENTRY_PER_PAGE);
        for (int i = 0; i < totalPages; ++i) {
            int j = i * ENTRY_PER_PAGE;
            List<RecipeEntryIncubator> recipes = new ArrayList<>(allRecipes.subList(Math.min(j, totalRecipes), Math.min(j + ENTRY_PER_PAGE, totalRecipes)));
            this.pages.add(new RecipePageIncubator(this, recipes));
        }
    }

    @Override
    public List<GuidebookPage> searchPages(SearchQuery searchQuery) {
        if (this.filteredPages != null && Objects.equals(this.filteredPages.getLeft(), searchQuery.rawQuery)) {
            return this.filteredPages.getRight();
        }
        List<GuidebookPage> theFilteredPages = new ArrayList<>();
        List<RecipeEntryIncubator> allRecipes = new ArrayList<>(AetherRecipes.INCUBATOR.getAllRecipes());
        List<RecipeEntryIncubator> filteredRecipes = new ArrayList<>();
        allRecipes.removeIf(Objects::isNull);

        for (RecipeEntryIncubator recipe : allRecipes) {
            if (recipe.matchesQueryIgnoreExceptions(searchQuery)) {
                filteredRecipes.add(recipe);
            }
        }

        int filteredRecipeSize = filteredRecipes.size();
        int filteredPageCount = MathHelper.ceilInt(filteredRecipeSize, ENTRY_PER_PAGE);
        for (int i = 0; i < filteredPageCount; i++) {
            int j = i * ENTRY_PER_PAGE;
            List<RecipeEntryIncubator> recipes = new ArrayList<>(filteredRecipes.subList(Math.min(j, filteredRecipeSize), Math.min(j + ENTRY_PER_PAGE, filteredRecipeSize)));
            if (!recipes.isEmpty()) {
                theFilteredPages.add(new RecipePageIncubator(this, recipes));
            }
        }
        this.filteredPages = Pair.of(searchQuery.rawQuery, theFilteredPages);
        return theFilteredPages;
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
