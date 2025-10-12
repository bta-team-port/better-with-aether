package teamport.aether.mixin.gameSettings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionEnum;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import teamport.aether.gameSettings.ExtraHealthDisplayEnum;
import teamport.aether.gameSettings.AetherGameSettingsOptions;

@Mixin(
        value = GameSettings.class,
        remap = false
)
public class HeartContainerOptionMixin implements AetherGameSettingsOptions {

    @Shadow
    @Final
    public Minecraft mc;
    private final GameSettings thisAs = ((GameSettings)(Object)this);

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

    public OptionEnum<ExtraHealthDisplayEnum> aether$getExtraHealthDisplayOptionEnum() {
        return aether$extraHealthDisplayOptionEnum;
    }

    @Override
    public OptionBoolean aether$getFlickAccessoryIconsOption() {
        return flickAccessoryIconsOption;
    }
}
