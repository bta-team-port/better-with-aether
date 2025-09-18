package teamport.aether.world.generate.feature.components.dungeon.bronze;

import net.minecraft.core.block.BlockLogicRotatable;
import teamport.aether.AetherMod;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.helper.Pair;
import teamport.aether.items.AetherItems;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.chests.WorldFeatureAetherBronzeChest;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;
import teamport.aether.world.generate.feature.dungeon.map.DungeonMapEntrySlider;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class BossRoom extends BaseBronzeRoom {
    public static final BlockPallet ROOM_PALLET = new BlockPallet();

    static {
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LOCKED.id(), 0, 90);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT_LOCKED.id(), 0, 10);
    }

    DungeonMapEntrySlider dungeon;
    private Door bossDoor;

    public BossRoom() {
        super();
        this.width = this.length = 16;
        this.height = 14;
        this.airTolerance = this.liquidTolerance = 0;
        addDoor(NORTH, wfp(6, 1, 0), UP, 4, EAST, 4);
        addDoor(EAST, wfp(15, 1, 6), UP, 4, SOUTH, 4);
        addDoor(SOUTH, wfp(6, 1, 15), UP, 4, EAST, 4);
        addDoor(WEST, wfp(0, 1, 6), UP, 4, SOUTH, 4);
    }

    public void makeShell() {
        room.add(drawShell(random, ROOM_PALLET, SOUTH, width, UP, height, EAST, length, x, y, z, false));
        room.add(drawVolume(0, 0, EAST, width - 2, UP, height - 2, SOUTH, length - 2, x + 1, y + 1, z + 1, false));
    }

    private void makeTreasureRoom() {
        decoration.add(drawShell(random, ROOM_PALLET, EAST, 4, UP, 4, SOUTH, 4, x + 6, y - 2, z + 6, false));
        decoration.add(drawVolume(0, 0, EAST, 2, UP, 2, SOUTH, 2, x + 7, y - 1, z + 7, false));
    }

    private void placeBoss() {
        dungeon = AetherDimension.dungeonMap.register(DungeonMapEntrySlider.class);

        dungeon.setPosition(new WorldFeaturePoint(x + 8, y + 2, z + 8));
        dungeon.setClearArea(new Pair<>(wfp(x, y - 2, z), wfp(x + 16, y + 14, z + 16)));
        WorldFeatureAetherBronzeChest.bronzeChest().place(world, random, x + 7 + random.nextInt(2), y - 1, z + 7 + random.nextInt(2));
        List<WorldFeaturePoint> treasureDoor = new ArrayList<>();
        treasureDoor.add(wfp(x + 7, y + 1, z + 7));
        treasureDoor.add(wfp(x + 8, y + 1, z + 7));
        treasureDoor.add(wfp(x + 7, y + 1, z + 8));
        treasureDoor.add(wfp(x + 8, y + 1, z + 8));
        dungeon.setTreasureDoor(treasureDoor);

        MobBossSlider boss = new MobBossSlider(world);
        boss.moveTo(x + 8, y + 2, z + 8, 0f, 0f);
        boss.setReturnPoint(new WorldFeaturePoint(x + 8, y + 2, z + 8));
        boss.setTrophy(AetherItems.KEY_BRONZE.getDefaultStack());
        boss.setDungeonID(dungeon.getId());
        world.entityJoinedWorld(boss);
    }

    @Override
    public void makeRoom() {
        this.makeShell();
        this.makeTreasureRoom();
    }

    @Override
    public void placeRoom() {
        this.room.place(world);
        this.decoration.place(world);
        this.placeBoss();
    }

    @Override
    public void markDoor(Door door) {
        this.bossDoor = door;
        doors.forEach(d -> d.mark = true);
        if (door == null){
            AetherMod.LOGGER.warn("Bronze dungeon door at: {}, {}, {} does not exist. Thus the slider door was not registered.",x,y,z);
            return;
        }
        int meta = BlockLogicRotatable.setDirection(0, door.heading);
        WorldFeatureComponent doorBlock = drawVolume(AetherBlocks.DOOR_DUNGEON_BRONZE.id(), meta, door.p1, door.p2, true);
        dungeon.setEntranceDoor(doorBlock.blockList);
    }

    public Door getBossDoor(){
        return bossDoor;
    }
}
