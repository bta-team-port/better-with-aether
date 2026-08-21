package teamport.aether.mixin.accessory;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.item.accessory.IAccessoryEffects;

import java.util.Arrays;

@Mixin(ContainerInventory.class)
public abstract class ContainerInventoryMixinAccessory implements IContainerInventoryAether {
    @Unique
    private static final int AETHER_ACCESSORY_SLOT_OFFSET = 104;

    @Shadow
    @Final
    @NonNull
    public Player player;
    @Shadow
    @Final
    public @Nullable ItemStack @NonNull [] armorInventory;
    @Shadow
    @Final
    public @Nullable ItemStack @NonNull [] mainInventory;

    @Unique
    private final ItemStack[] accessoryInventory = new ItemStack[4];

    @Override
    public ItemStack[] aether$getAccessoryInventory() {
        return accessoryInventory;
    }

    @ModifyReturnValue(method = "getContainerSize", at = @At("RETURN"))
    private int modifyContainerSize(int original) {
        return original + accessoryInventory.length;
    }

    @WrapMethod(method = "getItem")
    private ItemStack getAccessoryItem(int slot, Operation<ItemStack> original) {
        int accessoryIndex = getAccessoryIndex(slot);
        if (accessoryIndex >= 0) {
            return accessoryInventory[accessoryIndex];
        }
        return original.call(slot);
    }

