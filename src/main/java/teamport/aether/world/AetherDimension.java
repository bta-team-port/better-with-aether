package teamport.aether.world;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import teamport.aether.AetherConfig;
import teamport.aether.AetherMod;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.AetherMobFallingToOverworld;
import teamport.aether.helper.unboxed.IntPair;
import teamport.aether.net.message.SunspiritDeathNetworkMessage;
import teamport.aether.world.biome.AetherBiomes;
import teamport.aether.world.chunk.BiomeProviderAether;
import teamport.aether.world.feature.util.map.DungeonMap;
import teamport.aether.world.type.AetherWorldTypes;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

import java.util.*;

public class AetherDimension {
    private static boolean sunspiritDead = false;
    private static long sunspiritDeathTimestamp = 0;

    public static final int OVERWORLD_RETURN_HEIGHT = 270;
    public static final int DUNGEON_GENERATION_RADIUS = 16;
    public static final int BOSS_DETECTION_RADIUS = 80;
    public static final int BOSS_DETECTION_RANGE_SQR = 6400;

    private static final int AETHER_DIMENSION_ID = AetherConfig.DIMENSION;
    private static final HashMap<Integer, List<Integer>> DIMENSION_PLACEMENT_BLACKLIST = new HashMap<>();

    public static List<Integer> getDimensionBlacklist(Dimension dimension) {
        return getDimensionBlacklist(dimension.id);
    }

    public static List<Integer> getDimensionBlacklist(Integer dimensionID) {
        return DIMENSION_PLACEMENT_BLACKLIST.computeIfAbsent(dimensionID, k -> new ArrayList<>());
    }

    public static void deleteFromBlackLists(Integer dimensionID, Integer... unbannedBlockIds) {
        if (unbannedBlockIds == null) return;
        Set<Integer> blocks = new HashSet<>(Arrays.asList(unbannedBlockIds));
        List<Integer> aetherBlacklist = DIMENSION_PLACEMENT_BLACKLIST.get(dimensionID);
        List<Integer> newList = new ArrayList<>();
        for (int id : aetherBlacklist) {
            if (blocks.contains(id)) continue;
            newList.add(id);
        }
        DIMENSION_PLACEMENT_BLACKLIST.put(dimensionID, newList);
    }


    private static Dimension AETHER;

