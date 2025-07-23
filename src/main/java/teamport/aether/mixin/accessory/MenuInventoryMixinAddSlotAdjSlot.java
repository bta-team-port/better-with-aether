package teamport.aether.mixin.accessory;


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
import teamport.aether.accessory.SlotAccessory;
import teamport.aether.mixin.accessors.MenuAbstractAccessor;

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
            int armorType = 6 - i;
            ((MenuAbstractAccessor)menu).invokeAddSlot(new SlotAccessory(menu, inventory, inventory.getContainerSize() - 1 - i, 80, 8 + i * 18, armorType));
        }


    }
}
