package teamport.aether.world.generate.feature.components.dungeon.bronze;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureBlock.wfb;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class TallRoom extends BaseBronzeRoom {
    public static BlockPallet ROOM_PALLET = new BlockPallet();

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

    public void makeShell() {
        room.add(drawShell(random, ROOM_PALLET, SOUTH, width, UP, height, EAST, width, x, y, z, true));
        room.add(drawVolume(0, 0, SOUTH, width - 2, UP, height - 2, EAST, width - 2, x + 1, y + 1, z + 1, true));

        List<WorldFeaturePoint> pillar = new ArrayList<>();
        pillar.add(wfp(x + 3, y, z + 3));
        pillar.add(wfp(x + 3, y, z + 7));
        pillar.add(wfp(x + 7, y, z + 3));
        pillar.add(wfp(x + 7, y, z + 7));
        Collections.shuffle(pillar, random);

        int pillar_height = height - 10;
        int index = 0;
        for (int i = 0; i < 6; i++) {
            index = index % 4;
            WorldFeaturePoint point = pillar.get(index++);
            decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 2, EAST, 2, point.x, y + pillar_height, point.z, true));
            if (pillar_height < 6) {
                decoration.add(drawVolume(random, ROOM_PALLET, SOUTH, 2, EAST, 2, DOWN, pillar_height, point.x, y + pillar_height, point.z, true));
                pillar_height -= 4;
                continue;
            }
            decoration.add(wfb(point.x, y - 1 + pillar_height, point.z, AetherBlocks.AERCLOUD_WHITE.id(), 0));
            decoration.add(wfb(point.x + 1, y - 1 + pillar_height, point.z, AetherBlocks.AERCLOUD_WHITE.id(), 0));
            decoration.add(wfb(point.x, y - 1 + pillar_height, point.z + 1, AetherBlocks.AERCLOUD_WHITE.id(), 0));
            decoration.add(wfb(point.x, y - 2 + pillar_height, point.z, AetherBlocks.AERCLOUD_WHITE.id(), 0));
            pillar_height -= 4;
        }

        chest.add(drawPlane(random, chestOrMimic, SOUTH, 2, EAST, 2, x + 5, y + height - 4, z + 5, true));
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 2, EAST, 2, x + 5, y + height - 5, z + 5, true));

        decoration.add(wfb(x + 5, y + height - 6, z + 5, AetherBlocks.AERCLOUD_WHITE.id(), 0, true));
        decoration.add(wfb(x + 6, y + height - 6, z + 5, AetherBlocks.AERCLOUD_WHITE.id(), 0, true));
        decoration.add(wfb(x + 5, y + height - 6, z + 6, AetherBlocks.AERCLOUD_WHITE.id(), 0, true));
        decoration.add(wfb(x + 5, y + height - 7, z + 5, AetherBlocks.AERCLOUD_WHITE.id(), 0, true));
    }

    @Override
    public void makeRoom() {
        this.makeShell();
    }

}
