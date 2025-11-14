package teamport.aether.items;

import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.tag.ItemTags;

import java.lang.reflect.Field;

import static net.minecraft.core.block.tag.BlockTags.TAG_LIST;
import static org.apache.log4j.builders.appender.SocketAppenderBuilder.LOGGER;

public class AetherItemTags {

    public static Tag<Item> MOAS_FAVOURITE_ITEM = Tag.of("moas_favourite_item");
    public static Tag<Item> NATURE_STAFF_FOLLOW = Tag.of("nature_staff_follow");
    public static Tag<Item> TRINKET = Tag.of("trinket"); // only assign to vanilla items


    static {
        Field[] var0 = ItemTags.class.getDeclaredFields();

        for (Field field : var0) {
            if (field.getType().equals(Tag.class)) {
                try {
                    TAG_LIST.add((Tag) field.get(null));
                } catch (Exception var5) {
                    LOGGER.error("Failed to add tag '{}'!", field.getName(), var5);
                }
            }
        }

    }
}
