package teamport.aether.gui.guidebook.incubator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ItemElement;
import net.minecraft.client.gui.TooltipElement;
import net.minecraft.client.gui.guidebook.*;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.gui.guidebook.search.GuidebookPageSearch;
import net.minecraft.client.option.enums.DescriptionPromptEnum;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.TextureManager;
import net.minecraft.core.data.registry.recipe.SearchQuery;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.slot.Slot;
import org.jspecify.annotations.NonNull;
import org.lwjgl.opengl.GL11;
import teamport.aether.AetherMod;
import teamport.aether.recipe.RecipeEntryIncubator;

import java.util.*;

public class RecipePageIncubator extends RecipePage<RecipeEntryIncubator> {

    private final List<SlotGuidebook> slots;
    private final TooltipElement tooltipElement;
    private final ItemElement itemElement;
    private final Map<RecipeEntryIncubator, SlotGuidebook> map;
    private static final Minecraft mc = Minecraft.getMinecraft();

    public RecipePageIncubator(GuidebookIncubatorSection section, List<RecipeEntryIncubator> recipes) {
        super(section);
        this.recipes = recipes;
        this.slots = new ArrayList<>();
        this.tooltipElement = new TooltipElement(mc);
        this.itemElement = new ItemElement(mc);
        this.map = new HashMap<>();
        buildSlots(recipes);
    }

    public void buildSlots(List<RecipeEntryIncubator> recipes) {
        for (RecipeEntryIncubator recipe : recipes) {
            int yOffset = 32 * (this.map.size() + 1) - 16;
            SlotGuidebook recipeSlot = new SlotGuidebook(0, 20, 2 * yOffset, recipe.getInput(), false, recipe);
            this.map.put(recipe, recipeSlot);
            this.slots.add(recipeSlot);
        }
    }

    @Override
    public void renderForeground(TextureManager re, Font fr, int x, int y, int mouseX, int mouseY, float partialTicks) {
        if (this.recipes.isEmpty()) {
            this.drawStringCenteredNoShadow(fr, I18n.getInstance().translateKey("guidebook.section.search.error.no_recipes"), x + 79, y + 110, -8355712);
        }

        SlotGuidebook mouseOverSlot = null;
        for (RecipeEntryIncubator recipe : recipes) {
            SlotGuidebook slot = map.get(recipe);
            this.drawSlot(x + slot.x - 1, y + slot.y - 1, -1);
            if (this.getIsMouseOverSlot(slot, x, y, mouseX, mouseY)) {
                mouseOverSlot = slot;
            }

            String[] substrings = recipe.getOutput().getEntity().split(":");
            String entityName = substrings.length > 1 ? substrings[1] : substrings[0];
            String title = getEntityTitle(recipe);

            StringBuilder buildTime = new StringBuilder();
            int time = Math.round(recipe.getData() / 20.0F);
            if (time >= 60) {
                time = Math.round(time / 60.0f);
                buildTime.append(time).append(" min");
            } else {
                buildTime.append(time).append(" sec");
            }
            String duration = buildTime.toString();
            String[] description;


            boolean discovered = slot.getIsDiscovered(mc.thePlayer);
            if (discovered) {
                description = createDescLines(fr, "aether.guidebook.section.incubator." + entityName);
            } else {
                description = createDescLines(fr, "aether.guidebook.section.incubator.undiscovered");
                title = (new String(new char[title.length()])).replace("\u0000", "?");
                duration = (new String(new char[duration.length()])).replace("\u0000", "?");
            }

            int yOffset = y + slot.y - 1 - 10;
            int xOffset = x + slot.x - 1 + 30;
            this.drawStringNoShadow(fr, title, xOffset, yOffset, 0);
            yOffset += 10;
            this.drawStringNoShadow(fr, "Duration: " + duration, xOffset, yOffset, 0);
            yOffset += 10;
            for (String descLine : description) {
                this.drawStringNoShadow(fr, descLine, xOffset, yOffset, 5263440);
                yOffset += 10;
            }
            this.itemElement.render(slot.getItemStack(), x + slot.x, y + slot.y, mouseOverSlot == slot, slot);
        }
    }

