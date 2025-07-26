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
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class GuidebookIncubatorSection extends SearchableGuidebookSection {
    public final List<GuidebookPage> pages = new ArrayList<>();
    public Pair<String, List<GuidebookPage>> filteredPages = null;
    public static int entryPerPage = 3;

    public GuidebookIncubatorSection(String translationKey, ItemStack tabIcon, int bgColor, int fgColor) {
        super(translationKey, tabIcon, bgColor, fgColor);
        this.reloadRecipes();
    }

    public void reloadRecipes() {
        this.pages.clear();
        List<RecipeEntryIncubator> allRecipes = new ArrayList<>(AetherRecipes.INCUBATOR.getAllRecipes());
        allRecipes.removeIf(Objects::isNull);
        int totalRecipes = allRecipes.size();
        int totalPages = MathHelper.ceilInt(totalRecipes, entryPerPage);
        for(int i = 0; i < totalPages; ++i) {
            int j = i * entryPerPage;
            List<RecipeEntryIncubator> recipes = new ArrayList<>(allRecipes.subList(Math.min(j, totalRecipes), Math.min(j + entryPerPage, totalRecipes)));
            this.pages.add(new RecipePageIncubator(this, recipes));
        }
    }

    @Override
    public List<GuidebookPage> searchPages(SearchQuery searchQuery) {
        if (this.filteredPages != null && Objects.equals(this.filteredPages.getLeft(), searchQuery.rawQuery)) {
            return this.filteredPages.getRight();
        }
        List<GuidebookPage> filteredPages = new ArrayList<>();
        List<RecipeEntryIncubator> allRecipes = new ArrayList<>(AetherRecipes.INCUBATOR.getAllRecipes());
        List<RecipeEntryIncubator> filteredRecipes = new ArrayList<>();
        allRecipes.removeIf(Objects::isNull);

        for (RecipeEntryIncubator recipe : allRecipes) {
            if (recipe.matchesQueryIgnoreExceptions(searchQuery)) {
                filteredRecipes.add(recipe);
            }
        }

        int filteredRecipeSize = filteredRecipes.size();
        int filteredPageCount = MathHelper.ceilInt(filteredRecipeSize, entryPerPage);
        for(int i = 0; i < filteredPageCount; i++){
            int j = i * entryPerPage;
            List<RecipeEntryIncubator> recipes = new ArrayList<>(filteredRecipes.subList(Math.min(j, filteredRecipeSize), Math.min(j + entryPerPage, filteredRecipeSize)));
            if(!recipes.isEmpty()){
                filteredPages.add(new RecipePageIncubator(this, recipes));
            }
        }
        this.filteredPages = Pair.of(searchQuery.rawQuery, filteredPages);
        return filteredPages;
    }

    @Override
    public List<GuidebookPage> getPages() {
       return this.pages;
    }

    @Override
    public List<Index> getIndices() {
        return null;
    }
}
