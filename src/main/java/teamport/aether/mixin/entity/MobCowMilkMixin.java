package teamport.aether.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.entity.animal.MobCow;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.items.AetherItems;
import teamport.aether.items.ItemBucketSkyrootEmpty;

@Mixin(value = MobCow.class, remap = false)
public abstract class MobCowMilkMixin {
    @ModifyReturnValue(method = "interact", at = @At(value = "RETURN"))
    private boolean interact(boolean original, Player player) {
        ItemStack itemstack = player.inventory.getCurrentItem();
        if (itemstack != null && itemstack.itemID == AetherItems.BUCKET_SKYROOT.id) {
            ItemBucketSkyrootEmpty.useBucket(player, new ItemStack(AetherItems.BUCKET_SKYROOT_MILK));
            return true;
        } else {
            return original;
        }
    }
}
