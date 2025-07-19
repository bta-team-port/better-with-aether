package teamport.aether.guidebook;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ItemElement;
import net.minecraft.client.gui.TooltipElement;
import net.minecraft.client.gui.guidebook.*;
import net.minecraft.client.gui.guidebook.search.GuidebookPageSearch;
import net.minecraft.client.option.enums.DescriptionPromptEnum;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.TextureManager;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.SearchQuery;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.opengl.GL11;
import teamport.aether.recipe.RecipeEntryAetherMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class RecipePageEnchanting extends RecipePage<RecipeEntryAetherMachine> {
    public static final int RECIPES_PER_PAGE = 6;
    public List<SlotGuidebook> slots;
    public Map<RecipeEntryAetherMachine, List<SlotGuidebook>> map;
    private final TooltipElement tooltipElement;
    private final ItemElement itemElement;
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static long ticks = 0L;

    public RecipePageEnchanting(GuidebookSection section, ArrayList<RecipeEntryAetherMachine> recipes) {
        super(section);
        this.recipes = recipes;
        this.slots = new ArrayList();
        this.map = new HashMap();
        this.tooltipElement = new TooltipElement(mc);
        this.itemElement = new ItemElement(mc);

        for(RecipeEntryAetherMachine recipe : recipes) {
            List<SlotGuidebook> recipeSlots = new ArrayList();
            recipeSlots.add(new SlotGuidebook(0, 47, 32 * (this.map.size() + 1) - 16, (RecipeSymbol)recipe.getInput(), false, recipe));
            recipeSlots.add(new SlotGuidebook(1, 103, 32 * (this.map.size() + 1) - 16, new RecipeSymbol((ItemStack)recipe.getOutput()), false, recipe));
            this.map.put(recipe, recipeSlots);
            this.slots.addAll(recipeSlots);
        }

    }

    public void onTick() {
        ++ticks;

        for(SlotGuidebook slot : this.slots) {
            if (ticks > 20L) {
                slot.showRandomItem();
                if (this.slots.get(this.slots.size() - 1) == slot) {
                    ticks = 0L;
                }
            }
        }

    }

    protected void renderForeground(TextureManager re, Font fr, int x, int y, int mouseX, int mouseY, float partialTicks) {
        if (this.recipes.isEmpty()) {
            this.drawStringCenteredNoShadow(fr, I18n.getInstance().translateKey("guidebook.section.search.error.no_recipes"), x + 79, y + 110, -8355712);
        }

        SlotGuidebook mouseOverSlot = null;

        for(SlotGuidebook slot : this.slots) {
            this.drawSlot(x + slot.x - 1, y + slot.y - 1, -1);
            if (this.getIsMouseOverSlot(slot, x, y, mouseX, mouseY)) {
                mouseOverSlot = slot;
            }

            this.itemElement.render(slot.getItemStack(), x + slot.x, y + slot.y, mouseOverSlot == slot, slot);
        }

    }

    public boolean getIsMouseOverSlot(Slot slot, int x, int y, int mouseX, int mouseY) {
        return mouseX >= x + slot.x - 1 && mouseX < x + slot.x + 16 + 1 && mouseY >= y + slot.y - 1 && mouseY < y + slot.y + 16 + 1;
    }

    public boolean keyTyped(char c, int key, int x, int y, int mouseX, int mouseY) {
        super.keyTyped(c, key, x, y, mouseX, mouseY);
        if (mc.gameSettings.keyShowRecipe.isKeyboardKey(key)) {
            SlotGuidebook hoveringSlot = null;

            for(SlotGuidebook slot : this.slots) {
                if (this.getIsMouseOverSlot(slot, x, y, mouseX, mouseY)) {
                    hoveringSlot = slot;
                }
            }

            if (hoveringSlot != null && hoveringSlot.hasItem()) {
                String query = "r:" + hoveringSlot.getItemStack().getDisplayName() + "!";
                GuidebookPageManager.searchQuery = SearchQuery.resolve(query);
                GuidebookPageSearch.searchField.setText(query);
                ScreenGuidebook.getPageManager().updatePages();
                ScreenGuidebook.getPageManager().setCurrentPage(ScreenGuidebook.getPageManager().getSectionIndex(GuidebookSections.CRAFTING), true);
                return true;
            }
        } else if (mc.gameSettings.keyShowUsage.isKeyboardKey(key)) {
            SlotGuidebook hoveringSlot = null;

            for(SlotGuidebook slot : this.slots) {
                if (this.getIsMouseOverSlot(slot, x, y, mouseX, mouseY)) {
                    hoveringSlot = slot;
                }
            }

            if (hoveringSlot != null && hoveringSlot.hasItem()) {
                String query = "u:" + hoveringSlot.getItemStack().getDisplayName() + "!";
                GuidebookPageManager.searchQuery = SearchQuery.resolve(query);
                GuidebookPageSearch.searchField.setText(query);
                ScreenGuidebook.getPageManager().updatePages();
                ScreenGuidebook.getPageManager().setCurrentPage(ScreenGuidebook.getPageManager().getSectionIndex(GuidebookSections.CRAFTING), true);
                return true;
            }
        }

        return false;
    }

    public void render(TextureManager re, Font fr, int x, int y, int mouseX, int mouseY, float partialTicks) {
        super.render(re, fr, x, y, mouseX, mouseY, partialTicks);
    }

    protected void renderBackground(TextureManager re, int x, int y) {
        super.renderBackground(re, x, y);
        re.bindTexture(re.loadTexture("/assets/minecraft/textures/gui/container/guidebook/guidebook.png"));

        for(int i = 1; i <= this.recipes.size(); ++i) {
            RecipeEntryAetherMachine recipe = (RecipeEntryAetherMachine)this.recipes.get(i - 1);
            List<SlotGuidebook> list = (List)this.map.get(recipe);
            this.drawTexturedModalRect(x + ((SlotGuidebook)list.get(list.size() - 1)).x - 32, y + ((SlotGuidebook)list.get(list.size() - 1)).y, 234, 0, 22, 15);
        }

    }

    protected void renderOverlay(TextureManager re, Font fr, int x, int y, int mouseX, int mouseY, float partialTicks) {
        super.renderOverlay(re, fr, x, y, mouseX, mouseY, partialTicks);
        SlotGuidebook mouseOverSlot = null;

        for(SlotGuidebook slot : this.slots) {
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
}
