package bta.aether.item;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;

public class ItemGoldenFeather extends ItemAccessoryMisc{
    private double fallDistance;

    public ItemGoldenFeather(int id) {
        super(id);
    }

    @Override
    public void onAccessoryAdded(EntityPlayer player, ItemStack accessory) {
        this.fallDistance = 0.0F;
    }

}
