package bta.aether.world.generate.dungeon;

import bta.aether.block.AetherBlocks;
import net.minecraft.core.world.World;

public class AetherDungeonRoomTrappedBronze extends AetherDungeonRoom {
    public AetherDungeonRoomTrappedBronze() {
        super(true, 12, 10, 12, AetherBlocks.stoneCarved, AetherBlocks.stoneCarvedLight, AetherBlocks.trapStoneCarved);
    }

    @Override
    public void placeRoom(World world, int x, int y, int z) {
        super.placeRoom(world, x, y, z);
        placeFullCube(world, x, y ,z, x+sizeX, y, z+sizeZ, mainBlock, trapBlock);
    }
}
