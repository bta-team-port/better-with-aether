package teamport.aether.mixin.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.item.ItemKey;

@Mixin(value = EntityItem.class, remap = false)
public abstract class UnburnableItemMixin {
    @Shadow
    public ItemStack item;

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void preventGoldKeyBurn(Entity entity, int i, DamageType type, CallbackInfoReturnable<Boolean> cir) {
        if (this.item != null && this.item.getItem() instanceof ItemKey) {
            cir.setReturnValue(false);
        }
    }
}
