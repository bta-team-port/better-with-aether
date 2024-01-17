package bta.aether.item;


import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import csweetla.accessoryapi.API.HealthHelper;

public class ItemLifeShard extends Item {

    public ItemLifeShard(int id) {
        super(id);
        setMaxStackSize(10);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (HealthHelper.getExtraHealth(entityplayer) < 20 && itemstack.consumeItem(entityplayer)) {
            HealthHelper.addExtraHealth(entityplayer, 2);
            entityplayer.heal(2);
        }
        return itemstack;
    }
}
