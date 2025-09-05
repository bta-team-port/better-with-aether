package teamport.aether.world.generate.feature.components.dungeon.bronze;

import net.minecraft.core.world.World;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;

import java.util.Random;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;


public class TreasureBronzeRoom extends BaseBronzeRoom {
    public static BlockPallet ROOM_PALLET = new BlockPallet();
    public static BlockPallet chestOrMimic = new BlockPallet();


    static {
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE.id(), 0, 85);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT.id(), 0, 5);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, 10);

        chestOrMimic.addEntry(0, 1);
        chestOrMimic.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 1.5f);
        chestOrMimic.addEntry(AetherBlocks.CHEST_MIMIC.id(), 1.5f);
    }
    //    public TreasureBronzeRoom(World world, Random random, int x, int y, int z) {
    //        super(world, random, x, y, z);
    //    }
    @Override
    public void makeRoom() {
        // Shell
        room.add(drawShell(random, ROOM_PALLET, EAST, 12, UP, height, SOUTH, 12, x, y, z, true));
        room.add(drawVolume(0, 0, EAST, 10, UP, height - 2, SOUTH, 10, x + 1, y + 1, z + 1, true));
        // Plinth
        room.add(drawPlane(random, ROOM_PALLET, SOUTH, 4, EAST, 4, x + 4, y + 1, z + 4, true));
        // ChestOrMimic
        chest.add(drawPlane(random, chestOrMimic, SOUTH, 2, EAST, 2, x + 5, y + 2, z + 5, true));
    }
}
