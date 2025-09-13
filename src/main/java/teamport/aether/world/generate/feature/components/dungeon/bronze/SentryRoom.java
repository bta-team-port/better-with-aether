package teamport.aether.world.generate.feature.components.dungeon.bronze;

import static net.minecraft.core.util.helper.Direction.*;
import static net.minecraft.core.util.helper.Direction.EAST;
import static net.minecraft.core.util.helper.Direction.SOUTH;
import static net.minecraft.core.util.helper.Direction.UP;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawShell;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawVolume;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class SentryRoom extends BaseBronzeRoom{

    public SentryRoom() {
        super();
        this.height = 12;
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

    @Override
    public void makeRoom() {

    }
}
