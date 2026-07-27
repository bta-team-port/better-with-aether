package teamport.aether.item;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.effect.api.IHasEffects;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.effect.AetherEffects;

public class ItemBucketSkyrootPoison extends Item {
    public ItemBucketSkyrootPoison(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
        this.maxStackSize = 1;
    }

    @Override
    public ItemStack onUse(ItemStack itemstack, World world, Player entityplayer) {
        entityplayer.triggerAchievement(AetherAchievements.POISON);
        IHasEffects<?> effectPlayer = (IHasEffects<?>) entityplayer;
        AetherEffects.add((Mob) effectPlayer, AetherEffects.poisonEffect, 4);
        return new ItemStack(AetherItems.BUCKET_SKYROOT);
    }
}
