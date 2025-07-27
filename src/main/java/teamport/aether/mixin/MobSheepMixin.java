package teamport.aether.mixin;

import net.minecraft.core.entity.animal.MobSheep;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.items.AetherItemTags;

@Mixin(value = MobSheep.class, remap = false)
public class MobSheepMixin {

    @Inject(method = "isFavouriteItem", at = @At(value = "HEAD"), cancellable = true)
    public void isFavouriteItem(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir){
        if (itemStack != null && itemStack.getItem().hasTag(AetherItemTags.NATURE_STAFF_FOLLOW)) {
            cir.setReturnValue(true);
        }
    }
}
