package teamport.aether.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.MobCow;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.item.AetherItems;
import teamport.aether.item.ItemBucketSkyrootEmpty;

@Mixin(value = Item.class, remap = false)
public abstract class MobCowMilkMixin {
    @ModifyReturnValue(
        method = "useOnEntity(Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/entity/Mob;)Z",
        at = @At(value = "RETURN")
    )
    private boolean useOnEntity(boolean original, ItemStack itemstack, Player player, Mob mob) {
        if (mob instanceof MobCow && itemstack.itemID == AetherItems.BUCKET_SKYROOT.id) {
            ItemBucketSkyrootEmpty.useBucket(player, new ItemStack(AetherItems.BUCKET_SKYROOT_MILK));
            return true;
        } else {
            return original;
        }
    }
}
