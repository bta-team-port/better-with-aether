package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.items.accessory.pendant.ItemPendant;

@Mixin(value = ContainerInventory.class, remap = false)
public abstract class ContainerInventoryGetProtectionOfPendantMixin {
    @WrapOperation(method = "getTotalProtectionAmount", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/IArmorItem;getArmorPiece()I"))
    private int ignoreSlotEqualityForTrickets(IArmorItem instance, Operation<Integer> original, @Local int i) {
        if (instance instanceof ItemPendant) {
            return i;
        }
        return original.call(instance);
    }
}
