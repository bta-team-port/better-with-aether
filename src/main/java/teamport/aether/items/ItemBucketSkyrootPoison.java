package teamport.aether.items;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.AetherAchievements;

public class ItemBucketSkyrootPoison extends Item {
    public ItemBucketSkyrootPoison(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
        this.maxStackSize = 1;
    }

    //TODO Add in poisoning here when effect is added
    public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
        entityplayer.triggerAchievement(AetherAchievements.POISON);
        return new ItemStack(AetherItems.BUCKET_SKYROOT);
    }

}
