package teamport.aether.game_settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPages;

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

        OptionsPages.VIDEO.withComponent(new OptionsCategory("aether.options.video.aether")
            .withComponent(new BooleanOptionComponent(gameSettings.aether$getFlickAccessoryIconsOption()))
            .withComponent(new ToggleableOptionComponent<>(gameSettings.aether$getAccessoryFlickSpeed()))
        );
    }
}
