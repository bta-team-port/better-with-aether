package teamport.aether.world.feature.dungeon.gold;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureFlowers;
import net.minecraft.core.world.generate.feature.WorldFeatureTallGrass;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.helper.AetherMathHelper;
import teamport.aether.helper.Pair;
import teamport.aether.helper.unboxed.IntPair;
import teamport.aether.item.AetherItems;
import teamport.aether.world.feature.chest.WorldFeatureAetherGoldChest;
import teamport.aether.world.feature.terrain.WorldFeatureAetherTreeGoldenOak;
import teamport.aether.world.feature.util.BlockPallet;
import teamport.aether.world.feature.util.WorldFeatureBlock;
import teamport.aether.world.feature.util.WorldFeatureComponent;
import teamport.aether.world.feature.util.WorldFeaturePoint;
import teamport.aether.world.feature.util.map.WorldFeatureMap;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static teamport.aether.world.feature.util.WorldFeatureBlock.wfb;
import static teamport.aether.world.feature.util.WorldFeatureComponent.*;
import static teamport.aether.world.feature.util.WorldFeaturePoint.wfp;

public class WorldFeatureAetherGoldDungeon extends WorldFeatureMap<DungeonLogicGoldDungeon> {
    private static final List<Integer> STONES = Arrays.asList(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), AetherBlocks.COBBLE_HOLYSTONE.id());
    private static final BlockPallet HELLFIRE = new BlockPallet();
    private static final BlockPallet HOLYSTONE = new BlockPallet();
    private static final int RADIUS = 16;

    private final Direction direction;
    private WorldFeaturePoint dungeonAnchor;
    private WorldFeaturePoint bossPosition;

    private DungeonLogicGoldDungeon logic;
    private World world;
    private Random random;

    private List<WorldFeaturePoint> heightMap;

    static {
        HELLFIRE.addEntry(AetherBlocks.CARVED_HELLFIRE_LOCKED.id(), 0, 90);
        HELLFIRE.addEntry(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED.id(), 0, 10);
        HOLYSTONE.addEntry(AetherBlocks.COBBLE_HOLYSTONE.id(), 0, 90);
        HOLYSTONE.addEntry(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), 0, 10);
    }

    private static final WeightedRandomBag<Supplier<? extends WorldFeature>> WORLD_FEATURE = new WeightedRandomBag<>();

    static {
        WORLD_FEATURE.addEntry(null, 512);
        WORLD_FEATURE.addEntry(() -> new WorldFeatureTallGrass(AetherBlocks.TALLGRASS_AETHER.id()), 16);
        WORLD_FEATURE.addEntry(() -> new WorldFeatureFlowers(AetherBlocks.FLOWER_WHITE.id(), 64, true), 4);
        WORLD_FEATURE.addEntry(() -> new WorldFeatureFlowers(AetherBlocks.FLOWER_PURPLE.id(), 64, true), 4);
        WORLD_FEATURE.addEntry(WorldFeatureAetherTreeGoldenOak::new, 8);
    }

    private static final WeightedRandomBag<WeightedRandomLootObject> JUNK = new WeightedRandomBag<>();
    private static final WeightedRandomBag<WeightedRandomLootObject> AMMO = new WeightedRandomBag<>();
    private static final WeightedRandomBag<WeightedRandomLootObject> ARMOR = new WeightedRandomBag<>();

    static {
        // junk     8-10
        JUNK.addEntry(new WeightedRandomLootObject(null), 8);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.BLOCK_AMBER.getDefaultStack(), 1, 8), 4);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.BLOCK_AMBROSIUM.getDefaultStack(), 1, 6), 2);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.BLOCK_ZANITE.getDefaultStack(), 1, 4), 2);
        JUNK.addEntry(new WeightedRandomLootObject(AetherBlocks.BLOCK_GRAVITITE.getDefaultStack(), 1, 2), 2);
        JUNK.addEntry(new WeightedRandomLootObject(AetherItems.EGG_MOA_BLACK.getDefaultStack()), 1);

        // ammo     2-5
        AMMO.addEntry(new WeightedRandomLootObject(null), 8);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_GOLDEN.getDefaultStack(), 2, 6), 4);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_POISON.getDefaultStack(), 2, 6), 2);
        AMMO.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_ENCHANTED.getDefaultStack(), 2, 6), 1);

        // armor
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_AXE_GRAVITITE.getDefaultStack(), 1), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_PICKAXE_GRAVITITE.getDefaultStack(), 1), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SHOVEL_GRAVITITE.getDefaultStack(), 1), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_ZANITE.getDefaultStack(), 1), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_GRAVITITE.getDefaultStack(), 1), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_BOOTS_GRAVITITE.getDefaultStack(), 1), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_HELMET_GRAVITITE.getDefaultStack(), 1), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_GRAVITITE.getDefaultStack(), 1), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_GRAVITITE.getDefaultStack(), 1), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_GRAVITITE.getDefaultStack(), 1), 1);
        ARMOR.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_GRAVITITE.getDefaultStack(), 1), 1);
    }

    public static final WeightedRandomBag<WeightedRandomLootObject> TREASURE = new WeightedRandomBag<>();

    static {
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_VAMPIRE.getDefaultStack(), 1), 100.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_FLAME.getDefaultStack(), 1), 100.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_PIG.getDefaultStack(), 1), 100.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_BOW_PHOENIX.getDefaultStack()), 10);

        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_PHOENIX.getDefaultStack(), 1), 100.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_BOOTS_PHOENIX.getDefaultStack(), 1), 100.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_HELMET_PHOENIX.getDefaultStack(), 1), 100.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_PHOENIX.getDefaultStack(), 1), 100.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_PHOENIX.getDefaultStack(), 1), 100.0);

        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.LIFESHARD.getDefaultStack(), 1, 2), 50.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_DUNGEON_COMPASS.getDefaultStack()), 25.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.RECORD_NETHER.getDefaultStack()), 10.0);

        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CAPE_INVISIBILITY.getDefaultStack()), 100.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_SHIELD_REPULSION.getDefaultStack()), 100.0);
    }

    public WorldFeatureAetherGoldDungeon(int dir) {
        this.direction = Direction.horizontalDirections[dir & 3];
    }

    public WorldFeatureAetherGoldDungeon(Random random) {
        this(random.nextInt(4));
    }

    public void placeComponent(WorldFeatureComponent component) {
        for (WorldFeatureBlock block : component.getBlockList()) {
            block.rotateYAroundPivot(dungeonAnchor, direction);
            block.place(world);
        }
    }

    @Override
    protected Class<DungeonLogicGoldDungeon> getAppliedClass() {
        return DungeonLogicGoldDungeon.class;
    }

    @Override
    public DungeonLogicGoldDungeon register(World world, long seed, int x, int y, int z) {
        DungeonLogicGoldDungeon theLogic = super.register(world, seed, x, y, z);
        theLogic.direction = direction;
        return theLogic;
    }

    @Override
    public boolean canPlace(World world, int x, int y, int z) {
        final int checkDistance = 30;

        int[][] directions = {
            {1, 0, 0},   // +x
            {-1, 0, 0},  // -x
            {0, 1, 0},   // +y
            {0, -1, 0},  // -y
            {0, 0, 1},   // +z
            {0, 0, -1}   // -z
        };

        for (int[] dir : directions) {
            for (int i = 1; i <= checkDistance; i++) {
                int checkX = x + i * dir[0];
                int checkY = y + i * dir[1];
                int checkZ = z + i * dir[2];
                Material blockMaterial = world.getBlockMaterial(checkX, checkY, checkZ);
                if (blockMaterial != Material.air) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean generate(DungeonLogicGoldDungeon logic, World world, long seed, int x, int y, int z) {
        this.world = world;
        this.random = new Random(logic.seed);
        this.logic = logic;
        this.dungeonAnchor = new WorldFeaturePoint(x, y, z);
        this.bossPosition = new WorldFeaturePoint(x, y + RADIUS / 2 + 2, z);
        this.heightMap = new ArrayList<>();

        createMainSphere(x, y, z);
        createOuterSpheres(x, y, z);
        createMainRoom(x, y, z);
        createBossAndTreasure(x, y, z);
        createHeightMap(x, y, z);
        createGrassOnTopLevel();
        createDecorations();

        return true;
    }


    public static List<ItemStack> generateLoot(Random random) {
        List<ItemStack> loot = new ArrayList<>();
        //min 8 max 10
        int count = random.nextInt(3) + 8;
        for (int i = 0; i < count; i++) loot.add(JUNK.getRandom(random).getItemStack());
        // min 4 max 10
        count = random.nextInt(7) + 4;
        for (int i = 0; i < count; i++) loot.add(AMMO.getRandom(random).getItemStack());
        // min 1 max 2
        count = AetherMathHelper.invertedExponentialCapped(random, 0.5F, 2) + 1;
        for (int i = 0; i < count; i++) loot.add(ARMOR.getRandom(random).getItemStack());
        return loot;
    }

    private void createMainSphere(int x, int y, int z) {
        // place main spheroid
        drawSpheroid(random, HOLYSTONE, x, y + 15, z, RADIUS, (int) (RADIUS * 1.12), RADIUS, false).place(world);
        wfb(x, (int) Math.floor(15 * 1.12 * 2 + y) - 1, z, 0, 0, false).place(world);
        wfb(x, (int) Math.floor(15 * 1.12 * 2 + y) - 2, z, AetherBlocks.GRASS_AETHER.id(), 0, false).place(world);
    }

    // TODO these sphere do not rotate
    @SuppressWarnings("java:S5413")
    private void createOuterSpheres(int x, int y, int z) {
        // place the outer spheres
        List<Integer> angles = new ArrayList<>();
        for (int angle = 0; angle < 10; angle++) {
            angles.add(angle * (360 / 10));
        }

        int sphereCount = 6 + random.nextInt(4);
        for (int index = 0; index < sphereCount; index++) {
            int angleIndex = random.nextInt(angles.size());
            int angle = angles.remove(angleIndex);

            double newX = x + RADIUS * Math.cos(Math.toRadians(angle));
            double newZ = z + RADIUS * Math.sin(Math.toRadians(angle));
            double radMod = (double) (4 + random.nextInt(5)) / 10;

            drawSphere(random, HOLYSTONE, (int) newX, (int) (y + (RADIUS * 0.8F)), (int) newZ, (int) (RADIUS * radMod), false).place(world);
        }
        double radMod2 = 0.5F;
        WorldFeaturePoint cover = new WorldFeaturePoint(x, (int) (y + (RADIUS * 0.8F)), z + RADIUS);
        cover.rotateYAroundPivot(dungeonAnchor, direction);

        drawSphere(random, HOLYSTONE, cover.getX(), cover.getY(), cover.getZ(), (int) (RADIUS * radMod2), false).place(world);
    }

    private void createMainRoom(int x, int y, int z) {
        // main room
        int xRoomLength = 19;
        int yRoomHeight = 8;
        int zRoomLength = 19;
        WorldFeatureComponent main = new WorldFeatureComponent();

        Pair<WorldFeaturePoint, WorldFeaturePoint> clearArea = new Pair<>(
            new WorldFeaturePoint(x + 1 + RADIUS / 2 + 8, y + RADIUS / 2, z + 1 + RADIUS / 2),
            new WorldFeaturePoint(x + 1 + RADIUS / 2 - xRoomLength, y + RADIUS / 2 + yRoomHeight, z + 1 + RADIUS / 2 - zRoomLength)
        );

        clearArea.getFirst().rotateYAroundPivot(dungeonAnchor, direction);
        clearArea.getSecond().rotateYAroundPivot(dungeonAnchor, direction);
        logic.setClearArea(clearArea);

        main.add(drawHollowShell(
            random, HELLFIRE,
            Direction.WEST, xRoomLength,
            Direction.NORTH, zRoomLength,
            Direction.UP, yRoomHeight,
            x + 1 + RADIUS / 2, y + RADIUS / 2, z + 1 + RADIUS / 2, false
        ));
        main.add(drawSquareCylinder(
            random, HELLFIRE,
            Direction.WEST, xRoomLength - 2,
            Direction.NORTH, zRoomLength - 2,
            Direction.UP, 1,
            x + RADIUS / 2, y + 1 + RADIUS / 2, z + RADIUS / 2, false
        ));
        main.add(drawSquareCylinder(
            random, HELLFIRE,
            Direction.WEST, xRoomLength - 2,
            Direction.NORTH, zRoomLength - 2,
            Direction.UP, 1,
            x + RADIUS / 2, y + yRoomHeight - 2 + RADIUS / 2, z + RADIUS / 2, false)
        );

        main.add(
            drawVolume(0, 0,
                Direction.NORTH, RADIUS * 2,
                Direction.WEST, 3,
                Direction.UP, 3,
                x + 1, y + 2 + RADIUS / 2, z - RADIUS / 2 - 1, false
            )
        );

        world.setBlock(x, y, z, AetherBlocks.BLOCK_GRAVITITE.id());

        WorldFeatureComponent entranceDoor = new WorldFeatureComponent();
//        Direction doorDir = direction.getHorizontalIndex() % 2 == 0? direction : direction.getOpposite();
        int entranceDoorMeta = BlockLogicRotatable.setDirection(0, direction);

        iterate3d(
            wfp(x + 2, y + 2 + RADIUS / 2, z - RADIUS / 2),
            wfp(x - 1, y + 5 + RADIUS / 2, z - 1 - RADIUS / 2),
            w -> entranceDoor.add(wfb(w, AetherBlocks.DOOR_DUNGEON_GOLD.id(), entranceDoorMeta, true))
        );

        entranceDoor.rotateYAroundPivot(dungeonAnchor, direction);
        logic.setEntranceDoor(entranceDoor.getBlockList());

        this.placeComponent(main);
    }

    private void createBossAndTreasure(int x, int y, int z) {
        // chest room
        WorldFeatureComponent treasureRoom = drawHollowShell(
            random, HELLFIRE,
            Direction.SOUTH, 7,
            Direction.WEST, 7,
            Direction.UP, 5,
            x + 3, y + 1 + RADIUS / 2, z + RADIUS / 2 + 1, false
        );
        this.placeComponent(treasureRoom);

        // Place boss, chest and door

        MobBossSunspirit boss = new MobBossSunspirit(world);
        boss.moveTo(bossPosition.getX(), bossPosition.getY(), bossPosition.getZ(), 0f, 0f);
        boss.setReturnPoint(new WorldFeaturePoint(bossPosition.getX(), bossPosition.getY(), bossPosition.getZ()));
        boss.setDungeonID(logic.id);
        boss.setTrophy(AetherItems.KEY_GOLD.getDefaultStack());
        world.entityJoinedWorld(boss);

        WorldFeaturePoint chestPoint = new WorldFeaturePoint(x, y + 2 + RADIUS / 2, z - 4 + RADIUS);
        chestPoint.rotateYAroundPivot(dungeonAnchor, direction);
        new WorldFeatureAetherGoldChest(direction).place(world, random, chestPoint.getX(), chestPoint.getY(), chestPoint.getZ());

        WorldFeaturePoint anchor = wfp(x, y, z);
        List<WorldFeaturePoint> treasureDoor = new ArrayList<>();
        treasureDoor.add(new WorldFeaturePoint(x - 1, y + 2 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(x - 1, y + 3 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(x - 1, y + 4 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(x, y + 2 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(x, y + 3 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(x, y + 4 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(x + 1, y + 2 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(x + 1, y + 3 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(x + 1, y + 4 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.forEach(p -> p.rotateYAroundPivot(anchor, direction));
        logic.setTreasureDoor(treasureDoor);

        Pair<WorldFeaturePoint, WorldFeaturePoint> clearArea = new Pair<>(
            new WorldFeaturePoint(x - RADIUS - 8, y - 5, z - RADIUS - 8),
            new WorldFeaturePoint(x + RADIUS + 8, y + RADIUS + 8, z + RADIUS + 8)
        );
        clearArea.getFirst().rotateYAroundPivot(dungeonAnchor, direction);
        clearArea.getSecond().rotateYAroundPivot(dungeonAnchor, direction);
        logic.setClearArea(clearArea);
    }

    public void createHeightMap(int x, int y, int z) {
        int diameter = RADIUS << 1;
        Set<Integer> hell = HELLFIRE.getPallet().getEntries().stream().map(IntPair::getFirst).collect(Collectors.toSet());
        for (int ix = -diameter; ix < diameter; ix++) {
            for (int iz = -diameter; iz < diameter; iz++) {
                if (diameter * diameter >= ix * ix + iz * iz) {
                    for (int iy = y + diameter; iy > y + RADIUS - 4; iy--) {
                        int id = world.getBlockId(x + ix, iy - 1, z + iz);
                        if (id != 0 && (STONES.contains(id)) && !hell.contains(id)) {
                            this.heightMap.add(new WorldFeaturePoint(ix + x, iy, iz + z));
                            break;
                        }
                    }
                }

            }
        }
    }

    private void createGrassOnTopLevel() {
        for (WorldFeaturePoint p : heightMap) {
            int x = p.getX();
            int y = p.getY();
            int z = p.getZ();
            WorldFeatureComponent dirt = new WorldFeatureComponent();
            if (STONES.contains(world.getBlockId(x, y - 1, z))) {
                dirt.add(wfb(x, y - 1, z, AetherBlocks.GRASS_AETHER.id()));
            }
            if (STONES.contains(world.getBlockId(x, y - 2, z))) {
                dirt.add(wfb(x, y - 2, z, AetherBlocks.DIRT_AETHER.id()));
            }
            if (STONES.contains(world.getBlockId(x, y - 3, z))) {
                dirt.add(wfb(x, y - 3, z, AetherBlocks.DIRT_AETHER.id()));
            }
            if (STONES.contains(world.getBlockId(x, y - 4, z)) && world.rand.nextInt(10) > 3) {
                dirt.add(wfb(x, y - 4, z, AetherBlocks.DIRT_AETHER.id()));
            }
            dirt.place(world);
        }
    }


    private void createDecorations() {
        for (WorldFeaturePoint point : heightMap) {
            Supplier<? extends WorldFeature> feature = WORLD_FEATURE.getRandom(random);
            if (feature == null) continue;
            feature.get().place(world, random, point.getX(), point.getY(), point.getZ());
        }
    }
}
