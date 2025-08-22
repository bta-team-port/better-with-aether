package teamport.aether.world.generate.feature.dungeon;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
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
import teamport.aether.world.DungeonMapEntry;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.WorldFeatureAetherTreeGoldenOak;
import teamport.aether.world.generate.feature.chests.WorldFeatureAetherGoldChest;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static teamport.aether.world.generate.feature.components.WorldFeatureBlock.wfb;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;

public class WorldFeatureAetherGoldDungeon extends WorldFeature {
    public static final BlockPallet hellfire = new BlockPallet();
    public static final BlockPallet holystone = new BlockPallet();
    public float angle = 0;
    public WorldFeatureComponent decorations;
    public WorldFeaturePoint dungeonAnker;
    public WorldFeaturePoint bossPosition;
    public World world;
    public Random random;
    protected DungeonMapEntry dungeon;

    public static final List<Integer> stones = Arrays.asList(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), AetherBlocks.COBBLE_HOLYSTONE.id());

    static {
        hellfire.addEntry(AetherBlocks.CARVED_HELLFIRE_LOCKED.id(), 0, 90);
        hellfire.addEntry(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED.id(), 0, 10);

        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE.id(), 0, 90);
        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), 0, 10);
    }

    public static final int RADIUS = 16;

    public static final Pair<Integer, WorldFeature>[] veggies = new Pair[]{
            new Pair<>(128, new WorldFeatureAetherTreeGoldenOak(AetherBlocks.LEAVES_OAK_GOLDEN.id(), AetherBlocks.LOG_OAK_GOLDEN.id())),
            new Pair<>(32, new WorldFeatureTallGrass(AetherBlocks.TALLGRASS_AETHER.id())),
            new Pair<>(84, new WorldFeatureFlowers(AetherBlocks.FLOWER_WHITE.id(), 64, true))
    };

    public static final WeightedRandomBag<WeightedRandomLootObject> LOOT_NORMAL = new WeightedRandomBag<>();

    static {
        // unlucky
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(null), 600.0F);

        // common
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherBlocks.BLOCK_AMBER.getDefaultStack(), 1, 8), 300.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherBlocks.BLOCK_AMBROSIUM.getDefaultStack(), 1, 6), 300.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherBlocks.BLOCK_ZANITE.getDefaultStack(), 1, 4), 300.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherBlocks.BLOCK_GRAVITITE.getDefaultStack(), 1, 2), 300.0);

        // ammo
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_GOLDEN.getDefaultStack(), 8, 32), 600.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_POISON.getDefaultStack(), 6, 24), 400.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_ENCHANTED.getDefaultStack(), 4, 16), 200.0);

        // jack pot
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.EGG_MOA_BLACK.getDefaultStack()), 50.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_GRAVITITE.getDefaultStack(), 1), 50.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_BOOTS_GRAVITITE.getDefaultStack(), 1), 50.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_HELMET_GRAVITITE.getDefaultStack(), 1), 50.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_GRAVITITE.getDefaultStack(), 1), 50.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_GRAVITITE.getDefaultStack(), 1), 50.0);
    }

    public static final WeightedRandomBag<WeightedRandomLootObject> LOOT_RARE = new WeightedRandomBag<>();

    static {
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_VAMPIRE.getDefaultStack(), 1), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_FLAME.getDefaultStack(), 1), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_PIG.getDefaultStack(), 1), 100.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_PHOENIX.getDefaultStack(), 1), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_BOOTS_PHOENIX.getDefaultStack(), 1), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_HELMET_PHOENIX.getDefaultStack(), 1), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_PHOENIX.getDefaultStack(), 1), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_PHOENIX.getDefaultStack(), 1), 100.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.LIFESHARD.getDefaultStack(), 1, 2), 50.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.RECORD_NETHER.getDefaultStack()), 10.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_DUNGEON_COMPASS.getDefaultStack()), 25.0);
    }

    public WorldFeatureAetherGoldDungeon(int direction) {
        this.angle = direction * 90;
    }

    public WorldFeatureAetherGoldDungeon() {
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

    @Override
    public boolean place(World world, Random random, int x, int y, int z) {
        if (!canPlace(world, x, y, z)) return false;
        this.world = world;
        this.random = random;
//        this.angle = this.random.nextInt(4) * 90.0F;
        this.dungeonAnker = new WorldFeaturePoint(x, y, z);
        this.bossPosition = this.getPos(x, y + RADIUS / 2 + 2, z);
        this.decorations = new WorldFeatureComponent();
        this.dungeon = AetherDimension.dungeonMap.register();
        this.dungeon.setPosition(bossPosition);
        createMainSphere(x, y, z);
        createOuterSpheres(x, y, z);
        createMainRoom(x, y, z);
        createBossAndTreasure(x, y, z);
        createDecorations(x, y, z);
        return true;
    }

    private boolean canPlace(World world, int x, int y, int z) {
        int radius = (int) Math.ceil(RADIUS + RADIUS * 0.8f);
        for (int ix = -radius; ix < radius; ix++) {
            for (int iz = -radius; iz < radius; iz++) {
                if (radius * radius >= ix * ix + iz * iz) {
                    if (!world.canBlockSeeTheSky(ix + x, y, iz + z)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void createMainSphere(int x, int y, int z) {
        // place main spheroid
        drawSpheroid(random, holystone, x, y + 15, z, RADIUS, (int) (RADIUS * 1.12), RADIUS, true).place(world);
        wfb(x, (int) Math.floor(15 * 1.12 * 2 + y) - 1, z, 0, 0, true).place(world);
        wfb(x, (int) Math.floor(15 * 1.12 * 2 + y) - 2, z, AetherBlocks.GRASS_AETHER.id(), 0, true).place(world);
        createGrassOnTopLevel(RADIUS, x, y, z);
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
            createGrassOnTopLevel((int) (RADIUS * radMod), (int) (newX), (int) (y + (RADIUS * 0.8F)), (int) newZ);
        }

        double radMod2 = 0.7F;
        WorldFeaturePoint cover = new WorldFeaturePoint(x + RADIUS, (int) (y + (RADIUS * 0.8F)), z);
        cover.rotateFixPointYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, this.angle);
        drawSphere(random, holystone, cover.x, cover.y, cover.z, (int) (RADIUS * radMod2), true);
        createGrassOnTopLevel((int) (RADIUS * radMod2), cover.x, cover.y, cover.z);
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

        clearArea.first.rotateFixPointYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
        clearArea.second.rotateFixPointYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
        dungeon.setClearArea(clearArea);

        main.add(drawHollowShell(random, hellfire, Direction.WEST, xRoomLength, Direction.NORTH, ZRoomLength, Direction.UP, YRoomHeight, x + 1 + RADIUS / 2, y + RADIUS / 2, z + 1 + RADIUS / 2, true));
        main.add(drawSquareCylinder(random, hellfire, Direction.WEST, xRoomLength - 2, Direction.NORTH, ZRoomLength - 2, Direction.UP, 1, x + RADIUS / 2, y + 1 + RADIUS / 2, z + RADIUS / 2, true));
        main.add(drawSquareCylinder(random, hellfire, Direction.WEST, xRoomLength - 2, Direction.NORTH, ZRoomLength - 2, Direction.UP, 1, x + RADIUS / 2, y + YRoomHeight - 2 + RADIUS / 2, z + RADIUS / 2, true));
        main.add(drawVolume(0, 0, Direction.WEST, RADIUS * 2, Direction.NORTH, 3, Direction.UP, 3, x - RADIUS + xRoomLength, y + 2 + RADIUS / 2, z + 1, true));
        this.placeComponent(main);
    }

    private void createBossAndTreasure(int x, int y, int z) {
        // chest room
        this.placeComponent(drawHollowShell(random, hellfire, Direction.WEST, 7, Direction.NORTH, 7, Direction.UP, 5, x - 1 + RADIUS, y + 1 + RADIUS / 2, z + 7 / 2, true));
        // Place boss, chest and door

        MobBossSunspirit boss = new MobBossSunspirit(world);
        boss.moveTo(bossPosition.x, bossPosition.y, bossPosition.z, 0f, 0f);
        boss.setReturnPoint(new WorldFeaturePoint(bossPosition.x, bossPosition.y, bossPosition.z));
        boss.setDungeonID(dungeon.getId());
        boss.setTrophy(AetherItems.KEY_GOLD.getDefaultStack());

        WorldFeaturePoint chestPoint = new WorldFeaturePoint(x - 4 + RADIUS, y + 2 + RADIUS / 2, z);
        chestPoint.rotateFixPointYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
        WorldFeatureAetherGoldChest.goldChest().place(world, random, chestPoint.x, chestPoint.y, chestPoint.z);

        WorldFeaturePoint[] bossDoor = {
                new WorldFeaturePoint(x + RADIUS - 7, y + 2 + RADIUS / 2, z - 1),
                new WorldFeaturePoint(x + RADIUS - 7, y + 3 + RADIUS / 2, z - 1),
                new WorldFeaturePoint(x + RADIUS - 7, y + 4 + RADIUS / 2, z - 1),

                new WorldFeaturePoint(x + RADIUS - 7, y + 2 + RADIUS / 2, z),
                new WorldFeaturePoint(x + RADIUS - 7, y + 3 + RADIUS / 2, z),
                new WorldFeaturePoint(x + RADIUS - 7, y + 4 + RADIUS / 2, z),

                new WorldFeaturePoint(x + RADIUS - 7, y + 2 + RADIUS / 2, z + 1),
                new WorldFeaturePoint(x + RADIUS - 7, y + 3 + RADIUS / 2, z + 1),
                new WorldFeaturePoint(x + RADIUS - 7, y + 4 + RADIUS / 2, z + 1),
        };

        for (WorldFeaturePoint pos : bossDoor) {
            pos.rotateFixPointYAxis(x, y, z, angle);
        }
        dungeon.setDoorBlocks(bossDoor);

        world.entityJoinedWorld(boss);
    }

    // TODO make the decorator rotate
    public void createGrassOnTopLevel(int radius, int x, int y, int z) {
        int radX, radZ, height;
        for (radX = -radius; radX < radius; radX++) {
            for (radZ = -radius; radZ < radius; radZ++) {
                if (AetherMathHelper.distanceToSqr((radX + x), y, (radZ + z), x, y, z) < Math.pow(radius, 2)) {
                    height = world.getHeightValue((radX + x), (radZ + z));
                    if (Math.abs(height - y) > radius * 2.25) {
                        continue;
                    }
                    WorldFeatureComponent dirt = new WorldFeatureComponent();
                    if (stones.contains(world.getBlockId((radX + x), height - 1, (radZ + z)))) {
                        dirt.add(wfb((radX + x), height - 1, (radZ + z), AetherBlocks.GRASS_AETHER.id()));
                    }
                    if (stones.contains(world.getBlockId((radX + x), height - 2, (radZ + z)))) {
                        dirt.add(wfb((radX + x), height - 2, (radZ + z), AetherBlocks.DIRT_AETHER.id()));
                    }
                    if (stones.contains(world.getBlockId((radX + x), height - 3, (radZ + z)))) {
                        dirt.add(wfb((radX + x), height - 3, (radZ + z), AetherBlocks.DIRT_AETHER.id()));
                    }
                    if (stones.contains(world.getBlockId((radX + x), height - 4, (radZ + z))) && world.rand.nextInt(10) > 3) {
                        dirt.add(wfb((radX + x), height - 4, (radZ + z), AetherBlocks.DIRT_AETHER.id()));
                    }
                    dirt.place(world);
                    decorations.add(wfb((radX + x), height, (radZ + z), 0, 0));
                }
            }
        }
    }

    public void createDecorations(int x, int y, int z) {
        for (WorldFeaturePoint point : decorations.blockList) {
            for (Pair<Integer, WorldFeature> integerWorldFeaturePair : veggies) {
                if (random.nextInt(integerWorldFeaturePair.first) == 0) {
                    integerWorldFeaturePair.second.place(world, random, point.x, point.y, point.z);
                }
            }
        }
    }

}
