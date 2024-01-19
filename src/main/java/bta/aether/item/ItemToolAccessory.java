package bta.aether.item;

import csweetla.accessoryapi.API.Accessory;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;

public abstract class ItemToolAccessory extends Item implements Accessory {
    public ItemToolAccessory(String name, int id){
        super(name, id);
        this.maxStackSize = 1;
    }

    @Override
    public void onAccessoryAdded(EntityPlayer player, ItemStack accessory) {
    }

    @Override
    public void onAccessoryRemoved(EntityPlayer player, ItemStack accessory) {
    }
}
