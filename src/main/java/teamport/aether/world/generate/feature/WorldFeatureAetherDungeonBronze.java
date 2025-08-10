package teamport.aether.world.generate.feature;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.helper.BlockCoordinate;
import teamport.aether.items.AetherItems;
import teamport.aether.world.AetherDimension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WorldFeatureAetherDungeonBronze extends WorldFeatureAetherDungeonBase {

    public static final BlockPallet carvedHolystone = new BlockPallet();
    public static final BlockPallet holystone = new BlockPallet();
    static {
        carvedHolystone.addEntry(AetherBlocks.CARVED_STONE_LOCKED.id(), 0, 85);
        carvedHolystone.addEntry(AetherBlocks.CARVED_STONE_LIGHT_LOCKED.id(), 0, 5);
        carvedHolystone.addEntry(AetherBlocks.CARVED_STONE_TRAPPED.id(), 0, 10);

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

        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CAPE_SWET.getDefaultStack()), 100.0);

        LOOT_NORMAL.addEntry(new WeightedRandomLootObject(AetherItems.AMBROSIUM.getDefaultStack(), 1, 10), 100.0);

        for(int i = 0; i < 9; ++i) {
            LOOT_NORMAL.addEntry(new WeightedRandomLootObject(new ItemStack(Item.itemsList[AetherItems.RECORD_MORNING.id + i])), 10.0);
        }

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
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_BLUE.getDefaultStack(), 1, 8), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.FOOD_GUMMY_GOLD.getDefaultStack(), 1, 4), 90.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_BOW_PHOENIX.getDefaultStack()), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_HAMMER_NOTCH.getDefaultStack()), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_KNIFE_LIGHTNING.getDefaultStack(), 1, 16), 100.0);
        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.TOOL_SWORD_LIGHTNING.getDefaultStack()), 100.0);

        LOOT_RARE.addEntry(new WeightedRandomLootObject(AetherItems.ARMOR_CAPE_AGILITY.getDefaultStack()), 100.0);
    }

    public static final int roomCountMax = 13;
    public int roomCount = 0;

    @Override
    public boolean place(final World world, final Random random, final int x, final int y, final int z) {
        if (!canPlaceDungeon(x, y, z)) return false;
        if (!this.isBoxSolid(world, x, y, z, Direction.EAST, 16, Direction.UP, 12, Direction.SOUTH, 16)) return false;

        int dungeonID = AetherDimension.registerDungeonToMap(x + 8, y + 2, z + 8);

        drawShell(world, random, carvedHolystone, Direction.EAST, 16, Direction.UP, 12, Direction.SOUTH, 16, x, y, z, true);
        this.addSolidBox(world, 0, 0, x + 1, y + 1, z + 1, 14, 10, 14, true);
        drawShell(world, random, carvedHolystone, Direction.EAST, 4, Direction.UP, 4, Direction.SOUTH, 4,x + 6, y - 2, z + 6, true);
        this.addSolidBox(world, 0, 0,x + 7, y - 1, z + 7, 2, 2, 2, true);

        int x2 = x + 7 + random.nextInt(2);
        int y2 = y - 1;
        int z2 = z + 7 + random.nextInt(2);

        world.setBlockAndMetadataWithNotify(x2, y2, z2, AetherBlocks.BRONZE_CHEST_DUNGEON_LOCKED.id(), 4);
        Container inventory = BlockLogicChest.getInventory(world, x2, y2, z2);

        for (int i = 0; i < 6 + random.nextInt(6); i++) {
            inventory.setItem(
                random.nextInt(inventory.getContainerSize()),
                LOOT_RARE.getRandom().getItemStack(random)
            );
        }

        MobBossSlider boss = new MobBossSlider(world);
        boss.moveTo(x + 8, y + 2, z + 8, 0f, 0f);
        boss.setReturnPoint(new BlockCoordinate(x + 8, y + 2, z + 8));
        boss.setTrophy(AetherItems.KEY_BRONZE.getDefaultStack());

        boss.setDungeonID(dungeonID);

        BlockCoordinate[] treasureDoor = new BlockCoordinate[] {
            new BlockCoordinate(x + 7, y +1, z + 7),
            new BlockCoordinate(x + 8, y +1, z + 7),
            new BlockCoordinate(x + 7, y +1, z + 8),
            new BlockCoordinate(x + 8, y +1, z + 8),
        };

        Arrays.stream(treasureDoor).forEach(boss::addDestroyOnDeathBlock);

        world.entityJoinedWorld(boss);

        x2 = x + 20;
        y2 = y;
        z2 = z + 2;

        if (!this.isBoxSolid(world, x2, y2, z2, Direction.EAST, 12, Direction.UP, 12, Direction.SOUTH, 12)) {
            this.addSquareTube(world, random, holystone, x2 - 5, y2, z2 + 3, 6, 6, 6, 0);
            return true;
        }

        drawShell(world, random, carvedHolystone, Direction.EAST, 12, Direction.UP, 12, Direction.SOUTH, 12, x2, y2, z2, true);
        this.addSolidBox(world, 0, 0, x2 + 1, y2 + 1, z2 + 1, 10, 10, 10, true);
        this.addSquareTube(world, random, holystone, x2 - 5, y2, z2 + 3, 6, 6, 6, 0);

        findNextRoom(world, random, x2, y2, z2);
        return true;
    }

    public boolean findNextRoom(World world, Random random, int x, int y, int z) {
        int tries = 3;
        ArrayList<Integer> dirList = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        int index = random.nextInt(dirList.size()-1);

        boolean finished = true;
        while (finished && tries --> 0) {
            finished = this.placeNextRoom(world, random, x, y, z, dirList.get(index));
            index = random.nextInt(dirList.size()-1);
            dirList.remove(index);
        }

        if (!finished) return true;
        endCorridor(world, random, x, y, z, random.nextInt(3));
        return false;
    }

    public boolean placeNextRoom(final World world, final Random random, final int i, final int j, final int k, int dir) {
        int x = i;
        int z = k;

        if (dir == 0) {
            x += 16;
        }
        if (dir == 1) {
            z += 16;
        }
        if (dir == 2) {
            x -= 16;
        }
        if (dir == 3) {
            z -= 16;
        }

        if (this.roomCount > roomCountMax) {
            this.endCorridor(world, random, i, j, k, pickNewDir(dir, random));
            return false;
        }
        if (this.isBoxEmpty(world, x, j, z, 12, 8, 12)) return true;
        if (world.canBlockSeeTheSky(x, j + 1, z)) return true;

        ++this.roomCount;
        drawShell(world, random, carvedHolystone, Direction.EAST, 12, Direction.UP, 8, Direction.SOUTH, 12, x, j, z, true);
        this.addSolidBox(world, 0, 0, x + 1, j + 1, z + 1, 10, 6, 10, true);
        drawPlane(world, random, carvedHolystone, Direction.SOUTH, 4, Direction.EAST, 4,  x + 4, j + 1, z + 4, true);

        final int p2 = x + 5;
        final int q2 = z + 5;

        if (random.nextInt(48) == 0) {
            placeNextRoom(world, random, i, j-12, k, dir);
            this.addSolidBox(world, 0, 0, p2, j -9, q2, 2, 11, 2, true);
        } else {
            if (world.rand.nextInt(3) == 0) placeChestOrMimic(world, random, LOOT_NORMAL, 8, p2, j + 2, q2);
            if (world.rand.nextInt(3) == 0) placeChestOrMimic(world, random, LOOT_NORMAL, 8, p2, j + 2, q2 + 1);
            if (world.rand.nextInt(3) == 0) placeChestOrMimic(world, random, LOOT_NORMAL, 8, p2 + 1, j + 2, q2);
            if (world.rand.nextInt(3) == 0) placeChestOrMimic(world, random, LOOT_NORMAL, 8, p2 + 1, j + 2, q2 + 1);
        }

        switch (dir) {
            case 0: {
                this.addSquareTube(world, random, holystone, x - 5, j, z + 3, 6, 6, 6, 0);
                break;
            }
            case 1: {
                this.addSquareTube(world, random, holystone, x + 3, j, z - 5, 6, 6, 6, 2);
                break;
            }
            case 2: {
                this.addSquareTube(world, random, holystone, x + 11, j, z + 3, 6, 6, 6, 0);
                break;
            }
            case 3: {
                this.addSquareTube(world, random, holystone, x + 3, j, z + 11, 6, 6, 6, 2);
                break;
            }
        }

        return findNextRoom(world, random, x, j, z);
    }

    public int pickNewDir(int me, Random random) {
        int result = me;
        while (result == me) {
            result = random.nextInt(4);
        }
        return result;
    }

    public void endCorridor(final World world, final Random random, final int i, final int j, final int k, int dir) {
        boolean tunnelling = true;
        int x = i;
        int z = k;

        if (dir > 2) dir = random.nextInt(3);
        if (dir == 0) {
            x += 11;
            z += 3;
            while (tunnelling) {
                if (this.isBoxEmpty(world, x, j, z, 6, 8, 1) || z - k > 100) {
                    tunnelling = false;
                }

                drawPlane(world, random, holystone, Direction.UP, 8, Direction.SOUTH, 6, x, j, z, true);
                drawPlane(world, 0, 0, Direction.UP, 6, Direction.SOUTH, 4, x, j + 1, z + 1, true);
                ++x;
            }
        }

        if (dir == 1) {
            x += 3;
            z += 11;
            while (tunnelling) {
                if (this.isBoxEmpty(world, x, j, z, 6, 8, 1) || z - k > 100) {
                    tunnelling = false;
                }

                drawPlane(world, random, holystone, Direction.UP, 6, Direction.EAST, 8, x, j, z, true);
                drawPlane(world, 0, 0, Direction.UP, 4, Direction.EAST, 6, x + 1, j + 1, z, true);
                ++z;
            }
        }

        if (dir == 2) {
            x += 3;
            while (tunnelling) {
                if (this.isBoxEmpty(world, x, j, z, 6, 8, 1) || j - z > 100) {
                    tunnelling = false;
                }

                drawPlane(world, random, holystone, Direction.UP, 6, Direction.EAST, 8, x, j, z, true);
                drawPlane(world, 0, 0, Direction.UP, 4, Direction.EAST, 6, x + 1, j + 1, z, true);
                --z;
            }
        }
    }
    public boolean isBoxSolid(World world, int startX, int startY, int startZ, int length1, int length2, int length3) {
        return this.isBoxSolid(world, startX, startY, startZ, Direction.EAST, length1, Direction.UP, length2, Direction.SOUTH,length3);
    }

    public boolean isBoxEmpty(World world, int i, int j, int k, int di, int dj, int dk) {
        return !isBoxSolid(world, i, j, k, Direction.EAST, di, Direction.UP, dj, Direction.SOUTH, dk);
    }

    public boolean isBoxSolid(World world, int startX, int startY, int startZ, Direction direction1, int length1, Direction direction2, int length2, Direction direction3, int length3) {
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
        return !(volume > ((length1 * length2 * length3) * 0.35F));
    }

    public void addSolidBox(World world, Random random, BlockPallet pallet, int i, int j, int k, int di, int dj, int dk, Boolean withNotify) {
        for (int x = i; x < i + di; ++x) {
            for (int y = j; y < j + dj; ++y) {
                for (int z = k; z < k + dk; ++z) {
                    setBlock(world, x, y, z, pallet.getRandom(random), withNotify);
                }
            }
        }
    }

    public void addSolidBox(World world, int id, int meta, int i, int j, int k, int di, int dj, int dk, Boolean withNotify) {
        for (int x = i; x < i + di; ++x) {
            for (int y = j; y < j + dj; ++y) {
                for (int z = k; z < k + dk; ++z) {
                    if (withNotify) {
                        world.setBlockAndMetadataWithNotify(x, y, z, id, meta);
                        continue;
                    }
                    world.setBlockAndMetadata(x, y, z, Blocks.BLOCK_DIAMOND.id(), meta);
                }
            }
        }
    }


    public void addSquareTube(World world, Random random, BlockPallet pallet, int i, int j, int k, int di, int dj, int dk, int dir) {
        this.addSolidBox(world, 0, 0, i, j, k, di, dj, dk, true);

        if (dir == 0 || dir == 2) {
            drawPlane(world, random, pallet, Direction.SOUTH, di, Direction.EAST, dk,  i, j, k, true);
            drawPlane(world, random, pallet, Direction.SOUTH, di, Direction.EAST, dk,  i,j + dj - 1, k, true);
        }

        if (dir == 1 || dir == 2) {
            drawPlane(world, random, pallet, Direction.UP, dj, Direction.SOUTH, dk, i, j, k, true);
            drawPlane(world, random, pallet, Direction.UP, dj, Direction.SOUTH, dk, i + di - 1, j, k, true);
        }

        if (dir == 0 || dir == 1) {
            drawPlane(world, random, pallet, Direction.UP, di, Direction.EAST, dj, i, j, k, true);
            drawPlane(world, random, pallet, Direction.UP, di, Direction.EAST, dj, i, j, k + dk - 1, true);
        }
    }

}
