package teamport.aether;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.io.IOException;
import java.util.Optional;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherConfig {
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static TomlConfigHandler cfg;

    public static final Object CONFIGURATION_LOCK = new Object();


    public static final String GENERAL_CATEGORY = "General";

    public static int DIMENSION = 9;
    public static int EXTRA_HEALTH = 20;
    public static double QUICK_SOIL_SPEED_CAP = 1.325F;

    public static int ENCHANTER_SCREEN_ID = 12;
    public static int FREEZER_SCREEN_ID = 13;
    public static int INCUBATOR_SCREEN_ID = 14;

    private static int BLOCK_ID_STARTING_FROM = 10000;
    private static int ITEM_ID_STARTING_FROM = 26000;

    public static boolean INCLUDE_REPAIR_RECIPES = false;

    public static int currentBlockID;
    public static int currentItemID;

    public static volatile String REMOTE_RESOURCE_URL = getDefaultRemoteUrl();

    static String getDefaultRemoteUrl() {
        FabricLoader loader = FabricLoader.getInstance();
        Optional<ModContainer> modContainerOpt = loader.getModContainer(MOD_ID);

        String result;
        if (loader.isDevelopmentEnvironment() || !modContainerOpt.isPresent()) {
            result = "https://raw.githubusercontent.com/bta-team-port/better-with-aether/refs/heads/7.3/remoteAssets/";
        } else {
            ModContainer modContainer = modContainerOpt.get();

            String version = modContainer
                    .getMetadata()
                    .getVersion()
                    .getFriendlyString()
                    .substring(0, 5);

            if (version.endsWith(".0")) version = version.substring(0, 3);
            result = String.format(
                "https://raw.githubusercontent.com/bta-team-port/better-with-aether/refs/tags/%s-%s/remoteAssets/",
                version,
                AetherMod.STATE
            );

        }

        return result;
    }

    static void init() {
        LOGGER.info("Initializing config..");

        Toml props = new Toml("Aether Configs.toml");
        assembleProperties(props);

        cfg = new TomlConfigHandler(MOD_ID, props);

        if (cfg.getConfigFile().exists()) cfg.loadConfig();
        else {
            try {
                cfg.getConfigFile().createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            cfg.writeConfig();
        }

        loadProperties();
    }

    private static void loadProperties() {
        DIMENSION = cfgGetValueOrDefault(GENERAL_CATEGORY + ".DIMENSION", DIMENSION);
        EXTRA_HEALTH = cfgGetValueOrDefault(GENERAL_CATEGORY + ".EXTRA_HEALTH", EXTRA_HEALTH);
        QUICK_SOIL_SPEED_CAP = cfgGetValueOrDefault(GENERAL_CATEGORY + ".QUICK_SOIL_SPEED_CAP", QUICK_SOIL_SPEED_CAP);
        ENCHANTER_SCREEN_ID = cfgGetValueOrDefault(GENERAL_CATEGORY + ".ENCHANTER_SCREEN_ID", ENCHANTER_SCREEN_ID);
        FREEZER_SCREEN_ID = cfgGetValueOrDefault(GENERAL_CATEGORY + ".FREEZER_SCREEN_ID", FREEZER_SCREEN_ID);
        INCUBATOR_SCREEN_ID = cfgGetValueOrDefault(GENERAL_CATEGORY + ".INCUBATOR_SCREEN_ID", INCUBATOR_SCREEN_ID);

        INCLUDE_REPAIR_RECIPES = cfgGetValueOrDefault(GENERAL_CATEGORY + ".INCLUDE_REPAIR_RECIPES", INCLUDE_REPAIR_RECIPES);

        currentBlockID = BLOCK_ID_STARTING_FROM = cfgGetValueOrDefault(GENERAL_CATEGORY + ".BLOCK_ID_STARTING_FROM", BLOCK_ID_STARTING_FROM);
        currentItemID = ITEM_ID_STARTING_FROM = cfgGetValueOrDefault(GENERAL_CATEGORY + ".ITEM_ID_STARTING_FROM", ITEM_ID_STARTING_FROM);

        synchronized (CONFIGURATION_LOCK) {
            REMOTE_RESOURCE_URL = cfgGetValueOrDefault(GENERAL_CATEGORY + ".REMOTE_RESOURCE_URL", REMOTE_RESOURCE_URL);
        }

        if (!REMOTE_RESOURCE_URL.endsWith("/")) {
            LOGGER.error("Remote resource URL lacks trailing slash!");
        }
    }

    private static void assembleProperties(Toml properties) {
        properties.addCategory(GENERAL_CATEGORY)
                .addEntry("cfgVersion", 6)
                .addEntry("DIMENSION", DIMENSION)
                .addEntry("EXTRA_HEALTH", EXTRA_HEALTH)
                .addEntry("QUICK_SOIL_SPEED_CAP", QUICK_SOIL_SPEED_CAP)
                .addEntry("REMOTE_RESOURCE_URL", REMOTE_RESOURCE_URL)
                .addEntry("ENCHANTER_SCREEN_ID", ENCHANTER_SCREEN_ID)
                .addEntry("FREEZER_SCREEN_ID", FREEZER_SCREEN_ID)
                .addEntry("INCUBATOR_SCREEN_ID", INCUBATOR_SCREEN_ID)
                .addEntry("BLOCK_ID_STARTING_FROM", BLOCK_ID_STARTING_FROM)
                .addEntry("ITEM_ID_STARTING_FROM", ITEM_ID_STARTING_FROM)
                .addEntry("INCLUDE_REPAIR_RECIPES", INCLUDE_REPAIR_RECIPES);
    }

    @SuppressWarnings("unused")
    public static int itemID(String itemName) {
        return currentItemID++;
    }
    @SuppressWarnings("unused")
    public static int blockID(String blockName) {
        return currentBlockID++;
    }

    @SuppressWarnings("unchecked")
    static <T> T cfgGetValueOrDefault(String key, T def) {
        T res = null;

        try {
            if      (def instanceof String)  { res = (T) cfg.getString(key); }
            else if (def instanceof Integer) { res = (T) Integer.valueOf(cfg.getInt(key)); }
            else if (def instanceof Long)    { res = (T) Long.valueOf(cfg.getLong(key)); }
            else if (def instanceof Boolean) { res = (T) Boolean.valueOf(cfg.getBoolean(key)); }

            else if (def instanceof Double || def instanceof Float) {
                double raw = cfg.getDouble(key);

                if (def instanceof Float) res = (T) Float.valueOf((float) raw);
                else res = (T) Double.valueOf(raw);
            } else {
                throw new RuntimeException("Invalid value type!");
            }

        } catch (NullPointerException ignored) { /* noop */ }

        if (res == null) {
            LOGGER.warn("Failed to load \"{}\"! Assuming default...", key);
            return def;
        }

        return res;
    }
}
