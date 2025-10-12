package teamport.aether.world.generate.feature.dungeon.map;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.blocks.dungeon.BlockLogicDungeonDoor;
import teamport.aether.blocks.dungeon.BlockLogicLocked;
import teamport.aether.blocks.dungeon.BlockLogicTrapped;
import teamport.aether.entity.boss.EnemyBoss;
import teamport.aether.helper.Pair;
import teamport.aether.helper.ParticleHelper;
import teamport.aether.world.generate.feature.components.WorldFeatureBlock;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.ArrayList;
import java.util.List;

import static teamport.aether.helper.Pair.pair;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.iterate3d;

public class DungeonMapEntry {
    protected int id;
    @Nullable
    protected Pair<WorldFeaturePoint, WorldFeaturePoint> clearArea;
    protected boolean entranceLocked = false;
    protected List<WorldFeatureBlock> entranceDoor;
    protected List<WorldFeaturePoint> treasureDoor;
    protected int doorReplacementID = 0;
    protected int doorReplacementMeta = 0;

    protected WorldFeaturePoint position;

    public DungeonMapEntry(Integer id) {
        this.id = id;
        this.entranceDoor = new ArrayList<>();
        this.treasureDoor = new ArrayList<>();
    }

    public boolean isEntranceLocked() {
        return entranceLocked;
    }

    public <T extends Mob & EnemyBoss> void lock(@Nullable T boss, World world) {
        if (entranceDoor == null) return;
        entranceLocked = true;
        for (WorldFeatureBlock block : entranceDoor) {
            world.playSoundEffect(null, SoundCategory.ENTITY_SOUNDS, block.x, block.y, block.z, "random.door_open", 0.025f, 0.5f);
            block.place(world);
        }
    }

    public <T extends Mob & EnemyBoss> void unlock(@Nullable T boss, World world) {
        entranceLocked = false;
        if (entranceDoor == null) return;
        for (WorldFeatureBlock block : entranceDoor) {
            world.playSoundEffect(null, SoundCategory.ENTITY_SOUNDS, block.x, block.y, block.z, "random.door_open", 0.025f, 0.5f);
            world.setBlockWithNotify(block.x, block.y, block.z, 0);
        }
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
        WorldFeaturePoint lowest = WorldFeaturePoint.wfp(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.min(p1.z, p2.z));
        WorldFeaturePoint highest = WorldFeaturePoint.wfp(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y), Math.max(p1.z, p2.z));
        this.clearArea = pair(lowest, highest);
    }

    public void setTreasureDoor(List<WorldFeaturePoint> doorBlocks) {
        this.treasureDoor.addAll(doorBlocks);
    }

    public void setEntranceDoor(List<WorldFeatureBlock> entranceDoor) {
        this.entranceDoor.addAll(entranceDoor);
    }

    public void loadFromNBT(CompoundTag data) {
        this.id = data.getInteger("id");
        this.doorReplacementID = data.getInteger("doorReplacementID");
        this.doorReplacementMeta = data.getInteger("doorReplacementMeta");
        this.entranceLocked = data.getBoolean("entranceLocked");

        this.position = WorldFeaturePoint.fromCompoundTag(data.getCompound("position"));

        this.clearArea = new Pair<>(
                WorldFeaturePoint.fromCompoundTag(data.getCompound("clearPos1")),
                WorldFeaturePoint.fromCompoundTag(data.getCompound("clearPos2"))
        );

        if (this.clearArea.first == null || this.clearArea.second == null) {
            this.clearArea = null;
        }

        ListTag treasureDoorNBT = data.getList("blocksDestroyOnDeath");
        if (treasureDoorNBT != null) {
            List<WorldFeaturePoint> list = new ArrayList<>();

            for (int i = 0; i < treasureDoorNBT.tagCount(); i++) {
                CompoundTag blockNBT = (CompoundTag) treasureDoorNBT.tagAt(i);
                list.add(WorldFeaturePoint.fromCompoundTag(blockNBT));
            }

            this.treasureDoor = list;
        }

        ListTag entranceDoorNBT = data.getList("blocksDungeonEntrance");
        if (entranceDoorNBT != null) {
            List<WorldFeatureBlock> list = new ArrayList<>();

            for (int i = 0; i < entranceDoorNBT.tagCount(); i++) {
                CompoundTag blockNBT = (CompoundTag) entranceDoorNBT.tagAt(i);
                list.add(WorldFeatureBlock.fromCompoundTag(blockNBT));
            }

            this.entranceDoor = list;
        }

    }

    public CompoundTag writeToNBT(CompoundTag data) {
        data.putInt("id", this.id);
        data.putInt("doorReplacementID", this.doorReplacementID);
        data.putInt("doorReplacementMeta", this.doorReplacementMeta);
        data.putBoolean("entranceLocked", this.entranceLocked);

        data.putCompound("position", this.position.toCompoundTag());

        if (this.clearArea != null) {
            data.put("clearPos1", this.clearArea.first.toCompoundTag());
            data.put("clearPos2", this.clearArea.second.toCompoundTag());
        }

        if (this.treasureDoor != null && !this.treasureDoor.isEmpty()) {
            ListTag blockList = new ListTag();

            for (WorldFeaturePoint b : this.treasureDoor) blockList.addTag(b.toCompoundTag());
            data.put("blocksDestroyOnDeath", blockList);
        }

        if (this.entranceDoor != null && !this.entranceDoor.isEmpty()) {
            ListTag blockList = new ListTag();
            for (WorldFeatureBlock b : this.entranceDoor) blockList.addTag(b.toCompoundTag());
            data.put("blocksDungeonEntrance", blockList);
        }

        return data;
    }

    public <T extends Entity & EnemyBoss> void notifyBossDead(T boss) {
        remove(boss.world);
    }

    public void remove(World world) {
        unlock(null, world);
        if (DungeonMap.dungeonMap.get(id) != null) {
            DungeonMap.dungeonMap.remove(id);
        }

        if (treasureDoor != null) {
            for (WorldFeaturePoint coordinate : treasureDoor) {
                ParticleHelper.spawnParticle(world, "smoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0, 0);
                ParticleHelper.spawnParticle(world, "largesmoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0, 0);
                world.setBlockAndMetadataWithNotify(coordinate.x, coordinate.y, coordinate.z, doorReplacementID, doorReplacementMeta);
            }
        }

        if (clearArea != null) {
            iterate3d(clearArea, p -> {
                Block<?> block = world.getBlock(p.x, p.y, p.z);
                if (block == null) return;

                BlockLogic logic = block.getLogic();
                if (logic instanceof BlockLogicLocked) {
                    world.setBlockWithNotify(p.x, p.y, p.z, ((BlockLogicLocked) logic).replacement.id());
                } else if (logic instanceof BlockLogicTrapped) {
                    world.setBlockWithNotify(p.x, p.y, p.z, ((BlockLogicTrapped) logic).replaceOnClear.id());
                } else if (logic instanceof BlockLogicDungeonDoor) {
                    world.setBlockWithNotify(p.x, p.y, p.z, 0);
                }
            });
        }
    }
}
