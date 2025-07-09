package teamport.aether.items;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.accessory.api.HealthHelper;

public class ItemLifeShard extends Item {

    public ItemLifeShard(String translationKey, String namespaceId, int id) {
        super(translationKey, namespaceId, id);
    }
//

    @Override
    public ItemStack onUseItem(ItemStack itemstack, World world, Player player) {
        int extraHealth = HealthHelper.getExtraHealth(player);

        // to save on calculation further down
        boolean canHeal = player.getHealth() < player.getMaxHealth();
        boolean canGainExtraHealth = extraHealth < 20;

        // only processed if player can be healed or gain extra health
        if (!canHeal && !canGainExtraHealth) {
            return itemstack;
        }
        // consume the item if possible
        if (!itemstack.consumeItem(player)) return itemstack;
        if (!canGainExtraHealth) {
            player.heal(40);
            world.playSoundAtEntity(player, player, "aether:life.shard.chime", 0.45F, 0.6F);
            return itemstack;
        }
        // gives Player extra health
        HealthHelper.addExtraHealth(player, 2);
        // min to make damn sure we don't increase pitch and volume more than expected, because that's a recipe for earsplitting sound
        int extra_heart_amount = Math.min(extraHealth, 20);
        if (extra_heart_amount == 18) {
            world.playSoundAtEntity(player, player, "aether:life.shard.chime.final", 0.65F, 1.0F);
        } else {
            world.playSoundAtEntity(player, player, "aether:life.shard.chime", 0.45F + 0.02F * extra_heart_amount, 0.9F + 0.015F * extra_heart_amount);
        }
        player.heal(2);
        return itemstack;
    }
}
