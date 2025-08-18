package teamport.aether.world.generate.feature.dungeon;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
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
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.WorldFeatureAetherClouds;
import teamport.aether.world.generate.feature.WorldFeatureAetherTreeGoldenOak;
import teamport.aether.world.generate.feature.chests.WorldFeatureAetherSilverChest;
import teamport.aether.world.generate.feature.components.WorldFeatureBlock;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;
import teamport.aether.world.generate.feature.components.WorldFeatureSilverMaze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static teamport.aether.world.generate.feature.components.WorldFeatureBlock.wfb;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;

public class WorldFeatureAetherSilverDungeon extends WorldFeature {
    public static BlockPallet angelic = new BlockPallet();
    public static BlockPallet holystone = new BlockPallet();
    public float angle = 0;
    public WorldFeaturePoint dungeonAnker;
    public WorldFeaturePoint bossPosition;
    public WorldFeatureSilverMaze silverMaze;
    public World world;
    public Random random;

    protected DungeonMapEntry dungeon;

    static {
        angelic.addEntry(AetherBlocks.CARVED_ANGELIC_LOCKED.id(), 0, 95);
        angelic.addEntry(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED.id(), 0, 5);

        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE.id(), 0, 90);
        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), 0, 10);
    }

    public static final WeightedRandomBag<WeightedRandomLootObject> LOOT_NORMAL = new WeightedRandomBag<>();

    static {
        // unlucky
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(null), 900.0F);

        // common
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMBROSIUM.getDefaultStack(), 1, 10), 600.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherBlocks.TORCH_AMBROSIUM.getDefaultStack(), 1, 12), 400.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMBER.getDefaultStack(), 1, 9), 300.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ZANITE.getDefaultStack(), 1, 5), 300.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherBlocks.AERCLOUD_WHITE.getDefaultStack(), 1, 5), 300.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.BUCKET_SKYROOT.getDefaultStack()), 150.0);

        // ammo
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_GOLDEN.getDefaultStack(), 1, 10).setRandomMetadata(1, 1), 600.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_POISON.getDefaultStack(), 1, 6).setRandomMetadata(1, 1), 400.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_ENCHANTED.getDefaultStack(), 1, 6).setRandomMetadata(1, 1), 200.0);

        // jack pot
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.EGG_MOA_BLUE.getDefaultStack()), 50.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.MEDAL_VICTORY.getDefaultStack()), 10.0);
    }

    public static final WeightedRandomBag<WeightedRandomLootObject> LOOT_FOOD = new WeightedRandomBag<>();

    static {
        LOOT_FOOD.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_HEALING_STONE.getDefaultStack(), 1, 8), 100);
        LOOT_FOOD.addEntry(new WeightedRandomLootObject(AetherItems.BUCKET_SKYROOT_POISON.getDefaultStack()), 50);
        LOOT_FOOD.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_BLUE.getDefaultStack(), 1, 4), 2);
        LOOT_FOOD.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_GOLD.getDefaultStack(), 1, 2), 1);
    }

    public static final WeightedRandomBag<WeightedRandomLootObject> LOOT_ARMOR = new WeightedRandomBag<>();

    static {
        // unlucky
        LOOT_ARMOR.addEntry(new WeightedRandomLootObject(null), 500.00);
        // tools
        int minTool = MathHelper.ceil(AetherItems.TOOL_PICKAXE_ZANITE.getMaxDamage() / 2.0);
        int maxTool = MathHelper.ceil(AetherItems.TOOL_PICKAXE_ZANITE.getMaxDamage() * 3.0f / 4.0f);
        LOOT_ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_PICKAXE_ZANITE.getDefaultStack()).setRandomMetadata(minTool, maxTool), 100.0);
        LOOT_ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_AXE_ZANITE.getDefaultStack()).setRandomMetadata(minTool, maxTool), 100.0);
        LOOT_ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_ZANITE.getDefaultStack()).setRandomMetadata(minTool, maxTool), 100.0);
        LOOT_ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SHOVEL_ZANITE.getDefaultStack()).setRandomMetadata(minTool, maxTool), 100.0);

        // armor
        float minArmorFactor = 1.0f / 2.0f;
        float maxArmorFactor = 3.0f / 4.0f;
        LOOT_ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_BOOTS_ZANITE.getDefaultStack())
                .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_BOOTS_ZANITE.getMaxDamage() * minArmorFactor), MathHelper.ceil(AetherItems.ARMOR_BOOTS_ZANITE.getMaxDamage() * maxArmorFactor)), 100.0);

        LOOT_ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_HELMET_ZANITE.getDefaultStack())
                .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_HELMET_ZANITE.getMaxDamage() * minArmorFactor), MathHelper.ceil(AetherItems.ARMOR_HELMET_ZANITE.getMaxDamage() * maxArmorFactor)), 100.0);

        LOOT_ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_ZANITE.getDefaultStack()
        ).setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_LEGGINGS_ZANITE.getMaxDamage() * minArmorFactor), MathHelper.ceil(AetherItems.ARMOR_LEGGINGS_ZANITE.getMaxDamage() * maxArmorFactor)), 100.0);

        LOOT_ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_ZANITE.getDefaultStack())
                .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_CHESTPLATE_ZANITE.getMaxDamage() * minArmorFactor), MathHelper.ceil(AetherItems.ARMOR_CHESTPLATE_ZANITE.getMaxDamage() * maxArmorFactor)), 100.0);

        LOOT_ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_ZANITE.getDefaultStack())
                .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_GLOVES_ZANITE.getMaxDamage() * minArmorFactor), MathHelper.ceil(AetherItems.ARMOR_CHESTPLATE_ZANITE.getMaxDamage() * maxArmorFactor)), 100.0);

        LOOT_ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_ZANITE.getDefaultStack())
                .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_TALISMAN_ZANITE.getMaxDamage() * minArmorFactor), MathHelper.ceil(AetherItems.ARMOR_TALISMAN_ZANITE.getMaxDamage() * maxArmorFactor)), 100.0);
        // rare tool & armor
        LOOT_ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_GRAVITITE.getDefaultStack()), 100.0);
        LOOT_ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_CHAIN.getDefaultStack()), 50.0);
        LOOT_ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SHOOTER.getDefaultStack()), 100.0);
    }

    public static final WeightedRandomBag<WeightedRandomLootObject> LOOT_RARE = new WeightedRandomBag<>();

    static {
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_HOLY.getDefaultStack()), 200.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_STAFF_CLOUD.getDefaultStack()), 200.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CAPE_INVISIBILITY.getDefaultStack()), 200.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_FEATHER_GOLD.getDefaultStack()), 200.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_REGEN.getDefaultStack()), 200.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_HELMET_NEPTUNE.getDefaultStack()), 200.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_BOOTS_NEPTUNE.getDefaultStack()), 200.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_NEPTUNE.getDefaultStack()), 200.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_NEPTUNE.getDefaultStack()), 200.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_NEPTUNE.getDefaultStack()), 200.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_AXE_VALKYRIE.getDefaultStack()), 200.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_VALKYRIE.getDefaultStack()), 200.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SHOVEL_VALKYRIE.getDefaultStack()), 200.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_PICKAXE_VALKYRIE.getDefaultStack()), 200.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.RECORD_DAWN.getDefaultStack()), 10.0);

    }

    public WorldFeatureAetherSilverDungeon(int directrion) {
        this.angle = directrion * 90;
    }

    public WorldFeatureAetherSilverDungeon() {
    }

    public static WorldFeatureAetherSilverDungeon silverDungeon(Random random) {
        return new WorldFeatureAetherSilverDungeon(random.nextInt(4));
    }

    public void placeComponent(WorldFeatureComponent component) {
        component.rotateYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
        component.place(world);

    }

    public void modifyPoint(WorldFeaturePoint point) {
        point.rotateFixPointYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
    }

    public WorldFeaturePoint createModifiedPoint(int ix, int iy, int iz) {
        WorldFeaturePoint pos = new WorldFeaturePoint(ix, iy, iz);
        pos.rotateFixPointYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
        return pos;
    }

    private boolean canPlace(int x, int y, int z) {
        WorldFeatureComponent checker = drawPlane(0, 0, Direction.SOUTH, 75, Direction.WEST, 50, x + 10, y - 30, z - 10, false);
        checker.rotateYAxis(x, y, z, angle);
        for (WorldFeaturePoint point : checker.blockList) {
            if (!world.canBlockSeeTheSky(point.x, point.y, point.z)) {
                return true;
            }
        }
        return true;
    }


    @Override
    public boolean place(World world, Random random, int x, int y, int z) {
        this.world = world;
        this.random = random;
        if (!canPlace(x, y, z)) return false;
//        this.angle = random.nextInt(4) * 90.0F;
        this.dungeonAnker = new WorldFeaturePoint(x, y, z);
        this.bossPosition = this.createModifiedPoint(x - 15, y + 4, z + 42);
        this.silverMaze = new WorldFeatureSilverMaze();

        dungeon = AetherDimension.dungeonMap.register();
        dungeon.setPosition(bossPosition);
        createBaseStructure(x, y, z);
        createInnerDecorations(x, y, z);
        createBossAndTreasure(x, y, z);
        createOuterDecorations(x, y, z);
        return true;
    }

    private void createBossAndTreasure(int x, int y, int z) {
        Pair<WorldFeaturePoint, WorldFeaturePoint> clearArea = new Pair<>(
                new WorldFeaturePoint(x + 2, y, z - 3),
                new WorldFeaturePoint(x - 31, y + 23, z + 56)
        );
        clearArea.first.rotateFixPointYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
        clearArea.second.rotateFixPointYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
        dungeon.setClearArea(clearArea);

        // Place boss, chest and door
        MobBossValkyrie boss = new MobBossValkyrie(world);
        boss.moveTo(bossPosition.x, bossPosition.y, bossPosition.z, 0f, 0f);
        boss.setReturnPoint(new WorldFeaturePoint(bossPosition.x, bossPosition.y, bossPosition.z));
        boss.setDungeonID(dungeon.getId());
        boss.setTrophy(AetherItems.KEY_SILVER.getDefaultStack());

        WorldFeatureAetherSilverChest.silverChest().place(world, random, bossPosition.x, y, bossPosition.z);
        WorldFeaturePoint[] treasureDoor = {
                new WorldFeaturePoint(x - 14, y + 2, z + 42),
                new WorldFeaturePoint(x - 14, y + 2, z + 43),
                new WorldFeaturePoint(x - 15, y + 2, z + 42),
                new WorldFeaturePoint(x - 15, y + 2, z + 43),
                new WorldFeaturePoint(x - 14, y + 2, z + 41), //non trapdoor
                new WorldFeaturePoint(x - 15, y + 2, z + 41), //non trapdoor
        };

        for (WorldFeaturePoint pos : treasureDoor) {
            pos.rotateFixPointYAxis(x, y, z, angle);
        }
        dungeon.setDoorBlocks(treasureDoor);

        world.entityJoinedWorld(boss);
    }

    private void createBaseStructure(int x, int y, int z) {
        // clear the volume of the structure of blocks
        WorldFeatureComponent clear = drawVolume(0, 0, Direction.SOUTH, 55, Direction.UP, 30, Direction.WEST, 30, x, y, z, true);
        // create clouds
        this.placeComponent(clear);
        List<WorldFeaturePoint> cloudPoints = getCloudPoints(x, y, z);
        for (WorldFeaturePoint cloudPoint : cloudPoints) {
            this.modifyPoint(cloudPoint);
            new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_WHITE.id(), (6 + random.nextInt(10)), false).place(world, random, cloudPoint.x, cloudPoint.y, cloudPoint.z);
        }

        // holystone base
        WorldFeatureComponent base = drawVolume(random, holystone, Direction.SOUTH, 55, Direction.DOWN, 5, Direction.WEST, 30, x, y, z, false);
        int ix = base.tail.x;
        int iz = base.tail.z;
        this.placeComponent(base);

        // generate 3x3x3 grid of rooms
        WorldFeatureComponent[] maze = silverMaze.createMaze(world, random, x, y, z);
        this.placeComponent(maze[0]);
        this.placeComponent(maze[1]);
        for (WorldFeatureBlock chest : maze[1].blockList) {
            populateChest(world, chest, random, LOOT_NORMAL);
        }

        // Outer walls of dungeon itself
        this.placeComponent(drawShell(random, angelic, Direction.SOUTH, 22, Direction.UP, 16, Direction.WEST, 22, x - 4, y, z + 4, true));
        this.placeComponent(drawShell(random, angelic, Direction.NORTH, 26, Direction.UP, 16, Direction.EAST, 22, ix + 4, y, iz - 5, false));

        // Entrance hole into boss room
        this.placeComponent(drawPlane(0, 0, Direction.UP, 2, Direction.WEST, 2, x - 21, y + 1, z + 25, true));

        /// Throne room
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
        for (WorldFeatureBlock tree : trees.blockList) {
            new WorldFeatureAetherTreeGoldenOak(AetherBlocks.LEAVES_OAK_GOLDEN.id(), AetherBlocks.LOG_OAK_GOLDEN.id()).place(world, random, tree.x, tree.y, tree.z);
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
}
