package teamport.aether.item;

import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.AetherMod;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AetherItemTags {
    private AetherItemTags(){}

    public static final Tag<Item> MOAS_FAVOURITE_ITEM = Tag.of("moas_favourite_item");
    public static final Tag<Item> NATURE_STAFF_FOLLOW = Tag.of("nature_staff_follow");
    public static final Tag<Item> TRINKET = Tag.of("trinket"); // only assign to vanilla items

    // Tags for Item immunities.
    public static final Tag<Item> IMMUNE_TO_FIRE_DAMAGE = Tag.of("immune_to_fire_damage");
    public static final Tag<Item> IMMUNE_TO_BLAST_DAMAGE = Tag.of("immune_to_blast_damage");
    private static final Map<DamageType, Tag<Item>> DAMAGE_IMMUNITIES = new HashMap<>();

    public static final Tag<Item> FALLS_UPWARDS = Tag.of("falls_upwards");

    static {
        DAMAGE_IMMUNITIES.put(DamageType.FIRE, AetherItemTags.IMMUNE_TO_FIRE_DAMAGE);
        DAMAGE_IMMUNITIES.put(DamageType.BLAST, AetherItemTags.IMMUNE_TO_BLAST_DAMAGE);
    }

    public static boolean isImmuneToType(Item item, DamageType type){
        Tag<Item> tag = DAMAGE_IMMUNITIES.get(type);
        return tag != null && item.hasTag(tag);
    }

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
