package bta.aether.world;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.*;
import bta.aether.world.generate.feature.WorldFeatureTreeSkyroot;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.entity.animal.EntityFireflyCluster;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class BiomeAether extends Biome {
    public BiomeAether(String key) {
        super(key);
        spawnableAmbientCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableMonsterList.clear();

        this.spawnableCreatureList.add(new SpawnListEntry(EntityMoa.class, 10));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityAerbunny.class, 102));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPhyg.class, 102));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPhow.class, 102));
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySheepuff.class, 102));


        this.spawnableMonsterList.add(new SpawnListEntry(EntityZephyr.class, 10));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityWhirlwind.class, 10));

        this.spawnableAmbientCreatureList.add(new SpawnListEntry(EntityFireflyCluster.class, 10));
    }

    public int getSkyColor(float temperature) {
    return 0xc0c0ff;
    }
    public WorldFeature getRandomWorldGenForTrees(Random random) {
        if (random.nextInt(10) == 0) {
            return new WorldFeatureGoldenOak(AetherBlocks.leavesOakGolden.id,AetherBlocks.logOakGolden.id,5);
        }
        return new WorldFeatureTreeSkyroot(5);
    }
}
