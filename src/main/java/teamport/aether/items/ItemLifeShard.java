package teamport.aether.items;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.accessory.api.HealthHelper;
import turniplabs.halplibe.helper.SoundHelper;

public class ItemLifeShard extends Item {

    public ItemLifeShard(String translationKey, String namespaceId, int id) {
        super(translationKey, namespaceId, id);
    }

    @Override
    public ItemStack onUseItem(ItemStack itemstack, World world, Player player) {
        int extraHealth = HealthHelper.getExtraHealth(player);
        if (itemstack.consumeItem(player)){
            if(extraHealth < 20) {
            // min to make damn sure we don't increase pitch and volume more than expected, because that's a recipe for earsplitting sound
            int extra_heart_amount = Math.min(extraHealth, 20);
            if (extra_heart_amount == 18)
                world.playSoundAtEntity(player,player, "aether.life_shard_chime_final",0.65F,1.0F);
            else
                world.playSoundAtEntity(player,player,"aether.life_shard_chime", 0.45F + 0.02F * extra_heart_amount,0.9F + 0.015F * extra_heart_amount);

            // gives Player extra health
            HealthHelper.addExtraHealth(player, 2);
            }
            player.heal(2);
        }
        return itemstack;
    }
}
