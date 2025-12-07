package teamport.aether.world.feature.dungeon.bronze.component;

import teamport.aether.block.AetherBlocks;
import teamport.aether.world.feature.util.BlockPallet;
import teamport.aether.world.feature.util.WorldFeatureComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.feature.util.WorldFeatureComponent.*;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;

public class JumpRoom extends BaseBronzeRoom {
    private static final BlockPallet JUMP_MIMICS = new BlockPallet();

    static {
        JUMP_MIMICS.addEntry(0, 1.25);
        JUMP_MIMICS.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 2);
        JUMP_MIMICS.addEntry(AetherBlocks.CHEST_MIMIC_SKYROOT.id(), 2);
    }


    public JumpRoom() {
        super();
        this.height = 15;
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 4), UP, 6, SOUTH, 4);
    }

    @Override
    public void makeRoom() {
        // Shell
        room.add(drawShell(random, ROOM_PALLET, EAST, 12, UP, height, SOUTH, 12, x, y, z, false));
        room.add(drawVolume(0, 0, EAST, 10, UP, height - 2, SOUTH, 10, x + 1, y + 1, z + 1, false));

        // Platforms
        decoration.add(drawPlane(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, SOUTH, 2, EAST, 4, x + 4, y + 7, z + 1, false));
        decoration.add(drawPlane(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, SOUTH, 4, EAST, 2, x + 1, y + 7, z + 4, false));
        decoration.add(drawPlane(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, SOUTH, 4, EAST, 2, x + 9, y + 7, z + 4, false));
        decoration.add(drawPlane(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, SOUTH, 2, EAST, 4, x + 4, y + 7, z + 9, false));

        // Clouds
        decoration.add(drawPlane(AetherBlocks.AERCLOUD_BLUE.id(), 0, SOUTH, 3, EAST, 3, x + 1, y, z + 1, false));
        decoration.add(drawPlane(AetherBlocks.AERCLOUD_BLUE.id(), 0, SOUTH, 3, WEST, 3, x + 10, y, z + 1, false));
        decoration.add(drawPlane(AetherBlocks.AERCLOUD_BLUE.id(), 0, NORTH, 3, EAST, 3, x + 1, y, z + 10, false));
        decoration.add(drawPlane(AetherBlocks.AERCLOUD_BLUE.id(), 0, NORTH, 3, WEST, 3, x + 10, y, z + 10, false));

        // Chests
        List<WorldFeatureComponent> listChestPos = new ArrayList<>();
        listChestPos.add(drawLine(random, JUMP_MIMICS, EAST, 2, x + 5, y + 8, z + 1, false));
        listChestPos.add(drawLine(random, JUMP_MIMICS, SOUTH, 2, x + 1, y + 8, z + 5, false));
        listChestPos.add(drawLine(random, JUMP_MIMICS, SOUTH, 2, x + 10, y + 8, z + 5, false));
        listChestPos.add(drawLine(random, JUMP_MIMICS, EAST, 2, x + 5, y + 8, z + 10, false));
        Collections.shuffle(listChestPos, random);
        int max = random.nextInt(2) + 2;
        for (int i = 0; i < max; i++) {
            chest.add(listChestPos.get(i));
        }
    }
}
