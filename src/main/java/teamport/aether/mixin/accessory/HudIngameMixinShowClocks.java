package teamport.aether.mixin.accessory;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = HudIngame.class, remap = false)
public class HudIngameMixinShowClocks {

    @WrapOperation(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;getContainerSize()I", ordinal = 0))
    public int addClockButtonWhenInWildcard(ContainerInventory instance, Operation<Integer> original){
        return original.call(instance) + 4;
    }
}
