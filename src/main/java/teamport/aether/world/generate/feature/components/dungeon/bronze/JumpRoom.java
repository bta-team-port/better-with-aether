package teamport.aether.world.generate.feature.components.dungeon.bronze;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.helper.Pair;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.minecraft.core.util.helper.Direction.*;
import static net.minecraft.core.util.helper.Direction.EAST;
import static net.minecraft.core.util.helper.Direction.SOUTH;
import static net.minecraft.core.util.helper.Direction.UP;
import static net.minecraft.core.util.helper.Direction.WEST;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class JumpRoom extends BaseBronzeRoom{
    public static BlockPallet chestOrMimic = new BlockPallet();
    static {
        chestOrMimic.addEntry(0, 1);
        chestOrMimic.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 1);
        chestOrMimic.addEntry(AetherBlocks.CHEST_MIMIC_SKYROOT.id(), 1);
    }

    public JumpRoom(){
        super();
        this.height = 15;
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 4), UP, 6, SOUTH, 4);

        addDoor(DOWN, wfp(5,0,5), EAST, 2, SOUTH, 2);
        addDoor(UP, wfp(5,this.height,5), EAST, 2, SOUTH, 2);
    }

    @Override
    public void makeRoom() {
        // Shell
        room.add(drawShell(random, ROOM_PALLET, EAST, 12, UP, height, SOUTH, 12, x, y, z, true));
        room.add(drawVolume(0, 0, EAST, 10, UP, height - 2, SOUTH, 10, x + 1, y + 1, z + 1, true));

        // Platforms
        decoration.add(drawPlane(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, SOUTH, 2, EAST, 4, x + 4, y + 7, z + 1, true));
        decoration.add(drawPlane(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, SOUTH, 4, EAST, 2, x + 1, y + 7, z + 4, true));
        decoration.add(drawPlane(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, SOUTH, 4, EAST, 2, x + 9, y + 7, z + 4, true));
        decoration.add(drawPlane(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, SOUTH, 2, EAST, 4, x + 4, y + 7, z + 9, true));

        // Clouds
        decoration.add(drawPlane(AetherBlocks.AERCLOUD_BLUE.id(), 0, SOUTH, 3, EAST, 3, x + 1, y, z + 1, true));
        decoration.add(drawPlane(AetherBlocks.AERCLOUD_BLUE.id(), 0, SOUTH, 3, WEST, 3, x + 10, y, z + 1, true));
        decoration.add(drawPlane(AetherBlocks.AERCLOUD_BLUE.id(), 0, NORTH, 3, EAST, 3, x + 1, y, z + 10, true));
        decoration.add(drawPlane(AetherBlocks.AERCLOUD_BLUE.id(), 0, NORTH, 3, WEST, 3, x + 10, y, z + 10, true));

        // Chests
        List<WorldFeatureComponent> listChestPos = new ArrayList<>();
        listChestPos.add(drawLine(random, chestOrMimic, EAST, 2, x + 5, y + 8, z + 1, true));
        listChestPos.add(drawLine(random, chestOrMimic, SOUTH, 2, x + 1, y + 8, z + 5, true));
        listChestPos.add(drawLine(random, chestOrMimic, SOUTH, 2, x + 10, y + 8, z + 5, true));
        listChestPos.add(drawLine(random, chestOrMimic, EAST, 2, x + 5, y + 8, z + 10, true));
        Collections.shuffle(listChestPos, random);
        int max = random.nextInt(2) + 1;
        for(int i = 0; i < max; i++){
            chest.add(listChestPos.get(i));
        }
    }
}
