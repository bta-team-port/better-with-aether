package bta.aether.item.Accessories;

import bta.aether.accessory.API.AccessoryHelper;
import bta.aether.accessory.API.TickableWhileWorn;
import bta.aether.item.Accessories.base.ItemAccessoryMisc;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;

public class ItemAccessoryGoldenFeather extends ItemAccessoryMisc implements TickableWhileWorn {

    public ItemAccessoryGoldenFeather(String name, int id) {
        super(name, id);
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
