package teamport.aether.world.generate.feature.dungeon.map;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.IntTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.blocks.BlockLogicLocked;
import teamport.aether.blocks.BlockLogicTrapped;
import teamport.aether.entity.boss.EnemyBoss;
import teamport.aether.helper.Pair;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DungeonMapEntry {
    protected int id;
    @Nullable
    protected Pair<WorldFeaturePoint, WorldFeaturePoint> clearArea;
    protected WorldFeatureComponent treasureDoor;
    protected WorldFeatureComponent entranceDoor;
    protected List<WorldFeaturePoint> doorBlocks = new ArrayList<>();
    protected int doorReplacementID = 0;
    protected int doorReplacementMeta = 0;

    protected WorldFeaturePoint position;

    public DungeonMapEntry(Integer id) {
        this.id = id;
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
        this.clearArea = clearArea;
    }

    public void setDoorBlocks(WorldFeaturePoint[] doorBlocks) {
        this.doorBlocks.addAll(Arrays.asList(doorBlocks));
    }

    public void setEntranceDoor(WorldFeatureComponent entranceDoor){
        this.entranceDoor.add(entranceDoor);
    }

    public void setTreasureDoor(WorldFeatureComponent treasureDoor){
        this.treasureDoor.add(treasureDoor);
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

        CompoundTag blockListNBT = data.getCompound("blocksDestroyOnDeath");
        if (blockListNBT != null) {
            List<WorldFeaturePoint> list = new ArrayList<>();
            for (int i = 0; i < blockListNBT.getInteger("length"); i++) {
                CompoundTag blockNBT = blockListNBT.getCompound(String.valueOf(i));
                list.add(WorldFeaturePoint.fromCompoundTag(blockNBT));
            }

            doorBlocks = list;
        }
    }

    public CompoundTag writeToNBT(CompoundTag data) {
        data.putInt("id", id);
        data.putInt("doorReplacementID", doorReplacementID);
        data.putInt("doorReplacementMeta", doorReplacementMeta);

        if (clearArea != null) {
            data.put("clearPos1", clearArea.first.toCompoundTag());
            data.put("clearPos2", clearArea.second.toCompoundTag());
        }

        if (doorBlocks != null && !doorBlocks.isEmpty()) {
            CompoundTag blockList = new CompoundTag();
            int idx = 0;
            for (WorldFeaturePoint block : doorBlocks) {
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

        if (doorBlocks != null) {
            for (WorldFeaturePoint coordinate : doorBlocks) {
                world.spawnParticle("smoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0, 0);
                world.spawnParticle("largesmoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0, 0);
                world.setBlockAndMetadataWithNotify(coordinate.x, coordinate.y, coordinate.z, doorReplacementID, doorReplacementMeta);
            }
        }

        if (clearArea != null) {
            int firstX, firstY, firstZ;
            int secondX, secondY, secondZ;

            if (clearArea.first.x < clearArea.second.x) {
                firstX = clearArea.first.x;
                secondX = clearArea.second.x;
            } else {
                secondX = clearArea.first.x;
                firstX = clearArea.second.x;
            }

            if (clearArea.first.y < clearArea.second.y) {
                firstY = clearArea.first.y;
                secondY = clearArea.second.y;
            } else {
                secondY = clearArea.first.y;
                firstY = clearArea.second.y;
            }

            if (clearArea.first.z < clearArea.second.z) {
                firstZ = clearArea.first.z;
                secondZ = clearArea.second.z;
            } else {
                secondZ = clearArea.first.z;
                firstZ = clearArea.second.z;
            }

            for (int x = firstX; x < secondX; x++) {
                for (int y = firstY; y < secondY; y++) {
                    for (int z = firstZ; z < secondZ; z++) {

                        Block<?> block = world.getBlock(x, y, z);
                        if (block != null) {
                            BlockLogic logic = block.getLogic();

                            if (logic instanceof BlockLogicLocked) {
                                world.setBlockWithNotify(x, y, z, ((BlockLogicLocked) logic).replacement.id());

                            } else if (logic instanceof BlockLogicTrapped) {
                                world.setBlockWithNotify(x, y, z, ((BlockLogicTrapped) logic).replaceOnClear.id());
                            }
                        }
                    }
                }
            }
        }
    }
}
