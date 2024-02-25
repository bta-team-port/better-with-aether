package bta.aether.compat.terrainapi;

import bta.aether.block.AetherBlocks;
import bta.aether.world.AetherDimension;
import bta.aether.world.generate.feature.WorldFeatureAetherOre;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.generate.feature.WorldFeatureFlowers;
import net.minecraft.core.world.generate.feature.WorldFeatureTallGrass;
import useless.terrainapi.config.OreConfig;
import useless.terrainapi.generation.StructureFeatures;
import useless.terrainapi.generation.overworld.OverworldBiomeFeatures;
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
        aetherConfig.addTreeDensity(AetherDimension.biomeAether, 1);
        aetherConfig.addLakeDensity(AetherDimension.biomeAether, 4);
    }

    @Override
    protected void initStructure() {
        StructureFeatures structureFeatures = ChunkDecoratorAetherAPI.structureFeatures;

        structureFeatures.addFeature(AetherFunctions::generateAetherDungeonSilver, null);
        structureFeatures.addFeature(
            AetherFunctions::generateQuickSoil, null
        );
        structureFeatures.addFeature(AetherFunctions::generateLakeFeature, null);
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
        put(AetherBlocks.holystone.id, Block.fluidWaterFlowing.id);
    }};
    public static Map<Integer, Integer> waterMap = new HashMap<Integer, Integer>(){{
        put(AetherBlocks.holystone.id, Block.fluidWaterFlowing.id);
    }};

    @Override
    protected void initOre() {
        OverworldOreFeatures oreFeatures = ChunkDecoratorAetherAPI.oreFeatures;
        addManagedAetherOreFeature(MOD_ID, oreIceStoneMap,AetherBlocks.icestone, 32, 8, 0, 255);
        addManagedAetherOreFeature(MOD_ID, oreAmbrosiumMap,AetherBlocks.oreAmbrosiumHolystone, 32, 20, 0, 255);
        addManagedAetherOreFeature(MOD_ID, oreZaniteMap,AetherBlocks.oreZaniteHolystone, 8, 20, 0, 255);
        addManagedAetherOreFeature(MOD_ID, oreGravititeMap,AetherBlocks.oreGravititeHolystone, 7, 1, 0, 100);

        addManagedAetherOreFeature(MOD_ID, waterMap,Block.fluidWaterFlowing, 1, 24, 0, 255);
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
        randomFeatures.addFeature(new WorldFeatureFlowers(AetherBlocks.flowerPurple.id), 2, 1);
        randomFeatures.addFeature(new WorldFeatureFlowers(AetherBlocks.flowerWhite.id), 2, 1);
        randomFeatures.addFeature(new WorldFeatureTallGrass(AetherBlocks.aetherTallGrass.id), 8, 2);

        randomFeatures.addFeature(
                AetherFunctions::getNormalClouds, null,
                OverworldFunctions::getStandardBiomesDensity, new Object[]{3, null},
                8, 20f/255, 220f/255);
        randomFeatures.addFeature(
                AetherFunctions::getYellowClouds, null,
                OverworldFunctions::getStandardBiomesDensity, new Object[]{1, null},
                25, 210f/255, 240f/255);
        randomFeatures.addFeature(
                AetherFunctions::getFlatClouds, null,
                OverworldFunctions::getStandardBiomesDensity, new Object[]{1, null},
                40, 10f/255, 20f/255);
    }

    @Override
    protected void initBiome() {
        OverworldBiomeFeatures biomeFeatures = ChunkDecoratorAetherAPI.biomeFeatures;
        biomeFeatures.addFeature(OverworldFunctions::getTreeFeature, null, AetherFunctions::getTreeDensity, null, -1.0F);
    }
}
