package bta.aether.world;

import bta.aether.block.AetherBlocks;
import bta.aether.world.generate.feature.WorldFeatureTreeSkyroot;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.entity.animal.EntityFireflyCluster;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeFancy;

import java.util.Random;

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
    public WorldFeature getRandomWorldGenForTrees(Random random) {
        if (random.nextInt(10) == 0) {
            return new WorldFeatureTreeFancy(AetherBlocks.leavesOakGolden.id,AetherBlocks.logOakGolden.id,5);
        }
        return new WorldFeatureTreeSkyroot(5);
    }
}
