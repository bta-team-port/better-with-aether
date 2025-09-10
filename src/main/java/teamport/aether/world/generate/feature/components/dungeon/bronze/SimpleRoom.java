package teamport.aether.world.generate.feature.components.dungeon.bronze;

import net.minecraft.core.world.World;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.BlockPallet;

import java.util.Random;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawShell;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawVolume;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfpoint;

public class SimpleRoom extends BaseBronzeRoom {
    public static BlockPallet ROOM_PALLET = new BlockPallet();
    static {
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE.id(), 0, 85);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT.id(), 0, 5);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, 10);
    }
    public SimpleRoom() {
        super();
        addDoor(NORTH, wfpoint(4, 1, 0), UP, 6, EAST, 4);
//        addDoor(EAST, wfpoint(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfpoint(4, 1, 11), UP, 6, EAST, 4);
//        addDoor(WEST, wfpoint(0, 1, 4), UP, 6, SOUTH, 4);

        addDoor(DOWN, wfpoint(5,0,5), EAST, 2, SOUTH, 2);
        addDoor(UP, wfpoint(5,12,5), EAST, 2, SOUTH, 2);
    }
    public void makeShell() {
        room.add(drawShell(random, ROOM_PALLET, SOUTH, width, UP, height, EAST, width, x, y, z, true));
        room.add(drawVolume(0, 0, SOUTH, width - 2, UP, height - 2, EAST, width - 2, x + 1, y + 1, z + 1, true));
    }
//    @Override
    public void makeRoom() {
        this.makeShell();
    }
}
