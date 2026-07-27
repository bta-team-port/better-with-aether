package teamport.aether.world.feature.dungeon.bronze.component;

import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.pos.TilePos;
import org.jspecify.annotations.Nullable;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.dungeon.BlockLogicChestLocked;
import teamport.aether.block.dungeon.BlockLogicDungeonDoor;
import teamport.aether.block.dungeon.BlockLogicLocked;
import teamport.aether.world.feature.dungeon.bronze.WorldFeatureAetherBronzeDungeon;
import teamport.aether.world.feature.util.BlockPallet;
import teamport.aether.world.feature.util.WorldFeatureBlock;
import teamport.aether.world.feature.util.WorldFeatureComponent;
import teamport.aether.world.feature.util.WorldFeaturePoint;

import java.util.*;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.entity.monster.mimic.MobMimic.placeWallace;
import static teamport.aether.world.feature.dungeon.bronze.component.BaseBronzeRoom.ClosingType.OPEN;
import static teamport.aether.world.feature.util.WorldFeatureComponent.*;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;

public abstract class BaseBronzeRoom extends WorldFeature {
    protected World world;
    protected Random random;
    protected int x;
    protected int y;
    protected int z;
    protected int height;
    protected int width;
    protected int length;
    protected float airTolerance;
    protected float topAirTolerance;
    protected float bottomAirTolerance;
    protected float liquidTolerance;
    protected float topLiquidTolerance;
    protected float bottomLiquidTolerance;
    protected float roomWeight;
    protected WorldFeatureComponent room;
    protected WorldFeatureComponent decoration;
    protected WorldFeatureComponent chest;
    protected List<Door> doors;
    private boolean doorCoordinatesAdjusted = false;

    protected static final BlockPallet ROOM_PALLET = new BlockPallet();

