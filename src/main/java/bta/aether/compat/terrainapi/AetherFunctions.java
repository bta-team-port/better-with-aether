package bta.aether.compat.terrainapi;

import bta.aether.Aether;
import bta.aether.block.AetherBlocks;
import bta.aether.world.AetherDimension;
import bta.aether.world.generate.dungeon.*;
import bta.aether.world.generate.feature.WorldFeatureClouds;
import bta.aether.world.generate.feature.WorldFeatureQuicksoil;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkCoordinates;
import useless.terrainapi.generation.Parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static bta.aether.world.generate.WorldFeatureAetherDungeonBase.bronzeRoomList;

public class AetherFunctions {

    public static int getTreeDensity(Parameters parameters) {
        TerrainAetherConfig aetherConfig = ChunkDecoratorAetherAPI.aetherConfig;
        ChunkDecoratorAetherAPI decorator = (ChunkDecoratorAetherAPI)parameters.decorator;

        Integer treeDensity = aetherConfig.getTreeDensity(parameters.biome);
        if (decorator.treeDensityOverride != -1) {
            return decorator.treeDensityOverride;
        } else if (treeDensity != null && treeDensity == -1000) {
            return 0;
        } else {
            int x = parameters.chunk.xPosition * 16;
            int z = parameters.chunk.zPosition * 16;
            double d = 0.5;
            int noiseValue = (int)((decorator.treeDensityNoise.get((double)x * d, (double)z * d) / 8.0 + parameters.random.nextDouble() * 4.0 + 4.0) / 3.0);
            int treeDensityOffset = 0;
            if (parameters.random.nextInt(10) == 0) {
                ++treeDensityOffset;
            }

            return treeDensity == null ? treeDensityOffset : treeDensity + noiseValue + treeDensityOffset;
        }
    }

