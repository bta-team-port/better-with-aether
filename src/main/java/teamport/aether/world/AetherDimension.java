package teamport.aether.world;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import net.minecraft.core.world.weather.Weathers;
import teamport.aether.AetherConfig;
import teamport.aether.AetherMod;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.net.message.SunspiritDeathNetworkMessage;
import teamport.aether.world.generate.feature.dungeon.map.DungeonMap;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static net.minecraft.core.world.biome.Biomes.register;

public class AetherDimension {
    public static DungeonMap dungeonMap = new DungeonMap();

    public static boolean sunspiritIsDead = false;

    public static long sunspiritDeathTimestamp = 0;

    public static final int OVERWORLD_RETURN_HEIGHT = 270;
    public static final int bossDetectionRadius = 80;
    public static final int bossDetectionRangeSQR = 6400;
    public static final int dungeonRadius = 300;
    public static final int dungeonRadiusSQR = dungeonRadius * dungeonRadius;

    public static int AetherDimensionID = AetherConfig.DIMENSION;
    public static final HashMap<Integer, List<Integer>> dimensionPlacementBlacklist = new HashMap<>();

    public static List<Integer> getDimensionBlacklist(Dimension dimension) {
        return getDimensionBlacklist(dimension.id);
    }

    public static List<Integer> getDimensionBlacklist(Integer dimensionID) {
        if (!dimensionPlacementBlacklist.containsKey(dimensionID)) {
            dimensionPlacementBlacklist.put(dimensionID, new ArrayList<>());
        }
        return dimensionPlacementBlacklist.get(dimensionID);
    }

    public static Biome AETHER_PLAINS;

    public static WorldType AETHER_DEFAULT;
    public static WorldType AETHER_EXTENDED; // For in the future if we want to add a OG aether terrain vs extended
    public static WorldType AETHER_SKYBLOCK;
    public static WorldType AETHER_RETRO;

    public static Dimension AETHER;

    public static boolean hasInit = false;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            initializeDimension();
        }

    }

    public static void initializeDimension() {
        AETHER_PLAINS = register("aether:plains", (new BiomeAether("aether.plains"))
                .setBlockedWeathers(Weathers.OVERWORLD_RAIN, Weathers.OVERWORLD_SNOW, Weathers.OVERWORLD_STORM))
                .setTopBlock(AetherBlocks.GRASS_AETHER.id())
                .setFillerBlock(AetherBlocks.DIRT_AETHER.id());

        AETHER_EXTENDED = WorldTypes.register("aether:aether.extended", new WorldTypeAether(WorldTypeAether.defaultProperties("worldtype.aether.extended").portalBounds(128, 192)));
        AETHER_DEFAULT = WorldTypes.register("aether:aether.default", new WorldTypeAether(WorldTypeAether.defaultProperties("worldtype.aether.default").bounds(0, 127, 0).portalBounds(128, 192)));

        AETHER_SKYBLOCK = WorldTypes.register("aether:aether.skyblock", new WorldTypeAetherSkyblock(WorldTypeAether.defaultProperties("worldtype.aether.skyblock")));

        AETHER = new Dimension("aether", Dimension.OVERWORLD, 1.0f, AetherBlocks.PORTAL_AETHER, AETHER_DEFAULT);
        Dimension.registerDimension(AetherDimensionID, AETHER);

        List<Integer> AETHER_BLACKLIST = getDimensionBlacklist(AETHER);
        AETHER_BLACKLIST.add(Blocks.FIRE.id());
        AETHER_BLACKLIST.add(Blocks.BRAZIER_ACTIVE.id());

        AETHER_BLACKLIST.add(Blocks.FLUID_LAVA_FLOWING.id());
        AETHER_BLACKLIST.add(Blocks.FLUID_LAVA_STILL.id());
        AETHER_BLACKLIST.add(Blocks.TORCH_COAL.id());
        AETHER_BLACKLIST.add(Blocks.PUMPKIN_CARVED_ACTIVE.id());
        AETHER_BLACKLIST.add(Blocks.PORTAL_NETHER.id());

        if (sunspiritIsDead) {

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
            AETHER_BLACKLIST.add(Blocks.ORE_NETHERCOAL_NETHERRACK.id());
            AETHER_BLACKLIST.add(Blocks.BLOCK_NETHER_COAL.id());
        }
    }

    public static void unlockDaylightCycle(World world) {
        if (!sunspiritIsDead) {
            AetherMod.LOGGER.info("Attempted to unlock daylight cycle.");

            sunspiritIsDead = true;
            sunspiritDeathTimestamp = world.getWorldTime();

            if (EnvironmentHelper.isServerEnvironment()) {
                NetworkHandler.sendToAllPlayers(
                        new SunspiritDeathNetworkMessage(sunspiritIsDead, sunspiritDeathTimestamp)
                );
            }
        }
    }

    public static void setDimensionDataDefaults() {
        sunspiritDeathTimestamp = 0;
        sunspiritIsDead = false;
        dungeonMap = new DungeonMap();
    }

    public static void loadDimensionData(CompoundTag dimensionData) {
        AetherMod.LOGGER.info("Loading additional level data.");

        sunspiritIsDead = dimensionData.getBoolean(AetherMod.MOD_ID + ".sunspiritDeathTimestamp");
        dungeonMap.loadFromNBT(dimensionData.getCompound(AetherMod.MOD_ID + ".dungeon"));
    }

    public static void saveDimensionData(CompoundTag dimensionData) {
        AetherMod.LOGGER.info("Saving additional level data.");

        dimensionData.putBoolean(AetherMod.MOD_ID + ".sunspiritDeathTimestamp", AetherDimension.sunspiritIsDead);
        dimensionData.putCompound(AetherMod.MOD_ID + ".dungeon", AetherDimension.dungeonMap.writeToNBT(new CompoundTag()));
    }

}
