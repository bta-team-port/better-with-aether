package teamport.aether.mixin.armor.player.zanite;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.entity.IArmorWearing;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.item.AetherArmorMaterial;

@Mixin(value = IArmorWearing.class, remap = false)
public interface ContainerInventoryMixinZanite {
    @WrapOperation(method = "getTotalProtectionAmount(Lnet/minecraft/core/util/helper/DamageType;)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/material/ArmorMaterial;getProtection(Lnet/minecraft/core/util/helper/DamageType;)F"))
    private float modifyProtectionAmount(ArmorMaterial instance, DamageType damageType, Operation<Float> original, @Local ItemStack itemStack) {
        if (instance != AetherArmorMaterial.ZANITE || !((Object) this instanceof Player)) {
            return original.call(instance, damageType);
        }
        float durabilityProgress = (float) itemStack.getMetadata() / instance.durability;
        return MathHelper.lerp(instance.getProtection(damageType), AetherArmorMaterial.ZANITE_BROKEN.getProtection(damageType), durabilityProgress);
    }
}
