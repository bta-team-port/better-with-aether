package teamport.aether.world;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import it.unimi.dsi.fastutil.ints.IntIntMutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.core.world.type.WorldTypeGroups;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.AetherConfig;
import teamport.aether.AetherMod;
import teamport.aether.block.AetherBlocks;
import teamport.aether.compat.AetherPlugin;
import teamport.aether.entity.AetherMobFallingToOverworld;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;
import teamport.aether.net.message.SunspiritDeathNetworkMessage;
import teamport.aether.world.biome.AetherBiomes;
import teamport.aether.world.chunk.BiomeProviderAether;
import teamport.aether.world.feature.util.map.DungeonMap;
import teamport.aether.world.type.AetherWorldTypes;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

import java.util.*;

public class AetherDimension {

    private static final int SCHEMA_VERSION = 3;

    public static final int OVERWORLD_RETURN_HEIGHT = World.HEIGHT_BLOCKS + 64;
    public static final int DUNGEON_GENERATION_RADIUS = 16;
    public static final int BOSS_DETECTION_RADIUS = 80;
    public static final int BOSS_DETECTION_RANGE_SQR = 6400;

    private static final int AETHER_DIMENSION_ID = AetherConfig.DIMENSION;
    private static final HashMap<Integer, List<Integer>> DIMENSION_PLACEMENT_BLACKLIST = new HashMap<>();

    private static final HashMap<UUID, Boolean> HAS_RECEIVED_PARACHUTE_MAP = new HashMap<>();
    private static final HashMap<UUID, CompoundTag> HAS_BUNNY_MAP = new HashMap<>();


    public static List<Integer> getDimensionBlacklist(@NonNull Dimension dimension) {
        return getDimensionBlacklist(dimension.id);
    }

    public static List<Integer> getDimensionBlacklist(Integer dimensionID) {
        return DIMENSION_PLACEMENT_BLACKLIST.computeIfAbsent(dimensionID, k -> new ArrayList<>());
    }

    private AetherDimension() {
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

        WorldTypeGroups.GROUPS.size();

        AETHER = new Dimension("aether", Dimension.OVERWORLD, 1.0f, AetherBlocks.PORTAL_AETHER, AetherWorldTypes.AETHER_DEFAULT);
        Dimension.registerDimension(AETHER_DIMENSION_ID, AETHER);
        AetherWorldTypes.addToWorldTypeGroups(AETHER);

        initDimensionBlackList();
    }

    public static void initDimensionBlackList() {
        DIMENSION_PLACEMENT_BLACKLIST.clear();

        List<Integer> aetherBlacklist = getDimensionBlacklist(AETHER);

        aetherBlacklist.add(Blocks.PORTAL_NETHER.id());
        aetherBlacklist.add(Blocks.FIRE.id());
        aetherBlacklist.add(Blocks.TORCH_COAL.id());

        /// these blocks are replaced on placement.
        aetherBlacklist.add(Blocks.COBBLE_NETHERRACK_CRYSTALLINE.id());
        aetherBlacklist.add(Blocks.PUMICE_WET.id());
        aetherBlacklist.add(Blocks.BRAZIER_ACTIVE.id());
        aetherBlacklist.add(Blocks.PUMPKIN_CARVED_ACTIVE.id());
        aetherBlacklist.add(Blocks.FLUID_LAVA_FLOWING.id());
        aetherBlacklist.add(Blocks.FLUID_LAVA_STILL.id());

        /// blocks that should be banned until unlocked by the sunspirit's death
        if (!SunSpiritDeath.isDead()) {
            aetherBlacklist.add(Blocks.SOULSAND.id());
            aetherBlacklist.add(Blocks.SOULSCHIST.id());
            aetherBlacklist.add(Blocks.PUMPKIN_CARVED_ACTIVE.id());
            aetherBlacklist.add(Blocks.NETHERRACK.id());
            aetherBlacklist.add(Blocks.PUMICE_DRY.id());
            aetherBlacklist.add(Blocks.COBBLE_NETHERRACK.id());
            aetherBlacklist.add(Blocks.STAIRS_COBBLE_NETHERRACK.id());
            aetherBlacklist.add(Blocks.SLAB_COBBLE_NETHERRACK.id());
            aetherBlacklist.add(Blocks.COBBLE_NETHERRACK_CRYSTALLINE.id());
            aetherBlacklist.add(Blocks.NETHERRACK_CARVED.id());
            aetherBlacklist.add(Blocks.NETHERRACK_POLISHED.id());
            aetherBlacklist.add(Blocks.SLAB_NETHERRACK_POLISHED.id());
            aetherBlacklist.add(Blocks.BRICK_NETHERRACK.id());
            aetherBlacklist.add(Blocks.SLAB_BRICK_NETHERRACK.id());
            aetherBlacklist.add(Blocks.STAIRS_BRICK_NETHERRACK.id());

            aetherBlacklist.add(Blocks.ORE_NETHERCOAL_NETHERRACK.id());
            aetherBlacklist.add(Blocks.BLOCK_NETHER_COAL.id());
        }

        FabricLoader.getInstance()
            .getEntrypointContainers("aether", AetherPlugin.class)
            .forEach(plugin -> plugin.getEntrypoint().initializeDimensionBlacklist());
    }

