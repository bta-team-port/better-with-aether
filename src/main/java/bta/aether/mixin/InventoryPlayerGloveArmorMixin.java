package bta.aether.mixin;

import bta.aether.item.Accessories.base.ItemAccessoryGloves;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value= InventoryPlayer.class, remap = false)
public abstract class InventoryPlayerGloveArmorMixin {

    @Unique
    static private final int GLOVE_SLOT_INDEX = 10;

    @Unique
    static private final float BOOTS_PROT_PERCENTAGE = ((ItemArmor) Item.armorBootsIron).getArmorPieceProtectionPercentage();

    @Shadow
    public ItemStack[] armorInventory;

    @ModifyReturnValue(method = "getTotalProtectionAmount", at = @At("RETURN"))
    public float addGloveProtectionToProtectionAmount(float protectionPercentage, @Local DamageType damageType) {
        ItemStack glove_slot_contents = this.armorInventory[GLOVE_SLOT_INDEX];

        if (glove_slot_contents != null && glove_slot_contents.getItem() instanceof ItemAccessoryGloves) {
            ArmorMaterial glove_material = ((ItemAccessoryGloves) glove_slot_contents.getItem()).getArmorMaterial();
            protectionPercentage += glove_material.getProtection(damageType) * BOOTS_PROT_PERCENTAGE;
        }

        return protectionPercentage;
    }

    @Inject(method = "damageArmor(I)V", at = @At("HEAD"))
    public void damageGloves(int damage, CallbackInfo ci) {
        InventoryPlayer thisAs = (InventoryPlayer) (Object) this;
        thisAs.damageArmor(damage, GLOVE_SLOT_INDEX);
    }
}