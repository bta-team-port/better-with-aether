package teamport.aether.world.generate.feature.dungeon.map;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.Tag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import teamport.aether.AetherMod;
import teamport.aether.entity.boss.EnemyBoss;

import java.util.Collection;
import java.util.HashMap;

import static teamport.aether.AetherMod.LOGGER;
public class DungeonMap {

    private static final HashMap<String, Class<? extends DungeonMapEntry>> KEY_TYPE_MAP = new HashMap<>();
    private static final HashMap<Class<? extends DungeonMapEntry>, String> TYPE_KEY_MAP = new HashMap<>();

    public static void registerDungeonType(String key, Class<? extends DungeonMapEntry> type) {
        KEY_TYPE_MAP.put(key, type);
        TYPE_KEY_MAP.put(type, key);
    }

    static {
        DungeonMap.registerDungeonType("SLIDER", DungeonMapEntrySlider.class);
        DungeonMap.registerDungeonType("BASE", DungeonMapEntry.class);
    }

    protected static HashMap<Integer, DungeonMapEntry> dungeonMap = new HashMap<>();

    public DungeonMap() {}

    public DungeonMapEntry getDungeon(int id) {
        return dungeonMap.get(id);
    }

    public Collection<DungeonMapEntry> values() {
        return dungeonMap.values();
    }

    public boolean isEmpty() {
        return dungeonMap.isEmpty();
    }

    public <T extends Entity & EnemyBoss> void notifyBossDead(Integer id, T boss) {
        if (dungeonMap.get(id) == null) {
            LOGGER.error("Couldn't find dungeon of id {}", id);
            return;
        }

        dungeonMap.get(id).notifyBossDead(boss);
    }

    public void remove(Integer id, World world) {
        if (dungeonMap.get(id) == null) {
            LOGGER.error("Couldn't find dungeon of id {}", id);
            return;
        }

        dungeonMap.get(id).remove(world);
    }

    public <T extends DungeonMapEntry> T register(Class<T> dungeon) {
        int id = dungeonMap.size();
        while (dungeonMap.get(id) != null) { id++; }

        T result;
        try {  result = dungeon.getConstructor(Integer.class).newInstance(id); }
        catch (Exception e) {
            AetherMod.LOGGER.error("Failed to register dungeon!");
            throw new RuntimeException(e);
        }

        dungeonMap.put(id, result);
        return result;
    }

    public static DungeonMapEntry readDungeonMapEntryFromNBT(CompoundTag tag, int id) {
        String type = tag.getString("type");

        DungeonMapEntry dungeonEntry;

        try {  dungeonEntry = KEY_TYPE_MAP.get(type).getConstructor(Integer.class).newInstance(id); }
        catch (Exception e) {
            AetherMod.LOGGER.error("Failed to load dungeon {} from map!", id);
            AetherMod.LOGGER.error("Defaulting to base dungeon entry...");
            dungeonEntry = new DungeonMapEntry(id);
        }

        dungeonEntry.loadFromNBT(tag);
        return dungeonEntry;
    }

    public void loadFromNBT(CompoundTag data) {
        dungeonMap.clear();

        for (Tag<?> tag : data.getValues()) {
            if (tag instanceof CompoundTag) {
                int id = Integer.parseInt(tag.getTagName());
                DungeonMapEntry dungeonEntry = readDungeonMapEntryFromNBT((CompoundTag) tag, id);
                dungeonMap.put(dungeonEntry.id, dungeonEntry);
            }
        }
    }

    public CompoundTag writeToNBT(CompoundTag data) {
        dungeonMap.forEach( (id, dungeon) -> {
            CompoundTag dungeonData = dungeon.writeToNBT(new CompoundTag());
            dungeonData.putString("type", TYPE_KEY_MAP.get(dungeon.getClass()));

            data.put(String.valueOf(id), dungeonData);

        });
        return data;
    }
}
