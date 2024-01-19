package bta.aether.world.generate.dungeon;

import bta.aether.block.AetherBlocks;
import net.minecraft.core.world.World;

public class AetherDungeonRoomChestBronze extends AetherDungeonRoom {
    public AetherDungeonRoomChestBronze() {
        super(true, 12, 10, 12, AetherBlocks.stoneCarved, AetherBlocks.stoneCarvedLight, null);
    }

    @Override
    public void placeRoom(World world, int x, int y, int z) {
        super.placeRoom(world, x, y, z);
        int mid = (sizeX/2);
        for (int i = mid-1; i < mid+3; i++) {
            for (int k = mid-1; k < mid+3; k++) {
                world.setBlock(x + i, y + 1, z + k, mainBlock.id);
            }
        }
    }
}
