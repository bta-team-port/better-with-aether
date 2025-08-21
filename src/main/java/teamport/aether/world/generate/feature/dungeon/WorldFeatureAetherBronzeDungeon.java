package teamport.aether.world.generate.feature.dungeon;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.helper.AetherMathHelper;
import teamport.aether.helper.Pair;
import teamport.aether.items.AetherItems;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.DungeonMapEntry;
import teamport.aether.world.generate.feature.BlockPallet;
import teamport.aether.world.generate.feature.chests.WorldFeatureAetherBronzeChest;
import teamport.aether.world.generate.feature.components.WorldFeatureBlock;
import teamport.aether.world.generate.feature.components.WorldFeatureComponent;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static net.minecraft.core.util.helper.Direction.*;
import static teamport.aether.world.generate.feature.components.WorldFeatureComponent.*;

public class WorldFeatureAetherBronzeDungeon extends WorldFeature {

    public static final BlockPallet carvedHolystone = new BlockPallet();
    public static final BlockPallet lockedCarvedHolystone = new BlockPallet();
    public static final BlockPallet holystone = new BlockPallet();
    public static final int ROOM_COUNT_MAX = 13;
    public static final int ROOM_HEIHGT_MEAN = 2;
    public int roomCount = 0;
    public float angle = 0;
    public World world;
    public Random random;


    static {
        carvedHolystone.addEntry(AetherBlocks.CARVED_STONE.id(), 0, 85);
        carvedHolystone.addEntry(AetherBlocks.CARVED_STONE_LIGHT.id(), 0, 5);
        carvedHolystone.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, 10);

