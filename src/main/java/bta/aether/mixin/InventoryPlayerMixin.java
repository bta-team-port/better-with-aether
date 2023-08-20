package bta.aether.mixin;

import bta.aether.Aether;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = InventoryPlayer.class, remap = false)
public abstract class InventoryPlayerMixin {

    @Shadow public ItemStack[] armorInventory;

    @Redirect(
            method = "getTotalProtectionAmount",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/item/material/ArmorMaterial;getProtection(Lnet/minecraft/core/util/helper/DamageType;)F"
            )
    )
    private float changeProtection(ArmorMaterial instance, DamageType damageType, @Local(ordinal = 0) ItemArmor armor, @Local(ordinal = 0) int i) {
        if (instance == Aether.armorzanite) {
            float durability = (float) armorInventory[i].getMetadata() / ((float) armor.getMaxDamage() * 0.8f);
            durability = MathHelper.clamp(durability, 0.0f, 1.0f);
            return MathHelper.lerp(ArmorMaterial.iron.getProtection(damageType), ArmorMaterial.gold.getProtection(damageType), durability);
        } else {
            return instance.getProtection(damageType);
        }
    }
}
