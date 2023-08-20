package bta.aether.item;

import bta.aether.entity.EntityGoldenDart;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemShooter extends Item {
    public ItemShooter(int id) {
        super(id);
        this.setMaxDamage(100);
        this.maxStackSize = 1;
    }

    public boolean isFull3D() {
        return true;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * -0.2F + -0.4F));
        if (!world.isClientSide) {
            itemstack.damageItem(1, entityplayer);
            world.entityJoinedWorld(new EntityGoldenDart(world, 1));
        } else {
            return itemstack;
        }
        return itemstack;
    }
}
