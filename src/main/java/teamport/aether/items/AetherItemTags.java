package teamport.aether.items;

import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.tag.ItemTags;
import teamport.aether.AetherMod;

import java.lang.reflect.Field;

public class AetherItemTags {

    public static final Tag<Item> MOAS_FAVOURITE_ITEM = Tag.of("moas_favourite_item");
    public static final Tag<Item> NATURE_STAFF_FOLLOW = Tag.of("nature_staff_follow");
    public static final Tag<Item> TRINKET = Tag.of("trinket"); // only assign to vanilla items

    @SafeVarargs
    public static Tag<Item>[] tags(Tag<Item>... tags) {
        return tags;
    }

    static {
        for (Field field : AetherItemTags.class.getDeclaredFields()) {
            if (!field.getType().equals(Tag.class)) continue;
            try {
                @SuppressWarnings("unchecked")
                Tag<Item> tag = (Tag<Item>) field.get(null);
                ItemTags.TAG_LIST.add(tag);
            } catch (Exception e) {
                AetherMod.LOGGER.error("Failed to add tag '{}'!", field.getName(), e);
            }
        }
    }
}
