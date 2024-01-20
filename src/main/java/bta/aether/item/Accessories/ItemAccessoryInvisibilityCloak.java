package bta.aether.item.Accessories;

import bta.aether.entity.IAetherAccessories;
import bta.aether.item.Accessories.base.ItemAccessoryCape;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;

public class ItemAccessoryInvisibilityCloak extends ItemAccessoryCape {
    public ItemAccessoryInvisibilityCloak(String name, int id, String texturePath) {
        super(name, id, texturePath);
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"cape"};
    }

    @Override
    public void onAccessoryAdded(EntityPlayer player, ItemStack accessory) {
        ((IAetherAccessories)player).aether$setInvisible(true);
    }

    @Override
    public void onAccessoryRemoved(EntityPlayer player, ItemStack accessory) {
        ((IAetherAccessories)player).aether$setInvisible(false);
    }
}
