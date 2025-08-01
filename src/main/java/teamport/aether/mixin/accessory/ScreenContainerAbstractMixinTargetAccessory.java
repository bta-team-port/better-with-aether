package teamport.aether.mixin.accessory;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.container.ScreenContainerAbstract;
import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.items.accessory.IAccessory;

@Mixin(value = ScreenContainerAbstract.class, remap = false)
public class ScreenContainerAbstractMixinTargetAccessory {

//    @WrapOperation(
//            method = "clickInventory",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/player/controller/PlayerController;handleInventoryMouseClick(ILnet/minecraft/core/InventoryAction;[ILnet/minecraft/core/entity/player/Player;)Lnet/minecraft/core/item/ItemStack;"
//            )
//    )
//    private ItemStack adjustTargetForAccessory(
//            PlayerController instance,
//            int containerID,
//            InventoryAction action,
//            int[] args,
//            Player player,
//            Operation<ItemStack> original
//    ) {
//        ScreenContainerAbstract screen = (ScreenContainerAbstract) (Object) this;
//        int slotId = args[0];
//        Slot slot = screen.inventorySlots.getSlot(slotId);
//        ItemStack stackInSlot = slot != null ? slot.getItemStack() : null;
//        Item itemInSlot = stackInSlot != null ? stackInSlot.getItem() : null;
//        if (screen.inventorySlots instanceof MenuInventory && itemInSlot instanceof IAccessory) {
//            args[1] = 2;
//        }
//        return original.call(instance, containerID, action, args, player);
//    }
}
