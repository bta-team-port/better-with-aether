package teamport.aether.mixin.accessory;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemQuiver;
import net.minecraft.core.item.ItemQuiverEndless;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.container.ContainerCrafting;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.player.inventory.slot.SlotArmor;
import net.minecraft.core.player.inventory.slot.SlotResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.AetherMod;
import teamport.aether.items.AetherItemTags;
import teamport.aether.items.accessory.IAccessory;
import teamport.aether.items.accessory.ItemAccessoryArmor;
import teamport.aether.items.accessory.SlotAccessory;
import teamport.aether.mixin.accessors.MenuAbstractAccessor;
import teamport.aether.mixin.accessors.SlotAccessor;
import teamport.aether.mixin.accessors.SlotArmorAccessor;

import java.util.ArrayList;
import java.util.List;

import static teamport.aether.items.accessory.SlotAccessory.*;

@Mixin(value = MenuInventory.class, remap = false)
public abstract class MenuInventoryMixinAddSlotAdjSlot {
    @Shadow
    public ContainerInventory inventory;
    @Inject(method = "<init>(Lnet/minecraft/core/player/inventory/container/ContainerInventory;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/menu/MenuInventory;slotsChanged(Lnet/minecraft/core/player/inventory/container/Container;)V"))
    private void addingAndAdjustingSlots(ContainerInventory inventory, boolean active, CallbackInfo ci) {
        MenuInventory menu = (MenuInventory) (Object) this;
        for (int i = 0; i < menu.slots.size(); i++) {
            Slot slot = menu.slots.get(i);
            Container contain = slot.getContainer();
            // fixing the crafting inventory
            if (contain instanceof ContainerCrafting) {
                slot.x += 12;
            }
            if (slot instanceof SlotResult) {
                slot.x += 9;
            }
            //because getContainerSize now returns 44, both slot and index need to be adjusted for armor slot to work.
            if (slot instanceof SlotArmor) {
                SlotArmor newArmorSlot = new SlotArmor(menu, slot.getContainer(), ((SlotAccessor) slot).getSlot() - 4, slot.x, slot.y, ((SlotArmorAccessor) slot).getArmorType());
                newArmorSlot.index = i;
                menu.slots.set(menu.slots.indexOf(slot), newArmorSlot);
            }
        }
        // adding new accessories
        for (int i = 0; i < 4; ++i) {
            int armorPiece = 4 + i;
            // staring where armor ends
            ((MenuAbstractAccessor) menu).invokeAddSlot(new SlotAccessory(menu, inventory, inventory.getContainerSize() - 4 + i, 80, 8 + i * 18, armorPiece));
        }
    }
    /**
     * in the MAIN inventory (not including armor or crafting slots)
     * IDK what target does, but it always seems to be 0 for me - Colin
     * <br>
     * <br>
     * So the target is 0 because the ScreenContainerAbstract is not resolving the targeting correctly
     * as such the target is always the inventory. ScreenContainerAbstractMixinTargetAccessory
     * alters the target to be always 2 for accessories - Redart15
     */
    @ModifyReturnValue(method = "getTargetSlots", at = @At("RETURN"))
    private List<Integer> accessoryTargets(List<Integer> original, InventoryAction action, Slot slot, int target, Player player) {
        if (slot.index < 9 || slot.index > 44 || target == 1 || slot.getItemStack() == null || !(slot.getItemStack().getItem() instanceof IAccessory || slot.getItemStack().getItem().hasTag(AetherItemTags.TRINKET))) {
            return original;
        }
        Item accessory = slot.getItemStack().getItem();
        List<Integer> ints = new ArrayList<>();
        if (accessory instanceof ItemAccessoryArmor) {
            ints.add(AetherMod.ARMOR_START_INDEX + ((ItemAccessoryArmor) accessory).getSlotID());
        }
        if (accessory.hasTag(AetherItemTags.TRINKET)) {
            ints.add(AetherMod.ARMOR_START_INDEX + TRINKET_1_SLOT);
            ints.add(AetherMod.ARMOR_START_INDEX + TRINKET_2_SLOT);
        }
        return ints;
    }
    // allow quiver to be shift clicked in either the body or the cape slot
    @Inject(method = "getTargetSlots", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER))
    private void quiverTarget(InventoryAction action, Slot slot, int target, Player player, CallbackInfoReturnable<List<Integer>> cir, @Local IArmorItem armorItem, @Local List<Integer> ints) {
        if (!(armorItem instanceof ItemQuiver) && !(armorItem instanceof ItemQuiverEndless)) {
            return;
        }
        ints.add(AetherMod.ARMOR_START_INDEX + CAPE_SLOT);
    }
}
