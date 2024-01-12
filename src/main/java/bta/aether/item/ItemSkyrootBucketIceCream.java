package bta.aether.item;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemSkyrootBucketIceCream extends Item {
    protected int healAmount;

    public ItemSkyrootBucketIceCream(int id, int healAmount) {
        super(id);
        this.healAmount = healAmount;
        this.maxStackSize = 1;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        entityplayer.heal(this.healAmount);
        return new ItemStack(AetherItems.bucketSkyroot);
    }

    public int getHealAmount() {
        return this.healAmount;
    }
}
