package teamport.aether.item;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.AetherGlobals;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.entity.vehicle.parachute.EntityParachute;
import teamport.aether.helper.ParticleMaker;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class ItemParachute extends Item {
    Class<? extends EntityParachute> entity;

    public ItemParachute(String translationKey, String namespaceId, int id, Class<? extends EntityParachute> entity, int uses) {
        super(translationKey, namespaceId, id);
        this.entity = entity;
        this.maxStackSize = 1;
        this.setMaxDamage(uses - 1);
    }

    @Override
    public ItemStack onUse(@NonNull ItemStack itemstack, @NonNull World world, @NonNull Player player) {
        if (player.fallDistance > 2 && !player.isSneaking() && !player.isInWater() && !EnvironmentHelper.isMultiplayerClient()) {

            EntityParachute cloud;
            try {
                cloud = entity.getConstructor(World.class).newInstance(world);
            } catch (Exception e) {
                AetherGlobals.LOGGER.error("Failed to spawn parachute cloud!");
                throw new RuntimeException(e);
            }
            cloud.moveTo(player.x, player.y - 0.2, player.z, player.yRot + 180.0F, 0.0F);
            world.entityJoinedWorld(cloud);

            ParticleMaker.spawnParticle(world, cloud.getPathParticle(), player.x + 0.5, player.y + 1, player.z + 0.5, 0.0, 0.0, 0.0, 0);

            player.startRiding(cloud);

            if (!EnvironmentHelper.isMultiplayerServer()) {
                player.triggerAchievement(AetherAchievements.PARACHUTE);
            }

            if (player.gamemode.hasToolDurability()) {
                if (itemstack.getMaxDamage() == 0) {
                    itemstack.consumeItem(player);
                } else {
                    itemstack.damageItem(1, player);
                }
            }
        }

        return itemstack;
    }
}
