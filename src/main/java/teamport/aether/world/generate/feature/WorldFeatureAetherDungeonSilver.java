package teamport.aether.world.generate.feature;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.boss.valkyrie.queen.MobBossValkyrie;
import teamport.aether.helper.Pair;
import teamport.aether.items.AetherItems;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.DungeonMapEntry;
import teamport.aether.world.generate.feature.components.WorldFeatureBlock;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static teamport.aether.world.generate.feature.components.WorldFeatureBlock.wfb;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;

public class WorldFeatureAetherDungeonSilver extends WorldFeature {
    public static BlockPallet angelic = new BlockPallet();
    public static BlockPallet holystone = new BlockPallet();
    public static BlockPallet angelicTrapped = new BlockPallet();
    public float angle = 0;
    public WorldFeaturePoint dungeonAnker;
    public WorldFeaturePoint bossPosition;
    public World world;
    public Random random;

    protected DungeonMapEntry dungeon;

    static {
        angelic.addEntry(AetherBlocks.CARVED_ANGELIC_LOCKED.id(), 0, 95);
        angelic.addEntry(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED.id(), 0, 5);

        angelicTrapped.addEntry(AetherBlocks.CARVED_ANGELIC_LOCKED.id(), 0, 85);
        angelicTrapped.addEntry(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED.id(), 0, 5);
        angelicTrapped.addEntry(AetherBlocks.CARVED_ANGELIC_TRAPPED.id(), 0, 10);

        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE.id(), 0, 90);
        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), 0, 10);
    }
    public static final WeightedRandomBag<WeightedRandomLootObject> LOOT_NORMAL = new WeightedRandomBag<>();
    static {
        // unlucky
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject((ItemStack)null), (double)900.0F);

        // common
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMBROSIUM.getDefaultStack(), 1, 10), 600.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_HEALING_STONE.getDefaultStack(), 1, 16), 200.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherBlocks.TORCH_AMBROSIUM.getDefaultStack(), 1, 12), 400.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMBER.getDefaultStack(), 1, 9), 300.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ZANITE.getDefaultStack(), 1, 5), 300.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherBlocks.AERCLOUD_WHITE.getDefaultStack(), 1, 5), 300.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.BUCKET_SKYROOT_POISON.getDefaultStack()), 150.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.BUCKET_SKYROOT.getDefaultStack()), 150.0);


        // tools
        int minTool = MathHelper.ceil(AetherItems.TOOL_PICKAXE_ZANITE.getMaxDamage() / 10.0);
        int maxTool = MathHelper.ceil(AetherItems.TOOL_PICKAXE_ZANITE.getMaxDamage() / 2.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_PICKAXE_ZANITE.getDefaultStack()).setRandomMetadata(minTool, maxTool), 100.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_AXE_ZANITE.getDefaultStack()).setRandomMetadata(minTool, maxTool), 100.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_ZANITE.getDefaultStack()).setRandomMetadata(minTool, maxTool), 100.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SHOVEL_ZANITE.getDefaultStack()).setRandomMetadata(minTool, maxTool), 100.0);

        // armor
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_BOOTS_ZANITE.getDefaultStack())
                .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_BOOTS_ZANITE.getMaxDamage() / 10.0), MathHelper.ceil(AetherItems.ARMOR_BOOTS_ZANITE.getMaxDamage() / 2.0)), 100.0);

        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_HELMET_ZANITE.getDefaultStack())
                .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_HELMET_ZANITE.getMaxDamage() / 10.0), MathHelper.ceil(AetherItems.ARMOR_HELMET_ZANITE.getMaxDamage() / 2.0)), 100.0);

        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_ZANITE.getDefaultStack()
        ).setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_LEGGINGS_ZANITE.getMaxDamage() / 10.0), MathHelper.ceil(AetherItems.ARMOR_LEGGINGS_ZANITE.getMaxDamage() / 2.0)), 100.0);

        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_ZANITE.getDefaultStack())
                .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_CHESTPLATE_ZANITE.getMaxDamage() / 10.0), MathHelper.ceil(AetherItems.ARMOR_CHESTPLATE_ZANITE.getMaxDamage() / 2.0)), 100.0);

        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_ZANITE.getDefaultStack())
                .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_GLOVES_ZANITE.getMaxDamage() / 10.0), MathHelper.ceil(AetherItems.ARMOR_CHESTPLATE_ZANITE.getMaxDamage() / 2.0)), 100.0);

        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_ZANITE.getDefaultStack())
                .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_TALISMAN_ZANITE.getMaxDamage() / 10.0), MathHelper.ceil(AetherItems.ARMOR_TALISMAN_ZANITE.getMaxDamage() / 2.0)), 100.0);

        // ammo
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_GOLDEN.getDefaultStack(), 1, 5).setRandomMetadata(1, 1), 600.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_POISON.getDefaultStack(), 1, 3).setRandomMetadata(1, 1), 400.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_ENCHANTED.getDefaultStack(), 1, 3).setRandomMetadata(1, 1), 200.0);

        // rare tool & armor
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_GRAVITITE.getDefaultStack()), 100.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_CHAIN.getDefaultStack()), 50.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SHOOTER.getDefaultStack()), 100.0);
        // jack pot
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.EGG_MOA_BLUE.getDefaultStack()), 50.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.MEDAL_VICTORY.getDefaultStack()), 10.0);
        for (int i = 0; i < 9; ++i) {
            LOOT_NORMAL.addEntry(new WeightedRandomLootObject(new ItemStack(Item.itemsList[AetherItems.RECORD_DAWN.id + i])), 10.0);
        }
    }
    public static final WeightedRandomBag<WeightedRandomLootObject> LOOT_RARE = new WeightedRandomBag<>();
    static {
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_BLUE.getDefaultStack(), 1, 16), 200.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_GOLD.getDefaultStack(), 1, 8), 200.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_KNIFE_LIGHTNING.getDefaultStack(), 1, 16), 800.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_HOLY.getDefaultStack()), 200.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_STAFF_CLOUD.getDefaultStack()), 200.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CAPE_INVISIBILITY.getDefaultStack()), 200.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_FEATHER_GOLD.getDefaultStack()), 200.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_BUBBLE.getDefaultStack()), 200.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_HELMET_NEPTUNE.getDefaultStack()), 50.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_BOOTS_NEPTUNE.getDefaultStack()), 50.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_NEPTUNE.getDefaultStack()), 50.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_NEPTUNE.getDefaultStack()), 50.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_NEPTUNE.getDefaultStack()), 50.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_AXE_VALKYRIE.getDefaultStack()), 50.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_VALKYRIE.getDefaultStack()), 50.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SHOVEL_VALKYRIE.getDefaultStack()), 50.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_PICKAXE_VALKYRIE.getDefaultStack()), 50.0);
    }

    public void placeComponent(WorldFeatureComponent component) {
        component.rotateYAxis(dungeonAnker.x,dungeonAnker.y,dungeonAnker.z, angle);
        component.place(world);

    }
    public void modifyPoint(WorldFeaturePoint point){
        point.rotateFixPointYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z,angle);
    }
    public WorldFeaturePoint createModifiedPoint(int ix, int iy, int iz){
        WorldFeaturePoint pos = new WorldFeaturePoint(ix,iy,iz);
        pos.rotateFixPointYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z,angle);
        return pos;
    }

    @Override
    public boolean place(World world, Random random, int x, int y, int z) {
        this.angle = random.nextInt(4) * 90.0F;
        this.dungeonAnker = new WorldFeaturePoint(x,y,z);
        this.world = world;
        this.random = random;
        this.bossPosition = this.createModifiedPoint(x - 15, y + 4, z + 42);

        dungeon = AetherDimension.dungeonMap.register();
        dungeon.setPosition(bossPosition);

        createBaseStructure(x,y,z);
        createInnerDecorations(x,y,z);
        createBossAndTreasure(x, y, z);
        createOuterDecorations(x, y, z);
        return true;
    }

    private void createBossAndTreasure(int x, int y, int z) {
        // Place boss, chest and door
        MobBossValkyrie boss = new MobBossValkyrie(world);
        boss.moveTo(bossPosition.x, bossPosition.y, bossPosition.z, 0f, 0f);
        boss.setReturnPoint(new WorldFeaturePoint(bossPosition.x, bossPosition.y, bossPosition.z));
        boss.setDungeonID(dungeon.getId());
        boss.setTrophy(AetherItems.KEY_SILVER.getDefaultStack());
        world.setBlockAndMetadataWithNotify(bossPosition.x, y, bossPosition.z, AetherBlocks.SILVER_CHEST_DUNGEON_LOCKED.id(), 4);
        Container inventory = BlockLogicChest.getInventory(world, bossPosition.x, y, bossPosition.z);
        for (int i = 0; i < 6 + random.nextInt(6); i++) {
            inventory.setItem(
                    random.nextInt(inventory.getContainerSize()),
                    LOOT_RARE.getRandom().getItemStack(random)
            );
        }

        WorldFeaturePoint[] treasureDoor = {
                new WorldFeaturePoint(x - 14, y + 2, z + 41),
                new WorldFeaturePoint(x - 14, y + 2, z + 42),
                new WorldFeaturePoint(x - 14, y + 2, z + 43),
                new WorldFeaturePoint(x - 15, y + 2, z + 41),
                new WorldFeaturePoint(x - 15, y + 2, z + 42),
                new WorldFeaturePoint(x - 15, y + 2, z + 43),
        };

        for (WorldFeaturePoint pos : treasureDoor){ pos.rotateFixPointYAxis(x, y, z, angle); }
        dungeon.setDoorBlocks(treasureDoor);

        world.entityJoinedWorld(boss);
    }

    public WorldFeaturePoint getPos(int ix, int iy, int iz) {
        WorldFeaturePoint pos = new WorldFeaturePoint(ix,iy,iz);
        pos.rotateFixPointYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z,angle);
        return pos;
    }

    private void createBaseStructure(int x, int y, int z) {
        // clear the volume of the structure of blocks
        WorldFeatureComponent clear = drawVolume(0, 0, Direction.SOUTH, 55, Direction.UP, 30, Direction.WEST, 30, x, y, z, true);
        List<WorldFeaturePoint> cloudPoints = getCloudPoints(x,y,z);

        // holystone base
        WorldFeatureComponent base = drawVolume(random, holystone, Direction.SOUTH, 55, Direction.DOWN, 5, Direction.WEST, 30, x, y, z, false);

        Pair<WorldFeaturePoint, WorldFeaturePoint> clearArea = new Pair<>(
            new WorldFeaturePoint(x + 2, y, z - 3),
            new WorldFeaturePoint(x - 31, y + 23, z + 56)
        );

        clearArea.first.rotateFixPointYAxis(dungeonAnker.x,dungeonAnker.y,dungeonAnker.z, angle);
        clearArea.second.rotateFixPointYAxis(dungeonAnker.x,dungeonAnker.y,dungeonAnker.z, angle);
        dungeon.setClearArea(clearArea);

        int ix = base.tail.x;
        int iz = base.tail.z;

        // generate 3x3x3 grid of rooms
        WorldFeatureComponent rooms = new WorldFeatureComponent();
        WorldFeatureComponent chests = new WorldFeatureComponent();
        int ROOM_WIDTH = 7, ROOM_HEIGHT = 5;
        for (int LEVEL = 2; LEVEL >= 0; LEVEL--) {
            boolean genStairs = false;
            int counter = 0;
            int stairNum = random.nextInt(8);
            if (LEVEL < 2) {
                genStairs = true;
            }
            for (int COLUMN = 0; COLUMN < 3; COLUMN++) {
                for (int ROW = 0; ROW < 3; ROW++) {
                    int roomX = x - 4 - COLUMN * ROOM_WIDTH;
                    int roomY = y + ROOM_HEIGHT * LEVEL;
                    int roomZ = z + 4 + ROW * ROOM_WIDTH;
                    if (counter == stairNum && genStairs) {
                        rooms.add(createStaircaseRoom(roomX, roomY, roomZ, false, true));
                        genStairs = false;
                        continue;
                    }
                    if (COLUMN == 2 && ROW == 2) {
                        if (LEVEL == 2) {
                            rooms.add(createRoom(roomX, roomY, roomZ, true));
                        } else {
                            rooms.add(createStaircaseRoom(roomX, roomY, roomZ, true, false));
                        }
                        continue;
                    }
                    if (random.nextInt(3) == 0) {
                        rooms.add(createRoom(roomX, roomY, roomZ, false));
                    } else {
//                        WorldFeatureComponent[] treasureRoom = createTreasureRoom(roomX, roomY, roomZ, false);
                        WorldFeatureComponent[] treasureRoom = createTreasureRoom(roomX, roomY, roomZ, true);
                        rooms.add(treasureRoom[0]);
                        chests.add(treasureRoom[1]);
                    }
                    counter++;
                }
            }
        }

        this.placeComponent(clear);
        // create clouds
        for(WorldFeaturePoint cloudPoint : cloudPoints){
            this.modifyPoint(cloudPoint);
            new WorldFeatureClouds(AetherBlocks.AERCLOUD_WHITE.id(), (6 + random.nextInt(10)), false).place(world, random, cloudPoint.x, cloudPoint.y, cloudPoint.z);
        }

        this.placeComponent(base);
        this.placeComponent(rooms);
        this.placeComponent(chests);

        // Fill the chests with loot
        for(WorldFeatureBlock chest: chests.blockList){
            if(chest.blockID == AetherBlocks.CHEST_PLANKS_SKYROOT.id()){
                populateChest(world, chest, random, LOOT_NORMAL, (int)Math.round((random.nextGaussian() + 1) * 6));
            }
        }

        // Outer walls of dungeon itself
        this.placeComponent(drawShell(random, angelic, Direction.SOUTH, 22, Direction.UP, 16, Direction.WEST, 22, x - 4, y, z + 4, true));
        this.placeComponent(drawShell(random, angelic, Direction.NORTH, 26, Direction.UP, 16, Direction.EAST, 22, ix + 4, y, iz - 5, false));

        // Entrance hole into boss room
        this.placeComponent(drawPlane(0, 0, Direction.UP, 2, Direction.WEST, 2, x - 21, y + 1, z + 25, true));

        //// Throne room
        this.placeComponent(drawPlane(random, angelic, Direction.WEST, 22, Direction.SOUTH, 25, x - 4, y + 1, z + 26, false));
    }

    public void createInnerDecorations(int x, int y, int z) {
        // Create semicircle
        createSemiCircle(x, y, z);

        // Fountains
        createFountain(x - 5, y + 2, z + 33, Direction.WEST);
        createFountain(x - 24, y + 2, z + 33, Direction.EAST);

        // Tree pods
        createTreePods(x, y, z);

        // Throne
        this.placeComponent(drawPlane(random, angelic, Direction.WEST, 8, Direction.SOUTH, 6, x - 11, y + 2, z + 44, true));
        this.placeComponent(drawShell(random, angelic, Direction.WEST, 4, Direction.NORTH, 4, Direction.DOWN, 4, x - 13, y + 2, z + 44, true));

        // Chest hole
        this.placeComponent(drawVolume(0, 0, Direction.WEST, 2, Direction.NORTH, 2, Direction.DOWN, 2, x - 14, y + 1, z + 43, true));

        // Torches
        WorldFeatureComponent torches = new WorldFeatureComponent();
        torches.add(wfb(x - 11, y + 3, z + 44, AetherBlocks.TORCH_AMBROSIUM.id(), 0, true));
        torches.add(wfb(x - 11, y + 3, z + 49, AetherBlocks.TORCH_AMBROSIUM.id(), 0, true));
        torches.add(wfb(x - 18, y + 3, z + 49, AetherBlocks.TORCH_AMBROSIUM.id(), 0, true));
        torches.add(wfb(x - 18, y + 3, z + 44, AetherBlocks.TORCH_AMBROSIUM.id(), 0, true));
        this.placeComponent(torches);

        // Throne cushion
        this.placeComponent(drawPlane(random, angelic, Direction.WEST, 4, Direction.UP, 6, x - 13, y + 3, z + 49, true));
        this.placeComponent(drawVolume(random, angelic, Direction.WEST, 4, Direction.NORTH, 4, Direction.UP, 2, x - 13, y + 3, z + 49, true));
        this.placeComponent(drawPlane(Blocks.WOOL.id(), 11, Direction.WEST, 2, Direction.NORTH, 2, x - 14, y + 4, z + 48, true));
        this.placeComponent(drawLine(random, angelic, Direction.NORTH, 3, x - 13, y + 5, z + 48, true));
        this.placeComponent(drawLine(random, angelic, Direction.NORTH, 3, x - 16, y + 5, z + 48, true));

        // Ceiling lights
        createLight(x - 10, y + 14, z + 28);
        createLight(x - 19, y + 14, z + 28);
        createLight(x - 10, y + 14, z + 43);
        createLight(x - 19, y + 14, z + 43);

        // Staircase
        this.placeComponent(drawPlane(0, 0, Direction.WEST, 2, Direction.DOWN, 4, x - 14, y, z, false));
        this.placeComponent(drawPlane(0, 0, Direction.WEST, 2, Direction.DOWN, 3, x - 14, y, z + 1, false));
        this.placeComponent(drawPlane(0, 0, Direction.WEST, 2, Direction.DOWN, 2, x - 14, y, z + 2, false));
        this.placeComponent(drawPlane(0, 0, Direction.WEST, 2, Direction.DOWN, 1, x - 14, y, z + 3, false));
    }

    public void createOuterDecorations(int x, int y, int z) {
        WorldFeatureComponent roof = new WorldFeatureComponent();
        // Roof
        for (int i = 0; i < 7; i++) {
            roof.add(drawPlane(random, angelic, Direction.SOUTH, 57, Direction.WEST, 32 - 4 * i, x + 1 - 2 * i, y + 16 + i, z - 1, true));
        }

        WorldFeatureComponent pillars = new WorldFeatureComponent();
        // Pillars
        for (int i = 0; i < 14; i++) {
            pillars.add(createPillar(random, x, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4));
            pillars.add(createPillar(random, x - 27, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4));
            if (i == 0 || i == 13) {
                pillars.add(createPillar(random, x - 4, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4));
                pillars.add(createPillar(random, x - 8, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4));

                pillars.add(createPillar(random, x - 23, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4));
                pillars.add(createPillar(random, x - 19, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4));
            }
        }

        this.placeComponent(roof);
        this.placeComponent(pillars);
        // Entrance hole into building
        this.placeComponent(drawPlane(0, 0, Direction.WEST, 2, Direction.UP, 2, x - 14, y + 1, z + 4, false));
    }

    private void createTreePods(int x, int y, int z) {
        WorldFeatureComponent trees = new WorldFeatureComponent();
        WorldFeatureComponent pod = new WorldFeatureComponent();
        for (int i = 0; i < 2; i++) {
            int bx = x - 6 - i * 15;
            int bz = z + 45;
            pod.add(drawPlane(random, angelic, Direction.WEST, 3, Direction.SOUTH, 3, bx, y + 2, bz, true));
            pod.add(wfb(bx - 1, y + 2, bz + 1, AetherBlocks.DIRT_AETHER.id(), 0, true));
            if (world.rand.nextInt(6) == 0) {
                pod.add(wfb(bx - 1, y + 3, bz + 1, AetherBlocks.SAPLING_OAK_GOLDEN.id(), 0, false));
            } else {
                ///  we need a temp block that won't pop off unlike sapling
                trees.add(wfb(bx - 1, y + 3, bz + 1, 0, 0, false));
            }
            pod.add(wfb(bx, y + 3, bz, AetherBlocks.TORCH_AMBROSIUM.id(), 0, true));
            pod.add(wfb(bx - 2, y + 3, bz, AetherBlocks.TORCH_AMBROSIUM.id(), 0, true));
            pod.add(wfb(bx, y + 3, bz + 2, AetherBlocks.TORCH_AMBROSIUM.id(), 0, true));
            pod.add(wfb(bx - 2, y + 3, bz + 2, AetherBlocks.TORCH_AMBROSIUM.id(), 0, true));
        }
        this.placeComponent(pod);
        this.placeComponent(trees);
        for(WorldFeatureBlock tree: trees.blockList){
            new WorldFeatureTreeGoldenOak(AetherBlocks.LEAVES_OAK_GOLDEN.id(), AetherBlocks.LOG_OAK_GOLDEN.id()).place(world, random, tree.x, tree.y, tree.z);
        }
    }

    public void createSemiCircle(int x, int y, int z) {
        WorldFeatureComponent semi = new WorldFeatureComponent();

        // Big floor semicircle (plus oh god this code is awful 💀)
        semi.add(drawPlane(0, 0, Direction.WEST, 20, Direction.SOUTH, 4, x - 5, y + 1, z + 26, false));
        semi.add(drawPlane(0, 0, Direction.WEST, 18, Direction.SOUTH, 1, x - 6, y + 1, z + 30, false));
        semi.add(drawPlane(0, 0, Direction.WEST, 16, Direction.SOUTH, 2, x - 7, y + 1, z + 31, false));
        semi.add(drawPlane(0, 0, Direction.WEST, 14, Direction.SOUTH, 1, x - 8, y + 1, z + 33, false));
        semi.add(drawPlane(0, 0, Direction.WEST, 10, Direction.SOUTH, 1, x - 10, y + 1, z + 34, false));
        semi.add(drawPlane(0, 0, Direction.WEST, 4, Direction.SOUTH, 1, x - 13, y + 1, z + 35, false));
        this.placeComponent(semi);
    }

    public List<WorldFeaturePoint> getCloudPoints(int x, int y, int z) {
        List<WorldFeaturePoint> cloud = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            cloud.add(new WorldFeaturePoint(x + 5 - random.nextInt(40), y - 2 - random.nextInt(5), z - 5 + random.nextInt(65)));
        }
        return cloud;
    }

    public void createFountain(int x, int y, int z, Direction directionEW) {
        int[] walls = new int[]{2, 3, 4, 4, 4, 4, 3, 2};
        boolean[] torches = new boolean[]{false, false, true, false, false, true, false, false};
        int[] water = new int[]{0, 2, 3, 3, 3, 3, 2, 0};
        WorldFeatureComponent fountain = new WorldFeatureComponent();

        for (int i = 0; i < walls.length; i++) {
            WorldFeatureComponent end = drawLine(random, angelic, directionEW, walls[i], x, y, z + i, false);
            fountain.add(end);
            if (torches[i]) {
                fountain.add(wfb(end.tail.x, end.tail.y + 1, end.tail.z, AetherBlocks.TORCH_AMBROSIUM.id(), 0, true));
            }
            if (water[i] == 0) continue;
            fountain.add(drawLine(Blocks.FLUID_WATER_STILL.id(), 0, directionEW, water[i], x, y, z + i, false));
        }
        this.placeComponent(fountain);
    }

    public void createLight(int x, int y, int z) {
        WorldFeatureComponent lights = new WorldFeatureComponent();
        lights.add(wfb(x, y, z, AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, false));
        lights.add(wfb(x, y - 1, z, AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, false));
        lights.add(wfb(x, y - 2, z, AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, false));
        lights.add(wfb(x, y - 3, z, Blocks.GLOWSTONE.id(), 0, true));
        lights.add(wfb(x, y - 4, z, Blocks.GLOWSTONE.id(), 0, true));
        lights.add(wfb(x - 1, y - 4, z, Blocks.GLOWSTONE.id(), 0, true));
        lights.add(wfb(x + 1, y - 4, z, Blocks.GLOWSTONE.id(), 0, true));
        lights.add(wfb(x, y - 4, z - 1, Blocks.GLOWSTONE.id(), 0, true));
        lights.add(wfb(x, y - 4, z + 1, Blocks.GLOWSTONE.id(), 0, true));
        lights.add(wfb(x, y - 5, z, Blocks.GLOWSTONE.id(), 0, true));
        this.placeComponent(lights);
    }

    public WorldFeatureComponent createPillar(Random random, int x, int y, int z) {
        WorldFeatureComponent pillar = new WorldFeatureComponent();
        pillar.add(drawPlane(random, angelic, Direction.SOUTH, 3, Direction.WEST, 3, x, y, z, false));
        pillar.add(drawPlane(random, angelic, Direction.SOUTH, 3, Direction.WEST, 3, x, y + 14, z, false));
        pillar.add(drawLine(AetherBlocks.PILLAR.id(), 0, Direction.UP, 13, x + Direction.WEST.getOffsetX(), y, z + Direction.SOUTH.getOffsetZ(), false));
        pillar.add(wfb(x + Direction.WEST.getOffsetX(), y + 13, z + Direction.SOUTH.getOffsetZ(), AetherBlocks.PILLAR_CAPSTONE.id(), 0, false));
        return pillar;
    }

    private WorldFeatureComponent createEntranceRoom(int x, int y, int z) {
        WorldFeatureComponent room = new WorldFeatureComponent();
        room.add(drawShell(random, angelicTrapped, Direction.SOUTH, 8, Direction.UP, 6, Direction.WEST, 8, x, y, z, true));
        room.add(drawPlane(0, 0, Direction.UP, 2, Direction.WEST, 2, x - 3, y + 1, z + 7, true));
        room.add(drawPlane(0, 0, Direction.UP, 2, Direction.SOUTH, 2, x, y + 1, z + 3, true));
        room.add(drawPlane(0, 0, Direction.UP, 2, Direction.SOUTH, 2, x - 7, y + 1, z + 3, true));
        return room;
    }

    public WorldFeatureComponent createRoom(int x, int y, int z, boolean forceOpen) {
        WorldFeatureComponent room = new WorldFeatureComponent();
        room.add(drawShell(random, angelicTrapped, Direction.SOUTH, 8, Direction.UP, 6, Direction.WEST, 8, x, y, z, true));
        if (random.nextInt(2) != 0 || forceOpen) {
            room.add(drawPlane(0, 0, Direction.UP, 2, Direction.WEST, 2, x - 3, y + 1, z, true));
        }
        if (random.nextInt(2) != 0 || forceOpen) {
            room.add(drawPlane(0, 0, Direction.UP, 2, Direction.WEST, 2, x - 3, y + 1, z + 7, true));
        }
        if (random.nextInt(2) != 0 || forceOpen) {
            room.add(drawPlane(0, 0, Direction.UP, 2, Direction.SOUTH, 2, x, y + 1, z + 3, true));
        }
        if (random.nextInt(2) != 0 || forceOpen) {
            room.add(drawPlane(0, 0, Direction.UP, 2, Direction.SOUTH, 2, x - 7, y + 1, z + 3, true));
        }
        return room;
    }

    public WorldFeatureComponent[] createTreasureRoom(int x, int y, int z, boolean forceOpen) {
        WorldFeatureComponent room = createRoom(x, y, z, forceOpen);
        room.add(drawPlane(random, angelic, Direction.SOUTH, 2, Direction.WEST, 2, x - 3, y + 1, z + 3, true));
        WorldFeatureComponent chests = new WorldFeatureComponent();

        int chestCount = 0;
        if (random.nextInt(3) == 0) {
            chestCount++;
            chests.add(placeChestOrMimic(random, x - 3, y + 2, z + 3));
        }
        if (random.nextInt(3) == 0) {
            chestCount++;
            chests.add(placeChestOrMimic(random, x - 4, y + 2, z + 3));
        }
        if (random.nextInt(3) == 0) {
            chestCount++;
            chests.add(placeChestOrMimic(random, x - 3, y + 2, z + 4));
        }
        if (random.nextInt(2) == 0 && chestCount < 2) {
            chests.add(placeChestOrMimic(random, x - 4, y + 2, z + 4));
        }
        return new WorldFeatureComponent[]{room, chests};
    }

    public WorldFeatureComponent createStaircaseRoom(
            int x, int y, int z,
            boolean forceWalls,
            boolean forceOpen
    ) {
        WorldFeatureComponent staircase = new WorldFeatureComponent();
        if (forceWalls) {
            staircase.add(drawShell(random, angelic, Direction.SOUTH, 8, Direction.UP, 6, Direction.WEST, 8, x, y, z, true));
        } else {
            createRoom(x, y, z, forceOpen);
        }
        staircase.add(drawPlane(0, 0, Direction.SOUTH, 4, Direction.WEST, 4, x - 2, y + 5, z + 2, true));
        staircase.add(drawVolume(random, angelic, Direction.SOUTH, 2, Direction.WEST, 2, Direction.UP, 9, x - 3, y + 1, z + 3, true));

        staircase.add(wfb(x - 2, y + 1, z + 2, AetherBlocks.SLAB_CARVED_STONE.id(), 0, true));
        staircase.add(wfb(x - 2, y + 1, z + 3, AetherBlocks.SLAB_CARVED_STONE.id(), 1, true));
        staircase.add(wfb(x - 2, y + 2, z + 4, AetherBlocks.SLAB_CARVED_STONE.id(), 0, true));
        staircase.add(wfb(x - 2, y + 1, z + 4, AetherBlocks.SLAB_CARVED_STONE.id(), 2, true));
        staircase.add(wfb(x - 2, y + 2, z + 5, AetherBlocks.SLAB_CARVED_STONE.id(), 1, true));

        staircase.add(wfb(x - 3, y + 3, z + 5, AetherBlocks.SLAB_CARVED_STONE.id(), 0, true));
        staircase.add(wfb(x - 3, y + 2, z + 5, AetherBlocks.SLAB_CARVED_STONE.id(), 2, true));
        staircase.add(wfb(x - 4, y + 3, z + 5, AetherBlocks.SLAB_CARVED_STONE.id(), 1, true));
        staircase.add(wfb(x - 5, y + 4, z + 5, AetherBlocks.SLAB_CARVED_STONE.id(), 0, true));
        staircase.add(wfb(x - 5, y + 3, z + 5, AetherBlocks.SLAB_CARVED_STONE.id(), 2, true));

        staircase.add(wfb(x - 5, y + 4, z + 4, AetherBlocks.SLAB_CARVED_STONE.id(), 1, true));
        staircase.add(wfb(x - 5, y + 5, z + 3, AetherBlocks.SLAB_CARVED_STONE.id(), 0, true));
        staircase.add(wfb(x - 5, y + 4, z + 3, AetherBlocks.SLAB_CARVED_STONE.id(), 2, true));
        staircase.add(wfb(x - 5, y + 5, z + 2, AetherBlocks.SLAB_CARVED_STONE.id(), 1, true));

        staircase.add(wfb(x - 4, y + 5, z + 2, AetherBlocks.SLAB_CARVED_STONE.id(), 1, true));
        staircase.add(wfb(x - 3, y + 5, z + 2, AetherBlocks.SLAB_CARVED_STONE.id(), 1, true));
        return staircase;
    }
}
