package bta.aether.item.Accessories;

import bta.aether.item.Accessories.base.ItemAccessoryMisc;
import csweetla.accessoryapi.API.AccessoryHelper;
import csweetla.accessoryapi.API.TickableWhileWorn;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;

public class ItemAccessoryIronBubble extends ItemAccessoryMisc implements TickableWhileWorn {
    public ItemAccessoryIronBubble(int id) {
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
        stack.setMetadata(stack.getMetadata() + 1);
        if (slot == AccessoryHelper.firstSlotWithAccessory(player, stack.getItem())) {
            player.canBreatheUnderwater();
            player.airSupply = 300;
        }
        return stack;
    }
}
