package teamport.aether.world.generate.feature.components.dungeon.bronze;

import net.minecraft.core.block.BlockLogicIce;
import net.minecraft.core.block.Blocks;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.BlockLogicIceStone;
import teamport.aether.world.generate.feature.BlockPallet;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureBlock.wfb;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class IceRoom extends BaseBronzeRoom {
    public static BlockPallet CHESTORMIMIC = new BlockPallet();
    public static BlockPallet ICEORWATER = new BlockPallet();
    static {
        CHESTORMIMIC.addEntry(AetherBlocks.ICESTONE.id(), 1);
        CHESTORMIMIC.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 1);
        CHESTORMIMIC.addEntry(AetherBlocks.CHEST_MIMIC.id(), 1);

        ICEORWATER.addEntry(Blocks.FLUID_WATER_STILL.id(), 10);
        ICEORWATER.addEntry(AetherBlocks.ICESTONE.id(), 1);
    }
    public IceRoom() {
        super();
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 4), UP, 6, SOUTH, 4);

        addDoor(UP, wfp(5,12,5), EAST, 2, SOUTH, 2);
    }

    @Override
    public void makeRoom() {
        room.add(drawHollowShell(random, ROOM_PALLET, SOUTH, width, UP, height, EAST, width, x, y, z, true));
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, width, EAST, width, x, y - 1, z, true));
        decoration.add(drawPlane(random, ICEORWATER, SOUTH, 6, EAST, 6, x + 3, y, z + 3, true));
        chest.add(drawPlane(random, CHESTORMIMIC, SOUTH, 2, EAST, 2, x + 5, y, z + 5, true));

        decoration.add(wfb(x + 1, y + 1, z + 1, ROOM_PALLET.getRandom(random).first, 0));
        decoration.add(wfb(x + 10, y + 1, z + 1, ROOM_PALLET.getRandom(random).first, 0));
        decoration.add(wfb(x + 1, y + 1, z + 10, ROOM_PALLET.getRandom(random).first, 0));
        decoration.add(wfb(x + 10, y + 1, z + 10, ROOM_PALLET.getRandom(random).first, 0));

        decoration.add(wfb(x + 1, y + 2, z + 1, Blocks.FLUID_WATER_FLOWING.id(), 0, true));
        decoration.add(wfb(x + 10, y + 2, z + 1, Blocks.FLUID_WATER_FLOWING.id(), 0, true));
        decoration.add(wfb(x + 1, y + 2, z + 10, Blocks.FLUID_WATER_FLOWING.id(), 0, true));
        decoration.add(wfb(x + 10, y + 2, z + 10, Blocks.FLUID_WATER_FLOWING.id(), 0, true));

    }
}
