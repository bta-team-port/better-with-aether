package teamport.aether.world.generate.feature.components.dungeon.bronze;

import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.helper.Pair;
import teamport.aether.items.AetherItems;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.chests.WorldFeatureAetherBronzeChest;
import teamport.aether.world.generate.feature.components.WorldFeatureBlock;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;
import teamport.aether.world.generate.feature.dungeon.map.DungeonMapEntrySlider;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfpoint;
import static teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherBronzeDungeon.TUNNEL_HEIGHT;
import static teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherBronzeDungeon.TUNNEL_WIDTH;

public class BossRoom extends BaseBronzeRoom {
    public static final BlockPallet ROOM_PALLET = new BlockPallet();

    static {
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LOCKED.id(), 0, 85);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT_LOCKED.id(), 0, 5);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_TRAPPED_LOCKED.id(), 0, 10);
    }

    public BossRoom() {
        super();
        this.width = this.length = 16;
        this.height = 14;
        this.tolerance = 0;
        addDoor(NORTH, wfpoint(6, 1, 0), UP, 6, EAST, 4);
        addDoor(EAST, wfpoint(15, 1, 6), UP, 6, SOUTH, 4);
        addDoor(SOUTH, wfpoint(6, 1, 15), UP, 6, EAST, 4);
        addDoor(WEST, wfpoint(0, 1, 6), UP, 6, SOUTH, 4);
    }

    public void makeShell() {
        room.add(drawShell(random, ROOM_PALLET, SOUTH, width, UP, height, EAST, length, x, y, z, true));
        room.add(drawVolume(0, 0, EAST, width - 2, UP, height - 2, SOUTH, length - 2, x + 1, y + 1, z + 1, true));
    }

    private void makeTreasureRoom() {
        decoration.add(drawShell(random, ROOM_PALLET, EAST, 4, UP, 4, SOUTH, 4, x + 6, y - 2, z + 6, true));
        decoration.add(drawVolume(0, 0, EAST, 2, UP, 2, SOUTH, 2, x + 7, y - 1, z + 7, true));
    }

    private void placeBoss() {
        DungeonMapEntrySlider dungeon = AetherDimension.dungeonMap.register(DungeonMapEntrySlider.class);
        dungeon.setPosition(new WorldFeaturePoint(x + 8, y + 2, z + 8));
        dungeon.setClearArea(new Pair<>(
                new WorldFeaturePoint(x, y - 2, z),
                new WorldFeaturePoint(x + 16, y + 14, z + 16)
        ));
        WorldFeatureAetherBronzeChest.bronzeChest().place(world, random, x + 7 + random.nextInt(2), y - 1, z + 7 + random.nextInt(2));
        List<WorldFeaturePoint> treasureDoor = new ArrayList<>();
        treasureDoor.add(new WorldFeaturePoint(x + 7, y + 1, z + 7));
        treasureDoor.add(new WorldFeaturePoint(x + 8, y + 1, z + 7));
        treasureDoor.add(new WorldFeaturePoint(x + 7, y + 1, z + 8));
        treasureDoor.add(new WorldFeaturePoint(x + 8, y + 1, z + 8));
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
        for(Door d : doors){
           super.markDoor(d);
        }
    }
}
