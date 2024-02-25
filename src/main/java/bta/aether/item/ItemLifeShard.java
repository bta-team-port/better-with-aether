package bta.aether.item;


import bta.aether.accessory.API.HealthHelper;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemLifeShard extends Item {

    public ItemLifeShard(String name, int id) {
        super(name, id);
        setMaxStackSize(10);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (HealthHelper.getExtraHealth(entityplayer) < 20 && itemstack.consumeItem(entityplayer)) {
            // min to make damn sure we don't increase pitch and volume more than expected, because that's a recipe for earsplitting sound
            int extra_heart_amount = Math.min(HealthHelper.getExtraHealth(entityplayer), 20);

            if (extra_heart_amount == 18)
                world.playSoundAtEntity(entityplayer, "aether.life_shard_chime_final",0.65F,1.0F);
            else
                world.playSoundAtEntity(entityplayer,"aether.life_shard_chime", 0.45F + 0.02F * extra_heart_amount,0.9F + 0.015F * extra_heart_amount);

            HealthHelper.addExtraHealth(entityplayer, 2);
            entityplayer.heal(2);
        }
        return itemstack;
    }
}
