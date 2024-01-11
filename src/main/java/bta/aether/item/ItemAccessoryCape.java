package bta.aether.item;

import net.minecraft.core.item.ItemStack;

public class ItemAccessoryCape extends ItemToolAccessory {

    public ItemAccessoryCape(int id) {
        super(id);
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"cape"};
    }
}
