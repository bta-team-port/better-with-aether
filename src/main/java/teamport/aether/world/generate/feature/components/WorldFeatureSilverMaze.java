package teamport.aether.world.generate.feature.components;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import teamport.aether.AetherMod;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.helper.MazeHelper;
import teamport.aether.helper.Pair;
import teamport.aether.world.generate.feature.BlockPallet;

import java.util.*;

import static teamport.aether.world.generate.feature.WorldFeatureAetherDungeonSilver.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureBlock.wfb;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;

public class WorldFeatureSilverMaze {
    public static final int ENTRANCE = 1;
    public static final int ROOM_WIDTH = 7;
    public static final int ROOM_HEIGHT = 5;
    public static final int ROOM_COUNT = 27;
    WorldFeatureComponent rooms;
    WorldFeatureComponent chests;
    WorldFeatureComponent doors;
    WorldFeatureComponent traps;
    Map<Integer, List<Integer>> SPANNING_TREE;
    public World world;
    public Random random;

    public static BlockPallet angelicHallway = new BlockPallet();
    public static BlockPallet angelicTrapped = new BlockPallet();
    static {
        angelicHallway.addEntry(AetherBlocks.CARVED_ANGELIC_LOCKED.id(), 0, 85);
        angelicHallway.addEntry(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED.id(), 0, 5);
        angelicHallway.addEntry(AetherBlocks.CARVED_ANGELIC_TRAPPED.id(), 0, 10);

        angelicTrapped.addEntry(AetherBlocks.CARVED_ANGELIC_LOCKED.id(), 0, 60);
        angelicTrapped.addEntry(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED.id(), 0, 20);
        angelicTrapped.addEntry(AetherBlocks.CARVED_ANGELIC_TRAPPED.id(), 0, 20);
    }

    /// Graph
    static Map<Integer, List<Integer>> GRAPH = new HashMap<>();

    /// I am not going to generate this because it easier and safer to just write it down
    /// this graph is missing the staircases that are added later on so that they won't pollute the maze
    static {

        GRAPH.put(0, new ArrayList<>(Arrays.asList(1, 3)));
        GRAPH.put(1, new ArrayList<>(Arrays.asList(0, 2, 4)));
        GRAPH.put(2, new ArrayList<>(Arrays.asList(1, 5)));
        GRAPH.put(3, new ArrayList<>(Arrays.asList(0, 4, 6)));
        GRAPH.put(4, new ArrayList<>(Arrays.asList(1, 3, 5, 7)));
        GRAPH.put(5, new ArrayList<>(Arrays.asList(2, 4)));
        GRAPH.put(6, new ArrayList<>(Arrays.asList(3, 7)));
        GRAPH.put(7, new ArrayList<>(Arrays.asList(4, 6)));
        GRAPH.put(8, new ArrayList<>(Arrays.asList(17)));
        GRAPH.put(9, new ArrayList<>(Arrays.asList(10, 12)));
        GRAPH.put(10, new ArrayList<>(Arrays.asList(9, 11, 13)));
        GRAPH.put(11, new ArrayList<>(Arrays.asList(10, 14)));
        GRAPH.put(12, new ArrayList<>(Arrays.asList(9, 13, 15)));
        GRAPH.put(13, new ArrayList<>(Arrays.asList(10, 12, 14, 16)));
        GRAPH.put(14, new ArrayList<>(Arrays.asList(11, 13)));
        GRAPH.put(15, new ArrayList<>(Arrays.asList(12, 16)));
        GRAPH.put(16, new ArrayList<>(Arrays.asList(13, 15)));
        GRAPH.put(17, new ArrayList<>(Arrays.asList(8, 26)));
        GRAPH.put(18, new ArrayList<>(Arrays.asList(19, 21)));
        GRAPH.put(19, new ArrayList<>(Arrays.asList(18, 20, 22)));
        GRAPH.put(20, new ArrayList<>(Arrays.asList(19, 23)));
        GRAPH.put(21, new ArrayList<>(Arrays.asList(18, 22, 24)));
        GRAPH.put(22, new ArrayList<>(Arrays.asList(19, 21, 23, 25)));
        GRAPH.put(23, new ArrayList<>(Arrays.asList(20, 22, 26)));
        GRAPH.put(24, new ArrayList<>(Arrays.asList(21, 25)));
        GRAPH.put(25, new ArrayList<>(Arrays.asList(22, 24, 26)));
        GRAPH.put(26, new ArrayList<>(Arrays.asList(17, 23, 25)));
    }

