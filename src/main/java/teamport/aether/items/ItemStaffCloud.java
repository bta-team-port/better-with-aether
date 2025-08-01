package teamport.aether.items;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.entity.minicloud.MobMinicloud;

import java.util.List;

public class ItemStaffCloud extends Item {

    public ItemStaffCloud(String translationKey, String namespaceId, int id) {
        super(translationKey, namespaceId, id);
        this.setMaxStackSize(1);
        this.setMaxDamage(60);
    }

    private ItemStack useCloudStaff(ItemStack itemstack, World world, Player entityplayer) {
        if (!this.cloudsExist(world, entityplayer)) {

            MobMinicloud c1 = new MobMinicloud(world, entityplayer, false);
            MobMinicloud c2 = new MobMinicloud(world, entityplayer, true);
            world.entityJoinedWorld(c1);
            world.entityJoinedWorld(c2);
            itemstack.damageItem(1, entityplayer);
        }

        return itemstack;
    }

    public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
        return world.isClientSide ? itemstack : useCloudStaff(itemstack, world, entityplayer);
    }

    private boolean cloudsExist(World world, Player entityplayer) {
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.bb.expand(128.0, 128.0, 128.0));

        for (Entity entity1 : list) {
            if (entity1 instanceof MobMinicloud) {
                return true;
            }
        }
        return false;
    }
}
