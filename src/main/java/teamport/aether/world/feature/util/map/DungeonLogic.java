package teamport.aether.world.feature.util.map;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.blocks.dungeon.BlockLogicDungeonDoor;
import teamport.aether.blocks.dungeon.BlockLogicLocked;
import teamport.aether.blocks.dungeon.BlockLogicTrapped;
import teamport.aether.entity.boss.EnemyBoss;
import teamport.aether.helper.Pair;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.feature.util.WorldFeatureBlock;
import teamport.aether.world.feature.util.WorldFeaturePoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static teamport.aether.helper.Pair.pair;
import static teamport.aether.world.feature.util.WorldFeatureComponent.iterate3d;

public abstract class DungeonLogic {

    // <base data>
    public static final int SCHEMA_VERSION = 1;

    public boolean hasGenerated = false;
    public final int id;
    public final long seed;

    protected WorldFeaturePoint position;
    protected boolean markedRemoved = false;

    private int dimensionID;
    // </base data>

    public DungeonLogic(int dimensionID, int id, long seed) {
        this.id = id;
        this.seed = seed;
        this.dimensionID = dimensionID;
    }

    protected void setPosition(WorldFeaturePoint position) {
        this.position = position;
    }

    public WorldFeaturePoint getPosition() {
        return this.position.copy();
    }

    public boolean hasGenerated() {
        return hasGenerated;
    }

    public int getDimensionID() {
        return dimensionID;
    }

    protected CompoundTag save(CompoundTag data) {
        this.saveStructureData(data);
        data.putBoolean("hasGenerated", this.hasGenerated);
        data.putCompound("position", this.position.toCompoundTag());
        data.putInt("SCHEMA_VERSION", SCHEMA_VERSION);
        return data;
    }

    protected void load(CompoundTag data) {
        loadStructureData(data);
        hasGenerated = data.getBoolean("hasGenerated");
        position = WorldFeaturePoint.fromCompoundTag(data.getCompound("position"));

        if (data.getInteger("SCHEMA_VERSION") == 0) {
            hasGenerated = true;
            dimensionID = AetherDimension.AETHER.id;
        }
    }

    // <structure data>
    @Nullable
    protected Pair<WorldFeaturePoint, WorldFeaturePoint> clearArea;

    protected boolean entranceLocked = false;

    protected List<WorldFeatureBlock> entranceDoor = new ArrayList<>();
    protected List<WorldFeaturePoint> treasureDoor = new ArrayList<>();

    protected int doorReplacementID = 0;
    protected int doorReplacementMeta = 0;
    // </structure data>

    public boolean isEntranceLocked() {
        return entranceLocked;
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

    public <T extends Entity & EnemyBoss> void notifyBossDead(T boss) {
        onDungeonRemoved(boss.world);
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

    protected abstract boolean placeDungeon(World world, Random random);

    protected abstract boolean canPlaceDungeon(World world);

    protected boolean generate(World world) {
        if (hasGenerated) return false;
        if (!canPlaceDungeon(world)) {
            DungeonMap.remove(this.id);
            return false;
        }

        Random rand = new Random(seed);
        hasGenerated = this.placeDungeon(world, rand);

        if (!hasGenerated) DungeonMap.remove(this.id);
        return hasGenerated;
    }

    public void remove(World world) {
        DungeonMap.remove(this.id);
    }

    public boolean isPlayerWithinBound(Player player) {
        return false;
    }

    public boolean canTick(World world) {
        return false;
    }

    protected void onTick(World world) {
    }

    protected void onDungeonRemoved(World world) {
        if (hasGenerated) {
            unlock(null, world);
            if (DungeonMap.dungeonMap.get(id) != null) {
                DungeonMap.dungeonMap.remove(id);
            }

            if (treasureDoor != null) {
                for (WorldFeaturePoint coordinate : treasureDoor) {
                    ParticleMaker.spawnParticle(world, "smoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0, 0);
                    ParticleMaker.spawnParticle(world, "largesmoke", coordinate.x, coordinate.y + 0.8F, coordinate.z, 0.0, 0.0, 0.0, 0);
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

    public CompoundTag saveStructureData(CompoundTag data) {
        data.putInt("doorReplacementID", this.doorReplacementID);
        data.putInt("doorReplacementMeta", this.doorReplacementMeta);
        data.putBoolean("entranceLocked", this.entranceLocked);

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

    public void loadStructureData(CompoundTag data) {
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
}
