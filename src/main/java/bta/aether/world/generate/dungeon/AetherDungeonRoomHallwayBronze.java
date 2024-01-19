package bta.aether.world.generate.dungeon;

import bta.aether.block.AetherBlocks;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class AetherDungeonRoomHallwayBronze extends AetherDungeonRoom {
    private final Side side;

    public AetherDungeonRoomHallwayBronze(Side side, int width) {
        super(true, width, 4, 4, AetherBlocks.stoneCarved, AetherBlocks.stoneCarvedLight, null);
        this.side = side;
    }

    @Override
    public void placeRoom(World world, int x, int y, int z) {
        switch (side) {
            case EAST:
                placeFullCube(world, x, y, z, x+sizeX, y+sizeY ,z+sizeZ, null, null);
                placeFullCube(world, x, y-1, z, x+sizeX, y-1 ,z+sizeZ, AetherBlocks.holystone, null);
                break;
            case WEST:
                placeFullCube(world, x-sizeX, y, z, x, y+sizeY ,z+sizeZ, null, null);
                placeFullCube(world, x-sizeX, y-1, z, x, y-1 ,z+sizeZ, AetherBlocks.holystone, null);
                break;
            case SOUTH:
                placeFullCube(world, x, y, z, x+sizeZ, y+sizeY ,z+sizeX, null, null);
                placeFullCube(world, x, y-1, z, x+sizeZ, y-1 ,z+sizeX, AetherBlocks.holystone, null);
                break;
            case NORTH:
                placeFullCube(world, x, y, z-sizeX, x+sizeZ, y+sizeY ,z, null, null);
                placeFullCube(world, x, y-1, z-sizeX, x+sizeZ, y-1 ,z, AetherBlocks.holystone, null);
                break;
        }
    }
}