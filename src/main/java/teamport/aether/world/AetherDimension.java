package teamport.aether.world;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import net.minecraft.core.world.weather.Weathers;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.helper.BlockCoordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static net.minecraft.core.world.biome.Biomes.register;

public class AetherDimension {
    public static final int bossDetectionRange = 100;
    public static final int bossDetectionRangeSQR = 10000;
    public static final int dungeonRadius = 300;
    public static final int dungeonRadiusSQR = dungeonRadius * dungeonRadius;

    public static final HashMap<Integer, BlockCoordinate> dungeonMap = new HashMap<>();

    public static int registerDungeonToMap(int x, int y, int z){
        int id = dungeonMap.size();
        while (dungeonMap.get(id) != null) {
            id++;
        }

        dungeonMap.put(id, new BlockCoordinate(x, y, z));
        return id;
    }

    public static int AetherDimensionID = 3;
    public static final HashMap<Integer, List<Integer>> dimensionPlacementBlacklist = new HashMap<>();

    public static List<Integer> getDimensionBlacklist(Dimension dimension) {
        return getDimensionBlacklist(dimension.id);
    }

        public static List<Integer> getDimensionBlacklist(Integer dimensionID){
        if (!dimensionPlacementBlacklist.containsKey(dimensionID)){
            dimensionPlacementBlacklist.put(dimensionID, new ArrayList<>());
        }
        return dimensionPlacementBlacklist.get(dimensionID);
    }

    public static Biome AETHER_PLAINS;
    public static WorldType AETHER_DEFAULT;
    public static Dimension AETHER;
    public static boolean hasInit = false;

    public static void init() {
        if(!hasInit){
            hasInit = true;
            initializeDimension();
        }

    }

    public static void initializeDimension() {
        AETHER_PLAINS = register("aether:plains", (new BiomeAether("aether.plains"))
                .setBlockedWeathers(Weathers.OVERWORLD_RAIN, Weathers.OVERWORLD_SNOW, Weathers.OVERWORLD_STORM, Weathers.OVERWORLD_FOG))
                .setTopBlock(AetherBlocks.GRASS_AETHER.id())
                .setFillerBlock(AetherBlocks.DIRT_AETHER.id());

        AETHER_DEFAULT = WorldTypes.register("aether:aether.default", new WorldTypeAether(WorldTypeAether.defaultProperties("worldtype.aether.default")));

        AETHER = new Dimension("aether", Dimension.OVERWORLD, 1.0f, AetherBlocks.PORTAL_AETHER, AETHER_DEFAULT);
        Dimension.registerDimension(AetherDimensionID, AETHER);

        List<Integer> AETHER_BLACKLIST = AetherDimension.getDimensionBlacklist(AetherDimension.AETHER);
        AETHER_BLACKLIST.add(Blocks.FIRE.id());
        AETHER_BLACKLIST.add(Blocks.FLUID_LAVA_FLOWING.id());
        AETHER_BLACKLIST.add(Blocks.FLUID_LAVA_STILL.id());
        AETHER_BLACKLIST.add(Blocks.TORCH_COAL.id());
        AETHER_BLACKLIST.add(Blocks.PUMPKIN_CARVED_ACTIVE.id());
        AETHER_BLACKLIST.add(Blocks.NETHERRACK.id());
        AETHER_BLACKLIST.add(Blocks.NETHERRACK_CARVED.id());
        AETHER_BLACKLIST.add(Blocks.NETHERRACK_POLISHED.id());
        AETHER_BLACKLIST.add(Blocks.SLAB_NETHERRACK_POLISHED.id());

        AETHER_BLACKLIST.add(Blocks.COBBLE_NETHERRACK.id());
        AETHER_BLACKLIST.add(Blocks.COBBLE_NETHERRACK_IGNEOUS.id());
        AETHER_BLACKLIST.add(Blocks.COBBLE_NETHERRACK_MOSSY.id());

        AETHER_BLACKLIST.add(Blocks.BRICK_NETHERRACK.id());
        AETHER_BLACKLIST.add(Blocks.SLAB_BRICK_NETHERRACK.id());
        AETHER_BLACKLIST.add(Blocks.STAIRS_BRICK_NETHERRACK.id());

        AETHER_BLACKLIST.add(Blocks.SOULSAND.id());
        AETHER_BLACKLIST.add(Blocks.SOULSCHIST.id());
        AETHER_BLACKLIST.add(Blocks.PORTAL_NETHER.id());
        AETHER_BLACKLIST.add(Blocks.ORE_NETHERCOAL_NETHERRACK.id());
        AETHER_BLACKLIST.add(Blocks.BLOCK_NETHER_COAL.id());
    }
}
