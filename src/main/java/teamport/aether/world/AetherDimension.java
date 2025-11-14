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


    private static Dimension aether;

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

        aether = new Dimension("aether", Dimension.OVERWORLD, 1.0f, AetherBlocks.PORTAL_AETHER, AetherWorldTypes.AETHER_DEFAULT);
        Dimension.registerDimension(AETHER_DIMENSION_ID, aether);

        List<Integer> aetherBlacklist = getDimensionBlacklist(aether);
        aetherBlacklist.add(Blocks.FIRE.id());
        aetherBlacklist.add(Blocks.BRAZIER_ACTIVE.id());

        aetherBlacklist.add(Blocks.FLUID_LAVA_FLOWING.id());
        aetherBlacklist.add(Blocks.FLUID_LAVA_STILL.id());
        aetherBlacklist.add(Blocks.TORCH_COAL.id());
        aetherBlacklist.add(Blocks.PUMPKIN_CARVED_ACTIVE.id());
        aetherBlacklist.add(Blocks.COBBLE_NETHERRACK_IGNEOUS.id());
        aetherBlacklist.add(Blocks.PUMICE_WET.id());
        aetherBlacklist.add(Blocks.PORTAL_NETHER.id());

        if (sunspiritDead) {

            aetherBlacklist.add(Blocks.NETHERRACK.id());
            aetherBlacklist.add(Blocks.NETHERRACK_CARVED.id());
            aetherBlacklist.add(Blocks.NETHERRACK_POLISHED.id());
            aetherBlacklist.add(Blocks.SLAB_NETHERRACK_POLISHED.id());

            aetherBlacklist.add(Blocks.COBBLE_NETHERRACK.id());
            aetherBlacklist.add(Blocks.COBBLE_NETHERRACK_IGNEOUS.id());
            aetherBlacklist.add(Blocks.COBBLE_NETHERRACK_MOSSY.id());

            aetherBlacklist.add(Blocks.BRICK_NETHERRACK.id());
            aetherBlacklist.add(Blocks.SLAB_BRICK_NETHERRACK.id());
            aetherBlacklist.add(Blocks.STAIRS_BRICK_NETHERRACK.id());

            aetherBlacklist.add(Blocks.SOULSAND.id());
            aetherBlacklist.add(Blocks.SOULSCHIST.id());
            aetherBlacklist.add(Blocks.ORE_NETHERCOAL_NETHERRACK.id());
            aetherBlacklist.add(Blocks.BLOCK_NETHER_COAL.id());
        }
    }

    public static void unlockDaylightCycle(World world) {
        if (!sunspiritDead) {
            AetherMod.LOGGER.info("Attempted to unlock daylight cycle.");

            sunspiritDead = true;
            sunspiritDeathTimestamp = world.getWorldTime();

            if (EnvironmentHelper.isServerEnvironment()) {
                NetworkHandler.sendToAllPlayers(
                    new SunspiritDeathNetworkMessage(sunspiritDead, sunspiritDeathTimestamp)
                );
            }
        }
    }

    private static final Map<IntPair, List<CompoundTag>> entitiesMovedToOverworld = new HashMap<>();

    public static synchronized void addEntityToFallen(Entity target) {
        if (AetherMod.LOGGER.isInfoEnabled()) AetherMod.LOGGER.info("Sending {} to overworld", Entity.getNameFromEntity(target, true));

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

                    float scale = Dimension.getCoordScale(AetherDimension.aether, Dimension.OVERWORLD);
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

    public static void loadDimensionData(CompoundTag dimensionData) {
        AetherMod.LOGGER.debug("Loading additional level data.");


        sunspiritDead = dimensionData.getBoolean(AetherMod.MOD_ID + ".sunspiritDeathTimestamp");
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

            entryCompound.putInt("x", chunkPos.getFirst());
            entryCompound.putInt("z", chunkPos.getSecond());

            entryCompound.put("entities", entities);
            entitiesToMoveMap.addTag(entryCompound);
        }

        DungeonMap.save(dimensionData);
        dimensionData.putBoolean(AetherMod.MOD_ID + ".sunspiritDeathTimestamp", AetherDimension.sunspiritDead);
        dimensionData.put(AetherMod.MOD_ID + ".overworldFallen", entitiesToMoveMap);
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
        return aether;
    }
}
