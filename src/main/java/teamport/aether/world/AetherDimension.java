package teamport.aether.world;

import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import net.minecraft.core.world.weather.Weathers;
import teamport.aether.blocks.AetherBlocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static net.minecraft.core.world.biome.Biomes.register;

public class AetherDimension {

    public static int AetherDimensionID = 3;
    public static final HashMap<Dimension, List<Integer>> dimensionPlacementBlacklist = new HashMap<>();

    public static List<Integer> getDimensionBlacklist(Dimension dimension){
        if (!dimensionPlacementBlacklist.containsKey(dimension)){
            dimensionPlacementBlacklist.put(dimension, new ArrayList<>());
        }
        return dimensionPlacementBlacklist.get(dimension);
    }

    public static Biome AETHER_PLAINS;
    public static WorldType worldTypeAether;
    public static Dimension dimensionAether;
    public static boolean hasInit = false;


    public static void init() {
        if(!hasInit){
            hasInit = true;
            initializeDimension();
        }

    }

    public static void initializeDimension() {
        AETHER_PLAINS = register("aether:plains", (new BiomeAether("aether.plains")).setBlockedWeathers(Weathers.OVERWORLD_RAIN, Weathers.OVERWORLD_SNOW, Weathers.OVERWORLD_STORM, Weathers.OVERWORLD_FOG))
                .setTopBlock(AetherBlocks.GRASS_AETHER.id())
                .setFillerBlock(AetherBlocks.DIRT_AETHER.id());

        worldTypeAether = WorldTypes.register("aether:aether.default", new WorldTypeAether("worldType.aether.default") {
        });
        dimensionAether = new Dimension("aether", Dimension.OVERWORLD, 1f, AetherBlocks.PORTAL_AETHER, worldTypeAether);
        Dimension.registerDimension(AetherDimensionID, dimensionAether);
    }

}
