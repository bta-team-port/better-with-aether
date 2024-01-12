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
    public ItemStack tickWhileWorn(EntityPlayer player, ItemStack stack, int slot) {
        player.fallDistance = 0.0F;
        if (!player.onGround && !player.isInWater() && player.yd < 0.0 && !player.collision && slot == AccessoryHelper.firstSlotWithAccessory(player,stack.getItem())) {
            player.yd *= 0.6;
        }
        return stack;
    }
}
