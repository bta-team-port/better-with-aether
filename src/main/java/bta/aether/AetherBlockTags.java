package bta.aether;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.tag.Tag;

import java.lang.reflect.Field;

import static net.minecraft.core.block.tag.BlockTags.TAG_LIST;

public class AetherBlockTags {

    public static Tag<Block> MINEABLE_BY_AETHER_PICKAXE = Tag.of("mineable_by_aether_pickaxe");
    public static Tag<Block> MINEABLE_BY_AETHER_AXE = Tag.of("mineable_by_aether_axe");
    public static Tag<Block> MINEABLE_BY_AETHER_SHOVEL = Tag.of("mineable_by_aether_shovel");
    public static Tag<Block> MINEABLE_BY_AETHER_SWORD = Tag.of("mineable_by_aether_sword");

    public static Tag<Block> GROWS_AETHER_FLOWERS = Tag.of("grows_flowers");
    public static Tag<Block> PASSIVE_MOBS_SPAWN = Tag.of("aether_passive_mobs_spawn");

    public static Tag<Block> AETHER_JAR_RENDERING = Tag.of("aether_jar_dirt");

    static {
        for (Field field : AetherBlockTags.class.getDeclaredFields()) {
            if (!field.getType().equals(Tag.class)) continue;
            try {
                TAG_LIST.add((Tag)field.get(null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
