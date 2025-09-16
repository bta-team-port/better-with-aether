package teamport.aether.world.generate.feature.components.dungeon.bronze;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.BlockPallet;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;


public class TreasureRoom extends BaseBronzeRoom {
    public static BlockPallet chestOrMimic = new BlockPallet();
    static {
        chestOrMimic.addEntry(0, 1);
        chestOrMimic.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 1);
        chestOrMimic.addEntry(AetherBlocks.CHEST_MIMIC.id(), 1);
    }
    public TreasureRoom() {
        super();
        this.topAirTolerance = this.topLiquidTolerance = 0;
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 4), UP, 6, SOUTH, 4);

        addDoor(UP, wfp(5,12,5), EAST, 2, SOUTH, 2);
    }
//    @Override
    public void makeRoom() {
        // Shell
        room.add(drawShell(random, ROOM_PALLET, EAST, 12, UP, height, SOUTH, 12, x, y, z, true));
        room.add(drawVolume(0, 0, EAST, 10, UP, height - 2, SOUTH, 10, x + 1, y + 1, z + 1, true));
        // Plinth
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 4, EAST, 4, x + 4, y + 1, z + 4, true));
        // ChestOrMimic
        chest.add(drawPlane(random, chestOrMimic, SOUTH, 2, EAST, 2, x + 5, y + 2, z + 5, true));
    }
}
