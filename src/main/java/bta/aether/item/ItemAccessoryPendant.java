package bta.aether.item;

import net.minecraft.core.item.ItemStack;

public class ItemAccessoryPendant extends ItemToolAccessory {

    public ItemAccessoryPendant(int id) {
        super(id);
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"pendant"};
    }

}
