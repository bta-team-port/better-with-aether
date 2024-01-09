package bta.aether.world;

import bta.aether.Aether;
import bta.aether.block.AetherBlocks;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTree;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeFancy;

import java.util.Random;

public class BiomeAether extends Biome {

    public BiomeAether() {
        spawnableAmbientCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableMonsterList.clear();
    }
}
