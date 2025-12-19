package teamport.aether.world.biome;

import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.entity.animal.MobFireflyCluster;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.weather.Weathers;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;
import teamport.aether.entity.animal.aerwhale.MobAerwhale;
import teamport.aether.entity.animal.moa.MobMoaBlack;
import teamport.aether.entity.animal.moa.MobMoaBlue;
import teamport.aether.entity.animal.moa.MobMoaWhite;
import teamport.aether.entity.animal.phow.MobPhow;
import teamport.aether.entity.animal.phyg.MobPhyg;
import teamport.aether.entity.animal.sheepuff.MobSheepuff;
import teamport.aether.entity.monster.aechorplant.MobAechorPlant;
import teamport.aether.entity.monster.cockatrice.MobCockatrice;
import teamport.aether.entity.monster.swet.MobSwet;
import teamport.aether.entity.monster.swet.MobSwetGold;
import teamport.aether.entity.animal.whirly.MobWhirly;
import teamport.aether.entity.monster.tempest.MobTempest;
import teamport.aether.entity.monster.zephyr.MobZephyr;
import teamport.aether.world.feature.terrain.WorldFeatureAetherTree;
import teamport.aether.world.feature.terrain.WorldFeatureAetherTreeGoldenOak;

import java.util.Random;

public class BiomeAether extends Biome {
    public BiomeAether(String key) {
        super(key);
        setColor(0xc0c0ff);
        setTopBlock(AetherBlocks.GRASS_AETHER.id());
        setFillerBlock(AetherBlocks.DIRT_AETHER.id());
        setBlockedWeathers(Weathers.OVERWORLD_RAIN, Weathers.OVERWORLD_SNOW, Weathers.OVERWORLD_STORM);

        spawnableAmbientCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableMonsterList.clear();

        this.spawnableCreatureList.add(new SpawnListEntry(MobPhyg.class, 102));
        this.spawnableCreatureList.add(new SpawnListEntry(MobPhow.class, 102));
        this.spawnableCreatureList.add(new SpawnListEntry(MobSheepuff.class, 102));
        this.spawnableCreatureList.add(new SpawnListEntry(MobAerbunny.class, 102));
        this.spawnableCreatureList.add(new SpawnListEntry(MobWhirly.class, 10));

        this.spawnableAmbientCreatureList.add(new SpawnListEntry(MobAerwhale.class, 5));
        this.spawnableAmbientCreatureList.add(new SpawnListEntry(MobFireflyCluster.class, 30));

        this.spawnableCreatureList.add(new SpawnListEntry(MobMoaBlue.class, 51));
        this.spawnableCreatureList.add(new SpawnListEntry(MobMoaWhite.class, 26));
        this.spawnableCreatureList.add(new SpawnListEntry(MobMoaBlack.class, 13));

        this.spawnableMonsterList.add(new SpawnListEntry(MobZephyr.class, 10));
        this.spawnableMonsterList.add(new SpawnListEntry(MobSwet.class, 5));
        this.spawnableMonsterList.add(new SpawnListEntry(MobSwetGold.class, 2));
        this.spawnableMonsterList.add(new SpawnListEntry(MobAechorPlant.class, 5));
        this.spawnableMonsterList.add(new SpawnListEntry(MobCockatrice.class, 10));
        this.spawnableMonsterList.add(new SpawnListEntry(MobTempest.class, 5));
    }

    @Override
    public int getSkyColor(float temperature) {
        return 0xc0c0ff;
    }

    @Override
    public WorldFeature getRandomWorldGenForTrees(Random random) {
        return random.nextInt(10) == 0 ? new WorldFeatureAetherTree(AetherBlocks.LEAVES_SKYROOT.id(), AetherBlocks.LOG_SKYROOT.id(), 4)
            : new WorldFeatureAetherTreeGoldenOak();
    }
}
