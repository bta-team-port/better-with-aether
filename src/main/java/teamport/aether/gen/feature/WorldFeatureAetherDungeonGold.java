package teamport.aether.gen.feature;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureFlowers;
import net.minecraft.core.world.generate.feature.WorldFeatureTallGrass;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.sunspirit.MobBossSunspirit;
import teamport.aether.helper.BlockCoordinate;
import teamport.aether.helper.Pair;
import teamport.aether.items.AetherItems;
import teamport.aether.world.AetherDimension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WorldFeatureAetherDungeonGold extends WorldFeatureAetherDungeonBase{
    public static final BlockPallet hellfire = new BlockPallet();
    public static final BlockPallet holystone = new BlockPallet();

    public static final List<Integer> stones = Arrays.asList(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), AetherBlocks.COBBLE_HOLYSTONE.id());
    static {
        hellfire.addEntry(AetherBlocks.CARVED_HELLFIRE.id(), 0, 90);
        hellfire.addEntry(AetherBlocks.CARVED_HELLFIRE_LIGHT.id(), 0, 10);

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
        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_CHAIN.getDefaultStack(), 1, 1),
                100.0
        );

        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.TOOL_SWORD_VAMPIRE.getDefaultStack(), 1, 1),
                100.0
        );

        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.TOOL_SWORD_PIG.getDefaultStack(), 1, 1),
                100.0
        );

        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_PHOENIX.getDefaultStack(), 1, 1),
                99.0
        );

        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.ARMOR_BOOTS_PHOENIX.getDefaultStack(), 1, 1),
                99.0
        );

        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.ARMOR_HELMET_PHOENIX.getDefaultStack(), 1, 1),
                99.0
        );

        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_PHOENIX.getDefaultStack(), 1, 1),
                99.0
        );

        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_PHOENIX.getDefaultStack(), 1, 1),
                99.0
        );

        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.LIFESHARD.getDefaultStack(), 1, 1),
                100.0
        );

        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.ARMOR_GLOVES_GRAVITITE.getDefaultStack(), 1, 1),
                99.0
        );

        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.ARMOR_BOOTS_GRAVITITE.getDefaultStack(), 1, 1),
                99.0
        );

        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.ARMOR_HELMET_GRAVITITE.getDefaultStack(), 1, 1),
                99.0
        );

        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_GRAVITITE.getDefaultStack(), 1, 1),
                99.0
        );

        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.ARMOR_LEGGINGS_GRAVITITE.getDefaultStack(), 1, 1),
                99.0
        );

        LOOT_RARE.addEntry(
                new WeightedRandomLootObject(AetherItems.ARMOR_CHESTPLATE_OBSIDIAN.getDefaultStack(), 1, 1),
                100.0
        );
    }

    @Override
    public boolean place(World world, Random random, int x, int y, int z) {
        if (AetherDimension.dungeonMap.values().stream().anyMatch(dungeon -> distanceToSqr(x, y, z, dungeon.x, dungeon.y, dungeon.z) <= AetherDimension.dungeonRadiusSQR)) return false;

        int dungeonID = AetherDimension.registerDungeonToMap(x, y + radius/2 + 2, z);

        // place main spheroid
        drawSpheroid(world, random, x, y + 15, z, radius, (int) (radius * 1.12), radius, holystone, true);
        decorateTopLevelInRadius(veggies, radius, world, random, x, y, z);

        // place the outer spheres
        List<Integer> angles = new ArrayList<>();
        for (int angle = 0; angle < 10; angle++) angles.add(angle * (360 / 10));
        for (int index = 0; index < 6 + random.nextInt(4); index++) {
            int angleIndex = random.nextInt(angles.size());
            int angle = angles.get(angleIndex);
            angles.remove(angleIndex);

            double newX = x + radius * Math.cos(Math.toRadians(angle));
            double newZ = z + radius * Math.sin(Math.toRadians(angle));
            double radMod = (double) (4 + random.nextInt(5)) / 10;

            drawSphere(world, random, (int) newX, (int) (y + (radius * 0.8F)), (int) newZ, (int) (radius * radMod), holystone, true);
            decorateTopLevelInRadius(veggies, (int) (radius * radMod), world, random, (int) newX, (int) (y + (radius * 0.8F)), (int) newZ);
        }

        double radMod2 = 0.7F;
        drawSphere(world, random,x + radius, (int) (y + (radius*0.8F)), z, (int) (radius*radMod2), holystone, true);
        decorateTopLevelInRadius(veggies, (int) (radius * radMod2), world, random, x + radius, (int) (y + (radius*0.8F)), z);

        // main room
        int xRoomLength = 19;
        int YRoomHeight = 8;
        int ZRoomLength = 19;
        drawHollowShell(world, random, hellfire, Direction.WEST, xRoomLength, Direction.NORTH, ZRoomLength, Direction.UP, YRoomHeight, x +1 +radius/2, y + radius/2, z +1 +radius/2, true);
        drawSquareCylinder(world, random, hellfire, Direction.WEST, xRoomLength -2, Direction.NORTH, ZRoomLength-2, Direction.UP, 1, x +radius/2, y +1 +radius/2, z +radius/2, true);
        drawSquareCylinder(world, random, hellfire, Direction.WEST, xRoomLength -2, Direction.NORTH, ZRoomLength-2, Direction.UP, 1, x +radius/2, y +YRoomHeight-2 +radius/2, z +radius/2, true);
        drawVolume(world, 0, 0,Direction.WEST, radius*2, Direction.NORTH, 3, Direction.UP, 3,x -radius +xRoomLength, y +2 +radius/2, z +1, true);

        // chest room
        xRoomLength = 7;
        YRoomHeight = 5;
        ZRoomLength = 7;
        drawHollowShell(world, random, hellfire, Direction.WEST, xRoomLength, Direction.NORTH, ZRoomLength, Direction.UP, YRoomHeight, x -1 +radius, y +1 +radius/2, z +ZRoomLength/2, true);

        world.setBlockAndMetadataWithNotify(x -4 +radius, y +2 +radius/2, z, AetherBlocks.GOLD_CHEST_DUNGEON_LOCKED.id(), 4);
        Container inventory = BlockLogicChest.getInventory(world, x -4 +radius, y +2 +radius/2, z);

        for (int i = 0; i < 6 + random.nextInt(6); i++) {
            inventory.setItem(
                random.nextInt(inventory.getContainerSize()),
                LOOT_RARE.getRandom().getItemStack(random)
            );
        }

        MobBossSunspirit boss = new MobBossSunspirit(world);

        BlockCoordinate[] bossDoor = {
                new BlockCoordinate(x +radius -xRoomLength, y +2 +radius/2, z -1),
                new BlockCoordinate(x +radius -xRoomLength, y +3 +radius/2, z -1),
                new BlockCoordinate(x +radius -xRoomLength, y +4 +radius/2, z -1),

                new BlockCoordinate(x +radius -xRoomLength, y +2 +radius/2, z),
                new BlockCoordinate(x +radius -xRoomLength, y +3 +radius/2, z),
                new BlockCoordinate(x +radius -xRoomLength, y +4 +radius/2, z),

                new BlockCoordinate(x +radius -xRoomLength, y +2 +radius/2, z +1),
                new BlockCoordinate(x +radius -xRoomLength, y +3 +radius/2, z +1),
                new BlockCoordinate(x +radius -xRoomLength, y +4 +radius/2, z +1),
        };
        Arrays.stream(bossDoor).forEach(boss::addDestroyOnDeathBlock);

        boss.moveTo(x, y + (double) radius / 2 + 2, z, 0f,0f);
        boss.setTrophy(AetherItems.KEY_GOLD.getDefaultStack());
        boss.setReturnPoint(new BlockCoordinate(x, y + radius / 2 + 2, z));
        boss.setDungeonID(dungeonID);

        world.entityJoinedWorld(boss);

        return true;
    }

    public void drawSquareCylinder(World world, Random random, BlockPallet pallet, Direction direction1, int length1, Direction direction2, int length2, Direction direction3, int length3, int startX, int startY, int startZ, boolean withNotify) {
        drawVolume(world, random, pallet, direction1, length1, direction2, length2, direction3, length3, startX, startY, startZ, withNotify);
        drawVolume(world, 0, 0, direction1, length1 -2, direction2, length2 -2, direction3, length3, startX -1, startY, startZ -1, withNotify);
    }

    public void drawHollowShell(World world, Random random, BlockPallet pallet, Direction direction1, int length1, Direction direction2, int length2, Direction direction3, int length3, int startX, int startY, int startZ, boolean withNotify) {
        drawVolume(world, random, pallet, direction1, length1, direction2, length2, direction3, length3, startX, startY, startZ, withNotify);
        drawVolume(world, 0, 0, direction1, length1 -2, direction2, length2 -2, direction3, length3 -2, startX -1, startY +1, startZ -1, withNotify);
    }

    public void decorateTopLevelInRadius(Pair<Integer, WorldFeature>[] worldFeaturePair, int radius, World world, Random random, int x, int y, int z) {
        int radX, radZ, height;
        for (radX = -radius; radX < radius; radX++) for (radZ = -radius; radZ < radius; radZ++) {
            if (WorldFeatureAetherDungeonBase.distanceToSqr((radX + x), y, (radZ + z), x, y, z) < Math.pow(radius, 2)) {
                height = world.getHeightValue((radX + x), (radZ + z));
                if (Math.abs(height - y) > radius*2.25) continue;

                if (stones.contains(world.getBlockId((radX + x), height - 1, (radZ + z)))) world.setBlockWithNotify((radX + x), height - 1, (radZ + z), AetherBlocks.GRASS_AETHER.id());
                if (stones.contains(world.getBlockId((radX + x), height - 2, (radZ + z)))) world.setBlockWithNotify((radX + x), height - 2, (radZ + z), AetherBlocks.DIRT_AETHER.id());
                if (stones.contains(world.getBlockId((radX + x), height - 3, (radZ + z)))) world.setBlockWithNotify((radX + x), height - 3, (radZ + z), AetherBlocks.DIRT_AETHER.id());
                if (stones.contains(world.getBlockId((radX + x), height - 4, (radZ + z))) && world.rand.nextInt(10) > 3) world.setBlockWithNotify((radX + x), height - 4, (radZ + z), AetherBlocks.DIRT_AETHER.id());

                for (Pair<Integer, WorldFeature> integerWorldFeaturePair : worldFeaturePair)
                    if (random.nextInt(integerWorldFeaturePair.first) == 0)
                        integerWorldFeaturePair.second.place(world, random, (radX + x), height, (radZ + z));
            }
        }
    }
}
