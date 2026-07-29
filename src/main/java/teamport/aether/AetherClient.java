package teamport.aether;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievements.data.AchievementPages;
import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.HudComponent;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.HudComponents;
import net.minecraft.client.gui.hud.component.layout.LayoutAbsolute;
import net.minecraft.client.gui.hud.component.layout.LayoutSnap;
import net.minecraft.client.holiday.Holiday;
import net.minecraft.client.render.colorizer.Colorizer;
import net.minecraft.client.render.colorizer.Colorizers;
import net.minecraft.client.render.particle.ParticleDispatcher;
import net.minecraft.client.render.particle.ParticleFirefly;
import net.minecraft.client.render.texture.stitcher.AtlasStitcher;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.render.worldtype.WorldTypeFXDispatcher;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.pos.TilePos;
import sunsetsatellite.catalyst.effects.api.effect.render.EffectRenderer;
import sunsetsatellite.catalyst.effects.api.effect.render.EffectRendererDispatcher;
import teamport.aether.achievements.AchievementPageAether;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.block.AetherBlocks;
import teamport.aether.command.AetherCommand;
import teamport.aether.ducks.IBlockAether;
import teamport.aether.effect.AetherEffects;
import teamport.aether.effect.render.PoisonEffectRenderer;
import teamport.aether.effect.render.RemedyEffectRenderer;
import teamport.aether.entity.AetherMobInfoRegistry;
import teamport.aether.gui.HudComponentBossBar;
import teamport.aether.gui.HudComponentJumpBar;
import teamport.aether.models.AetherModels;
import teamport.aether.option.AetherGameSettings;
import teamport.aether.particle.*;
import teamport.aether.world.type.AetherWorldTypes;
import teamport.aether.world.type.WorldTypeFXAether;
import turniplabs.halplibe.event.defs.ClientEvents;
import turniplabs.halplibe.helper.TextureHelper;
import turniplabs.halplibe.util.dependency.Key;

import java.time.Month;

import static teamport.aether.AetherMod.LOGGER;
import static teamport.aether.AetherMod.MOD_ID;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"java:S1104", "java:S1444", "java:S3008"})
public class AetherClient implements ClientModInitializer {
    public static HudComponent BOSS_BAR;
    public static HudComponent JUMP_BAR;

    public static final Holiday ANNIVERSARY_AETHER = new Holiday(Month.JULY, 22);

    public static Colorizer grassAether;
    public static Colorizer skyroot;
    public static Colorizer oakGolden;

    private static final AetherModels MODELS = new AetherModels();

    public static AetherRemoteResourceDownloaderThread resourceDownloaderThread;
    @SuppressWarnings("unused")
    public static AtlasStitcher extras = TextureRegistry.register(new AtlasStitcher(true, false).addDirectory("extras", "textures/extras"));

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
            return id != 0 ? (new ParticleAetherLeaf(world, x, y, z, motionX, motionY, motionX)).init(new TilePos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z))) : null;
        });

        SoundRepository.namespaceAdded(MOD_ID);
        AetherCommand.registerClientCommands();
        AetherClient.registerTextures();
    }

    public void afterClientStart() {
        Minecraft mc = Minecraft.getMinecraft();
        registerEffectRenderers();

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

        WorldTypeFXDispatcher.getInstance().addDispatch(new WorldTypeFXAether(AetherWorldTypes.AETHER_EXTENDED));
        WorldTypeFXDispatcher.getInstance().addDispatch(new WorldTypeFXAether(AetherWorldTypes.AETHER_DEFAULT));
        WorldTypeFXDispatcher.getInstance().addDispatch(new WorldTypeFXAether(AetherWorldTypes.AETHER_SKYBLOCK));
        WorldTypeFXDispatcher.getInstance().addDispatch(new WorldTypeFXAether(AetherWorldTypes.AETHER_RETRO));
    }

    private static void registerEffectRenderers() {
        AetherEffects.init();
        EffectRendererDispatcher dispatcher = EffectRendererDispatcher.getInstance();
        dispatcher.addDispatch(AetherEffects.poisonEffect, new PoisonEffectRenderer<>(
            AetherEffects.poisonEffect,
            "/assets/aether/textures/other/poisonvignette.png",
            0x8218cb,
            "aether:gui/hud/poison/"
        ).setIcon("poison.png"));
        dispatcher.addDispatch(AetherEffects.remedyEffect, new RemedyEffectRenderer<>(
            AetherEffects.remedyEffect,
            "/assets/aether/textures/other/curevignette.png",
            0x009bc2,
            "aether:gui/hud/remedy/"
        ).setIcon("remedy.png"));
        dispatcher.addDispatch(AetherEffects.invisibility, new EffectRenderer<>(AetherEffects.invisibility).setIcon("invisibility.png"));
        dispatcher.addDispatch(AetherEffects.swetty, new EffectRenderer<>(AetherEffects.swetty).setIcon("swetty.png"));
    }

    public static void registerColorizers() {
        grassAether = Colorizers.add(new Colorizer("grassAether"));
        skyroot = Colorizers.add(new Colorizer("skyroot"));
        oakGolden = Colorizers.add(new Colorizer("oakGolden"));
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
        Key key = Key.of(MOD_ID);
        ClientEvents.BEFORE_CLIENT_START.listen(key, this::beforeClientStart);
        ClientEvents.AFTER_CLIENT_START.listen(key, this::afterClientStart);
        ClientEvents.BLOCK_MODEL_RELOAD.listen(key, MODELS::initBlockModels);
        ClientEvents.ITEM_MODEL_RELOAD.listen(key, MODELS::initItemModels);
        ClientEvents.ENTITY_RENDERER_RELOAD.listen(key, MODELS::initEntityModels);
        ClientEvents.BLOCK_COLOR_RELOAD.listen(key, MODELS::initBlockColors);

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

        ((HudComponentMovable) HudComponents.OXYGEN_BAR).setLayout(new LayoutSnap(HudComponents.BOOTS_BAR, ComponentAnchor.TOP_LEFT, ComponentAnchor.BOTTOM_LEFT));
    }

    public static void registerTextures() {
        for (final AtlasStitcher stitcher : TextureRegistry.stitcherMap.values()) {
            try {
                TextureHelper.initializeAllFiles(MOD_ID, stitcher, true);
            } catch (Exception e) {
                AetherMod.LOGGER.error("Failed to initialize texture files!", e);
            }
        }
    }
}
