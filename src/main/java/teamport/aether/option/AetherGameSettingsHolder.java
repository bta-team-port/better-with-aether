package teamport.aether.option;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.*;
import net.minecraft.core.enums.ArmorHiddenState;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public final class AetherGameSettingsHolder {
    public static final @NonNull OptionFloat COLOR_CORRECTION_AETHER = GameSettings.register(new OptionFloat("colorCorrectionAether", 0.5F));

    public static final OptionRange FLICK_ACCESSORY_SPEED = GameSettings.register(
        new OptionRange("aether.flickAccessorySpeed", 5, 0, 60)
            .withDisplayStringProvider((minecraft, i18n, option) ->
                option.value == 0 ? "OFF" : option.value + " seconds")
    );

    public static final @NonNull OptionEnum<ArmorHiddenState> HIDE_GLOVES_BAR = GameSettings.register((new OptionEnum<>("hideGlovesBar", ArmorHiddenState.class, ArmorHiddenState.WHEN_NOT_WEARING)).setIsSlider(true));
    public static final @NonNull OptionEnum<ArmorHiddenState> HIDE_CAPE_BAR = GameSettings.register((new OptionEnum<>("hideCapeBar", ArmorHiddenState.class, ArmorHiddenState.WHEN_NOT_WEARING)).setIsSlider(true));
    public static final @NonNull OptionEnum<ArmorHiddenState> HIDE_TRINKET_1_BAR = GameSettings.register((new OptionEnum<>("hideTrinket1Bar", ArmorHiddenState.class, ArmorHiddenState.WHEN_NOT_WEARING)).setIsSlider(true));
    public static final @NonNull OptionEnum<ArmorHiddenState> HIDE_TRINKET_2_BAR = GameSettings.register((new OptionEnum<>("hideTrinket2Bar", ArmorHiddenState.class, ArmorHiddenState.WHEN_NOT_WEARING)).setIsSlider(true));

    public static final @NonNull OptionBoolean FLIP_GLOVES_BAR = GameSettings.register(new OptionBoolean("flipGlovesBar", false));
    public static final @NonNull OptionBoolean FLIP_CAPE_BAR = GameSettings.register(new OptionBoolean("flipCapeBar", false));
    public static final @NonNull OptionBoolean FLIP_TRINKET_1_BAR = GameSettings.register(new OptionBoolean("flipTrinket1Bar", false));
    public static final @NonNull OptionBoolean FLIP_TRINKET_2_BAR = GameSettings.register(new OptionBoolean("flipTrinket2Bar", false));

    private AetherGameSettingsHolder() {
    }
}
