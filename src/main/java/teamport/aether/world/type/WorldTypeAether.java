package teamport.aether.world.type;

import net.minecraft.core.Global;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.provider.BiomeProvider;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.weather.Weathers;
import net.minecraft.core.world.wind.WindProviderGeneric;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.chunk.BiomeProviderAether;
import teamport.aether.world.chunk.ChunkGeneratorAether;

public class WorldTypeAether extends WorldType {
    public WorldTypeAether(WorldType.Properties properties) {
        super(properties);
    }

    public static WorldType.Properties defaultProperties(String translationKey) {
        return Properties.of(translationKey)
            .brightnessRamp(getLightRamp())
            .defaultWeather(Weathers.OVERWORLD_CLEAR)
            .windManager(new WindProviderGeneric())
            .seasonConfig(SeasonConfig.builder()
                .withSeasonInCycle(Seasons.OVERWORLD_SPRING, 14)
                .withSeasonInCycle(Seasons.OVERWORLD_SUMMER, 14)
                .withSeasonInCycle(Seasons.OVERWORLD_FALL, 14)
                .withSeasonInCycle(Seasons.OVERWORLD_WINTER, 14)
                .build())
            .dayNightCycleTicks(Global.DAY_LENGTH_TICKS)
            .oceanBlock(null)
            .fillerBlock(AetherBlocks.COBBLE_HOLYSTONE);
    }

    public static float[] getLightRamp() {
        float[] brightnessRamp = new float[32];
        float f = 0.05F;

        for (int i = 0; i <= 31; ++i) {
            float f1 = 1.0F - i / 15.0F;
            if (i > 15) {
                f1 = 0.0F;
            }

            brightnessRamp[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }

        return brightnessRamp;
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
    public BiomeProvider createBiomeProvider(World world) {
        return new BiomeProviderAether(world.getRandomSeed(), this);
    }

    @Override
    public ChunkGenerator createChunkGenerator(World world) {
        return new ChunkGeneratorAether(world);
    }

    @Override
    public boolean isValidSpawn(World world, int i, int j, int k) {
        return world.getBlock(i, j, k) == AetherBlocks.GRASS_AETHER;
    }

    @Override
    public float getTimeOfDay(World world, long tick, float partialTick) {
        if (!AetherDimension.isSunspiritDead()) return 0.0F;

        float timeFraction = getTimeFraction(tick, partialTick);

        long currTime = world.getWorldTime();
        if (AetherDimension.getSunspiritDeathTimestamp() != 0 && AetherDimension.getSunspiritDeathTimestamp() + 250 >= currTime) {
            float animProgress = (((currTime + partialTick) - AetherDimension.getSunspiritDeathTimestamp()) / 250);

            if (animProgress == 1) {
                AetherDimension.setSunspiritDeathTimestamp(0);
            }
            return ((float) (-(Math.cos(Math.PI * animProgress) - 1) / 2) * (timeFraction + 1)) % 1;
        }

        return timeFraction;
    }

    private static float getTimeFraction(long tick, float partialTick) {
        int timeTicks = (int) (tick % 0x13880L);
        float timeFraction = (timeTicks + partialTick) / 120000F - 0.25F;

        if (timeTicks > 60000) {
            timeTicks -= 40000;
            timeFraction = (timeTicks + partialTick) / 20000F - 0.25F;
        }

        if (timeFraction < 0.0F) {
            timeFraction++;
        }

        if (timeFraction > 1.0F) {
            timeFraction--;
        }

        float f2 = timeFraction;
        timeFraction = 1.0F - (float) ((Math.cos(timeFraction * 3.1415926535897931D) + 1.0D) / 2D);
        timeFraction = f2 + (timeFraction - f2) / 3F;
        return timeFraction;
    }

    @Override
    public float getCelestialAngle(World world, long tick, float partialTick) {
        return this.getTimeOfDay(world, tick, partialTick);
    }

    @Override
    public int getSkyDarken(World world, long tick, float partialTick) {
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
            weatherOffset = currentWeather.subtractLightLevel * world.weatherManager.getWeatherIntensity() * world.weatherManager.getWeatherPower();
        }
        return (int) (f2 * (11.0f - weatherOffset) + weatherOffset);
    }
}
