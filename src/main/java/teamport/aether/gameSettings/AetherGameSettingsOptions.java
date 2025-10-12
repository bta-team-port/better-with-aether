package teamport.aether.gameSettings;

import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionEnum;
import net.minecraft.client.option.OptionInteger;
import net.minecraft.client.option.OptionRange;

public interface AetherGameSettingsOptions {
    OptionEnum<ExtraHealthDisplayEnum> aether$getExtraHealthDisplayOptionEnum();
    OptionBoolean aether$getFlickAccessoryIconsOption();
    OptionRange aether$getAccessoryFlickSpeed();
}
