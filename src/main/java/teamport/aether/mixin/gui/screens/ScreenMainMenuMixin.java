package teamport.aether.mixin.gui.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.ScreenMainMenu;
import net.minecraft.core.lang.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherClient;

@Environment(EnvType.CLIENT)
@Mixin(ScreenMainMenu.class)
public abstract class ScreenMainMenuMixin {

    @Shadow
    private String splashText;

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/holiday/Holiday;isActive()Z", ordinal = 6, shift = At.Shift.AFTER))
    private void injectAetherAnniversarySplash(CallbackInfo ci) {
        if (AetherClient.ANNIVERSARY_AETHER.isActive()) {
            this.splashText = I18n.getInstance().translateKey("gui.main_menu.label.splash.aether_birthday");
        }
    }
}

