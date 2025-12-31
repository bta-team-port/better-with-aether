package teamport.aether.world.feature.dungeon.bronze.component;

import net.minecraft.core.block.Blocks;
import teamport.aether.block.AetherBlocks;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.feature.util.WorldFeatureComponent.*;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;

public class SpikerRoom extends BaseBronzeRoom {
    public SpikerRoom() {
        super();
        this.height = 12;
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 4), UP, 6, SOUTH, 4);
    }

    public void makeShell() {
        room.add(drawHollowShell(random, ROOM_PALLET, SOUTH, width, UP, height, EAST, width, x, y, z, false));
    }

    public void makeSpikePit(int pitHeight) {
        room.add(drawSquareCylinder(random, ROOM_PALLET, SOUTH, 8, EAST, 8, DOWN, pitHeight, x + 2, y, z + 2, false));
        room.add(drawPlane(random, ROOM_PALLET, SOUTH, 6, EAST, 6, x + 3, y - pitHeight + 1, z + 3, false));
        decoration.add(drawPlane(Blocks.SPIKES.id(), 0, SOUTH, 6, EAST, 6, x + 3, y - pitHeight + 2, z + 3, false));
    }

    public void makeHangingChest() {
        decoration.add(drawPlane(AetherBlocks.SLAB_PLANKS_SKYROOT.id(), 2, SOUTH, 4, EAST, 4, x + 4, y + height - 7, z + 4, false));
        chest.add(drawPlane(random, CHEST_OR_MIMIC, SOUTH, 2, EAST, 2, x + 5, y + height - 6, z + 5, false));

        decoration.add(drawPlane(AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, DOWN, 5, EAST, 1, x + 4, y + height - 2, z + 4, true));
        decoration.add(drawPlane(AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, DOWN, 5, EAST, 1, x + 7, y + height - 2, z + 4, true));
        decoration.add(drawPlane(AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, DOWN, 5, EAST, 1, x + 7, y + height - 2, z + 7, true));
        decoration.add(drawPlane(AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, DOWN, 5, EAST, 1, x + 4, y + height - 2, z + 7, true));
    }

    @Override
    public void makeRoom() {
        this.makeShell();
        this.makeSpikePit(this.random.nextBoolean() ? 14 : 4);

        if (this.random.nextInt(3) == 0) {
            this.makeHangingChest();
        }
    }
}
