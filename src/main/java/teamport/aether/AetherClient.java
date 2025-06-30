package teamport.aether;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.achievements.data.AchievementPages;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.block.Blocks;
import teamport.aether.particle.ParticleFlameAmbrosium;
import turniplabs.halplibe.helper.ParticleHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.apache.log4j.builders.appender.SocketAppenderBuilder.LOGGER;
import static teamport.aether.AetherMod.MOD_ID;

@Environment(EnvType.CLIENT)
public class AetherClient implements ClientModInitializer, ClientStartEntrypoint {

    @Override
    public void beforeClientStart() {
        ParticleHelper.createParticle("flameambrosium", (world, x, y, z, xa, ya, za, id) -> new ParticleFlameAmbrosium(world, x, y, z, xa, ya, za));
        SoundRepository.registerNamespace(MOD_ID);

        try {
            TextureRegistry.initializeAllFiles(MOD_ID, TextureRegistry.particleAtlas, false);
        } catch (URISyntaxException | IOException e) {
            LOGGER.error("Failed to initialize textures!");
        }

    }

    @Override
    public void afterClientStart() {
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("AetherMod client initialized.");
    }

    public static void initAchievementsPage() {
        AchievementPageAether page = new AchievementPageAether(MOD_ID, Blocks.GLOWSTONE.getDefaultStack());
        page.addAchievement(AetherAchievements.HOSTILE_PARADISE, 0, 0);
        page.addAchievement(AetherAchievements.BOUNCE, -2, -1);
        page.addAchievement(AetherAchievements.MOA, 2, 1);
        page.addAchievement(AetherAchievements.PHYG, -2, 1);
        page.addAchievement(AetherAchievements.ENCHANTER, 2, 1);
        page.addAchievement(AetherAchievements.BRONZE, -2, 3);
        page.addAchievement(AetherAchievements.SILVER, 0, 4);
        page.addAchievement(AetherAchievements.GOLD, 2, 3);
        page.addAchievement(AetherAchievements.GRAVITITE, 2, -1);
        AchievementPages.register(page);
    }
}
