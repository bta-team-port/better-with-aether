package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.tag.Tag;
import teamport.aether.AetherMod;

import java.lang.reflect.Field;

@SuppressWarnings({"java:S1104", "java:S1444", "java:S3008", "unchecked"})
public class AetherBlockTags {

    public static Tag<Block<?>> MINEABLE_BY_AETHER_PICKAXE = Tag.of("mineable_by_aether_pickaxe");
    public static Tag<Block<?>> MINEABLE_BY_AETHER_AXE = Tag.of("mineable_by_aether_axe");
    public static Tag<Block<?>> MINEABLE_BY_AETHER_SHOVEL = Tag.of("mineable_by_aether_shovel");
    public static Tag<Block<?>> MINEABLE_BY_AETHER_SWORD = Tag.of("mineable_by_aether_sword");

    public static Tag<Block<?>> GROWS_AETHER_FLOWERS = Tag.of("grows_aether_flowers");
    public static Tag<Block<?>> GROWS_AETHER_TREES = Tag.of("grows_aether_trees");
    public static Tag<Block<?>> PASSIVE_MOBS_SPAWN = Tag.of("aether_passive_mobs_spawn");

    public static Tag<Block<?>> AETHER_DOES_NOT_FIT_IN_MINECART = Tag.of("aether_does_not_fit_in_minecart");

    public static Tag<Block<?>> PLANTABLE_IN_AETHER_JAR = Tag.of("plantable_in_aether_jar");

    static {
        for (Field field : AetherBlockTags.class.getDeclaredFields()) {
            if (field.getType().equals(Tag.class)) {
                try {
                    @SuppressWarnings("unchecked")
                    Tag<Block<?>> tag = (Tag<Block<?>>) field.get(null);
                    BlockTags.TAG_LIST.add(tag);
                } catch (Exception e) {
                    AetherMod.LOGGER.error("Failed to add tag '{}'!", field.getName(), e);
                }
            }
        }
    }
}
