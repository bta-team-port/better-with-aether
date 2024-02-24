package bta.aether.world.generate.feature;

import bta.aether.block.AetherBlocks;
import bta.aether.world.generate.WorldFeatureAetherDungeonBase;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;

import java.util.Random;

public class WorldFeatureAetherDungeonSilver extends WorldFeatureAetherDungeonBase {
    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        // clear the volume of the structure of blocks
        drawVolume(world, 0, 0, Direction.SOUTH, 57,Direction.UP, 30, Direction.WEST, 32, x + 1, y, z - 1, false);
        // holystone base
        int[] volume = drawVolume(world, AetherBlocks.holystone.id, 0, Direction.SOUTH, 55,Direction.DOWN, 5, Direction.WEST, 30, x, y, z, false);

        // generate 3x3x3 grid of rooms
        for (int j = 2; j >= 0; j--) {
            boolean genStairs = false;
            int counter = 0;
            int stairNum = random.nextInt(8);
            if (j < 2){
                genStairs = true;
            }
            for (int i = 0; i < 3; i++) {
                for (int k = 0; k < 3; k++) {
                    int roomX = x - 4 - i * 7;
                    int roomY = y + 5 * j;
                    int roomZ = z + 4 + k * 7;
                    if (counter == stairNum && genStairs){
                        createStaircaseRoom(world, random, roomX, roomY, roomZ, false, true);
                        genStairs = false;
                        continue;
                    }
                    if (i == 2 && k == 2){
                        if (j == 2){
                            createRoom(world, random, roomX, roomY, roomZ, true);
                        } else {
                            createStaircaseRoom(world, random, roomX, roomY, roomZ, true, false);
                        }
                        continue;
                    }
                    if (random.nextInt(3) == 0){
                        createRoom(world, random, roomX, roomY, roomZ, false);
                    } else {
                        createTreasureRoom(world, random, roomX, roomY, roomZ, false);
                    }
                    counter++;
                }
            }
        }

        // Outer walls of dungeon itself
        drawShell(world, AetherBlocks.stoneAngelic.id, 0, Direction.SOUTH, 22,Direction.UP, 16, Direction.WEST, 22, x - 4, y, z + 4, true);
        drawShell(world, AetherBlocks.stoneAngelic.id, 0, Direction.NORTH, 26,Direction.UP, 16, Direction.EAST, 22, volume[0] + 4, y, volume[2] - 5, false);

        // Entrance hole into boss room
        drawPlane(world, 0, 0,Direction.UP, 2, Direction.WEST, 2, x - 4 - 17, y + 1, z + 25, true);

        // Staircase
        drawPlane(world, 0, 0, Direction.WEST, 2, Direction.DOWN, 4, x - 14, y, z, false);
        drawPlane(world, 0, 0, Direction.WEST, 2, Direction.DOWN, 3, x - 14, y, z + 1, false);
        drawPlane(world, 0, 0, Direction.WEST, 2, Direction.DOWN, 2, x - 14, y, z + 2, false);
        drawPlane(world, 0, 0, Direction.WEST, 2, Direction.DOWN, 1, x - 14, y, z + 3, false);

        // Roof
        for (int i = 0; i < 7; i++) {
            drawPlane(world, AetherBlocks.stoneAngelic.id, 0, Direction.SOUTH, 57, Direction.WEST, 32 - 4 * i, x + 1 - 2 * i, y + 16 + i, z - 1, true);
        }

        // Pillars
        for (int i = 0; i < 14; i++) {
            createPillar(world, x, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4);
            createPillar(world, x - 27, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4);
            if (i == 0 || i == 13){
                createPillar(world, x - 4, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4);
                createPillar(world, x - 8, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4);

                createPillar(world, x - 23, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4);
                createPillar(world, x - 19, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4);
            }
        }
        // Entrance hole into building
        drawPlane(world, 0, 0, Direction.WEST, 2, Direction.UP, 2, x - 14, y + 1, z + 4, false);

