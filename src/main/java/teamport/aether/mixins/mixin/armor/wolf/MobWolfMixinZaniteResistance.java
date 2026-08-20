package teamport.aether.mixins.mixin.armor.wolf;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.IArmorWearing;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.item.AetherArmorMaterial;

@Mixin(IArmorWearing.class)
public interface MobWolfMixinZaniteResistance {
    @WrapOperation(method = "getTotalProtectionAmount(Lnet/minecraft/core/util/helper/DamageType;)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/material/ArmorMaterial;getProtection(Lnet/minecraft/core/util/helper/DamageType;)F"))
    private float reduceWolfDamage(ArmorMaterial instance, DamageType damageType, Operation<Float> original) {
        if (instance != AetherArmorMaterial.ZANITE || !((Object) this instanceof MobWolf wolf)) {
            return original.call(instance, damageType);
        }
        float healthPercentage = (float) wolf.getHealth() / wolf.getMaxHealth();
        return MathHelper.lerp(AetherArmorMaterial.ZANITE_BROKEN.getProtection(damageType) * 1.5f, instance.getProtection(damageType), healthPercentage);
    }
}
