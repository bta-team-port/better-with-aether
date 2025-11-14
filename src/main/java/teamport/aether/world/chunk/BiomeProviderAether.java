package teamport.aether.world.chunk;

import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.data.BiomeRange;
import net.minecraft.core.world.biome.data.BiomeRangeMap;
import net.minecraft.core.world.biome.provider.BiomeProvider;
import net.minecraft.core.world.noise.PerlinSimplexNoise;
import net.minecraft.core.world.type.WorldType;
import teamport.aether.world.biome.AetherBiomes;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public final class BiomeProviderAether extends BiomeProvider {
    private static final BiomeRangeMap BIOME_RANGE_MAP = new BiomeRangeMap();
    private static final double TEMPERATURE_X_SCALE = 0.0125;
    private static final double TEMPERATURE_Z_SCALE = 0.0125;
    private static final double TEMPERATURE_EXPONENT = 0.25;
    private static final double TEMPERATURE_FUZZ_PERCENTAGE = 0.01;
    private static final double HUMIDITY_X_SCALE = 0.025;
    private static final double HUMIDITY_Z_SCALE = 0.025;
    private static final double HUMIDITY_EXPONENT = 0.3;
    private static final double HUMIDITY_FUZZ_PERCENTAGE = 0.01;
    private static final double VARIETY_X_SCALE = 0.25;
    private static final double VARIETY_Z_SCALE = 0.25;
    private static final double VARIETY_EXPONENT = 0.3;
    private static final double VARIETY_FUZZ_PERCENTAGE = 0.0;
    private static final double FUZZINESS_X_SCALE = 0.25;
    private static final double FUZZINESS_Z_SCALE = 0.25;
    private static final double FUZZINESS_EXPONENT = 1.0;
    private final WorldType worldType;
    private final PerlinSimplexNoise temperatureNoise;
    private final PerlinSimplexNoise humidityNoise;
    private final PerlinSimplexNoise varietyNoise;
    private final PerlinSimplexNoise fuzzinessNoise;

    public BiomeProviderAether(long seed, WorldType worldType) {
        this.worldType = worldType;
        this.temperatureNoise = new PerlinSimplexNoise(new Random(seed * 9871L), 4);
        this.humidityNoise = new PerlinSimplexNoise(new Random(seed * 39811L), 4);
        this.varietyNoise = new PerlinSimplexNoise(new Random(seed), 4);
        this.fuzzinessNoise = new PerlinSimplexNoise(new Random(seed * 543321L), 2);
    }

    @Override
    public Biome[] getBiomes(Biome[] biomes, double[] temperatures, double[] humidities, double[] varieties, int x, int y, int z, int xSize, int ySize, int zSize) {
        if (biomes == null || biomes.length < xSize * ySize * zSize) {
            biomes = new Biome[xSize * ySize * zSize];
        }

        if (temperatures == null || temperatures.length < xSize * zSize) {
            temperatures = this.getTemperatures(temperatures, x, z, xSize, zSize);
        }

        if (humidities == null || humidities.length < xSize * zSize) {
            humidities = this.getHumidities(humidities, x, z, xSize, zSize);
        }

        if (varieties == null || varieties.length < xSize * zSize) {
            varieties = this.getVarieties(varieties, x, z, xSize, zSize);
        }

        for (int dx = 0; dx < xSize; ++dx) {
            for (int dz = 0; dz < zSize; ++dz) {
                double temperature = temperatures[dx * zSize + dz];
                double humidity = humidities[dx * zSize + dz];
                double variety = varieties[dx * zSize + dz];

                for (int dy = 0; dy < ySize; ++dy) {
                    double altitude = this.worldType.getYPercentage(y + dy << 3);
                    biomes[dy * xSize * zSize + dz * xSize + dx] = this.lookupBiome(temperature, humidity, variety, altitude);
                }
            }
        }

        return biomes;
    }

    @Override
    public double[] getTemperatures(double[] temperatures, int x, int z, int xSize, int zSize) {
        if (temperatures == null || temperatures.length < xSize * zSize) {
            temperatures = new double[xSize * zSize];
        }

        double[] tnResult = this.temperatureNoise.getValue(null, x, z, xSize, zSize, TEMPERATURE_X_SCALE, TEMPERATURE_Z_SCALE, TEMPERATURE_EXPONENT);
        double[] fnResult = this.fuzzinessNoise.getValue(null, x, z, xSize, zSize, FUZZINESS_X_SCALE, FUZZINESS_Z_SCALE, FUZZINESS_EXPONENT);

        for (int dx = 0; dx < xSize; ++dx) {
            for (int dz = 0; dz < zSize; ++dz) {
                double fuzziness = fnResult[dx * zSize + dz] * 1.1 + 0.5;
                double fuzzPctg = TEMPERATURE_FUZZ_PERCENTAGE;
                double valPctg = 1.0 - fuzzPctg;
                double temperature = (tnResult[dx * zSize + dz] * 0.15 + 0.7) * valPctg + fuzziness * fuzzPctg;
                if (temperature < 0.0) {
                    temperature = 0.0;
                }

                if (temperature > 1.0) {
                    temperature = 1.0;
                }

                temperatures[dx * zSize + dz] = temperature;
            }
        }

        return temperatures;
    }

    @Override
    public double[] getHumidities(double[] humidities, int x, int z, int xSize, int zSize) {
        if (humidities == null || humidities.length < xSize * zSize) {
            humidities = new double[xSize * zSize];
        }

        double[] hnResult = this.humidityNoise.getValue(null, x, z, xSize, zSize, HUMIDITY_X_SCALE, HUMIDITY_Z_SCALE, HUMIDITY_EXPONENT);
        double[] fnResult = this.fuzzinessNoise.getValue(null, x, z, xSize, zSize, FUZZINESS_X_SCALE, FUZZINESS_Z_SCALE, FUZZINESS_EXPONENT);

        for (int dx = 0; dx < xSize; ++dx) {
            for (int dz = 0; dz < zSize; ++dz) {
                double fuzziness = fnResult[dx * zSize + dz] * 1.1 + 0.5;
                double fuzzPctg = HUMIDITY_FUZZ_PERCENTAGE;
                double valPctg = 1.0 - fuzzPctg;
                double humidity = (hnResult[dx * zSize + dz] * 0.15 + 0.5) * valPctg + fuzziness * fuzzPctg;
                if (humidity < 0.0) {
                    humidity = 0.0;
                }

                if (humidity > 1.0) {
                    humidity = 1.0;
                }

                humidities[dx * zSize + dz] = humidity;
            }
        }

        return humidities;
    }

    @Override
    public double[] getVarieties(double[] varieties, int x, int z, int xSize, int zSize) {
        if (varieties == null || varieties.length < xSize * zSize) {
            varieties = new double[xSize * zSize];
        }

        double[] vnResult = this.varietyNoise.getValue(null, x, z, xSize, zSize, VARIETY_X_SCALE, VARIETY_Z_SCALE, VARIETY_EXPONENT);
        double[] fnResult = this.fuzzinessNoise.getValue(null, x, z, xSize, zSize, FUZZINESS_X_SCALE, FUZZINESS_Z_SCALE, FUZZINESS_EXPONENT);

        for (int dx = 0; dx < xSize; ++dx) {
            for (int dz = 0; dz < zSize; ++dz) {
                double fuzziness = fnResult[dx * zSize + dz] * 1.1 + 0.5;
                double fuzzPctg = VARIETY_FUZZ_PERCENTAGE;
                double valPctg = 1.0 - fuzzPctg;
                double variety = (vnResult[dx * zSize + dz] * 0.15 + 0.5) * valPctg + fuzziness * fuzzPctg;
                if (variety < 0.0) {
                    variety = 0.0;
                }

                if (variety > 1.0) {
                    variety = 1.0;
                }

                varieties[dx * zSize + dz] = variety;
            }
        }

        return varieties;
    }

    @SuppressWarnings({"java:S1119", "java:S6541"})
    @Override
    public double[] getBiomenesses(double[] biomenesses, int x, int y, int z, int xSize, int ySize, int zSize) {
        if (biomenesses == null || biomenesses.length < xSize * ySize * zSize) {
            biomenesses = new double[xSize * ySize * zSize];
        }

        double[] temperatures = this.getTemperatures(null, x, z, xSize, zSize);
        double[] humidities = this.getHumidities(null, x, z, xSize, zSize);
        double[] varieties = this.getVarieties(null, x, z, xSize, zSize);

        for (int dx = 0; dx < xSize; ++dx) {
            for (int dy = 0; dy < ySize; ++dy) {
                label111:
                for (int dz = 0; dz < zSize; ++dz) {
                    double temperature = MathHelper.clamp(temperatures[dx * zSize + dz], 0.0, 1.0);
                    double humidity = MathHelper.clamp(humidities[dx * zSize + dz], 0.0, 1.0);
                    double altitude = MathHelper.clamp(this.worldType.getYPercentage(y + dy << 3), 0.0, 1.0);
                    double variety = MathHelper.clamp(varieties[dx * zSize + dz], 0.0, 1.0);
                    Biome biome = this.lookupBiome(temperature, humidity, variety, altitude);
                    Set<BiomeRange> ranges = BIOME_RANGE_MAP.getRanges(biome);
                    humidity *= temperature;
                    double biomeness = 0.0;
                    Iterator<BiomeRange> var26 = ranges.iterator();

                    while (true) {
                        BiomeRange range;
                        do {
                            if (!var26.hasNext()) {
                                biomenesses[dy * xSize * zSize + dz * xSize + dx] = biomeness;
                                continue label111;
                            }

                            range = var26.next();
                        } while (!range.contains(temperature, humidity, variety, altitude));

                        double temperatureRange = range.getMaxTemperature() - range.getMinTemperature();
                        double humidityRange = range.getMaxHumidity() - range.getMinHumidity();
                        double altitudeRange = range.getMaxAltitude() - range.getMinAltitude();
                        double varietyRange = range.getMaxVariety() - range.getMinVariety();
                        double newTemperature = (temperature - range.getMinTemperature()) / temperatureRange;
                        double newHumidity = (humidity - range.getMinHumidity()) / humidityRange;
                        double newAltitude = (altitude - range.getMinAltitude()) / altitudeRange;
                        double newVariety = (variety - range.getMinVariety()) / varietyRange;
                        if ((range.getMinTemperature() > 0.0 || newTemperature > 0.5) && (range.getMaxTemperature() < 1.0 || newTemperature < 0.5)) {
                            newTemperature = -Math.abs(newTemperature * 2.0 - 1.0) + 1.0;
                        } else {
                            newTemperature = 1.0;
                        }

                        if ((range.getMinHumidity() > 0.0 || newHumidity > 0.5) && (range.getMaxHumidity() < 1.0 || newHumidity < 0.5)) {
                            newHumidity = -Math.abs(newHumidity * 2.0 - 1.0) + 1.0;
                        } else {
                            newHumidity = 1.0;
                        }

                        if ((range.getMinAltitude() > 0.0 || newAltitude > 0.5) && (range.getMaxAltitude() < 1.0 || newAltitude < 0.5)) {
                            newAltitude = -Math.abs(newAltitude * 2.0 - 1.0) + 1.0;
                        } else {
                            newAltitude = 1.0;
                        }

                        if ((range.getMinVariety() > 0.0 || newVariety > 0.5) && (range.getMaxVariety() < 1.0 || newVariety < 0.5)) {
                            newVariety = -Math.abs(newVariety * 2.0 - 1.0) + 1.0;
                        } else {
                            newVariety = 1.0;
                        }

                        double newBiomeness = newTemperature * newHumidity * newAltitude * newVariety;
                        if (newBiomeness > biomeness) {
                            biomeness = newBiomeness;
                        }
                    }
                }
            }
        }

        return biomenesses;
    }

    @Override
    public Biome lookupBiome(double temperature, double humidity, double variety, double altitude) {
        humidity *= temperature;
        return BIOME_RANGE_MAP.lookupBiome(temperature, humidity, variety, altitude);
    }

    public static void init() {
        BIOME_RANGE_MAP.clear();
        BIOME_RANGE_MAP.addRange(AetherBiomes.AETHER_PLAINS, new BiomeRange(0.00, 1.00, 0.00, 1.00, 0.00, 1.00, 0.00, 1.00));
        BIOME_RANGE_MAP.lock();
    }
}
