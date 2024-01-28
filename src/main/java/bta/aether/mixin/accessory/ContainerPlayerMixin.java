package bta.aether.mixin.accessory;

import bta.aether.accessory.API.Accessory;
import bta.aether.accessory.AccessorySlot;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.ContainerPlayer;
import net.minecraft.core.player.inventory.InventoryCrafting;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.player.inventory.slot.SlotArmor;
import net.minecraft.core.player.inventory.slot.SlotCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = ContainerPlayer.class, remap = false)
public abstract class ContainerPlayerMixin extends Container {

	@Unique
	private static final String[] slot_types = {"pendant","cape","shield","misc","ring","ring","gloves","misc"};

	@Redirect(method="<init>(Lnet/minecraft/core/player/inventory/InventoryPlayer;Z)V", at=@At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/ContainerPlayer;addSlot(Lnet/minecraft/core/player/inventory/slot/Slot;)V"))
	private void changeTopSlotPositions(ContainerPlayer instance, Slot slot) {

		// the player 'doll' display area is the width of 3 slots = 3 * 18 = 54 -> shift 54 to display on the right
		if (slot instanceof SlotArmor)
			slot.xDisplayPosition += 54;

		// slotCrafting is actually only the result
		if (slot instanceof SlotCrafting) {
			//I fr guessed these
			slot.xDisplayPosition -= 10;
			slot.yDisplayPosition += 26;
		}

		// crafting matrix
		if (slot.getInventory() instanceof InventoryCrafting) {
			slot.yDisplayPosition -= 18;
			slot.xDisplayPosition += 37;
		}

		instance.addSlot(slot);
	}

	// add all custom accessory slots
	@Inject(method = "<init>(Lnet/minecraft/core/player/inventory/InventoryPlayer;Z)V", at = @At(value = "TAIL"))
	private void addSlots(InventoryPlayer inv, boolean par2, CallbackInfo ci) {
		// 36 default + 4 default armor
		// wait but what about the crafting inventory?? I forgot about that???
		// but it crashes if I change it!!!
		int slotnum = 40;

		// slot 5 is the helmet slot, we will place the accessories relative to it.
		int startX = this.getSlot(5).xDisplayPosition;
		int startY = this.getSlot(5).yDisplayPosition;
		int slot_w = 18;

		for (int i = 0; i < 8; i++) {
			int row_num = (i % 4);
			// col 0 is the already placed armor slots
			int col_num = (i / 4) + 1;
			addSlot(new AccessorySlot(inv, slotnum++,startX + slot_w * col_num, startY + slot_w * row_num, slot_types[i]));
		}
	}

	@Inject(method = "getTargetSlots", at = @At(value = "RETURN"), cancellable = true)
	public void getTargetSlots(InventoryAction action, Slot slot, int target, EntityPlayer player, CallbackInfoReturnable<List<Integer>> cir) {
		// in the MAIN inventory (not including armor or crafting slots)
		// IDK what target does, but it always seems to be 0 for me
		if (slot.id >= 9 && slot.id <= 44 && slot.getStack() != null && slot.getStack().getItem() instanceof Accessory && target == 0) {
			List<Integer> target_slots = this.getSlots(45, 8, false);
			target_slots.addAll(cir.getReturnValue());
			cir.setReturnValue(target_slots);
		}
	}
}
