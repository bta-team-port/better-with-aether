package teamport.aether.world.generate.feature.components.dungeon.bronze;

import net.minecraft.core.block.Blocks;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.helper.unboxed.IntPair;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.components.WorldFeatureBlock;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureBlock.wfb;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class DisplayRoom extends BaseBronzeRoom {
    public static BlockPallet CHESTORMIMIC = new BlockPallet();
    public static BlockPallet DISPLAY = new BlockPallet();
    static {
        CHESTORMIMIC.addEntry(0, 1.5);
        CHESTORMIMIC.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 1);
        CHESTORMIMIC.addEntry(AetherBlocks.CHEST_MIMIC_SKYROOT.id(), 2);

        DISPLAY.addEntry(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE.id(), 10);
        DISPLAY.addEntry(AetherBlocks.ORE_ZANITE_HOLYSTONE.id(), 5);
        DISPLAY.addEntry(AetherBlocks.ORE_GRAVITITE_HOLYSTONE.id(), 1);
    }
    public DisplayRoom() {
        super();
        addDoor(NORTH, wfp(4, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfp(11, 1, 4), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfp(4, 1, 11), UP, 6, EAST, 4);
        addDoor(WEST, wfp(0, 1, 4), UP, 6, SOUTH, 4);

        addDoor(UP, wfp(5,this.height,5), EAST, 2, SOUTH, 2);
    }

    @Override
    public void makeRoom() {
        //room
        room.add(drawHollowShell(random, ROOM_PALLET, SOUTH, width, UP, height, EAST, width, x, y, z, true));
        // center deco
        decoration.add(drawPlane(AetherBlocks.SLAB_CARVED_STONE.id(), 0, SOUTH, 4, EAST, 2, x + 5, y + 1, z + 4, true));
        decoration.add(drawPlane(AetherBlocks.SLAB_CARVED_STONE.id(), 0, SOUTH, 2, EAST, 4, x + 4, y + 1, z + 5, true));
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 2, EAST, 2, x + 5, y + 1, z + 5, true));
        decoration.add(drawVolume(AetherBlocks.CARVED_STONE_LIGHT.id(), 0, SOUTH, 2, EAST, 2, UP, 2, x + 5, y + 4, z + 5, true));
        // corner plinth
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 2, EAST, 2, x + 1, y + 1, z + 1, true));
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 2, EAST, 2, x + 1, y + 1, z + 9, true));
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 2, EAST, 2, x + 9, y + 1, z + 1, true));
        decoration.add(drawPlane(random, ROOM_PALLET, SOUTH, 2, EAST, 2, x + 9, y + 1, z + 9, true));

        // deco in corner
        List<WorldFeaturePoint[]> pointList = new ArrayList<>();
        pointList.add(new WorldFeaturePoint[]{wfp(x + 1, y + 2, z + 1), wfp(x + 2, y + 2, z + 1), wfp(x + 1, y + 2, z + 2) });
        pointList.add(new WorldFeaturePoint[]{wfp(x + 9, y + 2, z + 1), wfp(x + 10, y + 2, z + 1), wfp(x + 10, y + 2, z + 2) });
        pointList.add(new WorldFeaturePoint[]{wfp(x + 1, y + 2, z + 9), wfp(x + 1, y + 2, z + 10), wfp(x + 2, y + 2, z + 10) });
        pointList.add(new WorldFeaturePoint[]{wfp(x + 9, y + 2, z + 10), wfp(x + 10, y + 2, z + 10), wfp(x + 10, y + 2, z + 9) });
        Collections.shuffle(pointList, random);
        for(int i = 0; i < 2; i++){
            WorldFeaturePoint[] points = pointList.get(i);
            for(WorldFeaturePoint point: points){
                IntPair idMeta = DISPLAY.getRandom(random);
                chest.add(wfb(point.x, point.y, point.z, idMeta.first, idMeta.second,  true));
            }
        }
        for(int i = 2; i < pointList.size(); i++){
            WorldFeaturePoint[] points = pointList.get(i);
            for(WorldFeaturePoint point: points){
                IntPair idMeta = CHESTORMIMIC.getRandom(random);
                chest.add(wfb(point.x, point.y, point.z, idMeta.first, idMeta.second,  true));
            }
        }
    }
}