    private static boolean hasInit = false;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            initializeDimension();
        }

    }

    public static void initializeDimension() {
        AetherBiomes.init();
        AetherWorldTypes.init();
        BiomeProviderAether.init();

        AETHER = new Dimension("aether", Dimension.OVERWORLD, 1.0f, AetherBlocks.PORTAL_AETHER, AetherWorldTypes.AETHER_DEFAULT);
        Dimension.registerDimension(AETHER_DIMENSION_ID, AETHER);

        List<Integer> aetherBlacklist = getDimensionBlacklist(AETHER);
        aetherBlacklist.add(Blocks.PORTAL_NETHER.id());
        aetherBlacklist.add(Blocks.FIRE.id());
        aetherBlacklist.add(Blocks.TORCH_COAL.id());

        if (!sunspiritDead) {
            ///  I think those get converted
            aetherBlacklist.add(Blocks.COBBLE_NETHERRACK_IGNEOUS.id());
            aetherBlacklist.add(Blocks.PUMICE_WET.id());
            aetherBlacklist.add(Blocks.BRAZIER_ACTIVE.id());
            aetherBlacklist.add(Blocks.COBBLE_NETHERRACK_IGNEOUS.id());
            aetherBlacklist.add(Blocks.FLUID_LAVA_FLOWING.id());
            aetherBlacklist.add(Blocks.FLUID_LAVA_STILL.id());

            /// blocks that should not be banned or unlocked by sunspirit death
            aetherBlacklist.add(Blocks.SOULSAND.id());
            aetherBlacklist.add(Blocks.SOULSCHIST.id());
            aetherBlacklist.add(Blocks.PUMPKIN_CARVED_ACTIVE.id());
            aetherBlacklist.add(Blocks.NETHERRACK.id());
            aetherBlacklist.add(Blocks.COBBLE_NETHERRACK.id());
            aetherBlacklist.add(Blocks.STAIRS_COBBLE_NETHERRACK.id());
            aetherBlacklist.add(Blocks.SLAB_COBBLE_NETHERRACK.id());
            aetherBlacklist.add(Blocks.COBBLE_NETHERRACK_MOSSY.id());
            aetherBlacklist.add(Blocks.NETHERRACK_CARVED.id());
            aetherBlacklist.add(Blocks.NETHERRACK_POLISHED.id());
            aetherBlacklist.add(Blocks.SLAB_NETHERRACK_POLISHED.id());
            aetherBlacklist.add(Blocks.BRICK_NETHERRACK.id());
            aetherBlacklist.add(Blocks.SLAB_BRICK_NETHERRACK.id());
            aetherBlacklist.add(Blocks.STAIRS_BRICK_NETHERRACK.id());

            aetherBlacklist.add(Blocks.ORE_NETHERCOAL_NETHERRACK.id());
            aetherBlacklist.add(Blocks.BLOCK_NETHER_COAL.id());
        }
    }

    public static void unlockDaylightCycle(World world) {
        if (!sunspiritDead) {
            AetherMod.LOGGER.info("Attempted to unlock daylight cycle.");
            sunspiritDead = true;
            sunspiritDeathTimestamp = world.getWorldTime();

            deleteFromBlackLists(AETHER_DIMENSION_ID,
                Blocks.SOULSAND.id(),
                Blocks.SOULSCHIST.id(),
                Blocks.PUMPKIN_CARVED_ACTIVE.id(),
                Blocks.NETHERRACK.id(),
                Blocks.COBBLE_NETHERRACK.id(),
                Blocks.STAIRS_COBBLE_NETHERRACK.id(),
                Blocks.SLAB_COBBLE_NETHERRACK.id(),
                Blocks.COBBLE_NETHERRACK_MOSSY.id(),
                Blocks.NETHERRACK_CARVED.id(),
                Blocks.NETHERRACK_POLISHED.id(),
                Blocks.SLAB_NETHERRACK_POLISHED.id(),
                Blocks.BRICK_NETHERRACK.id(),
                Blocks.SLAB_BRICK_NETHERRACK.id(),
                Blocks.STAIRS_BRICK_NETHERRACK.id(),
                Blocks.ORE_NETHERCOAL_NETHERRACK.id(),
                Blocks.BLOCK_NETHER_COAL.id()
            );

            if (EnvironmentHelper.isServerEnvironment()) {
                NetworkHandler.sendToAllPlayers(
                    new SunspiritDeathNetworkMessage(sunspiritDead, sunspiritDeathTimestamp)
                );
            }
        }
    }

    private static final Map<IntPair, List<CompoundTag>> entitiesMovedToOverworld = new HashMap<>();

    public static synchronized void addEntityToFallen(Entity target) {
        if (AetherMod.LOGGER.isInfoEnabled())
            AetherMod.LOGGER.info("Sending {} to overworld", Entity.getNameFromEntity(target, true));

        IntPair chunk = new IntPair(
            ((int) target.x) / 16,
            ((int) target.z) / 16
        );
        List<CompoundTag> chunkList = entitiesMovedToOverworld.computeIfAbsent(chunk, i -> new ArrayList<>());

        CompoundTag data = new CompoundTag();
        target.save(data);
        target.remove();

        chunkList.add(data);
    }

    public static synchronized void loadEntitiesNearPlayer(Player player, World world) {
        List<IntPair> toRemove = new ArrayList<>();
        for (IntPair pos : entitiesMovedToOverworld.keySet()) {
            if (player.distanceTo(pos.getFirst() * 16.0, player.y, pos.getSecond() * 16.0) < 100) {
                List<CompoundTag> entities = entitiesMovedToOverworld.computeIfAbsent(pos, intPair -> new ArrayList<>());

                while (!entities.isEmpty()) {
                    CompoundTag data = entities.remove(0);

                    Entity copy = EntityDispatcher.createEntityFromNBT(data, world);
                    copy.load(data);

                    float scale = Dimension.getCoordScale(AetherDimension.getAether(), Dimension.OVERWORLD);
                    copy.moveTo(copy.x * scale, OVERWORLD_RETURN_HEIGHT, copy.z * scale, copy.yRot, copy.xRot);

                    world.entityJoinedWorld(copy);

                    if (copy instanceof AetherMobFallingToOverworld) {
                        ((AetherMobFallingToOverworld) copy).onEnteredOverworld();
                    }
                }

                toRemove.add(pos);
            }
        }

        toRemove.forEach(entitiesMovedToOverworld::remove);
    }

    public static void setDimensionDataDefaults() {
        sunspiritDeathTimestamp = 0;
        sunspiritDead = false;
    }

    private static final int SCHEMA_VERSION = 1;

    protected static void loadFallenEntities(ListTag entitiesMoved) {
        entitiesMovedToOverworld.clear();

        entitiesMoved.forEach(tag -> {
            ListTag entities = ((CompoundTag) tag).getList("entities");
            IntPair chunk = new IntPair(
                ((CompoundTag) tag).getInteger("x"),
                ((CompoundTag) tag).getInteger("z")
            );

            List<CompoundTag> entitiesList = new ArrayList<>();
            entities.forEach(e -> entitiesList.add((CompoundTag) e));
            entitiesMovedToOverworld.put(chunk, entitiesList);
        });
    }

    public static void saveWorldData(CompoundTag aetherWorldData) {
        ListTag entitiesToMoveMap = new ListTag();
        for (Map.Entry<IntPair, List<CompoundTag>> entry : entitiesMovedToOverworld.entrySet()) {
            CompoundTag entryCompound = new CompoundTag();

            ListTag entities = new ListTag();
            for (CompoundTag entity : entry.getValue()) {
                entities.addTag(entity);
            }

            IntPair chunkPos = entry.getKey();

            entryCompound.putInt("x", chunkPos.getFirst());
            entryCompound.putInt("z", chunkPos.getSecond());

            entryCompound.put("entities", entities);
            entitiesToMoveMap.addTag(entryCompound);
        }

        aetherWorldData.putInt(AetherMod.MOD_ID + ".__SCHEMA_VERSION__", SCHEMA_VERSION);
        aetherWorldData.put(AetherMod.MOD_ID + ".overworldFallen", entitiesToMoveMap);
        DungeonMap.save(aetherWorldData);
    }

    public static void loadWorldData(CompoundTag aetherWorldData) {
        AetherMod.LOGGER.debug("Loading additional level data.");

        loadFallenEntities(aetherWorldData.getList(AetherMod.MOD_ID + ".overworldFallen"));
        DungeonMap.load(aetherWorldData);
    }

    public static void loadDimensionData(CompoundTag dimensionData) {
        AetherMod.LOGGER.debug("Loading additional dimension data.");

        if (!dimensionData.containsKey(AetherMod.MOD_ID + ".__SCHEMA_VERSION__")) {
            loadFallenEntities(dimensionData.getList(AetherMod.MOD_ID + ".overworldFallen"));
        }
        sunspiritDead = dimensionData.getBoolean(AetherMod.MOD_ID + ".sunspiritDeathTimestamp");
    }

    public static void saveDimensionData(CompoundTag dimensionData) {
        AetherMod.LOGGER.debug("Saving additional dimension data.");

        dimensionData.putInt("__SCHEMA_VERSION__", SCHEMA_VERSION);
        dimensionData.putBoolean(AetherMod.MOD_ID + ".sunspiritDeathTimestamp", AetherDimension.sunspiritDead);
    }

    public static boolean isSunspiritDead() {
        return sunspiritDead;
    }

    public static void setSunspiritDead(boolean sunspiritDead) {
        AetherDimension.sunspiritDead = sunspiritDead;
    }

    public static long getSunspiritDeathTimestamp() {
        return sunspiritDeathTimestamp;
    }

    public static void setSunspiritDeathTimestamp(long sunspiritDeathTimestamp) {
        AetherDimension.sunspiritDeathTimestamp = sunspiritDeathTimestamp;
    }

    public static Dimension getAether() {
        return AETHER;
    }
}
