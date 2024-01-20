package bta.aether.item;

import bta.aether.entity.projectiles.EntityProjectileModular;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public abstract class ItemShooter extends Item {
    public int arrowId;
    public ItemShooter(String name, int id, int arrowId) {
        super(name, id);
        this.setMaxDamage(100);
        this.maxStackSize = 1;
        this.arrowId = arrowId;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    public abstract EntityProjectileModular getArrow(World world, EntityPlayer entityPlayer, Boolean belongToPlayer);

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (entityplayer.inventory.consumeInventoryItem(this.arrowId)) {
            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide) {
                world.entityJoinedWorld(getArrow(world, entityplayer, true));
            }
        }
        return itemstack;
    }
}

