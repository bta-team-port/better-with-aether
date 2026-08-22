package teamport.aether.world.feature.dungeon.silver.component;

import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.DyeColor;
import org.jspecify.annotations.NonNull;
import teamport.aether.AetherMod;
import teamport.aether.block.AetherBlocks;
import teamport.aether.world.feature.util.BlockPallet;
import teamport.aether.world.feature.util.MazeHelper;
import teamport.aether.world.feature.util.WorldFeatureComponent;

import java.util.*;

import static teamport.aether.world.feature.dungeon.silver.WorldFeatureAetherSilverDungeon.ANGELIC;
import static teamport.aether.world.feature.util.MetadataHelper.*;
import static teamport.aether.world.feature.util.WorldFeatureBlock.wfb;
import static teamport.aether.world.feature.util.WorldFeatureComponent.*;

public class WorldFeatureSilverMaze {
    private static final int ENTRANCE = 1;
    private static final int ROOM_WIDTH = 7;
    private static final int ROOM_HEIGHT = 5;
    private static final int ROOM_COUNT = 27;
    private WorldFeatureComponent rooms;
    private WorldFeatureComponent chests;
    private WorldFeatureComponent doors;
    private Map<Integer, List<Integer>> spanningTree;
    private Random random;
    private Map<Integer, List<Integer>> graph;

    private static final BlockPallet ANGELIC_ROOM = new BlockPallet();

    static {
        ANGELIC_ROOM.addEntry(AetherBlocks.CARVED_ANGELIC_LOCKED.id(), 0, 85);
        ANGELIC_ROOM.addEntry(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED.id(), 0, 5);
        ANGELIC_ROOM.addEntry(AetherBlocks.CARVED_ANGELIC_TRAPPED_LOCKED.id(), 0, 10);
    }

    private static final BlockPallet CHEST_OR_MIMIC = new BlockPallet();

