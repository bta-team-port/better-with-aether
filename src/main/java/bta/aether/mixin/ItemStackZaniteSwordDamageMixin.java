package bta.aether.mixin;

import bta.aether.item.AetherItems;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ItemStack.class, remap = false)
public class ItemStackZaniteSwordDamageMixin {

    @ModifyReturnValue(method = "getDamageVsEntity", at=@At("RETURN"))
    public int changeDamageIfZanite(int original) {
        ItemStack thisAs = (ItemStack) (Object) this;
        if (thisAs != null && thisAs.getItem().id == AetherItems.toolSwordZanite.id) {
            float durability_progress = (float) thisAs.getMetadata() / thisAs.getMaxDamage();

            // we will 'lerp' between these two
            float starting_damage = 0.0F;
            float ending_damage   = 6.0F;

            return (int) (4.0F + (starting_damage * (1.0 - durability_progress) + (ending_damage * durability_progress) * 2.0F));
        }
        return original;
    }

}
