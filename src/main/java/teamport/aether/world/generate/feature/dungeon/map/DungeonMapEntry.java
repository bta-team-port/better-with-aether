package teamport.aether.world.generate.feature.dungeon.map;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.IntTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.BlockLogicLocked;
import teamport.aether.blocks.BlockLogicTrapped;
import teamport.aether.entity.boss.EnemyBoss;
import teamport.aether.helper.Pair;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static teamport.aether.helper.Pair.pair;

public class DungeonMapEntry {
    protected int id;
    @Nullable
    protected Pair<WorldFeaturePoint, WorldFeaturePoint> clearArea;
    protected List<WorldFeaturePoint> entranceDoor;
    protected List<WorldFeaturePoint> treasureDoor;
    protected int doorReplacementID = 0;
    protected int doorReplacementMeta = 0;

    @Nullable
    protected Pair<WorldFeaturePoint, WorldFeaturePoint> bossDoorArea;
    protected int bossDoorMeta;

    protected WorldFeaturePoint position;

    public DungeonMapEntry(Integer id) {
        this.id = id;
        this.entranceDoor = new ArrayList<>();
        this.treasureDoor = new ArrayList<>();
    }

    public void lock(EnemyBoss<? extends Mob> boss, World world) {
        if (bossDoorArea == null) return;

        for (int x = Math.min(bossDoorArea.first.x, bossDoorArea.second.x); x < Math.max(bossDoorArea.first.x, bossDoorArea.second.x); x++) {
            for (int y = Math.min(bossDoorArea.first.y, bossDoorArea.second.y); y < Math.max(bossDoorArea.first.y, bossDoorArea.second.y); y++) {
                for (int z = Math.min(bossDoorArea.first.z, bossDoorArea.second.z); z < Math.max(bossDoorArea.first.z, bossDoorArea.second.z); z++) {
                    world.setBlockAndMetadataWithNotify(x, y, z, AetherBlocks.DOOR_DUNGEON.id(), bossDoorMeta);
                }
            }
        }

        world.playSoundEffect(null, SoundCategory.ENTITY_SOUNDS, bossDoorArea.first.x, bossDoorArea.first.y, bossDoorArea.first.z, "random.door_open", 0.5f, 0.5f);
    }

    public void unlock(EnemyBoss<? extends Mob> boss, World world) {
        if (bossDoorArea == null) return;

        for (int x = Math.min(bossDoorArea.first.x, bossDoorArea.second.x); x < Math.max(bossDoorArea.first.x, bossDoorArea.second.x); x++) {
            for (int y = Math.min(bossDoorArea.first.y, bossDoorArea.second.y); y < Math.max(bossDoorArea.first.y, bossDoorArea.second.y); y++) {
                for (int z = Math.min(bossDoorArea.first.z, bossDoorArea.second.z); z < Math.max(bossDoorArea.first.z, bossDoorArea.second.z); z++) {
                    world.setBlockWithNotify(x, y, z, 0);
                }
            }
        }

        world.playSoundEffect(null, SoundCategory.ENTITY_SOUNDS, bossDoorArea.first.x, bossDoorArea.first.y, bossDoorArea.first.z, "random.door_close", 0.5f, 0.5f);
    }

    public int getId() {
        return id;
    }

    public void setPosition(WorldFeaturePoint position) {
        this.position = position;
    }

    public WorldFeaturePoint getPosition() {
        return position;
    }

    public void setClearArea(Pair<WorldFeaturePoint, WorldFeaturePoint> clearArea) {
        this.setClearArea(clearArea.first, clearArea.second);
    }

