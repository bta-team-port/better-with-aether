package teamport.aether.game_settings;

import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionRange;

public interface AetherGameSettingsOptions {

    OptionBoolean aether$getFlickAccessoryIconsOption();

    OptionRange aether$getAccessoryFlickSpeed();
}
