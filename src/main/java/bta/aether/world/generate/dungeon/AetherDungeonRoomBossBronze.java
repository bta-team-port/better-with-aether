package bta.aether.world.generate.dungeon;

import bta.aether.block.AetherBlocks;
import net.minecraft.core.world.World;

public class AetherDungeonRoomBossBronze extends AetherDungeonRoom implements IAetherDungeonBossRoom {
    public AetherDungeonRoomBossBronze() {
        super(true, 16, 13, 16, AetherBlocks.stoneCarved, AetherBlocks.stoneCarvedLight, null);
    }

    @Override
    public void placeRoom(World world, int x, int y, int z) {
        super.placeRoom(world, x, y, z);
        for (int i = 5; i < sizeX-4; i++) {
            for (int k = 5; k < sizeZ - 4; k++) {
                if ((x+i > x + 5 && x+i < x + sizeX - 5) && (z+k > z + 5 && z+k < z + sizeZ - 5))
                    world.setBlock(x + i, y + 2, z + k, mainBlock.id);
                world.setBlock(x + i, y + 1, z + k, mainBlock.id);
            }
        }
    }
}