    public void setClearArea(WorldFeaturePoint p1, WorldFeaturePoint p2) {
        WorldFeaturePoint lowest = WorldFeaturePoint.wfpoint(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.min(p1.z, p2.z));
        WorldFeaturePoint highest = WorldFeaturePoint.wfpoint(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y), Math.max(p1.z, p2.z));
        this.clearArea = pair(lowest, highest);
    }

    public void setTreasureDoor(List<WorldFeaturePoint> doorBlocks) {
        this.treasureDoor.addAll(doorBlocks);
    }
    public void setEntranceDoor(List<WorldFeaturePoint> entranceDoor){
        this.entranceDoor.addAll(entranceDoor);
    }


    public void loadFromNBT(CompoundTag data) {
        id = data.getInteger("id");
        doorReplacementID = data.getInteger("doorReplacementID");
        doorReplacementMeta = data.getInteger("doorReplacementMeta");

        clearArea = new Pair<>(
                WorldFeaturePoint.fromCompoundTag(data.getCompound("clearPos1")),
                WorldFeaturePoint.fromCompoundTag(data.getCompound("clearPos2"))
        );

        if (clearArea.first == null || clearArea.second == null) {
            clearArea = null;
        }

        bossDoorMeta = data.getInteger("bossDoorMeta");

        bossDoorArea = new Pair<>(
            WorldFeaturePoint.fromCompoundTag(data.getCompound("bossDoorArea1")),
            WorldFeaturePoint.fromCompoundTag(data.getCompound("bossDoorArea2"))
        );

        if (bossDoorArea.first == null || bossDoorArea.second == null) {
            bossDoorArea = null;
        }

        CompoundTag blockListNBT = data.getCompound("blocksDestroyOnDeath");
        if (blockListNBT != null) {
            List<WorldFeaturePoint> list = new ArrayList<>();
            for (int i = 0; i < blockListNBT.getInteger("length"); i++) {
                CompoundTag blockNBT = blockListNBT.getCompound(String.valueOf(i));
                list.add(WorldFeaturePoint.fromCompoundTag(blockNBT));
            }

            treasureDoor = list;
        }
    }

    public CompoundTag writeToNBT(CompoundTag data) {
        data.putInt("id", id);
        data.putInt("doorReplacementID", doorReplacementID);
        data.putInt("doorReplacementMeta", doorReplacementMeta);

        if (bossDoorArea != null) {
            data.put("bossDoorArea1", bossDoorArea.first.toCompoundTag());
            data.put("bossDoorArea2", bossDoorArea.second.toCompoundTag());
            data.putInt("bossDoorMeta", bossDoorMeta);
        }

        if (clearArea != null) {
            data.put("clearPos1", clearArea.first.toCompoundTag());
            data.put("clearPos2", clearArea.second.toCompoundTag());
        }

        if (treasureDoor != null && !treasureDoor.isEmpty()) {
            CompoundTag blockList = new CompoundTag();
            int idx = 0;
            for (WorldFeaturePoint block : treasureDoor) {
                blockList.put(String.valueOf(idx++), block.toCompoundTag());
            }
            blockList.put("length", new IntTag(idx));
            data.put("blocksDestroyOnDeath", blockList);
        }

        return data;
    }

    public <T extends Entity & EnemyBoss> void notifyBossDead(T boss) {
        remove(boss.world);
    }

    public void remove(World world) {
        if (DungeonMap.dungeonMap.get(id) != null) {
            DungeonMap.dungeonMap.remove(id);
        }

        if (treasureDoor != null) {
            for (WorldFeaturePoint coordinate : treasureDoor) {
                world.spawnParticle("smoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0, 0);
                world.spawnParticle("largesmoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0, 0);
                world.setBlockAndMetadataWithNotify(coordinate.x, coordinate.y, coordinate.z, doorReplacementID, doorReplacementMeta);
            }
        }

        if (clearArea != null) {
            WorldFeaturePoint p1 = clearArea.first;
            WorldFeaturePoint p2 = clearArea.second;

            for (int x = p1.x; x < p2.x; x++) {
                for (int y = p1.y; y < p2.y; y++) {
                    for (int z = p1.z; z < p2.z; z++) {
                        Block<?> block = world.getBlock(x, y, z);
                        if (block == null) {
                            continue;
                        }
                        BlockLogic logic = block.getLogic();
                        if (logic instanceof BlockLogicLocked) {
                            world.setBlockWithNotify(x, y, z, ((BlockLogicLocked) logic).replacement.id());
                            continue;
                        }
                        if (logic instanceof BlockLogicTrapped) {
                            world.setBlockWithNotify(x, y, z, ((BlockLogicTrapped) logic).replaceOnClear.id());
                            continue;
                        }
                    }
                }
            }
        }
    }

    public void setBossDoor(Pair<WorldFeaturePoint, WorldFeaturePoint> bossDoor, int doorMeta) {
        this.bossDoorArea = bossDoor;
        this.bossDoorMeta = doorMeta;
    }
}
