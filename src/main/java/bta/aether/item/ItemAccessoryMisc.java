package bta.aether.item;

import net.minecraft.core.item.ItemStack;

public class ItemAccessoryMisc extends ItemToolAccessory {

    public ItemAccessoryMisc(int id) {
        super(id);
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"misc"};
    }
}
