package teamport.aether.items;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.AetherAchievements;
import teamport.aether.entity.vehicle.parachute.EntityParachute;
import teamport.aether.entity.vehicle.parachute.EntityParachuteGold;

public class ItemParachute extends Item {
    boolean gold;

    public ItemParachute(String translationKey, String namespaceId, int id, boolean gold) {
        super(translationKey, namespaceId, id);
        this.gold = gold;
    }

    public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
        if (entityplayer.fallDistance > 0) {
            if (!world.isClientSide) {
                if (!gold) {
                    EntityParachute cloud = new EntityParachute(world);
                    cloud.spawnInit();
                    cloud.absMoveTo(entityplayer.x, entityplayer.y - 2, entityplayer.z, (entityplayer.yRot), (entityplayer.xRot));
                    world.spawnParticle("explode", entityplayer.x + 0.5, entityplayer.y + 1, entityplayer.z + 0.5, 0.0, 0.0, 0.0, 0);
                    entityplayer.startRiding(cloud);
                    world.entityJoinedWorld(cloud);
                    itemstack.damageItem(1, entityplayer);

                } else {
                    EntityParachuteGold cloud = new EntityParachuteGold(world);
                    cloud.spawnInit();
                    cloud.absMoveTo(entityplayer.x, entityplayer.y - 2, entityplayer.z, (entityplayer.yRot), (entityplayer.xRot));
                    world.spawnParticle("goldendust", entityplayer.x + 0.5, entityplayer.y + 1, entityplayer.z + 0.5, 0.0, 0.0, 0.0, 0);
                    entityplayer.startRiding(cloud);
                    world.entityJoinedWorld(cloud);
                }
                itemstack.damageItem(1, entityplayer);
                entityplayer.triggerAchievement(AetherAchievements.PARACHUTE);
            }
        }

        return itemstack;
    }
}
