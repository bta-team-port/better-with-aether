package teamport.aether.mixin.armor.wolf;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.material.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.item.AetherArmorMaterial;

@Mixin(value = Mob.class, remap = false)
public abstract class MobWolfNoKnockbackKnockBack {
    @WrapMethod(method = "knockBack")
    private void knockBack(Entity entity, int i, double d, double d1, Operation<Void> original) {
        if (!((Entity) (Object) this instanceof MobWolf)) {
            original.call(entity, i, d, d1);
            return;
        }
        ArmorMaterial material = ((MobWolf) (Object) this).getArmorMaterial();
        if (material != null && material.equals(AetherArmorMaterial.OBSIDIAN)) return;
        original.call(entity, i, d, d1);
    }
}
