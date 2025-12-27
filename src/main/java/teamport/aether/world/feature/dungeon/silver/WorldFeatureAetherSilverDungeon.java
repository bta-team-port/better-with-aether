package teamport.aether.world.feature.dungeon.silver;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import teamport.aether.AetherMod;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.terrain.BlockLogicCloudBase;
import teamport.aether.entity.boss.valkyrie.queen.MobBossValkyrie;
import teamport.aether.helper.AetherMathHelper;
import teamport.aether.helper.Pair;
import teamport.aether.item.AetherItems;
import teamport.aether.world.feature.chest.WorldFeatureAetherSilverChest;
import teamport.aether.world.feature.dungeon.silver.component.WorldFeatureSilverMaze;
import teamport.aether.world.feature.terrain.WorldFeatureAetherClouds;
import teamport.aether.world.feature.terrain.WorldFeatureAetherTreeGoldenOak;
import teamport.aether.world.feature.util.BlockPallet;
import teamport.aether.world.feature.util.WorldFeatureBlock;
import teamport.aether.world.feature.util.WorldFeatureComponent;
import teamport.aether.world.feature.util.WorldFeaturePoint;
import teamport.aether.world.feature.util.map.WorldFeatureMap;

import java.util.*;

import static teamport.aether.entity.monster.mimic.MobMimic.placeWallace;
import static teamport.aether.world.feature.util.WorldFeatureBlock.wfb;
import static teamport.aether.world.feature.util.WorldFeatureComponent.*;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;

public class WorldFeatureAetherSilverDungeon extends WorldFeatureMap<DungeonLogicSilverDungeon> {
    public static final BlockPallet ANGELIC = new BlockPallet();
    private static final BlockPallet HOLYSTONE = new BlockPallet();
    private final Direction direction;
    private WorldFeaturePoint dungeonAnchor;
    private WorldFeaturePoint bossPosition;
    private WorldFeatureSilverMaze silverMaze;
    private World world;
    private Random random;
    private DungeonLogicSilverDungeon logic;

