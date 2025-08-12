package teamport.aether.world;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.Tag;
import net.minecraft.core.world.World;

import java.util.Collection;
import java.util.HashMap;

public class DungeonMap {

    protected static HashMap<Integer, DungeonMapEntry> dungeonMap = new HashMap<>();

    public DungeonMap() {}

    public Collection<DungeonMapEntry> values() {
        return dungeonMap.values();
    }

    public boolean isEmpty() {
        return dungeonMap.isEmpty();
    }

    public void remove(Integer id, World world) {
        if (dungeonMap.get(id) == null) return;

        dungeonMap.get(id).remove(world);
        dungeonMap.remove(id);
    }

    public DungeonMapEntry register() {
        int id = dungeonMap.size();
        while (dungeonMap.get(id) != null) {
            id++;
        }

        DungeonMapEntry result = new DungeonMapEntry(id);

        dungeonMap.put(id, result);
        return result;
    }

    public void loadFromNBT(CompoundTag data) {
        dungeonMap.clear();

        for (Tag<?> tag: data.getValues()) {
            if (tag instanceof CompoundTag) {
                dungeonMap.put(
                    Integer.parseInt(tag.getTagName()),
                    DungeonMapEntry.loadFromNBT((CompoundTag) tag)
                );
            }
        }
    }

    public CompoundTag writeToNBT(CompoundTag data) {
        dungeonMap.forEach( (id, coords) -> data.put(String.valueOf(id), coords.writeToNBT(new CompoundTag())));
        return data;
    }
}
