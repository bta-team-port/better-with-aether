package teamport.aether.world;

import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.entity.animal.MobFireflyCluster;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.phow.MobPhow;
import teamport.aether.entity.phyg.MobPhyg;
import teamport.aether.entity.sheepuff.MobSheepuff;
import teamport.aether.entity.zephyr.MobZephyr;
import teamport.aether.gen.feature.WorldFeatureTreeAether;
import teamport.aether.gen.feature.WorldFeatureTreeGoldenOak;

import java.util.Random;

public class BiomeAether extends Biome {
    public BiomeAether(String key) {
        super(key);
        spawnableAmbientCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableMonsterList.clear();

        this.spawnableCreatureList.add(new SpawnListEntry(MobPhyg.class, 102));
        this.spawnableCreatureList.add(new SpawnListEntry(MobPhow.class, 102));
        this.spawnableCreatureList.add(new SpawnListEntry(MobSheepuff.class, 102));


        this.spawnableMonsterList.add(new SpawnListEntry(MobZephyr.class, 10));

        this.spawnableAmbientCreatureList.add(new SpawnListEntry(MobFireflyCluster.class, 10));
    }

    public int getSkyColor(float temperature) {
        return 0xc0c0ff;
    }
    public WorldFeature getRandomWorldGenForTrees(Random random) {
        if (random.nextInt(10) == 0) {
            return new WorldFeatureTreeGoldenOak(AetherBlocks.LEAVES_OAK_GOLDEN.id(), AetherBlocks.LOG_OAK_GOLDEN.id());
        }
        return new WorldFeatureTreeAether(AetherBlocks.LEAVES_SKYROOT.id(), AetherBlocks.LOG_SKYROOT.id(), 4);
    }
}
