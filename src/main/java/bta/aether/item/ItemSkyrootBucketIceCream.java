package bta.aether.item;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemSkyrootBucketIceCream extends ItemFood {
    protected int healAmount;

    public ItemSkyrootBucketIceCream(String name, int id, int healAmount, int ticksPerHeal) {
        super(name, id, healAmount, ticksPerHeal, false, 1);
        this.maxStackSize = 1;
    }

    public ItemStack onUseItem(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (entityplayer.getHealth() < entityplayer.getMaxHealth()) {
            super.onUseItem(itemstack, world, entityplayer);
            return new ItemStack(AetherItems.bucketSkyroot);
        } else {
            return itemstack;
        }
    }
}
