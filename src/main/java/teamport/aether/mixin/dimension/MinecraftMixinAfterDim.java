package teamport.aether.mixin.dimension;


import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherEvents;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixinAfterDim {

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/EntityDispatcher;init()V"))
    private void afterDimInit(CallbackInfo ci){
        AetherEvents.AFTER_DIM_INIT.emit(Runnable::run);
    }
}
