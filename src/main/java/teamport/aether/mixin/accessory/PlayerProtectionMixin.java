package teamport.aether.mixin.accessory;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.entity.IArmorWearing;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.item.accessory.IAccessoryWearing;

@Mixin(IArmorWearing.class)
public interface PlayerProtectionMixin {

    @ModifyReturnValue(method = "getTotalProtectionAmount", at = @At("RETURN"))
    private float addAccessoryProtection(float originalProtection, DamageType damageType) {
        if (this instanceof IAccessoryWearing<?> accessoryWearing) {
            return originalProtection + accessoryWearing.getTotalAccessoryProtectionAmount(damageType);
        }
        return originalProtection;
    }

    @Inject(method = "damageArmor(I)V", at = @At("HEAD"))
    default void damageAccessoriesOnHit(int damage, CallbackInfo ci) {
        if (this instanceof IAccessoryWearing<?> accessoryWearing) {
            accessoryWearing.damageAccessories(damage);
        }
    }
}
