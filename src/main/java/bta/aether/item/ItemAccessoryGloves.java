package bta.aether.item;

import net.minecraft.core.item.ItemStack;

public class ItemAccessoryGloves extends ItemToolAccessory {

    public ItemAccessoryGloves(int id) {
        super(id);
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"gloves"};
    }
}
