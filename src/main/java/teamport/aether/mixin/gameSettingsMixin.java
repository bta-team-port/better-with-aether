package teamport.aether.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.gameSettings.AetherGameSettingsOptions;

@Mixin(
        value = GameSettings.class,
        remap = false
)
public class gameSettingsMixin implements AetherGameSettingsOptions {

    @Shadow
    @Final
    public Minecraft mc;
    @Unique
    private final GameSettings thisAs = ((GameSettings) (Object) this);

    @Unique
    public OptionBoolean flickAccessoryIconsOption = new OptionBoolean(
            thisAs,
            "aether.flickAccessoryIcons",
            true
    );

    @Unique
    public OptionRange flickAccessorySpeed = new OptionRange(thisAs, "aether.flickAccessorySpeed", 5, 1, 60);


    @Override
    public OptionBoolean aether$getFlickAccessoryIconsOption() {
        return flickAccessoryIconsOption;
    }

    @Override
    public OptionRange aether$getAccessoryFlickSpeed() {
        return flickAccessorySpeed;
    }

    @Inject(method = "getDisplayString", at = @At("HEAD"), cancellable = true)
    public void changeDisplayString(Option<?> option, CallbackInfoReturnable<String> cir) {
        if (option == null) {
            cir.setReturnValue("");
            return;
        }
        if (option == flickAccessorySpeed) {
            cir.setReturnValue(option.value + " seconds");
        }
    }
}