    public static void unlockDaylightCycle(World world) {
        if (!SunSpiritDeath.isDead()) {
            AetherMod.LOGGER.info("Attempted to unlock daylight cycle.");

            SunSpiritDeath.setDead(true);
            SunSpiritDeath.setDeathTime(world.getWorldTime());

            if (EnvironmentHelper.isMultiplayerServer()) {
                NetworkHandler.sendToAllPlayers(
                    new SunspiritDeathNetworkMessage(SunSpiritDeath.isDead(), SunSpiritDeath.getDeathTime())
                );
            }
        }
    }

    public static @Nullable MobAerbunny popBunnyFromPlayer(UUID uuidPlayer, World world) {
        CompoundTag tag = HAS_BUNNY_MAP.remove(uuidPlayer);
        if (tag == null) return null;
        MobAerbunny mobAerbunny = (MobAerbunny) EntityDispatcher.getInstance().createEntityFromNBT(tag, world);
        world.entityJoinedWorld(mobAerbunny);
        return mobAerbunny;
    }

    public static void addBunnyToPlayer(UUID uuidPlayer, @NonNull MobAerbunny mobAerbunny) {
        CompoundTag tag = new CompoundTag();
        mobAerbunny.save(tag);
        mobAerbunny.remove();
        HAS_BUNNY_MAP.put(uuidPlayer, tag);
    }

    public static boolean canGetParachute(UUID uuid) {
        return !HAS_RECEIVED_PARACHUTE_MAP.computeIfAbsent(uuid, it -> false);
    }

    public static void setParachuteReceived(UUID uuid) {
        HAS_RECEIVED_PARACHUTE_MAP.put(uuid, true);
    }

    private static final Map<IntIntPair, List<CompoundTag>> ENTITIES_MOVED_TO_OVERWORLD = new HashMap<>();

    public static synchronized void addEntityToFallen(Entity target) {
        if (AetherMod.LOGGER.isInfoEnabled())
            AetherMod.LOGGER.debug("Sending {} to overworld", Entity.getNameFromEntity(target, true));

        IntIntPair chunk = new IntIntMutablePair(
            ((int) target.x) / 16,
            ((int) target.z) / 16
        );
        List<CompoundTag> chunkList = ENTITIES_MOVED_TO_OVERWORLD.computeIfAbsent(chunk, i -> new ArrayList<>());

        CompoundTag data = new CompoundTag();
        target.save(data);
        target.remove();

        chunkList.add(data);
    }

    public static synchronized void loadEntitiesNearPlayer(Player player, World world) {
        List<IntIntPair> toRemove = new ArrayList<>();
        for (IntIntPair pos : ENTITIES_MOVED_TO_OVERWORLD.keySet()) {
            if (player.distanceTo(pos.firstInt() * 16.0, player.y, pos.secondInt() * 16.0) < 100) {
                List<CompoundTag> entities = ENTITIES_MOVED_TO_OVERWORLD.computeIfAbsent(pos, intPair -> new ArrayList<>());

                while (!entities.isEmpty()) {
                    CompoundTag data = entities.remove(0);

                    Entity copy = EntityDispatcher.getInstance().createEntityFromNBT(data, world);
                    copy.load(data);

                    float scale = Dimension.getCoordScale(AetherDimension.getAether(), Dimension.OVERWORLD);
                    copy.moveTo(copy.x * scale, OVERWORLD_RETURN_HEIGHT, copy.z * scale, copy.yRot, copy.xRot);

                    world.entityJoinedWorld(copy);

                    if (copy instanceof AetherMobFallingToOverworld aetherMobFallingToOverworld) {
                        aetherMobFallingToOverworld.onEnteredOverworld();
                    }
                }

                toRemove.add(pos);
            }
        }

        toRemove.forEach(ENTITIES_MOVED_TO_OVERWORLD::remove);
    }

    public static void setDimensionDataDefaults() {
        SunSpiritDeath.setDeathTime(0);
        SunSpiritDeath.setDead(false);
    }

    public static void setWorldDataDefaults() {
        ENTITIES_MOVED_TO_OVERWORLD.clear();
        HAS_RECEIVED_PARACHUTE_MAP.clear();
        HAS_BUNNY_MAP.clear();
        DungeonMap.clear();
    }

