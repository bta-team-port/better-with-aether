package teamport.aether.world.generate.feature.dungeon;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.helper.Pair;
import teamport.aether.items.AetherItems;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.chests.WorldFeatureAetherBronzeChest;
import teamport.aether.world.generate.feature.components.WorldFeatureBlock;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;
import teamport.aether.world.generate.feature.dungeon.map.DungeonMapEntrySlider;

import java.util.*;

import static net.minecraft.core.util.helper.Direction.DOWN;
import static net.minecraft.core.util.helper.Direction.EAST;
import static net.minecraft.core.util.helper.Direction.SOUTH;
import static net.minecraft.core.util.helper.Direction.UP;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;

public class WorldFeatureAetherBronzeDungeonLegacy extends WorldFeature {
    public int ROOM_COUNT_MAX = 10;
    public static final int TUNNEL_HEIGHT = 8;
    public static final int TUNNEL_WIDTH = 6;
    public int roomCount = 0;
    public WorldFeatureComponent hallway;
    public World world;
    public Random random;

    public static final BlockPallet carvedHolystone = new BlockPallet();
    public static final BlockPallet lockedCarvedHolystone = new BlockPallet();
    public static final BlockPallet holystone = new BlockPallet();
    public static final BlockPallet chestsOrMimic = new BlockPallet();

    static {
        carvedHolystone.addEntry(AetherBlocks.CARVED_STONE.id(), 85);
        carvedHolystone.addEntry(AetherBlocks.CARVED_STONE_LIGHT.id(), 5);
        carvedHolystone.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 10);

