package teamport.aether.world.type;

import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypeGroups;
import net.minecraft.core.world.type.WorldTypes;
import net.minecraft.core.world.type.tag.WorldTypeTags;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"java:S1104", "java:S1444", "java:S3008"})
public abstract class AetherWorldTypes {
    public static WorldType AETHER_DEFAULT;
    public static WorldType AETHER_EXTENDED;
    public static WorldType AETHER_SKYBLOCK;
    public static WorldType AETHER_RETRO;

    public static final @NonNull Tag<WorldType> AETHER = Tag.of("aether");

    protected AetherWorldTypes() {
    }

    private static boolean hasInit = false;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            initializeWorldTypes();
        }

    }

    public static void initializeWorldTypes() {
        AETHER_EXTENDED = WorldTypes.register("aether:aether.extended", new WorldTypeAetherExtended
            (WorldTypeAether.defaultProperties("worldType.aether.extended").withTags(AETHER)
                .portalBounds(0, 256)));

        AETHER_DEFAULT = WorldTypes.register("aether:aether.default", new WorldTypeAether
            (WorldTypeAether.defaultProperties("worldType.aether.default").withTags(AETHER)
                .bounds(0, 127, 0)
                .portalBounds(0, 256)));


        AETHER_SKYBLOCK = WorldTypes.register("aether:aether.skyblock", new WorldTypeAetherSkyblock
            (WorldTypeAether.defaultProperties("worldType.aether.skyblock").withTags(AETHER)
                .bounds(0, 127, 0)
                .portalBounds(0, 256)));


        AETHER_RETRO = WorldTypes.register("aether:aether.retro", new WorldTypeAetherRetro
            (WorldTypeAether.defaultProperties("worldType.aether.retro").withTags(AETHER, WorldTypeTags.RETRO)
                .seasonConfig(null)
                .bounds(0, 127, 0)
                .portalBounds(0, 256)));
    }

    public static void addToWorldTypeGroups(Dimension aether) {
        Map<WorldType, WorldType> overworldToAetherWorldTypeMap = new HashMap<>();
        for (WorldType type : new WorldType[]{
            WorldTypes.OVERWORLD_EXTENDED,
            WorldTypes.OVERWORLD_AMPLIFIED,
            WorldTypes.OVERWORLD_INLAND,
            WorldTypes.OVERWORLD_PARADISE,
            WorldTypes.OVERWORLD_WOODS,
            WorldTypes.OVERWORLD_HELL,
            WorldTypes.OVERWORLD_WINTER,
            WorldTypes.OVERWORLD_ISLANDS,
            WorldTypes.OVERWORLD_FLOATING,
            WorldTypes.FLAT,
            WorldTypes.EMPTY,
            WorldTypes.DEBUG,
        }) {
            overworldToAetherWorldTypeMap.put(type, AETHER_EXTENDED);
        }

        overworldToAetherWorldTypeMap.put(WorldTypes.OVERWORLD_DEFAULT, AETHER_DEFAULT);
        overworldToAetherWorldTypeMap.put(WorldTypes.OVERWORLD_SKYBLOCK, AETHER_SKYBLOCK);
        overworldToAetherWorldTypeMap.put(WorldTypes.OVERWORLD_RETRO, AETHER_RETRO);
        overworldToAetherWorldTypeMap.put(WorldTypes.OVERWORLD_CLASSIC, AETHER_RETRO);
        overworldToAetherWorldTypeMap.put(WorldTypes.OVERWORLD_INDEV, AETHER_RETRO);

        for (WorldTypeGroups.Group group : WorldTypeGroups.GROUPS) {
            WorldType overworldType = group.get(Dimension.OVERWORLD);
            group.with(aether, overworldToAetherWorldTypeMap.getOrDefault(overworldType, AETHER_DEFAULT));
            group.with(Dimension.DRIFT, Dimension.DRIFT.defaultWorldType);
        }
    }
}
