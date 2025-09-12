package teamport.aether.world.generate.feature.components.dungeon.bronze;

import net.minecraft.core.block.Blocks;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.BlockPallet;

import static net.minecraft.core.util.helper.Direction.*;
import static net.minecraft.core.util.helper.Direction.UP;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class SpikerRoom extends BaseBronzeRoom {
    public static BlockPallet ROOM_PALLET = new BlockPallet();
    static{
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE.id(), 0, 60);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT.id(), 0, 5);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, 35);
    }
    public SpikerRoom() {
        super();
        this.height = 13;
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 4), UP, 6, SOUTH, 4);

        addDoor(DOWN, wfp(5,0,5), EAST, 2, SOUTH, 2);
        addDoor(UP, wfp(5,12,5), EAST, 2, SOUTH, 2);
    }
    public void makeShell(){
        room.add(drawShell(random, ROOM_PALLET, SOUTH, width, UP, height, EAST, width, x, y, z, true));
        room.add(drawVolume(0, 0, SOUTH, width - 2, UP, height - 2, EAST, width - 2, x + 1, y + 1, z + 1, true));
    }
    public void makePitBottom(){
        room.add(drawPlane(random, ROOM_PALLET, SOUTH, width - 2, EAST, width - 2, x + 1, y - 1, z + 1, true));
    }
    public void makeSpikePit(){
        room.add(drawPlane(Blocks.SPIKES.id(), 0, SOUTH, 6, EAST, 6, x + 3, y, z + 3, true));
    }
//    @Override
    public void makeRoom() {
        this.makeShell();
        this.makePitBottom();
        this.makeSpikePit();
    }
}
