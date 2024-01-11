package bta.aether.item;

import net.minecraft.core.item.ItemStack;

public class ItemAccessoryShield extends ItemToolAccessory {

    public ItemAccessoryShield(int id) {
        super(id);
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"shield"};
    }
}
