package teamport.aether.item;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.AetherMod;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.entity.vehicle.parachute.EntityParachute;
import teamport.aether.helper.ParticleMaker;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class ItemParachute extends Item {
    Class<? extends EntityParachute> entity;

    public ItemParachute(String translationKey, String namespaceId, int id, Class<? extends EntityParachute> entity) {
        super(translationKey, namespaceId, id);
        this.entity = entity;
    }

    @Override
    public ItemStack onUseItem(ItemStack itemstack, World world, Player player) {
        if (player.fallDistance > 0 && !player.isInWater() && !EnvironmentHelper.isClientWorld()) {

            EntityParachute cloud;
            try {
                cloud = entity.getConstructor(World.class).newInstance(world);
            } catch (Exception e) {
                AetherMod.LOGGER.error("Failed to spawn parachute cloud!");
                throw new RuntimeException(e);
            }

            cloud.absMoveTo(player.x, player.y - 2, player.z, (player.yRot), (player.xRot));
            world.entityJoinedWorld(cloud);
            ParticleMaker.spawnParticle(world, cloud.getPathParticle(), player.x + 0.5, player.y + 1, player.z + 0.5, 0.0, 0.0, 0.0, 0);

            player.startRiding(cloud);

            if (!EnvironmentHelper.isServerEnvironment()) {
                player.triggerAchievement(AetherAchievements.PARACHUTE);
            }

            if (player.gamemode.toolDurability()) {
                if (itemstack.getMaxDamage() == 1) {
                    itemstack.consumeItem(player);
                } else {
                    itemstack.damageItem(1, player);
                }
            }
        }

        return itemstack;
    }
}
