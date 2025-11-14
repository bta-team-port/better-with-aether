package teamport.aether.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.entity.animal.MobSheep;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.items.AetherItemTags;

@Mixin(value = MobSheep.class, remap = false)
public abstract class MobSheepMixin {
    @ModifyReturnValue(method = "isFavouriteItem", at = @At(value = "RETURN"))
    public boolean isFavouriteItem(boolean original, ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem().hasTag(AetherItemTags.NATURE_STAFF_FOLLOW)) return true;
        return original;
    }
}
