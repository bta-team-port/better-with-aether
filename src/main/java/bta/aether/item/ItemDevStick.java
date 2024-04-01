package bta.aether.item;

import bta.aether.entity.IPlayerBossList;
import bta.aether.entity.projectiles.EntityZephyrSnowball;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;

public class ItemDevStick extends Item {
    public ItemDevStick(String name, int id) {
        super(name, id);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        AABB boundingBox = new AABB(entityplayer.x -3, entityplayer.y -3, entityplayer.z -3, entityplayer.x +3, entityplayer.y +3, entityplayer.z +3);
        for (Entity entityInBox : world.getEntitiesWithinAABB(EntityLiving.class, boundingBox)) {
            if (entityInBox != entityplayer)
                ((IPlayerBossList) entityplayer).aether$getBossList().add((EntityLiving) entityInBox);
        }

        return itemstack;
    }
}
