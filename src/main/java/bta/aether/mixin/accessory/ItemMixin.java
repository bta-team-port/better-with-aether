package bta.aether.mixin.accessory;

import bta.aether.accessory.API.Accessory;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = Item.class, remap = false)
public class ItemMixin implements Accessory {

    // allow inserting compass, clock, and calendar into misc slots
    // making every item an accessory is kinda cursed, but it doesn't seem to cause issues
    // if some issues come up, maybe accessories should be based on item tags instead...
    @Override
    public String[] getAccessoryTypes(ItemStack stack) {
        if (stack != null && (stack.getItem().equals(Item.toolCompass) || stack.getItem().equals(Item.toolClock) || stack.getItem().equals(Item.toolCalendar))) {
            return new String[] {"misc"};
        }

        return new String[0];
    }
}
