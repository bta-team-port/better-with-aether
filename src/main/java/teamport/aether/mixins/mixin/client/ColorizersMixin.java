package teamport.aether.mixins.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.colorizer.Colorizers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherClient;

@Environment(EnvType.CLIENT)
@Mixin(Colorizers.class)
public abstract class ColorizersMixin {
    @Inject(method = "registerColorizers", at = @At("TAIL"))
    private static void aether$registerColorizers(CallbackInfo ci) {
        AetherClient.registerColorizers();
    }
}
