package teamport.aether;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherItems;
import teamport.aether.mixin.accessors.ConfigAccessor;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherConfig {
    public static final Object CONFIGURATION_LOCK = new Object();

    private static final Toml properties = new Toml("Aether Configs.toml \n[!] Be careful with IDs. Changes can affect your existing worlds.");
    private static TomlConfigHandler cfg;

    public static final String BlockIDCategory = "Block IDs";
    public static final String ItemIDCategory = "Item IDs";
    public static final String GeneralCategory = "General";


    public static int currentItemID;
    public static int currentBlockID;


    public static int DIMENSION = 3;
    public static int EXTRA_HEALTH = 20;
    public static float QUICK_SOIL_SPEED_CAP = 1.325F;

    public static int ENCHANTER_SCREEN_ID = 12;
    public static int FREEZER_SCREEN_ID = 13;
    public static int INCUBATOR_SCREEN_ID = 14;

    private static int BLOCK_ID_STARTING_FROM = 10000;
    private static int ITEM_ID_STARTING_FROM = 20000;

    public static volatile String REMOTE_RESOURCE_URL = "https://raw.githubusercontent.com/bta-team-port/better-with-aether/refs/heads/7.3/remoteAssets/";


    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final HashMap<Integer, String> itemIDToKeyMap = new HashMap<>();
    private static final HashMap<Integer, String> blockIDtoKeyMap = new HashMap<>();

    static void Setup() {
        LOGGER.info("Initializing config..");

        assembleProperties();
        cfg = new TomlConfigHandler(MOD_ID, properties);

        if (cfg.getConfigFile().exists()) { cfg.loadConfig(); }
        else {
            try { cfg.getConfigFile().createNewFile(); }
            catch (IOException e) { throw new RuntimeException(e); }

            cfg.writeConfig();
        }

        resolveIdMappings();
        currentBlockID = BLOCK_ID_STARTING_FROM = cfgGetValueOrDefault(GeneralCategory + ".BLOCK_IDS_STARTING_FROM", BLOCK_ID_STARTING_FROM);
        currentItemID  = ITEM_ID_STARTING_FROM  = cfgGetValueOrDefault(GeneralCategory  + ".ITEM_IDS_STARTING_FROM", ITEM_ID_STARTING_FROM);

        DIMENSION            = cfgGetValueOrDefault(GeneralCategory + ".DIMENSION", DIMENSION);
        EXTRA_HEALTH         = cfgGetValueOrDefault(GeneralCategory + ".EXTRA_HEALTH", EXTRA_HEALTH);
        QUICK_SOIL_SPEED_CAP = cfgGetValueOrDefault(GeneralCategory + ".QUICK_SOIL_SPEED_CAP", QUICK_SOIL_SPEED_CAP);
        ENCHANTER_SCREEN_ID  = cfgGetValueOrDefault(GeneralCategory + ".ENCHANTER_SCREEN_ID", ENCHANTER_SCREEN_ID);
        FREEZER_SCREEN_ID    = cfgGetValueOrDefault(GeneralCategory + ".FREEZER_SCREEN_ID", FREEZER_SCREEN_ID);
        INCUBATOR_SCREEN_ID  = cfgGetValueOrDefault(GeneralCategory + ".INCUBATOR_SCRREN_ID", INCUBATOR_SCREEN_ID);

        synchronized (CONFIGURATION_LOCK) {
            REMOTE_RESOURCE_URL = cfgGetValueOrDefault(GeneralCategory + ".REMOTE_RESOURCE_URL", REMOTE_RESOURCE_URL);
        }

        if (!REMOTE_RESOURCE_URL.endsWith("/")) { LOGGER.error("Remote resource URL lacks trailing slash!"); }
    }

    private static void assembleProperties() {
        properties.addCategory(GeneralCategory)
            .addEntry("cfgVersion", 6)
            .addEntry("DIMENSION", DIMENSION)
            .addEntry("EXTRA_HEALTH", EXTRA_HEALTH)
            .addEntry("QUICK_SOIL_SPEED_CAP", QUICK_SOIL_SPEED_CAP)
            .addEntry("REMOTE_RESOURCE_URL", REMOTE_RESOURCE_URL)
            .addEntry("ENCHANTER_SCREEN_ID", ENCHANTER_SCREEN_ID)
            .addEntry("FREEZER_SCREEN_ID", FREEZER_SCREEN_ID)
            .addEntry("INCUBATOR_SCRREN_ID", INCUBATOR_SCREEN_ID);

        //BLOCK ID
        properties.addEntry(GeneralCategory + ".BLOCK_IDS_STARTING_FROM", BLOCK_ID_STARTING_FROM);
        properties.addCategory(BlockIDCategory);

        List<Field> blockFields = Arrays.stream(AetherBlocks.class.getDeclaredFields())
                .filter((F)-> Block.class.isAssignableFrom(F.getType()))
                .collect(Collectors.toList());

        for (Field blockField : blockFields) {
            properties.addEntry(BlockIDCategory + "." + blockField.getName(), BLOCK_ID_STARTING_FROM++);
        }

        //ITEM ID
        properties.addEntry(GeneralCategory + ".ITEM_IDS_STARTING_FROM", ITEM_ID_STARTING_FROM);
        properties.addCategory(ItemIDCategory);

        List<Field> itemFields = Arrays.stream(AetherItems.class.getDeclaredFields())
                .filter((F)-> Item.class.isAssignableFrom(F.getType()))
                .collect(Collectors.toList());

        for (Field itemField : itemFields) {
            properties.addEntry(ItemIDCategory + "." + itemField.getName(), ITEM_ID_STARTING_FROM++);
        }
    }

    private static void resolveIdMappings() {
        List<String> configuredItemKeys  = ((ConfigAccessor) cfg).getConfig().get("."+ItemIDCategory, Toml.class).getOrderedKeys();
        List<String> configuredBlockKeys = ((ConfigAccessor) cfg).getConfig().get("."+BlockIDCategory, Toml.class).getOrderedKeys();

        configuredItemKeys.forEach(
                key -> {
                    int id = cfg.getInt(AetherConfig.ItemIDCategory + "." + key);
                    if (itemIDToKeyMap.containsKey(id)) throw new RuntimeException("Found duplicated item id in " + key);

                    itemIDToKeyMap.put(id, key);
                }
        );

        configuredBlockKeys.forEach(
                key -> {
                    int id = cfg.getInt(AetherConfig.BlockIDCategory + "." + key);
                    if (blockIDtoKeyMap.containsKey(id)) throw new RuntimeException("Found duplicated block id in " + key);

                    blockIDtoKeyMap.put(id, key);
                }
        );
    }


    public static int itemID(String itemName) {
        try { return AetherConfig.cfg.getInt(AetherConfig.ItemIDCategory + "." + itemName); }

        catch (NullPointerException e) {
            LOGGER.warn("Couldn't find item key for {}, Trying to insert at next available ID...", itemName);
            while (itemIDToKeyMap.containsKey(currentItemID)) { currentItemID++; }

            AetherConfig.properties.addEntry(AetherConfig.ItemIDCategory + "." + itemName, currentItemID);
            itemIDToKeyMap.put(currentItemID, itemName);

            return currentItemID;
        }
    }

    public static int blockID(String blockName) {
        try { return AetherConfig.cfg.getInt(AetherConfig.BlockIDCategory + "." + blockName); }

        catch (NullPointerException e) {
            LOGGER.warn("Couldn't find block key for {}, Trying to insert at next available ID...", blockName);
            while (blockIDtoKeyMap.containsKey(currentBlockID)) { currentBlockID++; }

            AetherConfig.properties.addEntry(AetherConfig.BlockIDCategory + "." + blockName, currentBlockID);
            blockIDtoKeyMap.put(currentBlockID, blockName);

            return currentBlockID;
        }
    }

    @SuppressWarnings("unchecked")
    static <T> T cfgGetValueOrDefault(String key, T def) {
        T res = null;
        try {
            if (def instanceof String) {
                res = (T) cfg.getString(key);
            }
            else if (def instanceof Integer) {
                res = (T) new Integer(cfg.getInt(key));
            }
            else if (def instanceof Long) {
                res = (T) new Long(cfg.getLong(key));
            }
            else if (def instanceof Float) {
                Object raw = cfg.getRawParsed().get(key);
                if (raw instanceof Double) {
                    res = (T) new Float(((Double) raw));
                    return res;
                }
                res = (T) new Float((float) raw);
            }
            else if (def instanceof Double) {
                Object raw = cfg.getRawParsed().get(key);
                if (raw instanceof Float) {
                    res = (T) new Double(((float) raw));
                    return res;
                }
                res = (T) new Double((double) raw);
            }
            else if (def instanceof Boolean) {
                res = (T) new Boolean(cfg.getBoolean(key));
            }
            else {
                throw new RuntimeException("Invalid value type!");
            };

        } catch (NullPointerException ignored) {}

        if (res == null) {
            LOGGER.warn("Failed to load \"{}\"! Assuming default...", key);
            return def;
        }

        return res;
    }
}
