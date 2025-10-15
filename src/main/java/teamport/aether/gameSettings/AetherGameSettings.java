package teamport.aether.gameSettings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.*;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.save.ISaveFormat;
import net.minecraft.core.world.save.mcregion.SaveFormat19134;
import sunsetsatellite.catalyst.CatalystClient;
import teamport.aether.AetherMod;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.generate.chunk.ChunkGeneratorAether;

import java.io.File;

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

        OptionsComponent cursedButton = new ButtonComponent("aether.cursed") {
            @Override
            public void resetValue() {}

            @Override
            public boolean isDefault() {
                return false;
            }

            @Override
            protected void buttonClicked(int i, int j, int k, int l, int m, int n, int o) {
                ISaveFormat saveFormat = new SaveFormat19134(new File("C:/test/stupidSHit"));
                World world = new World
                        (saveFormat.getSaveHandler("lol", false),
                                "lol",
                                123,
                                AetherDimension.AETHER,
                                null
                        );

                ChunkGenerator chunkGenerator = new ChunkGeneratorAether(world);

                int sizeX = 100;
                int sizeZ = 100;

                long startTime = System.currentTimeMillis();

                for (int x = 0; x < sizeX; x++) {
                    for (int z = 0; z < sizeZ; z++) {
                        Chunk chunk = chunkGenerator.generate(x, z);
                        chunkGenerator.decorate(chunk);
                    }
                }

                long elapsedTime = System.currentTimeMillis() - startTime;

                AetherMod.LOGGER.info("Time Elapsed: {}", elapsedTime);
            };
        };

        OptionsPages.VIDEO.withComponent(new OptionsCategory("aether.options.video.aether")
                .withComponent(new BooleanOptionComponent(gameSettings.aether$getFlickAccessoryIconsOption()))
                .withComponent(new ToggleableOptionComponent<>(gameSettings.aether$getAccessoryFlickSpeed()))
                .withComponent(cursedButton)
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
