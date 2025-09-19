package teamport.aether.world.generate.feature.dungeon;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureFlowers;
import net.minecraft.core.world.generate.feature.WorldFeatureTallGrass;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.helper.AetherMathHelper;
import teamport.aether.helper.Pair;
import teamport.aether.items.AetherItems;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.generate.feature.dungeon.map.DungeonMapEntry;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.WorldFeatureAetherTreeGoldenOak;
import teamport.aether.world.generate.feature.chests.WorldFeatureAetherGoldChest;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.*;
import java.util.stream.Collectors;

import static teamport.aether.world.generate.feature.components.WorldFeatureBlock.wfb;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.iterate3d;
import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfp;

public class WorldFeatureAetherGoldDungeon extends WorldFeature {
    public static final BlockPallet hellfire = new BlockPallet();
    public static final BlockPallet holystone = new BlockPallet();
    public WorldFeatureComponent decorations;
    public WorldFeaturePoint dungeonAnker;
    public WorldFeaturePoint bossPosition;
    public World world;
    public Random random;
    protected DungeonMapEntry dungeon;
    public static final int RADIUS = 16;

    public final float angle;
    public final Direction direction;

    private static final List<Integer> stones = Arrays.asList(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), AetherBlocks.COBBLE_HOLYSTONE.id());
    private List<WorldFeaturePoint> heightMap;

    static {
        hellfire.addEntry(AetherBlocks.CARVED_HELLFIRE_LOCKED.id(), 0, 90);
        hellfire.addEntry(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED.id(), 0, 10);
        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE.id(), 0, 90);
        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), 0, 10);
    }

    private static final Pair<Integer, WorldFeature>[] veggies = new Pair[]{
            new Pair<>(128, new WorldFeatureAetherTreeGoldenOak(AetherBlocks.LEAVES_OAK_GOLDEN.id(), AetherBlocks.LOG_OAK_GOLDEN.id())),
            new Pair<>(32, new WorldFeatureTallGrass(AetherBlocks.TALLGRASS_AETHER.id())),
            new Pair<>(84, new WorldFeatureFlowers(AetherBlocks.FLOWER_WHITE.id(), 64, true))
    };
    public static final WeightedRandomBag<WeightedRandomLootObject> JUNK = new WeightedRandomBag<>();
    public static final WeightedRandomBag<WeightedRandomLootObject> AMMO = new WeightedRandomBag<>();
    public static final WeightedRandomBag<WeightedRandomLootObject> ARMOR = new WeightedRandomBag<>();

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

        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CAPE_INVISIBILITY.getDefaultStack()), 200.0);
        TREASURE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_SHIELD_REPULSION.getDefaultStack()), 200.0);
    }

    public WorldFeatureAetherGoldDungeon(int dir) {
        this.angle = 360 - dir * 90;
        this.direction = Direction.horizontalDirections[dir & 3];
    }

    public static WorldFeatureAetherGoldDungeon goldDungeon(Random random) {
        return new WorldFeatureAetherGoldDungeon((random.nextInt(4)));
    }

    public WorldFeaturePoint getPos(int ix, int iy, int iz) {
        return new WorldFeaturePoint(ix, iy, iz);
    }

    public void placeComponent(WorldFeatureComponent component) {
        component.rotateYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
        component.place(world);
    }

    private boolean canPlace(World world, int x, int y, int z) {
        for (int ix = -RADIUS; ix < RADIUS; ix++) {
            for (int iz = -RADIUS; iz < RADIUS; iz++) {
                if (RADIUS * RADIUS >= ix * ix + iz * iz) {
                    if(!world.canBlockSeeTheSky(x + ix,y + RADIUS,z + iz)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean place(World world, Random random, int x, int y, int z) {
        if (!canPlace(world, x, y, z)) return false;
        this.world = world;
        this.random = random;
        this.dungeonAnker = new WorldFeaturePoint(x, y, z);
        this.bossPosition = this.getPos(x, y + RADIUS / 2 + 2, z);
        this.decorations = new WorldFeatureComponent();
        this.dungeon = AetherDimension.dungeonMap.register(DungeonMapEntry.class);
        this.dungeon.setPosition(bossPosition);
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
        drawSpheroid(random, holystone, x, y + 15, z, RADIUS, (int) (RADIUS * 1.12), RADIUS, true).place(world);
        wfb(x, (int) Math.floor(15 * 1.12 * 2 + y) - 1, z, 0, 0, true).place(world);
        wfb(x, (int) Math.floor(15 * 1.12 * 2 + y) - 2, z, AetherBlocks.GRASS_AETHER.id(), 0, true).place(world);
    }

    // TODO these sphere do not rotate
    private void createOuterSpheres(int x, int y, int z) {
        // place the outer spheres
        List<Integer> angles = new ArrayList<>();
        for (int angle = 0; angle < 10; angle++) {
            angles.add(angle * (360 / 10));
        }

        int sphereCount = 6 + random.nextInt(4);
        for (int index = 0; index < sphereCount; index++) {
            int angleIndex = random.nextInt(angles.size());
            int angle = angles.get(angleIndex);
            angles.remove(angleIndex);

            double newX = x + RADIUS * Math.cos(Math.toRadians(angle));
            double newZ = z + RADIUS * Math.sin(Math.toRadians(angle));
            double radMod = (double) (4 + random.nextInt(5)) / 10;

            drawSphere(random, holystone, (int) newX, (int) (y + (RADIUS * 0.8F)), (int) newZ, (int) (RADIUS * radMod), true).place(world);
        }
        double radMod2 = 0.5F;
        WorldFeaturePoint cover = new WorldFeaturePoint(x, (int) (y + (RADIUS * 0.8F)), z + RADIUS);
        cover.rotateYAroundPivot(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, this.angle);

        drawSphere(random, holystone, cover.x, cover.y, cover.z, (int) (RADIUS * radMod2), true).place(world);
    }

    private void createMainRoom(int x, int y, int z) {
        // main room
        int xRoomLength = 19;
        int YRoomHeight = 8;
        int ZRoomLength = 19;
        WorldFeatureComponent main = new WorldFeatureComponent();

        Pair<WorldFeaturePoint, WorldFeaturePoint> clearArea = new Pair<>(
                new WorldFeaturePoint(x + 1 + RADIUS / 2 + 8, y + RADIUS / 2, z + 1 + RADIUS / 2),
                new WorldFeaturePoint(x + 1 + RADIUS / 2 - xRoomLength, y + RADIUS / 2 + YRoomHeight, z + 1 + RADIUS / 2 - ZRoomLength)
        );

        clearArea.first.rotateYAroundPivot(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
        clearArea.second.rotateYAroundPivot(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
        dungeon.setClearArea(clearArea);

        main.add(drawHollowShell(
                random, hellfire,
                Direction.WEST, xRoomLength,
                Direction.NORTH, ZRoomLength,
                Direction.UP, YRoomHeight,
                x + 1 + RADIUS / 2, y + RADIUS / 2, z + 1 + RADIUS / 2, true
        ));
        main.add(drawSquareCylinder(
                random, hellfire,
                Direction.WEST, xRoomLength - 2,
                Direction.NORTH, ZRoomLength - 2,
                Direction.UP, 1,
                x + RADIUS / 2, y + 1 + RADIUS / 2, z + RADIUS / 2, true
        ));
        main.add(drawSquareCylinder(
                random, hellfire,
                Direction.WEST, xRoomLength - 2,
                Direction.NORTH, ZRoomLength - 2,
                Direction.UP, 1,
                x + RADIUS / 2, y + YRoomHeight - 2 + RADIUS / 2, z + RADIUS / 2, true)
        );

        main.add(
            drawVolume(0, 0,
                Direction.NORTH, RADIUS*2,
                Direction.WEST, 3,
                Direction.UP, 3,
                x +1, y +2 +RADIUS/2, z -RADIUS/2 -1, true
            )
        );

        world.setBlockWithNotify(x, y, z,AetherBlocks.BLOCK_GRAVITITE.id());

        WorldFeatureComponent entranceDoor = new WorldFeatureComponent();
        int entranceDoorMeta = BlockLogicRotatable.setDirection(0, direction);

        iterate3d(
            wfp(x +2,y +2 +RADIUS/2, z -RADIUS/2),
            wfp(x -1,y +5 +RADIUS/2, z -1 -RADIUS/2),
            w -> entranceDoor.add(wfb(w, AetherBlocks.DOOR_DUNGEON_GOLD.id(), entranceDoorMeta, true))
        );

        entranceDoor.rotateYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
        dungeon.setEntranceDoor(entranceDoor.blockList);

        this.placeComponent(main);
    }

    private void createBossAndTreasure(int x, int y, int z) {
        // chest room
        this.placeComponent(drawHollowShell(
                random, hellfire,
                Direction.SOUTH, 7,
                Direction.WEST, 7,
                Direction.UP, 5,
                x +3, y +1 +RADIUS/2, z +RADIUS/2 +1, true
        ));
        // Place boss, chest and door

        MobBossSunspirit boss = new MobBossSunspirit(world);
        boss.moveTo(bossPosition.x, bossPosition.y, bossPosition.z, 0f, 0f);
        boss.setReturnPoint(new WorldFeaturePoint(bossPosition.x, bossPosition.y, bossPosition.z));
        boss.setDungeonID(dungeon.getId());
        boss.setTrophy(AetherItems.KEY_GOLD.getDefaultStack());
        world.entityJoinedWorld(boss);

        WorldFeaturePoint chestPoint = new WorldFeaturePoint(x, y + 2 + RADIUS / 2, z - 4 + RADIUS);
        chestPoint.rotateYAroundPivot(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
        WorldFeatureAetherGoldChest.goldChest(direction).place(world, random, chestPoint.x, chestPoint.y, chestPoint.z);

        List<WorldFeaturePoint> treasureDoor = new ArrayList<>();
        treasureDoor.add(new WorldFeaturePoint(x - 1, y + 2 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(x - 1, y + 3 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(x - 1, y + 4 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(   x,     y + 2 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(   x,     y + 3 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(   x,     y + 4 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(x + 1, y + 2 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(x + 1, y + 3 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.add(new WorldFeaturePoint(x + 1, y + 4 + RADIUS / 2, z + RADIUS - 7));
        treasureDoor.forEach(p -> p.rotateYAroundPivot(x, y, z, angle));
        dungeon.setTreasureDoor(treasureDoor);
    }

    public void createHeightMap(int x, int y, int z) {
        int diameter = RADIUS << 1;
        Set<Integer> hell = hellfire.pallet.getEntries().stream().map(p -> p.first).collect(Collectors.toSet());
        for (int ix = -diameter; ix < diameter; ix++) {
            for (int iz = -diameter; iz < diameter; iz++) {
                if (diameter * diameter >= ix * ix + iz * iz) {
                    for (int iy = y + diameter; iy > y + RADIUS - 4; iy--) {
                        int id = world.getBlockId(x + ix, iy - 1, z + iz);
                        if (id != 0 && (stones.contains(id)) && !hell.contains(id)) {
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
            int x = p.x;
            int y = p.y;
            int z = p.z;
            WorldFeatureComponent dirt = new WorldFeatureComponent();
            if (stones.contains(world.getBlockId(x, y - 1, z))) {
                dirt.add(wfb(x, y - 1, z, AetherBlocks.GRASS_AETHER.id()));
            }
            if (stones.contains(world.getBlockId(x, y - 2, z))) {
                dirt.add(wfb(x, y - 2, z, AetherBlocks.DIRT_AETHER.id()));
            }
            if (stones.contains(world.getBlockId(x, y - 3, z))) {
                dirt.add(wfb(x, y - 3, z, AetherBlocks.DIRT_AETHER.id()));
            }
            if (stones.contains(world.getBlockId(x, y - 4, z)) && world.rand.nextInt(10) > 3) {
                dirt.add(wfb(x, y - 4, z, AetherBlocks.DIRT_AETHER.id()));
            }
            dirt.place(world);
        }
    }


    private void createDecorations() {
        for (WorldFeaturePoint point : heightMap) {
            for (Pair<Integer, WorldFeature> integerWorldFeaturePair : veggies) {
                if (random.nextInt(integerWorldFeaturePair.first) == 0) {
                    integerWorldFeaturePair.second.place(world, random, point.x, point.y, point.z);
                }
            }
        }
    }

}
