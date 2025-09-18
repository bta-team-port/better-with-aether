package teamport.aether.world.generate.feature.components.dungeon.bronze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawVolume;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class HallwayRoom extends BaseBronzeRoom {


    public HallwayRoom() {
        super();
        this.roomWeight = 0.25F;
        this.airTolerance = this.liquidTolerance = 0.85F;
        this.topAirTolerance = this.bottomAirTolerance = this.topLiquidTolerance = this.bottomLiquidTolerance = 0.85F;
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 4), UP, 6, SOUTH, 4);
    }

    @Override
    public void makeRoom() {
        List<Door> doorList = new ArrayList<>(doors);
        Collections.shuffle(doorList, random);
        int end = random.nextInt(2) + 2;
        for(int c = 0; c < end; c++){
            Door closingDoor = doorList.get(c);
            room.add(drawVolume(0, 0, closingDoor.p1, wfp(0,0,0).moveInDirection(closingDoor.heading.getOpposite()).multiply(6).add(closingDoor.p2), true));
            closingDoor.mark = false;
        }
        for(int c = end; c < doorList.size(); c++){
            doorList.get(c).mark = true;
        }
    }


    @Override
    public void markDoor(Door door) {
        super.markDoor(door);
        room.add(drawVolume(0, 0, door.p1, wfp().moveInDirection(door.heading.getOpposite()).multiply(7).add(door.p2), false));
//        room.add(drawVolume(0, 0, SOUTH, 4, UP, 6, EAST, 4, x + 4, y + 1, x + 4, false));
        this.placeRoom();
    }
}
