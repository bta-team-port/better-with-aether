package teamport.aether.world.feature.dungeon.bronze.component;

import net.minecraft.core.block.BlockLogicRotatable;
import teamport.aether.AetherMod;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.helper.Pair;
import teamport.aether.item.AetherItems;
import teamport.aether.world.feature.chest.WorldFeatureAetherBronzeChest;
import teamport.aether.world.feature.dungeon.bronze.DungeonLogicBronzeDungeon;
import teamport.aether.world.feature.util.BlockPallet;
import teamport.aether.world.feature.util.WorldFeatureComponent;
import teamport.aether.world.feature.util.WorldFeaturePoint;
import teamport.aether.world.feature.util.map.DungeonMap;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.feature.util.WorldFeatureComponent.drawShell;
import static teamport.aether.world.feature.util.WorldFeatureComponent.drawVolume;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;

public class BossRoom extends BaseBronzeRoom {
    private static final BlockPallet ROOM_PALLET = new BlockPallet();

    static {
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LOCKED.id(), 0, 80);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT_LOCKED.id(), 0, 10);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_TRAPPED_LOCKED.id(), 0, 10); //
    }

    private DungeonLogicBronzeDungeon dungeon;

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
        // you see... My structure fell apart here, so enjoy your quality garbage!

        dungeon = DungeonMap.register(DungeonLogicBronzeDungeon.class, world, world.getRandomSeed() + random.nextInt(), x + 8, y + 2, z + 8);
        dungeon.setGenerated(true);

        dungeon.setClearArea(new Pair<>(wfp(x, y - 2, z), wfp(x + 16, y + 14, z + 16)));
        new WorldFeatureAetherBronzeChest().place(world, random, x + 7 + random.nextInt(2), y - 1, z + 7 + random.nextInt(2));
        List<WorldFeaturePoint> treasureDoor = new ArrayList<>();
        treasureDoor.add(wfp(x + 7, y + 1, z + 7));
        treasureDoor.add(wfp(x + 8, y + 1, z + 7));
        treasureDoor.add(wfp(x + 7, y + 1, z + 8));
        treasureDoor.add(wfp(x + 8, y + 1, z + 8));
        dungeon.setTreasureDoor(treasureDoor);

        MobBossSlider boss = new MobBossSlider(world);
        boss.moveTo(x + 8.0, y + 2.0, z + 8.0, 0f, 0f);
        boss.setReturnPoint(new WorldFeaturePoint(x + 8, y + 2, z + 8));
        boss.setTrophy(AetherItems.KEY_BRONZE.getDefaultStack());
        boss.setDungeonID(dungeon.id);
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
    public void markDoor(Door door, ClosingType closingType) {
        super.markDoor(door, closingType);
        if (closingType != ClosingType.PLACED && door.getMark() != ClosingType.PLACED) return;
        doors.forEach(d -> d.setMark(ClosingType.ROOM_LOCKED));
        if (door == null) {
            AetherMod.LOGGER.warn("Bronze dungeon door at: {}, {}, {} does not exist. Thus the slider door was not registered.", x, y, z);
            return;
        }
        int meta = BlockLogicRotatable.setDirection(0, door.getHeading());
        WorldFeatureComponent doorBlock = drawVolume(AetherBlocks.DOOR_DUNGEON_BRONZE.id(), meta, door.getP1(), door.getP2(), true);
        dungeon.setEntranceDoor(doorBlock.getBlockList());
    }
}
