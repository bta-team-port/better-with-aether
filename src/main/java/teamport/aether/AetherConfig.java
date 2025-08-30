package teamport.aether;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherItems;
import teamport.aether.mixin.accessors.ConfigAccessor;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;
import turniplabs.halplibe.util.toml.TomlParser;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherConfig {
    public static final Object CONFIGURATION_LOCK = new Object();

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
    private static final String FILE_COMMENT = "Aether Configs.toml \n[!] Be careful with IDs. Changes can affect your existing worlds.";

    static void Setup() {
        LOGGER.info("Initializing config..");

        // TODO: throw halplibe's TomlConfigHandler where it belong. The garbage bin. >:(

        File configFile = new File(FabricLoader.getInstance().getGameDir().toString() + "/config/" + MOD_ID + ".cfg");
        if (configFile.exists()) {
            String fileContent;

            try { fileContent = new String(Files.readAllBytes(configFile.toPath()), StandardCharsets.UTF_8); }
            catch (Exception e) { throw new RuntimeException(e);}

            Toml parsed = new Toml(FILE_COMMENT);
            parsed.addMissing(TomlParser.parse(fileContent));

            // the cfg should now hold whatever the last configuration state was.
            cfg = new TomlConfigHandler(MOD_ID, parsed);

            //  now simply register each id to the mappings, that way we can know what is being used already.
            currentBlockID = BLOCK_ID_STARTING_FROM = cfgGetValueOrDefault(GeneralCategory + ".BLOCK_IDS_STARTING_FROM", BLOCK_ID_STARTING_FROM);
            currentItemID = ITEM_ID_STARTING_FROM = cfgGetValueOrDefault(GeneralCategory + ".ITEM_IDS_STARTING_FROM", ITEM_ID_STARTING_FROM);
            resolveIdMappings();

            // add the default properties. It should merge it all correctly.
            ((ConfigAccessor) cfg).getConfig().addMissing(assembleProperties());

            String updatedFileContent = ((ConfigAccessor) cfg).getConfig().toString();

            if (!fileContent.equals(updatedFileContent)) {
                try {
                    Files.move(configFile.toPath(), new File(configFile + "." + String.valueOf(System.nanoTime()) + ".old").toPath());
                    Files.write(configFile.toPath(), updatedFileContent.getBytes());
                }
                catch (Exception e) {
                    LOGGER.error("Failed to refresh file!");
                    throw new RuntimeException(e);
                }
            }
        }

        else {
            currentBlockID = BLOCK_ID_STARTING_FROM;
            currentItemID = ITEM_ID_STARTING_FROM;

            cfg = new TomlConfigHandler(MOD_ID, assembleProperties());
            cfg.writeConfig();
        }

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

    private static Toml assembleProperties() {
        Toml properties = new Toml(FILE_COMMENT);

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
            properties.addEntry(BlockIDCategory + "." + blockField.getName(), blockID(blockField.getName()));
        }

        //ITEM ID
        properties.addEntry(GeneralCategory + ".ITEM_IDS_STARTING_FROM", ITEM_ID_STARTING_FROM);
        properties.addCategory(ItemIDCategory);

        List<Field> itemFields = Arrays.stream(AetherItems.class.getDeclaredFields())
                .filter((F)-> Item.class.isAssignableFrom(F.getType()))
                .collect(Collectors.toList());

        for (Field itemField : itemFields) {
            properties.addEntry(ItemIDCategory + "." + itemField.getName(), itemID(itemField.getName()));
        }

        return properties;
    }

    private static void resolveIdMappings() {
        List<String> configuredItemKeys  = ((ConfigAccessor) cfg).getConfig().get("."+ItemIDCategory, Toml.class).getOrderedKeys();
        List<String> configuredBlockKeys = ((ConfigAccessor) cfg).getConfig().get("."+BlockIDCategory, Toml.class).getOrderedKeys();

        configuredItemKeys.forEach(
            key -> {
                if (key.equals("startingFrom")) return;

                int id = cfg.getInt(AetherConfig.ItemIDCategory + "." + key);
                if (itemIDToKeyMap.containsKey(id)) {
                    throw new RuntimeException(String.format("Found duplicated item ID in \"%s\". ID already in use by \"%s\"", key, itemIDToKeyMap.get(id)));
                }

                itemIDToKeyMap.put(id, key);
            }
        );

        configuredBlockKeys.forEach(
            key -> {
                if (key.equals("startingFrom")) return;

                int id = cfg.getInt(AetherConfig.BlockIDCategory + "." + key);
                if (blockIDtoKeyMap.containsKey(id)) {
                    throw new RuntimeException(String.format("Found duplicated item ID in \"%s\". ID already in use by \"%s\"", key, blockIDtoKeyMap.get(id)));
                }

                blockIDtoKeyMap.put(id, key);
                }
        );
    }


    public static int itemID(String itemName) {
        try { return AetherConfig.cfg.getInt(AetherConfig.ItemIDCategory + "." + itemName); }

        catch (NullPointerException e) {
            LOGGER.warn("Couldn't find item key for {}, Trying to insert at next available ID...", itemName);
            while (itemIDToKeyMap.containsKey(currentItemID)) { currentItemID++; }

            itemIDToKeyMap.put(currentItemID, itemName);

            return currentItemID;
        }
    }

    public static int blockID(String blockName) {
        try { return AetherConfig.cfg.getInt(AetherConfig.BlockIDCategory + "." + blockName); }

        catch (NullPointerException e) {
            LOGGER.warn("Couldn't find block key for {}, Trying to insert at next available ID...", blockName);
            while (blockIDtoKeyMap.containsKey(currentBlockID)) { currentBlockID++; }

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
