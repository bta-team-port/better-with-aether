package teamport.aether.world.type;

import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;

public abstract class AetherWorldTypes {
    public static WorldType AETHER_DEFAULT;
    public static WorldType AETHER_EXTENDED;
    public static WorldType AETHER_SKYBLOCK;
    public static WorldType AETHER_RETRO;

    public AetherWorldTypes() {
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
                (WorldTypeAether.defaultProperties("worldtype.aether.extended")
                        .portalBounds(64, 192)));

        AETHER_DEFAULT = WorldTypes.register("aether:aether.default", new WorldTypeAether
                (WorldTypeAether.defaultProperties("worldtype.aether.default")
                        .bounds(0, 127, 0)
                        .portalBounds(32, 96)));


        AETHER_SKYBLOCK = WorldTypes.register("aether:aether.skyblock", new WorldTypeAetherSkyblock
                (WorldTypeAether.defaultProperties("worldtype.aether.skyblock")
                        .bounds(0, 127, 0)));


        AETHER_RETRO = WorldTypes.register("aether:aether.retro", new WorldTypeAether
                (WorldTypeAether.defaultProperties("worldtype.aether.retro")
                        .bounds(0, 127, 0)
                        .portalBounds(32, 96)
                        .setRetro()));
    }
}