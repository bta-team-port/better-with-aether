package teamport.aether.world;

import net.minecraft.core.Global;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.provider.BiomeProvider;
import net.minecraft.core.world.biome.provider.BiomeProviderSingleBiome;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.generate.chunk.perlin.overworld.floating.ChunkGeneratorOverworldFloating;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.weather.Weathers;
import net.minecraft.core.world.wind.WindProviderGeneric;
import teamport.aether.blocks.AetherBlocks;

public abstract class WorldTypeAether extends WorldType {
    public final float[] colorsSunriseSunset = new float[4];

    public WorldTypeAether(String languageKey) {
        super(Properties.of(languageKey)
                .defaultWeather(Weathers.OVERWORLD_CLEAR)
                .windManager(new WindProviderGeneric())
                .brightnessRamp(createLightRamp())
                .seasonConfig(SeasonConfig.builder().withSingleSeason(Seasons.NULL).build())
                .dayNightCycleTicks(Global.DAY_LENGTH_TICKS)
        );
    }

    public static float[] createLightRamp() {
        float[] brightnessRamp = new float[32];
        float f = 0.05F;

        for(int i = 0; i <= 31; ++i) {
            float f1 = 1.0F - (float)i / 15.0F;
            if (i > 15) {
                f1 = 0.0F;
            }

            brightnessRamp[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }

        return brightnessRamp;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getMaxY() {
        return 255;
    }

    @Override
    public int getOceanY() {
        return 0;
    }

    @Override
    public int getOceanBlockId() {
        return 0;
    }

    @Override
    public int getFillerBlockId() {
        return AetherBlocks.COBBLE_HOLYSTONE.id();
    }

    @Override
    public BiomeProvider createBiomeProvider(World world) {
        return new BiomeProviderSingleBiome(AetherDimension.biomeAether, 0.5, 0.5, 0.5);
    }

    @Override
    public ChunkGenerator createChunkGenerator(World world) {
        return new ChunkGeneratorOverworldFloating(world);
    }

    @Override
    public boolean isValidSpawn(World world, int i, int j, int k) {
        return world.getBlock(i, j, k) == AetherBlocks.GRASS_AETHER;
    }

    @Override
    public float getCelestialAngle(World world, long tick, float partialTick) {  //TODO REPLACE WITH SUN SPIRIT STUFF
        return this.getTimeOfDay(world, tick, partialTick);
    }

    @Override
    public int getSkyDarken(World world, long tick, float partialTick) { //TODO REPLACE WITH SUN SPIRIT STUFF
        float f1 = this.getCelestialAngle(world, tick, partialTick);
        float f2 = 1.0f - (MathHelper.cos(f1 * 3.141593f * 2.0f) * 2.0f + 0.5f);
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        float weatherOffset = 0.0f;
        Weather currentWeather = world.getCurrentWeather();
        if (currentWeather != null) {
            weatherOffset = (float)currentWeather.subtractLightLevel * world.weatherManager.getWeatherIntensity() * world.weatherManager.getWeatherPower();
        }
        return (int)(f2 * (11.0f - weatherOffset) + weatherOffset);
    }

    @Override
    public boolean mayRespawn() {
        return false;
    }

}
