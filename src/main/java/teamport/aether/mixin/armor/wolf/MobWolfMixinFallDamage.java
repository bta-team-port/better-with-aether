package teamport.aether.mixin.armor.wolf;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.material.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Mob.class, remap = false)
public abstract class MobWolfMixinFallDamage {
    @WrapMethod(method = "causeFallDamage")
    private void causeFallDamage(float distance, Operation<Void> original) {
        if (!((Mob) (Object) this instanceof MobWolf)) {
            original.call(distance);
            return;
        }
        ArmorMaterial material = ((MobWolf) (Object) this).getArmorMaterial();
        if (material == null || !material.equals(AetherArmorMaterial.GRAVITITE)) {
            original.call(distance);
        }
    }
}