    static {
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE.id(), 0, 85);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT.id(), 0, 5);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, 10);
    }

    protected static final BlockPallet CHEST_OR_MIMIC = new BlockPallet();

    static {
        CHEST_OR_MIMIC.addEntry(0, 1.5f);
        CHEST_OR_MIMIC.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 1);
        CHEST_OR_MIMIC.addEntry(AetherBlocks.CHEST_MIMIC_SKYROOT.id(), 1);
    }

    protected BaseBronzeRoom() {
        this.width = this.length = this.height = 12;
        this.airTolerance = this.liquidTolerance = 0.45F;
        this.topAirTolerance = this.topLiquidTolerance = 0.2F;
        this.bottomAirTolerance = this.bottomLiquidTolerance = 0.2F;
        this.roomWeight = 1.0F;
        this.room = new WorldFeatureComponent();
        this.chest = new WorldFeatureComponent();
        this.decoration = new WorldFeatureComponent();
        this.doors = new ArrayList<>();
    }

    public BaseBronzeRoom set(World world, Random random, int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.random = random;
        this.world = world;
        return this;
    }

    public WorldFeatureComponent getRoom() {
        return room;
    }

    public WorldFeatureComponent getChest() {
        return chest;
    }

    public List<Door> getAdjustedDoors() {
        if (doorCoordinatesAdjusted) return doors;
        return new ArrayList<>();
    }

    public void addDoor(Direction heading, WorldFeaturePoint p1, Direction direction1, int length1, Direction direction2, int length2) {
        WorldFeaturePoint p2 = new WorldFeaturePoint(p1.getX(), p1.getY(), p1.getZ());
        p2.add(direction1.offsetX() * length1, direction1.offsetY() * length1, direction1.offsetZ() * length1);
        p2.add(direction2.offsetX() * length2, direction2.offsetY() * length2, direction2.offsetZ() * length2);
        this.addDoor(heading, p1, p2);
    }

    public void addDoor(Direction direction, WorldFeaturePoint p1, WorldFeaturePoint p2) {
        this.doors.add(new Door(direction, p1, p2));
    }

    @Override
    public final boolean place(World world, Random random, int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.random = random;
        this.world = world;
        if (!canPlace()) return false;
        this.adjustDoorCoordinates();
        this.makeRoom();
        this.placeRoom();
        return true;
    }

    public boolean canPlace() {
        // checking worldHeight
        if (this.y <= 11 && this.y + this.height + 3 >= this.world.getHeightBlocks()) {
            return false;
        }
        WorldFeatureComponent check;
        int countAir = 0;
        int countLiquid = 0;

        // checking top & bottom surface
        check = drawPlane(0, 0, SOUTH, this.width, EAST, this.length, this.x, this.y + this.height, this.z, true);
        for (WorldFeaturePoint point : check.getBlockList()) {
            Block<?> block = world.getBlock(point.getX(), point.getY(), point.getZ());
            Material blockMaterial = block == null ? Materials.AIR : block.getMaterial();
            if (block != null && block.blockHardness < 0) return false;
            if (blockMaterial == Materials.AIR) countAir++;
            if (blockMaterial.isLiquid()) countLiquid++;
        }
        if (check.getBlockList().size() * this.topAirTolerance < countAir || check.getBlockList().size() * this.topLiquidTolerance < countLiquid) {
            return false;
        }

        check = drawPlane(0, 0, SOUTH, this.width, EAST, this.length, this.x, this.y, this.z, true);
        countAir = countLiquid = 0;
        for (WorldFeaturePoint point : check.getBlockList()) {
            Block<?> block = this.world.getBlock(point.getX(), point.getY(), point.getZ());
            Material blockMaterial = block == null ? Materials.AIR : block.getMaterial();
            if (block != null && block.blockHardness < 0) return false;
            if (blockMaterial == Materials.AIR) countAir++;
            if (blockMaterial.isLiquid()) countLiquid++;
        }
        if (check.getBlockList().size() * this.bottomAirTolerance < countAir || check.getBlockList().size() * this.bottomLiquidTolerance < countLiquid) {
            return false;
        }

        // checking volume
        check = drawVolume(0, 0, SOUTH, this.width, UP, this.height, EAST, this.length, this.x, this.y, this.z, true);
        countAir = countLiquid = 0;
        for (WorldFeaturePoint point : check.getBlockList()) {
            Block<?> block = this.world.getBlock(point.getX(), point.getY(), point.getZ());
            Material blockMaterial = block == null ? Materials.AIR : block.getMaterial();
            if (block != null && block.blockHardness < 0) return false;
            if (blockMaterial == Materials.AIR) countAir++;
            if (blockMaterial.isLiquid()) countLiquid++;
        }
        return check.getBlockList().size() * this.airTolerance >= countAir && check.getBlockList().size() * this.liquidTolerance >= countLiquid;
    }

    public void adjustDoorCoordinates() {
        for (Door door : doors) {
            door.p1.add(this.x, this.y, this.z);
            door.p2.add(this.x, this.y, this.z);
        }
        this.doorCoordinatesAdjusted = true;
    }

    public abstract void makeRoom();

    public void placeRoom() {
        Map<WorldFeaturePoint, WorldFeatureBlock> blockMap = new HashMap<>();
        for (WorldFeatureBlock block : this.room.getBlockList()) {
            WorldFeaturePoint point = new WorldFeaturePoint(block.getX(), block.getY(), block.getZ());
            blockMap.put(point, block);
        }
        this.decoration.add(this.chest);
        for (WorldFeatureBlock block : this.decoration.getBlockList()) {
            WorldFeatureBlock otherBlock = blockMap.computeIfAbsent(wfp(block.getX(), block.getY(), block.getZ()), key -> block);
            otherBlock.setBlockId(block.getBlockId());
            otherBlock.setMetadata(block.getMetadata());
            otherBlock.setWithNotify(block.isWithNotify());
        }
        for (WorldFeatureBlock wfblock : blockMap.values()) {
            if (roomCanReplace(this.world, wfblock)) {
                wfblock.place(this.world);
            }
        }
        for (WorldFeatureBlock wfblock : this.chest.getBlockList()) {
            BlockLogicChest.setDefaultDirection(this.world, new TilePos(wfblock.getX(), wfblock.getY(), wfblock.getZ()));
            populateChest(this.world, this.random, wfblock, WorldFeatureAetherBronzeDungeon::generateLoot);

            if (this.world.rand.nextInt(256) == 0 && wfblock.getBlockId() == AetherBlocks.CHEST_MIMIC_SKYROOT.id()) {
                placeWallace(this.world, wfblock.getX(), wfblock.getY(), wfblock.getZ());
            }
        }
    }

    public static boolean roomCanReplace(World world, WorldFeatureBlock wfblock) {
        Block<?> block = world.getBlock(wfblock.getX(), wfblock.getY(), wfblock.getZ());
        int blockID = block == null ? 0 : block.id();
        Material blockMaterial = blockID == 0 ? Materials.AIR : block.getMaterial();
        if (block != null) {
            BlockLogic logic = block.getLogic();
            if (
                logic instanceof BlockLogicLocked
                    || logic instanceof BlockLogicMobSpawner
                    || logic instanceof BlockLogicChestLocked
                    || logic instanceof BlockLogicDungeonDoor
            ) {
                return false;
            }

            if (block.blockHardness < 0) {
                return false;
            }
        }

        if (blockMaterial == Materials.WATER || blockMaterial == Materials.LAVA) {
            return false;
        }

        if (blockID == Blocks.SPIKES.id()) {
            return true;
        }

        if (blockID == AetherBlocks.CHEST_MIMIC_OAK.id() || blockID == AetherBlocks.CHEST_PLANKS_SKYROOT.id()) {
            world.removeBlockTileEntity(wfblock.getX(), wfblock.getY(), wfblock.getZ());
            return true;
        }
        return BlockTags.CAVES_CUT_THROUGH.appliesTo(block)
            || blockMaterial == Materials.GRASS
            || blockMaterial == Materials.DIRT
            || blockMaterial == Materials.MARBLE
            || blockMaterial == Materials.MOSS
            || blockMaterial.isStone();
    }

    public List<Door> getAvailableDoors() {
        List<Door> freeDoors = new ArrayList<>();
        for (Door door : doors) {
            if (door.mark == OPEN) {
                freeDoors.add(door);
            }
        }
        return freeDoors;
    }

    public void markDoor(@Nullable Door door, ClosingType closingType) {
        if (door == null) return;
        door.mark = closingType;
    }

    public List<WorldFeaturePoint> getAnchors(WorldFeaturePoint doorPoint, Direction heading) {
        List<WorldFeaturePoint> list = new ArrayList<>();
        for (Door door : doors) {
            if (door.heading == heading.opposite()) {
                list.add(new WorldFeaturePoint(
                    doorPoint.getX() - door.p1.getX(),
                    doorPoint.getY() - door.p1.getY(),
                    doorPoint.getZ() - door.p1.getZ()
                ));
            }
        }
        return list;
    }

    public Door getDoor(WorldFeaturePoint nextDoor) {
        for (Door door : doors) {
            if (door.p1.equals(nextDoor) || door.p2.equals(nextDoor)) {
                return door;
            }
        }
        return null;
    }

    public boolean intercept(WorldFeaturePoint point) {
        return (point.getX() <= this.x + length && point.getX() >= this.x)
            && (point.getY() <= this.y + height && point.getY() >= this.y)
            && (point.getZ() <= this.z + width && point.getZ() >= this.z);
    }

    public boolean intercept(WorldFeaturePoint point, BaseBronzeRoom room) {
        return (point.getX() <= this.x + length && point.getX() + room.length >= this.x)
            && (point.getY() <= this.y + height && point.getY() + room.height >= this.y)
            && (point.getZ() <= this.z + width && point.getZ() + room.width >= this.z);
    }

    public static class Door {
        private final Direction heading;
        private final WorldFeaturePoint p1;
        private final WorldFeaturePoint p2;
        private ClosingType mark;

        Door(Direction heading, WorldFeaturePoint p1, WorldFeaturePoint p2) {
            this.heading = heading;
            this.p1 = p1;
            this.p2 = p2;
            this.mark = OPEN;
        }

        public static Door door(Direction heading, WorldFeaturePoint p1, WorldFeaturePoint p2) {
            return new Door(heading, p1, p2);
        }

        @Override
        public String toString() {
            return String.format("(%s, %s, %s, %s)", heading, p1, p2, mark);
        }

        @Override
        public int hashCode() {
            return Objects.hash(heading, p1.hashCode(), p2.hashCode(), mark);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (!(o instanceof Door)) return false;
            Door d = (Door) o;
            return this.heading.equals(d.heading) && this.p1.equals(d.p1) && this.p2.equals(d.p2);
        }

        public Door copy() {
            return new Door(this.heading, this.p1.copy(), this.p2.copy());
        }
        public Direction getHeading() {
            return heading;
        }
        public WorldFeaturePoint getP1() {
            return p1;
        }
        public WorldFeaturePoint getP2() {
            return p2;
        }
        public ClosingType getMark() {
            return mark;
        }
        @SuppressWarnings("SameParameterValue")
        protected void setMark(ClosingType mark) {
            this.mark = mark;
        }
    }

    public enum ClosingType {
        INTERCEPT,
        NO_SPACE,
        ROOM_LOCKED,
        OPEN,
        PLACED
    }
    public float getRoomWeight() {
        return roomWeight;
    }
}
