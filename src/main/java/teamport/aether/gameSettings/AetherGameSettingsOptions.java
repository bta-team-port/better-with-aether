package teamport.aether.gameSettings;

import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionEnum;

public interface AetherGameSettingsOptions {
    OptionEnum<ExtraHealthDisplayEnum> aether$getExtraHealthDisplayOptionEnum();
    OptionBoolean aether$getFlickAccessoryIconsOption();

}
