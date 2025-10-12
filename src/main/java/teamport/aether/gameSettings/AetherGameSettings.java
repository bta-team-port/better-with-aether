package teamport.aether.gameSettings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPages;
import sunsetsatellite.catalyst.CatalystClient;

@Environment(EnvType.CLIENT)
public class AetherGameSettings {

    private static boolean hasInit = false;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            registerSettings();
        }
    }

    public static void registerSettings() {
        AetherGameSettingsOptions gameSettings = (AetherGameSettingsOptions) Minecraft.getMinecraft().gameSettings;

        OptionsPages.VIDEO.withComponent(
            new BooleanOptionComponent(
                gameSettings.aether$getFlickAccessoryIconsOption()
            )
        );
    }

    public static void registerCatalystSettings() {
        AetherGameSettingsOptions gameSettings = (AetherGameSettingsOptions) Minecraft.getMinecraft().gameSettings;

        CatalystClient.effectsCategory.withComponent(
            new ToggleableOptionComponent<>(
                gameSettings.aether$getExtraHealthDisplayOptionEnum()
            )
        );
    }
}
