package teamport.aether.world.generate.feature;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureFlowers;
import net.minecraft.core.world.generate.feature.WorldFeatureTallGrass;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.world.DungeonMapEntry;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;
import teamport.aether.helper.Pair;
import teamport.aether.items.AetherItems;
import teamport.aether.world.AetherDimension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static teamport.aether.world.generate.feature.components.WorldFeatureBlock.wfb;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawSphere;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawSpheroid;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.drawVolume;

public class WorldFeatureAetherDungeonGold extends WorldFeature{
    public static final BlockPallet hellfire = new BlockPallet();
    public static final BlockPallet holystone = new BlockPallet();
    public float angle = 0;
    public WorldFeaturePoint dungeonAnker;
    public WorldFeaturePoint bossPosition;
    public World world;
    public Random random;

    public static final List<Integer> stones = Arrays.asList(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), AetherBlocks.COBBLE_HOLYSTONE.id());
    static {
        hellfire.addEntry(AetherBlocks.CARVED_HELLFIRE_LOCKED.id(), 0, 90);
        hellfire.addEntry(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED.id(), 0, 10);

        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE.id(), 0, 90);
        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), 0, 10);
    }

    public static final int radius = 16;

    public static final Pair<Integer, WorldFeature>[] veggies = new Pair[]{
            new Pair<>(128, new WorldFeatureTreeGoldenOak(AetherBlocks.LEAVES_OAK_GOLDEN.id(), AetherBlocks.LOG_OAK_GOLDEN.id())),
            new Pair<>(32, new WorldFeatureTallGrass(AetherBlocks.TALLGRASS_AETHER.id())),
            new Pair<>(84, new WorldFeatureFlowers(AetherBlocks.FLOWER_WHITE.id(), 64, true))
    };

    public static final WeightedRandomBag<WeightedRandomLootObject> LOOT_RARE = new WeightedRandomBag<>();

    static {
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_VAMPIRE.getDefaultStack(), 1, 1), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_FLAME.getDefaultStack(), 1, 1), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_PIG.getDefaultStack(), 1, 1), 100.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_PHOENIX.getDefaultStack(), 1, 1), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_BOOTS_PHOENIX.getDefaultStack(), 1, 1), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_HELMET_PHOENIX.getDefaultStack(), 1, 1), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_PHOENIX.getDefaultStack(), 1, 1), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_PHOENIX.getDefaultStack(), 1, 1), 100.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.LIFESHARD.getDefaultStack(), 1, 1), 100.0);

        for(int i = 0; i < 9; ++i) {
            LOOT_RARE.addEntry(new WeightedRandomLootObject(new ItemStack(Item.itemsList[AetherItems.RECORD_NETHER.id + i])), 10.0);
        }

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_GRAVITITE.getDefaultStack(), 1, 1), 50.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_BOOTS_GRAVITITE.getDefaultStack(), 1, 1), 50.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_HELMET_GRAVITITE.getDefaultStack(), 1, 1), 50.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_GRAVITITE.getDefaultStack(), 1, 1), 50.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_GRAVITITE.getDefaultStack(), 1, 1), 50.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_DUNGEON_COMPASS.getDefaultStack()), 25.0);
    }
    @Override
    public boolean place(World world, Random random, int x, int y, int z) {
        this.angle = random.nextInt(4) * 90.0F;
        this.dungeonAnker = new WorldFeaturePoint(x,y,z);
        this.world = world;
        this.random = random;
        this.bossPosition = this.getPos(x, y + radius/2 + 2, z);
        createMainSphere(x, y, z);
        createOuterSpheres(x, y, z);
        createMainRoom(x, y, z);
        createBossAndTreasure(x, y, z);
        return true;
    }

    private void createMainSphere(int x, int y, int z) {
        // place main spheroid
        this.placeComponent(drawSpheroid(random, holystone, x, y + 15, z, radius, (int) (radius * 1.12), radius,  true));
        decorateTopLevelInRadius(veggies, radius, x, y, z);
    }

    private void createBossAndTreasure(int x, int y, int z) {
        // chest room
        this.placeComponent(drawHollowShell(hellfire, Direction.WEST, 7, Direction.NORTH, 7, Direction.UP, 5, x -1 +radius, y +1 +radius/2, z + 7/2, true));
        // Place boss, chest and door

        DungeonMapEntry dungeon = AetherDimension.dungeonMap.register();
        dungeon.setPosition(new WorldFeaturePoint(bossPosition.x, bossPosition.y, bossPosition.z));

        MobBossSunspirit boss = new MobBossSunspirit(world);
        boss.moveTo(bossPosition.x, bossPosition.y , bossPosition.z, 0f,0f);
        boss.setReturnPoint(new WorldFeaturePoint(bossPosition.x, bossPosition.y, bossPosition.z));
        boss.setDungeonID(dungeon.getId());
        boss.setTrophy(AetherItems.KEY_GOLD.getDefaultStack());

        WorldFeaturePoint chestPoint = new WorldFeaturePoint(x -4 +radius, y +2 +radius/2, z);
        chestPoint.rotateFixPointYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
        world.setBlockAndMetadataWithNotify(chestPoint.x, chestPoint.y, chestPoint.z, AetherBlocks.GOLD_CHEST_DUNGEON_LOCKED.id(), 4);
        Container inventory = BlockLogicChest.getInventory(world, chestPoint.x, chestPoint.y, chestPoint.z);

        for (int i = 0; i < 6 + random.nextInt(6); i++) {
            inventory.setItem(
                    random.nextInt(inventory.getContainerSize()),
                    LOOT_RARE.getRandom().getItemStack(random)
            );
        }

        WorldFeaturePoint[] bossDoor = {
                new WorldFeaturePoint(x +radius -7, y +2 +radius/2, z -1),
                new WorldFeaturePoint(x +radius -7, y +3 +radius/2, z -1),
                new WorldFeaturePoint(x +radius -7, y +4 +radius/2, z -1),

                new WorldFeaturePoint(x +radius -7, y +2 +radius/2, z),
                new WorldFeaturePoint(x +radius -7, y +3 +radius/2, z),
                new WorldFeaturePoint(x +radius -7, y +4 +radius/2, z),

                new WorldFeaturePoint(x +radius -7, y +2 +radius/2, z +1),
                new WorldFeaturePoint(x +radius -7, y +3 +radius/2, z +1),
                new WorldFeaturePoint(x +radius -7, y +4 +radius/2, z +1),
        };

        for(WorldFeaturePoint pos : bossDoor){ pos.rotateFixPointYAxis(x, y, z, angle); }
        dungeon.setDoorBlocks(bossDoor);

        world.entityJoinedWorld(boss);
    }

    private void createMainRoom(int x, int y, int z) {
        // main room
        int xRoomLength = 19;
        int YRoomHeight = 8;
        int ZRoomLength = 19;
        WorldFeatureComponent main = new WorldFeatureComponent();
        main.add(drawHollowShell(hellfire, Direction.WEST, xRoomLength, Direction.NORTH, ZRoomLength, Direction.UP, YRoomHeight, x +1 +radius/2, y + radius/2, z +1 +radius/2, true));
        main.add(drawSquareCylinder(hellfire, Direction.WEST, xRoomLength -2, Direction.NORTH, ZRoomLength -2, Direction.UP, 1, x +radius/2, y +1 +radius/2, z +radius/2, true));
        main.add(drawSquareCylinder(hellfire, Direction.WEST, xRoomLength -2, Direction.NORTH, ZRoomLength -2, Direction.UP, 1, x +radius/2, y + YRoomHeight -2 +radius/2, z +radius/2, true));
        main.add(drawVolume(0, 0,Direction.WEST, radius*2, Direction.NORTH, 3, Direction.UP, 3, x -radius + xRoomLength, y +2 +radius/2, z +1, true));
        this.placeComponent(main);
    }

    // TODO these sphere do not rotate
    private void createOuterSpheres(int x, int y, int z) {
        // place the outer spheres
        List<Integer> angles = new ArrayList<>();
        for (int angle = 0; angle < 10; angle++) {
            angles.add(angle * (360 / 10));
        }
        for (int index = 0; index < 6 + random.nextInt(4); index++) {
//            WorldFeatureComponent outerSphere = new WorldFeatureComponent();
            int angleIndex = random.nextInt(angles.size());
            int angle = angles.get(angleIndex);
            angles.remove(angleIndex);

            double newX = x + radius * Math.cos(Math.toRadians(angle));
            double newZ = z + radius * Math.sin(Math.toRadians(angle));
            double radMod = (double) (4 + random.nextInt(5)) / 10;

            this.placeComponent(drawSphere(random, holystone, (int) newX, (int) (y + (radius * 0.8F)), (int) newZ, (int) (radius * radMod), true));
            decorateTopLevelInRadius(veggies, (int) (radius * radMod), (int) newX, (int) (y + (radius * 0.8F)), (int) newZ);
        }

        double radMod2 = 0.7F;
        this.placeComponent(drawSphere(random, holystone, x + radius, (int) (y + (radius*0.8F)), z, (int) (radius*radMod2),  true));

        decorateTopLevelInRadius(veggies, (int) (radius * radMod2), x + radius, (int) (y + (radius*0.8F)), z);

    }

    public void placeComponent(WorldFeatureComponent component) {
        component.rotateYAxis(dungeonAnker.x,dungeonAnker.y,dungeonAnker.z, angle);
        component.place(world);
    }

    public WorldFeaturePoint getPos(int ix, int iy, int iz) {
        WorldFeaturePoint pos = new WorldFeaturePoint(ix,iy,iz);
        pos.rotateFixPointYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z,angle);
        return pos;
    }

    public WorldFeatureComponent drawSquareCylinder(
            BlockPallet pallet,
            Direction direction1, int length1,
            Direction direction2, int length2,
            Direction direction3, int length3,
            int startX, int startY, int startZ,
            boolean withNotify
    ) {
        WorldFeatureComponent cylinder = drawVolume(
                random, pallet,
                direction1, length1, direction2, length2, direction3,
                length3, startX, startY, startZ, withNotify
        );
        cylinder.add(drawVolume(
                0, 0,
                direction1, length1 -2, direction2, length2 -2, direction3,
                length3, startX -1, startY, startZ -1, withNotify)
        );
        return cylinder;
    }

    public WorldFeatureComponent drawHollowShell(
            BlockPallet pallet,
            Direction direction1, int length1,
            Direction direction2, int length2,
            Direction direction3, int length3,
            int startX, int startY, int startZ,
            boolean withNotify
    ) {
        WorldFeatureComponent hollow = drawVolume(
                random, pallet,
                direction1, length1, direction2, length2, direction3,
                length3, startX, startY, startZ, withNotify
        );
        hollow.add(drawVolume(
                0, 0,
                direction1, length1 -2, direction2, length2 -2, direction3,
                length3 -2, startX -1, startY +1, startZ -1, withNotify)
        );
        return  hollow;
    }

    // TODO make the decorator rotate
    public void decorateTopLevelInRadius(Pair<Integer, WorldFeature>[] worldFeaturePair, int radius, int x, int y, int z) {
        int radX, radZ, height;
        for (radX = -radius; radX < radius; radX++) for (radZ = -radius; radZ < radius; radZ++) {
            if (WorldFeatureComponent.distanceToSqr((radX + x), y, (radZ + z), x, y, z) < Math.pow(radius, 2)) {
                WorldFeatureComponent decorator = new WorldFeatureComponent();
                height = world.getHeightValue((radX + x), (radZ + z));
                if (Math.abs(height - y) > radius*2.25) continue;

                if (stones.contains(world.getBlockId((radX + x), height - 1, (radZ + z)))) {
                    decorator.add(wfb((radX + x), height - 1, (radZ + z), AetherBlocks.GRASS_AETHER.id()));
                }
                if (stones.contains(world.getBlockId((radX + x), height - 2, (radZ + z)))) {
                    decorator.add(wfb((radX + x), height - 2, (radZ + z), AetherBlocks.DIRT_AETHER.id()));
                }
                if (stones.contains(world.getBlockId((radX + x), height - 3, (radZ + z)))) {
                    decorator.add(wfb((radX + x), height - 3, (radZ + z), AetherBlocks.DIRT_AETHER.id()));
                }
                if (stones.contains(world.getBlockId((radX + x), height - 4, (radZ + z))) && world.rand.nextInt(10) > 3) {
                    decorator.add(wfb((radX + x), height - 4, (radZ + z), AetherBlocks.DIRT_AETHER.id()));
                }
                this.placeComponent(decorator);

                for (Pair<Integer, WorldFeature> integerWorldFeaturePair : worldFeaturePair) {
                    if (random.nextInt(integerWorldFeaturePair.first) == 0) {
                        WorldFeaturePoint coords = new WorldFeaturePoint((radX + x), height, (radZ + z));
                        coords.rotateFixPointYAxis(dungeonAnker.x, dungeonAnker.y, dungeonAnker.z, angle);
                        integerWorldFeaturePair.second.place(world, random,coords.x, coords.y, coords.z);
                    }
                }
            }
        }
    }
}