        lockedCarvedHolystone.addEntry(AetherBlocks.CARVED_STONE_LOCKED.id(), 0, 85);
        lockedCarvedHolystone.addEntry(AetherBlocks.CARVED_STONE_LIGHT_LOCKED.id(), 0, 5);
        lockedCarvedHolystone.addEntry(AetherBlocks.CARVED_STONE_TRAPPED_LOCKED.id(), 0, 10);

        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE.id(), 0, 90);
        holystone.addEntry(AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id(), 0, 10);
    }

    public static final WeightedRandomBag<WeightedRandomLootObject> LOOT_NORMAL = new WeightedRandomBag<>();

    static {
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ZANITE.getDefaultStack(), 1, 4), 100.0);

        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_PICKAXE_HOLYSTONE.getDefaultStack()).setRandomMetadata(AetherItems.TOOL_PICKAXE_HOLYSTONE.getMaxDamage() / 2, AetherItems.TOOL_PICKAXE_HOLYSTONE.getMaxDamage()), 100.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_AXE_HOLYSTONE.getDefaultStack()).setRandomMetadata(AetherItems.TOOL_AXE_HOLYSTONE.getMaxDamage() / 2, AetherItems.TOOL_AXE_HOLYSTONE.getMaxDamage()), 100.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_HOLYSTONE.getDefaultStack()).setRandomMetadata(AetherItems.TOOL_SWORD_HOLYSTONE.getMaxDamage() / 2, AetherItems.TOOL_SWORD_HOLYSTONE.getMaxDamage()), 100.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SHOVEL_HOLYSTONE.getDefaultStack()).setRandomMetadata(AetherItems.TOOL_SHOVEL_HOLYSTONE.getMaxDamage() / 2, AetherItems.TOOL_SHOVEL_HOLYSTONE.getMaxDamage()), 100.0);


        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMBROSIUM.getDefaultStack(), 1, 10), 100.0);

        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.BUCKET_SKYROOT.getDefaultStack()), 100.0);

        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_LEATHER.getDefaultStack()), 96.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_ZANITE.getDefaultStack()), 90.0);

        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_GOLDEN.getDefaultStack(), 1, 5), 100.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_POISON.getDefaultStack(), 1, 3), 100.0);
        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMMO_DART_ENCHANTED.getDefaultStack(), 1, 3), 100.0);

        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherBlocks.TORCH_AMBROSIUM.getDefaultStack(), 1, 8), 100.0);

    }

    public static final WeightedRandomBag<WeightedRandomLootObject> LOOT_RARE = new WeightedRandomBag<>();

    static {
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.RECORD_MORNING.getDefaultStack()), 10.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_BLUE.getDefaultStack(), 1, 8), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_GOLD.getDefaultStack(), 1, 4), 90.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_TALISMAN_BUBBLE.getDefaultStack()), 100.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_BOW_PHOENIX.getDefaultStack()), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_HAMMER_NOTCH.getDefaultStack()), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_KNIFE_LIGHTNING.getDefaultStack(), 1, 16), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_LIGHTNING.getDefaultStack()), 100.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CAPE_AGILITY.getDefaultStack()), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CAPE_SWET.getDefaultStack()), 100.0);
    }

    public WorldFeatureAetherBronzeDungeon(int direction) {
        this.angle = direction * 90;
    }

    public WorldFeatureAetherBronzeDungeon() {
    }

    public static WorldFeatureAetherBronzeDungeon bronzeDungeon(Random random) {
        return new WorldFeatureAetherBronzeDungeon(0);
    }

    @Override
    public boolean place(final World world, final Random random, final int x, final int y, final int z) {
        this.world = world;
        this.random = random;

        if (this.isBoxEmpty(x, y, z, EAST, 16, UP, 12, SOUTH, 16)) return false;

        // antechamber
        drawShell(random, lockedCarvedHolystone, EAST, 16, UP, 12, SOUTH, 16, x, y, z, true).place(world);
        this.addSolidBox(0, 0, x + 1, y + 1, z + 1, 14, 10, 14);

        // boss room
        createBossAndTreasure(x, y, z, x + 7 + random.nextInt(2), y - 1, z + 7 + random.nextInt(2));


        int x2 = x + 20;
        int z2 = z + 2;

        if (this.isBoxEmpty(x2, y, z2, EAST, 12, UP, 12, SOUTH, 12)) {
            this.addSquareTube(holystone, x2 - 5, y, z2 + 3, 6, 6, 6, NORTH);
            return true;
        }

        drawShell(random, carvedHolystone, EAST, 12, UP, 12, SOUTH, 12, x2, y, z2, true).place(world);
        this.addSolidBox(0, 0, x2 + 1, y + 1, z2 + 1, 10, 10, 10);
        this.addSquareTube(holystone, x2 - 5, y, z2 + 3, 6, 6, 6, NORTH);

        findNextRoom(x2, y, z2);
        return true;
    }

    private void createBossAndTreasure(int x, int y, int z, int x2, int y2, int z2) {
        drawShell(random, lockedCarvedHolystone, EAST, 4, UP, 4, SOUTH, 4, x + 6, y - 2, z + 6, true).place(world);
        this.addSolidBox(0, 0, x + 7, y - 1, z + 7, 2, 2, 2);
        DungeonMapEntry dungeon = AetherDimension.dungeonMap.register();
        dungeon.setPosition(new WorldFeaturePoint(x + 8, y + 2, z + 8));
        dungeon.setClearArea(new Pair<>(
                new WorldFeaturePoint(x, y - 2, z),
                new WorldFeaturePoint(x + 16, y + 14, z + 16)
        ));

        WorldFeatureAetherBronzeChest.bronzeChest().place(world, random, x2, y2, z2);
        MobBossSlider boss = new MobBossSlider(world);
        boss.moveTo(x + 8, y + 2, z + 8, 0f, 0f);
        boss.setReturnPoint(new WorldFeaturePoint(x + 8, y + 2, z + 8));
        boss.setTrophy(AetherItems.KEY_BRONZE.getDefaultStack());

        boss.setDungeonID(dungeon.getId());

        dungeon.setDoorBlocks(new WorldFeaturePoint[]{
                new WorldFeaturePoint(x + 7, y + 1, z + 7),
                new WorldFeaturePoint(x + 8, y + 1, z + 7),
                new WorldFeaturePoint(x + 7, y + 1, z + 8),
                new WorldFeaturePoint(x + 8, y + 1, z + 8),
        });

        world.entityJoinedWorld(boss);
    }

    // TODO rewrite it to be not recursive!
    public boolean findNextRoom(int x, int y, int z) {
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
        endCorridor(x, y, z, Direction.horizontalDirections[random.nextInt(3)]);
        return false;
    }

    // TODO sometime places the start of a tunnel but does not build it in full
    public boolean placeNextRoom(final int finalX, final int finalY, final int finalZ, Direction dir) {
        int x = finalX, z = finalZ;

        if (dir == NORTH) {
            x += 16;
        }
        if (dir == EAST) {
            z += 16;
        }
        if (dir == SOUTH) {
            x -= 16;
        }
        if (dir == WEST) {
            z -= 16;
        }
        int height = Math.min((int) Math.floor(AetherMathHelper.nextExponential(random) * ROOM_HEIHGT_MEAN), 6);

        if (this.roomCount > ROOM_COUNT_MAX) {
            this.endCorridor(finalX, finalY, finalZ, pickNewDir(dir));
            return false;
        }
        if (this.isBoxEmpty(x, finalY, z, EAST, 12, UP, 8 + height, SOUTH, 12)) {
            return true;
        }
        if (world.canBlockSeeTheSky(x, finalY + 1, z)) return true;

        ++this.roomCount;
        // varies the height, clamps it down to 8, cause we could get very large numbers

        // place room
        drawShell(random, carvedHolystone, EAST, 12, UP, 8 + height, SOUTH, 12, x, finalY, z, true).place(world);
        this.addSolidBox(0, 0, x + 1, finalY + 1, z + 1, 10, 6 + height, 10);

        // plinth
        drawPlane(random, carvedHolystone, SOUTH, 4, EAST, 4, x + 4, finalY + 1, z + 4, true).place(world);

        final int p2 = x + 5;
        final int q2 = z + 5;

        WorldFeatureComponent chests = new WorldFeatureComponent();
        int chestCount = 0;
        if (random.nextInt(48) == 0) {
            placeNextRoom(finalX, finalY - 12, finalZ, dir);
            this.addSolidBox(0, 0, p2, finalY - 9, q2, 2, 11, 2);
        } else {
            if (world.rand.nextInt(3) == 0) {
                chestCount++;
                chests.add(placeChestOrMimic(random, p2, finalY + 2, q2));
            }
            if (world.rand.nextInt(3) == 0) {
                chestCount++;
                chests.add(placeChestOrMimic(random, p2, finalY + 2, q2 + 1));
            }
            if (world.rand.nextInt(3) == 0) {
                chestCount++;
                chests.add(placeChestOrMimic(random, p2 + 1, finalY + 2, q2));
            }
            if (world.rand.nextInt(3) == 0 || chestCount < 2) {
                chests.add(placeChestOrMimic(random, p2 + 1, finalY + 2, q2 + 1));
            }
        }
        chests.place(world);
        for (WorldFeatureBlock chest : chests.blockList) {
            populateChest(world, chest, random, LOOT_NORMAL);
        }

        switch (dir) {
            case NORTH: {
                this.addSquareTube(holystone, x - 5, finalY, z + 3, 6, 6, 6, NORTH);
                break;
            }
            case EAST: {
                this.addSquareTube(holystone, x + 3, finalY, z - 5, 6, 6, 6, SOUTH);
                break;
            }
            case SOUTH: {
                this.addSquareTube(holystone, x + 11, finalY, z + 3, 6, 6, 6, NORTH);
                break;
            }
            case WEST: {
                this.addSquareTube(holystone, x + 3, finalY, z + 11, 6, 6, 6, SOUTH);
                break;
            }
        }

        return findNextRoom(x, finalY, z);
    }

    public Direction pickNewDir(Direction me) {
        Direction result = me;
        while (result == me) {
            result = Direction.horizontalDirections[random.nextInt(4)];
        }
        return result;
    }


    public void endCorridor(final int finalX, final int finalY, final int finalZ, Direction dir) {
        boolean tunnelling = true;
        int x = finalX;
        int z = finalZ;

        /// If we somehow get a non-horizontal direction
        if (dir.getId() > 2) {
            dir = Direction.horizontalDirections[random.nextInt(3)];
        }

        if (dir == NORTH) {
            x += 11;
            z += 3;
            while (tunnelling) {
                if (this.isBoxEmpty(x, finalY, z, UP, 8, EAST, 6, SOUTH, 1) || z - finalZ > 100) {
                    tunnelling = false;
                }
                drawPlane(random, holystone, UP, 8, SOUTH, 6, x, finalY, z, true).place(world);
                drawPlane(0, 0, UP, 6, SOUTH, 4, x, finalY + 1, z + 1, true).place(world);
                ++x;
            }
        }
        if (dir == EAST) {
            x += 3;
            z += 11;
            while (tunnelling) {
                if (this.isBoxEmpty(x, finalY, z, UP, 8, EAST, 6, SOUTH, 1) || z - finalZ > 100) {
                    tunnelling = false;
                }
                drawPlane(random, holystone, UP, 8, EAST, 6, x, finalY, z, true).place(world);
                drawPlane(0, 0, UP, 6, EAST, 4, x + 1, finalY + 1, z, true).place(world);
                ++z;
            }
        }

        if (dir == SOUTH) {
            x += 3;
            while (tunnelling) {
                if (this.isBoxEmpty(x, finalY, z, UP, 8, EAST, 6, SOUTH, 1) || finalY - z > 100) {
                    tunnelling = false;
                }

                drawPlane(random, holystone, UP, 8, EAST, 6, x, finalY, z, true).place(world);
                drawPlane(0, 0, UP, 6, EAST, 4, x + 1, finalY + 1, z, true).place(world);
                --z;
            }
        }
    }

    public boolean isBoxEmpty(int startX, int startY, int startZ, Direction direction1, int length1, Direction direction2, int length2, Direction direction3, int length3) {
        return isBoxEmpty(startX, startY, startZ, direction1, length1, direction2, length2, direction3, length3, 0.35F);
    }

    public boolean isBoxEmpty(int startX, int startY, int startZ, Direction direction1, int length1, Direction direction2, int length2, Direction direction3, int length3, float percent) {
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
                    if (world.getBlockId(blockX, blockY, blockZ) == 0) volume++;
                    blockX += direction1.getOffsetX();
                    blockY += direction1.getOffsetY();
                    blockZ += direction1.getOffsetZ();
                }
            }
        }

        // I'm literally frito-lay fr fr fr
        return volume > ((length1 * length2 * length3) * percent);
    }

    public void addSolidBox(int id, int meta, int startX, int startY, int startZ, int lengthX, int lengthY, int lengthZ) {
        for (int x = startX; x < startX + lengthX; ++x) {
            for (int y = startY; y < startY + lengthY; ++y) {
                for (int z = startZ; z < startZ + lengthZ; ++z) {
                    world.setBlockAndMetadataWithNotify(x, y, z, id, meta);
                }
            }
        }
    }


    public void addSquareTube(BlockPallet pallet, int x, int y, int z, int lengthX, int lengthY, int lengthZ, Direction dir) {
        this.addSolidBox(0, 0, x, y, z, lengthX, lengthY, lengthZ);

        if (dir == NORTH || dir == SOUTH) {
            drawPlane(random, pallet, SOUTH, lengthX, EAST, lengthZ, x, y, z, true).place(world);
            drawPlane(random, pallet, SOUTH, lengthX, EAST, lengthZ, x, y + lengthY - 1, z, true).place(world);
        }

        if (dir == EAST || dir == SOUTH) {
            drawPlane(random, pallet, UP, lengthY, SOUTH, lengthZ, x, y, z, true).place(world);
            drawPlane(random, pallet, UP, lengthY, SOUTH, lengthZ, x + lengthX - 1, y, z, true).place(world);
        }

        if (dir == NORTH || dir == EAST) {
            drawPlane(random, pallet, UP, lengthX, EAST, lengthY, x, y, z, true).place(world);
            drawPlane(random, pallet, UP, lengthX, EAST, lengthY, x, y, z + lengthZ - 1, true).place(world);
        }
    }

}
