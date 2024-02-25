package bta.aether.world.generate.feature;

import bta.aether.block.AetherBlocks;
import bta.aether.world.generate.BlockPallet;
import bta.aether.world.generate.WorldFeatureAetherDungeonBase;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;

import java.util.Random;

public class WorldFeatureAetherDungeonSilver extends WorldFeatureAetherDungeonBase {
    private static BlockPallet angelic = new BlockPallet();
    private static BlockPallet holystone = new BlockPallet();
    static {
        angelic.addEntry(AetherBlocks.stoneAngelic.id, 0, 95);
        angelic.addEntry(AetherBlocks.stoneAngelicLight.id, 0, 5);

        holystone.addEntry(AetherBlocks.holystone.id, 0, 90);
        holystone.addEntry(AetherBlocks.holystoneMossy.id, 0, 10);
    }
    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        for (int i = 0; i < 120; i++) {
            new WorldFeatureClouds(6 + random.nextInt(10), AetherBlocks.aercloudWhite.id, false).generate(world, random, x + 5 - random.nextInt(40), y - 2 - random.nextInt(5), z - 5 + random.nextInt(65));
        }

        // clear the volume of the structure of blocks
        drawVolume(world, 0, 0, Direction.SOUTH, 55,Direction.UP, 30, Direction.WEST, 30, x, y, z, true);

        // holystone base
        int[] volume = drawVolume(world, random, holystone, Direction.SOUTH, 55,Direction.DOWN, 5, Direction.WEST, 30, x, y, z, false);

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
        drawShell(world, random, angelic, Direction.SOUTH, 22,Direction.UP, 16, Direction.WEST, 22, x - 4, y, z + 4, true);
        drawShell(world, random, angelic, Direction.NORTH, 26,Direction.UP, 16, Direction.EAST, 22, volume[0] + 4, y, volume[2] - 5, false);

        // Entrance hole into boss room
        drawPlane(world, 0, 0,Direction.UP, 2, Direction.WEST, 2, x - 21, y + 1, z + 25, true);

        //// Throne room
        drawPlane(world, random, angelic, Direction.WEST, 22, Direction.SOUTH, 25, x - 4, y + 1, z + 26, false);

        // Big floor semicircle (plus oh god this code is awful 💀)
        drawPlane(world, 0, 0, Direction.WEST, 20, Direction.SOUTH, 4, x - 5, y + 1, z + 26, false);
        drawPlane(world, 0, 0, Direction.WEST, 18, Direction.SOUTH, 1, x - 6, y + 1, z + 30, false);
        drawPlane(world, 0, 0, Direction.WEST, 16, Direction.SOUTH, 2, x - 7, y + 1, z + 31, false);
        drawPlane(world, 0, 0, Direction.WEST, 14, Direction.SOUTH, 1, x - 8, y + 1, z + 33, false);
        drawPlane(world, 0, 0, Direction.WEST, 10, Direction.SOUTH, 1, x - 10, y + 1, z + 34, false);
        drawPlane(world, 0, 0, Direction.WEST, 4, Direction.SOUTH, 1, x - 13, y + 1, z + 35, false);

        // Fountains
        createFountain(world, random, x - 5, y + 2, z + 33, Direction.WEST);
        createFountain(world, random, x - 24, y + 2, z + 33, Direction.EAST);

        // Tree pods
        for (int i = 0; i < 2; i++) {
            int bx = x - 6 - i * 15;
            int bz = z + 45;
            drawPlane(world, random, angelic,Direction.WEST, 3, Direction.SOUTH, 3, bx, y + 2, bz, true);
            setBlock(world, bx - 1, y + 2, bz + 1, AetherBlocks.dirtAether.id, 0, true);
            setBlock(world, bx - 1, y + 3, bz + 1, AetherBlocks.saplingOakGolden.id, 0, false);

            setBlock(world, bx, y + 3, bz,AetherBlocks.torchAmbrosium.id, 0, true);
            setBlock(world, bx - 2, y + 3, bz,AetherBlocks.torchAmbrosium.id, 0, true);
            setBlock(world, bx, y + 3, bz + 2, AetherBlocks.torchAmbrosium.id, 0, true);
            setBlock(world, bx - 2, y + 3, bz + 2, AetherBlocks.torchAmbrosium.id, 0, true);
        }

        // Throne
        drawPlane(world, random, angelic, Direction.WEST, 8, Direction.SOUTH, 6, x - 11, y + 2, z + 44, true);
        drawShell(world, random, angelic, Direction.WEST, 4, Direction.NORTH, 4, Direction.DOWN, 4, x - 13, y + 2, z + 44, true);
        // Chest hole
        drawVolume(world, 0, 0, Direction.WEST, 2, Direction.NORTH, 2, Direction.DOWN, 2, x - 14, y + 1, z + 43, true);
        setBlock(world, x - 15, y, z + 42, AetherBlocks.dungeonChestLocked.id, 0, true);

