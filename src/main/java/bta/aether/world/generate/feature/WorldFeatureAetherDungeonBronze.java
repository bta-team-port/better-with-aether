package bta.aether.world.generate.feature;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.EntityBossBase;
import bta.aether.entity.EntityBossSlider;
import bta.aether.entity.EntitySentry;
import bta.aether.item.AetherItems;
import bta.aether.util.AetherBlockCoord;
import bta.aether.world.AetherDimension;
import bta.aether.world.generate.BlockPallet;
import net.minecraft.client.Minecraft;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WorldFeatureAetherDungeonBronze extends WorldFeatureAetherDungeonBase {

    private static final BlockPallet carvedHolystone = new BlockPallet();
    private static final BlockPallet holystone = new BlockPallet();
    static {
        carvedHolystone.addEntry(AetherBlocks.stoneCarvedLocked.id, 0, 85);
        carvedHolystone.addEntry(AetherBlocks.stoneCarvedLightLocked.id, 0, 5);
        carvedHolystone.addEntry(AetherBlocks.trapStoneCarved.id, 0, 10);

        holystone.addEntry(AetherBlocks.holystone.id, 0, 90);
        holystone.addEntry(AetherBlocks.holystoneMossy.id, 0, 10);
    }

    public static final int roomCountMax = 13;
    public int roomCount = 0;

    @Override
    public boolean generate(final World world, final Random random, final int x, final int y, final int z) {
        if (AetherDimension.dugeonMap.values().stream().anyMatch(dungeon -> distanceToSqr(x, y, z, dungeon.x, dungeon.y, dungeon.z) <= AetherDimension.dungeonRadiusSQR)) return false;
        if (!this.isBoxSolid(world, x, y, z, Direction.EAST, 16, Direction.UP, 12, Direction.SOUTH, 16)) return false;
        int dungeonID = AetherDimension.registerDungeonToMap(x, y, z);

        this.drawShell(world, random, carvedHolystone, Direction.EAST, 16, Direction.UP, 12, Direction.SOUTH, 16, x, y, z, true);
        this.addSolidBox(world, 0, 0, x + 1, y + 1, z + 1, 14, 10, 14, true);
        this.drawShell(world, random, carvedHolystone, Direction.EAST, 4, Direction.UP, 4, Direction.SOUTH, 4,x + 6, y - 2, z + 6, true);
        this.addSolidBox(world, 0, 0,x + 7, y - 1, z + 7, 2, 2, 2, true);

        int x2 = x + 7 + random.nextInt(2);
        int y2 = y - 1;
        int z2 = z + 7 + random.nextInt(2);

        ItemStack key = makeTreasureChest(lootTableBronzeRare, 6 + random.nextInt(6), AetherItems.keySilver, true, world, x2, y2, z2);
        EntityBossBase boss = placeBoss(world, x + 8, y + 2, z + 8, EntityBossSlider.class);

        if (boss != null) {
            boss.setToDungeon(dungeonID);
            boss.setKeychain(key);
            boss.setReturnPoint(new AetherBlockCoord(x + 8, y + 2, z + 8));
            AetherBlockCoord[] treasureDoor = new AetherBlockCoord[] {
                    new AetherBlockCoord(x + 7, y +1, z + 7),
                    new AetherBlockCoord(x + 8, y +1, z + 7),
                    new AetherBlockCoord(x + 7, y +1, z + 8),
                    new AetherBlockCoord(x + 8, y +1, z + 8),
            };
            boss.setBlocksDestroyOnDeath(treasureDoor);
        }

        x2 = x + 20;
        y2 = y;
        z2 = z + 2;
        if (!this.isBoxSolid(world, x2, y2, z2, Direction.EAST, 12, Direction.UP, 12, Direction.SOUTH, 12)) {
            return true;
        }

        this.drawShell(world, random, carvedHolystone, Direction.EAST, 12, Direction.UP, 12, Direction.SOUTH, 12, x2, y2, z2, true);
        this.addSolidBox(world, 0, 0, x2 + 1, y2 + 1, z2 + 1, 10, 10, 10, true);
        this.addSquareTube(world, random, holystone, x2 - 5, y2, z2 + 3, 6, 6, 6, 0);

        findNextRoom(world, random, x2, y2, z2);
        System.out.println(x + " " + y + " " + z);
        return true;
    }

    public boolean findNextRoom(World world, Random random, int x, int y, int z) {
        int tries = 3;
        ArrayList<Integer> dirList = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        int index = random.nextInt(dirList.size()-1);
        boolean finished = true;
        while (finished && tries --> 0) {
            finished = this.generateNextRoom(world, random, x, y, z, dirList.get(index));
            index = random.nextInt(dirList.size()-1);
            dirList.remove(index);
        }
        if (!finished) return true;
        this.endCorridor(world, random, x, y, z, random.nextInt(3));
        return false;
    }

    public boolean generateNextRoom(final World world, final Random random, final int i, final int j, final int k, int dir) {
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
        this.drawShell(world, random, carvedHolystone, Direction.EAST, 12, Direction.UP, 8, Direction.SOUTH, 12, x, j, z, true);
        this.addSolidBox(world, 0, 0, x + 1, j + 1, z + 1, 10, 6, 10, true);
        this.drawPlane(world, random, carvedHolystone, Direction.SOUTH, 4, Direction.EAST, 4,  x + 4, j + 1, z + 4, true);

        final int p2 = x + 5;
        final int q2 = z + 5;

        if (random.nextInt(48) == 0) {
            generateNextRoom(world, random, i, j-12, k, dir);
            this.addSolidBox(world, 0, 0, p2, j -9, q2, 2, 11, 2, true);
        } else {
            if (world.rand.nextInt(3) == 0) placeChest(world, random, p2, j + 2, q2);
            if (world.rand.nextInt(3) == 0) placeChest(world, random, p2, j + 2, q2 + 1);
            if (world.rand.nextInt(3) == 0) placeChest(world, random, p2 + 1, j + 2, q2);
            if (world.rand.nextInt(3) == 0) placeChest(world, random, p2 + 1, j + 2, q2 + 1);
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

    private void placeChest(World world, Random random, int x, int y, int z) {
        if (random.nextBoolean()) world.setBlock( x, y, z, AetherBlocks.chestMimic.id);
        else makeTreasureChest(lootTableBronzeNormal, 6 + random.nextInt(6), world, x, y, z);

    }

    private int pickNewDir(int me, Random random) {
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

                this.drawPlane(world, random, holystone, Direction.UP, 8, Direction.SOUTH, 6, x, j, z, true);
                this.drawPlane(world, 0, 0, Direction.UP, 6, Direction.SOUTH, 4, x, j + 1, z + 1, true);
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

                this.drawPlane(world, random, holystone, Direction.UP, 6, Direction.EAST, 8, x, j, z, true);
                this.drawPlane(world, 0, 0, Direction.UP, 4, Direction.EAST, 6, x + 1, j + 1, z, true);
                ++z;
            }
        }

        if (dir == 2) {
            x += 3;
            while (tunnelling) {
                if (this.isBoxEmpty(world, x, j, z, 6, 8, 1) || j - z > 100) {
                    tunnelling = false;
                }

                this.drawPlane(world, random, holystone, Direction.UP, 6, Direction.EAST, 8, x, j, z, true);
                this.drawPlane(world, 0, 0, Direction.UP, 4, Direction.EAST, 6, x + 1, j + 1, z, true);
                --z;
            }
        }
    }
    private boolean isBoxSolid(World world, int startX, int startY, int startZ, int length1, int length2, int length3) {
        return this.isBoxSolid(world, startX, startY, startZ, Direction.EAST, length1, Direction.UP, length2, Direction.SOUTH,length3);
    }

    public boolean isBoxEmpty(World world, int i, int j, int k, int di, int dj, int dk) {
        return !isBoxSolid(world, i, j, k, Direction.EAST, di, Direction.UP, dj, Direction.SOUTH, dk);
    }

    private boolean isBoxSolid(World world, int startX, int startY, int startZ, Direction direction1, int length1, Direction direction2, int length2, Direction direction3, int length3) {
        int volume = 0;
        int blockX = startX;
        int blockY = startY;
        int blockZ = startZ;
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
                    world.setBlockAndMetadata(x, y, z, Block.blockDiamond.id, meta);
                }
            }
        }
    }


    public void addSquareTube(World world, Random random, BlockPallet pallet, int i, int j, int k, int di, int dj, int dk, int dir) {
        this.addSolidBox(world, 0, 0, i, j, k, di, dj, dk, true);

        if (dir == 0 || dir == 2) {
            this.drawPlane(world, random, pallet, Direction.SOUTH, di, Direction.EAST, dk,  i, j, k, true);
            this.drawPlane(world, random, pallet, Direction.SOUTH, di, Direction.EAST, dk,  i,j + dj - 1, k, true);
        }

        if (dir == 1 || dir == 2) {
            this.drawPlane(world, random, pallet, Direction.UP, dj, Direction.SOUTH, dk, i, j, k, true);
            this.drawPlane(world, random, pallet, Direction.UP, dj, Direction.SOUTH, dk, i + di - 1, j, k, true);
        }

        if (dir == 0 || dir == 1) {
            this.drawPlane(world, random, pallet, Direction.UP, di, Direction.EAST, dj, i, j, k, true);
            this.drawPlane(world, random, pallet, Direction.UP, di, Direction.EAST, dj, i, j, k + dk - 1, true);
        }
    }

}