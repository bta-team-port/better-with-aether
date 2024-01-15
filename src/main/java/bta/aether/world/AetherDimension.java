package bta.aether.world;

import bta.aether.block.AetherBlocks;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.chunk.ChunkCoordinates;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import net.minecraft.core.world.weather.Weather;
import turniplabs.halplibe.HalpLibe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AetherDimension implements PreLaunchEntrypoint {

    // coordinates and if the boss has been defeated.
    public static Map<Integer, ChunkCoordinates> dugeonMap = new HashMap<>();
    public static int AetherDimensionID = 3;
    private static final HashMap<Dimension, List<Integer>> dimensionPlacementBlacklist = new HashMap<>();
    public static List<Integer> getDimensionBlacklist(Dimension dimension){
        if (!dimensionPlacementBlacklist.containsKey(dimension)){
            dimensionPlacementBlacklist.put(dimension, new ArrayList<>());
        }
        return dimensionPlacementBlacklist.get(dimension);
    }

    public static int registerDungeonToMap(int x, int y, int z){
        int id = dugeonMap.size();
        while (dugeonMap.get(id) != null) {
            id++;
        }

        dugeonMap.put(id, new ChunkCoordinates(x, y, z));
        return id;
    }

    // Biomes
    public static final Biome biomeAether = Biomes.register("aether:aether.aether", new BiomeAether().setBlockedWeathers(Weather.overworldRain, Weather.overworldSnow, Weather.overworldStorm, Weather.overworldFog))
            .setTopBlock(AetherBlocks.grassAether.id)
            .setFillerBlock(AetherBlocks.dirtAether.id);
    // World types
    public static final WorldType worldTypeAether = WorldTypes.register("aether:aether.default", new WorldTypeAetherDefault("worldType.aether.default"));
    // Dimensions
    public static final Dimension dimensionAether = new Dimension("aether", Dimension.overworld, 3f, AetherBlocks.portalAether.id).setDefaultWorldType(worldTypeAether);
    @Override
    public void onPreLaunch() {
        // This is here so that the dimension is created and added to the dimension list before the Server even launches, it'll crash otherwise
        Dimension.registerDimension(AetherDimensionID, dimensionAether);
    }
}
