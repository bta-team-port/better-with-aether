package teamport.aether.world.generate.feature.components.dungeon.bronze;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawVolume;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class CrossRoadRoom extends BaseBronzeRoom{

    public CrossRoadRoom() {
        super();
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 4), UP, 6, SOUTH, 4);

        addDoor(DOWN, wfp(5,0,5), EAST, 2, SOUTH, 2);
        addDoor(UP, wfp(5,12,5), EAST, 2, SOUTH, 2);
    }

//    @Override
    public void makeRoom() {
        room.add(drawVolume(0, 0, SOUTH, width, UP, 8, EAST, width / 2, x + 3, y + 1, z, true));
        room.add(drawVolume(0, 0, EAST, width, UP, 8, SOUTH, width / 2, x , y + 1, z + 3, true));
    }
}
