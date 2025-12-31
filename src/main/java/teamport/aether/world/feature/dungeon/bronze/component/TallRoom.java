package teamport.aether.world.feature.dungeon.bronze.component;

import teamport.aether.block.AetherBlocks;
import teamport.aether.world.feature.util.BlockPallet;
import teamport.aether.world.feature.util.WorldFeaturePoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.feature.util.WorldFeatureBlock.wfb;
import static teamport.aether.world.feature.util.WorldFeatureComponent.*;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;

public class TallRoom extends BaseBronzeRoom {
    private static final BlockPallet ROOM_PALLET = new BlockPallet();

    static {
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE.id(), 0, 85);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT.id(), 0, 5);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, 10);
    }

    public TallRoom() {
        super();
        this.height = 30;
        this.roomWeight = 0.5F;
        this.airTolerance = this.liquidTolerance = 0.75F;
        this.topAirTolerance = this.bottomAirTolerance = this.topLiquidTolerance = this.bottomLiquidTolerance = 1.0F;
        // level 1
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 4), UP, 6, SOUTH, 4);
        // level 2
        addDoor(NORTH, wfp(4, 19, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 19, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 19, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 19, 4), UP, 6, SOUTH, 4);
    }

    private void makeShell() {
        room.add(drawShell(random, ROOM_PALLET, SOUTH, width, UP, height, EAST, width, x, y, z, false));
        room.add(drawVolume(0, 0, SOUTH, width - 2, UP, height - 2, EAST, width - 2, x + 1, y + 1, z + 1, false));

        List<WorldFeaturePoint> pillar = new ArrayList<>();
        pillar.add(wfp(x + 3, y, z + 3));
        pillar.add(wfp(x + 3, y, z + 7));
        pillar.add(wfp(x + 7, y, z + 3));
        pillar.add(wfp(x + 7, y, z + 7));
        Collections.shuffle(pillar, random);

        int pillarheight = height - 10;
        int index = 0;
        for (int i = 0; i < 6; i++) {
            index = index % 4;
            WorldFeaturePoint point = pillar.get(index++);
            decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 2, EAST, 2, point.getX(), y + pillarheight, point.getZ(), false));
            if (pillarheight < 6) {
                decoration.add(drawVolume(random, ROOM_PALLET, SOUTH, 2, EAST, 2, DOWN, pillarheight, point.getX(), y + pillarheight, point.getZ(), false));
                pillarheight -= 4;
                continue;
            }
            decoration.add(wfb(point.getX(), y - 1 + pillarheight, point.getZ(), AetherBlocks.AERCLOUD_WHITE.id(), 0));
            decoration.add(wfb(point.getX() + 1, y - 1 + pillarheight, point.getZ(), AetherBlocks.AERCLOUD_WHITE.id(), 0));
            decoration.add(wfb(point.getX(), y - 1 + pillarheight, point.getZ() + 1, AetherBlocks.AERCLOUD_WHITE.id(), 0));
            decoration.add(wfb(point.getX(), y - 2 + pillarheight, point.getZ(), AetherBlocks.AERCLOUD_WHITE.id(), 0));
            pillarheight -= 4;
        }

        chest.add(drawPlane(random, CHEST_OR_MIMIC, SOUTH, 2, EAST, 2, x + 5, y + height - 4, z + 5, false));
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 2, EAST, 2, x + 5, y + height - 5, z + 5, false));

        decoration.add(wfb(x + 5, y + height - 6, z + 5, AetherBlocks.AERCLOUD_WHITE.id(), 0, false));
        decoration.add(wfb(x + 6, y + height - 6, z + 5, AetherBlocks.AERCLOUD_WHITE.id(), 0, false));
        decoration.add(wfb(x + 5, y + height - 6, z + 6, AetherBlocks.AERCLOUD_WHITE.id(), 0, false));
        decoration.add(wfb(x + 5, y + height - 7, z + 5, AetherBlocks.AERCLOUD_WHITE.id(), 0, false));
    }

    @Override
    public void makeRoom() {
        this.makeShell();
    }

}
