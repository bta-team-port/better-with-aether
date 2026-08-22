package teamport.aether.mixin.accessory;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
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
    private final ItemStack[] accessorySlots = new ItemStack[4];

    @Override
    public ItemStack[] aether$getAccessoryInventory() {
        return accessorySlots;
    }

    @ModifyReturnValue(method = "getContainerSize", at = @At("RETURN"))
    private int modifyContainerSize(int original) {
        return original + accessorySlots.length;
    }

    @Inject(method = "getItem", at = @At("HEAD"), cancellable = true)
    private void getAccessoryItem(int slot, CallbackInfoReturnable<ItemStack> cir) {
        int accessoryIndex = getAccessoryIndex(slot);
        if (accessoryIndex >= 0) {
            cir.setReturnValue(accessorySlots[accessoryIndex]);
        }
    }

    @Inject(method = "save(Lcom/mojang/nbt/tags/ListTag;)Lcom/mojang/nbt/tags/ListTag;", at = @At("RETURN"))
    private void saveAccessories(ListTag parentTag, @NonNull CallbackInfoReturnable<ListTag> cir) {
        ListTag result = cir.getReturnValue();
        for (int slot = 0; slot < accessorySlots.length; ++slot) {
            ItemStack itemStack = accessorySlots[slot];
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
            if (slot >= 0 && slot < accessorySlots.length) {
                accessorySlots[slot] = ItemStack.readItemStackFromNbt(itemTag);
            }
        }
        for (ItemStack item : accessorySlots) {
            if (item != null && item.getItem() instanceof IAccessoryEffects effects) {
                effects.addEffect(player, item);
            }
        }
    }

    @Inject(method = "clear", at = @At("HEAD"))
    private void clearAccessories(CallbackInfo ci) {
        for (ItemStack item : accessorySlots) {
            if (item != null && item.getItem() instanceof IAccessoryEffects effects) {
                effects.removeEffect(player, item);
            }
        }
        Arrays.fill(accessorySlots, null);
    }

    @Inject(method = "decrementAnimations", at = @At("TAIL"))
    private void addArmorAnimations(CallbackInfo ci) {
        ContainerInventory inv = (ContainerInventory) (Object) this;
        for (int slot = 0; slot < accessorySlots.length; slot++) {
            if (accessorySlots[slot] != null && inv.player.world != null) {
                accessorySlots[slot].updateAnimation(
                    inv.player.world,
                    inv.player,
                    slot + inv.mainInventory.length + inv.armorInventory.length,
                    false
                );
            }
        }
    }

    /**
     * @reason 7.3_04 currently handles left click and drop differently from shift clicking.
     * To guarantee that the effect of the accessories is correctly remove on left click and drop
     * a mixin is needed into removeItem. - Redart15
     */
    @Inject(method = "removeItem", at = @At("HEAD"), cancellable = true)
    private void updateEffectsOnRemove(int slot, int takeAmount, CallbackInfoReturnable<ItemStack> cir) {
        int accessoryIndex = getAccessoryIndex(slot);
        if (accessoryIndex < 0) {
            return;
        }

        ItemStack removed = removeAccessoryItem(accessoryIndex, takeAmount);

        if (removed != null && removed.getItem() instanceof IAccessoryEffects effects) {
            effects.removeEffect(player, removed);
        }
        cir.setReturnValue(removed);
    }


    /**
     * @reason 7.3_04 currently handles left click and drop differently from shift clicking.
     * To guarantee that the effect of the accessories is correctly remove on shift clicking
     * a mixin is needed into setItem. - Redart15
     */
    @Inject(method = "setItem", at = @At("HEAD"), cancellable = true)
    private void updateEffectsOnSet(int slot, ItemStack stack, CallbackInfo ci) {
        int accessoryIndex = getAccessoryIndex(slot);
        if (accessoryIndex < 0) {
            return;
        }

        ItemStack oldItem = accessorySlots[accessoryIndex];
        if (oldItem != null && oldItem.getItem() instanceof IAccessoryEffects oldEffects) {
            oldEffects.removeEffect(player, oldItem);
        }

        accessorySlots[accessoryIndex] = stack;

        if (stack != null && stack.getItem() instanceof IAccessoryEffects newEffects) {
            newEffects.addEffect(player, stack);
        }

        ci.cancel();
    }

    @Inject(method = "dropAllItems", at = @At("TAIL"))
    private void dropAccessoryItems(CallbackInfo ci) {
        for (int slot = 0; slot < accessorySlots.length; ++slot) {
            ItemStack itemStack = accessorySlots[slot];
            if (itemStack != null) {
                if (itemStack.getItem() instanceof IAccessoryEffects effects) {
                    effects.removeEffect(player, itemStack);
                }
                player.dropItem(itemStack, true);
                accessorySlots[slot] = null;
            }
        }
    }

    @Inject(method = "containsItem", at = @At("RETURN"), cancellable = true)
    private void containsAccessory(ItemStack stack, @NonNull CallbackInfoReturnable<Boolean> cir) {
        if (Boolean.TRUE.equals(cir.getReturnValue())) {
            return;
        }
        for (ItemStack accessory : accessorySlots) {
            if (accessory != null && accessory.isStackEqual(stack)) {
                cir.setReturnValue(true);
                return;
            }
        }
    }

    @Inject(method = "transferAllContents", at = @At("TAIL"))
    private void transferAccessoryContents(ContainerInventory inventory, CallbackInfo ci) {
        ItemStack[] sourceAccessories = ((IContainerInventoryAether) inventory).aether$getAccessoryInventory();
        for (int slot = 0; slot < accessorySlots.length; ++slot) {
            accessorySlots[slot] = sourceAccessories[slot];
            sourceAccessories[slot] = null;
        }
    }

    @Unique
    private int getAccessoryIndex(int containerIndex) {
        int index = containerIndex - this.mainInventory.length - this.armorInventory.length;
        return index >= 0 && index < accessorySlots.length ? index : -1;
    }

    @Unique
    private @Nullable ItemStack removeAccessoryItem(int index, int takeAmount) {
        ItemStack itemStack = accessorySlots[index];
        if (itemStack == null) {
            return null;
        }
        if (itemStack.stackSize <= takeAmount) {
            accessorySlots[index] = null;
            return itemStack;
        }
        ItemStack removed = itemStack.splitStack(takeAmount);
        if (itemStack.stackSize <= 0) {
            accessorySlots[index] = null;
        }
        return removed;
    }
}
