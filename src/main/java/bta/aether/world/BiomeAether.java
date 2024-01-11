package bta.aether.world;

import net.minecraft.core.world.biome.Biome;

import java.awt.*;

public class BiomeAether extends Biome {
    public BiomeAether() {
        spawnableAmbientCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableMonsterList.clear();
    }

    public int getSkyColor(float temperature) {
    return 12632319;
    }
}
