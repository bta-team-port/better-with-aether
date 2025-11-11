package teamport.aether.world.feature.dungeon.bronze.component;

import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import org.jspecify.annotations.Nullable;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.dungeon.BlockLogicChestLocked;
import teamport.aether.blocks.dungeon.BlockLogicDungeonDoor;
import teamport.aether.blocks.dungeon.BlockLogicLocked;
import teamport.aether.blocks.dungeon.BlockLogicPaintedChestMimic;
import teamport.aether.entity.tile.TileEntityMimic;
import teamport.aether.world.feature.util.BlockPallet;
import teamport.aether.world.feature.util.WorldFeatureBlock;
import teamport.aether.world.feature.util.WorldFeatureComponent;
import teamport.aether.world.feature.util.WorldFeaturePoint;
import teamport.aether.world.feature.dungeon.bronze.WorldFeatureAetherBronzeDungeon;

import java.util.*;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.feature.util.WorldFeatureComponent.*;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;
import static teamport.aether.world.feature.dungeon.bronze.component.BaseBronzeRoom.ClosingType.OPEN;

public abstract class BaseBronzeRoom extends WorldFeature {
    public World world;
    public Random random;
    public int x;
    public int y;
    public int z;
    public int height;
    public int width;
    public int length;
    public float airTolerance;
    public float topAirTolerance;
    public float bottomAirTolerance;
    public float liquidTolerance;
    public float topLiquidTolerance;
    public float bottomLiquidTolerance;
    public float roomWeight;
    protected WorldFeatureComponent room;
    protected WorldFeatureComponent decoration;
    protected WorldFeatureComponent chest;
    protected List<Door> doors;
    private boolean doorCoordinatesAdjusted = false;

    public static BlockPallet ROOM_PALLET = new BlockPallet();

