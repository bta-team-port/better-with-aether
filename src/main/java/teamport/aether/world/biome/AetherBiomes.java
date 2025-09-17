package teamport.aether.world.biome;

import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.world.biome.Biome;

public class AetherBiomes {
    public static Biome AETHER_PLAINS;

    public AetherBiomes() {
    }

    public static Biome register(String key, Biome biome) {
        Registries.BIOMES.register(key, biome);
        return biome;
    }

    private static boolean hasInit = false;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            initializeBiomes();
        }
    }

    public static void initializeBiomes() {
        AETHER_PLAINS = register("aether:plains", (new BiomeAether("aether.plains")));
    }
}
