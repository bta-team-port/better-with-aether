package teamport.aether.world;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import it.unimi.dsi.fastutil.ints.IntBooleanImmutablePair;
import it.unimi.dsi.fastutil.ints.IntBooleanPair;
import it.unimi.dsi.fastutil.ints.IntIntMutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
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
import teamport.aether.AetherGlobals;
import teamport.aether.block.AetherBlocks;
import teamport.aether.compat.AetherApiEvents;
import teamport.aether.compat.AetherPlugin;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;
import teamport.aether.entity.interfaces.AetherMobFallingToOverworld;
import teamport.aether.net.message.SunspiritDeathNetworkMessage;
import teamport.aether.world.biome.AetherBiomes;
import teamport.aether.world.chunk.BiomeProviderAether;
import teamport.aether.world.feature.util.map.DungeonMap;
import teamport.aether.world.type.AetherWorldTypes;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

import java.util.*;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherDimension {
    private static final int SCHEMA_VERSION = 3;
    // config
    private static final int AETHER_DIMENSION_ID = AetherConfig.DIMENSION;
    public static final int OVERWORLD_RETURN_HEIGHT = World.HEIGHT_BLOCKS + 64;
    // dungeon
    public static final int DUNGEON_GENERATION_RADIUS = 16;
    public static final int BOSS_DETECTION_RADIUS = 80;
    public static final int BOSS_DETECTION_RANGE_SQR = 6400;
    // blacklist and transforms
    private static final HashMap<Integer, List<Integer>> DIMENSION_PLACEMENT_BLACKLIST = new HashMap<>();
    private static final Map<Integer, Integer> BLOCK_TO_BECOME = new HashMap<>();
    // dim crossing riddables
    private static final HashMap<UUID, Boolean> HAS_RECEIVED_PARACHUTE_MAP = new HashMap<>();
    private static final HashMap<UUID, CompoundTag> HAS_BUNNY_MAP = new HashMap<>();
    // inits
    private static Dimension AETHER;
    private static boolean hasInit = false;

    private AetherDimension() {
    }

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
        AetherDimension.initDimensionBlackList();
    }

    public static List<Integer> getDimensionBlacklist(@NonNull Dimension dimension) {
        return getDimensionBlacklist(dimension.id);
    }

    public static List<Integer> getDimensionBlacklist(Integer dimensionID) {
        return DIMENSION_PLACEMENT_BLACKLIST.computeIfAbsent(dimensionID, k -> new ArrayList<>());
    }

    public static Dimension getAether() {
        return AETHER;
    }

    public static int getToBecomeBlockID(int blockID, int defaultValue) {
        return BLOCK_TO_BECOME.getOrDefault(blockID, defaultValue);
    }

    public static void addBannedBlocks(@NonNull BannedBlock aetherBlacklist) {
        aetherBlacklist.add(Blocks.PORTAL_NETHER.id());
        aetherBlacklist.add(Blocks.FIRE.id());
        aetherBlacklist.add(Blocks.FIRE_SULFURIC.id());
        aetherBlacklist.add(Blocks.FIRE_COLD.id());
        aetherBlacklist.add(Blocks.TORCH_COAL.id());

        aetherBlacklist.add(Blocks.MAGMA.id());
        aetherBlacklist.add(Blocks.BRIMTHAW.id());
        aetherBlacklist.add(Blocks.BOULDER_MAGMATIC.id());
        aetherBlacklist.add(Blocks.BOULDER_SULFURIC.id());
        aetherBlacklist.add(Blocks.EMBER.id());

        aetherBlacklist.add(Blocks.BLOCK_NETHER_COAL.id());

        /// these blocks are replaced on placement.
        aetherBlacklist.add(Blocks.PUMICE_WET.id(), Blocks.PUMICE_DRY.id());
        aetherBlacklist.add(Blocks.BRAZIER_ACTIVE.id(), Blocks.BRAZIER_INACTIVE.id());
        aetherBlacklist.add(Blocks.FLUID_LAVA_FLOWING.id(), AetherBlocks.AEROGEL.id());
        aetherBlacklist.add(Blocks.FLUID_LAVA_STILL.id(), AetherBlocks.AEROGEL.id());
        aetherBlacklist.add(Blocks.PUMPKIN_CARVED_ACTIVE.id(), Blocks.PUMPKIN_CARVED_IDLE.id());


        /// blocks that should be banned until unlocked by the sunspirit's death
        aetherBlacklist.add(Blocks.BLOCK_ASH.id(), true);
        aetherBlacklist.add(Blocks.LAYER_ASH.id(), true);

        aetherBlacklist.add(Blocks.SOULSAND.id(), true);
        aetherBlacklist.add(Blocks.SOULSCHIST.id(), true);
        aetherBlacklist.add(Blocks.SOUL_CATCHER.id());

        aetherBlacklist.add(Blocks.BONESHALE.id());

        aetherBlacklist.add(Blocks.ORE_NETHERCOAL_BASALT.id(), true);

        aetherBlacklist.add(Blocks.SULFUR.id(), true);

        aetherBlacklist.add(Blocks.THERMAL_VENT.id(), true);

        aetherBlacklist.add(Blocks.RUBYGLASS_COLUMN.id(), true);
        aetherBlacklist.add(Blocks.RUBYGLASS_CRYSTAL.id(), true);
        aetherBlacklist.add(Blocks.RUBYGLASS_NODE.id(), true);
        aetherBlacklist.add(Blocks.BLOCK_RUBYGLASS.id(), true);
        aetherBlacklist.add(Blocks.RUBYGLASS_GROWTH.id(), true);
        aetherBlacklist.add(Blocks.BRICK_RUBYGLASS.id(), true);
        aetherBlacklist.add(Blocks.SLAB_BRICK_RUBYGLASS.id(), true);
        aetherBlacklist.add(Blocks.STAIRS_BRICK_RUBYGLASS.id(), true);

        aetherBlacklist.add(Blocks.PUMICE_DRY.id(), true);

        // Netherrack
        aetherBlacklist.add(Blocks.NETHERRACK.id(), true);
        aetherBlacklist.add(Blocks.NETHERRACK_CARVED.id(), true);
        aetherBlacklist.add(Blocks.NETHERRACK_POLISHED.id(), true);
        aetherBlacklist.add(Blocks.SLAB_NETHERRACK_POLISHED.id(), true);

        aetherBlacklist.add(Blocks.COBBLE_NETHERRACK.id(), true);
        aetherBlacklist.add(Blocks.STAIRS_COBBLE_NETHERRACK.id(), true);
        aetherBlacklist.add(Blocks.SLAB_COBBLE_NETHERRACK.id(), true);
        aetherBlacklist.add(Blocks.COBBLE_NETHERRACK_CRYSTALLINE.id(), true);

        aetherBlacklist.add(Blocks.BRICK_NETHERRACK.id(), true);
        aetherBlacklist.add(Blocks.SLAB_BRICK_NETHERRACK.id(), true);
        aetherBlacklist.add(Blocks.STAIRS_BRICK_NETHERRACK.id(), true);

        aetherBlacklist.add(Blocks.BUTTON_NETHERRACK.id(), true);
        aetherBlacklist.add(Blocks.PRESSURE_PLATE_NETHERRACK.id(), true);
        aetherBlacklist.add(Blocks.PRESSURE_PLATE_COBBLE_NETHERRACK.id(), true);

        aetherBlacklist.add(Blocks.STATUE_NETHERRACK_LOWER.id(), true);
        aetherBlacklist.add(Blocks.STATUE_NETHERRACK_UPPER.id(), true);

        aetherBlacklist.add(Blocks.ORE_NETHERCOAL_NETHERRACK.id(), true);

        // Gloomstone
        aetherBlacklist.add(Blocks.GLOOMSTONE.id(), true);
        aetherBlacklist.add(Blocks.GLOOMSTONE_CARVED.id(), true);
        aetherBlacklist.add(Blocks.GLOOMSTONE_POLISHED.id(), true);
        aetherBlacklist.add(Blocks.SLAB_GLOOMSTONE_POLISHED.id(), true);

        aetherBlacklist.add(Blocks.COBBLE_GLOOMSTONE.id(), true);
        aetherBlacklist.add(Blocks.STAIRS_COBBLE_GLOOMSTONE.id(), true);
        aetherBlacklist.add(Blocks.SLAB_COBBLE_GLOOMSTONE.id(), true);

        aetherBlacklist.add(Blocks.BRICK_GLOOMSTONE.id(), true);
        aetherBlacklist.add(Blocks.SLAB_BRICK_GLOOMSTONE.id(), true);
        aetherBlacklist.add(Blocks.STAIRS_BRICK_GLOOMSTONE.id(), true);

        aetherBlacklist.add(Blocks.BUTTON_GLOOMSTONE.id(), true);
        aetherBlacklist.add(Blocks.PRESSURE_PLATE_GLOOMSTONE.id(), true);
        aetherBlacklist.add(Blocks.PRESSURE_PLATE_COBBLE_GLOOMSTONE.id(), true);

        aetherBlacklist.add(Blocks.STATUE_GLOOMSTONE_LOWER.id(), true);
        aetherBlacklist.add(Blocks.STATUE_GLOOMSTONE_UPPER.id(), true);

        aetherBlacklist.add(Blocks.ORE_NETHERCOAL_GLOOMSTONE.id(), true);

        // Brimstone
        aetherBlacklist.add(Blocks.BRIMSAND.id(), true);

        aetherBlacklist.add(Blocks.BRIMSTONE.id(), true);
        aetherBlacklist.add(Blocks.SLAB_BRIMSTONE.id(), true);
        aetherBlacklist.add(Blocks.STAIRS_BRIMSTONE.id(), true);

        aetherBlacklist.add(Blocks.BRICK_BRIMSTONE.id(), true);
        aetherBlacklist.add(Blocks.SLAB_BRICK_BRIMSTONE.id(), true);
        aetherBlacklist.add(Blocks.STAIRS_BRICK_BRIMSTONE.id(), true);

        aetherBlacklist.add(Blocks.BUTTON_BRIMSTONE.id(), true);
        aetherBlacklist.add(Blocks.PRESSURE_PLATE_BRIMSTONE.id(), true);
    }


    public static void initDimensionBlackList() {
        DIMENSION_PLACEMENT_BLACKLIST.clear();
        List<Integer> aetherBlacklist = getDimensionBlacklist(AETHER);
        AetherApiEvents.DIMENSION_BLACKLIST.emit(mod -> {
                BannedBlock banned = new BannedBlock();
                mod.accept(banned);
                for (IntBooleanPair pair : banned.getList()) {
                    if (!pair.secondBoolean() || !SunSpiritDeath.isDead()) {
                        aetherBlacklist.add(pair.firstInt());
                    }
                }
            }
        );
        // will be removed at a later date
        FabricLoader.getInstance()
            .getEntrypointContainers("aether", AetherPlugin.class)
            .forEach(plugin -> plugin.getEntrypoint().initializeDimensionBlacklist());
    }

    public static void unlockDaylightCycle(World world) {
        if (!SunSpiritDeath.isDead()) {
            AetherGlobals.LOGGER.info("Attempted to unlock daylight cycle.");

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
        if (AetherGlobals.LOGGER.isInfoEnabled())
            AetherGlobals.LOGGER.debug("Sending {} to overworld", Entity.getNameFromEntity(target, true));

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

        aetherWorldData.putInt(MOD_ID + ".__SCHEMA_VERSION__", SCHEMA_VERSION);

        aetherWorldData.put(MOD_ID + ".bunnyMap", bunnyMap);
        aetherWorldData.put(MOD_ID + ".overworldFallen", entitiesToMoveMap);
        DungeonMap.save(aetherWorldData);

        CompoundTag canReceiveParachuteCompound = new CompoundTag();
        HAS_RECEIVED_PARACHUTE_MAP.forEach((key, value) -> canReceiveParachuteCompound.putBoolean(key.toString(), value));
        aetherWorldData.putCompound(MOD_ID + ".canReceiveParachute", canReceiveParachuteCompound);
    }

    public static void loadWorldData(@NonNull CompoundTag aetherWorldData) {
        AetherGlobals.LOGGER.debug("Loading additional level data.");

        loadFallenEntities(aetherWorldData.getList(MOD_ID + ".overworldFallen"));
        DungeonMap.load(aetherWorldData);

        HAS_RECEIVED_PARACHUTE_MAP.clear();
        CompoundTag canReceiveParachuteCompound = aetherWorldData.getCompound(MOD_ID + ".canReceiveParachute");
        canReceiveParachuteCompound.getValues().forEach(it -> HAS_RECEIVED_PARACHUTE_MAP.put(UUID.fromString(it.getTagName()), ((Byte) it.getValue()) > 0));

        HAS_BUNNY_MAP.clear();
        CompoundTag bunnyCompound = aetherWorldData.getCompound(MOD_ID + ".bunnyMap");
        bunnyCompound.getValues().forEach(it -> HAS_BUNNY_MAP.put(UUID.fromString(it.getTagName()), (CompoundTag) it));
    }

    public static void loadDimensionData(@NonNull CompoundTag dimensionData) {
        AetherGlobals.LOGGER.debug("Loading additional dimension data.");
        if (!dimensionData.containsKey(MOD_ID + ".__SCHEMA_VERSION__")
            && !dimensionData.containsKey("__SCHEMA_VERSION__")) {
            loadFallenEntities(dimensionData.getList(MOD_ID + ".overworldFallen"));
        }
        SunSpiritDeath.setDead(dimensionData.getBoolean(MOD_ID + ".sunspiritDeathTimestamp"));
    }

    public static void saveDimensionData(@NonNull CompoundTag dimensionData) {
        AetherGlobals.LOGGER.debug("Saving additional dimension data.");
        dimensionData.putInt(MOD_ID + ".__SCHEMA_VERSION__", SCHEMA_VERSION);
        dimensionData.putBoolean(MOD_ID + ".sunspiritDeathTimestamp", SunSpiritDeath.isDead());
    }

    public static class BannedBlock {
        private final ObjectList<IntBooleanPair> list = new ObjectArrayList<>();

        public boolean add(int blockID) {
            return this.add(blockID, false);
        }

        public boolean add(int blockID, boolean removeOnDeath) {
            return this.list.add(new IntBooleanImmutablePair(blockID, removeOnDeath));
        }

        public boolean add(int originalID, int replaceID, boolean removeOnDeath) {
            BLOCK_TO_BECOME.put(originalID, replaceID);
            return this.list.add(new IntBooleanImmutablePair(originalID, removeOnDeath));
        }

        public boolean add(int originalID, int replaceID) {
            return this.add(originalID, replaceID, false);
        }

        public List<IntBooleanPair> getList() {
            return ObjectLists.unmodifiable(this.list);
        }
    }
}
