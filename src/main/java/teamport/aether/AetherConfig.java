package teamport.aether;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherItems;
import turniplabs.halplibe.util.toml.Toml;
import turniplabs.halplibe.util.toml.TomlParser;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherConfig {
    public static final Object CONFIGURATION_LOCK = new Object();

    private static Toml cfg;

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
    private static int ITEM_ID_STARTING_FROM = 26000;

    public static volatile String REMOTE_RESOURCE_URL = "https://raw.githubusercontent.com/bta-team-port/better-with-aether/refs/heads/7.3/remoteAssets/";


    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final HashMap<Integer, String> itemIDToKeyMap = new HashMap<>();
    private static final HashMap<Integer, String> blockIDtoKeyMap = new HashMap<>();

    static void Setup() {
        LOGGER.info("Initializing config..");

        // TODO: throw halplibe's TomlConfigHandler where it belong. The garbage bin. >:(

        cfg = new Toml("Aether Configs.toml \n[!] Be careful with IDs. Changes can affect your existing worlds.");

        File configFile = new File(FabricLoader.getInstance().getGameDir().toString() + "/config/" + MOD_ID + ".cfg");
        if (configFile.exists()) {
            String fileContent;

            try { fileContent = new String(Files.readAllBytes(configFile.toPath()), StandardCharsets.UTF_8); }
            catch (Exception e) { throw new RuntimeException(e);}

            // the cfg should now hold whatever the last configuration state was.
            Toml parsed = TomlParser.parse(fileContent);
            cfg.addMissing(parsed);

            //  now simply register each id to the mappings, that way we can know what is being used already.
            currentBlockID = BLOCK_ID_STARTING_FROM = cfgGetValueOrDefault(GeneralCategory + ".BLOCK_IDS_STARTING_FROM", BLOCK_ID_STARTING_FROM);
            currentItemID = ITEM_ID_STARTING_FROM = cfgGetValueOrDefault(GeneralCategory + ".ITEM_IDS_STARTING_FROM", ITEM_ID_STARTING_FROM);
            resolveIdMappings();

            // add the default properties. It should merge it all correctly.
            cfg.addMissing(assembleProperties(new Toml()));

            String updatedFileContent = TomlToString(cfg, "", 0);
            if (!fileContent.equals(updatedFileContent)) {
                try {
                    Files.move(configFile.toPath(), new File(configFile + "." + String.valueOf(System.nanoTime()) + ".old").toPath());
                    Files.write(configFile.toPath(), updatedFileContent.getBytes());
                }
                catch (Exception e) {
                    LOGGER.error("Failed to refresh config file!");
                    throw new RuntimeException(e);
                }
            }
        }

        else {
            currentBlockID = BLOCK_ID_STARTING_FROM;
            currentItemID = ITEM_ID_STARTING_FROM;

            assembleProperties(cfg);

            try {Files.write(configFile.toPath(), TomlToString(cfg, "", 0).getBytes());}

            catch (Exception e) {
                LOGGER.error("Failed to write config file!");
                throw new RuntimeException(e);
            }
        }

        DIMENSION            = cfgGetValueOrDefault(GeneralCategory + ".DIMENSION", DIMENSION);
        EXTRA_HEALTH         = cfgGetValueOrDefault(GeneralCategory + ".EXTRA_HEALTH", EXTRA_HEALTH);
        QUICK_SOIL_SPEED_CAP = cfgGetValueOrDefault(GeneralCategory + ".QUICK_SOIL_SPEED_CAP", QUICK_SOIL_SPEED_CAP);
        ENCHANTER_SCREEN_ID  = cfgGetValueOrDefault(GeneralCategory + ".ENCHANTER_SCREEN_ID", ENCHANTER_SCREEN_ID);
        FREEZER_SCREEN_ID    = cfgGetValueOrDefault(GeneralCategory + ".FREEZER_SCREEN_ID", FREEZER_SCREEN_ID);
        INCUBATOR_SCREEN_ID  = cfgGetValueOrDefault(GeneralCategory + ".INCUBATOR_SCREEN_ID", INCUBATOR_SCREEN_ID);

        synchronized (CONFIGURATION_LOCK) {
            REMOTE_RESOURCE_URL = cfgGetValueOrDefault(GeneralCategory + ".REMOTE_RESOURCE_URL", REMOTE_RESOURCE_URL);
        }

        if (!REMOTE_RESOURCE_URL.endsWith("/")) { LOGGER.error("Remote resource URL lacks trailing slash!"); }
    }

    private static Toml assembleProperties(Toml properties) {
        properties.addCategory(GeneralCategory)
            .addEntry("cfgVersion", 6)
            .addEntry("DIMENSION", DIMENSION)
            .addEntry("EXTRA_HEALTH", EXTRA_HEALTH)
            .addEntry("QUICK_SOIL_SPEED_CAP", QUICK_SOIL_SPEED_CAP)
            .addEntry("REMOTE_RESOURCE_URL", REMOTE_RESOURCE_URL)
            .addEntry("ENCHANTER_SCREEN_ID", ENCHANTER_SCREEN_ID)
            .addEntry("FREEZER_SCREEN_ID", FREEZER_SCREEN_ID)
            .addEntry("INCUBATOR_SCREEN_ID", INCUBATOR_SCREEN_ID);

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
        List<String> configuredItemKeys  = cfg.get("."+ItemIDCategory, Toml.class).getOrderedKeys();
        List<String> configuredBlockKeys = cfg.get("."+BlockIDCategory, Toml.class).getOrderedKeys();

        configuredItemKeys.forEach(
            key -> {
                if (key.equals("startingFrom")) return;

                int id = (int) cfg.get(AetherConfig.ItemIDCategory + "." + key);
                if (itemIDToKeyMap.containsKey(id)) {
                    throw new RuntimeException(String.format("Found duplicated item ID in \"%s\". ID already in use by \"%s\"", key, itemIDToKeyMap.get(id)));
                }

                itemIDToKeyMap.put(id, key);
            }
        );

        configuredBlockKeys.forEach(
            key -> {
                if (key.equals("startingFrom")) return;

                int id = (int) cfg.get(AetherConfig.BlockIDCategory + "." + key);
                if (blockIDtoKeyMap.containsKey(id)) {
                    throw new RuntimeException(String.format("Found duplicated item ID in \"%s\". ID already in use by \"%s\"", key, blockIDtoKeyMap.get(id)));
                }

                blockIDtoKeyMap.put(id, key);
                }
        );
    }


    public static int itemID(String itemName) {
        try { return (int) cfg.get(ItemIDCategory + "." + itemName); }

        catch (NullPointerException e) {
            LOGGER.warn("Couldn't find item key for {}, Trying to insert at next available ID...", itemName);
            while (itemIDToKeyMap.containsKey(currentItemID)) { currentItemID++; }

            itemIDToKeyMap.put(currentItemID, itemName);

            return currentItemID;
        }
    }

    public static int blockID(String blockName) {
        try { return (int) cfg.get(BlockIDCategory + "." + blockName); }

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
        try { res = (T) cfg.get(key); }
        catch (NullPointerException ignored) {}

        if (res == null) {
            LOGGER.warn("Failed to load \"{}\"! Assuming default...", key);
            return def;
        }

        return res;
    }

    public static String repeat(String txt, int count) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < count; i++) out.append(txt);
        return out.toString();
    }

    public static String TomlToString(Toml toml, String rootKey, int indent) {
        StringBuilder out = new StringBuilder();

        if (toml.getComment().isPresent()) {
            String comment = toml.getComment().get();

            for (String line : comment.split("\n")) {
                out.append(repeat("\t", indent)).append("# ").append(line).append("\n");
            }

            out.append("\n");
        }

        Set<String> realKeys = new HashSet<>(toml.getOrderedKeys());

        for (String orderedKey : realKeys) {
            String[] res;
            int offset = 0;
            int sep = 0;

            if (orderedKey.startsWith(".")) {
                if (orderedKey.substring(1).contains(".")) continue;

                Toml cat = toml.get(orderedKey, Toml.class);
                String full = rootKey + (rootKey.isEmpty() ? "" : ".") + orderedKey.substring(1);

                if (cat.getComment().isPresent()) {
                    String comment = cat.getComment().get();

                    for (String re : comment.split("\n"))
                        out.append(repeat("\t", indent)).append("# ").append(re).append("\n");
                }

                out.append(repeat("\t", indent)).append("[").append(full).append("]").append("\n");


                res = TomlToString(cat, full, 0).split("\n");
                sep = offset = 1;
            } else {
                res = toml.getEntry(orderedKey).toString(orderedKey).split("\n");
            }

            for (String re : res) out.append(repeat("\t", indent + offset)).append(re).append("\n");
            out.append(repeat("\n", sep));
        }

        return out.toString();
    }
}
