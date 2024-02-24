package bta.aether.item;

import bta.aether.entity.projectiles.EntityZephyrSnowball;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemDevStick extends Item {
    public ItemDevStick(String name, int id) {
        super(name, id);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        world.entityJoinedWorld(new EntityZephyrSnowball(world, entityplayer, true));
        return itemstack;
    }
}
