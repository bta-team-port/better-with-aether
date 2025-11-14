package teamport.aether.world.feature.dungeon.bronze.component;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.feature.util.WorldFeatureComponent.*;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;


public class TreasureRoom extends BaseBronzeRoom {
    public TreasureRoom() {
        super();
        this.topAirTolerance = this.topLiquidTolerance = 0;
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 4), UP, 6, SOUTH, 4);
    }

    @Override
    public void makeRoom() {
        // Shell
        room.add(drawShell(random, ROOM_PALLET, EAST, 12, UP, height, SOUTH, 12, x, y, z, false));
        room.add(drawVolume(0, 0, EAST, 10, UP, height - 2, SOUTH, 10, x + 1, y + 1, z + 1, false));
        // Plinth
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 4, EAST, 4, x + 4, y + 1, z + 4, false));
        // ChestOrMimic
        chest.add(drawPlane(random, CHEST_OR_MIMIC, SOUTH, 2, EAST, 2, x + 5, y + 2, z + 5, false));
    }
}