    protected static void loadFallenEntities(@NonNull ListTag entitiesMoved) {
        ENTITIES_MOVED_TO_OVERWORLD.clear();

        entitiesMoved.forEach(tag -> {
            ListTag entities = ((CompoundTag) tag).getList("entities");
            IntIntPair chunk = new IntIntMutablePair(
                ((CompoundTag) tag).getInteger("x"),
                ((CompoundTag) tag).getInteger("z")
            );

            List<CompoundTag> entitiesList = new ArrayList<>();
            entities.forEach(e -> entitiesList.add((CompoundTag) e));
            ENTITIES_MOVED_TO_OVERWORLD.put(chunk, entitiesList);
        });
    }

    public static void saveWorldData(CompoundTag aetherWorldData) {
        ListTag entitiesToMoveMap = new ListTag();
        for (Map.Entry<IntIntPair, List<CompoundTag>> entry : ENTITIES_MOVED_TO_OVERWORLD.entrySet()) {
            CompoundTag entryCompound = new CompoundTag();

            ListTag entities = new ListTag();
            for (CompoundTag entity : entry.getValue()) {
                entities.addTag(entity);
            }

            IntIntPair chunkPos = entry.getKey();

            entryCompound.putInt("x", chunkPos.firstInt());
            entryCompound.putInt("z", chunkPos.secondInt());

            entryCompound.put("entities", entities);
            entitiesToMoveMap.addTag(entryCompound);
        }

        CompoundTag bunnyMap = new CompoundTag();
        for (Map.Entry<UUID, CompoundTag> entry : HAS_BUNNY_MAP.entrySet()) {
            bunnyMap.put(entry.getKey().toString(), entry.getValue());
        }

        aetherWorldData.putInt(AetherMod.MOD_ID + ".__SCHEMA_VERSION__", SCHEMA_VERSION);

        aetherWorldData.put(AetherMod.MOD_ID + ".bunnyMap", bunnyMap);
        aetherWorldData.put(AetherMod.MOD_ID + ".overworldFallen", entitiesToMoveMap);
        DungeonMap.save(aetherWorldData);

        CompoundTag canReceiveParachuteCompound = new CompoundTag();
        HAS_RECEIVED_PARACHUTE_MAP.forEach((key, value) -> canReceiveParachuteCompound.putBoolean(key.toString(), value));
        aetherWorldData.putCompound(AetherMod.MOD_ID + ".canReceiveParachute", canReceiveParachuteCompound);
    }

    public static void loadWorldData(@NonNull CompoundTag aetherWorldData) {
        AetherMod.LOGGER.debug("Loading additional level data.");

        loadFallenEntities(aetherWorldData.getList(AetherMod.MOD_ID + ".overworldFallen"));
        DungeonMap.load(aetherWorldData);

        HAS_RECEIVED_PARACHUTE_MAP.clear();
        CompoundTag canReceiveParachuteCompound = aetherWorldData.getCompound(AetherMod.MOD_ID + ".canReceiveParachute");
        canReceiveParachuteCompound.getValues().forEach(it -> HAS_RECEIVED_PARACHUTE_MAP.put(UUID.fromString(it.getTagName()), ((Byte) it.getValue()) > 0));

        HAS_BUNNY_MAP.clear();
        CompoundTag bunnyCompound = aetherWorldData.getCompound(AetherMod.MOD_ID + ".bunnyMap");
        bunnyCompound.getValues().forEach(it -> HAS_BUNNY_MAP.put(UUID.fromString(it.getTagName()), (CompoundTag) it));
    }

    public static void loadDimensionData(@NonNull CompoundTag dimensionData) {
        AetherMod.LOGGER.debug("Loading additional dimension data.");

        if (!dimensionData.containsKey(AetherMod.MOD_ID + ".__SCHEMA_VERSION__")
            && !dimensionData.containsKey("__SCHEMA_VERSION__")) {
            loadFallenEntities(dimensionData.getList(AetherMod.MOD_ID + ".overworldFallen"));
        }

        SunSpiritDeath.setDead(dimensionData.getBoolean(AetherMod.MOD_ID + ".sunspiritDeathTimestamp"));
    }

    public static void saveDimensionData(@NonNull CompoundTag dimensionData) {
        AetherMod.LOGGER.debug("Saving additional dimension data.");

        dimensionData.putInt(AetherMod.MOD_ID + ".__SCHEMA_VERSION__", SCHEMA_VERSION);
        dimensionData.putBoolean(AetherMod.MOD_ID + ".sunspiritDeathTimestamp", SunSpiritDeath.isDead());
    }

    public static Dimension getAether() {
        return AETHER;
    }
}
