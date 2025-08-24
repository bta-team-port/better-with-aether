package teamport.aether;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherItems;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherConfig {
    public static final Toml properties = new Toml("Aether Configs.toml \n[!] Be careful with IDs. Changes can affect your existing worlds.");
    public static TomlConfigHandler cfg;

    public static int blockIDs = 10000;
    public static int itemIDs = 20000;

    public static int DIMENSION;
    public static int EXTRA_HEALTH;
    public static float QUICK_SOIL_SPEED_CAP;
    public static int ENCHANTER_SCREEN_ID;
    public static int FREEZER_SCREEN_ID;
    public static int INCUBATOR_SCREEN_ID;

    public static volatile String REMOTE_RESOURCE_URL;

    public static String BlockIDs = "Block IDs";
    public static String ItemIDs = "Item IDs";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    static void Setup() {
        int dimensionDefault = 3; // so it wont bug me for testing
        int extraHealthDefault = 20;
        float quicksoilCapDefault = 1.325F;

        int enchanterScreenID = 12;
        int freezerScreenID = 13;
        int incubatorScreenID = 14;
        String remoteResourceURLDefault = "https://raw.githubusercontent.com/bta-team-port/better-with-aether/refs/heads/7.3/remoteAssets/";

        LOGGER.info("Initializing config..");

        properties.addCategory("General")
                .addEntry("cfgVersion", 6)
                .addEntry("DIMENSION", dimensionDefault)
                .addEntry("EXTRA_HEALTH", extraHealthDefault)
                .addEntry("QUICK_SOIL_SPEED_CAP", quicksoilCapDefault)
                .addEntry("REMOTE_RESOURCE_URL", remoteResourceURLDefault)
                .addEntry("ENCHANTER_SCREEN_ID", enchanterScreenID)
                .addEntry("FREEZER_SCREEN_ID", freezerScreenID)
                .addEntry("INCUBATOR_SCRREN_ID", incubatorScreenID);

        //BLOCK ID
        properties.addCategory(BlockIDs);
        properties.addEntry(BlockIDs+".startingFrom", blockIDs);

        List<Field> blockFields = Arrays.stream(AetherBlocks.class.getDeclaredFields())
                .filter((F)-> Block.class.isAssignableFrom(F.getType()))
                .collect(Collectors.toList());

        for (Field blockField : blockFields) {
            properties.addEntry(BlockIDs + "." + blockField.getName(), blockIDs++);
        }

        //ITEM ID
        properties.addCategory(ItemIDs);
        properties.addEntry(ItemIDs+".startingFrom", itemIDs);

        List<Field> itemFields = Arrays.stream(AetherItems.class.getDeclaredFields())
                .filter((F)-> Item.class.isAssignableFrom(F.getType()))
                .collect(Collectors.toList());

        for (Field itemField : itemFields) {
            properties.addEntry(ItemIDs+ "." + itemField.getName(), itemIDs++);
        }

        cfg = new TomlConfigHandler(MOD_ID, properties);

        if (cfg.getConfigFile().exists()) { cfg.loadConfig(); }
        else {
            try { cfg.getConfigFile().createNewFile(); }
            catch (IOException e) { throw new RuntimeException(e); }

            cfg.writeConfig();
        }

        DIMENSION            = cfgGetValueOrDefault("General.DIMENSION", dimensionDefault);
        EXTRA_HEALTH         = cfgGetValueOrDefault("General.EXTRA_HEALTH", extraHealthDefault);
        QUICK_SOIL_SPEED_CAP = cfgGetValueOrDefault("General.QUICK_SOIL_SPEED_CAP", quicksoilCapDefault);
        REMOTE_RESOURCE_URL  = cfgGetValueOrDefault("General.REMOTE_RESOURCE_URL", remoteResourceURLDefault);
        ENCHANTER_SCREEN_ID  = cfgGetValueOrDefault("General.ENCHANTER_SCREEN_ID", enchanterScreenID);
        FREEZER_SCREEN_ID    = cfgGetValueOrDefault("General.FREEZER_SCREEN_ID", freezerScreenID);
        INCUBATOR_SCREEN_ID  = cfgGetValueOrDefault("General.INCUBATOR_SCRREN_ID", incubatorScreenID);

        if (!REMOTE_RESOURCE_URL.endsWith("/")) { LOGGER.error("Remote resource URL lacks trailing slash!"); }
    }


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
                res = (T) new Float(cfg.getDouble(key));
            }
            else if (def instanceof Double) {
                res = (T) new Double(cfg.getDouble(key));
            }
            else if (def instanceof Boolean) {
                res = (T) new Boolean(cfg.getBoolean(key));
            }
            else {
                throw new RuntimeException("Invalid value type!");
            };

        } catch (NullPointerException ignored) {}

        return res == null ? def : res;
    }
}
