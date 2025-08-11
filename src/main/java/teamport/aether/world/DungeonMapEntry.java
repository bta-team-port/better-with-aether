package teamport.aether.world;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.IntTag;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.helper.BlockCoordinate;
import teamport.aether.helper.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DungeonMapEntry {
    protected Integer id;
    @Nullable
    protected Pair<BlockCoordinate, BlockCoordinate> clearArea;
    protected List<BlockCoordinate> doorBlocks = new ArrayList<>();
    protected Integer doorReplacementID = 0;
    protected Integer doorReplacementMeta = 0;

    protected BlockCoordinate position;

    public DungeonMapEntry(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setPosition(BlockCoordinate position) {
        this.position = position;
    }

    public BlockCoordinate getPosition() {
        return position;
    }

    public void setClearArea(Pair<BlockCoordinate, BlockCoordinate> clearArea) {
        this.clearArea = clearArea;
    }

    public void setDoorBlocks(BlockCoordinate[] doorBlocks) {
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
            BlockCoordinate.fromCompoundTag(data.getCompound("clearPos1")),
            BlockCoordinate.fromCompoundTag(data.getCompound("clearPos2"))
        );

        if (result.clearArea.first == null || result.clearArea.second == null) {
            result.clearArea = null;
        }

        CompoundTag blockListNBT = data.getCompound("blocksDestroyOnDeath");
        if (blockListNBT != null) {
            List<BlockCoordinate> list = new ArrayList<>();
            for (int i = 0; i < blockListNBT.getInteger("length"); i++) {
                CompoundTag blockNBT = blockListNBT.getCompound(String.valueOf(i));
                list.add(BlockCoordinate.fromCompoundTag(blockNBT));
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
            for (BlockCoordinate block : doorBlocks) {
                blockList.put(String.valueOf(idx++), block.toCompoundTag());
            }
            blockList.put("length", new IntTag(idx));
            data.put("blocksDestroyOnDeath", blockList);
        }

        return data;
    }

    public void remove(World world) {
        if (doorBlocks != null) {
            for (BlockCoordinate coordinate : doorBlocks) {
                world.spawnParticle("smoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0,0);
                world.spawnParticle("largesmoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0,0);
                world.setBlockAndMetadataWithNotify(coordinate.x, coordinate.y, coordinate.z, doorReplacementID, doorReplacementMeta);
            }
        }
    }
}
