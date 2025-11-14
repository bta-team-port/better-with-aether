package teamport.aether.gui.guidebook;

import net.minecraft.client.gui.guidebook.SlotGuidebook;
import net.minecraft.core.data.registry.recipe.RecipeEntryBase;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.item.ItemStack;

import java.util.List;
import java.util.Random;

public class AetherSlotGuidebook extends SlotGuidebook {
    private final Random random = new Random();
    public AetherSlotGuidebook(int id, int x, int y, RecipeSymbol symbol, boolean discovered, RecipeEntryBase<?, ?, ?> recipe) {
        super(id, x, y, symbol, discovered, recipe);
    }

    @Override
    public void showRandomItem() {
        if (this.symbol != null) {
            List<ItemStack> list = this.symbol.resolve();
            ItemStack newItem = list.get(random.nextInt(list.size()));
            if (list.size() > 1) {
                while (newItem == this.item) {
                    newItem = list.get(random.nextInt(list.size()));
                }
            }
            this.item = newItem;
        }
    }
}
