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
    public static int DIMENSION;

    public static int blockIDs = 10000;
    public static int itemIDs = 20000;

    public static float QUICK_SOIL_SPEED_CAP;
    public static int EXTRA_HEALTH;

    public static String BlockIDs = "Block IDs";
    public static String ItemIDs = "Item IDs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    static void Setup() {
        int dimensionDefault = 3; // so it wont bug me for testing
        int extraHealthDefault = 20;
        float quicksoilCapDefault = 1.325F;

        LOGGER.info("Initializing config..");

        properties.addCategory("General")
                .addEntry("cfgVersion", 6)
                .addEntry("DIMENSION", dimensionDefault);
        //BLOCK ID
        properties.addCategory(BlockIDs);
        properties.addEntry(BlockIDs+".startingFrom", blockIDs);
        List<Field> blockFields = Arrays.stream(AetherBlocks.class.getDeclaredFields()).filter((F)-> Block.class.isAssignableFrom(F.getType())).collect(Collectors.toList());
        for (Field blockField : blockFields) {
            properties.addEntry(BlockIDs + "." + blockField.getName(), blockIDs++);
        }
        //ITEM ID
        properties.addCategory(ItemIDs);
        properties.addEntry(ItemIDs+".startingFrom", itemIDs);
        List<Field> itemFields = Arrays.stream(AetherItems.class.getDeclaredFields()).filter((F)-> Item.class.isAssignableFrom(F.getType())).collect(Collectors.toList());
        for (Field itemField : itemFields) {
            properties.addEntry(ItemIDs+ "." + itemField.getName(), itemIDs++);
        }

        properties.addCategory("Others")
                .addEntry("EXTRA_HEALTH", extraHealthDefault);

        properties.addCategory("Others")
                .addEntry("QUICK_SOIL_SPEED_CAP", quicksoilCapDefault);

        cfg = new TomlConfigHandler(MOD_ID, properties);

        if (cfg.getConfigFile().exists()) {
            cfg.loadConfig();
        } else {
            try {cfg.getConfigFile().createNewFile();} catch (IOException e) {throw new RuntimeException(e);}
            cfg.writeConfig();
        }

        try {
            DIMENSION = AetherConfig.cfg.getInt("DIMENSION");
        } catch (NullPointerException e) {
            DIMENSION = dimensionDefault;
        }

        try {
            EXTRA_HEALTH = AetherConfig.cfg.getInt("EXTRA_HEALTH");
        } catch (NullPointerException e) {
            EXTRA_HEALTH = extraHealthDefault;
        }

        try {
            QUICK_SOIL_SPEED_CAP = AetherConfig.cfg.getInt("QUICK_SOIL_SPEED_CAP");
        } catch (NullPointerException e) {
            QUICK_SOIL_SPEED_CAP = quicksoilCapDefault;
        }

    }
}
