package teamport.aether.world.generate.feature.components.dungeon.bronze;

import net.minecraft.core.block.Block;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.helper.Pair;
import teamport.aether.items.AetherItems;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.chests.WorldFeatureAetherBronzeChest;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;
import teamport.aether.world.generate.feature.dungeon.map.DungeonMapEntrySlider;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;

public class BossRoom extends BaseBronzeRoom {
    public static final BlockPallet ROOM_PALLET = new BlockPallet();

    static {
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LOCKED.id(), 0, 85);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_LIGHT_LOCKED.id(), 0, 5);
        ROOM_PALLET.addEntry(AetherBlocks.CARVED_STONE_TRAPPED_LOCKED.id(), 0, 10);
    }


//    public BossBronzeRoom(World world, Random random, int x, int y, int z) {
//        super(world, random, x, y, z);
//    }

    public BossRoom() {
        super();
    }


    public void makeShell() {
        room.add(drawShell(random, ROOM_PALLET, EAST, 12, UP, height, SOUTH, 12, x, y, z, true));
        room.add(drawVolume(0, 0, EAST, 10, UP, height - 2, SOUTH, 10, x + 1, y + 1, z + 1, true));
    }

    private void makeTreasureRoom() {
        room.add(drawShell(random, ROOM_PALLET, EAST, 4, UP, 4, SOUTH, 4, x + 4, y - 2, z + 4, true));
        room.add(drawVolume(0, 0, EAST, 2, UP, 2, SOUTH, 2, x + 5, y - 1, z + 5, true));
    }

    private void placeBoss() {
        DungeonMapEntrySlider dungeon = AetherDimension.dungeonMap.register(DungeonMapEntrySlider.class);
        dungeon.setPosition(new WorldFeaturePoint(x + 5, y + 2, z + 5));
        dungeon.setClearArea(new Pair<>(
                new WorldFeaturePoint(x, y - 2, z),
                new WorldFeaturePoint(x + 16, y + 14, z + 16)
        ));
        WorldFeatureAetherBronzeChest.bronzeChest().place(world, random, x + 5 + random.nextInt(2), y - 1, z + 5 + random.nextInt(2));

        MobBossSlider boss = new MobBossSlider(world);
        boss.moveTo(x + 6, y + 2, z + 6, 0f, 0f);
        boss.setReturnPoint(new WorldFeaturePoint(x + 6, y + 2, z + 6));
        boss.setTrophy(AetherItems.KEY_BRONZE.getDefaultStack());
        boss.setDungeonID(dungeon.getId());
        world.entityJoinedWorld(boss);

        dungeon.setDoorBlocks(new WorldFeaturePoint[]{
                new WorldFeaturePoint(x + 5, y + 1, z + 5),
                new WorldFeaturePoint(x + 6, y + 1, z + 5),
                new WorldFeaturePoint(x + 5, y + 1, z + 6),
                new WorldFeaturePoint(x + 6, y + 1, z + 6),
        });
    }

    @Override
    public boolean canPlace() {
        for (WorldFeaturePoint p : room.blockList) {
            Block<?> block = world.getBlock(p.x, p.y, p.z);
            if (block == null || block.id() == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void makeRoom() {
        this.makeShell();
        this.makeTreasureRoom();
    }
}
