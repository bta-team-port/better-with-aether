package bta.aether.mixin.accessory;

import bta.aether.accessory.API.Accessory;
import com.mojang.nbt.ListTag;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value= InventoryPlayer.class, remap = false)
public abstract class InventoryPlayerMixin implements IInventory {

	@Shadow
	public ItemStack[] armorInventory;

	@Shadow
	public ItemStack[] mainInventory;

	@Shadow
	public EntityPlayer player;

	// Armor inventory expanded to fit the extra 8 accessory slots
	@Inject(method = "<init>", at = @At("TAIL"))
	public void setNewSize(EntityPlayer player, CallbackInfo ci) {
		this.armorInventory = new ItemStack[4 + 8];
	}

	// change hardcoded size of the armor inventory
	@ModifyConstant(method = "readFromNBT", constant = @Constant(intValue = 4), require = 1)
	private int modifyArmourSize(int original) {
		return 4 + 8;
	}

	// call accessory added on player loaded
	@Inject(method = "readFromNBT", at = @At("TAIL"))
	public void activateAccessories(ListTag tags, CallbackInfo ci) {
        for (ItemStack item : this.armorInventory) {
            if (item != null && item.getItem() instanceof Accessory) {
				((Accessory) item.getItem()).onAccessoryAdded(this.player, item);
            }
        }
	}

	// call accessory added/removed
	@Inject(method = "setInventorySlotContents", at = @At("HEAD"))
	public void onSetInventoryItem(int i, ItemStack newItem, CallbackInfo ci) {
		if (i < this.mainInventory.length) {
			return;
		}
		if (i >= this.armorInventory.length) {
			EntityPlayer player = ((InventoryPlayer)(Object)this).player;

			int index = i - this.mainInventory.length;
			ItemStack oldItem = this.armorInventory[index];

			// this is only called when we SWAP an item
			if (oldItem != null && oldItem.getItem() instanceof Accessory) {
				((Accessory) oldItem.getItem()).onAccessoryRemoved(player, oldItem);
			}

			if (newItem != null && newItem.getItem() instanceof Accessory) {
				((Accessory) newItem.getItem()).onAccessoryAdded(player, newItem);
			}
		}
	}
}