        setBlock(world, x - 11, y + 3, z + 44, AetherBlocks.torchAmbrosium.id, 0, true);
        setBlock(world, x - 11, y + 3, z + 49, AetherBlocks.torchAmbrosium.id, 0, true);
        setBlock(world, x - 18, y + 3, z + 49, AetherBlocks.torchAmbrosium.id, 0, true);
        setBlock(world, x - 18, y + 3, z + 44, AetherBlocks.torchAmbrosium.id, 0, true);

        drawPlane(world, random, angelic, Direction.WEST, 4, Direction.UP, 6, x - 13, y + 3, z + 49, true);
        drawVolume(world, random, angelic, Direction.WEST, 4, Direction.NORTH, 4, Direction.UP, 2, x - 13, y + 3, z + 49, true);
        drawPlane(world, Block.wool.id, 11, Direction.WEST, 2, Direction.NORTH, 2, x - 14, y + 4, z + 48, true);
        drawLine(world, random, angelic, Direction.NORTH, 3, x - 13, y + 5, z + 48, true);
        drawLine(world, random, angelic, Direction.NORTH, 3, x - 16, y + 5, z + 48, true);

        // Ceiling lights
        createLight(world, random, x - 10, y + 14, z + 28);
        createLight(world, random, x - 19, y + 14, z + 28);
        createLight(world, random, x - 10, y + 14, z + 43);
        createLight(world, random, x - 19, y + 14, z + 43);

        // Staircase
        drawPlane(world, 0, 0, Direction.WEST, 2, Direction.DOWN, 4, x - 14, y, z, false);
        drawPlane(world, 0, 0, Direction.WEST, 2, Direction.DOWN, 3, x - 14, y, z + 1, false);
        drawPlane(world, 0, 0, Direction.WEST, 2, Direction.DOWN, 2, x - 14, y, z + 2, false);
        drawPlane(world, 0, 0, Direction.WEST, 2, Direction.DOWN, 1, x - 14, y, z + 3, false);

        // Roof
        for (int i = 0; i < 7; i++) {
            drawPlane(world, random, angelic, Direction.SOUTH, 57, Direction.WEST, 32 - 4 * i, x + 1 - 2 * i, y + 16 + i, z - 1, true);
        }

        // Pillars
        for (int i = 0; i < 14; i++) {
            createPillar(world, random, x, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4);
            createPillar(world, random, x - 27, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4);
            if (i == 0 || i == 13){
                createPillar(world, random, x - 4, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4);
                createPillar(world, random, x - 8, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4);

                createPillar(world, random, x - 23, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4);
                createPillar(world, random, x - 19, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4);
            }
        }
        // Entrance hole into building
        drawPlane(world, 0, 0, Direction.WEST, 2, Direction.UP, 2, x - 14, y + 1, z + 4, false);

