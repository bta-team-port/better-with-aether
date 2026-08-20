package teamport.aether.mixin.armor.wolf;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.item.AetherArmorMaterial;

@Mixin(Mob.class)
public abstract class MobWolfNoKnockbackKnockBack {
    @WrapMethod(method = "knockBack")
    private void knockBack(Entity entity, int i, double d, double d1, Operation<Void> original) {
        if (!((Entity) (Object) this instanceof MobWolf)) {
            original.call(entity, i, d, d1);
            return;
        }
        ItemStack armor = ((MobWolf)(Object) this).getArmorItem();
        ArmorMaterial material = (armor != null && armor.getItem() instanceof IArmorItem) ? ((IArmorItem<?>) armor.getItem()).getArmorMaterial() : null;
        if (material != null && material.equals(AetherArmorMaterial.OBSIDIAN)) return;
        original.call(entity, i, d, d1);
    }
}
