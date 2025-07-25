package teamport.aether.mixin.accessory.quiver;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinNextArrow {


    @Shadow public ContainerInventory inventory;

    @WrapOperation(
            method = "getNextArrow",
            at=@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;armorItemInSlot(I)Lnet/minecraft/core/item/ItemStack;"
            )
    )
    public ItemStack checkAdditionalSlots(ContainerInventory instance, int slotId, Operation<ItemStack> original){
        ItemStack bodySlot = original.call(instance, slotId);
        return bodySlot != null ? bodySlot : instance.armorItemInSlot(5);
    }
}
