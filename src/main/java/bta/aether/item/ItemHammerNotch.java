package bta.aether.item;

import bta.aether.entity.projectiles.EntityHammerHead;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.world.World;

public class ItemHammerNotch extends ItemToolSword {


    public ItemHammerNotch(String name, int id, ToolMaterial enumtoolmaterial) {
        super(name, id, enumtoolmaterial);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        itemstack.damageItem(1, entityplayer);
        world.playSoundAtEntity(entityplayer, "mob.ghast.fireball", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isClientSide) {
            world.entityJoinedWorld(new EntityHammerHead(world, entityplayer, true));
        }
        return itemstack;
    }
}
