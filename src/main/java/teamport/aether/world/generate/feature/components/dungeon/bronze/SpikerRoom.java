package teamport.aether.world.generate.feature.components.dungeon.bronze;

import net.minecraft.core.block.Blocks;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.BlockPallet;

import static net.minecraft.core.util.helper.Direction.*;
import static net.minecraft.core.util.helper.Direction.UP;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;

public class SpikerRoom extends BaseBronzeRoom {
    public static BlockPallet ROOM_PALLET = new BlockPallet();
    static{
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE.id(), 0, 60);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT.id(), 0, 5);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, 35);
    }
//    public MiddleSpikePitBronzeRoom(World world, Random random, int x, int y, int z) {
//        super(world, random, x, y, z);
//        height += 1;
//    }
    public void makeShell(){
        room.add(drawShell(random, ROOM_PALLET, EAST, 12, UP, height, SOUTH, 12, x, y - 1, z, true));
        room.add(drawVolume(0, 0, EAST, 10, UP, height - 2, SOUTH, 10, x + 1, y, z + 1, true));
    }

    public void makeWalkway(){
        room.add(drawPlane(random, ROOM_PALLET, SOUTH, 10, EAST, 10, x + 1, y, z + 1, true));
    }
    public void makeSpikePit(){
        room.add(drawPlane(Blocks.SPIKES.id(), 0, SOUTH, 6, EAST, 6, x + 3, y, z + 3, true));
    }
    @Override
    public void makeRoom() {
        this.makeShell();
        this.makeWalkway();
        this.makeSpikePit();
    }
}
