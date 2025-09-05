package teamport.aether.world.generate.feature.components.dungeon.bronze;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.BlockPallet;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawShell;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawVolume;

public class SimpleRoom extends BaseBronzeRoom {
    public static BlockPallet ROOM_PALLET = new BlockPallet();

    static {
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE.id(), 0, 85);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT.id(), 0, 5);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, 10);
    }

//    public SimpleBronzeRoom(World world, Random random, int x, int y, int z) {
//        super(world, random, x, y, z);
//    }

    public void makeShell() {
        room.add(drawShell(random, ROOM_PALLET, EAST, 12, UP, height, SOUTH, 12, x, y, z, true));
        room.add(drawVolume(0, 0, EAST, 10, UP, height - 2, SOUTH, 10, x + 1, y + 1, z + 1, true));
    }

    @Override
    public void makeRoom() {
        this.makeShell();
    }
}
