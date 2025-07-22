package teamport.aether.guidebook.incubator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ItemElement;
import net.minecraft.client.gui.TooltipElement;
import net.minecraft.client.gui.guidebook.RecipePage;
import net.minecraft.client.gui.guidebook.SlotGuidebook;
import net.minecraft.client.option.enums.DescriptionPromptEnum;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.TextureManager;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.opengl.GL11;
import teamport.aether.AetherMod;
import teamport.aether.lookup.Repairable;
import teamport.aether.recipe.RecipeEntryAetherMachine;
import teamport.aether.recipe.RecipeEntryIncubator;
import teamport.aether.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static teamport.aether.util.Pair.pair;

public class RecipePageIncubator extends RecipePage<RecipeEntryIncubator> {

    private final List<SlotGuidebook> slots;
    private final TooltipElement tooltipElement;
    private final ItemElement itemElement;
    public Map<RecipeEntryIncubator, Pair<SlotGuidebook, String>> map;
    public static final Minecraft mc = Minecraft.getMinecraft();

    public RecipePageIncubator(GuidebookIncubatorSection section, List<RecipeEntryIncubator> recipes) {
        super(section);
        this.recipes = recipes;
        this.slots = new ArrayList<>();
        this.tooltipElement = new TooltipElement(mc);
        this.itemElement = new ItemElement(mc);
        this.map = new HashMap<>();
        buildSlots(recipes);
    }

    @Override
    protected void renderForeground(TextureManager re, Font fr, int x, int y, int mouseX, int mouseY, float partialTicks) {
        if (this.recipes.isEmpty()) {
            this.drawStringCenteredNoShadow(fr, I18n.getInstance().translateKey("guidebook.section.search.error.no_recipes"), x + 79, y + 110, -8355712);
        }

        SlotGuidebook mouseOverSlot = null;

        for (SlotGuidebook slot : this.slots) {
            this.drawSlot(x + slot.x - 1, y + slot.y - 1, -1);
            if (this.getIsMouseOverSlot(slot, x, y, mouseX, mouseY)) {
                mouseOverSlot = slot;
            }
            this.itemElement.render(slot.getItemStack(), x + slot.x, y + slot.y, mouseOverSlot==slot, slot);
        }
    }

    public void buildSlots(List<RecipeEntryIncubator> recipes){
        for (RecipeEntryIncubator recipe : recipes) {
            String[] entityName = recipe.getOutput().split(":");
            if(entityName.length <= 1){
                AetherMod.LOGGER.error("Failed to parse the creatures name '{}'!", recipe.getOutput());
                throw new IndexOutOfBoundsException();
            }
            String languageKey = "aether.guidebook.section.incubator" + "." + entityName[1];
            String text = I18n.getInstance().translateKey(languageKey);
            int yOffset = 32 * (this.map.size() + 1) - 16;
            SlotGuidebook recipeSlot = new SlotGuidebook(0, 20, 2 * yOffset, recipe.getInput() , false, recipe);
            this.map.put(recipe, pair(recipeSlot, text));
            this.slots.add(recipeSlot);
        }
    }
    public boolean getIsMouseOverSlot(Slot slot, int x, int y, int mouseX, int mouseY) {
        return mouseX >= x + slot.x - 1 && mouseX < x + slot.x + 16 + 1 && mouseY >= y + slot.y - 1 && mouseY < y + slot.y + 16 + 1;
    }

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

}
