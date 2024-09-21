package bta.aether;

import bta.aether.block.AetherBlocks;
import bta.aether.item.AetherItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.util.ConfigUpdater;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AetherConfig {
    public static ConfigUpdater updater = ConfigUpdater.fromProperties();
    public static final Toml properties = new Toml("AetherMod TOML Config");
    public static TomlConfigHandler cfg;

    public static int blockIDs = 10000;

    public static int itemIDs = 30000;

    static {
        properties.addCategory("aether")
                .addEntry("cfgVersion", 5);

        properties.addCategory("Block IDs");
        properties.addEntry("Block IDs.startingID", 10000);
        properties.addCategory("Item IDs");
        properties.addEntry("Item IDs.startingID", 30000);


        List<Field> blockFields = Arrays.stream(AetherBlocks.class.getDeclaredFields()).filter((F)-> Block.class.isAssignableFrom(F.getType())).collect(Collectors.toList());
        for (Field blockField : blockFields) {
            properties.addEntry("Block IDs." + blockField.getName(), blockIDs++);
        }
        List<Field> itemFields = Arrays.stream(AetherItems.class.getDeclaredFields()).filter((F)-> Item.class.isAssignableFrom(F.getType())).collect(Collectors.toList());
        for (Field itemField : itemFields) {
            properties.addEntry("Item IDs." + itemField.getName(), itemIDs++);
        }

        cfg = new TomlConfigHandler(updater, AetherMod.MOD_ID, properties);

    }
}
