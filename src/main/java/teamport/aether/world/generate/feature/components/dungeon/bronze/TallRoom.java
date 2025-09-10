package teamport.aether.world.generate.feature.components.dungeon.bronze;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.BlockPallet;

import static net.minecraft.core.util.helper.Direction.*;
import static net.minecraft.core.util.helper.Direction.EAST;
import static net.minecraft.core.util.helper.Direction.SOUTH;
import static net.minecraft.core.util.helper.Direction.UP;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawShell;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawVolume;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfpoint;

public class TallRoom extends BaseBronzeRoom{
    public static BlockPallet ROOM_PALLET = new BlockPallet();
    static {
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE.id(), 0, 85);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT.id(), 0, 5);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, 10);
    }
    public TallRoom() {
        super();
        this.height = 30;
        // level 1
        addDoor(NORTH, wfpoint(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfpoint(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfpoint(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfpoint(0, 1, 4), UP, 6, SOUTH, 4);
        // level 2
        addDoor(NORTH, wfpoint(4, 19, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfpoint(11, 19, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfpoint(4, 19, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfpoint(0, 19, 4), UP, 6, SOUTH, 4);

    }
    public void makeShell() {
        room.add(drawShell(random, ROOM_PALLET, SOUTH, width, UP, height, EAST, width, x, y, z, true));
        room.add(drawVolume(0, 0, SOUTH, width - 2, UP, height - 2, EAST, width - 2, x + 1, y + 1, z + 1, true));
    }

    @Override
    public void makeRoom() {
        this.makeShell();
    }

    @Override
    public void markDoor(Door door) {
        super.markDoor(door);
        for(Door ndoor : doors){
            if(ndoor.p1.y == door.p1.y){
                super.markDoor(ndoor);
                return;
            }
        }
    }
}