        return true;
    }
    protected void createFountain(World world, Random random, int x, int y, int z, Direction directionEW){
        int[] walls = new int[]{2, 3, 4, 4, 4, 4, 3, 2};
        boolean[] torches = new boolean[]{false, false, true, false, false, true, false, false};
        int[] water = new int[]{0, 2, 3, 3, 3, 3, 2, 0};
        for (int i = 0; i < walls.length; i++) {
            int[] end = drawLine(world, random, angelic, directionEW, walls[i], x, y, z + i, false);
            if (torches[i]){
                setBlock(world, end[0], end[1] + 1, end[2], AetherBlocks.torchAmbrosium.id, 0, true);
            }
            if (water[i] == 0) continue;
            drawLine(world, Block.fluidWaterStill.id, 0, directionEW, water[i], x, y, z + i, false);
        }
    }
    protected void createLight(World world, Random random, int x, int y, int z){
        setBlock(world, x, y, z, Block.fencePlanksOak.id, 0, false);
        setBlock(world, x, y - 1, z, Block.fencePlanksOak.id, 0, false);
        setBlock(world, x, y - 2, z, Block.fencePlanksOak.id, 0, false);
        setBlock(world, x, y - 3, z, Block.glowstone.id, 0, true);
        setBlock(world, x, y - 4, z, Block.glowstone.id, 0, true);
        setBlock(world, x - 1, y - 4, z, Block.glowstone.id, 0, true);
        setBlock(world, x + 1, y - 4, z, Block.glowstone.id, 0, true);
        setBlock(world, x, y - 4, z - 1, Block.glowstone.id, 0, true);
        setBlock(world, x, y - 4, z + 1, Block.glowstone.id, 0, true);
        setBlock(world, x, y - 5, z, Block.glowstone.id, 0, true);
    }
    protected void createPillar(World world, Random random, int x, int y, int z){
        drawPlane(world, random, angelic, Direction.SOUTH, 3, Direction.WEST, 3, x, y, z, false);
        drawPlane(world, random, angelic, Direction.SOUTH, 3, Direction.WEST, 3, x, y + 14, z, false);
        drawLine(world, AetherBlocks.pillar.id, 0, Direction.UP, 13, x + Direction.WEST.getOffsetX(), y, z + Direction.SOUTH.getOffsetZ(), false);
        setBlock(world, x + Direction.WEST.getOffsetX(), y + 13, z + Direction.SOUTH.getOffsetZ(), AetherBlocks.pillarTop.id, 0, false);
    }
    protected void createRoom(World world, Random random, int x, int y, int z, boolean forceOpen){
        drawShell(world, random, angelic, Direction.SOUTH, 8, Direction.UP, 6, Direction.WEST, 8, x, y, z, true);
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
        drawPlane(world, random, angelic, Direction.SOUTH, 2, Direction.WEST, 2, x - 3, y + 1, z + 3, true);

        int chestCount = 0;
        if (random.nextInt(3) == 0){
            chestCount++;
            setBlock(world,x - 3, y + 2, z + 3, random.nextInt(2) == 0 ? Block.chestPlanksOak.id : AetherBlocks.chestMimic.id, 0, true);
        }
        if (random.nextInt(3) == 0){
            chestCount++;
            setBlock(world,x - 4, y + 2, z + 3, random.nextInt(2) == 0 ? Block.chestPlanksOak.id : AetherBlocks.chestMimic.id, 0, true);
        }
        if (random.nextInt(3) == 0 && chestCount < 2){
            chestCount++;
            setBlock(world,x - 3, y + 2, z + 4, random.nextInt(2) == 0 ? Block.chestPlanksOak.id : AetherBlocks.chestMimic.id, 0, true);
        }
        if (random.nextInt(3) == 0 && chestCount < 2){
            chestCount++;
            setBlock(world,x - 4, y + 2, z + 4, random.nextInt(2) == 0 ? Block.chestPlanksOak.id : AetherBlocks.chestMimic.id, 0, true);
        }
    }
    protected void createStaircaseRoom(World world, Random random, int x, int y, int z, boolean forceWalls, boolean forceOpen){
        if (forceWalls){
            drawShell(world, random, angelic, Direction.SOUTH, 8, Direction.UP, 6, Direction.WEST, 8, x, y, z, true);
        } else {
            createRoom(world, random, x, y, z, forceOpen);
        }
        drawPlane(world, 0, 0, Direction.SOUTH, 4, Direction.WEST, 4, x - 2, y + 5, z + 2, true);
        drawVolume(world, random, angelic, Direction.SOUTH, 2, Direction.WEST, 2, Direction.UP, 9, x - 3, y + 1, z + 3, true);


        setBlock(world, x - 2, y + 1, z + 2, AetherBlocks.slabStoneCarved.id, 0, true);
        setBlock(world, x - 2, y + 1, z + 3, AetherBlocks.slabStoneCarved.id, 1, true);
        setBlock(world, x - 2, y + 2, z + 4, AetherBlocks.slabStoneCarved.id, 0, true);
        setBlock(world, x - 2, y + 1, z + 4, AetherBlocks.slabStoneCarved.id, 2, true);
        setBlock(world, x - 2, y + 2, z + 5, AetherBlocks.slabStoneCarved.id, 1, true);

        setBlock(world, x - 3, y + 3, z + 5, AetherBlocks.slabStoneCarved.id, 0, true);
        setBlock(world, x - 3, y + 2, z + 5, AetherBlocks.slabStoneCarved.id, 2, true);
        setBlock(world, x - 4, y + 3, z + 5, AetherBlocks.slabStoneCarved.id, 1, true);
        setBlock(world, x - 5, y + 4, z + 5, AetherBlocks.slabStoneCarved.id, 0, true);
        setBlock(world, x - 5, y + 3, z + 5, AetherBlocks.slabStoneCarved.id, 2, true);

        setBlock(world, x - 5, y + 4, z + 4, AetherBlocks.slabStoneCarved.id, 1, true);
        setBlock(world, x - 5, y + 5, z + 3, AetherBlocks.slabStoneCarved.id, 0, true);
        setBlock(world, x - 5, y + 4, z + 3, AetherBlocks.slabStoneCarved.id, 2, true);
        setBlock(world, x - 5, y + 5, z + 2, AetherBlocks.slabStoneCarved.id, 1, true);

        setBlock(world, x - 4, y + 5, z + 2, AetherBlocks.slabStoneCarved.id, 1, true);
        setBlock(world, x - 3, y + 5, z + 2, AetherBlocks.slabStoneCarved.id, 1, true);
    }
}
