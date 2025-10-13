package teamport.aether.mixin.gameSettings;

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
import teamport.aether.gameSettings.ExtraHealthDisplayEnum;

@Mixin(
        value = GameSettings.class,
        remap = false
)
public class SettingsAddOn implements AetherGameSettingsOptions {

    @Shadow
    @Final
    public Minecraft mc;
    @Unique
    private final GameSettings thisAs = ((GameSettings) (Object) this);

    @Unique
    public OptionEnum<ExtraHealthDisplayEnum> aether$extraHealthDisplayOptionEnum = new OptionEnum<>(
            thisAs,
            "catalyst-effect.aether$displayExtraHealthAs",
            ExtraHealthDisplayEnum.class,
            ExtraHealthDisplayEnum.EXTRA_BARS
    );

    @Unique
    public OptionBoolean flickAccessoryIconsOption = new OptionBoolean(
            thisAs,
            "aether.flickAccessoryIcons",
            true
    );

    @Unique
    public OptionRange flickAccessorySpeed = new OptionRange(thisAs, "aether.flickAccessorySpeed", 5, 1, 60);

    public OptionEnum<ExtraHealthDisplayEnum> aether$getExtraHealthDisplayOptionEnum() {
        return aether$extraHealthDisplayOptionEnum;
    }

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
