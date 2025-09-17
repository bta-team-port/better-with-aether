package teamport.aether.world.generate.feature.components.dungeon.bronze;

import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import static net.minecraft.core.util.helper.Direction.*;
import static net.minecraft.core.util.helper.Direction.EAST;
import static net.minecraft.core.util.helper.Direction.SOUTH;
import static net.minecraft.core.util.helper.Direction.UP;
import static net.minecraft.core.util.helper.Direction.WEST;
import static teamport.aether.world.generate.feature.components.WorldFeatureBlock.wfb;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class StairwellRoom extends BaseBronzeRoom {

    public StairwellRoom() {
        super();
        this.roomWeight = 0.25f;
        this.height = 30;
        this.length = this.width = 8;

        addDoor(NORTH, wfp(2, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(7, 1, 2), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(2, 1, 7), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 2), UP, 6, SOUTH, 4);

        addDoor(NORTH, wfp(2, height - 8, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(7, height - 8, 2), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(2, height - 8, 7), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, height - 8, 2), UP, 6, SOUTH, 4);
    }

    @Override
    public void makeRoom() {
        room.add(drawHollowShell(random, ROOM_PALLET, SOUTH, width, UP, 9, EAST, width, x, y, z, true));
        decoration.add(drawShell(random, ROOM_PALLET, SOUTH, width, UP, 9, EAST, width, x, y + height - 9, z, true));
        decoration.add(drawVolume(0, 0, SOUTH, width - 2, UP, 7, EAST, width - 2, x + 1, y + 1 + height - 9, z + 1, false));
        decoration.add(drawSquareCylinder(random, ROOM_PALLET, SOUTH, width - 2, EAST, width - 2, UP, height - 16, x + 1, y + 8, z + 1, false));

        Direction dir = WEST;
        WorldFeaturePoint stepPosition = wfp(x + 2, y + 1, z + 5);
        for (int i = 0; i < (height - 8) << 1; i++) {
            if ((i % 3) == 0) dir = dir.rotate(1);
            stepPosition.moveInDirection(dir);
            decoration.add(wfb(stepPosition.x, stepPosition.y + MathHelper.floor(i / 2.0f), stepPosition.z, AetherBlocks.SLAB_CARVED_STONE.id(), i & 1));
        }

        decoration.add(drawVolume(random, ROOM_PALLET, SOUTH, 2, UP, height, EAST, 2, x + 3, y, z + 3, true));
    }
}
