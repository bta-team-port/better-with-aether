package bta.aether.item;

import bta.aether.entity.EntityLightningKnife;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.projectile.EntityEgg;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemLightningKnife extends Item {
    public ItemLightningKnife(String name, int id) {
        super(name, id);
        this.maxStackSize = 64;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        itemstack.consumeItem(entityplayer);
        world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isClientSide) {
            world.entityJoinedWorld(new EntityLightningKnife(world, entityplayer));
        }

        return itemstack;
    }
}
