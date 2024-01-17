/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package net.minecraft.core.enums;

import bta.aether.world.AetherDimension;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;

public enum EnumFireflyColor {
    GREEN(0, "fireflyGreen", new Biome[]{Biomes.OVERWORLD_RAINFOREST, Biomes.OVERWORLD_SWAMPLAND, Biomes.OVERWORLD_FOREST, Biomes.OVERWORLD_SEASONAL_FOREST}),
    ORANGE(1, "fireflyOrange", new Biome[]{Biomes.OVERWORLD_DESERT, Biomes.OVERWORLD_OUTBACK, Biomes.OVERWORLD_OUTBACK_GRASSY}),
    BLUE(2, "fireflyBlue", new Biome[]{Biomes.OVERWORLD_TAIGA, Biomes.OVERWORLD_TUNDRA, Biomes.OVERWORLD_BOREAL_FOREST, Biomes.OVERWORLD_GLACIER, Biomes.PARADISE_PARADISE}),
    RED(3, "fireflyRed", new Biome[]{Biomes.NETHER_NETHER}),
    SILVER(4, "fireflySilver", new Biome[]{AetherDimension.biomeAether});

    private int id;
    private String particleName;
    private Biome[] spawnBiomes;

    public int getId() {
        return this.id;
    }

    public String getParticleName() {
        return this.particleName;
    }

    public Biome[] getSpawnBiomes() {
        return this.spawnBiomes;
    }

    private EnumFireflyColor(int id, String particleName, Biome[] spawnBiomes) {
        this.id = id;
        this.particleName = particleName;
        this.spawnBiomes = spawnBiomes;
    }
}

