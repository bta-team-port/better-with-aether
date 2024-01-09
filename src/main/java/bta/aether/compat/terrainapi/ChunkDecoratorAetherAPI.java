package bta.aether.compat.terrainapi;

import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.noise.PerlinNoise;
import useless.terrainapi.config.ConfigManager;
import useless.terrainapi.generation.ChunkDecoratorAPI;
import useless.terrainapi.generation.Parameters;
import useless.terrainapi.generation.StructureFeatures;
import useless.terrainapi.generation.overworld.OverworldBiomeFeatures;
import useless.terrainapi.generation.overworld.OverworldOreFeatures;
import useless.terrainapi.generation.overworld.OverworldRandomFeatures;

import java.util.Random;
import java.util.function.Function;

public class ChunkDecoratorAetherAPI extends ChunkDecoratorAPI {
    public static TerrainAetherConfig aetherConfig = ConfigManager.getConfig("aether", TerrainAetherConfig.class);
    public final PerlinNoise treeDensityNoise;
    public final int treeDensityOverride;
    public static StructureFeatures structureFeatures = new StructureFeatures();
    public static OverworldOreFeatures oreFeatures = new OverworldOreFeatures(aetherConfig);
    public static OverworldRandomFeatures randomFeatures = new OverworldRandomFeatures();
    public static OverworldBiomeFeatures biomeFeatures = new OverworldBiomeFeatures();

    public ChunkDecoratorAetherAPI(World world, int treeDensityOverride) {
        super(world);
        this.treeDensityOverride = treeDensityOverride;
        this.treeDensityNoise = new PerlinNoise(world.getRandomSeed(), 8, 74);
    }

    public ChunkDecoratorAetherAPI(World world) {
        this(world, -1);
    }

    @Override
    public void decorateAPI() {
        int xCoord = parameterBase.chunk.xPosition * 16;
        int zCoord = parameterBase.chunk.zPosition * 16;
        generateStructures(parameterBase.biome, parameterBase.chunk, parameterBase.random);
        generateOreFeatures(parameterBase.biome, xCoord, zCoord, parameterBase.random, parameterBase.chunk);
        generateBiomeFeature(parameterBase.biome,xCoord, zCoord, parameterBase.random, parameterBase.chunk);
        generateRandomFeatures(parameterBase.biome,xCoord, zCoord, parameterBase.random, parameterBase.chunk);
        freezeSurface(xCoord, zCoord);
    }

    @Override
    public void generateStructures(Biome biome, Chunk chunk, Random random){
        int featureSize = structureFeatures.featureFunctionList.size();
        for (int i = 0; i < featureSize; i++) {
            structureFeatures.featureFunctionList.get(i)
                    .apply(new Parameters(parameterBase, structureFeatures.featureParametersList.get(i)));
        }
    }

    @Override
    public void generateOreFeatures(Biome biome, int x, int z, Random random, Chunk chunk){
        int featureSize = oreFeatures.featureFunctionsList.size();
        for (int i = 0; i < featureSize; i++) {
            int density = oreFeatures.densityFunctionsList.get(i)
                    .apply(new Parameters(parameterBase, oreFeatures.densityParametersList.get(i)));

            float startingRange = oreFeatures.startingRangeList.get(i);
            float endingRange = oreFeatures.endingRangeList.get(i);
            generateWithChancesUnderground(oreFeatures.featureFunctionsList.get(i),
                    new Parameters(parameterBase, oreFeatures.featureParametersList.get(i)),
                    density, (int) (startingRange * rangeY), (int) (endingRange * rangeY), x, z, random);
        }
    }

    @Override
    public void generateRandomFeatures(Biome biome, int x, int z, Random random, Chunk chunk){
        int featureSize = randomFeatures.featureFunctionsList.size();
        for (int i = 0; i < featureSize; i++) {
            if (random.nextInt(randomFeatures.inverseProbabilityList.get(i)) != 0) {continue;}
            Function<Parameters, WorldFeature> featureFunction = randomFeatures.featureFunctionsList.get(i);

            int density = randomFeatures.densityFunctionsList.get(i)
                    .apply(new Parameters(parameterBase, randomFeatures.densityParametersList.get(i)));

            float startingRange = randomFeatures.startingRangeList.get(i);
            float endingRange = randomFeatures.endingRangeList.get(i);
            if (-1.01 <= startingRange && startingRange <= -0.99 || -1.01 <= endingRange && endingRange <= -0.99){
                generateWithChancesSurface(featureFunction,
                        new Parameters(parameterBase, randomFeatures.featureParametersList.get(i)),
                        density, x, z, 8, 8, random);
            } else {
                generateWithChancesUnderground(featureFunction,
                        new Parameters(parameterBase, randomFeatures.featureParametersList.get(i)),
                        density, (int) (startingRange * rangeY), (int) (endingRange * rangeY), x, z, 8, 8, random);
            }
        }
    }

    @Override
    public void generateBiomeFeature(Biome biome, int x, int z, Random random, Chunk chunk){
        int featureSize = biomeFeatures.featureFunctionsList.size();
        for (int i = 0; i < featureSize; i++) {
            Function<Parameters, WorldFeature> featureFunction = biomeFeatures.featureFunctionsList.get(i);

            int density = biomeFeatures.densityFunctionsList.get(i)
                    .apply(new Parameters(parameterBase, biomeFeatures.densityParametersList.get(i)));

            float startingRange = biomeFeatures.startingRangeList.get(i);
            float endingRange = biomeFeatures.endingRangeList.get(i);
            if (-1.01 <= startingRange && startingRange <= -0.99 || -1.01 <= endingRange && endingRange <= -0.99){
                generateWithChancesSurface(featureFunction,
                        new Parameters(parameterBase, biomeFeatures.featureParametersList.get(i)),
                        density, x, z, 8, 8, random);
            } else {
                generateWithChancesUnderground(featureFunction,
                        new Parameters(parameterBase, biomeFeatures.featureParametersList.get(i)),
                        density, (int) (startingRange * rangeY), (int) (endingRange * rangeY), x, z, 8, 8, random);
            }
        }
    }
}
