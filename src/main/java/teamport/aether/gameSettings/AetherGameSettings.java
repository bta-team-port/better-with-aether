package teamport.aether.gameSettings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.option.GameSettings;
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
    }

    public static void registerCatalystSettings() {
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;

        CatalystClient.effectsCategory.withComponent(
                new ToggleableOptionComponent<>(
                        ((GameSettingsDisplayHeartsOption) gameSettings).aether$getExtraHealthDisplayOptionEnum()
                )
        );
    }
}
