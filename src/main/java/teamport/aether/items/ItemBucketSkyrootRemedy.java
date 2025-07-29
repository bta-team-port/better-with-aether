package teamport.aether.items;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.AetherAchievements;

public class ItemBucketSkyrootRemedy extends ItemFood {
    public ItemBucketSkyrootRemedy(String name, String namespaceId, int id) {
        super(name, namespaceId, id, 1, 1, false, 1);
        this.maxStackSize = 1;
    }

    //TODO Cure poison effect
    public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
        entityplayer.eatFood(this);
        entityplayer.triggerAchievement(AetherAchievements.REMEDY);
        return new ItemStack(AetherItems.BUCKET_SKYROOT);
    }

}
