package teamport.aether.mixin.gameSettings;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.catalyst.CatalystClient;
import teamport.aether.gameSettings.AetherGameSettings;

@Mixin(value = CatalystClient.class, remap = false)
public class CatalystAddToMixin {

    @Inject(method = "afterClientStart", at = @At("RETURN"))
    public void callAetherCatalystAddons(CallbackInfo ci) {
        AetherGameSettings.registerCatalystSettings();
    }

}
