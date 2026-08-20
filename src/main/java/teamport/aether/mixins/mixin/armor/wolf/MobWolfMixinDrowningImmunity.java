package teamport.aether.mixins.mixin.armor.wolf;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.item.AetherArmorMaterial;

@Mixin(Mob.class)
public abstract class MobWolfMixinDrowningImmunity {
    @ModifyReturnValue(method = "canBreatheUnderwater", at = @At("RETURN"))
    private boolean canBreatheUnderwater(boolean original) {
        if (!((Mob) (Object) this instanceof MobWolf)) return original;
        ItemStack armor = ((MobWolf)(Object) this).getArmorItem();
        ArmorMaterial material = (armor != null && armor.getItem() instanceof IArmorItem) ? ((IArmorItem<?>) armor.getItem()).getArmorMaterial() : null;
        if (material != null && material.equals(AetherArmorMaterial.NEPTUNE)) return true;
        return original;
    }
}
