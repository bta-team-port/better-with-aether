package teamport.aether.mixin.gameSettings;

import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionEnum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import teamport.aether.gameSettings.ExtraHealthDisplayEnum;
import teamport.aether.gameSettings.GameSettingsDisplayHeartsOption;

@Mixin(
        value = GameSettings.class,
        remap = false
)
public class HeartContainerOptionMixin implements GameSettingsDisplayHeartsOption {

    @Unique
    private final GameSettings thisAs = ((GameSettings) (Object) this);

    @Unique
    public OptionEnum<ExtraHealthDisplayEnum> aether$extraHealthDisplayOptionEnum = new OptionEnum<>(
            thisAs,
            "catalyst-effect.aether$displayExtraHealthAs",
            ExtraHealthDisplayEnum.class,
            ExtraHealthDisplayEnum.EXTRA_BARS
    );

    public OptionEnum<ExtraHealthDisplayEnum> aether$getExtraHealthDisplayOptionEnum() {
        return aether$extraHealthDisplayOptionEnum;
    }
}
