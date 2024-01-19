package bta.aether.item.Accessories;

import bta.aether.item.Accessories.base.ItemAccessoryMisc;
import csweetla.accessoryapi.API.TickableWhileWorn;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;

public class ItemAccessoryIronBubble extends ItemAccessoryMisc implements TickableWhileWorn {
    public ItemAccessoryIronBubble(String name, int id) {
        super(name, id);
    }

    @Override
    public ItemStack tickWhileWorn(EntityPlayer player, ItemStack stack, int slot) {
        player.airSupply = 300;
        return stack;
    }
}
