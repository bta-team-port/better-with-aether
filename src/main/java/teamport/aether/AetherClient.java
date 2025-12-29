package teamport.aether;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.particle.ParticleDispatcher;
import net.minecraft.client.entity.particle.ParticleFirefly;
import net.minecraft.client.gui.achievements.data.AchievementPages;
import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.HudComponent;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.HudComponents;
import net.minecraft.client.gui.hud.component.layout.LayoutAbsolute;
import net.minecraft.client.gui.hud.component.layout.LayoutSnap;
import net.minecraft.client.holiday.Holiday;
import net.minecraft.client.render.colorizer.Colorizer;
import net.minecraft.client.render.texture.stitcher.AtlasStitcher;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.render.worldtype.WorldTypeFXDispatcher;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.util.helper.MathHelper;
import teamport.aether.achievements.AchievementPageAether;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.block.AetherBlocks;
import teamport.aether.command.AetherCommand;
import teamport.aether.ducks.IBlockAether;
import teamport.aether.entity.AetherMobInfoRegistry;
import teamport.aether.gui.HudComponentBossBar;
import teamport.aether.gui.HudComponentJumpBar;
import teamport.aether.option.AetherGameSettings;
import teamport.aether.particle.*;
import teamport.aether.world.type.AetherWorldTypes;
import teamport.aether.world.type.WorldTypeFXAether;
import turniplabs.halplibe.helper.TextureHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import java.time.Month;

import static net.minecraft.client.render.colorizer.Colorizers.add;
import static net.minecraft.client.render.texture.stitcher.TextureRegistry.register;
import static teamport.aether.AetherMod.LOGGER;
import static teamport.aether.AetherMod.MOD_ID;

@SuppressWarnings({"java:S1104", "java:S1444", "java:S3008"})
@Environment(EnvType.CLIENT)
public class AetherClient implements ClientModInitializer, ClientStartEntrypoint {
    public static HudComponent BOSS_BAR;
    public static HudComponent JUMP_BAR;

    public static final Holiday ANNIVERSARY_AETHER = new Holiday(Month.JULY, 22);

    public static Colorizer grassAether;
    public static Colorizer skyroot;
    public static Colorizer oakGolden;

    public static AetherRemoteResourceDownloaderThread resourceDownloaderThread;
    @SuppressWarnings("unused")
    public static AtlasStitcher extras = register("extras", new AtlasStitcher("textures/extras", true, false, null));

    @Override
    public void beforeClientStart() {
        ParticleDispatcher dispatcher = ParticleDispatcher.getInstance();

        dispatcher.addDispatch("flameambrosium", (world, x, y, z, xa, ya, za, id) -> new ParticleFlameAmbrosium(world, x, y, z, xa, ya, za));
        dispatcher.addDispatch("flameenchanter", (world, x, y, z, xa, ya, za, id) -> new ParticleFlameEnchanter(world, x, y, z, xa, ya, za));
        dispatcher.addDispatch("lightning", (world, x, y, z, xa, ya, za, id) -> new ParticleLightningKnife(world, x, y, z, xa, ya, za));
        dispatcher.addDispatch("snowflake", (world, x, y, z, xa, ya, za, id) -> new ParticleSnowflake(world, x, y, z, xa, ya, za));
        dispatcher.addDispatch("darttrail", (world, x, y, z, xa, ya, za, id) -> new ParticleDartEnchanted(world, x, y, z, xa, ya, za));
        dispatcher.addDispatch("goldendust", (world, x, y, z, xa, ya, za, id) -> new ParticleGoldenDust(world, x, y, z, xa, ya, za));
        dispatcher.addDispatch("fireflySilver", (world, x, y, z, motionX, motionY, motionZ, data) -> new ParticleFirefly(world, x, y, z, motionX, motionY, motionZ, AetherMod.SILVER.getId()));
        dispatcher.addDispatch("poison", (world, x, y, z, xa, ya, za, id) -> new ParticlePoison(world, x, y, z, xa, ya, za));
        dispatcher.addDispatch("remedy", (world, x, y, z, xa, ya, za, id) -> new ParticleRemedy(world, x, y, z, xa, ya, za));
        dispatcher.addDispatch("whirly", (world, x, y, z, xa, ya, za, id) -> new ParticleWhirlySpiral(world, x, y, z));
        dispatcher.addDispatch("tempest", (world, x, y, z, xa, ya, za, id) -> new ParticleTempestSpiral(world, x, y, z));
        dispatcher.addDispatch("fire", (world, x, y, z, xa, ya, za, id) -> new ParticleFireSpiral(world, x, y, z));
        dispatcher.addDispatch("fallingAetherLeaf", (world, x, y, z, motionX, motionY, motionZ, data) -> {
            int id = world.getBlockId(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
            return id != 0 ? (new ParticleAetherLeaf(world, x, y, z, motionX, motionY, motionX)).init(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)) : null;
        });

        SoundRepository.registerNamespace(MOD_ID);
        AetherCommand.registerClientCommands();
        AetherClient.registerTextures();
    }

