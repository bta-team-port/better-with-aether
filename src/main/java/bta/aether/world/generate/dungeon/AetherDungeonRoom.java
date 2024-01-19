package bta.aether.world.generate.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkCoordinates;

import java.util.HashMap;
import java.util.Random;

public abstract class AetherDungeonRoom {
    public boolean underground;
    public int sizeX;
    public int sizeY;
    public int sizeZ;

    public Block mainBlock;
    public Block secondaryBlock;
    public Block trapBlock;

    public AetherDungeonRoom(boolean underground, int sX, int sY, int sZ, Block mB, Block sB, Block tB) {
        this.underground = underground;
        this.sizeX = sX-1;
        this.sizeY = sY-1;
        this.sizeZ = sZ-1;
        this.mainBlock = mB;
        this.secondaryBlock = sB;
        this.trapBlock = tB;
    }

    public boolean canPlaceRoom(World world, int x, int y, int z) {
        //return (world.getBlockId(x, y, z) == 0) != this.underground;
        int p = 0;
        if ((world.getBlockId(x, y, z) == 0) != this.underground) p++;
        if ((world.getBlockId(x + this.sizeX, y, z) == 0) != this.underground) p++;
        if ((world.getBlockId(x, y + this.sizeY, z) == 0) != this.underground) p++;
        if ((world.getBlockId(x, y, z + this.sizeZ) == 0) != this.underground) p++;

        if ((world.getBlockId(x + this.sizeX, y, z + this.sizeZ) == 0) != this.underground) p++;
        if ((world.getBlockId(x, y + this.sizeY, z + this.sizeZ) == 0) != this.underground) p++;
        if ((world.getBlockId(x + this.sizeX, y + this.sizeY, z) == 0) != this.underground) p++;
        if ((world.getBlockId(x + this.sizeX, y + this.sizeY, z + this.sizeZ) == 0) != this.underground) p++;
        return p > 2;
    }

    public boolean canPlaceRoom(World world, int x, int y, int z, HashMap<ChunkCoordinates, AetherDungeonRoom> roomMap) {
        if (!canPlaceRoom(world, x, y, z)) return false;
        for (ChunkCoordinates roomCoords : roomMap.keySet()) {
            AetherDungeonRoom hisRoom = roomMap.get(roomCoords);
            // TODO: A room bigger than the other can be undetected by the algorythm.
            int hisX = roomCoords.x;
            int hisY = roomCoords.y;
            int hisZ = roomCoords.z;
            if (!(      (x + this.sizeX >= hisX && x <= hisX + hisRoom.sizeX) && // inside xAxis
                        (y + this.sizeY >= hisY && y <= hisY + hisRoom.sizeY) && // inside yAxis
                        (z + this.sizeZ >= hisZ && z <= hisZ + hisRoom.sizeZ)    // inside zAxis
            )) return true;
        }
        return false;
    }

    public void placeRoom(World world, int x, int y, int z) {
        placeEmptyCube(world, x, y, z, x+sizeX, y+sizeY, z+sizeZ, mainBlock, secondaryBlock);
        placeFullCube(world, x+1, y+1, z+1, x+sizeX-1, y+sizeY-1, z+sizeZ-1, Block.getBlock(0), null);
    }

    public static void placeEmptyCube(World world, int x, int y, int z, int maxX, int maxY, int maxZ, Block mainBlock, Block secondaryBlock) {
        placeFullCube(world, x, y, z, maxX, maxY, z, mainBlock, secondaryBlock);
        placeFullCube(world, x, y, z, x, maxY, maxZ, mainBlock, secondaryBlock);
        placeFullCube(world, x, y, z, maxX, y, maxZ, mainBlock, secondaryBlock);
        placeFullCube(world, x, y, maxZ, maxX, maxY, maxZ, mainBlock, secondaryBlock);
        placeFullCube(world, x, maxY, z, maxX, maxY, maxZ, mainBlock, secondaryBlock);
        placeFullCube(world, maxX, y, z, maxX, maxY, maxZ, mainBlock, secondaryBlock);
    }

    public static void placeFullCube(World world, int x, int y, int z, int maxX, int maxY, int maxZ, Block mainBlock, Block secondaryBlock) {
        Random random = new Random();
        for (int i = x; i <= maxX; i++) {
            for (int j = y; j <= maxY; j++) {
                for (int k = z; k <= maxZ; k++) {
                    boolean secondary = random.nextInt(25) == 0;
                    world.setBlock(i, j, k,
                            (secondary && secondaryBlock != null) ? secondaryBlock.id : (mainBlock == null) ? 0 : mainBlock.id);
                }
            }
        }
    }
}
