package teamport.aether.mixin.accessory;


import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.container.ContainerCrafting;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.player.inventory.slot.SlotResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.items.accessory.Accessory;
import teamport.aether.items.accessory.SlotAccessory;
import teamport.aether.mixin.accessors.MenuAbstractAccessor;

import java.util.List;

@Mixin(value = MenuInventory.class, remap = false)
public class MenuInventoryMixinAddSlotAdjSlot {


    @Shadow public ContainerInventory inventory;

    @Inject(method = "Lnet/minecraft/core/player/inventory/menu/MenuInventory;<init>(Lnet/minecraft/core/player/inventory/container/ContainerInventory;Z)V",
            at= @At(value = "INVOKE",
                    target = "Lnet/minecraft/core/player/inventory/menu/MenuInventory;slotsChanged(Lnet/minecraft/core/player/inventory/container/Container;)V"
            )
    )
    public void addingAndAdjustingSlots(ContainerInventory inventory, boolean active, CallbackInfo ci){
        MenuInventory menu = (MenuInventory) (Object) this;
        // fixing the crafting inventory
        for(Slot slot: menu.slots){
            Container contain = slot.getContainer();
            if(contain instanceof ContainerCrafting){
                slot.x += 11;
                continue;
            }
            if(slot instanceof SlotResult){
                slot.x += 8;
            }
        }

        // adding new accessories
        for(int i = 0; i < 4; ++i) {
            ((MenuAbstractAccessor)menu).invokeAddSlot(new SlotAccessory(menu, inventory, inventory.getContainerSize() + i, 80, 8 + i * 18, i));
        }
    }

    /**
     * @implNote
     * Should the targeting break, then the ScreenContainerAbstractMixinTargetFix need
     * to be fixed, more details there.
     * */

//    @Inject(method = "getTargetSlots", at=@At("RETURN"), cancellable = true)
//    public void getTargetSlots(InventoryAction action, Slot slot, int target, Player player, CallbackInfoReturnable<List<Integer>> cir) {
//        // in the MAIN inventory (not including armor or crafting slots)
//        // IDK what target does, but it always seems to be 0 for me
//        //TODO figure out how this system works
//        if (slot.index >= 9 && slot.index <= 44 && slot.getItemStack() != null && slot.getItemStack().getItem() instanceof Accessory && target == 2) {
//            MenuInventory menu = (MenuInventory) (Object) this;
//            List<Integer> target_slots = menu.getSlots(45, 4, false);
//            target_slots.addAll(cir.getReturnValue());
//            cir.setReturnValue(target_slots);
//        }
//    }


}