        lockedCarvedHolystone.addEntry(AetherBlocks.CARVED_STONE_LOCKED.id(), 90);
        lockedCarvedHolystone.addEntry(AetherBlocks.CARVED_STONE_LIGHT_LOCKED.id(), 10);

        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE.id(), 90);
        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), 10);

        chestsOrMimic.addEntry(0, 1);
        chestsOrMimic.addEntry(AetherBlocks.CHEST_MIMIC.id(), 1);
        chestsOrMimic.addEntry(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 1);
    }

    public static final WeightedRandomBag<WeightedRandomLootObject> ARMOR = new WeightedRandomBag<>();
    public static final WeightedRandomBag<WeightedRandomLootObject> JUNK = new WeightedRandomBag<>();
    public static final WeightedRandomBag<WeightedRandomLootObject> AMMO = new WeightedRandomBag<>();
    public static final WeightedRandomBag<WeightedRandomLootObject> GADGET = new WeightedRandomBag<>();
    public static final WeightedRandomBag<WeightedRandomLootObject> FOOD = new WeightedRandomBag<>();

    static {
        // junk                                    8-10
        JUNK.addEntry(new WeightedRandomLootObject(null), 8);
        JUNK.addEntry(new WeightedRandomLootObject(AetherItems.AMBROSIUM.getDefaultStack(), 1, 6), 4);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.TORCH_AMBROSIUM.getDefaultStack(), 1, 4), 4);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.HOLYSTONE.getDefaultStack(), 4, 12), 2);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.HOLYSTONE_MOSSY.getDefaultStack(), 4, 12), 2);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.ICESTONE.getDefaultStack(), 4, 12), 2);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.CARVED_STONE_LIGHT.getDefaultStack(), 4, 12), 2);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.CARVED_STONE.getDefaultStack(), 4, 12), 2);
        JUNK.addEntry(new WeightedRandomLootObject(AetherItems.BUCKET_SKYROOT.getDefaultStack()), 1);
        JUNK.addEntry(new WeightedRandomLootObject(AetherItems.ZANITE.getDefaultStack(), 1, 4), 1);
        // food                                     2-4
        FOOD.addEntry(new WeightedRandomLootObject(null), 8);
        FOOD.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_HEALING_STONE.getDefaultStack(), 1, 4), 4);
        FOOD.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_BLUE.getDefaultStack(), 1, 4), 2);
        FOOD.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_GOLD.getDefaultStack(), 1, 2), 1);
        // armor & tool - chestplate                0-2(super rare)
        int minTool = AetherItems.TOOL_PICKAXE_HOLYSTONE.getMaxDamage() / 2;
        int maxTool = AetherItems.TOOL_PICKAXE_HOLYSTONE.getMaxDamage();
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_PICKAXE_HOLYSTONE.getDefaultStack())
                .setRandomMetadata(minTool, maxTool), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_AXE_HOLYSTONE.getDefaultStack())
                .setRandomMetadata(minTool, maxTool), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_HOLYSTONE.getDefaultStack())
                .setRandomMetadata(minTool, maxTool), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SHOVEL_HOLYSTONE.getDefaultStack())
                .setRandomMetadata(minTool, maxTool), 1);
        // ammo                                     2-5
        AMMO.addEntry(new WeightedRandomLootObject(null), 8);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_GOLDEN.getDefaultStack(), 2, 6), 4);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_POISON.getDefaultStack(), 2, 6), 2);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_ENCHANTED.getDefaultStack(), 2, 6), 1);
        // gadget - cape colored, talisman          0-2(super rare)
        GADGET.addEntry(new WeightedRandomLootObject(null), 4);
        GADGET.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_LEATHER.getDefaultStack()), 3);
        GADGET.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_ZANITE.getDefaultStack()), 1);
    }

    public static final WeightedRandomBag<WeightedRandomLootObject> TREASURE = new WeightedRandomBag<>();

    static {
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.RECORD_MORNING.getDefaultStack()), 1);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_HAMMER_NOTCH.getDefaultStack()), 10);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_KNIFE_LIGHTNING.getDefaultStack(), 1, 16), 10);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_LIGHTNING.getDefaultStack()), 10);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_REGEN.getDefaultStack()), 10);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CAPE_SWET.getDefaultStack()), 10);
    }


    public WorldFeatureAetherBronzeDungeonLegacy() {
    }

    @Override
    public boolean place(final World world, final Random random, final int x, final int y, final int z) {
        this.world = world;
        this.random = random;
        this.hallway = new WorldFeatureComponent();
        if (this.isBoxEmpty(x, y, z, EAST, 16, UP, 12, SOUTH, 16, 0)) {
            return false;
        }
        createBossAndTreasure(x, y, z, x + 7 + random.nextInt(2), y - 1, z + 7 + random.nextInt(2));
        int x2 = x + 20;
        int z2 = z + 2;
        if (this.isBoxEmpty(x2, y, z2, EAST, 12, UP, 12, SOUTH, 12)) {
            this.addSquareTube(holystone, x2 - 5, y, z2 + 3, 6, 6, 6);
            return true;
        }
        makeAnotherRoom(world, random, y, x2, z2);
        findNextRoom(x2, y, z2);
        this.placeComponent(hallway);
        return true;
    }

    private void placeComponent(WorldFeatureComponent rooms) {
        for (WorldFeatureBlock wfblock : rooms.blockList) {
            if (this.canReplace(wfblock)) {
                wfblock.place(world);
            }
        }
    }

    private boolean canReplace(WorldFeatureBlock wfblock) {
        Block<?> block = world.getBlock(wfblock.x, wfblock.y, wfblock.z);
        int blockID = block == null ? 0 : block.id();
        Material blockMaterial = blockID == 0 ? Material.air : block.getMaterial();
        if (blockID == AetherBlocks.CHEST_MIMIC.id() || blockID == AetherBlocks.CHEST_PLANKS_SKYROOT.id()) {
            world.removeBlockTileEntity(wfblock.x, wfblock.y, wfblock.z);
            return true;
        }
        return BlockTags.CAVES_CUT_THROUGH.appliesTo(block)
                || blockMaterial == Material.grass
                || blockMaterial == Material.dirt
                || blockMaterial == Material.marble
                || blockMaterial == Material.moss
                || blockMaterial.isStone()
                || blockMaterial.isLiquid();
    }

    private boolean findNextRoom(int x, int y, int z) {
        int tries = 3;
        ArrayList<Direction> dirList = new ArrayList<>(Arrays.asList(Direction.horizontalDirections));
        int index = random.nextInt(dirList.size() - 1);

        boolean finished = true;
        while (finished && tries-- > 0) {
            // placeRoom calls findNextRoom
            finished = this.placeNextRoom(x, y, z, dirList.get(index));
            index = random.nextInt(dirList.size() - 1);
            dirList.remove(index);
        }

        if (!finished) return true;
        return false;
    }

    private boolean placeNextRoom(final int finalX, final int finalY, final int finalZ, Direction dir) {
        if (this.roomCount++ > ROOM_COUNT_MAX) {
            return false;
        }
        int x = finalX + dir.getOffsetX() * 16;
        int y = finalY + dir.getOffsetY() * 16;
        int z = finalZ + dir.getOffsetZ() * 16;


        if (random.nextInt(5) == 0) { // down I will need to rewrite it
            if (canPlace(x, y, z)) return true;
            WorldFeatureComponent rooms = new WorldFeatureComponent();
            rooms.add(drawShell(random, carvedHolystone, EAST, 12, UP, 12, SOUTH, 12, x, y, z, true));
            rooms.add(drawVolume(0, 0, EAST, 10, UP, 10, SOUTH, 10, x + 1, y + 1, z + 1, true));
            this.placeComponent(rooms);
            drawPlane(random, carvedHolystone, SOUTH, 4, EAST, 4, x + 4, y + 1, z + 4, true).place(world);
            placeNextRoom(finalX, finalY - 15, finalZ, dir);
            hallway.add(drawVolume(0, 0, SOUTH, 2, DOWN, 6, EAST, 2, x + 5, y + 1, z + 5, true));
        } else {
            if (canPlace(x, y, z)) return true;
            placeChestRoom(x, y, z);
        }

        if (canPlace(x, y, z)) return true;
        placeChestRoom(x, y, z);
        generateTunnel(finalX, finalY, finalZ, dir);
        return findNextRoom(x, y, z);
    }

    private void generateTunnel(int x, int y, int z, Direction dir) {
        switch (dir) {
            case NORTH: {
//                hallway.add(drawVolume(0, 0, SOUTH, TUNNEL_WIDTH, UP, TUNNEL_HEIGHT - 2, EAST, TUNNEL_WIDTH - 2, x + 4, y + 1, z - 5, true));
                hallway.add(drawVolume(1, 0, dir, TUNNEL_WIDTH, UP, TUNNEL_HEIGHT - 2, dir.rotate(1), TUNNEL_WIDTH - 2, x + 4, y + 1, z, true));
                break;
            }
            case EAST: {

//                hallway.add(drawVolume(0, 0, SOUTH, TUNNEL_WIDTH - 2, UP, TUNNEL_HEIGHT - 2, EAST, TUNNEL_WIDTH, x + 11, y + 1, z + 4, true));
                hallway.add(drawVolume(2, 0, dir, TUNNEL_WIDTH, UP, TUNNEL_HEIGHT - 2, dir.rotate(1), TUNNEL_WIDTH - 2, x + 11, y + 1, z + 4, true));
                break;
            }
            case SOUTH: {
//                hallway.add(drawVolume(0, 0, SOUTH, TUNNEL_WIDTH, UP, TUNNEL_HEIGHT - 2, EAST, TUNNEL_WIDTH - 2, x + 4, y + 1, z + 11, true));
                hallway.add(drawVolume(3, 0, dir, TUNNEL_WIDTH, UP, TUNNEL_HEIGHT - 2, dir.rotate(1), TUNNEL_WIDTH - 2, x + 8, y + 1, z + 11, true));
                break;
            }
            case WEST: {
//                hallway.add(drawVolume(0, 0, SOUTH, TUNNEL_WIDTH - 2, UP, TUNNEL_HEIGHT - 2, EAST, TUNNEL_WIDTH, x - 5, y + 1, z + 4, true));
                hallway.add(drawVolume(4, 0, dir, TUNNEL_WIDTH, UP, TUNNEL_HEIGHT - 2, dir.rotate(1), TUNNEL_WIDTH - 2, x, y + 1, z + 8, true));
                break;
            }
            case UP: {

            }
            case DOWN: {

            }
        }
    }

    private void placeChestRoom(int x, int y, int z) {
        WorldFeatureComponent rooms = new WorldFeatureComponent();
        rooms.add(drawShell(random, carvedHolystone, EAST, 12, UP, 12, SOUTH, 12, x, y, z, true));
        rooms.add(drawVolume(0, 0, EAST, 10, UP, 10, SOUTH, 10, x + 1, y + 1, z + 1, true));
        this.placeComponent(rooms);
        WorldFeatureComponent chests = new WorldFeatureComponent();
        drawPlane(random, carvedHolystone, SOUTH, 4, EAST, 4, x + 4, y + 1, z + 4, true).place(world);
        chests.add(drawPlane(random, chestsOrMimic, SOUTH, 2, EAST, 2, x + 5, y + 2, z + 5, true));
        chests.place(world);
        for (WorldFeatureBlock chest : chests.blockList) {
            populateChest(world, random, chest, WorldFeatureAetherBronzeDungeon::generateLoot);
        }
    }


    private boolean canPlace(int x, int y, int z) {
        if (y <= 11) return false;
        return this.isBoxEmpty(x, y, z, EAST, 12, UP, 12, SOUTH, 12);
    }

    private boolean isBoxEmpty(int startX, int startY, int startZ, Direction direction1, int length1, Direction direction2, int length2, Direction direction3, int length3) {
        return isBoxEmpty(startX, startY, startZ, direction1, length1, direction2, length2, direction3, length3, 0.35F);
    }

    private boolean isBoxEmpty(int startX, int startY, int startZ, Direction direction1, int length1, Direction direction2, int length2, Direction direction3, int length3, float percent) {
        int volume = 0;
        int blockX;
        int blockY;
        int blockZ;

        for (int i = 0; i < length3; i++) {
            int x3 = startX + direction3.getOffsetX() * i;
            int y3 = startY + direction3.getOffsetY() * i;
            int z3 = startZ + direction3.getOffsetZ() * i;
            for (int j = 0; j < length2; j++) {
                blockX = x3 + direction2.getOffsetX() * j;
                blockY = y3 + direction2.getOffsetY() * j;
                blockZ = z3 + direction2.getOffsetZ() * j;
                for (int k = 0; k < length1; k++) {
                    if (world.getBlockId(blockX, blockY, blockZ) == 0) {
                        volume++;
                    }
                    blockX += direction1.getOffsetX();
                    blockY += direction1.getOffsetY();
                    blockZ += direction1.getOffsetZ();
                }
            }
        }

        // I'm literally frito-lay fr fr fr
        return volume > ((length1 * length2 * length3) * percent);
    }

    private void addSquareTube(BlockPallet pallet, int x, int y, int z, int lengthX, int lengthY, int lengthZ) {
        for (int sx = x; sx < x + lengthX; ++sx) {
            for (int sy = y; sy < y + lengthY; ++sy) {
                for (int sz = z; sz < z + lengthZ; ++sz) {
                    world.setBlockAndMetadataWithNotify(sx, sy, sz, 0, 0);
                }
            }
        }
    }

    public void createBossAndTreasure(int x, int y, int z, int x2, int y2, int z2) {
        WorldFeatureComponent rooms = new WorldFeatureComponent();
        rooms.add(drawShell(random, lockedCarvedHolystone, EAST, 16, UP, 12, SOUTH, 16, x, y, z, true));
        rooms.add(drawVolume(0, 0, EAST, 14, UP, 10, SOUTH, 14, x + 1, y + 1, z + 1, true));

        rooms.add(drawShell(random, lockedCarvedHolystone, EAST, 4, UP, 4, SOUTH, 4, x + 6, y - 2, z + 6, true));
        rooms.add(drawVolume(0, 0, EAST, 2, UP, 2, SOUTH, 2, x + 7, y - 1, z + 7, true));
        rooms.place(world);

        DungeonMapEntrySlider dungeon = AetherDimension.dungeonMap.register(DungeonMapEntrySlider.class);
        dungeon.setPosition(new WorldFeaturePoint(x + 8, y + 2, z + 8));
        dungeon.setClearArea(new Pair<>(
                new WorldFeaturePoint(x, y - 2, z),
                new WorldFeaturePoint(x + 16, y + 14, z + 16)
        ));
        List<WorldFeaturePoint> treasureDoor = new ArrayList<>();
        treasureDoor.add(new WorldFeaturePoint(x + 7, y + 1, z + 7));
        treasureDoor.add(new WorldFeaturePoint(x + 8, y + 1, z + 7));
        treasureDoor.add(new WorldFeaturePoint(x + 7, y + 1, z + 8));
        treasureDoor.add(new WorldFeaturePoint(x + 8, y + 1, z + 8));
        dungeon.setTreasureDoor(treasureDoor);

        WorldFeatureAetherBronzeChest.bronzeChest().place(world, random, x2, y2, z2);

        MobBossSlider boss = new MobBossSlider(world);
        boss.moveTo(x + 8, y + 2, z + 8, 0f, 0f);
        boss.setReturnPoint(new WorldFeaturePoint(x + 8, y + 2, z + 8));
        boss.setTrophy(AetherItems.KEY_BRONZE.getDefaultStack());
        boss.setDungeonID(dungeon.getId());
        world.entityJoinedWorld(boss);
    }

    private void makeAnotherRoom(World world, Random random, int y, int x2, int z2) {
        WorldFeatureComponent rooms = new WorldFeatureComponent();
        rooms.add(drawShell(random, carvedHolystone, EAST, 12, UP, 12, SOUTH, 12, x2, y, z2, true));
        rooms.add(drawVolume(0, 0, EAST, 10, UP, 10, SOUTH, 10, x2 + 1, y + 1, z2 + 1, true));
        this.placeComponent(rooms);
        this.addSquareTube(holystone, x2 - 5, y, z2 + 3, 6, 6, 6);
    }
}
