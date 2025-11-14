package teamport.aether.items;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.effects.helper.HealthHelper;
import teamport.aether.achievements.AetherAchievements;

import static teamport.aether.AetherConfig.EXTRA_HEALTH;

public class ItemLifeShard extends Item {
    private static final float ADD_VOLUME = 0.4F / EXTRA_HEALTH;
    private static final float ADD_PITCH = 0.3F / EXTRA_HEALTH;


    public ItemLifeShard(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
    }

    @Override
    public ItemStack onUseItem(ItemStack itemstack, World world, Player player) {
        int extraHealth = HealthHelper.getExtraHealth(player);

        // to save on calculation further down
        boolean canHeal = player.getHealth() < player.getMaxHealth();
        boolean canGainExtraHealth = extraHealth < EXTRA_HEALTH;

        // only processed if player can be healed or gain extra health
        if (!canHeal && !canGainExtraHealth) {
            return itemstack;
        }
        // consume the item if possible
        if (!itemstack.consumeItem(player)) return itemstack;
        if (!canGainExtraHealth) {
            player.heal(player.getMaxHealth());
            world.playSoundAtEntity(player, player, "aether:life.shard.chime", 0.45F, 0.6F);
            return itemstack;
        }
        int gainHealth = extraHealth + 2 <= EXTRA_HEALTH ? 2 : 1;

        // gives Player extra health
        HealthHelper.addExtraHealth(player, gainHealth);
        // min to make damn sure we don't increase pitch and volume more than expected, because that's a recipe for earsplitting sound
        int extraHealthCapped = Math.min(extraHealth, EXTRA_HEALTH);

        if (extraHealth >= EXTRA_HEALTH - 2) {
            world.playSoundAtEntity(player, player, "aether:life.shard.chime.final", 0.65F, 1.0F);
            player.triggerAchievement(AetherAchievements.MAX_LIFE);
        } else {
            world.playSoundAtEntity(player, player, "aether:life.shard.chime", 0.45F + ADD_VOLUME * extraHealthCapped, 0.9F + ADD_PITCH * extraHealthCapped);
        }
        player.heal(2);
        return itemstack;
    }
}
