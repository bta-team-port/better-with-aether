package teamport.aether.option;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.OptionRange;
import net.minecraft.client.option.GameSettings;

@Environment(EnvType.CLIENT)
public final class AetherGameSettingsHolder {
    public static final OptionRange FLICK_ACCESSORY_SPEED = GameSettings.register(
        new OptionRange("aether.flickAccessorySpeed", 5, 0, 60)
            .withDisplayStringProvider((minecraft, i18n, option) ->
                option.value == 0 ? "OFF" : option.value + " seconds")
    );

    private AetherGameSettingsHolder() {}
}
