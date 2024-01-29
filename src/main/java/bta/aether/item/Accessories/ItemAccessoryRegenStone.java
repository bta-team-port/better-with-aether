package bta.aether.item.Accessories;

import bta.aether.accessory.API.AccessoryHelper;
import bta.aether.accessory.API.TickableWhileWorn;
import bta.aether.item.Accessories.base.ItemAccessoryMisc;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;

public class ItemAccessoryRegenStone extends ItemAccessoryMisc implements TickableWhileWorn {

    // 14 seconds at 20 ticks per second to heal half heart (estimated from original mod)
    public static int ticks_per_half_heart_heal = 14 * 20;

    public ItemAccessoryRegenStone(String name, int id) {
        super(name, id);
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
        if (stack.getMetadata() > ticks_per_half_heart_heal) {
            stack.setMetadata(0);
            // only heal if it's the first equipped, since in the OG mod effect doesn't stack.
            // also don't heal when player has max health, since that causes hearts flash
            if (slot == AccessoryHelper.firstSlotWithAccessory(player, stack.getItem()) && player.health < 20) {
                player.heal(1);
            }
        }
        return stack;
    }
}