    private static @NonNull String getEntityTitle(RecipeEntryIncubator recipe) {
        Class<? extends Entity> entity = EntityDispatcher.classForId(recipe.getOutput().getEntity());
        MobInfoRegistry.MobInfo mobInfo = MobInfoRegistry.getMobInfo(entity);
        String translationKey = mobInfo.getNameTranslationKey();
        return "Hatch " + AetherMod.TRANSLATOR.translateKey(translationKey);
    }

    public boolean getIsMouseOverSlot(Slot slot, int x, int y, int mouseX, int mouseY) {
        return mouseX >= x + slot.x - 1 && mouseX < x + slot.x + 16 + 1 && mouseY >= y + slot.y - 1 && mouseY < y + slot.y + 16 + 1;
    }

    @Override
    public void renderOverlay(TextureManager re, Font fr, int x, int y, int mouseX, int mouseY, float partialTicks) {
        super.renderOverlay(re, fr, x, y, mouseX, mouseY, partialTicks);
        SlotGuidebook mouseOverSlot = null;

        for (SlotGuidebook slot : this.slots) {
            if (this.getIsMouseOverSlot(slot, x, y, mouseX, mouseY)) {
                mouseOverSlot = slot;
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            if (mouseOverSlot != null && mouseOverSlot.hasItem()) {
                boolean showDescription = DescriptionPromptEnum.showDescription(mc);
                String str = this.tooltipElement.getTooltipText(mouseOverSlot.getItemStack(), showDescription, mouseOverSlot);
                if (!str.isEmpty()) {
                    this.tooltipElement.render(str, mouseX, mouseY, 8, -8);
                }
            }
        }
    }

    public static String[] createDescLines(Font fr, String languageKey) {
        String[] words = I18n.getInstance().translateKey(languageKey).split(" ");
        List<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (fr.getStringWidth(line + " " + word) > 100) {
                lines.add(line.toString());
                line = new StringBuilder();
            }

            if (word.contains("\n")) {
                String safeWord = word.replace("\r", "");
                String[] wordParts = safeWord.split("\n");

                for (int i = 0; i < wordParts.length; ++i) {
                    if (i > 0) {
                        lines.add(line.toString());
                        line = new StringBuilder();
                    }

                    line.append(wordParts[i]).append(" ");
                }
            } else {
                line.append(word).append(" ");
            }
        }

        lines.add(line.toString());
        return lines.toArray(new String[0]);
    }

    @Override
    public boolean keyTyped(char c, int key, int x, int y, int mouseX, int mouseY) {
        super.keyTyped(c, key, x, y, mouseX, mouseY);
        if (mc.gameSettings.keyShowRecipe.isKeyboardKey(key)) {
            SlotGuidebook hoveringSlot = null;

            for (SlotGuidebook slot : this.slots) {
                if (this.getIsMouseOverSlot(slot, x, y, mouseX, mouseY)) {
                    hoveringSlot = slot;
                }
            }

            if (hoveringSlot != null && hoveringSlot.hasItem()) {
                String query = "r:" + Objects.requireNonNull(hoveringSlot.getItemStack()).getDisplayName() + "!";
                GuidebookPageManager.searchQuery = SearchQuery.resolve(query);
                GuidebookPageSearch.searchField.setText(query);
                ScreenGuidebook.getPageManager().updatePages();
                ScreenGuidebook.getPageManager().setCurrentPage(ScreenGuidebook.getPageManager().getSectionIndex(GuidebookSections.CRAFTING), true);
                return true;
            }
        } else if (mc.gameSettings.keyShowUsage.isKeyboardKey(key)) {
            SlotGuidebook hoveringSlot = null;

            for (SlotGuidebook slot : this.slots) {
                if (this.getIsMouseOverSlot(slot, x, y, mouseX, mouseY)) {
                    hoveringSlot = slot;
                }
            }

            if (hoveringSlot != null && hoveringSlot.hasItem()) {
                String query = "u:" + Objects.requireNonNull(hoveringSlot.getItemStack()).getDisplayName() + "!";
                GuidebookPageManager.searchQuery = SearchQuery.resolve(query);
                GuidebookPageSearch.searchField.setText(query);
                ScreenGuidebook.getPageManager().updatePages();
                ScreenGuidebook.getPageManager().setCurrentPage(ScreenGuidebook.getPageManager().getSectionIndex(GuidebookSections.CRAFTING), true);
                return true;
            }
        }

        return false;
    }

}
