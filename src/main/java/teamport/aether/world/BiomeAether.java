package teamport.aether.world;

import net.minecraft.core.world.biome.Biome;
import teamport.aether.blocks.AetherBlocks;

public class BiomeAether extends Biome {
    public BiomeAether(String key) {
        super(key);
        setColor(353825);
        setTopBlock(AetherBlocks.GRASS_AETHER.id());
        setFillerBlock(AetherBlocks.DIRT_AETHER.id());
        spawnableAmbientCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableMonsterList.clear();
    }

    public int getSkyColor(float temperature) {
        return 0xc0c0ff;
    }
}
