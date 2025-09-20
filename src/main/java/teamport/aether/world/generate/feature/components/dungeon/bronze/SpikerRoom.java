package teamport.aether.world.generate.feature.components.dungeon.bronze;

import net.minecraft.core.block.Blocks;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.BlockPallet;

import static net.minecraft.core.util.helper.Direction.*;
import static net.minecraft.core.util.helper.Direction.UP;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class SpikerRoom extends BaseBronzeRoom {
    public SpikerRoom() {
        super();
        this.height = 12;
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST,  wfp(0, 1, 4), UP, 6, SOUTH, 4);

        addDoor(DOWN, wfp(5,1,5), EAST, 2, SOUTH, 2);
        addDoor(UP, wfp(5,this.height,5), EAST, 2, SOUTH, 2);
    }

    public void makeShell() {
        room.add(drawHollowShell(random, ROOM_PALLET, SOUTH, width, UP, height, EAST, width, x, y, z, true));
        room.add(drawSquareCylinder(random, ROOM_PALLET, SOUTH, 8, EAST ,8, DOWN, 14, x + 2, y, z + 2, true));
        room.add(drawPlane(random, ROOM_PALLET, SOUTH,6, EAST, 6, x + 3, y - 13, z + 3, false));
    }

    public void makeSpikePit() {
        decoration.add(drawPlane(Blocks.SPIKES.id(), 0, SOUTH, 6, EAST, 6, x + 3, y -12, z + 3, false));
    }

    public void makeHangingChest() {
        decoration.add(drawPlane(AetherBlocks.SLAB_PLANKS_SKYROOT.id(), 2, SOUTH, 4, EAST, 4, x + 4, y + height - 7, z + 4, false));
        chest.add(drawPlane(random, chestOrMimic, SOUTH, 2, EAST, 2, x + 5, y + height - 6, z + 5, true));

        decoration.add(drawPlane(AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, DOWN, 5, EAST, 1, x + 4, y + height -2, z + 4, false));
        decoration.add(drawPlane(AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, DOWN, 5, EAST, 1, x + 7, y + height -2, z + 4, false));
        decoration.add(drawPlane(AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, DOWN, 5, EAST, 1, x + 7, y + height -2, z + 7, false));
        decoration.add(drawPlane(AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, DOWN, 5, EAST, 1, x + 4, y + height -2, z + 7, false));
    }

    @Override
    public void makeRoom() {
        this.makeShell();
        this.makeSpikePit();

        if (this.random.nextInt(3) == 0) {
            this.makeHangingChest();
        }
    }
}
