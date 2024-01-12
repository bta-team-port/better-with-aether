package bta.aether.item.Accessories;

import bta.aether.item.Accessories.base.ItemAccessoryMisc;
import csweetla.accessoryapi.API.AccessoryHelper;
import csweetla.accessoryapi.API.TickableWhileWorn;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;

public class ItemAccessoryGoldenFeather extends ItemAccessoryMisc implements TickableWhileWorn {

    public ItemAccessoryGoldenFeather(int id) {
        super(id);
    }

    @Override
    public void onAccessoryAdded(EntityPlayer player, ItemStack accessory) {
        accessory.setMetadata(0);
    }

    @Override
    public void onAccessoryRemoved(EntityPlayer player, ItemStack accessory) {
        accessory.setMetadata(0);
    }

    @Override
    public ItemStack tickWhileWorn(EntityPlayer player, ItemStack stack, int slot) {
        if (slot == AccessoryHelper.firstSlotWithAccessory(player, stack.getItem())) {
            player.fallDistance = 0.0F;
            if (!player.onGround && player.yd < 0.0 && !player.collision){
                player.yd *= 0.6;

                player.fallDistance = 0.0F;
            }
            return stack;
        }
        return stack;
    }
}
