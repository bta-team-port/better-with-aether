package teamport.aether.world.feature.dungeon.bronze.component;

import teamport.aether.block.AetherBlocks;
import teamport.aether.helper.unboxed.IntPair;
import teamport.aether.world.feature.util.BlockPallet;
import teamport.aether.world.feature.util.WorldFeaturePoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.feature.util.WorldFeatureBlock.wfb;
import static teamport.aether.world.feature.util.WorldFeatureComponent.*;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;

public class StorageRoom extends BaseBronzeRoom {
    private static final BlockPallet DISPLAY = new BlockPallet();

    static {
        DISPLAY.addEntry(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE.id(), 10);
        DISPLAY.addEntry(AetherBlocks.ORE_ZANITE_HOLYSTONE.id(), 5);
        DISPLAY.addEntry(AetherBlocks.ORE_GRAVITITE_HOLYSTONE.id(), 1);
    }

    public StorageRoom() {
        super();
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 4), UP, 6, SOUTH, 4);
    }

    @Override
    public void makeRoom() {
        //room
        room.add(drawHollowShell(random, ROOM_PALLET, SOUTH, width, UP, height, EAST, width, x, y, z, false));
        // corner plinth
        this.placeCornerDecoration();
    }

    private void placeCornerDecoration() {
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 2, EAST, 2, x + 1, y + 1, z + 1, false));
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 2, EAST, 2, x + 1, y + 1, z + 9, false));
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 2, EAST, 2, x + 9, y + 1, z + 1, false));
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 2, EAST, 2, x + 9, y + 1, z + 9, false));
        List<WorldFeaturePoint[]> pointList = new ArrayList<>();
        pointList.add(new WorldFeaturePoint[]{wfp(x + 1, y + 2, z + 1), wfp(x + 2, y + 2, z + 1), wfp(x + 1, y + 2, z + 2)});
        pointList.add(new WorldFeaturePoint[]{wfp(x + 9, y + 2, z + 1), wfp(x + 10, y + 2, z + 1), wfp(x + 10, y + 2, z + 2)});
        pointList.add(new WorldFeaturePoint[]{wfp(x + 1, y + 2, z + 9), wfp(x + 1, y + 2, z + 10), wfp(x + 2, y + 2, z + 10)});
        pointList.add(new WorldFeaturePoint[]{wfp(x + 9, y + 2, z + 10), wfp(x + 10, y + 2, z + 10), wfp(x + 10, y + 2, z + 9)});
        Collections.shuffle(pointList, random);
        for (int i = 0; i < 2; i++) {
            WorldFeaturePoint[] points = pointList.get(i);
            for (WorldFeaturePoint point : points) {
                IntPair idMeta = DISPLAY.getRandom(random);
                chest.add(wfb(point.getX(), point.getY(), point.getZ(), idMeta.getFirst(), idMeta.getSecond(), true));
            }
        }
        for (int i = 2; i < pointList.size(); i++) {
            WorldFeaturePoint[] points = pointList.get(i);
            for (WorldFeaturePoint point : points) {
                IntPair idMeta = CHEST_OR_MIMIC.getRandom(random);
                chest.add(wfb(point.getX(), point.getY(), point.getZ(), idMeta.getFirst(), idMeta.getSecond(), false));
            }
        }
    }
}
