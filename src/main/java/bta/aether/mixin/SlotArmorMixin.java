package bta.aether.mixin;

import net.minecraft.core.player.inventory.slot.SlotArmor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SlotArmor.class, remap = false)
public abstract class SlotArmorMixin {

    // cancel the bta armor icon from appearing, since the aether has its own that are on the inventory.png
    @Inject(method = "getBackgroundIconIndex",at=@At("HEAD"),cancellable = true)
    public void getBackgroundIconIndex(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(-1);
    }
}
