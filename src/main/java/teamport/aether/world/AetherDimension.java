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
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.AetherMobFallingToOverworld;
import teamport.aether.helper.unboxed.IntPair;
import teamport.aether.net.message.SunspiritDeathNetworkMessage;
import teamport.aether.world.biome.AetherBiomes;
import teamport.aether.world.chunk.BiomeProviderAether;
import teamport.aether.world.feature.util.map.DungeonMap;
import teamport.aether.world.type.AetherWorldTypes;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AetherDimension {
    public static boolean sunspiritIsDead = false;
    public static long sunspiritDeathTimestamp = 0;

    public static final int OVERWORLD_RETURN_HEIGHT = 270;
    public final static int DUNGEON_GENERATION_RADIUS = 16;
    public static final int BOSS_DETECTION_RADIUS = 80;
    public static final int BOSS_DETECTION_RANGE_SQR = 6400;

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

    public static Dimension AETHER;

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
        Dimension.registerDimension(AetherDimensionID, AETHER);

        List<Integer> AETHER_BLACKLIST = getDimensionBlacklist(AETHER);
        AETHER_BLACKLIST.add(Blocks.FIRE.id());
        AETHER_BLACKLIST.add(Blocks.BRAZIER_ACTIVE.id());

        AETHER_BLACKLIST.add(Blocks.FLUID_LAVA_FLOWING.id());
        AETHER_BLACKLIST.add(Blocks.FLUID_LAVA_STILL.id());
        AETHER_BLACKLIST.add(Blocks.TORCH_COAL.id());
        AETHER_BLACKLIST.add(Blocks.PUMPKIN_CARVED_ACTIVE.id());
        AETHER_BLACKLIST.add(Blocks.COBBLE_NETHERRACK_IGNEOUS.id());
        AETHER_BLACKLIST.add(Blocks.PUMICE_WET.id());
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

    private static final Map<IntPair, List<CompoundTag>> entitiesMovedToOverworld = new HashMap<>();

    public static synchronized void addEntityToFallen(Entity target) {
        AetherMod.LOGGER.info("Sending {} to overworld", Entity.getNameFromEntity(target, true));

        IntPair chunk = new IntPair(
                ((int) target.x) / 16,
                ((int) target.z) / 16
        );
        List<CompoundTag> chunkList = entitiesMovedToOverworld.computeIfAbsent(chunk, (i) -> new ArrayList<>());

        CompoundTag data = new CompoundTag();
        target.save(data);
        target.remove();

        chunkList.add(data);
    }

    public static synchronized void loadEntitiesNearPlayer(Player player, World world) {
        List<IntPair> toRemove = new ArrayList<>();
        for (IntPair pos : entitiesMovedToOverworld.keySet()) {
            if (player.distanceTo(pos.first * 16, player.y, pos.second * 16) < 100) {
                List<CompoundTag> entities = entitiesMovedToOverworld.computeIfAbsent(pos, intPair -> new ArrayList<>());

                while (!entities.isEmpty()) {
                    CompoundTag data = entities.remove(0);

                    Entity copy = EntityDispatcher.createEntityFromNBT(data, world);
                    copy.load(data);

                    float scale = Dimension.getCoordScale(AetherDimension.AETHER, Dimension.OVERWORLD);
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
        sunspiritIsDead = false;
    }

    public static void loadDimensionData(CompoundTag dimensionData) {
        AetherMod.LOGGER.debug("Loading additional level data.");


        sunspiritIsDead = dimensionData.getBoolean(AetherMod.MOD_ID + ".sunspiritDeathTimestamp");
        DungeonMap.load(dimensionData);

        entitiesMovedToOverworld.clear();
        ListTag entitiesMoved = dimensionData.getList(AetherMod.MOD_ID + ".overworldFallen");
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

    public static void saveDimensionData(CompoundTag dimensionData) {
        AetherMod.LOGGER.debug("Saving additional level data.");

        ListTag entitiesToMoveMap = new ListTag();
        for (Map.Entry<IntPair, List<CompoundTag>> entry : entitiesMovedToOverworld.entrySet()) {
            CompoundTag entryCompound = new CompoundTag();

            ListTag entities = new ListTag();
            for (CompoundTag entity : entry.getValue()) {
                entities.addTag(entity);
            }

            IntPair chunkPos = entry.getKey();

            entryCompound.putInt("x", chunkPos.first);
            entryCompound.putInt("z", chunkPos.second);

            entryCompound.put("entities", entities);
            entitiesToMoveMap.addTag(entryCompound);
        }

        DungeonMap.save(dimensionData);
        dimensionData.putBoolean(AetherMod.MOD_ID + ".sunspiritDeathTimestamp", AetherDimension.sunspiritIsDead);
        dimensionData.put(AetherMod.MOD_ID + ".overworldFallen", entitiesToMoveMap);
    }

}