        return true;
    }
    protected void createPillar(World world, int x, int y, int z){
        drawPlane(world, AetherBlocks.stoneAngelic.id, 0, Direction.SOUTH, 3, Direction.WEST, 3, x, y, z, false);
        drawPlane(world, AetherBlocks.stoneAngelic.id, 0, Direction.SOUTH, 3, Direction.WEST, 3, x, y + 14, z, false);
        drawLine(world, AetherBlocks.pillar.id, 0, Direction.UP, 15, x + Direction.WEST.getOffsetX(), y, z + Direction.SOUTH.getOffsetZ(), false);
    }
    protected void createRoom(World world, Random random, int x, int y, int z, boolean forceOpen){
        drawShell(world, AetherBlocks.stoneAngelic.id, 0, Direction.SOUTH, 8, Direction.UP, 6, Direction.WEST, 8, x, y, z, true);
        if (random.nextInt(2) != 0 || forceOpen){
            drawPlane(world, 0, 0, Direction.UP, 2, Direction.WEST, 2, x - 3, y + 1, z, true);
        }
        if (random.nextInt(2) != 0 || forceOpen){
            drawPlane(world, 0, 0, Direction.UP, 2, Direction.WEST, 2, x - 3, y + 1, z + 7, true);
        }
        if (random.nextInt(2) != 0 || forceOpen){
            drawPlane(world, 0, 0, Direction.UP, 2, Direction.SOUTH, 2, x, y + 1, z + 3, true);
        }
        if (random.nextInt(2) != 0 || forceOpen){
            drawPlane(world, 0, 0, Direction.UP, 2, Direction.SOUTH, 2, x - 7, y + 1, z + 3, true);
        }
    }
    protected void createTreasureRoom(World world, Random random, int x, int y, int z, boolean forceOpen){
        createRoom(world, random, x, y, z, forceOpen);
        drawPlane(world, AetherBlocks.stoneAngelic.id, 0, Direction.SOUTH, 2, Direction.WEST, 2, x - 3, y + 1, z + 3, true);

        int chestCount = 0;
        if (random.nextInt(3) == 0){
            chestCount++;
            setBlock(world,x - 3, y + 2, z + 3, Block.chestPlanksOak.id, 0, true);
        }
        if (random.nextInt(3) == 0){
            chestCount++;
            setBlock(world,x - 4, y + 2, z + 3, Block.chestPlanksOak.id, 0, true);
        }
        if (random.nextInt(3) == 0 && chestCount < 2){
            chestCount++;
            setBlock(world,x - 3, y + 2, z + 4, Block.chestPlanksOak.id, 0, true);
        }
        if (random.nextInt(3) == 0 && chestCount < 2){
            chestCount++;
            setBlock(world,x - 4, y + 2, z + 4, Block.chestPlanksOak.id, 0, true);
        }
    }
    protected void createStaircaseRoom(World world, Random random, int x, int y, int z, boolean forceWalls, boolean forceOpen){
        if (forceWalls){
            drawShell(world, AetherBlocks.stoneAngelic.id, 0, Direction.SOUTH, 8, Direction.UP, 6, Direction.WEST, 8, x, y, z, true);
        } else {
            createRoom(world, random, x, y, z, forceOpen);
        }
        drawPlane(world, 0, 0, Direction.SOUTH, 4, Direction.WEST, 4, x - 2, y + 5, z + 2, true);
        drawVolume(world, AetherBlocks.stoneAngelic.id, 0, Direction.SOUTH, 2, Direction.WEST, 2, Direction.UP, 9, x - 3, y + 1, z + 3, true);


        setBlock(world, x - 2, y + 1, z + 2, Block.slabStonePolished.id, 0, true);
        setBlock(world, x - 2, y + 1, z + 3, Block.slabStonePolished.id, 1, true);
        setBlock(world, x - 2, y + 2, z + 4, Block.slabStonePolished.id, 0, true);
        setBlock(world, x - 2, y + 2, z + 5, Block.slabStonePolished.id, 1, true);

        setBlock(world, x - 3, y + 3, z + 5, Block.slabStonePolished.id, 0, true);
        setBlock(world, x - 4, y + 3, z + 5, Block.slabStonePolished.id, 1, true);
        setBlock(world, x - 5, y + 4, z + 5, Block.slabStonePolished.id, 0, true);

        setBlock(world, x - 5, y + 4, z + 4, Block.slabStonePolished.id, 1, true);
        setBlock(world, x - 5, y + 5, z + 3, Block.slabStonePolished.id, 0, true);
        setBlock(world, x - 5, y + 5, z + 2, Block.slabStonePolished.id, 1, true);

        setBlock(world, x - 4, y + 5, z + 2, Block.slabStonePolished.id, 1, true);
        setBlock(world, x - 3, y + 5, z + 2, Block.slabStonePolished.id, 1, true);
    }
}
