package teamport.aether.mixin.armor.wolf;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.material.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Mob.class, remap = false)
public abstract class MobWolfMixinDrowningImmunity {
    @ModifyReturnValue(method = "canBreatheUnderwater", at = @At("RETURN"))
    private boolean canBreatheUnderwater(boolean original) {
        if (!((Mob) (Object) this instanceof MobWolf)) return original;
        ArmorMaterial material = ((MobWolf) (Object) this).getArmorMaterial();
        if (material != null && material.equals(AetherArmorMaterial.NEPTUNE)) return true;
        return original;
    }
}
