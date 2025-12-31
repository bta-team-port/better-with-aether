package teamport.aether.world.type;

import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;

@SuppressWarnings({"java:S1104", "java:S1444", "java:S3008"})
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
                .portalBounds(0, 192)));

        AETHER_DEFAULT = WorldTypes.register("aether:aether.default", new WorldTypeAether
            (WorldTypeAether.defaultProperties("worldType.aether.default")
                .bounds(0, 127, 0)
                .portalBounds(0, 96)));


        AETHER_SKYBLOCK = WorldTypes.register("aether:aether.skyblock", new WorldTypeAetherSkyblock
            (WorldTypeAether.defaultProperties("worldType.aether.skyblock")
                .bounds(0, 127, 0)));


        AETHER_RETRO = WorldTypes.register("aether:aether.retro", new WorldTypeAetherRetro
            (WorldTypeAether.defaultProperties("worldType.aether.retro")
                .seasonConfig(null)
                .bounds(0, 127, 0)
                .portalBounds(0, 96)
                .setRetro()));
    }
}
