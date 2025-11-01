package teamport.aether.world.biome;

import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;

public class AetherBiomes {
    public static Biome AETHER_PLAINS;

    public AetherBiomes() {
    }

    private static boolean hasInit = false;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            initializeBiomes();
        }
    }

    public static void initializeBiomes() {
        AETHER_PLAINS = Biomes.register("aether:plains", (new BiomeAether("aether.plains")));
    }
}
