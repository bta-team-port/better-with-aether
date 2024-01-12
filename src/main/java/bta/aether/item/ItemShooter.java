package bta.aether.item;

import bta.aether.entity.EntityGoldenDart;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.entity.projectile.EntityCannonball;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemShooter extends Item {
    public ItemShooter(int id) {
        super(id);
        this.setMaxDamage(100);
        this.maxStackSize = 1;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (entityplayer.inventory.consumeInventoryItem(AetherItems.dartGolden.id)) {
            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide) {
                world.entityJoinedWorld(new EntityGoldenDart(world, entityplayer, true, 10));
            }
        }
        return itemstack;
    }
}

