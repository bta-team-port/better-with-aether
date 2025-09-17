package teamport.aether.world.generate.feature.components.dungeon.bronze;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawVolume;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawVolumeX;
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
    }


    @Override
    public void markDoor(Door door) {
        super.markDoor(door);
        room.add(drawVolumeX(0, 0, door.p1, wfp().moveInDirection(door.heading.getOpposite()).multiply(8).add(door.p2), false));
        room.add(drawVolume(0, 0, SOUTH, 4, UP, 6, EAST, 4, x + 4, y + 1, x + 4, false));
        this.placeRoom();
    }
}
