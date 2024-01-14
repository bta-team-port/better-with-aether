package bta.aether.world;

import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.entity.animal.EntityFireflyCluster;
import net.minecraft.core.world.biome.Biome;

import java.awt.*;

public class BiomeAether extends Biome {
    public BiomeAether() {
        spawnableAmbientCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableMonsterList.clear();
        this.spawnableAmbientCreatureList.add(new SpawnListEntry(EntityFireflyCluster.class, 10));
    }

    public int getSkyColor(float temperature) {
    return 12632319;
    }
}
