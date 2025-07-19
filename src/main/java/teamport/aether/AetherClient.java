package teamport.aether;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.particle.ParticleDispatcher;
import net.minecraft.client.entity.particle.ParticleFirefly;
import net.minecraft.client.gui.achievements.data.AchievementPages;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.item.ItemStack;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.command.AetherCommand;
import teamport.aether.entity.mimic.MobMimic;
import teamport.aether.entity.sentry.MobSentry;
import teamport.aether.entity.zephyr.MobZephyr;
import teamport.aether.particle.ParticleDartEnchanted;
import teamport.aether.particle.ParticleFlameAmbrosium;
import teamport.aether.particle.ParticleGoldenDust;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.apache.log4j.builders.appender.SocketAppenderBuilder.LOGGER;
import static teamport.aether.AetherMod.MOD_ID;

@Environment(EnvType.CLIENT)
public class AetherClient implements ClientModInitializer, ClientStartEntrypoint {

    @Override
    public void beforeClientStart() {
        ParticleDispatcher dispatcher = ParticleDispatcher.getInstance();

        dispatcher.addDispatch("flameambrosium", (world, x, y, z, xa, ya, za, id) -> new ParticleFlameAmbrosium(world, x, y, z, xa, ya, za));
        dispatcher.addDispatch("darttrail", (world, x, y, z, xa, ya, za, id) -> new ParticleDartEnchanted(world, x, y, z, xa, ya, za));
        dispatcher.addDispatch("goldendust", (world, x, y, z, xa, ya, za, id) -> new ParticleGoldenDust(world, x, y, z, xa, ya, za));
        dispatcher.addDispatch("fireflySilver", (world, x, y, z, motionX, motionY, motionZ, data) -> new ParticleFirefly(world, x, y, z, motionX, motionY, motionZ, AetherMod.SILVER.getId()));

        SoundRepository.registerNamespace(MOD_ID);
        AetherCommand.registerClientCommands();

        try {
            TextureRegistry.initializeAllFiles(MOD_ID, TextureRegistry.particleAtlas, false);
        } catch (URISyntaxException | IOException e) {
            LOGGER.error("Failed to initialize textures!");
        }

    }

    @Override
    public void afterClientStart() {
        MobInfoRegistry.register(MobSentry.class, "aether.sentry.name", "aether.sentry.desc", 10, 100, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.CARVED_STONE), 1.0f, 1 ,1),
                new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.CARVED_STONE_LIGHT), 1.0f, 1, 1)});

        MobInfoRegistry.register(MobZephyr.class, "aether.zephyr.name", "aether.zephyr.desc", 10, 100, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.AERCLOUD_WHITE), 1.0f, 0, 6)});

        MobInfoRegistry.register(MobMimic.class, "aether.mimic.name", "aether.mimic.desc", 20, 100, new MobInfoRegistry.MobDrop[]{
                new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.CHEST_PLANKS_SKYROOT), 1.0f, 1, 1)});
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("AetherMod client initialized.");
    }

    public static void initAchievementsPage() {
        AchievementPageAether page = new AchievementPageAether(MOD_ID, AetherBlocks.GRASS_AETHER.getDefaultStack());
        page.addAchievement(AetherAchievements.HOSTILE_PARADISE, 0, 0);

        page.addAchievement(AetherAchievements.SHOOTER, 1, -3);
        page.addAchievement(AetherAchievements.ZEPHYR, 2, -5);

        page.addAchievement(AetherAchievements.POISON, -1, -3);
        page.addAchievement(AetherAchievements.REMEDY, -2, -5);

        page.addAchievement(AetherAchievements.BOUNCE, -2, 0);
        page.addAchievement(AetherAchievements.GOLD_CLOUD, -3, -2);
        page.addAchievement(AetherAchievements.PARACHUTE, -4, 0);

        page.addAchievement(AetherAchievements.PHYG, -2, 2);
        page.addAchievement(AetherAchievements.MOA, -4, 2);

        page.addAchievement(AetherAchievements.ENCHANTER, 5, -1);

        page.addAchievement(AetherAchievements.SENTRY_DEPLOYED, 0, 3);
        page.addAchievement(AetherAchievements.BRONZE, 0, 5);

        page.addAchievement(AetherAchievements.ITS_A_TRAP, 2, 5);
        page.addAchievement(AetherAchievements.SILVER, 2, 3);

        page.addAchievement(AetherAchievements.ICE_DEFLECT, 4, 3);
        page.addAchievement(AetherAchievements.GOLD, 4, 5);

        page.addAchievement(AetherAchievements.SKYROOT, 3, 0);
        page.addAchievement(AetherAchievements.AMBROSIUM, 4, 1);
        page.addAchievement(AetherAchievements.HEALING_STONE, 6, 1);

        page.addAchievement(AetherAchievements.GRAVITITE, 3, -2);

        page.addAchievement(AetherAchievements.ALL_MUSIC_DISCS, 4, -4);

        page.addAchievement(AetherAchievements.MAX_LIFE, 6, 6);

        page.addAchievement(AetherAchievements.ALL_ACCESSORY_TYPES, -2, 6);

        AchievementPages.register(page);
    }
}
