package teamport.aether.gui.guidebook.enchanter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.guidebook.GuidebookSection;
import net.minecraft.client.gui.guidebook.SlotGuidebook;
import net.minecraft.client.render.TextureManager;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.lwjgl.opengl.GL11;
import teamport.aether.gui.guidebook.AetherSlotGuidebook;
import teamport.aether.gui.guidebook.RecipePageAetherMachines;
import teamport.aether.recipe.RecipeEntryAetherMachine;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class RecipePageEnchanting extends RecipePageAetherMachines {

    public RecipePageEnchanting(GuidebookSection section, List<RecipeEntryAetherMachine> recipes) {
        super(section, recipes);
    }

    @Override
    public void buildSlots(List<RecipeEntryAetherMachine> recipes) {
        for (RecipeEntryAetherMachine recipe : recipes) {
            List<SlotGuidebook> recipeSlots = new ArrayList<>();
            RecipeSymbol varietyItem = getDamagedVariety(recipe);
            recipeSlots.add(new AetherSlotGuidebook(0, 47, 32 * (this.getMap().size() + 1) - 16, varietyItem, false, recipe));
            recipeSlots.add(new AetherSlotGuidebook(1, 103, 32 * (this.getMap().size() + 1) - 16, new RecipeSymbol(recipe.getOutput()), false, recipe));
            this.getMap().put(recipe, recipeSlots);
            this.getSlots().addAll(recipeSlots);
        }
    }


    @Override
    public void renderBackground(TextureManager re, int x, int y) {
        super.renderBackground(re, x, y);
        re.bindTexture(re.loadTexture("/assets/minecraft/textures/gui/container/guidebook/guidebook.png"));

        for (int i = 1; i <= this.recipes.size(); ++i) {
            RecipeEntryAetherMachine recipe = this.recipes.get(i - 1);
            List<SlotGuidebook> list = this.getMap().get(recipe);
            int posX = x + list.get(list.size() - 1).x - 32;
            int posY = y + list.get(list.size() - 1).y;
            this.drawTexturedModalRect(posX, posY, 234, 0, 22, 15);
            String timeString = getTimeAsString(recipe.getData());
            int alignRight = (timeString.length() > 2 ? -1 : 3);

            // move the text below the arrow
            int adjY = 0;
            ItemStack input = recipe.getInput().getStack();
            ItemStack output = recipe.getOutput();
            if (
                input != null
                    && output != null
                    && input.isItemStackDamageable()
                    && output.isItemStackDamageable()
                    && output.itemID == input.itemID
            ) {
                GL11.glPushMatrix();
                GL11.glTranslatef(posX - 1.0F, posY - 1.0F, 0.0f);
                GL11.glScalef(0.85f, 0.93f, 1.0f);
                this.drawStringNoShadow(mc.font, "max", 0, 0, -12566464);
                GL11.glPopMatrix();
                adjY = 11;
            }

            GL11.glPushMatrix();
            GL11.glTranslatef((float) posX + alignRight, posY - 1.0F + adjY, 0.0f);
            GL11.glScalef(0.85f, 0.93f, 1.0f);
            this.drawStringNoShadow(mc.font, timeString, 0, 0, -12566464);
            GL11.glPopMatrix();
            re.loadTexture("/assets/minecraft/textures/gui/container/guidebook/guidebook.png").bind();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

    }

    public static String getTimeAsString(int data) {
        StringBuilder buildTime = new StringBuilder();
        int time = Math.round(data / 20.0F);
        if (time >= 60) {
            time = Math.round(time / 60.0f);
            buildTime.append(time).append("m");
        } else {
            buildTime.append(time).append("s");
        }
        return buildTime.toString();
    }

    public static @NonNull RecipeSymbol getDamagedVariety(RecipeEntryAetherMachine recipe) {
        RecipeSymbol varientRecipeInput = recipe.getInput();
        ItemStack input = varientRecipeInput.getStack();
        ItemStack copyInput = ItemStack.copyItemStack(input);

        ItemStack output = recipe.getOutput();
        if (
            copyInput != null
                && output != null
                && copyInput.isItemStackDamageable()
                && output.isItemStackDamageable()
                && output.itemID == copyInput.itemID
        ) {
            List<ItemStack> variations = new ArrayList<>();
            for (int i = 1; i < 10; i++) {
                int damage = Math.round(copyInput.getMaxDamage() / 10.0f * i);
                copyInput.setMetadata(damage);
                ItemStack stack = copyInput.copy();
                variations.add(stack);
            }
            varientRecipeInput = new RecipeSymbol(variations);
        }
        return varientRecipeInput;
    }
}
