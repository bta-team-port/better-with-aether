package teamport.aether.gui.guidebook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ItemElement;
import net.minecraft.client.gui.TooltipElement;
import net.minecraft.client.gui.guidebook.*;
import net.minecraft.client.gui.guidebook.search.GuidebookPageSearch;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.enums.DescriptionPromptEnum;
import net.minecraft.client.render.font.FontRenderer;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.core.data.registry.recipe.SearchQuery;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.slot.Slot;
import org.jspecify.annotations.NonNull;
import teamport.aether.recipe.RecipeEntryAetherMachine;

import java.util.*;

public abstract class RecipePageAetherMachines extends RecipePage<RecipeEntryAetherMachine> {
    private final List<SlotGuidebook> slots;
    private final Map<RecipeEntryAetherMachine, List<SlotGuidebook>> map;
    private final TooltipElement tooltipElement;
    private final ItemElement itemElement;
    private static long ticks = 0L;

    public static final Minecraft mc = Minecraft.getMinecraft();

    protected RecipePageAetherMachines(GuidebookSection section, List<RecipeEntryAetherMachine> recipes) {
        super(section);
        this.recipes = recipes;
        this.slots = new ArrayList<>();
        this.map = new HashMap<>();
        this.tooltipElement = new TooltipElement(mc);
        this.itemElement = new ItemElement(mc);
        buildSlots(recipes);
    }

    public abstract void buildSlots(List<RecipeEntryAetherMachine> recipes);

    @Override
    public void onTick() {
        ++ticks;

        for (SlotGuidebook slot : this.slots) {
            if (ticks > 20L) {
                slot.showRandomItem();
                if (this.slots.get(this.slots.size() - 1) == slot) {
                    ticks = 0L;
                }
            }
        }

    }

    @Override
    public void renderForeground(TextureManager re, FontRenderer fr, int x, int y, int mouseX, int mouseY, float partialTicks) {
        if (this.recipes.isEmpty()) {
            this.drawStringCenteredNoShadow(fr, I18n.getInstance().translateKey("guidebook.section.search.error.no_recipes"), x + 79, y + 110, -8355712);
        }

        SlotGuidebook mouseOverSlot = null;

        for (SlotGuidebook slot : this.slots) {
            this.drawSlot(x + slot.x - 1, y + slot.y - 1, -1);
            if (this.getIsMouseOverSlot(slot, x, y, mouseX, mouseY)) {
                mouseOverSlot = slot;
            }

            this.itemElement.render(slot.getItemStack(), x + slot.x, y + slot.y, mouseOverSlot == slot, slot);
        }

    }

    public boolean getIsMouseOverSlot(@NonNull Slot slot, int x, int y, int mouseX, int mouseY) {
        return mouseX >= x + slot.x - 1 && mouseX < x + slot.x + 16 + 1 && mouseY >= y + slot.y - 1 && mouseY < y + slot.y + 16 + 1;
    }

    @Override
    public boolean keyTyped(char c, int key, int x, int y, int mouseX, int mouseY) {
        super.keyTyped(c, key, x, y, mouseX, mouseY);
        if (GameSettings.KEY_SHOW_RECIPE.isKeyboardKey(key)) {
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
        } else if (GameSettings.KEY_SHOW_USAGE.isKeyboardKey(key)) {
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

    @Override
    public void renderBackground(TextureManager re, int x, int y) {
        super.renderBackground(re, x, y);
        re.bindTexture(re.loadTexture("/assets/minecraft/textures/gui/container/guidebook/guidebook.png"));

        for (int i = 1; i <= this.recipes.size(); ++i) {
            RecipeEntryAetherMachine recipe = this.recipes.get(i - 1);
            List<SlotGuidebook> list = this.map.get(recipe);
            this.drawTexturedModalRect(x + list.get(list.size() - 1).x - 32, y + list.get(list.size() - 1).y, 234, 0, 22, 15);
        }

    }

    @Override
    public void renderOverlay(TextureManager re, FontRenderer fr, int x, int y, int mouseX, int mouseY, float partialTicks) {
        super.renderOverlay(re, fr, x, y, mouseX, mouseY, partialTicks);
        SlotGuidebook mouseOverSlot = null;

        for (SlotGuidebook slot : this.slots) {
            if (this.getIsMouseOverSlot(slot, x, y, mouseX, mouseY)) {
                mouseOverSlot = slot;
            }

            GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            if (mouseOverSlot != null && mouseOverSlot.hasItem()) {
                boolean showDescription = DescriptionPromptEnum.showDescription();
                String str = this.tooltipElement.getTooltipText(mouseOverSlot.getItemStack(), showDescription, mouseOverSlot);
                if (!str.isEmpty()) {
                    this.tooltipElement.render(str, mouseX, mouseY, 8, -8);
                }
            }
        }
    }
    protected List<SlotGuidebook> getSlots() {
        return slots;
    }
    public Map<RecipeEntryAetherMachine, List<SlotGuidebook>> getMap() {
        return map;
    }
}
