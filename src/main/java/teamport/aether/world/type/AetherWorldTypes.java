package teamport.aether.world.type;

import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypeGroups;
import net.minecraft.core.world.type.WorldTypes;
import net.minecraft.core.world.type.tag.WorldTypeTags;

import java.util.HashMap;
import java.util.Map;

public abstract class AetherWorldTypes {
    public static WorldType AETHER_DEFAULT;
    public static WorldType AETHER_EXTENDED;
    public static WorldType AETHER_SKYBLOCK;
    public static WorldType AETHER_RETRO;

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
            (WorldTypeAether.defaultProperties("worldType.aether.extended")
                .portalBounds(0, 256)));

        AETHER_DEFAULT = WorldTypes.register("aether:aether.default", new WorldTypeAether
            (WorldTypeAether.defaultProperties("worldType.aether.default")
                .bounds(0, 127, 0)
                .portalBounds(0, 256)));


        AETHER_SKYBLOCK = WorldTypes.register("aether:aether.skyblock", new WorldTypeAetherSkyblock
            (WorldTypeAether.defaultProperties("worldType.aether.skyblock")
                .bounds(0, 127, 0)
                .portalBounds(0, 256)));


        AETHER_RETRO = WorldTypes.register("aether:aether.retro", new WorldTypeAetherRetro
            (WorldTypeAether.defaultProperties("worldType.aether.retro")
                .seasonConfig(null)
                .bounds(0, 127, 0)
                .portalBounds(0, 256)));
        WorldTypeTags.RETRO.tag(AETHER_RETRO);
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