    static {
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE.id(), 0, 85);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT.id(), 0, 5);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, 10);
    }

    public static BlockPallet chestOrMimic = new BlockPallet();

    static {
        chestOrMimic.addEntry(0, 1.5f);
        chestOrMimic.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 1);
        chestOrMimic.addEntry(AetherBlocks.CHEST_MIMIC_SKYROOT.id(), 1);
    }

    public BaseBronzeRoom() {
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

    public BaseBronzeRoom setCoords(WorldFeaturePoint p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
        return this;
    }

    public BaseBronzeRoom setCoords(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
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

    public WorldFeatureComponent getDecoration() {
        return decoration;
    }

    public WorldFeatureComponent getChest() {
        return chest;
    }

    public List<Door> getAdjustedDoors() {
        if (doorCoordinatesAdjusted) return doors;
        return new ArrayList<>();
    }

    public List<Door> getDoors() {
        if (doorCoordinatesAdjusted) {
            WorldFeaturePoint point = wfp(this.x, this.y, this.z);
            List<Door> doorList = new ArrayList<>();
            for (Door d : doors) {
                Door copy = d.copy();
                copy.p1.subtract(point);
                copy.p2.subtract(point);
                doorList.add(copy);
            }
            return doorList;
        }
        return doors;
    }


    public void addDoor(Direction heading, WorldFeaturePoint p1, Direction direction1, int length1, Direction direction2, int length2) {
        WorldFeaturePoint p2 = new WorldFeaturePoint(p1.x, p1.y, p1.z);
        p2.add(direction1.getOffsetX() * length1, direction1.getOffsetY() * length1, direction1.getOffsetZ() * length1);
        p2.add(direction2.getOffsetX() * length2, direction2.getOffsetY() * length2, direction2.getOffsetZ() * length2);
        this.addDoor(heading, p1, p2);
    }

    public void addDoor(Direction direction, WorldFeaturePoint p1, WorldFeaturePoint p2) {
        this.doors.add(new Door(direction, p1, p2));
    }

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
        if (this.y <= 11 && this.y + height + 3 >= world.getHeightBlocks()) {
            return false;
        }
        WorldFeatureComponent check;
        int countAir = 0, countLiquid = 0;

        // checking top & bottom surface
        check = drawPlane(0, 0, SOUTH, width, EAST, length, x, y + height, z, true);
        for (WorldFeaturePoint point : check.blockList) {
            Block<?> block = world.getBlock(point.x, point.y, point.z);
            Material blockMaterial = block == null ? Material.air : block.getMaterial();
            if (block != null && block.blockHardness < 0) return false;
            if (blockMaterial == Material.air) countAir++;
            if (blockMaterial.isLiquid()) countLiquid++;
        }
        if (check.blockList.size() * topAirTolerance < countAir || check.blockList.size() * topLiquidTolerance < countLiquid) {
            return false;
        }

        check = drawPlane(0, 0, SOUTH, width, EAST, length, x, y, z, true);
        countAir = countLiquid = 0;
        for (WorldFeaturePoint point : check.blockList) {
            Block<?> block = world.getBlock(point.x, point.y, point.z);
            Material blockMaterial = block == null ? Material.air : block.getMaterial();
            if (block != null && block.blockHardness < 0) return false;
            if (blockMaterial == Material.air) countAir++;
            if (blockMaterial.isLiquid()) countLiquid++;
        }
        if (check.blockList.size() * bottomAirTolerance < countAir || check.blockList.size() * bottomLiquidTolerance < countLiquid) {
            return false;
        }

        // checking volume
        check = drawVolume(0, 0, SOUTH, width, UP, height, EAST, length, x, y, z, true);
        countAir = countLiquid = 0;
        for (WorldFeaturePoint point : check.blockList) {
            Block<?> block = world.getBlock(point.x, point.y, point.z);
            Material blockMaterial = block == null ? Material.air : block.getMaterial();
            if (block != null && block.blockHardness < 0) return false;
            if (blockMaterial == Material.air) countAir++;
            if (blockMaterial.isLiquid()) countLiquid++;
        }
        return !(check.blockList.size() * airTolerance < countAir) && !(check.blockList.size() * liquidTolerance < countLiquid);
    }

    public void adjustDoorCoordinates() {
        for (Door door : doors) {
            door.p1.add(x, y, z);
            door.p2.add(x, y, z);
        }
        this.doorCoordinatesAdjusted = true;
    }

    public abstract void makeRoom();

    public void placeRoom() {
        Map<WorldFeaturePoint, WorldFeatureBlock> blockMap = new HashMap<>();
        for (WorldFeatureBlock block : room.blockList) {
            WorldFeaturePoint point = new WorldFeaturePoint(block.x, block.y, block.z);
            blockMap.put(point, block);
        }
        decoration.add(chest);
        for (WorldFeatureBlock block : decoration.blockList) {
            WorldFeatureBlock otherBlock = blockMap.computeIfAbsent(wfp(block.x, block.y, block.z), key -> block);
            otherBlock.blockID = block.blockID;
            otherBlock.metadata = block.metadata;
            otherBlock.withNotify = block.withNotify;
        }
        for (WorldFeatureBlock wfblock : blockMap.values()) {
            if (roomCanReplace(world, wfblock)) {
                wfblock.place(world);
            }
        }
        for (WorldFeatureBlock wfblock : this.chest.blockList) {
            BlockLogicChest.setDefaultDirection(world, wfblock.x, wfblock.y, wfblock.z);
            populateChest(world, random, wfblock, WorldFeatureAetherBronzeDungeon::generateLoot);

            if (
                    world.rand.nextInt(250) == 0
                            && wfblock.blockID == AetherBlocks.CHEST_MIMIC_SKYROOT.id()
            ) {
                BlockLogicPaintedChestMimic blockLogic = AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.getLogic();
                world.setBlockRaw(wfblock.x, wfblock.y, wfblock.z, AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.id());
                blockLogic.setColor(world, wfblock.x, wfblock.y, wfblock.z, DyeColor.PURPLE);
                ((TileEntityMimic) world.getTileEntity(wfblock.x, wfblock.y, wfblock.z)).setCustomName("Wallace", (byte) TextFormatting.PURPLE.id);
            }
        }
    }

    public static boolean roomCanReplace(World world, WorldFeatureBlock wfblock) {
        Block<?> block = world.getBlock(wfblock.x, wfblock.y, wfblock.z);
        int blockID = block == null ? 0 : block.id();
        Material blockMaterial = blockID == 0 ? Material.air : block.getMaterial();
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

        if (blockMaterial == Material.water || blockMaterial == Material.lava) {
            return false;
        }

        if (blockID == Blocks.SPIKES.id()) {
            return true;
        }

        if (blockID == AetherBlocks.CHEST_MIMIC_OAK.id() || blockID == AetherBlocks.CHEST_PLANKS_SKYROOT.id()) {
            world.removeBlockTileEntity(wfblock.x, wfblock.y, wfblock.z);
            return true;
        }
        return BlockTags.CAVES_CUT_THROUGH.appliesTo(block)
                || blockMaterial == Material.grass
                || blockMaterial == Material.dirt
                || blockMaterial == Material.marble
                || blockMaterial == Material.moss
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

    public List<WorldFeaturePoint> getAchors(WorldFeaturePoint doorPoint, Direction heading) {
        List<WorldFeaturePoint> list = new ArrayList<>();
        for (Door door : doors) {
            if (door.heading == heading.getOpposite()) {
                list.add(new WorldFeaturePoint(
                        doorPoint.x - door.p1.x,
                        doorPoint.y - door.p1.y,
                        doorPoint.z - door.p1.z
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
        return (point.x <= this.x + length && point.x >= this.x)
                && (point.y <= this.y + height && point.y >= this.y)
                && (point.z <= this.z + width && point.z >= this.z);
    }

    public boolean intercept(WorldFeaturePoint point, BaseBronzeRoom room) {
        return (point.x <= this.x + length && point.x + room.length >= this.x)
                && (point.y <= this.y + height && point.y + room.height >= this.y)
                && (point.z <= this.z + width && point.z + room.width >= this.z);
    }

    public static class Door {
        public Direction heading;
        public WorldFeaturePoint p1;
        public WorldFeaturePoint p2;
        //        public boolean mark;
        public ClosingType mark;

        Door(Direction heading, WorldFeaturePoint p1, WorldFeaturePoint p2) {
            this.heading = heading;
            this.p1 = p1;
            this.p2 = p2;
//            this.mark = false;
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
    }

    public enum ClosingType {
        INTERCEPT,
        NO_SPACE,
        ROOM_LOCKED,
        OPEN,
        PLACED
    }
}
