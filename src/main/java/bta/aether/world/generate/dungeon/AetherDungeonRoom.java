package bta.aether.world.generate.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;

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
        if (this.underground) {
            if (world.getBlock(x, y, z).id == 0) return false;
            if (world.getBlock(x + this.sizeX, y, z).id == 0) return false;
            if (world.getBlock(x, y + this.sizeY, z).id == 0) return false;
            if (world.getBlock(x, y, z + this.sizeZ).id == 0) return false;

            if (world.getBlock(x + this.sizeX, y, z + this.sizeZ).id == 0) return false;
            if (world.getBlock(x, y + this.sizeY, z + this.sizeZ).id == 0) return false;
            if (world.getBlock(x + this.sizeX, y + this.sizeY, z).id == 0) return false;
            if (world.getBlock(x + this.sizeX, y + this.sizeY, z + this.sizeZ).id == 0) return false;
        } else {
            if (world.getBlock(x, y, z).id != 0) return false;
            if (world.getBlock(x + this.sizeX, y, z).id != 0) return false;
            if (world.getBlock(x, y + this.sizeY, z).id != 0) return false;
            if (world.getBlock(x, y, z + this.sizeZ).id != 0) return false;

            if (world.getBlock(x + this.sizeX, y, z + this.sizeZ).id != 0) return false;
            if (world.getBlock(x, y + this.sizeY, z + this.sizeZ).id != 0) return false;
            if (world.getBlock(x + this.sizeX, y + this.sizeY, z).id != 0) return false;
            if (world.getBlock(x + this.sizeX, y + this.sizeY, z + this.sizeZ).id != 0) return false;
        }
        return true;
    }

    public void placeRoom(World world, int x, int y, int z) {
        placeEmptyCube(world, x, y, z, x+sizeX, y+sizeY, z+sizeZ, mainBlock, secondaryBlock);
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
        if (mainBlock == null) return;
        Random random = new Random();
        for (int i = x; i <= maxX; i++) {
            for (int j = y; j <= maxY; j++) {
                for (int k = z; k <= maxZ; k++) {
                    boolean secondary = random.nextInt(25) == 0;
                    world.setBlockWithNotify(i, j, k,
                            (secondary && secondaryBlock != null) ? secondaryBlock.id : mainBlock.id);
                }
            }
        }
    }
}
