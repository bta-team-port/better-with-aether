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
    private static final Toml properties = new Toml("Aether Configs.toml \n[!] Be careful with IDs. Changes can affect your existing worlds.");
    private static TomlConfigHandler cfg;

    private static int BLOCK_ID_STARTING_FROM = 10000;
    private static int ITEM_ID_STARTING_FROM = 20000;

    public static int currentItemID;
    public static int currentBlockID;

    public static int DIMENSION;
    public static int EXTRA_HEALTH;
    public static float QUICK_SOIL_SPEED_CAP;
    public static int ENCHANTER_SCREEN_ID;
    public static int FREEZER_SCREEN_ID;
    public static int INCUBATOR_SCREEN_ID;

    public static volatile String REMOTE_RESOURCE_URL;

    public static final String BlockIDCategory = "Block IDs";
    public static final String ItemIDCategory = "Item IDs";
    public static final String GeneralCategory = "General";


    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final HashMap<Integer, String> itemIDToKeyMap = new HashMap<>();
    private static final HashMap<Integer, String> blockIDtoKeyMap = new HashMap<>();

    static void Setup() {
        int dimensionDefault = 3; // so it wont bug me for testing
        int extraHealthDefault = 20;
        float quicksoilCapDefault = 1.325F;

        int enchanterScreenID = 12;
        int freezerScreenID = 13;
        int incubatorScreenID = 14;
        String remoteResourceURLDefault = "https://raw.githubusercontent.com/bta-team-port/better-with-aether/refs/heads/7.3/remoteAssets/";

        LOGGER.info("Initializing config..");

        properties.addCategory(GeneralCategory)
                .addEntry("cfgVersion", 6)
                .addEntry("DIMENSION", dimensionDefault)
                .addEntry("EXTRA_HEALTH", extraHealthDefault)
                .addEntry("QUICK_SOIL_SPEED_CAP", quicksoilCapDefault)
                .addEntry("REMOTE_RESOURCE_URL", remoteResourceURLDefault)
                .addEntry("ENCHANTER_SCREEN_ID", enchanterScreenID)
                .addEntry("FREEZER_SCREEN_ID", freezerScreenID)
                .addEntry("INCUBATOR_SCRREN_ID", incubatorScreenID);

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

        cfg = new TomlConfigHandler(MOD_ID, properties);

        if (cfg.getConfigFile().exists()) { cfg.loadConfig(); }
        else {
            try { cfg.getConfigFile().createNewFile(); }
            catch (IOException e) { throw new RuntimeException(e); }

            cfg.writeConfig();
        }

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

        DIMENSION            = cfgGetValueOrDefault(GeneralCategory + ".DIMENSION", dimensionDefault);
        EXTRA_HEALTH         = cfgGetValueOrDefault(GeneralCategory + ".EXTRA_HEALTH", extraHealthDefault);
        QUICK_SOIL_SPEED_CAP = cfgGetValueOrDefault(GeneralCategory + ".QUICK_SOIL_SPEED_CAP", quicksoilCapDefault);
        REMOTE_RESOURCE_URL  = cfgGetValueOrDefault(GeneralCategory + ".REMOTE_RESOURCE_URL", remoteResourceURLDefault);
        ENCHANTER_SCREEN_ID  = cfgGetValueOrDefault(GeneralCategory + ".ENCHANTER_SCREEN_ID", enchanterScreenID);
        FREEZER_SCREEN_ID    = cfgGetValueOrDefault(GeneralCategory + ".FREEZER_SCREEN_ID", freezerScreenID);
        INCUBATOR_SCREEN_ID  = cfgGetValueOrDefault(GeneralCategory + ".INCUBATOR_SCRREN_ID", incubatorScreenID);

        currentBlockID = BLOCK_ID_STARTING_FROM = cfgGetValueOrDefault(GeneralCategory + ".BLOCK_IDS_STARTING_FROM", incubatorScreenID);
        currentItemID  = ITEM_ID_STARTING_FROM  = cfgGetValueOrDefault(GeneralCategory  + ".ITEM_IDS_STARTING_FROM", incubatorScreenID);

        if (!REMOTE_RESOURCE_URL.endsWith("/")) { LOGGER.error("Remote resource URL lacks trailing slash!"); }
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

    static void onExit() {
        cfg.writeConfig();
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
