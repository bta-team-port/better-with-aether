package teamport.aether.world;

import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import net.minecraft.core.world.weather.Weathers;
import teamport.aether.blocks.AetherBlocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AetherDimension {

    public static int AetherDimensionID = 3;
    private static final HashMap<Dimension, List<Integer>> dimensionPlacementBlacklist = new HashMap<>();

    public static List<Integer> getDimensionBlacklist(Dimension dimension){
        if (!dimensionPlacementBlacklist.containsKey(dimension)){
            dimensionPlacementBlacklist.put(dimension, new ArrayList<>());
        }
        return dimensionPlacementBlacklist.get(dimension);
    }

    public static Biome biomeAether;
    public static WorldType worldTypeAether;
    public static Dimension dimensionAether;

    public void initializeDimension() {
        biomeAether = Biomes.register("aether:aether.aether", new BiomeAether("aether.aether").setBlockedWeathers(Weathers.OVERWORLD_RAIN, Weathers.OVERWORLD_SNOW, Weathers.OVERWORLD_STORM, Weathers.OVERWORLD_FOG))
                .setTopBlock(AetherBlocks.GRASS_AETHER.id())
                .setFillerBlock(AetherBlocks.DIRT_AETHER.id());

        worldTypeAether = WorldTypes.register("aether:aether.default", new WorldTypeAether("worldType.aether.default") {
        });
//        dimensionAether = new Dimension("aether", Dimension.OVERWORLD, 3f, AetherBlocks.PORTAL_AETHER, new WorldTypeAether("worldtype.aether") {
//        }).defaultWorldType(new WorworldTypeAether);

        // This is here so that the dimension is created and added to the dimension list before the Server even launches, it'll crash otherwise
        Dimension.registerDimension(AetherDimensionID, dimensionAether);
    }

}
