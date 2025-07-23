package teamport.aether.guidebook.freezer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.guidebook.*;
import net.minecraft.client.render.TextureManager;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import org.lwjgl.opengl.GL11;
import teamport.aether.guidebook.AetherSlotGuidebook;
import teamport.aether.guidebook.RecipePageAetherMachines;
import teamport.aether.recipe.RecipeEntryAetherMachine;

import java.util.*;

@Environment(EnvType.CLIENT)
public class RecipePageFreezer extends RecipePageAetherMachines {

    public RecipePageFreezer(GuidebookSection section, List<RecipeEntryAetherMachine> recipes) {
        super(section, recipes);
    }

    @Override
    public void buildSlots(List<RecipeEntryAetherMachine> recipes){
        for(RecipeEntryAetherMachine recipe : recipes) {
            List<SlotGuidebook> recipeSlots = new ArrayList<>();
            recipeSlots.add(new AetherSlotGuidebook(0, 47, 32 * (this.map.size() + 1) - 16, recipe.getInput(), false, recipe));
            recipeSlots.add(new AetherSlotGuidebook(1, 103, 32 * (this.map.size() + 1) - 16, new RecipeSymbol(recipe.getOutput()), false, recipe));
            this.map.put(recipe, recipeSlots);
            this.slots.addAll(recipeSlots);
        }
    }

    @Override
    public void renderBackground(TextureManager re, int x, int y) {
        super.renderBackground(re, x, y);
        re.bindTexture(re.loadTexture("/assets/minecraft/textures/gui/container/guidebook/guidebook.png"));

        for(int i = 1; i <= this.recipes.size(); ++i) {
            RecipeEntryAetherMachine recipe = this.recipes.get(i - 1);
            List<SlotGuidebook> list = this.map.get(recipe);
            int posX = x + list.get(list.size() - 1).x - 32;
            int posY = y + list.get(list.size() - 1).y;
            this.drawTexturedModalRect(posX, posY, 234, 0, 22, 15);

            StringBuilder buildTime = new StringBuilder();
            int time = Math.round(recipe.getData() / 20.0F);
            if(time >= 60){
                time = Math.round(time / 60.0f);
                buildTime.append(time).append("m");
            }else{
                buildTime.append(time).append("s");
            }
            String timeString = buildTime.toString();
            int alignRight = (timeString.length() > 2 ? -1 : 3);

            GL11.glPushMatrix();
            GL11.glTranslatef(posX + alignRight , posY - 1, 0.0f);
            GL11.glScalef(0.85f,0.93f,1.0f);
            this.drawStringNoShadow(mc.font, timeString, 0, 0, -12566464);
            GL11.glPopMatrix();
            re.loadTexture("/assets/minecraft/textures/gui/container/guidebook/guidebook.png").bind();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

    }
}
