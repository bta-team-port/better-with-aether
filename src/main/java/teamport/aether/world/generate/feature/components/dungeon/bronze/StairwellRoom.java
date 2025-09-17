package teamport.aether.world.generate.feature.components.dungeon.bronze;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import static net.minecraft.core.util.helper.Direction.*;
import static net.minecraft.core.util.helper.Direction.EAST;
import static net.minecraft.core.util.helper.Direction.SOUTH;
import static net.minecraft.core.util.helper.Direction.UP;
import static net.minecraft.core.util.helper.Direction.WEST;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class StairwellRoom extends BaseBronzeRoom{

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

    private static final WorldFeaturePoint[] steps = {
            wfp(2, 0, 4),
            wfp(4, 0, 4),
            wfp(4, 0, 2),
            wfp(2, 0, 2),
    };

    @Override
    public void makeRoom() {
        room.add(drawShell(random, ROOM_PALLET, SOUTH, width, UP, 9, EAST, width, x, y, z, true));
        room.add(drawVolume(0, 0, SOUTH, width - 2, UP, 7, EAST, width - 2, x + 1, y + 1, z + 1, false));

        room.add(drawShell(random, ROOM_PALLET, SOUTH, width, UP, 9, EAST, width, x, y + height - 9, z, true));
        room.add(drawVolume(0, 0, SOUTH, width - 2, UP, 7, EAST, width - 2, x + 1, y + 1 + height - 9, z + 1, false));

        room.add(drawSquareCylinder(random, ROOM_PALLET, SOUTH, width-2, EAST, width-2, UP, height-16, x+1, y+8, z+1, false));

        for (int i = 0; i < height - 8; i++) {
            WorldFeaturePoint offset = steps[i%4];
            WorldFeaturePoint stepPosition = wfp(x, y + i, z).add(offset);
            room.add(
                drawVolume(
                    AetherBlocks.SLAB_HOLYSTONE_POLISHED.id(), 1,
                    SOUTH, 2,
                    UP, 1,
                    EAST, 2,
                    stepPosition.x, stepPosition.y, stepPosition.z,
                    false
                )
            );
        }

        room.add(drawVolume(random, ROOM_PALLET, SOUTH, 2, UP, height, EAST, 2, x+3, y, z+3, true));
    }
}
