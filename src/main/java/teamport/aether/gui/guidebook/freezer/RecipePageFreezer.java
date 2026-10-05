package teamport.aether.gui.guidebook.freezer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.guidebook.GuidebookSection;
import net.minecraft.client.gui.guidebook.SlotGuidebook;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import org.jspecify.annotations.NonNull;
import teamport.aether.gui.guidebook.AetherSlotGuidebook;
import teamport.aether.gui.guidebook.RecipePageAetherMachines;
import teamport.aether.recipe.RecipeEntryAetherMachine;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class RecipePageFreezer extends RecipePageAetherMachines {

    public RecipePageFreezer(GuidebookSection section, List<RecipeEntryAetherMachine> recipes) {
        super(section, recipes);
    }

    @Override
    public void buildSlots(@NonNull List<RecipeEntryAetherMachine> recipes) {
        for (RecipeEntryAetherMachine recipe : recipes) {
            List<SlotGuidebook> recipeSlots = new ArrayList<>();
            recipeSlots.add(new AetherSlotGuidebook(0, 47, 32 * (this.getMap().size() + 1) - 16, recipe.getInput(), false, recipe));
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

            StringBuilder buildTime = new StringBuilder();
            int time = Math.round(recipe.getData() / 20.0F);
            if (time >= 60) {
                time = Math.round(time / 60.0f);
                buildTime.append(time).append("m");
            } else {
                buildTime.append(time).append("s");
            }
            String timeString = buildTime.toString();
            int alignRight = (timeString.length() > 2 ? -1 : 3);

            GLRenderer.pushFrame();
            GLRenderer.modelM4f().translate((float) posX + alignRight, posY - 1.0F, 0.0F);
            GLRenderer.modelM4f().scale(0.85f, 0.93f, 1.0f);
            this.drawStringNoShadow(mc.font, timeString, 0, 0, -12566464);
            GLRenderer.popFrame();
            re.loadTexture("/assets/minecraft/textures/gui/container/guidebook/guidebook.png").bind();
            GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

    }
}
