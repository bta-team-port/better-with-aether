package teamport.aether.mixin.gameSettings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.option.*;
import net.minecraft.core.util.helper.Toggleable;
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
public class SettingsAddOn implements AetherGameSettingsOptions {

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
}
