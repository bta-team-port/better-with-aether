package bta.aether.item;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;

public class ItemIronBubble extends ItemAccessoryMisc{
    public ItemIronBubble(int id) {
        super(id);
    }

    @Override
    public void onAccessoryAdded(EntityPlayer player, ItemStack accessory) {
        player.canBreatheUnderwater();
    }

}