    public static int addAdditionalStaircase(Random random) {
        int countStaircaseRooms = 0;
        int prev = -1;
        for (int LEVEL = 1; LEVEL >= 0; LEVEL--) {
            int staircaseAmount = random.nextInt(2) + 1;
            for (int i = staircaseAmount; i > 0; i--) {
                int index = random.nextInt(8);
                if (index == 1 && LEVEL == 0) index++;
                if (prev == index) index++;
                if (index > 7) index = index % 7;
                prev = index;
                System.out.printf("stairs: from:%d, to:%d\n", LEVEL * 9 + index, (LEVEL + 1) * 9 + index);;
                GRAPH.get(LEVEL * 9 + index).add((LEVEL + 1) * 9 + index);
                GRAPH.get((LEVEL + 1) * 9 + index).add(LEVEL * 9 + index);
            }
            countStaircaseRooms += staircaseAmount;
        }
        return countStaircaseRooms;
    }
    public WorldFeatureComponent[] createMaze(World world, Random random, int x, int y, int z) {
        this.world = world;
        this.random = random;
        this.rooms = new WorldFeatureComponent();
        this.doors = new WorldFeatureComponent();
        this.chests = new WorldFeatureComponent();
        this.traps = new WorldFeatureComponent();
        WorldFeatureSilverMaze.addAdditionalStaircase(random);
        List<Pair<Integer, Integer>> edges = MazeHelper.randomMazeKruskal(GRAPH, 27);
        this.SPANNING_TREE = MazeHelper.makeGraph(edges);

        boolean[] generated = new boolean[ROOM_COUNT];
        for (Pair<Integer, Integer> edge : edges) {
            Integer to = edge.first;
            Integer from = edge.second;
            generated[to] = true;
            createRoomMaze(to, from, x, y, z);
            if (!generated[from]) {
                generated[from] = true;
                createRoomMaze(from, to, x, y, z);
            }
        }
        rooms.add(this.traps);
        rooms.add(this.doors);
        return new WorldFeatureComponent[]{this.rooms, this.chests};
    }
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
            createStaircase(roomX, roomY, roomZ);
            return;
        }
        if (to == ENTRANCE || SPANNING_TREE.get(to).size() > 2) {
            createHallway(roomX, roomY, roomZ, doorDirection);
            return;
        }
        if (SPANNING_TREE.get(to).size() == 1) {
            createTreasureRoom(roomX, roomY, roomZ, doorDirection);
            return;
        }
        createRoom(roomX, roomY, roomZ, doorDirection);
    }
    public void createHallway(int x, int y, int z, Direction doorDirection) {
        rooms.add(drawShell(random, angelicHallway, Direction.SOUTH, 8, Direction.UP, 6, Direction.WEST, 8, x, y, z, true));
        // only generate door in horizontal direction
        if (doorDirection == Direction.SOUTH) {
            doors.add(drawPlane(0, 0, Direction.UP, 2, Direction.WEST, 2, x - 3, y + 1, z + 7, true));
        }
        if (doorDirection == Direction.WEST) {
            doors.add(drawPlane(0, 0, Direction.UP, 2, Direction.SOUTH, 2, x - 7, y + 1, z + 3, true));
        }
        if (doorDirection == Direction.NORTH) {
            doors.add(drawPlane(0, 0, Direction.UP, 2, Direction.WEST, 2, x - 3, y + 1, z, true));
        }
        if (doorDirection == Direction.EAST) {
            doors.add(drawPlane(0, 0, Direction.UP, 2, Direction.SOUTH, 2, x, y + 1, z + 3, true));
        }
    }
    public void createRoom(int x, int y, int z, Direction doorDirection) {
        createHallway(x, y, z, doorDirection);
        rooms.add(drawPlane(random, angelic, Direction.SOUTH, 2, Direction.WEST, 2, x - 3, y + 1, z + 3, true));
        createChests(x, y, z);
    }
    public void createTreasureRoom(int x, int y, int z, Direction doorDirection) {
        createHallway(x, y, z, doorDirection);
        // places chests
        for(int i = 0; i < Direction.horizontalDirections.length; i++){
            Direction dir = Direction.horizontalDirections[i];
            if(dir == doorDirection){
                continue;
            }
            if (dir == Direction.SOUTH) {
                rooms.add(drawLine(random, angelic, Direction.WEST, 2, x - 3, y + 1, z + 6, true));
                chests.add(wfb(x - 3, y + 2, z + 6, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), true));
                chests.add(wfb(x - 4, y + 2, z + 6, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), true));

            }
            if (dir == Direction.WEST) {
                rooms.add(drawLine(random, angelic, Direction.SOUTH, 2, x - 6, y + 1, z + 3, true));
                chests.add(wfb(x - 6, y + 2, z + 3, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), true));
                chests.add(wfb(x - 6, y + 2, z + 4, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), true));
            }
            if (dir == Direction.NORTH) {
                rooms.add(drawLine(random, angelic, Direction.WEST, 2, x - 3, y + 1, z + 1, true));
                chests.add(wfb(x - 3, y + 2, z + 1, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), true));
                chests.add(wfb(x - 4, y + 2, z + 1, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), true));
            }
            if (dir == Direction.EAST) {
                rooms.add(drawLine(random, angelic, Direction.SOUTH, 2, x - 1, y + 1, z + 3, true));
                chests.add(wfb(x - 1, y + 2, z + 3, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), true));
                chests.add(wfb(x - 1, y + 2, z + 4, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), true));
            }
        }
        // replaces the floor with more trapped valkyries
        traps.add(drawPlane(random, angelicTrapped, Direction.SOUTH, 8, Direction.WEST, 8, x, y, z, true));


        // places decorations
        rooms.add(wfb(x - 1, y + 1, z + 1, AetherBlocks.CARVED_ANGELIC.id(), 0, true));
        rooms.add(wfb(x - 1, y + 2, z + 1, AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, true));
        rooms.add(wfb(x - 1, y + 3, z + 1, Blocks.GLOWSTONE.id(), 0, true));

        rooms.add(wfb(x - 1, y + 1, z + 6, AetherBlocks.CARVED_ANGELIC.id(), 0, true));
        rooms.add(wfb(x - 1, y + 2, z + 6, AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, true));
        rooms.add(wfb(x - 1, y + 3, z + 6, Blocks.GLOWSTONE.id(), 0, true));

        rooms.add(wfb(x - 6, y + 1, z + 1, AetherBlocks.CARVED_ANGELIC.id(), 0, true));
        rooms.add(wfb(x - 6, y + 2, z + 1, AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, true));
        rooms.add(wfb(x - 6, y + 3, z + 1, Blocks.GLOWSTONE.id(), 0, true));

        rooms.add(wfb(x - 6, y + 1, z + 6, AetherBlocks.CARVED_ANGELIC.id(), 0, true));
        rooms.add(wfb(x - 6, y + 2, z + 6, AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, true));
        rooms.add(wfb(x - 6, y + 3, z + 6, Blocks.GLOWSTONE.id(), 0, true));
    }
    public void createChests(int x, int y, int z) {
        int chestCount = 0;
        if (random.nextInt(3) == 0) {
            chestCount++;
            chests.add(placeChestOrMimic(random, x - 3, y + 2, z + 3));
        }
        if (random.nextInt(3) == 0) {
            chestCount++;
            chests.add(placeChestOrMimic(random, x - 4, y + 2, z + 3));
        }
        if (random.nextInt(3) == 0) {
            chestCount++;
            chests.add(placeChestOrMimic(random, x - 3, y + 2, z + 4));
        }
        if (random.nextInt(2) == 0 && chestCount < 2) {
            chests.add(placeChestOrMimic(random, x - 4, y + 2, z + 4));
        }
    }
    public void createStaircase(int x, int y, int z) {
        // draw room
        rooms.add(drawShell(random, angelicTrapped, Direction.SOUTH, 8, Direction.UP, 6, Direction.WEST, 8, x, y, z, true));
        // add opening
        doors.add(drawPlane(0, 0, Direction.SOUTH, 4, Direction.WEST, 4, x - 2, y + 5, z + 2, true));
        // add pillar
        doors.add(drawVolume(random, angelic, Direction.SOUTH, 2, Direction.WEST, 2, Direction.UP, 9, x - 3, y + 1, z + 3, true));

        // add chests
        createChests(x, y + 5, z);

        // add stairs
        doors.add(wfb(x - 2, y + 1, z + 2, AetherBlocks.SLAB_CARVED_STONE.id(), 0, true));
        doors.add(wfb(x - 2, y + 1, z + 3, AetherBlocks.SLAB_CARVED_STONE.id(), 1, true));
        doors.add(wfb(x - 2, y + 2, z + 4, AetherBlocks.SLAB_CARVED_STONE.id(), 0, true));
        doors.add(wfb(x - 2, y + 1, z + 4, AetherBlocks.SLAB_CARVED_STONE.id(), 2, true));
        doors.add(wfb(x - 2, y + 2, z + 5, AetherBlocks.SLAB_CARVED_STONE.id(), 1, true));

        doors.add(wfb(x - 3, y + 3, z + 5, AetherBlocks.SLAB_CARVED_STONE.id(), 0, true));
        doors.add(wfb(x - 3, y + 2, z + 5, AetherBlocks.SLAB_CARVED_STONE.id(), 2, true));
        doors.add(wfb(x - 4, y + 3, z + 5, AetherBlocks.SLAB_CARVED_STONE.id(), 1, true));
        doors.add(wfb(x - 5, y + 4, z + 5, AetherBlocks.SLAB_CARVED_STONE.id(), 0, true));
        doors.add(wfb(x - 5, y + 3, z + 5, AetherBlocks.SLAB_CARVED_STONE.id(), 2, true));

        doors.add(wfb(x - 5, y + 4, z + 4, AetherBlocks.SLAB_CARVED_STONE.id(), 1, true));
        doors.add(wfb(x - 5, y + 5, z + 3, AetherBlocks.SLAB_CARVED_STONE.id(), 0, true));
        doors.add(wfb(x - 5, y + 4, z + 3, AetherBlocks.SLAB_CARVED_STONE.id(), 2, true));
        doors.add(wfb(x - 5, y + 5, z + 2, AetherBlocks.SLAB_CARVED_STONE.id(), 1, true));

        doors.add(wfb(x - 4, y + 5, z + 2, AetherBlocks.SLAB_CARVED_STONE.id(), 1, true));
        doors.add(wfb(x - 3, y + 5, z + 2, AetherBlocks.SLAB_CARVED_STONE.id(), 1, true));
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
}
