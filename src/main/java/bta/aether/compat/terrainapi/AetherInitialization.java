package bta.aether.compat.terrainapi;

import bta.aether.Aether;
import bta.aether.block.AetherBlocks;
import bta.aether.world.AetherDimension;
import bta.aether.world.generate.feature.WorldFeatureTreeSkyroot;
import net.minecraft.core.world.generate.feature.WorldFeatureFlowers;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeFancy;
import useless.terrainapi.generation.StructureFeatures;
import useless.terrainapi.generation.overworld.OverworldOreFeatures;
import useless.terrainapi.generation.overworld.OverworldRandomFeatures;
import useless.terrainapi.initialization.BaseInitialization;

import static bta.aether.Aether.MOD_ID;

public class AetherInitialization extends BaseInitialization {
    private static final TerrainAetherConfig aetherConfig = ChunkDecoratorAetherAPI.aetherConfig;
    @Override
    protected void initValues() {
        aetherConfig.addTreeDensity(AetherDimension.biomeAether, 32);
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

    @Override
    protected void initOre() {
        OverworldOreFeatures oreFeatures = ChunkDecoratorAetherAPI.oreFeatures;
        oreFeatures.addManagedOreFeature(MOD_ID, AetherBlocks.oreAmbrosiumHolystone, 16, 24, 1f, false);
        oreFeatures.addManagedOreFeature(MOD_ID, AetherBlocks.oreZaniteHolystone, 8, 16, 1f, false);
        oreFeatures.addManagedOreFeature(MOD_ID, AetherBlocks.oreZaniteHolystone, 2, 2, 1/3f, false);
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