    @Inject(method = "save(Lcom/mojang/nbt/tags/ListTag;)Lcom/mojang/nbt/tags/ListTag;", at = @At("RETURN"))
    private void saveAccessories(ListTag parentTag, @NonNull CallbackInfoReturnable<ListTag> cir) {
        ListTag result = cir.getReturnValue();
        for (int slot = 0; slot < accessoryInventory.length; ++slot) {
            ItemStack itemStack = accessoryInventory[slot];
            if (itemStack != null) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putByte("Slot", (byte) (AETHER_ACCESSORY_SLOT_OFFSET + slot));
                itemStack.writeToNBT(itemTag);
                result.addTag(itemTag);
            }
        }
    }

    @Inject(method = "load(Lcom/mojang/nbt/tags/ListTag;)V", at = @At("TAIL"))
    private void loadAccessories(@NonNull ListTag parentTag, CallbackInfo ci) {
        for (int i = 0; i < parentTag.tagCount(); ++i) {
            CompoundTag itemTag = (CompoundTag) parentTag.tagAt(i);
            int slot = (itemTag.getByte("Slot") & 255) - AETHER_ACCESSORY_SLOT_OFFSET;
            if (slot >= 0 && slot < accessoryInventory.length) {
                accessoryInventory[slot] = ItemStack.readItemStackFromNbt(itemTag);
            }
        }
        for (ItemStack item : armorInventory) {
            if (item != null && item.getItem() instanceof IAccessoryEffects iAccessoryEffects) {
                iAccessoryEffects.addEffect(player, item);
            }
        }
        for (ItemStack item : accessoryInventory) {
            if (item != null && item.getItem() instanceof IAccessoryEffects iAccessoryEffects) {
                iAccessoryEffects.addEffect(player, item);
            }
        }
    }

    @Inject(method = "clear", at = @At("TAIL"))
    private void clearAccessories(CallbackInfo ci) {
        Arrays.fill(accessoryInventory, null);
    }

    @Inject(method = "decrementAnimations", at = @At("TAIL"))
    private void addArmorAnimations(CallbackInfo ci) {
        ContainerInventory inv = (ContainerInventory) (Object) this;
        for (int slot = 0; slot < accessoryInventory.length; slot++) {
            if (accessoryInventory[slot] != null && inv.player.world != null) {
                accessoryInventory[slot].updateAnimation(
                    inv.player.world,
                    inv.player,
                    slot + inv.mainInventory.length + inv.armorInventory.length,
                    false
                );
            }
        }
    }
    /**
     * @return
     * @reason 7.3_04 currently handles left click and drop differently from shift clicking.
     * To guarantee that the effect of the accessories is correctly remove on left click and drop
     * a mixin is needed into removeItem. - Redart15
     */
    @WrapMethod(method = "removeItem")
    private ItemStack updateEffects(int slot, int takeAmount, Operation<ItemStack> original) {
        if (slot >= this.mainInventory.length) {
            int accessoryIndex = getAccessoryIndex(slot);
            ItemStack itemStack = accessoryIndex >= 0
                ? accessoryInventory[accessoryIndex]
                : this.armorInventory[slot - this.mainInventory.length];
            if (itemStack != null && itemStack.getItem() instanceof IAccessoryEffects iAccessoryEffects) {
                iAccessoryEffects.removeEffect(player, itemStack);
            }
            if (accessoryIndex >= 0) {
                return removeAccessoryItem(accessoryIndex, takeAmount);
            }
        }
        return original.call(slot, takeAmount);
    }


    /**
     * @reason 7.3_04 currently handles left click and drop differently from shift clicking.
     * To guarantee that the effect of the accessories is correctly remove on shift clicking
     * a mixin is needed into setItem. - Redart15
     */
    @WrapMethod(method = "setItem")
    private void updateEffects(int slot, ItemStack stack, Operation<Void> original) {
        if (slot >= this.mainInventory.length) {
            int accessoryIndex = getAccessoryIndex(slot);
            ItemStack oldItem = accessoryIndex >= 0
                ? accessoryInventory[accessoryIndex]
                : this.armorInventory[slot - this.mainInventory.length];
            // this is only called when we SWAP an item
            if (oldItem != null && oldItem.getItem() instanceof IAccessoryEffects iAccessoryEffects) {
                iAccessoryEffects.removeEffect(player, oldItem);
            }
            if (accessoryIndex >= 0) {
                accessoryInventory[accessoryIndex] = stack;
                return;
            }
        }
        original.call(slot, stack);
    }

    @Inject(method = "dropAllItems", at = @At("TAIL"))
    private void dropAccessoryItems(CallbackInfo ci) {
        for (int slot = 0; slot < accessoryInventory.length; ++slot) {
            ItemStack itemStack = accessoryInventory[slot];
            if (itemStack != null) {
                player.dropItem(itemStack, true);
                accessoryInventory[slot] = null;
            }
        }
    }

    @WrapMethod(method = "containsItem")
    private boolean containsAccessory(ItemStack stack, Operation<Boolean> original) {
        for (ItemStack accessory : accessoryInventory) {
            if (accessory != null && accessory.isStackEqual(stack)) {
                return true;
            }
        }
        return original.call(stack);
    }

    @Inject(method = "transferAllContents", at = @At("TAIL"))
    private void transferAccessoryContents(ContainerInventory inventory, CallbackInfo ci) {
        ItemStack[] sourceAccessories = ((IContainerInventoryAether) inventory).aether$getAccessoryInventory();
        for (int slot = 0; slot < accessoryInventory.length; ++slot) {
            accessoryInventory[slot] = sourceAccessories[slot];
            sourceAccessories[slot] = null;
        }
    }

    @Unique
    private int getAccessoryIndex(int containerIndex) {
        int index = containerIndex - this.mainInventory.length - this.armorInventory.length;
        return index >= 0 && index < accessoryInventory.length ? index : -1;
    }

    @Unique
    private @Nullable ItemStack removeAccessoryItem(int index, int takeAmount) {
        ItemStack itemStack = accessoryInventory[index];
        if (itemStack == null) {
            return null;
        }
        if (itemStack.stackSize <= takeAmount) {
            accessoryInventory[index] = null;
            return itemStack;
        }
        ItemStack removed = itemStack.splitStack(takeAmount);
        if (itemStack.stackSize <= 0) {
            accessoryInventory[index] = null;
        }
        return removed;
    }
}
