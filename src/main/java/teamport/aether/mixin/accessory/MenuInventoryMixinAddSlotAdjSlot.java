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

import java.util.ArrayList;
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
            int armorPiece = 4 + i;
            ((MenuAbstractAccessor)menu).invokeAddSlot(new SlotAccessory(menu, inventory, inventory.getContainerSize() + i, 80, 8 + i * 18, armorPiece));
        }
    }

    /** Colin:
     * in the MAIN inventory (not including armor or crafting slots)
     * IDK what target does, but it always seems to be 0 for me
     *
     * @reason
     * So the target is 0 because the ScreenContainerAbstract is not resolving the targeting correctly
     * a such the target is always the inventory. To fix this a mixin is needed.
     *
     * @implNote
     * Due to gloves beeing now an armor piece target can return 0 or 2
     */
    @Inject(method = "getTargetSlots", at=@At("HEAD"), cancellable = true)
    public void getTargetSlots(InventoryAction action, Slot slot, int target, Player player, CallbackInfoReturnable<List<Integer>> cir) {
        // TODO MIXIN into ScreenContainerAbstract and fix the targeting, so it wont cause problems down the line
        if (slot.index >= 9 && slot.index <= 44 && slot.getItemStack() != null && slot.getItemStack().getItem() instanceof Accessory) {
            Accessory armorItem = (Accessory)slot.getItemStack().getItem();
            List<Integer> ints = new ArrayList();
            ints.add(41 + armorItem.getAccessoryTypes());
            cir.setReturnValue(ints);
        }
    }


}
