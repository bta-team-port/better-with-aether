package teamport.aether.option;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.core.item.ItemStack;
import teamport.aether.block.AetherBlocks;

@Environment(EnvType.CLIENT)
public class AetherGameSettings {

    private AetherGameSettings(){}

    private static boolean hasInit = false;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            registerSettings();
        }
    }

    public static void registerSettings() {
        AetherGameSettingsOptions gameSettings = (AetherGameSettingsOptions) Minecraft.getMinecraft().gameSettings;

        OptionsPage AETHER = new OptionsPage("gui.options.page.aether.title", new ItemStack(AetherBlocks.GRASS_AETHER))
            .withComponent(new OptionsCategory("gui.options.page.aether.category.user_interface")
                .withComponent(new ToggleableOptionComponent<>(gameSettings.aether$getAccessoryFlickSpeed()))
            );
        OptionsPages.register(AETHER);
    }
}
