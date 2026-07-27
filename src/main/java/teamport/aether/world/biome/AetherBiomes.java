package teamport.aether.world.biome;

import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;

public class AetherBiomes {
    public static Biome AETHER_PLAINS;

    private static boolean hasInit = false;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            initializeBiomes();
        }
    }

    private static void initializeBiomes() {
        AETHER_PLAINS = Biomes.register("aether:aether.plains", (new BiomeAether("aether.plains")));
    }
}
