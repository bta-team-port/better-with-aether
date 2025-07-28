package teamport.aether.mixin.accessory.functional;

import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = ContainerInventory.class, remap = false)
public class ContainerInventoryMixinTickableArmorInventory {
    @Inject(method = "decrementAnimations", at = @At("TAIL"))
    public void addArmorAnimations(CallbackInfo ci){
        ContainerInventory inv = (ContainerInventory)(Object) this;
        for(int slot = 0; slot < inv.armorInventory.length; slot++){
            if (inv.armorInventory[slot] != null) {
                inv.armorInventory[slot].updateAnimation(inv.player.world, inv.player, slot + inv.mainInventory.length, inv.getCurrentItemIndex() == slot);
            }
        }
    }

}