    static {
        CHEST_OR_MIMIC.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED.id(), DyeColor.SILVER.blockMeta << 4, 3);
        CHEST_OR_MIMIC.addEntry(AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.id(), DyeColor.SILVER.blockMeta << 4, 3);
        CHEST_OR_MIMIC.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED.id(), DyeColor.YELLOW.blockMeta << 4, 1);
        CHEST_OR_MIMIC.addEntry(AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.id(), DyeColor.YELLOW.blockMeta << 4, 1);
    }

    private static final BlockPallet CHEST_OR_MIMIC_ALT = new BlockPallet();

    static {
        CHEST_OR_MIMIC_ALT.addEntry(0, 4);
        CHEST_OR_MIMIC_ALT.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED.id(), DyeColor.SILVER.blockMeta << 4, 3);
        CHEST_OR_MIMIC_ALT.addEntry(AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.id(), DyeColor.SILVER.blockMeta << 4, 3);
        CHEST_OR_MIMIC_ALT.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED.id(), DyeColor.YELLOW.blockMeta << 4, 1);
        CHEST_OR_MIMIC_ALT.addEntry(AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.id(), DyeColor.YELLOW.blockMeta << 4, 1);
    }

    private static final BlockPallet CHEST_OR_MIMIC_GARDEN = new BlockPallet();

    static {
        CHEST_OR_MIMIC_GARDEN.addEntry(0, 3);
        CHEST_OR_MIMIC_GARDEN.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED.id(), DyeColor.GREEN.blockMeta << 4, 1.5f);
        CHEST_OR_MIMIC_GARDEN.addEntry(AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.id(), DyeColor.GREEN.blockMeta << 4, 1.5f);
        CHEST_OR_MIMIC_GARDEN.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED.id(), DyeColor.LIME.blockMeta << 4, 1.5f);
        CHEST_OR_MIMIC_GARDEN.addEntry(AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.id(), DyeColor.LIME.blockMeta << 4, 1.5f);
    }

    private static final BlockPallet GARDEN_DECO = new BlockPallet();

    static {
        GARDEN_DECO.addEntry(0, 20);
        GARDEN_DECO.addEntry(AetherBlocks.FLOWER_PURPLE.id(), 128, 1);
        GARDEN_DECO.addEntry(AetherBlocks.FLOWER_PURPLE.id(), 160, 1);
        GARDEN_DECO.addEntry(AetherBlocks.FLOWER_PURPLE.id(), 192, 1);
        GARDEN_DECO.addEntry(AetherBlocks.FLOWER_PURPLE.id(), 224, 1);
        GARDEN_DECO.addEntry(AetherBlocks.FLOWER_WHITE.id(), 128, 1);
        GARDEN_DECO.addEntry(AetherBlocks.FLOWER_WHITE.id(), 160, 1);
        GARDEN_DECO.addEntry(AetherBlocks.FLOWER_WHITE.id(), 192, 1);
        GARDEN_DECO.addEntry(AetherBlocks.FLOWER_WHITE.id(), 224, 1);
        GARDEN_DECO.addEntry(AetherBlocks.TALLGRASS_AETHER.id(), 128, 4);
        GARDEN_DECO.addEntry(AetherBlocks.SAPLING_SKYROOT.id(), 224, 4);
        GARDEN_DECO.addEntry(AetherBlocks.SAPLING_OAK_GOLDEN.id(), 224, 4);
    }


    /// Graph
    private static final Map<Integer, List<Integer>> STATIC_GRAPH = new HashMap<>();


    /// I am not going to generate this because it easier and safer to just write it down
    /// this graph is missing the staircases that are added later on so that they won't pollute the maze
    static {
        STATIC_GRAPH.put(0, new ArrayList<>(Arrays.asList(1, 3)));
        STATIC_GRAPH.put(1, new ArrayList<>(Arrays.asList(0, 2, 4)));
        STATIC_GRAPH.put(2, new ArrayList<>(Arrays.asList(1, 5)));
        STATIC_GRAPH.put(3, new ArrayList<>(Arrays.asList(0, 4, 6)));
        STATIC_GRAPH.put(4, new ArrayList<>(Arrays.asList(1, 3, 5, 7)));
        STATIC_GRAPH.put(5, new ArrayList<>(Arrays.asList(2, 4)));
        STATIC_GRAPH.put(6, new ArrayList<>(Arrays.asList(3, 7)));
        STATIC_GRAPH.put(7, new ArrayList<>(Arrays.asList(4, 6)));
        STATIC_GRAPH.put(8, new ArrayList<>(Collections.singletonList(17)));
        STATIC_GRAPH.put(9, new ArrayList<>(Arrays.asList(10, 12)));
        STATIC_GRAPH.put(10, new ArrayList<>(Arrays.asList(9, 11, 13)));
        STATIC_GRAPH.put(11, new ArrayList<>(Arrays.asList(10, 14)));
        STATIC_GRAPH.put(12, new ArrayList<>(Arrays.asList(9, 13, 15)));
        STATIC_GRAPH.put(13, new ArrayList<>(Arrays.asList(10, 12, 14, 16)));
        STATIC_GRAPH.put(14, new ArrayList<>(Arrays.asList(11, 13)));
        STATIC_GRAPH.put(15, new ArrayList<>(Arrays.asList(12, 16)));
        STATIC_GRAPH.put(16, new ArrayList<>(Arrays.asList(13, 15)));
        STATIC_GRAPH.put(17, new ArrayList<>(Arrays.asList(8, 26)));
        STATIC_GRAPH.put(18, new ArrayList<>(Arrays.asList(19, 21)));
        STATIC_GRAPH.put(19, new ArrayList<>(Arrays.asList(18, 20, 22)));
        STATIC_GRAPH.put(20, new ArrayList<>(Arrays.asList(19, 23)));
        STATIC_GRAPH.put(21, new ArrayList<>(Arrays.asList(18, 22, 24)));
        STATIC_GRAPH.put(22, new ArrayList<>(Arrays.asList(19, 21, 23, 25)));
        STATIC_GRAPH.put(23, new ArrayList<>(Arrays.asList(20, 22, 26)));
        STATIC_GRAPH.put(24, new ArrayList<>(Arrays.asList(21, 25)));
        STATIC_GRAPH.put(25, new ArrayList<>(Arrays.asList(22, 24, 26)));
        STATIC_GRAPH.put(26, new ArrayList<>(Arrays.asList(17, 23, 25)));
    }

    public void addAdditionalStaircase(Random random) {
        int prev = -1;
        for (int LEVEL = 1; LEVEL >= 0; LEVEL--) {
            int staircaseAmount = random.nextInt(2) + 1;
            for (int i = staircaseAmount; i > 0; i--) {
                int index = random.nextInt(8);
                if (index == 1 && LEVEL == 0) index++;
                if (prev == index) index++;
                if (index > 7) index = index % 7;
                prev = index;
                this.graph.get(LEVEL * 9 + index).add((LEVEL + 1) * 9 + index);
                this.graph.get((LEVEL + 1) * 9 + index).add(LEVEL * 9 + index);
            }
        }
    }

    @SuppressWarnings("java:S2234")
    public void createMaze(Random random, int x, int y, int z) {
        this.random = random;
        this.rooms = new WorldFeatureComponent();
        this.doors = new WorldFeatureComponent();
        this.chests = new WorldFeatureComponent();
        this.graph = new HashMap<>(STATIC_GRAPH);
        this.addAdditionalStaircase(random);
        List<IntIntPair> edges = MazeHelper.randomMazeKruskal(STATIC_GRAPH, 27);
        this.spanningTree = MazeHelper.makeGraph(edges);

        boolean[] generated = new boolean[ROOM_COUNT];
        for (IntIntPair edge : edges) {
            int to = edge.firstInt();
            int from = edge.secondInt();
            generated[to] = true;
            this.createRoomMaze(to, from, x, y, z);
            if (!generated[from]) {
                generated[from] = true;
                this.createRoomMaze(from, to, x, y, z);
            }
        }
        this.rooms.add(new WorldFeatureComponent());
        this.rooms.add(this.doors);
    }

    @SuppressWarnings("java:S131")
    public void createRoomMaze(int to, int from, int x, int y, int z) {
        int levelCurrent = to / 9;
        int columnCurrent = (to - levelCurrent * 9) / 3;
        int rowCurrent = to - levelCurrent * 9 - columnCurrent * 3;

        int roomX = x - 4 - ROOM_WIDTH * rowCurrent;
        int roomY = y + ROOM_HEIGHT * levelCurrent;
        int roomZ = z + 4 + ROOM_WIDTH * columnCurrent;

        Direction doorDirection = this.getDoorDirection(to, from);

        if (doorDirection == Direction.NONE) {
            AetherMod.LOGGER.error("SilverMazeRoom failed to generate a room at x:{}, y:{},  z:{},INDEX:{}, LEVEl:{}, COLUMN:{}, ROW:{}", x, y, z, to, levelCurrent, columnCurrent, rowCurrent);
            return;
        }
        if (doorDirection == Direction.UP) {
            this.createStaircase(roomX, roomY, roomZ);
            return;
        }
        if (doorDirection == Direction.DOWN) {
            this.createRoom(roomX, roomY, roomZ, doorDirection);
            return;
        }
        if (to == ENTRANCE) {
            this.createHallway(roomX, roomY, roomZ, doorDirection);
            return;
        }
        if (this.spanningTree.get(to).size() > 2) {
            List<Integer> neighbor = this.spanningTree.get(to);
            int count = 0;
            for (int id : neighbor) {
                if (this.getDoorDirection(to, id).isHorizontal()) {
                    count++;
                }
            }
            if (count == neighbor.size()) {
                this.createCorridor(roomX, roomY, roomZ, doorDirection);
                return;
            }
        }
        if (this.spanningTree.get(to).size() == 1) {
            int index = this.random.nextInt(4);
            switch (index) {
                case 0:
                    this.createTreasureRoom(roomX, roomY, roomZ, doorDirection);
                    return;
                case 1:
                    this.createSleepingChambers(roomX, roomY, roomZ, doorDirection);
                    return;
                case 2:
                    this.createGardenRoom(roomX, roomY, roomZ, doorDirection);
                    return;
                case 3:
                    this.createCorridor(roomX, roomY, roomZ, doorDirection);
                    return;
            }
        }
        this.createRoom(roomX, roomY, roomZ, doorDirection);
    }

    private void createCorridor(int x, int y, int z, Direction doorDirection) {
        this.createHallway(x, y, z, doorDirection);
        this.rooms.add(wfb(x - 1, y + 1, z + 1, ANGELIC_ROOM.getRandom(this.random), false));
        this.rooms.add(wfb(x - 6, y + 1, z + 1, ANGELIC_ROOM.getRandom(this.random), false));
        this.rooms.add(wfb(x - 1, y + 1, z + 6, ANGELIC_ROOM.getRandom(this.random), false));
        this.rooms.add(wfb(x - 6, y + 1, z + 6, ANGELIC_ROOM.getRandom(this.random), false));

        this.chests.add(wfb(x - 1, y + 2, z + 1, CHEST_OR_MIMIC_ALT.getRandom(this.random), false));
        this.chests.add(wfb(x - 6, y + 2, z + 1, CHEST_OR_MIMIC_ALT.getRandom(this.random), false));
        this.chests.add(wfb(x - 1, y + 2, z + 6, CHEST_OR_MIMIC_ALT.getRandom(this.random), false));
        this.chests.add(wfb(x - 6, y + 2, z + 6, CHEST_OR_MIMIC_ALT.getRandom(this.random), false));
    }


    public void createHallway(int x, int y, int z, @NonNull Direction doorDirection) {
        rooms.add(drawShell(random, ANGELIC_ROOM, Direction.SOUTH, 8, Direction.UP, 6, Direction.WEST, 8, x, y, z, false));
        // only generate door in horizontal direction
        switch (doorDirection) {
            case NORTH:
                this.doors.add(drawPlane(0, 0, Direction.UP, 2, Direction.WEST, 2, x - 3, y + 1, z, false));
                break;
            case EAST:
                this.doors.add(drawPlane(0, 0, Direction.UP, 2, Direction.SOUTH, 2, x, y + 1, z + 3, false));
                break;
            case WEST:
                this.doors.add(drawPlane(0, 0, Direction.UP, 2, Direction.SOUTH, 2, x - 7, y + 1, z + 3, false));
                break;
            case SOUTH:
            default:
                this.doors.add(drawPlane(0, 0, Direction.UP, 2, Direction.WEST, 2, x - 3, y + 1, z + 7, false));
                break;
        }
    }

    public void createRoom(int x, int y, int z, Direction doorDirection) {
        this.createHallway(x, y, z, doorDirection);
        this.rooms.add(drawPlane(random, ANGELIC, Direction.SOUTH, 2, Direction.WEST, 2, x - 3, y + 1, z + 3, false));
        this.createChests(x, y, z);
    }

    public void createTreasureRoom(int x, int y, int z, Direction doorDirection) {
        this.createHallway(x, y, z, doorDirection);
        // places chests
        for (int i = 0; i < Direction.horizontal.length; i++) {

            Direction dir = Direction.horizontal[i];
            if (dir == doorDirection) {
                continue;
            }
            switch (dir) {
                case EAST:
                    this.rooms.add(drawLine(random, ANGELIC, Direction.SOUTH, 2, x - 1, y + 1, z + 3, false));
                    this.chests.add(wfb(x - 1, y + 2, z + 3, CHEST_OR_MIMIC.getRandom(random), false));
                    this.chests.add(wfb(x - 1, y + 2, z + 4, CHEST_OR_MIMIC.getRandom(random), false));
                    break;
                case WEST:
                    this.rooms.add(drawLine(random, ANGELIC, Direction.SOUTH, 2, x - 6, y + 1, z + 3, false));
                    this.chests.add(wfb(x - 6, y + 2, z + 3, CHEST_OR_MIMIC.getRandom(random), false));
                    this.chests.add(wfb(x - 6, y + 2, z + 4, CHEST_OR_MIMIC.getRandom(random), false));
                    break;
                case NORTH:
                    this.rooms.add(drawLine(random, ANGELIC, Direction.WEST, 2, x - 3, y + 1, z + 1, false));
                    this.chests.add(wfb(x - 3, y + 2, z + 1, CHEST_OR_MIMIC.getRandom(random), false));
                    this.chests.add(wfb(x - 4, y + 2, z + 1, CHEST_OR_MIMIC.getRandom(random), false));
                    break;
                case SOUTH:
                    this.rooms.add(drawLine(random, ANGELIC, Direction.WEST, 2, x - 3, y + 1, z + 6, false));
                    this.chests.add(wfb(x - 3, y + 2, z + 6, CHEST_OR_MIMIC.getRandom(random), false));
                    this.chests.add(wfb(x - 4, y + 2, z + 6, CHEST_OR_MIMIC.getRandom(random), false));
                    break;
                default:
                    break;
            }
        }

        // places decorations
        this.rooms.add(wfb(x - 1, y + 1, z + 1, AetherBlocks.CARVED_ANGELIC.id(), 0, false));
        this.rooms.add(wfb(x - 1, y + 2, z + 1, AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED.id(), DyeColor.WHITE.blockMeta << 4, true));
        this.rooms.add(wfb(x - 1, y + 3, z + 1, Blocks.GLOWSTONE.id(), 0, false));

        this.rooms.add(wfb(x - 1, y + 1, z + 6, AetherBlocks.CARVED_ANGELIC.id(), 0, false));
        this.rooms.add(wfb(x - 1, y + 2, z + 6, AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED.id(), DyeColor.WHITE.blockMeta << 4, true));
        this.rooms.add(wfb(x - 1, y + 3, z + 6, Blocks.GLOWSTONE.id(), 0, false));

        this.rooms.add(wfb(x - 6, y + 1, z + 1, AetherBlocks.CARVED_ANGELIC.id(), 0, false));
        this.rooms.add(wfb(x - 6, y + 2, z + 1, AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED.id(), DyeColor.WHITE.blockMeta << 4, true));
        this.rooms.add(wfb(x - 6, y + 3, z + 1, Blocks.GLOWSTONE.id(), 0, false));

        this.rooms.add(wfb(x - 6, y + 1, z + 6, AetherBlocks.CARVED_ANGELIC.id(), 0, false));
        this.rooms.add(wfb(x - 6, y + 2, z + 6, AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED.id(), DyeColor.WHITE.blockMeta << 4, true));
        this.rooms.add(wfb(x - 6, y + 3, z + 6, Blocks.GLOWSTONE.id(), 0, false));
    }

    private void createGardenRoom(int x, int y, int z, Direction doorDirection) {
        this.createHallway(x, y, z, doorDirection);
        for (int i = 0; i < Direction.horizontal.length; i++) {
            Direction dir = Direction.horizontal[i];
            if (dir == doorDirection) {
                continue;
            }
            switch (dir) {
                case EAST:
                    this.rooms.add(drawLine(AetherBlocks.GRASS_AETHER.id(), 0, Direction.SOUTH, 2, x - 1, y + 1, z + 3, false));
                    this.rooms.add(drawLine(AetherBlocks.LEAVES_SKYROOT.id(), 0, Direction.SOUTH, 2, x - 1, y + 4, z + 3, false));
                    this.rooms.add(drawLine(random, GARDEN_DECO, Direction.SOUTH, 2, x - 1, y + 2, z + 3, false));

                    this.rooms.add(wfb(x - 1, y + 4, z + 2, AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED.id(), getMetadataFromDyeAndDirection(DyeColor.GREEN, dir)));
                    this.rooms.add(wfb(x - 1, y + 4, z + 5, AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED.id(), getMetadataFromDyeAndDirection(DyeColor.GREEN, dir)));

                    this.rooms.add(wfb(x - 1, y + 1, z + 2, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, Direction.NORTH)));
                    this.rooms.add(wfb(x - 2, y + 1, z + 3, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, dir.opposite())));
                    this.rooms.add(wfb(x - 2, y + 1, z + 4, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, dir.opposite())));
                    this.rooms.add(wfb(x - 1, y + 1, z + 5, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, Direction.SOUTH)));

                    break;
                case WEST:
                    this.rooms.add(drawLine(AetherBlocks.GRASS_AETHER.id(), 0, Direction.SOUTH, 2, x - 6, y + 1, z + 3, false));
                    this.rooms.add(drawLine(AetherBlocks.LEAVES_SKYROOT.id(), 0, Direction.SOUTH, 2, x - 6, y + 4, z + 3, false));
                    this.rooms.add(drawLine(random, GARDEN_DECO, Direction.SOUTH, 2, x - 6, y + 2, z + 3, false));

                    this.rooms.add(wfb(x - 6, y + 4, z + 2, AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED.id(), getMetadataFromDyeAndDirection(DyeColor.GREEN, dir)));
                    this.rooms.add(wfb(x - 6, y + 4, z + 5, AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED.id(), getMetadataFromDyeAndDirection(DyeColor.GREEN, dir)));

                    this.rooms.add(wfb(x - 6, y + 1, z + 2, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, Direction.NORTH)));
                    this.rooms.add(wfb(x - 5, y + 1, z + 3, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, dir.opposite())));
                    this.rooms.add(wfb(x - 5, y + 1, z + 4, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, dir.opposite())));
                    this.rooms.add(wfb(x - 6, y + 1, z + 5, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, Direction.SOUTH)));
                    break;
                case NORTH:
                    this.rooms.add(drawLine(AetherBlocks.GRASS_AETHER.id(), 0, Direction.WEST, 2, x - 3, y + 1, z + 1, false));
                    this.rooms.add(drawLine(AetherBlocks.LEAVES_SKYROOT.id(), 0, Direction.WEST, 2, x - 3, y + 4, z + 1, false));
                    this.rooms.add(drawLine(random, GARDEN_DECO, Direction.WEST, 2, x - 3, y + 2, z + 1, false));

                    this.rooms.add(wfb(x - 2, y + 4, z + 1, AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED.id(), getMetadataFromDyeAndDirection(DyeColor.GREEN, dir)));
                    this.rooms.add(wfb(x - 5, y + 4, z + 1, AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED.id(), getMetadataFromDyeAndDirection(DyeColor.GREEN, dir)));

                    this.rooms.add(wfb(x - 2, y + 1, z + 1, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, Direction.EAST)));
                    this.rooms.add(wfb(x - 4, y + 1, z + 2, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, dir.opposite())));
                    this.rooms.add(wfb(x - 3, y + 1, z + 2, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, dir.opposite())));
                    this.rooms.add(wfb(x - 5, y + 1, z + 1, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, Direction.WEST)));
                    break;
                case SOUTH:
                default:
                    this.rooms.add(drawLine(AetherBlocks.GRASS_AETHER.id(), 0, Direction.WEST, 2, x - 3, y + 1, z + 6, false));
                    this.rooms.add(drawLine(AetherBlocks.LEAVES_SKYROOT.id(), 0, Direction.WEST, 2, x - 3, y + 4, z + 6, false));
                    this.rooms.add(drawLine(random, GARDEN_DECO, Direction.WEST, 2, x - 3, y + 2, z + 6, false));

                    this.rooms.add(wfb(x - 2, y + 4, z + 6, AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED.id(), getMetadataFromDyeAndDirection(DyeColor.GREEN, Direction.SOUTH)));
                    this.rooms.add(wfb(x - 5, y + 4, z + 6, AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED.id(), getMetadataFromDyeAndDirection(DyeColor.GREEN, Direction.SOUTH)));

                    this.rooms.add(wfb(x - 2, y + 1, z + 6, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, Direction.EAST)));
                    this.rooms.add(wfb(x - 3, y + 1, z + 5, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, dir.opposite())));
                    this.rooms.add(wfb(x - 4, y + 1, z + 5, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, dir.opposite())));
                    this.rooms.add(wfb(x - 5, y + 1, z + 6, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.WHITE, false, true, Direction.WEST)));
                    break;
            }
            this.placeGardenCorner(x - 1, y, z + 1);
            this.placeGardenCorner(x - 1, y, z + 6);
            this.placeGardenCorner(x - 6, y, z + 1);
            this.placeGardenCorner(x - 6, y, z + 6);
            this.rooms.add(drawPlane(AetherBlocks.SLAB_BRICK_HOLYSTONE.id(), 0, Direction.SOUTH, 2, Direction.WEST, 2, x - 3, y + 1, z + 3, false));
        }
    }

    private void placeGardenCorner(int x, int y, int z) {
        this.rooms.add(wfb(x, y + 1, z, AetherBlocks.BRICK_HOLYSTONE.id(), 0));
        this.chests.add(wfb(x, y + 2, z, CHEST_OR_MIMIC_GARDEN.getRandom(random), false));
        this.rooms.add(wfb(x, y + 4, z, Blocks.GLOWSTONE.id(), 0));
    }

    private void createSleepingChambers(int x, int y, int z, Direction doorDirection) {
        this.createHallway(x, y, z, doorDirection);
        // places chests
        for (int i = 0; i < Direction.horizontal.length; i++) {

            Direction dir = Direction.horizontal[i];
            if (dir == doorDirection) {
                continue;
            }
            switch (dir) {
                case EAST:
                    this.rooms.add(drawLine(AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED.id(), getMetadataFromDyeAndLower(DyeColor.WHITE, 0), Direction.UP, 3, x - 1, y + 1, z + 3, false));
                    this.rooms.add(drawLine(AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED.id(), getMetadataFromDyeAndLower(DyeColor.PURPLE, 0), Direction.UP, 3, x - 1, y + 1, z + 4, false));
                    this.rooms.add(wfb(x - 1, y + 4, z + 4, AetherBlocks.TORCH_AMBROSIUM.id(), maskDirectionHorizontal(0, getTorchMetadataFromDirection(dir))));
                    this.rooms.add(drawLine(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.YELLOW, false, true, Direction.NORTH), Direction.UP, 3, x - 1, y + 1, z + 2, false));
                    this.rooms.add(drawLine(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.YELLOW, false, true, Direction.SOUTH), Direction.UP, 3, x - 1, y + 1, z + 5, false));
                    break;
                case WEST:
                    this.rooms.add(drawLine(AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED.id(), getMetadataFromDyeAndLower(DyeColor.WHITE, 0), Direction.UP, 3, x - 6, y + 1, z + 3, false));
                    this.rooms.add(drawLine(AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED.id(), getMetadataFromDyeAndLower(DyeColor.PURPLE, 0), Direction.UP, 3, x - 6, y + 1, z + 4, false));
                    this.rooms.add(wfb(x - 6, y + 4, z + 4, AetherBlocks.TORCH_AMBROSIUM.id(), maskDirectionHorizontal(0, getTorchMetadataFromDirection(dir))));
                    this.rooms.add(drawLine(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.YELLOW, false, true, Direction.NORTH), Direction.UP, 3, x - 6, y + 1, z + 2, false));
                    this.rooms.add(drawLine(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.YELLOW, false, true, Direction.SOUTH), Direction.UP, 3, x - 6, y + 1, z + 5, false));
                    break;
                case NORTH:
                    this.rooms.add(wfb(x - 2, y + 1, z + 1, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.YELLOW, false, true, Direction.EAST)));
                    this.rooms.add(wfb(x - 3, y + 1, z + 1, AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED.id(), getMetadataStairs(DyeColor.YELLOW, false, Direction.NORTH)));
                    this.rooms.add(wfb(x - 4, y + 1, z + 1, AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED.id(), getMetadataStairs(DyeColor.YELLOW, false, Direction.NORTH)));
                    this.rooms.add(wfb(x - 5, y + 1, z + 1, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.YELLOW, false, true, Direction.WEST)));
                    break;
                case SOUTH:
                default:
                    this.rooms.add(wfb(x - 2, y + 1, z + 6, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.YELLOW, false, true, Direction.EAST)));
                    this.rooms.add(wfb(x - 3, y + 1, z + 6, AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED.id(), getMetadataStairs(DyeColor.YELLOW, false, Direction.SOUTH)));
                    this.rooms.add(wfb(x - 4, y + 1, z + 6, AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED.id(), getMetadataStairs(DyeColor.YELLOW, false, Direction.SOUTH)));
                    this.rooms.add(wfb(x - 5, y + 1, z + 6, AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), setMetadataTrapdoor(DyeColor.YELLOW, false, true, Direction.WEST)));
                    break;
            }

            this.rooms.add(wfb(x - 1, y + 1, z + 1, ANGELIC_ROOM.getRandom(random), false));
            chests.add(wfb(x - 1, y + 2, z + 1, CHEST_OR_MIMIC_ALT.getRandom(random), false));

            this.rooms.add(wfb(x - 1, y + 1, z + 6, ANGELIC_ROOM.getRandom(random), false));
            chests.add(wfb(x - 1, y + 2, z + 6, CHEST_OR_MIMIC_ALT.getRandom(random), false));

            this.rooms.add(wfb(x - 6, y + 1, z + 1, ANGELIC_ROOM.getRandom(random), false));
            chests.add(wfb(x - 6, y + 2, z + 1, CHEST_OR_MIMIC_ALT.getRandom(random), false));

            this.rooms.add(wfb(x - 6, y + 1, z + 6, ANGELIC_ROOM.getRandom(random), false));
            chests.add(wfb(x - 6, y + 2, z + 6, CHEST_OR_MIMIC_ALT.getRandom(random), false));
        }
    }

    public void createChests(int x, int y, int z) {
        int chestCount = 0;
        if (random.nextInt(3) == 0) {
            chestCount++;
            this.chests.add(wfb(x - 3, y + 2, z + 3, CHEST_OR_MIMIC.getRandom(random), false));
        }
        if (random.nextInt(3) == 0) {
            chestCount++;
            this.chests.add(wfb(x - 4, y + 2, z + 3, CHEST_OR_MIMIC.getRandom(random), false));
        }
        if (random.nextInt(3) == 0) {
            chestCount++;
            this.chests.add(wfb(x - 3, y + 2, z + 4, CHEST_OR_MIMIC.getRandom(random), false));
        }
        if (random.nextInt(2) == 0 || chestCount < 2) {
            this.chests.add(wfb(x - 4, y + 2, z + 4, CHEST_OR_MIMIC.getRandom(random), false));
        }
    }

    public void createStaircase(int x, int y, int z) {
        // draw room
        this.rooms.add(drawShell(random, ANGELIC_ROOM, Direction.SOUTH, 8, Direction.UP, 6, Direction.WEST, 8, x, y, z, false));
        // add opening
        this.doors.add(drawPlane(0, 0, Direction.SOUTH, 4, Direction.WEST, 4, x - 2, y + 5, z + 2, false));
        // add pillar
        this.doors.add(drawVolume(random, ANGELIC, Direction.SOUTH, 2, Direction.WEST, 2, Direction.UP, 9, x - 3, y + 1, z + 3, false));

        // add chests
        createChests(x, y + 5, z);

        // add stairs
        this.doors.add(wfb(x - 2, y + 1, z + 2, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 0, false));
        this.doors.add(wfb(x - 2, y + 1, z + 3, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 1, false));
        this.doors.add(wfb(x - 2, y + 2, z + 4, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 0, false));
        this.doors.add(wfb(x - 2, y + 1, z + 4, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 2, false));
        this.doors.add(wfb(x - 2, y + 2, z + 5, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 1, false));

        this.doors.add(wfb(x - 3, y + 3, z + 5, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 0, false));
        this.doors.add(wfb(x - 3, y + 2, z + 5, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 2, false));
        this.doors.add(wfb(x - 4, y + 3, z + 5, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 1, false));
        this.doors.add(wfb(x - 5, y + 4, z + 5, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 0, false));
        this.doors.add(wfb(x - 5, y + 3, z + 5, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 2, false));

        this.doors.add(wfb(x - 5, y + 4, z + 4, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 1, false));
        this.doors.add(wfb(x - 5, y + 5, z + 3, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 0, false));
        this.doors.add(wfb(x - 5, y + 4, z + 3, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 2, false));
        this.doors.add(wfb(x - 5, y + 5, z + 2, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 1, false));

        this.doors.add(wfb(x - 4, y + 5, z + 2, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 1, false));
        this.doors.add(wfb(x - 3, y + 5, z + 2, AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 1, false));
    }

    public Direction getDoorDirection(int to, int from) {
        int levelTo = to / 9;
        int columnTo = (to - levelTo * 9) / 3;
        int rowTo = to - levelTo * 9 - columnTo * 3;

        int levelFrom = from / 9;
        int columnFrom = (from - levelFrom * 9) / 3;
        int rowFrom = from - levelFrom * 9 - columnFrom * 3;

        if (columnFrom < columnTo) {
            return Direction.NORTH;
        }
        if (columnFrom > columnTo) {
            return Direction.SOUTH;
        }
        if (rowFrom > rowTo) {
            return Direction.WEST;
        }
        if (rowFrom < rowTo) {
            return Direction.EAST;
        }
        if (levelFrom > levelTo) {
            return Direction.UP;
        }
        if (levelFrom < levelTo) {
            return Direction.DOWN;
        }
        return Direction.NONE;
    }

    public WorldFeatureComponent getRooms() {
        return rooms;
    }

    public WorldFeatureComponent getChests() {
        return chests;
    }
}
