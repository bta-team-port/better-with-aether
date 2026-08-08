package teamport.aether.item;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;

public class ItemBucketSkyrootIceCream extends ItemFood {
    public ItemBucketSkyrootIceCream(String name, String namespaceId, int id, int healAmount, int ticksPerHeal) {
        super(name, namespaceId, id, healAmount, ticksPerHeal, false, 1);
        this.maxStackSize = 1;
    }

    @Override
    public ItemStack onUse(@NonNull ItemStack itemstack, @NonNull World world, @NonNull Player entityplayer) {
        if (entityplayer.getHealth() < entityplayer.getMaxHealth()) {
            super.onUse(itemstack, world, entityplayer);
            return new ItemStack(AetherItems.BUCKET_SKYROOT);
        } else {
            return itemstack;
        }
    }
}
