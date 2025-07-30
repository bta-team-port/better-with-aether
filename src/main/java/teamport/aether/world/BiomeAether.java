package teamport.aether.world;

import net.minecraft.core.world.biome.Biome;

public class BiomeAether extends Biome {
    public BiomeAether(String key) {
        super(key);
        spawnableAmbientCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableMonsterList.clear();
    }

    public int getSkyColor(float temperature) {
        return 0xc0c0ff;
    }
}
