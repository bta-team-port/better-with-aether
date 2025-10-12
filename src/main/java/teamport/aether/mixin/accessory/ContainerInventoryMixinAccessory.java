package teamport.aether.mixin.accessory;

import com.mojang.nbt.tags.ListTag;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.items.accessory.IAccessoryEffects;


@Mixin(value = ContainerInventory.class, remap = false)
public class ContainerInventoryMixinAccessory {

    @Shadow
    public Player player;

    @Shadow
    public ItemStack[] armorInventory;

    @Shadow
    public ItemStack[] mainInventory;

    // armor inventory expanded to fit the extra 4 accessory slots
    @Inject(method = "<init>", at = @At("TAIL"))
    public void setNewSize(Player player, CallbackInfo ci) {
        ((ContainerInventory) (Object) this).armorInventory = new ItemStack[4 + 4];
    }

    // change hardcoded size of the armor inventory
    @ModifyConstant(method = "readFromNBT", constant = @Constant(intValue = 4), require = 1)
    public int modifyArmourSize(int original) {
        return 4 + 4;
    }

    // call accessory added on player loaded
    @Inject(method = "readFromNBT", at = @At("TAIL"))
    public void activateAccessories(ListTag nbttaglist, CallbackInfo ci) {
        ContainerInventory inv = (ContainerInventory) (Object) this;
        for (ItemStack item : inv.armorInventory) {
            if (item != null && item.getItem() instanceof IAccessoryEffects) {
//                ((IAccessoryEffects) item.getItem()).addEffect(inv.player, item);
            }
        }
    }


    @ModifyConstant(method = "getContainerSize", constant = @Constant(intValue = 4), require = 1)
    public int modifyContainerSize(int original) {
        return this.armorInventory.length;
    }

    @Inject(method = "decrementAnimations", at = @At("TAIL"))
    public void addArmorAnimations(CallbackInfo ci) {
        ContainerInventory inv = (ContainerInventory) (Object) this;
        for (int slot = 0; slot < inv.armorInventory.length; slot++) {
            if (inv.armorInventory[slot] != null) {
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
    public void updateEffects(int index, int takeAmount, CallbackInfoReturnable<ItemStack> cir) {
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
    public void updateEffects(int index, ItemStack newItem, CallbackInfo ci) {
        if (index < this.mainInventory.length) {
            return;
        }
        ContainerInventory cont = (ContainerInventory) (Object) this;

        ItemStack oldItem = this.armorInventory[index - this.mainInventory.length];

        // this is only called when we SWAP an item
        if (oldItem != null && oldItem.getItem() instanceof IAccessoryEffects) {
            ((IAccessoryEffects) oldItem.getItem()).removeEffect(player, oldItem);
        }
    }
}
