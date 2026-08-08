package teamport.aether.world.feature.dungeon.bronze.component;

import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import teamport.aether.block.AetherBlocks;
import teamport.aether.world.feature.util.WorldFeatureComponent;
import teamport.aether.world.feature.util.WorldFeaturePoint;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.feature.util.WorldFeatureBlock.wfb;
import static teamport.aether.world.feature.util.WorldFeatureComponent.*;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;

public class StairwellRoom extends BaseBronzeRoom {
    public StairwellRoom() {
        super();
        this.roomWeight = 0.25f;
        this.height = 30;
        this.length = this.width = 8;
        this.topAirTolerance = this.bottomAirTolerance = this.topLiquidTolerance = this.bottomLiquidTolerance = 1.0f;
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
        room.add(drawHollowShell(random, ROOM_PALLET, SOUTH, width, UP, 9, EAST, width, x, y, z, false));
        room.add(drawHollowShell(random, ROOM_PALLET, SOUTH, width, UP, 9, EAST, width, x, y + height - 9, z, false));

        decoration.add(drawSquareCylinder(random, ROOM_PALLET, SOUTH, width - 2, EAST, width - 2, UP, height - 16, x + 1, y + 8, z + 1, false));

        Direction dir = horizontal[random.nextInt(4)];

        WorldFeatureComponent staircase = new WorldFeatureComponent();
        WorldFeaturePoint offset;

        switch (dir) {
            case WEST:
                decoration.add(drawVolume(random, ROOM_PALLET, SOUTH, 4, UP, 1, EAST, 1, x + 5, y + height - 9, z + 2, false));
                offset = wfp(0, 0, 0);
                break;

            case SOUTH:
                decoration.add(drawVolume(random, ROOM_PALLET, SOUTH, 1, UP, 1, EAST, 4, x + 2, y + height - 9, z + 2, false));
                offset = wfp(3, 0, 0);
                break;

            case EAST:
                decoration.add(drawVolume(random, ROOM_PALLET, SOUTH, 4, UP, 1, EAST, 1, x + 2, y + height - 9, z + 2, false));
                offset = wfp(3, 0, -3);
                break;

            case NORTH:
                decoration.add(drawVolume(random, ROOM_PALLET, SOUTH, 1, UP, 1, EAST, 4, x + 2, y + height - 9, z + 5, false));
                offset = wfp(0, 0, -3);
                break;

            default:
                offset = wfp(0, 0, 0);
        }

        WorldFeaturePoint stepPosition = wfp(x + 2, y + 1, z + 5);
        for (int i = 0; i < (height - 9) << 1; i++) {
            if ((i % 3) == 0) dir = dir.rotateY(-1);
            stepPosition.moveInDirection(dir);
            staircase.add(wfb(stepPosition.getX(), stepPosition.getY() + MathHelper.floor(i / 2.0f), stepPosition.getZ(), AetherBlocks.SLAB_CARVED_STONE.id(), i & 1));
        }

        staircase.moveByOffset(offset);
        decoration.add(staircase);
        decoration.add(drawVolume(random, ROOM_PALLET, SOUTH, 2, UP, height, EAST, 2, x + 3, y, z + 3, false));
    }
}
