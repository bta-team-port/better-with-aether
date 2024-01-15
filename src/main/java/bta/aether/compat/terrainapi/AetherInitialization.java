package bta.aether.compat.terrainapi;

import bta.aether.block.AetherBlocks;
import bta.aether.world.AetherDimension;
import bta.aether.world.generate.feature.WorldFeatureAetherOre;
import bta.aether.world.generate.feature.WorldFeatureTreeSkyroot;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.generate.feature.WorldFeatureFlowers;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeFancy;
import useless.terrainapi.config.OreConfig;
import useless.terrainapi.generation.StructureFeatures;
import useless.terrainapi.generation.overworld.OverworldFunctions;
import useless.terrainapi.generation.overworld.OverworldOreFeatures;
import useless.terrainapi.generation.overworld.OverworldRandomFeatures;
import useless.terrainapi.initialization.BaseInitialization;

import java.util.HashMap;
import java.util.Map;

import static bta.aether.Aether.MOD_ID;

public class AetherInitialization extends BaseInitialization {
    private static final TerrainAetherConfig aetherConfig = ChunkDecoratorAetherAPI.aetherConfig;
    @Override
    protected void initValues() {
        aetherConfig.addTreeDensity(AetherDimension.biomeAether, 64);
        aetherConfig.addLakeDensity(AetherDimension.biomeAether, 1);
    }

    @Override
    protected void initStructure() {
        StructureFeatures structureFeatures = ChunkDecoratorAetherAPI.structureFeatures;

        structureFeatures.addFeature(
            AetherFunctions::generateQuickSoil, null
        );

        structureFeatures.addFeature(
                AetherFunctions::generateClouds, null
        );
    }
    public static Map<Integer, Integer> oreIceStoneMap = new HashMap<Integer, Integer>(){{
        put(AetherBlocks.holystone.id, AetherBlocks.icestone.id);
    }};
    public static Map<Integer, Integer> oreAmbrosiumMap = new HashMap<Integer, Integer>(){{
        put(AetherBlocks.holystone.id, AetherBlocks.oreAmbrosiumHolystone.id);
    }};
    public static Map<Integer, Integer> oreZaniteMap = new HashMap<Integer, Integer>(){{
        put(AetherBlocks.holystone.id, AetherBlocks.oreZaniteHolystone.id);
    }};
    public static Map<Integer, Integer> oreGravititeMap = new HashMap<Integer, Integer>(){{
        put(AetherBlocks.holystone.id, AetherBlocks.oreGravititeHolystone.id);
    }};
    public static Map<Integer, Integer> iceMap = new HashMap<Integer, Integer>(){{
        put(AetherBlocks.holystone.id, Block.ice.id);
    }};
    public static Map<Integer, Integer> lavaMap = new HashMap<Integer, Integer>(){{
        put(AetherBlocks.holystone.id, Block.fluidLavaStill.id);
    }};

    @Override
    protected void initOre() {
        OverworldOreFeatures oreFeatures = ChunkDecoratorAetherAPI.oreFeatures;
        addManagedAetherOreFeature(MOD_ID, oreIceStoneMap,AetherBlocks.icestone, 32, 8, 0, 255);
        addManagedAetherOreFeature(MOD_ID, oreAmbrosiumMap,AetherBlocks.oreAmbrosiumHolystone, 16, 24, 0, 255);
        addManagedAetherOreFeature(MOD_ID, oreZaniteMap,AetherBlocks.oreZaniteHolystone, 8, 16, 0, 255);
        addManagedAetherOreFeature(MOD_ID, oreGravititeMap,AetherBlocks.oreGravititeHolystone, 5, 16, 0, 255);
        addManagedAetherOreFeature(MOD_ID, oreZaniteMap,AetherBlocks.oreZaniteHolystone, 2, 2, 0, 128);

        addManagedAetherOreFeature(MOD_ID, iceMap,Block.fluidWaterFlowing, 3, 8, 0, 255);
    }
    public static void addManagedAetherOreFeature(String modID, Map<Integer, Integer> blockMap, Block configSymbol, int defaultClusterSize, int defaultChances, int minY, int maxY){
        float defaultRangeStart = minY/255f;
        float defaultRangeEnd = maxY/255f;
        OreConfig config = ChunkDecoratorAetherAPI.aetherConfig;
        config.setOreValues(modID, configSymbol, defaultClusterSize, defaultChances, defaultRangeStart, defaultRangeEnd);
        ChunkDecoratorAetherAPI.oreFeatures.addFeature(
                x -> new WorldFeatureAetherOre(config.clusterSize.get(configSymbol.getKey()), blockMap),
                null,
                OverworldFunctions::getStandardOreBiomesDensity,
                new Object[]{config.chancesPerChunk.get(configSymbol.getKey()), null},
                config.verticalStartingRange.get(configSymbol.getKey()),
                config.verticalEndingRange.get(configSymbol.getKey())
        );
    }
    @Override
    protected void initRandom() {
        OverworldRandomFeatures randomFeatures = ChunkDecoratorAetherAPI.randomFeatures;

        randomFeatures.addFeatureSurface(new WorldFeatureTreeSkyroot(5), 2);
        randomFeatures.addFeatureSurface(new WorldFeatureTreeFancy(AetherBlocks.leavesOakGolden.id,AetherBlocks.logOakGolden.id,5), 15); // might need a custom class for this but i've seen no issues thus far.
        randomFeatures.addFeature(new WorldFeatureFlowers(AetherBlocks.flowerPurple.id), 2, 1);
        randomFeatures.addFeature(new WorldFeatureFlowers(AetherBlocks.flowerWhite.id), 2, 1);
    }

    @Override
    protected void initBiome() {

    }
}
