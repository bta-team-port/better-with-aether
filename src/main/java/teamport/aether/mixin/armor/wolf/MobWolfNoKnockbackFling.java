package teamport.aether.mixin.armor.wolf;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.material.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.item.AetherArmorMaterial;

@Mixin(value = Entity.class)
public abstract class MobWolfNoKnockbackFling {
    @WrapMethod(method = "fling")
    private void fling(double xd, double yd, double zd, float pushTime, Operation<Void> original) {
        if (!((Entity) (Object) this instanceof MobWolf)) {
            original.call(xd, yd, zd, pushTime);
            return;
        }
        net.minecraft.core.item.ItemStack armor = ((MobWolf)(Object) this).getArmorItem();
        ArmorMaterial material = (armor != null && armor.getItem() instanceof net.minecraft.core.item.IArmorItem) ? ((net.minecraft.core.item.IArmorItem<?>) armor.getItem()).getArmorMaterial() : null;
        if (material != null && material.equals(AetherArmorMaterial.OBSIDIAN)) return;
        original.call(xd, yd, zd, pushTime);
    }
}
