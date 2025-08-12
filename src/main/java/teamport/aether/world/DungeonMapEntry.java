package teamport.aether.world;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.IntTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.blocks.BlockLogicLocked;
import teamport.aether.helper.Pair;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DungeonMapEntry {
    protected Integer id;
    @Nullable
    protected Pair<WorldFeaturePoint, WorldFeaturePoint> clearArea;
    protected List<WorldFeaturePoint> doorBlocks = new ArrayList<>();
    protected Integer doorReplacementID = 0;
    protected Integer doorReplacementMeta = 0;

    protected WorldFeaturePoint position;

    public DungeonMapEntry(Integer id) {
        this.id = id;
    }

    public Integer getId() {
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

    public void setDoorReplacement(Integer doorReplacementID) {
        this.doorReplacementID = doorReplacementID;
    }

    public void setDoorReplacement(Integer doorReplacementID, Integer doorReplacementMeta) {
        this.doorReplacementID = doorReplacementID;
        this.doorReplacementMeta = doorReplacementMeta;
    }

    protected DungeonMapEntry() {}

     public static DungeonMapEntry loadFromNBT(CompoundTag data) {
        DungeonMapEntry result = new DungeonMapEntry();

        result.id = data.getInteger("id");
        result.doorReplacementID = data.getInteger("doorReplacementID");
        result.doorReplacementMeta = data.getInteger("doorReplacementMeta");

        result.clearArea = new Pair<>(
            WorldFeaturePoint.fromCompoundTag(data.getCompound("clearPos1")),
            WorldFeaturePoint.fromCompoundTag(data.getCompound("clearPos2"))
        );

        if (result.clearArea.first == null || result.clearArea.second == null) {
            result.clearArea = null;
        }

        CompoundTag blockListNBT = data.getCompound("blocksDestroyOnDeath");
        if (blockListNBT != null) {
            List<WorldFeaturePoint> list = new ArrayList<>();
            for (int i = 0; i < blockListNBT.getInteger("length"); i++) {
                CompoundTag blockNBT = blockListNBT.getCompound(String.valueOf(i));
                list.add(WorldFeaturePoint.fromCompoundTag(blockNBT));
            }

            result.doorBlocks = list;
        }

        return result;
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

    public void remove(World world) {
        if (doorBlocks != null) {
            for (WorldFeaturePoint coordinate : doorBlocks) {
                world.spawnParticle("smoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0,0);
                world.spawnParticle("largesmoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0,0);
                world.setBlockAndMetadataWithNotify(coordinate.x, coordinate.y, coordinate.z, doorReplacementID, doorReplacementMeta);
            }
        }

        for (int x = clearArea.first.x; x < clearArea.second.x; x++) {
            for (int y = clearArea.first.y; y < clearArea.second.y; y++) {
                for (int z = clearArea.first.z; z < clearArea.second.z; z++) {
                    if (world.getBlock(x, y, z) == null) continue;
                    BlockLogic block = world.getBlock(x, y, z).getLogic();
                    if (block instanceof BlockLogicLocked) { world.setBlock(x, y, z, ((BlockLogicLocked) block).replacement.id()); }
                }
            }
        }
    }
}
