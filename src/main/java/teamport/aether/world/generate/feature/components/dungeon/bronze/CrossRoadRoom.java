package teamport.aether.world.generate.feature.components.dungeon.bronze;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawVolume;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfpoint;

public class CrossRoadRoom extends BaseBronzeRoom{

    public CrossRoadRoom() {
        super();
        addDoor(NORTH, wfpoint(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfpoint(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfpoint(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfpoint(0, 1, 4), UP, 6, SOUTH, 4);

        addDoor(DOWN, wfpoint(5,0,5), EAST, 2, SOUTH, 2);
        addDoor(UP, wfpoint(5,12,5), EAST, 2, SOUTH, 2);
    }

//    @Override
    public void makeRoom() {
        room.add(drawVolume(0, 0, SOUTH, width, UP, 8, EAST, width / 2, x + 3, y + 1, z, true));
        room.add(drawVolume(0, 0, EAST, width, UP, 8, SOUTH, width / 2, x , y + 1, z + 3, true));
    }
}