    static {
        ANGELIC.addEntry(AetherBlocks.CARVED_ANGELIC_LOCKED.id(), 0, 95);
        ANGELIC.addEntry(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED.id(), 0, 5);
        HOLYSTONE.addEntry(AetherBlocks.COBBLE_HOLYSTONE.id(), 0, 90);
        HOLYSTONE.addEntry(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), 0, 10);
    }

    private static final WeightedRandomBag<WeightedRandomLootObject> JUNK = new WeightedRandomBag<>();
    private static final WeightedRandomBag<WeightedRandomLootObject> AMMO = new WeightedRandomBag<>();
    private static final WeightedRandomBag<WeightedRandomLootObject> GADGET = new WeightedRandomBag<>();
    private static final WeightedRandomBag<WeightedRandomLootObject> FOOD = new WeightedRandomBag<>();
    private static final WeightedRandomBag<WeightedRandomLootObject> ARMOR = new WeightedRandomBag<>();

    static {
        // junk         8-10
        JUNK.addEntry(new WeightedRandomLootObject(null), 80);
        JUNK.addEntry(new WeightedRandomLootObject(AetherItems.AMBROSIUM.getDefaultStack(), 1, 10), 40);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.TORCH_AMBROSIUM.getDefaultStack(), 1, 12), 30);
        JUNK.addEntry(new WeightedRandomLootObject(AetherItems.AMBER.getDefaultStack(), 1, 9), 20);
        JUNK.addEntry(new WeightedRandomLootObject(AetherItems.ZANITE.getDefaultStack(), 1, 5), 20);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.SAPLING_OAK_GOLDEN.getDefaultStack(), 1, 2), 20);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.FLOWER_PURPLE.getDefaultStack(), 1, 3), 20);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.FLOWER_WHITE.getDefaultStack(), 1, 3), 20);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.AEROGEL.getDefaultStack(), 1, 2), 20);
        JUNK.addEntry(new WeightedRandomLootObject(AetherItems.BUCKET_SKYROOT.getDefaultStack()), 10);

        // ammo         2-5
        AMMO.addEntry(new WeightedRandomLootObject(null), 8);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_GOLDEN.getDefaultStack(), 2, 6), 4);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_POISON.getDefaultStack(), 2, 6), 2);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_ENCHANTED.getDefaultStack(), 2, 6), 1);

        // food         2-4
        FOOD.addEntry(new WeightedRandomLootObject(null), 16);
        FOOD.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_HEALING_STONE.getDefaultStack(), 1, 4), 16);
        FOOD.addEntry(new WeightedRandomLootObject(AetherItems.BUCKET_SKYROOT_POISON.getDefaultStack()), 8);
        FOOD.addEntry(new WeightedRandomLootObject(AetherItems.BUCKET_SKYROOT_REMEDY.getDefaultStack()), 8);
        FOOD.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_BLUE.getDefaultStack(), 1, 2), 2);
        FOOD.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_GOLD.getDefaultStack(), 1, 2), 1);

        // tools
        int minTool = AetherItems.TOOL_PICKAXE_ZANITE.getMaxDamage() / 2;
        int maxTool = AetherItems.TOOL_PICKAXE_ZANITE.getMaxDamage();
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_PICKAXE_ZANITE.getDefaultStack())
            .setRandomMetadata(minTool, maxTool), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_AXE_ZANITE.getDefaultStack())
            .setRandomMetadata(minTool, maxTool), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_ZANITE.getDefaultStack())
            .setRandomMetadata(minTool, maxTool), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SHOVEL_ZANITE.getDefaultStack())
            .setRandomMetadata(minTool, maxTool), 1);

        // armor
        float minArmorFactor = 1.0f / 2.0f;
        float maxArmorFactor = 1.0f;
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_BOOTS_ZANITE.getDefaultStack())
            .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_BOOTS_ZANITE.getMaxDamage() * minArmorFactor), MathHelper.ceil(AetherItems.ARMOR_BOOTS_ZANITE.getMaxDamage() * maxArmorFactor)), 1);

        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_HELMET_ZANITE.getDefaultStack())
            .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_HELMET_ZANITE.getMaxDamage() * minArmorFactor), MathHelper.ceil(AetherItems.ARMOR_HELMET_ZANITE.getMaxDamage() * maxArmorFactor)), 1);

        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_ZANITE.getDefaultStack())
            .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_LEGGINGS_ZANITE.getMaxDamage() * minArmorFactor), MathHelper.ceil(AetherItems.ARMOR_LEGGINGS_ZANITE.getMaxDamage() * maxArmorFactor)), 1);

        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_ZANITE.getDefaultStack())
            .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_CHESTPLATE_ZANITE.getMaxDamage() * minArmorFactor), MathHelper.ceil(AetherItems.ARMOR_CHESTPLATE_ZANITE.getMaxDamage() * maxArmorFactor)), 1);

        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_ZANITE.getDefaultStack())
            .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_GLOVES_ZANITE.getMaxDamage() * minArmorFactor), MathHelper.ceil(AetherItems.ARMOR_CHESTPLATE_ZANITE.getMaxDamage() * maxArmorFactor)), 1);

        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_ZANITE.getDefaultStack())
            .setRandomMetadata(MathHelper.ceil(AetherItems.ARMOR_TALISMAN_ZANITE.getMaxDamage() * minArmorFactor), MathHelper.ceil(AetherItems.ARMOR_TALISMAN_ZANITE.getMaxDamage() * maxArmorFactor)), 1);

        // rare tool & armor
        GADGET.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SHOOTER.getDefaultStack()), 20);

        // rare
        GADGET.addEntry(new WeightedRandomLootObject(null), 4);
        GADGET.addEntry(new WeightedRandomLootObject(AetherItems.EGG_MOA_BLUE.getDefaultStack()), 3);
        GADGET.addEntry(new WeightedRandomLootObject(AetherItems.MEDAL_VICTORY.getDefaultStack()), 1);
    }

    public static final WeightedRandomBag<WeightedRandomLootObject> TREASURE = new WeightedRandomBag<>();

    static {
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_HOLY.getDefaultStack()), 200.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_STAFF_CLOUD.getDefaultStack()), 200.0);

        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_FEATHER_GOLD.getDefaultStack()), 200.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_BUBBLE.getDefaultStack()), 10);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CAPE_AGILITY.getDefaultStack()), 10);

        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_HELMET_NEPTUNE.getDefaultStack()), 200.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_BOOTS_NEPTUNE.getDefaultStack()), 200.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_NEPTUNE.getDefaultStack()), 200.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_NEPTUNE.getDefaultStack()), 200.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_NEPTUNE.getDefaultStack()), 200.0);

        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_AXE_VALKYRIE.getDefaultStack()), 200.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_VALKYRIE.getDefaultStack()), 200.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SHOVEL_VALKYRIE.getDefaultStack()), 200.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_PICKAXE_VALKYRIE.getDefaultStack()), 200.0);

        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.RECORD_DAWN.getDefaultStack()), 10.0);

    }

    public WorldFeatureAetherSilverDungeon(int direction) {
        this.direction = Direction.horizontalDirections[direction & 3]; // to prevent overflow
    }

    public WorldFeatureAetherSilverDungeon(Random random) {
        this(random.nextInt(4));
    }

    public void placeComponent(WorldFeatureComponent component) {
        for (WorldFeatureBlock block : component.getBlockList()) {
            block.rotateYAroundPivot(dungeonAnchor, direction);
            block.place(world);
        }
    }

    @Override
    public boolean canPlace(World world, int x, int y, int z) {
        if (y + 35 >= world.getHeightBlocks()) {
            return false;
        }

        WorldFeaturePoint theDungeonAnchor = wfp(x, y, z);

        WorldFeatureComponent clear = drawVolume(0, 0, Direction.SOUTH, 55, Direction.UP, 30, Direction.WEST, 30, x, y, z, true);
        for (WorldFeaturePoint point : clear.getBlockList()) {

            point.rotateYAroundPivot(theDungeonAnchor, this.direction);
            Material blockMaterial = world.getBlockMaterial(point.getX(), point.getY(), point.getZ());
            BlockLogicCloudBase blockLogic = world.getBlockLogic(point.getX(), point.getY(), point.getZ(), BlockLogicCloudBase.class);

            if (blockMaterial != null && blockMaterial != Material.air && (blockMaterial != Material.cloth || blockLogic == null)) {
                AetherMod.LOGGER.info("Could not place a silver dungeon at {},{},{}, with blockMaterial {}", x, y, z, blockMaterial);
                return false;
            }
        }
        return true;
    }

    @Override
    public DungeonLogicSilverDungeon register(World world, long seed, int x, int y, int z) {
        DungeonLogicSilverDungeon theLogic = super.register(world, seed, x, y, z);
        theLogic.direction = direction;
        return theLogic;
    }

    @Override
    public boolean generate(DungeonLogicSilverDungeon logic, World world, long seed, int x, int y, int z) {
        this.world = world;
        this.random = new Random(logic.seed);
        this.logic = logic;
        this.logic.setGenerated(true);
        this.dungeonAnchor = wfp(x, y, z);
        this.silverMaze = new WorldFeatureSilverMaze();
        this.bossPosition = wfp(x - 15, y + 4, z + 42).rotateYAroundPivot(dungeonAnchor, direction);

        this.createBaseStructure(x, y, z);
        this.createInnerDecorations(x, y, z);
        this.createBossAndTreasure(x, y, z);
        this.createOuterDecorations(x, y, z);
        return true;
    }

    public static List<ItemStack> generateLoot(Random random) {
        List<ItemStack> loot = new ArrayList<>();
        //min 8 max 10
        int count = random.nextInt(3) + 8;
        for (int i = 0; i < count; i++) loot.add(JUNK.getRandom(random).getItemStack());
        // min 2 max 5
        count = random.nextInt(4) + 2;
        for (int i = 0; i < count; i++) loot.add(AMMO.getRandom(random).getItemStack());
        // min 2 max 4
        count = random.nextInt(3) + 2;
        for (int i = 0; i < count; i++) loot.add(FOOD.getRandom(random).getItemStack());
        // min 1 max 2
        count = AetherMathHelper.invertedExponentialCapped(random, 0.5F, 2) + 1;
        for (int i = 0; i < count; i++) loot.add(ARMOR.getRandom(random).getItemStack());
        // min 0 max 2
        count = AetherMathHelper.invertedExponentialCapped(random, 0.5F, 2);
        for (int i = 0; i < count; i++) loot.add(GADGET.getRandom(random).getItemStack());
        return loot;
    }

    private List<WorldFeaturePoint> getCloudPoints(int x, int y, int z) {
        List<WorldFeaturePoint> cloud = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            cloud.add(new WorldFeaturePoint(x + 5 - random.nextInt(40), y - 2 - random.nextInt(5), z - 5 + random.nextInt(65)));
        }
        return cloud;
    }

    private void createBaseStructure(int x, int y, int z) {
        // clear the volume of the structure of blocks
        WorldFeatureComponent clear = drawVolume(0, 0, Direction.SOUTH, 55, Direction.UP, 30, Direction.WEST, 30, x, y, z, true);
        // create clouds
        this.placeComponent(clear);
        List<WorldFeaturePoint> cloudPoints = getCloudPoints(x, y, z);
        for (WorldFeaturePoint cloudPoint : cloudPoints) {
            cloudPoint.rotateYAroundPivot(this.dungeonAnchor, this.direction);
            new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_WHITE.id(), (6 + random.nextInt(10))).place(world, random, cloudPoint.getX(), cloudPoint.getY(), cloudPoint.getZ());
        }

        // holystone base
        WorldFeatureComponent base = drawVolume(this.random, HOLYSTONE, Direction.SOUTH, 55, Direction.DOWN, 5, Direction.WEST, 30, x, y, z, false);
        int ix = base.getTail().getX();
        int iz = base.getTail().getZ();
        this.placeComponent(base);

        // generate 3x3x3 grid of rooms
        this.silverMaze.createMaze(this.random, x, y, z);
        this.placeComponent(this.silverMaze.getRooms());
        this.placeComponent(this.silverMaze.getChests());

        Set<WorldFeatureBlock> chestSet = new HashSet<>(this.silverMaze.getChests().getBlockList());
        for (WorldFeatureBlock chest : chestSet) {
            populateChest(this.world, this.random, chest, WorldFeatureAetherSilverDungeon::generateLoot);
            if (this.world.rand.nextInt(256) == 0 && chest.getBlockId() == AetherBlocks.CHEST_MIMIC_SKYROOT.id()) {
                placeWallace(this.world, chest.getX(), chest.getY(), chest.getZ());
            }
        }

        // Outer walls of dungeon itself
        this.placeComponent(drawShell(this.random, ANGELIC, Direction.SOUTH, 22, Direction.UP, 16, Direction.WEST, 22, x - 4, y, z + 4, false));
        this.placeComponent(drawShell(this.random, ANGELIC, Direction.NORTH, 26, Direction.UP, 16, Direction.EAST, 22, ix + 4, y, iz - 5, false));


        /// Throne room
        this.placeComponent(drawPlane(this.random, ANGELIC, Direction.WEST, 22, Direction.SOUTH, 25, x - 4, y + 1, z + 26, false));
    }

    private void createBossAndTreasure(int x, int y, int z) {
        Pair<WorldFeaturePoint, WorldFeaturePoint> clearArea = new Pair<>(
            new WorldFeaturePoint(x + 2, y - 1, z - 3),
            new WorldFeaturePoint(x - 31, y + 23, z + 56)
        );
        clearArea.getFirst().rotateYAroundPivot(this.dungeonAnchor, this.direction);
        clearArea.getSecond().rotateYAroundPivot(this.dungeonAnchor, this.direction);

        // Entrance hole into boss room
        int entranceDoorMeta = BlockLogicRotatable.setDirection(0, this.direction);
        WorldFeatureComponent entranceDoor = drawPlane(0, 0, Direction.UP, 3, Direction.WEST, 2, x - 21, y + 1, z + 25, true);
        this.placeComponent(entranceDoor);
        for (WorldFeatureBlock blocks : entranceDoor.getBlockList()) {
            blocks.setBlockId(AetherBlocks.DOOR_DUNGEON_SILVER.id());
            blocks.setMetadata(entranceDoorMeta);
        }
        this.logic.setEntranceDoor(entranceDoor.getBlockList());
        this.logic.setClearArea(clearArea);

        // Place boss, chest and door
        MobBossValkyrie boss = new MobBossValkyrie(this.world);
        boss.moveTo(this.bossPosition.getX(), this.bossPosition.getY(), this.bossPosition.getZ(), 0f, 0f);
        boss.setReturnPoint(new WorldFeaturePoint(this.bossPosition.getX(), this.bossPosition.getY(), this.bossPosition.getZ()));
        boss.setDungeonID(this.logic.id);
        boss.setTrophy(AetherItems.KEY_SILVER.getDefaultStack());

        new WorldFeatureAetherSilverChest().place(this.world, this.random, this.bossPosition.getX(), y, this.bossPosition.getZ());
        List<WorldFeaturePoint> treasureDoor = new ArrayList<>();
        treasureDoor.add(new WorldFeaturePoint(x - 14, y + 2, z + 42));
        treasureDoor.add(new WorldFeaturePoint(x - 14, y + 2, z + 43));
        treasureDoor.add(new WorldFeaturePoint(x - 15, y + 2, z + 42));
        treasureDoor.add(new WorldFeaturePoint(x - 15, y + 2, z + 43));
        treasureDoor.add(new WorldFeaturePoint(x - 14, y + 2, z + 41)); //non trapdoor
        treasureDoor.add(new WorldFeaturePoint(x - 15, y + 2, z + 41));

        treasureDoor.add(new WorldFeaturePoint(x - 15, y + 1, z + 41));
        treasureDoor.add(new WorldFeaturePoint(x - 14, y + 1, z + 41));

        treasureDoor.forEach(p -> p.rotateYAroundPivot(wfp(x, y, z), direction));
        this.logic.setTreasureDoor(treasureDoor);
        this.world.entityJoinedWorld(boss);
    }

    private void createInnerDecorations(int x, int y, int z) {
        // Create semicircle
        this.createSemiCircle(x, y, z);

        // Fountains
        this.createFountain(x - 5, y + 2, z + 33, Direction.WEST);
        this.createFountain(x - 24, y + 2, z + 33, Direction.EAST);

        // Tree pods
        this.createTreePods(x, y, z);

        // Throne
        this.placeComponent(drawPlane(this.random, ANGELIC, Direction.WEST, 8, Direction.SOUTH, 6, x - 11, y + 2, z + 44, false));
        this.placeComponent(drawShell(this.random, ANGELIC, Direction.WEST, 4, Direction.NORTH, 4, Direction.DOWN, 4, x - 13, y + 2, z + 44, false));

        // Chest hole
        this.placeComponent(drawVolume(0, 0, Direction.WEST, 2, Direction.NORTH, 2, Direction.DOWN, 2, x - 14, y + 1, z + 43, false));

        // Torches
        WorldFeatureComponent torches = new WorldFeatureComponent();
        torches.add(wfb(x - 11, y + 3, z + 44, AetherBlocks.TORCH_AMBROSIUM.id(), 0, false));
        torches.add(wfb(x - 11, y + 3, z + 49, AetherBlocks.TORCH_AMBROSIUM.id(), 0, false));
        torches.add(wfb(x - 18, y + 3, z + 49, AetherBlocks.TORCH_AMBROSIUM.id(), 0, false));
        torches.add(wfb(x - 18, y + 3, z + 44, AetherBlocks.TORCH_AMBROSIUM.id(), 0, false));
        this.placeComponent(torches);

        // Throne cushion
        this.placeComponent(drawPlane(this.random, ANGELIC, Direction.WEST, 4, Direction.UP, 6, x - 13, y + 3, z + 49, false));
        this.placeComponent(drawVolume(this.random, ANGELIC, Direction.WEST, 4, Direction.NORTH, 4, Direction.UP, 2, x - 13, y + 3, z + 49, false));
        this.placeComponent(drawPlane(Blocks.WOOL.id(), 11, Direction.WEST, 2, Direction.NORTH, 2, x - 14, y + 4, z + 48, false));
        this.placeComponent(drawLine(this.random, ANGELIC, Direction.NORTH, 3, x - 13, y + 5, z + 48, false));
        this.placeComponent(drawLine(this.random, ANGELIC, Direction.NORTH, 3, x - 16, y + 5, z + 48, false));

        // Ceiling lights
        this.createLight(x - 10, y + 14, z + 28);
        this.createLight(x - 19, y + 14, z + 28);
        this.createLight(x - 10, y + 14, z + 43);
        this.createLight(x - 19, y + 14, z + 43);

        // Staircase
        this.placeComponent(drawPlane(0, 0, Direction.WEST, 2, Direction.DOWN, 4, x - 14, y, z, false));
        this.placeComponent(drawPlane(0, 0, Direction.WEST, 2, Direction.DOWN, 3, x - 14, y, z + 1, false));
        this.placeComponent(drawPlane(0, 0, Direction.WEST, 2, Direction.DOWN, 2, x - 14, y, z + 2, false));
        this.placeComponent(drawPlane(0, 0, Direction.WEST, 2, Direction.DOWN, 1, x - 14, y, z + 3, false));
    }

    private WorldFeatureComponent createPillar(int x, int y, int z) {
        WorldFeatureComponent pillar = new WorldFeatureComponent();
        pillar.add(drawPlane(this.random, ANGELIC, Direction.SOUTH, 3, Direction.WEST, 3, x, y, z, false));
        pillar.add(drawPlane(this.random, ANGELIC, Direction.SOUTH, 3, Direction.WEST, 3, x, y + 14, z, false));
        pillar.add(drawLine(AetherBlocks.PILLAR.id(), 0, Direction.UP, 13, x + Direction.WEST.getOffsetX(), y, z + Direction.SOUTH.getOffsetZ(), false));
        pillar.add(wfb(x + Direction.WEST.getOffsetX(), y + 13, z + Direction.SOUTH.getOffsetZ(), AetherBlocks.PILLAR_CAPSTONE.id(), 0, false));
        return pillar;
    }

    private void createOuterDecorations(int x, int y, int z) {
        WorldFeatureComponent roof = new WorldFeatureComponent();
        // Roof
        for (int i = 0; i < 7; i++) {
            roof.add(drawPlane(this.random, ANGELIC, Direction.SOUTH, 57, Direction.WEST, 32 - 4 * i, x + 1 - 2 * i, y + 16 + i, z - 1, false));
        }

        WorldFeatureComponent pillars = new WorldFeatureComponent();
        // Pillars
        for (int i = 0; i < 14; i++) {
            pillars.add(this.createPillar(x, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4));
            pillars.add(this.createPillar(x - 27, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4));
            if (i == 0 || i == 13) {
                pillars.add(this.createPillar(x - 4, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4));
                pillars.add(this.createPillar(x - 8, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4));

                pillars.add(this.createPillar(x - 23, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4));
                pillars.add(this.createPillar(x - 19, y + 1, z + Direction.SOUTH.getOffsetZ() * i * 4));
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
            pod.add(drawPlane(this.random, ANGELIC, Direction.WEST, 3, Direction.SOUTH, 3, bx, y + 2, bz, false));
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
        for (WorldFeatureBlock tree : trees.getBlockList()) {
            new WorldFeatureAetherTreeGoldenOak().place(this.world, this.random, tree.getX(), tree.getY(), tree.getZ());
        }
    }

    private void createSemiCircle(int x, int y, int z) {
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

    private void createFountain(int x, int y, int z, Direction directionEW) {
        int[] walls = new int[]{2, 3, 4, 4, 4, 4, 3, 2};
        boolean[] torches = new boolean[]{false, false, true, false, false, true, false, false};
        int[] water = new int[]{0, 2, 3, 3, 3, 3, 2, 0};
        WorldFeatureComponent fountain = new WorldFeatureComponent();

        for (int i = 0; i < walls.length; i++) {
            WorldFeatureComponent end = drawLine(this.random, ANGELIC, directionEW, walls[i], x, y, z + i, false);
            fountain.add(end);
            if (torches[i]) {
                fountain.add(wfb(end.getTail().getX(), end.getTail().getY() + 1, end.getTail().getZ(), AetherBlocks.TORCH_AMBROSIUM.id(), 0, true));
            }
            if (water[i] == 0) {
                continue;
            }
            fountain.add(drawLine(Blocks.FLUID_WATER_STILL.id(), 0, directionEW, water[i], x, y, z + i, false));
        }
        this.placeComponent(fountain);
    }

    private void createLight(int x, int y, int z) {
        WorldFeatureComponent lights = new WorldFeatureComponent();
        lights.add(wfb(x, y, z, AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, false));
        lights.add(wfb(x, y - 1, z, AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, false));
        lights.add(wfb(x, y - 2, z, AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 0, false));
        lights.add(wfb(x, y - 3, z, Blocks.GLOWSTONE.id(), 0, false));
        lights.add(wfb(x, y - 4, z, Blocks.GLOWSTONE.id(), 0, false));
        lights.add(wfb(x - 1, y - 4, z, Blocks.GLOWSTONE.id(), 0, false));
        lights.add(wfb(x + 1, y - 4, z, Blocks.GLOWSTONE.id(), 0, false));
        lights.add(wfb(x, y - 4, z - 1, Blocks.GLOWSTONE.id(), 0, false));
        lights.add(wfb(x, y - 4, z + 1, Blocks.GLOWSTONE.id(), 0, false));
        lights.add(wfb(x, y - 5, z, Blocks.GLOWSTONE.id(), 0, false));
        this.placeComponent(lights);
    }

    @Override
    protected Class<DungeonLogicSilverDungeon> getAppliedClass() {
        return DungeonLogicSilverDungeon.class;
    }
}
