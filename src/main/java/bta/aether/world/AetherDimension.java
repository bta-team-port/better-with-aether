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

import java.util.HashMap;
import java.util.Map;

public class AetherDimension implements PreLaunchEntrypoint {

    // coordinates and if the boss has been defeated.
    public static Map<Integer, ChunkCoordinates> dugeonMap = new HashMap<>();
    public static int AetherDimensionID = 3;

    public static int registerDungeonToMap(int x, int y, int z){
        int id = dugeonMap.size();
        while (dugeonMap.get(id) != null) {
            id++;
        }

        dugeonMap.put(id, new ChunkCoordinates(x, y, z));
        return id;
    }

    // Biomes
    public static final Biome biomeAether = Biomes.register("aether:aether.aether", new BiomeAether().setBlockedWeathers(Weather.overworldRain, Weather.overworldSnow, Weather.overworldStorm, Weather.overworldFog));
    // World types
    public static final WorldType worldTypeAether = WorldTypes.register("aether:aether.default", new WorldTypeAetherDefault("worldType.aether.default"));
    // Dimensions
    public static final Dimension dimensionAether = new Dimension("aether", Dimension.overworld, 3f, AetherBlocks.portalAether.id).setDefaultWorldType(worldTypeAether);
    @Override
    public void onPreLaunch() {
        // This is here so that the dimension is created and added to the dimension list before the Server even launches, it'll crash otherwise
        biomeAether.topBlock = (short) AetherBlocks.grassAether.id;
        biomeAether.fillerBlock = (short) AetherBlocks.dirtAether.id;
        Dimension.registerDimension(AetherDimensionID, dimensionAether);
    }
}
