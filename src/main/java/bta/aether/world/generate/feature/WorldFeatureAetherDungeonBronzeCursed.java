package bta.aether.world.generate.feature;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.EntityBossBase;
import bta.aether.entity.EntityBossSlider;
import bta.aether.item.AetherItems;
import bta.aether.util.AetherBlockCoord;
import bta.aether.world.AetherDimension;
import bta.aether.world.generate.BlockPallet;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;

import java.util.Random;

// this class is a chimera made from the stapi port and our tooling. good luck! :3
public class WorldFeatureAetherDungeonBronzeCursed extends WorldFeatureAetherDungeonBase {

    private static BlockPallet carvedHolystone = new BlockPallet();
    private static BlockPallet holystone = new BlockPallet();
    static {
        carvedHolystone.addEntry(AetherBlocks.stoneCarvedLocked.id, 0, 95);
        carvedHolystone.addEntry(AetherBlocks.stoneCarvedLightLocked.id, 0, 5);

        holystone.addEntry(AetherBlocks.holystone.id, 0, 90);
        holystone.addEntry(AetherBlocks.holystoneMossy.id, 0, 10);
    }

    private final int numRooms = 25;
    private int n;
    private boolean finished;

    @Override
    public boolean generate(final World world, final Random random, final int x, final int y, final int z) {
        if (AetherDimension.dugeonMap.values().stream().anyMatch(dungeon -> distanceToSqr(x, y, z, dungeon.x, dungeon.y, dungeon.z) < AetherDimension.dungeonRadiusSQR*1.5)) return false;
        if (!world.canBlockSeeTheSky(x, y, z)) return false;
        int dungeonID = AetherDimension.registerDungeonToMap(x, y, z);

        this.n = 0;
        if (!this.isBoxSolid(world, x, y, z, Direction.EAST, 16, Direction.UP, 12, Direction.SOUTH, 16) || !this.isBoxSolid(world, x + 20, y, z + 2, Direction.EAST, 12, Direction.UP, 12, Direction.SOUTH, 12)) {
           return false;
        }
        this.drawShell(world, random, carvedHolystone, Direction.EAST, 16, Direction.UP, 12, Direction.SOUTH, 16, x, y, z, true);
        this.addSolidBox(world, 0, 0, x + 1, y + 1, z + 1, 14, 10, 14, true);

        this.drawShell(world, random, carvedHolystone, Direction.EAST, 4, Direction.UP, 4, Direction.SOUTH, 4,x + 6, y - 2, z + 6, true);
        this.addSolidBox(world, 0, 0,x + 7, y - 1, z + 7, 2, 2, 2, true);

        int x2 = x + 7 + random.nextInt(2);
        int y2 = y - 1;
        int z2 = z + 7 + random.nextInt(2);
        ItemStack key = makeTreasureChest(lootTableBronzeRare, 6 + random.nextInt(6), AetherItems.keySilver, true, world, x2, y2, z2);

        AetherBlockCoord[] treasureDoor = new AetherBlockCoord[] {
                new AetherBlockCoord(x + 7, y +1, z + 7),
                new AetherBlockCoord(x + 8, y +1, z + 7),
                new AetherBlockCoord(x + 7, y +1, z + 8),
                new AetherBlockCoord(x + 8, y +1, z + 8),
        };

        // Boss TODO: replace with sunfire spirit.
        EntityBossBase boss = placeBoss(world, x + 8, y + 2, z + 8, EntityBossSlider.class);
        if (boss != null) {
            boss.setToDungeon(dungeonID);
            boss.setKeychain(key);
            boss.setReturnPoint(new AetherBlockCoord(x + 8, y + 2, z + 8));
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

        for (int p = x2 + 2; p < x2 + 10; p += 3) {
            for (int q = z2 + 2; q < z2 + 10; q += 3) {
                world.setBlock(p, y, q, AetherBlocks.trapStoneCarved.id);
            }
        }

        ++this.n;
        this.generateNextRoom(world, random, x2, y2, z2);
        this.generateNextRoom(world, random, x2, y2, z2);
        this.generateNextRoom(world, random, x2, y2, z2);
        if (this.n > this.numRooms || this.finished) {
            this.endCorridor(world, random, x2, y2, z2);
        }
        System.out.println(x + " " + y + " " + z);
        return true;
    }

    public boolean generateNextRoom(final World world, final Random random, final int i, final int j, final int k) {
        if (this.n > this.numRooms || this.finished) {
            this.endCorridor(world, random, i, j, k);
            return false;
        }
        final int dir = random.nextInt(4);

        int x = i;
        int y = j;
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

        if (!this.isBoxSolid(world, x, y, z, Direction.EAST, 12, Direction.UP, 8, Direction.SOUTH, 12)) {
            return false;
        }

        this.drawShell(world, random, carvedHolystone, Direction.EAST, 12, Direction.UP, 8, Direction.SOUTH, 12, x, y, z, true);
        this.addSolidBox(world, 0, 0, x + 1, y + 1, z + 1, 10, 6, 10, true);

        for (int p = x; p < x + 12; ++p) {
            for (int q = y; q < y + 8; ++q) {
                for (int r = z; r < z + 12; ++r) {
                    if (world.getBlockId(p, q, r) == AetherBlocks.stoneCarvedLocked.id && random.nextInt(100) == 0) {
                        world.setBlock(p, q, r, AetherBlocks.holystone.id);
                    }
                }
            }
        }

        for (int p = x + 2; p < x + 10; p += 7) {
            for (int q = z + 2; q < z + 10; q += 7) {
                world.setBlock(p, j, q, AetherBlocks.stoneCarvedLocked.id);
            }
        }
        this.drawPlane(world, random, carvedHolystone, Direction.SOUTH, 4, Direction.EAST, 4,  x + 4, y + 1, z + 4, true);
        final int type = random.nextInt(2);
        int p2 = x + 5 + random.nextInt(2);
        final int q2 = z + 5 + random.nextInt(2);
        switch (type) {
            case 0: {
                world.setBlock(p2, y + 2, q2, AetherBlocks.chestMimic.id);
                break;
            }
            case 1: {
                if (world.getBlockId(p2, y + 2, q2) == 0) {
                    makeTreasureChest(lootTableBronzeNormal, 6 + random.nextInt(6), world, p2, y + 2, q2);
                    break;
                }
                break;
            }
        }

        switch (dir) {
            case 0: {
                this.addSquareTube(world, random, holystone, x - 5, y, z + 3, 6, 6, 6, 0);
                break;
            }
            case 1: {
                this.addSquareTube(world, random, holystone, x + 3, y, z - 5, 6, 6, 6, 2);
                break;
            }
            case 2: {
                this.addSquareTube(world, random, holystone, x + 11, y, z + 3, 6, 6, 6, 0);
                break;
            }
            case 3: {
                this.addSquareTube(world, random, holystone, x + 3, y, z + 11, 6, 6, 6, 2);
                break;
            }
        }

        ++this.n;
        return this.generateNextRoom(world, random, x, y, z) && this.generateNextRoom(world, random, x, y, z);
    }

    public void endCorridor(final World world, final Random random, final int i, final int j, final int k) {

        boolean tunnelling = true;
        final int dir = random.nextInt(3);
        int x = i;
        final int y = j;
        int z = k;

        if (dir == 0) {
            x += 11;
            z += 3;
            while (tunnelling) {
                if (this.isBoxEmpty(world, x, y, z, 1, 8, 6) || x - i > 100) {
                    tunnelling = false;
                }
                boolean flag = true;
                while (flag && (world.getBlockId(x, y, z) == AetherBlocks.stoneCarvedLocked.id || world.getBlockId(x, y, z) == AetherBlocks.stoneCarvedLightLocked.id || world.getBlockId(x, y, z) == AetherBlocks.stoneCarvedLight.id || world.getBlockId(x, y, z) == AetherBlocks.stoneCarved.id)) {
                    if (world.getBlockId(x + 1, y, z) == AetherBlocks.stoneCarvedLocked.id || world.getBlockId(x + 1, y, z) == AetherBlocks.stoneCarvedLightLocked.id || world.getBlockId(x + 1, y, z) == AetherBlocks.stoneCarvedLight.id || world.getBlockId(x + 1, y, z) == AetherBlocks.stoneCarved.id) {
                        ++x;
                    } else {
                        flag = false;
                    }
                }

                this.drawPlane(world, random, holystone, Direction.UP, 8, Direction.SOUTH, 6, x, y, z, true);
                this.drawPlane(world, 0, 0, Direction.UP, 6, Direction.SOUTH, 4, x, y + 1, z + 1, true);

                ++x;
            }
        }
        if (dir == 1) {
            x += 3;
            z += 11;
            while (tunnelling) {
                if (this.isBoxEmpty(world, x, y, z, 6, 8, 1) || z - k > 100) {
                    tunnelling = false;
                }
                boolean flag = true;
                while (flag && (world.getBlockId(x, y, z) == AetherBlocks.stoneCarvedLocked.id || world.getBlockId(x, y, z) == AetherBlocks.stoneCarvedLightLocked.id || world.getBlockId(x, y, z) == AetherBlocks.stoneCarvedLight.id || world.getBlockId(x, y, z) == AetherBlocks.stoneCarved.id)) {
                    if (world.getBlockId(x, y, z + 1) == AetherBlocks.stoneCarvedLocked.id || world.getBlockId(x, y, z + 1) == AetherBlocks.stoneCarvedLightLocked.id || world.getBlockId(x, y, z + 1) == AetherBlocks.stoneCarvedLight.id || world.getBlockId(x, y, z + 1) == AetherBlocks.stoneCarved.id) {
                        ++z;
                    } else {
                        flag = false;
                    }
                }

                this.drawPlane(world, random, holystone, Direction.UP, 6, Direction.EAST, 8, x, y, z, true);
                this.drawPlane(world, 0, 0, Direction.UP, 4, Direction.EAST, 6, x + 1, y + 1, z, true);

                ++z;
            }
        }
        if (dir == 2) {
            x += 3;
            z += 0;
            while (tunnelling) {
                if (this.isBoxEmpty(world, x, y, z, 6, 8, 1) || j - z > 100) {
                    tunnelling = false;
                }
                boolean flag = true;
                while (flag && (world.getBlockId(x, y, z) == AetherBlocks.stoneCarvedLocked.id || world.getBlockId(x, y, z) == AetherBlocks.stoneCarvedLightLocked.id || world.getBlockId(x, y, z) == AetherBlocks.stoneCarvedLight.id || world.getBlockId(x, y, z) == AetherBlocks.stoneCarved.id)) {
                    if (world.getBlockId(x, y, z - 1) == AetherBlocks.stoneCarvedLocked.id || world.getBlockId(x, y, z - 1) == AetherBlocks.stoneCarvedLightLocked.id || world.getBlockId(x, y, z - 1) == AetherBlocks.stoneCarvedLight.id || world.getBlockId(x, y, z - 1) == AetherBlocks.stoneCarved.id) {
                        --z;
                    } else {
                        flag = false;
                    }
                }

                this.drawPlane(world, random, holystone, Direction.UP, 6, Direction.EAST, 8, x, y, z, true);
                this.drawPlane(world, 0, 0, Direction.UP, 4, Direction.EAST, 6, x + 1, y + 1, z, true);

                --z;
            }
        }
        this.finished = true;
    }
    private boolean isBoxSolid(World world, int startX, int startY, int startZ, int length1, int length2, int length3) {
        return this.isBoxSolid(world, startX, startY, startZ, Direction.EAST, length1, Direction.UP, length2, Direction.NORTH,length3);
    }

    public boolean isBoxEmpty(World world, int i, int j, int k, int di, int dj, int dk) {
        return !isBoxSolid(world, i, j, k, Direction.EAST, di, Direction.UP, dj, Direction.SOUTH, dk);
    }

    private boolean isBoxSolid(World world, int startX, int startY, int startZ, Direction direction1, int length1, Direction direction2, int length2, Direction direction3, int length3) {
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
                    if (world.getBlockId(blockX, blockY, blockZ) == 0) return false;
                    blockX += direction1.getOffsetX();
                    blockY += direction1.getOffsetY();
                    blockZ += direction1.getOffsetZ();
                }
            }
        }
        return true;
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