package teamport.aether.mixin.accessory;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.items.accessory.IAccessoryEffects;

@Mixin(value = ContainerInventory.class, remap = false)
public abstract class ContainerInventoryMixinAccessory {
    @Shadow
    public Player player;
    @Shadow
    public ItemStack[] armorInventory;
    @Shadow
    public ItemStack[] mainInventory;
    // armor inventory expanded to fit the extra 4 accessory slots
    @Inject(method = "<init>", at = @At("TAIL"))
    private void setNewSize(Player player, CallbackInfo ci) {
        ((ContainerInventory) (Object) this).armorInventory = new ItemStack[4 + 4];
    }
    // change hardcoded size of the armor inventory
    @ModifyExpressionValue(method = "readFromNBT", at = @At(value = "CONSTANT", args = "intValue=4"))
    private int modifyArmourSize(int original) {
        return original + 4;
    }
    @ModifyExpressionValue(method = "getContainerSize", at = @At(value = "CONSTANT", args = "intValue=4"))
    private int modifyContainerSize(int original) {
        return this.armorInventory.length;
    }

    @Inject(method = "decrementAnimations", at = @At("TAIL"))
    private void addArmorAnimations(CallbackInfo ci) {
        ContainerInventory inv = (ContainerInventory) (Object) this;
        for (int slot = 0; slot < inv.armorInventory.length; slot++) {
            if (inv.armorInventory[slot] != null && inv.player.world != null) {
                inv.armorInventory[slot].updateAnimation(inv.player.world, inv.player, slot + inv.mainInventory.length, inv.getCurrentItemIndex() == slot);
            }
        }
    }
    /**
     * @reason 7.3_04 currently handles left click and drop differently from shift clicking.
     * To guarantee that the effect of the accessories is correctly remove on left click and drop
     * a mixin is needed into removeItem. - Redart15
     */
    @Inject(method = "removeItem", at = @At("HEAD"))
    private void updateEffects(int index, int takeAmount, CallbackInfoReturnable<ItemStack> cir) {
        if (index < this.mainInventory.length) {
            return;
        }
        ItemStack itemStack = this.armorInventory[index - this.mainInventory.length];
        if (itemStack != null && itemStack.getItem() instanceof IAccessoryEffects) {
            ((IAccessoryEffects) itemStack.getItem()).removeEffect(player, itemStack);
        }
    }


    /**
     * @reason 7.3_04 currently handles left click and drop differently from shift clicking.
     * To guarantee that the effect of the accessories is correctly remove on shift clicking
     * a mixin is needed into setItem. - Redart15
     */
    @Inject(method = "setItem", at = @At("HEAD"))
    private void updateEffects(int index, ItemStack newItem, CallbackInfo ci) {
        if (index < this.mainInventory.length) {
            return;
        }

        ItemStack oldItem = this.armorInventory[index - this.mainInventory.length];
        // this is only called when we SWAP an item
        if (oldItem != null && oldItem.getItem() instanceof IAccessoryEffects) {
            ((IAccessoryEffects) oldItem.getItem()).removeEffect(player, oldItem);
        }
    }
}