    public static Void generateQuickSoil(Parameters parameters) {

        int x = parameters.chunk.xPosition * 16;
        int z = parameters.chunk.zPosition * 16;
        World world = parameters.chunk.world;
        Random rand = new Random();
        int dx;
        int dy;
        int dz;

        if(rand.nextInt(8) == 0) {
            for (dx = x; dx < x + 16; dx++) {
                for (dy = 0; dy < 256; dy++) {
                    for (dz = z; dz < z + 16; dz++) {
                        if (world.getBlockId(dx, dy, dz) == 0 && world.getBlockId(dx, dy + 1, dz) == AetherBlocks.grassAether.id) {
                            new WorldFeatureQuicksoil(AetherBlocks.quicksoil.id, 3).generate(world, rand, dx, dy, dz);
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Void generateClouds(Parameters parameters) {
        Random rand = new Random();
        World world = parameters.chunk.world;
        int x = parameters.chunk.xPosition * 16;
        int z = parameters.chunk.zPosition * 16;

        // Normal clouds
        int dx = x + rand.nextInt(16);
        int dy = 20 + rand.nextInt(200);
        int dz = z + rand.nextInt(16);

        int cloudID = 0;
        int choice = rand.nextInt(20);
        if (choice == 0)  cloudID = AetherBlocks.aercloudGold.id;
        if (choice > 15)  cloudID = AetherBlocks.aercloudBlue.id;
        if (choice >= 1 && choice <= 15)  cloudID = AetherBlocks.aercloudWhite.id;


        int cloudSize = 6 + rand.nextInt(10);
        if (rand.nextInt(4) == 0) {
            (new WorldFeatureClouds(cloudSize, cloudID, false)).generate(world, rand, dx, dy, dz);
        }

        // Yellow clouds
        dx = x + rand.nextInt(16);
        dy = 210 + rand.nextInt(30);
        dz = z + rand.nextInt(16);

        cloudSize = 6 + rand.nextInt(10);
        if (rand.nextInt(15) == 0) {
            (new WorldFeatureClouds(cloudSize, AetherBlocks.aercloudGold.id, false)).generate(world, rand, dx, dy, dz);
        }

        // Flat clouds
        dx = x + rand.nextInt(16);
        dy = 10 + rand.nextInt(20);
        dz = z + rand.nextInt(16);

        cloudSize = 10 + rand.nextInt(20);
        if (rand.nextInt(30) == 0) {
            (new WorldFeatureClouds(cloudSize, AetherBlocks.aercloudWhite.id, true)).generate(world, rand, dx, dy, dz);
        }

        return null;
    }

    public static Void generateBronzeDungeon(Parameters parameters) {
        Random rand = new Random();
        World world = parameters.chunk.world;
        if (parameters.chunk.xPosition%2 != 0 || parameters.chunk.zPosition%2 != 0) return null;

        int x = parameters.chunk.xPosition * 16;
        int y = 100 + rand.nextInt(50);
        int z = parameters.chunk.zPosition * 16;


        //FIXME: this gets straight up ignored sometimes and that's  v e r y  bad  -martin
        for (ChunkCoordinates coords : AetherDimension.dugeonMap.values()) {
            if (coords.getSqDistanceTo(x, y, z) < 400) {
                return null;
            }
        }
        // Dungeon room placement logic
        int maxIteration = 5;
        HashMap<ChunkCoordinates, AetherDungeonRoom> hallwayMap = new HashMap<>();
        HashMap<ChunkCoordinates, AetherDungeonRoom> generatedDungeonLayout = generateDungeonLayout(world, x, y, z, 0, maxIteration, new HashMap<>(), hallwayMap);

        // check for dungeon size being large enough
        if (generatedDungeonLayout.size() < 10) return null;
        System.out.printf("got dungeon: %d%n", generatedDungeonLayout.size());

        // TODO: Boos Room can generate inside already placed room
        // Boss Room
        int roomOffset = 3;
        AetherDungeonRoom newRoom = new AetherDungeonRoomBossBronze();
        HashMap<ChunkCoordinates, AetherDungeonRoom> newRoomMap = (HashMap<ChunkCoordinates, AetherDungeonRoom>) generatedDungeonLayout.clone();
        boolean boosRoomSpawned = false;
        for (ChunkCoordinates coordinate : newRoomMap.keySet()) {
            AetherDungeonRoom room = newRoomMap.get(coordinate);
            if (newRoom.canPlaceRoom(world, x + roomOffset + room.sizeX-2, y, z-2, generatedDungeonLayout)) { // X+ Axis
                generatedDungeonLayout.put(new ChunkCoordinates(x + roomOffset + room.sizeX-2, y, z-2), newRoom);
                //hallwayMap.put(new ChunkCoordinates(x + room.sizeX, y + 1, z + (room.sizeZ / 2)-1), new AetherDungeonRoomHallwayBronze(Side.EAST, roomOffset + 1));
                boosRoomSpawned = true;
                break;
            } else if (newRoom.canPlaceRoom(world, x - roomOffset - room.sizeX-2, y, z-2, generatedDungeonLayout)) { // X- Axis
                generatedDungeonLayout.put(new ChunkCoordinates(x - roomOffset - room.sizeX-2, y, z-2), newRoom);
                //hallwayMap.put(new ChunkCoordinates(x - roomOffset+1, y+1, z+(room.sizeZ/2)-1), new AetherDungeonRoomHallwayBronze(Side.WEST, roomOffset + 1));
                boosRoomSpawned = true;
                break;
            } else if (newRoom.canPlaceRoom(world, x-2, y, z + roomOffset + room.sizeZ-2, generatedDungeonLayout)) { // Z+ Axis
                generatedDungeonLayout.put(new ChunkCoordinates(x-2, y, z + roomOffset + room.sizeZ-2), newRoom);
                //hallwayMap.put(new ChunkCoordinates(x+(room.sizeX/2)-1, y+1, z + room.sizeX), new AetherDungeonRoomHallwayBronze(Side.SOUTH, roomOffset + 1));
                boosRoomSpawned = true;
                break;
            } else if (newRoom.canPlaceRoom(world, x-2, y, z-roomOffset-room.sizeZ-2, generatedDungeonLayout)) { // Z- Axis
                generatedDungeonLayout.put(new ChunkCoordinates(x-2, y, z-roomOffset-room.sizeZ-2), newRoom);
                //hallwayMap.put(new ChunkCoordinates(x+(room.sizeX/2)-1, y+1, z - roomOffset+1), new AetherDungeonRoomHallwayBronze(Side.NORTH, roomOffset + 1));
                boosRoomSpawned = true;
                break;
            }
        }
        if (!boosRoomSpawned) return null;

        //failsafe in case the check above gets ignored
        int dungeon = AetherDimension.registerDungeonToMap(x, y, z);
        if(dungeon == -1){
            Aether.LOGGER.warn("Tried to generate dungeon too close to another one.");
            return null;
        }

        System.out.printf("Dungeon Generated: %d, %d, %d%n", x, y, z);
        // Room generation
        for (ChunkCoordinates coordinate : generatedDungeonLayout.keySet()) {
            AetherDungeonRoom room = generatedDungeonLayout.get(coordinate);
            room.placeRoom(world, coordinate.x, coordinate.y, coordinate.z);
        }
        for (ChunkCoordinates coordinate : hallwayMap.keySet()) {
            AetherDungeonRoom room = hallwayMap.get(coordinate);
            room.placeRoom(world, coordinate.x, coordinate.y, coordinate.z);
        }

        return null;
    }

    private static HashMap<ChunkCoordinates, AetherDungeonRoom> generateDungeonLayout(World world, int x, int y, int z, int iteration, int maxIteration, HashMap<ChunkCoordinates, AetherDungeonRoom> roomMap, HashMap<ChunkCoordinates, AetherDungeonRoom> hallwayMap) {
        Random random = new Random();
        int roomOffset = 5;
        if (iteration >= maxIteration) {
            return roomMap;
        }
        if (roomMap.isEmpty()) {
            AetherDungeonRoom room = new AetherDungeonRoomEmptyBronze();
            if (!room.canPlaceRoom(world, x, y, z)) return roomMap;
            roomMap.put(new ChunkCoordinates(x, y, z), room);
            return generateDungeonLayout(world, x, y, z, iteration+1, maxIteration, roomMap, hallwayMap);
        } else {
            HashMap<ChunkCoordinates, AetherDungeonRoom> newRoomMap = (HashMap<ChunkCoordinates, AetherDungeonRoom>) roomMap.clone();
            for (ChunkCoordinates coordinate : roomMap.keySet()) {
                AetherDungeonRoom room = roomMap.get(coordinate);

                List<AetherDungeonRoom> possibleRooms;
                AetherDungeonRoom newRoom;

                // X+ axis
                possibleRooms = new ArrayList<>();
                possibleRooms.addAll(getPossibleRooms(world, x+roomOffset+room.sizeX, y, z, newRoomMap));
                newRoom = (possibleRooms.size() > 0) ? possibleRooms.get(random.nextInt(possibleRooms.size())) : null;
                if (newRoom != null) {
                    newRoomMap.put(new ChunkCoordinates(x + roomOffset + room.sizeX, y, z), newRoom);
                    hallwayMap.put(new ChunkCoordinates(x + room.sizeX, y+1, z+(room.sizeZ/2)-1), new AetherDungeonRoomHallwayBronze(Side.EAST, roomOffset+1));
                    newRoomMap = generateDungeonLayout(world, x + roomOffset + room.sizeX, y, z, iteration + 1, maxIteration, newRoomMap, hallwayMap);
                }
                // X- axis
                possibleRooms = new ArrayList<>();
                possibleRooms.addAll(getPossibleRooms(world, x-roomOffset-room.sizeX, y, z, newRoomMap));
                newRoom = (possibleRooms.size() > 0) ? possibleRooms.get(random.nextInt(possibleRooms.size())) : null;
                if (newRoom != null) {
                    newRoomMap.put(new ChunkCoordinates(x - roomOffset - room.sizeX, y, z), possibleRooms.get(random.nextInt(possibleRooms.size())));
                    //hallwayMap.put(new ChunkCoordinates(x - roomOffset+1, y+1, z+(room.sizeZ/2)-1), new AetherDungeonRoomHallwayBronze(Side.WEST, roomOffset+1));
                    newRoomMap = generateDungeonLayout(world, x - roomOffset - room.sizeX, y, z, iteration + 1, maxIteration, newRoomMap, hallwayMap);
                }
                // Z+ axis
                possibleRooms = new ArrayList<>();
                possibleRooms.addAll(getPossibleRooms(world, x, y, z+roomOffset+room.sizeZ, newRoomMap));
                newRoom = (possibleRooms.size() > 0) ? possibleRooms.get(random.nextInt(possibleRooms.size())) : null;
                if (newRoom != null) {
                    newRoomMap.put(new ChunkCoordinates(x, y, z + roomOffset + room.sizeZ), possibleRooms.get(random.nextInt(possibleRooms.size())));
                    hallwayMap.put(new ChunkCoordinates(x+(room.sizeX/2)-1, y+1, z + room.sizeX), new AetherDungeonRoomHallwayBronze(Side.SOUTH, roomOffset+1));
                    newRoomMap = generateDungeonLayout(world, x, y, z + roomOffset + room.sizeZ, iteration + 1, maxIteration, newRoomMap, hallwayMap);
                }
                // Z- axis
                possibleRooms = new ArrayList<>();
                possibleRooms.addAll(getPossibleRooms(world, x, y, z-roomOffset-room.sizeZ, newRoomMap));
                newRoom = (possibleRooms.size() > 0) ? possibleRooms.get(random.nextInt(possibleRooms.size())) : null;
                if (newRoom != null) {
                    newRoomMap.put(new ChunkCoordinates(x, y, z - roomOffset - room.sizeZ), possibleRooms.get(random.nextInt(possibleRooms.size())));
                    //hallwayMap.put(new ChunkCoordinates(x+(room.sizeX/2)-1, y+1, z - roomOffset+1), new AetherDungeonRoomHallwayBronze(Side.NORTH, roomOffset+1));
                    newRoomMap = generateDungeonLayout(world, x, y, z - roomOffset - room.sizeZ, iteration + 1, maxIteration, newRoomMap, hallwayMap);
                }
            }
            return newRoomMap;
        }
    }

    private static List<AetherDungeonRoom> getPossibleRooms(World world, int x, int y, int z, HashMap<ChunkCoordinates, AetherDungeonRoom> roomMap) {
        List<AetherDungeonRoom> possibleRooms = new ArrayList<>();
        for (AetherDungeonRoom tryRoom : bronzeRoomList) {
            if (tryRoom.canPlaceRoom(world, x, y, z, roomMap)) {
                possibleRooms.add(tryRoom);
            }
        }
        return possibleRooms;
    }
}
