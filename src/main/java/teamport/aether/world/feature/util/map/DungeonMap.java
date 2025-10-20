package teamport.aether.world.feature.util.map;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import teamport.aether.AetherMod;
import teamport.aether.net.message.AetherDungeonMapUpdateNetworkMessage;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.feature.dungeon.bronze.DungeonLogicBronzeDungeon;
import teamport.aether.world.feature.dungeon.bronzeLegacy.DungeonLogicBronzeDungeonLegacy;
import teamport.aether.world.feature.dungeon.gold.DungeonLogicGoldDungeon;
import teamport.aether.world.feature.dungeon.silver.DungeonLogicSilverDungeon;
import teamport.aether.world.feature.util.WorldFeaturePoint;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static teamport.aether.AetherMod.LOGGER;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfpoint;

public class DungeonMap {

    protected static final HashMap<Integer, DungeonLogic> dungeonMap = new HashMap<>();

    protected DungeonMap() {}

    private static final HashMap<String, Class<? extends DungeonLogic>> KEY_TYPE_MAP = new HashMap<>();
    private static final HashMap<Class<? extends DungeonLogic>, String> TYPE_KEY_MAP = new HashMap<>();

    static {
        DungeonMap.registerDungeonType("SUNSPIRIT", DungeonLogicGoldDungeon.class);
        DungeonMap.registerDungeonType("VALKYRIE", DungeonLogicSilverDungeon.class);
        DungeonMap.registerDungeonType("SLIDER", DungeonLogicBronzeDungeon.class);
        DungeonMap.registerDungeonType("SLIDER_LEGACY", DungeonLogicBronzeDungeonLegacy.class);
        DungeonMap.registerDungeonType("BASE", DungeonLogic.class);
    }

    public static void registerDungeonType(String key, Class<? extends DungeonLogic> type) {
        KEY_TYPE_MAP.put(key, type);
        TYPE_KEY_MAP.put(type, key);
    }

    static public CompoundTag save(CompoundTag data) {
        ListTag dungeons = new ListTag();

        dungeonMap.forEach((id, dungeon) -> {
            CompoundTag dungeonData = new CompoundTag();
            dungeon.save(dungeonData);
            dungeonData.putString("type", TYPE_KEY_MAP.get(dungeon.getClass()));
            dungeonData.putInt("dimensionID", dungeon.dimensionID);
            dungeonData.putLong("seed", dungeon.seed);
            dungeonData.putInt("id", id);
        });

        data.put(AetherMod.MOD_ID+".dungeon", dungeons);
        return data;
    }

    static public void load(CompoundTag data) {
        dungeonMap.clear();
        ListTag dungeons = data.getList(AetherMod.MOD_ID+".dungeon");

        dungeons.forEach( tag -> {
            DungeonLogic dungeon = loadDungeonFromNBT((CompoundTag) tag);
            if (dungeon != null) dungeonMap.put(dungeon.id, dungeon);
        });
    }

    public static DungeonLogic loadDungeonFromNBT(CompoundTag tag) {
        String type = tag.getString("type");
        int dimensionID = tag.getInteger("dimensionID");
        long seed = tag.getLong("seed");
        int id = tag.getInteger("id");

        DungeonLogic dungeonEntry;
        try {
            dungeonEntry = KEY_TYPE_MAP.get(type)
                    .getConstructor(int.class, int.class, long.class)
                    .newInstance(dimensionID, id, seed);
        }

        catch (Exception e) {
            AetherMod.LOGGER.error("Failed to load dungeon {} from map!", id);
            AetherMod.LOGGER.error("This world might be outdated or corrupted!");
            Thread.dumpStack();
            return null;
        }

        dungeonEntry.load(tag);
        return dungeonEntry;
    }

    protected static final List<DungeonLogic> entryListCache = new ArrayList<>();
    protected static final int MP_LIST_UPDATE_COOLDOWN = 3000;
    protected static long lastListUpdateStamp = 0;

    public static Collection<DungeonLogic> getDungeonList() {
        if (EnvironmentHelper.isClientWorld()) {
            long time = System.currentTimeMillis();
            if (time - lastListUpdateStamp >= MP_LIST_UPDATE_COOLDOWN) {
                lastListUpdateStamp = time;
                NetworkHandler.sendToServer(new AetherDungeonMapUpdateNetworkMessage());
            }

            return entryListCache;
        }

        return dungeonMap.values();
    }

    public static ListTag serializeListFor(Player player) {
        ListTag resultTag = new ListTag();

        dungeonMap.values().stream()
            .filter(Objects::nonNull)
            .filter(d -> d.getPosition() != null) // :^)
            .filter(d -> d.getPosition().distanceTo(wfpoint(player)) < 300)
            .forEach(d -> resultTag.addTag(d.save(new CompoundTag())));

        return resultTag;
    }

    public static void updateListCache(ListTag dungeons) {
        entryListCache.clear();

        dungeons.forEach( tag -> {
            DungeonLogic dungeon = loadDungeonFromNBT((CompoundTag) tag);
            if (dungeon != null) entryListCache.add(dungeon);
        });
    }

    public static void runWithDungeon(Integer dungeonID, Consumer<DungeonLogic> func) {
        if (dungeonID == null) return;

        DungeonLogic dungeon = dungeonMap.get(dungeonID);
        if (dungeon == null) return;

        func.accept(dungeon);
    }


    public static DungeonLogic getDungeon(int id) {
        return dungeonMap.get(id);
    }

    public static boolean isEmpty() {
        return dungeonMap.isEmpty();
    }

    /// Marks dungeon for removal.
    public static void remove(Integer id) {
        DungeonLogic dungeon = dungeonMap.get(id);

        if (dungeon == null) {
            LOGGER.error("Couldn't find dungeon of id {}", id);
            return;
        }

        dungeonMap.get(id).markedRemoved = true;
    }

    public static <T extends DungeonLogic> T register(Class<T> dungeonClass, World world, long seed, int x, int y, int z) {
        int id = dungeonMap.size();
        while (dungeonMap.get(id) != null) id++;

        T dungeon;
        try {
            dungeon = dungeonClass
                    .getConstructor(int.class, int.class, long.class)
                    .newInstance(world.dimension.id, id, seed);
        }

        catch (Exception e) {
            AetherMod.LOGGER.error("Failed to register dungeon!");
            throw new RuntimeException(e);
        }

        dungeon.setPosition(new WorldFeaturePoint(x, y, z));
        dungeonMap.put(id, dungeon);
        return dungeon;
    }
}
