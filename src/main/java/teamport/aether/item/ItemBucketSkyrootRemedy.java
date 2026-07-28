package teamport.aether.item;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.effect.AetherEffects;

public class ItemBucketSkyrootRemedy extends Item {
    public ItemBucketSkyrootRemedy(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
        this.maxStackSize = 1;
    }

    @Override
    public ItemStack onUse(ItemStack itemstack, World world, Player entityplayer) {
        entityplayer.triggerAchievement(AetherAchievements.REMEDY);
        IHasEffects<?> effectPlayer = (IHasEffects<?>) entityplayer;
        AetherEffects.add((Mob) effectPlayer, AetherEffects.remedyEffect, 1);
        return new ItemStack(AetherItems.BUCKET_SKYROOT);
    }
}
