package bta.aether.item;

import net.minecraft.core.item.ItemStack;

public class ItemAccessoryRing extends ItemToolAccessory {

    public ItemAccessoryRing(int id) {
        super(id);
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"ring"};
    }
}
