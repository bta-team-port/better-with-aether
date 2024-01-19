package bta.aether.item;

import bta.aether.entity.EntityArrowFlaming;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemPhoenixBow extends Item {
    public ItemPhoenixBow(String name, int id) {
        super(name, id);
        this.maxStackSize = 1;
        this.setMaxDamage(768);
    }
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        ItemStack quiverSlot = entityplayer.inventory.armorItemInSlot(2);
        if (quiverSlot != null && quiverSlot.itemID == Item.armorQuiver.id && quiverSlot.getMetadata() < quiverSlot.getMaxDamage()) {
            entityplayer.inventory.armorItemInSlot(2).damageItem(1, entityplayer);
            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, "mob.ghast.fireball", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide) {
                world.entityJoinedWorld(new EntityArrowFlaming(world, entityplayer, true, 4));
            }
        } else if (quiverSlot != null && quiverSlot.itemID == Item.armorQuiverGold.id) {
            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, "mob.ghast.fireball", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide) {
                world.entityJoinedWorld(new EntityArrowFlaming(world, entityplayer, false, 4));
            }
        } else if (entityplayer.inventory.consumeInventoryItem(Item.ammoArrowGold.id)) {
            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, "mob.ghast.fireball", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide) {
                world.entityJoinedWorld(new EntityArrowFlaming(world, entityplayer, true, 4));
            }
        } else if (entityplayer.inventory.consumeInventoryItem(Item.ammoArrow.id)) {
            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, "mob.ghast.fireball", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide) {
                world.entityJoinedWorld(new EntityArrowFlaming(world, entityplayer, true, 4));
            }
        }

        return itemstack;
    }
}

