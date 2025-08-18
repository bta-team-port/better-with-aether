package teamport.aether.mixin.accessory;


import net.minecraft.client.gui.container.ScreenContainerAbstract;
import org.spongepowered.asm.mixin.Mixin;

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