    @Override
    public void afterClientStart() {
        Minecraft mc = Minecraft.getMinecraft();

        try {
            LOGGER.info("Starting Resource Download Thread...");
            resourceDownloaderThread = new AetherRemoteResourceDownloaderThread(mc.getMinecraftDir(), mc);
            resourceDownloaderThread.start();

        } catch (Exception exc) {
            LOGGER.error("Failed to start Resource Download Thread!", exc);
        }

        setupCustomBlockLight();
        AetherMobInfoRegistry.init();
        AetherGameSettings.init();

        grassAether = add(new Colorizer("grassAether"));
        skyroot = add(new Colorizer("skyroot"));
        oakGolden = add(new Colorizer("oakGolden"));

        WorldTypeFXDispatcher.getInstance().addDispatch(new WorldTypeFXAether(AetherWorldTypes.AETHER_EXTENDED)
            .setHasGround(false));
        WorldTypeFXDispatcher.getInstance().addDispatch(new WorldTypeFXAether(AetherWorldTypes.AETHER_DEFAULT)
            .setHasGround(false));
        WorldTypeFXDispatcher.getInstance().addDispatch(new WorldTypeFXAether(AetherWorldTypes.AETHER_SKYBLOCK)
            .setHasGround(false));
        WorldTypeFXDispatcher.getInstance().addDispatch(new WorldTypeFXAether(AetherWorldTypes.AETHER_RETRO)
            .setHasGround(false));
    }

    public void setupCustomBlockLight() {
        IBlockAether.of(AetherBlocks.CARVED_STONE_LIGHT).better_with_aether$setEmissionOverride(0);
        IBlockAether.of(AetherBlocks.CARVED_STONE_LIGHT_LOCKED).better_with_aether$setEmissionOverride(0);
        IBlockAether.of(AetherBlocks.CARVED_ANGELIC_LIGHT).better_with_aether$setEmissionOverride(0);
        IBlockAether.of(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED).better_with_aether$setEmissionOverride(0);
        IBlockAether.of(AetherBlocks.CARVED_HELLFIRE_LIGHT).better_with_aether$setEmissionOverride(0);
        IBlockAether.of(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED).better_with_aether$setEmissionOverride(0);
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("AetherMod client initialized.");
    }

    public static void initAchievementsPage() {
        AchievementPageAether page = new AchievementPageAether(MOD_ID, AetherBlocks.GRASS_AETHER.getDefaultStack());
        page.addAchievement(AetherAchievements.HOSTILE_PARADISE, 0, 0);

        page.addAchievement(AetherAchievements.SHOOTER, 1, -3);
        page.addAchievement(AetherAchievements.HIT_ZEPHYR, 2, -5);

        page.addAchievement(AetherAchievements.POISON, -1, -3);
        page.addAchievement(AetherAchievements.REMEDY, -2, -5);

        page.addAchievement(AetherAchievements.BOUNCE, -2, 0);
        page.addAchievement(AetherAchievements.GOLD_CLOUD, -3, -2);
        page.addAchievement(AetherAchievements.PARACHUTE, -4, 0);

        page.addAchievement(AetherAchievements.PHYG, -2, 2);
        page.addAchievement(AetherAchievements.MOA, -4, 2);

        page.addAchievement(AetherAchievements.ENCHANTER, 5, -1);

        page.addAchievement(AetherAchievements.WEVE_GOT_HOSTILES, 2, 1);

        page.addAchievement(AetherAchievements.SENTRY_DEPLOYED, 0, 3);
        page.addAchievement(AetherAchievements.BRONZE, 0, 5);

        page.addAchievement(AetherAchievements.ITS_A_TRAP, 2, 3);
        page.addAchievement(AetherAchievements.SILVER, 2, 5);

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

    public static void registerHUDComponents() {
        BOSS_BAR = HudComponents.register(
            new HudComponentBossBar(
                "boss_bar",
                new LayoutAbsolute(0.5f, 0.0f, ComponentAnchor.TOP_CENTER)
            )
        );

        JUMP_BAR = HudComponents.register(
            new HudComponentJumpBar(
                "wing_bar",
                new LayoutSnap(HudComponents.HEALTH_BAR, ComponentAnchor.TOP_LEFT, ComponentAnchor.BOTTOM_LEFT)
            )
        );

        ((HudComponentMovable) HudComponents.OXYGEN_BAR).setLayout(new LayoutSnap(HudComponents.ARMOR_BAR, ComponentAnchor.TOP_LEFT, ComponentAnchor.BOTTOM_LEFT));
    }

    public static void registerTextures() {
        for (final AtlasStitcher stitcher : TextureRegistry.stitcherMap.values()) {
            try {
                TextureHelper.initializeAllFiles(MOD_ID, stitcher, Integer.MAX_VALUE);
            } catch (Exception e) {
                AetherMod.LOGGER.error("Failed to initialize texture files!", e);
            }
        }
    }
}
