package teamport.aether.mixin.accessory.quiver;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.item.ItemBow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ItemBow.class)
public abstract class ItemBowMixinQuiverSlotFix {
    @WrapOperation(method = "onUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;armorItemInSlot(I)Lnet/minecraft/core/item/ItemStack;"))
    private ItemStack checkAdditionalSlots(ContainerInventory instance, int slotId, Operation<ItemStack> original) {
        ItemStack bodyItem = original.call(instance, slotId);
        ItemStack capeItem = instance.armorItemInSlot(5);
        if (bodyItem == null || (bodyItem.itemID != Items.ARMOR_QUIVER_GOLD.id && 0 >= bodyItem.getMaxDamage() - bodyItem.getMetadata())) {
            return capeItem;
        }
        return bodyItem;
    }
}
