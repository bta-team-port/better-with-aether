package teamport.aether.mixin.accessory.functional;

import com.mojang.nbt.tags.ListTag;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.accessory.Accessory;


@Mixin(value = ContainerInventory.class, remap = false)
public class ContainerInventoryMixinIncArmorInv {

    @Shadow public Player player;

    @Shadow public ItemStack[] armorInventory;
    @Shadow public ItemStack[] mainInventory;

    // armor inventory expanded to fit the extra 4 accessory slots
    @Inject(method = "<init>", at = @At("TAIL"))
    public void setNewSize(Player player, CallbackInfo ci) {
       ((ContainerInventory) (Object) this).armorInventory = new ItemStack[4 + 4];
    }

    // change hardcoded size of the armor inventory
    @ModifyConstant(method = "readFromNBT", constant = @Constant(intValue = 4), require = 1)
    private int modifyArmourSize(int original) {
        return 4 + 4;
    }

    // call accessory added on player loaded
    @Inject(method = "readFromNBT", at = @At("TAIL"))
    public void activateAccessories(ListTag nbttaglist, CallbackInfo ci) {
        ContainerInventory inv = (ContainerInventory) (Object) this;
        for (ItemStack item : inv.armorInventory) {
            if (item != null && item.getItem() instanceof Accessory) {
                ((Accessory) item.getItem()).onAccessoryAdded(inv.player, item);
            }
        }
    }

    @ModifyConstant(method = "getContainerSize", constant = @Constant(intValue = 4), require = 1)
    private int modifyContainerSize(int original) {
        return 4 + 4;
    }

    @Inject(method = "setItem", at = @At("HEAD"))
    public void onSetInventoryItem(int index, ItemStack newItem, CallbackInfo ci) {
        if (index < this.mainInventory.length) {
            return;
        }
        if (index >= this.armorInventory.length) {
            ItemStack oldItem = this.armorInventory[index - this.mainInventory.length];

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
