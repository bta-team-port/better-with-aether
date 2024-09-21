package bta.aether.world.generate.feature;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.EntityBossSlider;
import bta.aether.world.AetherDimension;
import net.minecraft.core.world.World;

import java.util.Random;

// this class is a chimera made from the stapi port and our tooling. good luck! :3
public class WorldFeatureAetherDungeonBronze extends WorldFeatureAetherBuildings {
    private int lockedBlockID1;
    private int lockedBlockID2;
    private int wallBlockID1;
    private int wallBlockID2;
    private int corridorBlockID1;
    private int corridorBlockID2;
    private int numRooms;
    private int n;
    private boolean finished;
    private boolean flat;

    public WorldFeatureAetherDungeonBronze(final int i, final int j, final int k, final int l, final int m, final int o, final int p, final boolean flag) {
        this.lockedBlockID1 = i;
        this.lockedBlockID2 = j;
        this.wallBlockID1 = k;
        this.wallBlockID2 = l;
        this.corridorBlockID1 = m;
        this.corridorBlockID2 = o;
        this.numRooms = p;
        this.flat = flag;
        this.finished = false;
    }

    public boolean generate(final World world, final Random rand, final int x, final int y, final int z) {
        int dungeonID = AetherDimension.registerDungeonToMap(x, y, z);
        this.replaceAir = true;
        this.replaceSolid = true;
        this.n = 0;
        if (this.isBoxSolid(world, x, y, z, 16, 12, 16) || this.isBoxSolid(world, x + 20, y, z + 2, 12, 12, 12)) {
            return false;
        }
        this.setBlocks(this.lockedBlockID1, this.lockedBlockID2, 20);
        this.addHollowBox(world, rand, x, y, z, 16, 12, 16);
        this.addHollowBox(world, rand, x + 6, y - 2, z + 6, 4, 4, 4);
        final EntityBossSlider slider = new EntityBossSlider(world);
        slider.setPos(x + 8, y + 2, z + 8);
        slider.setToDungeon(dungeonID);
        world.entityJoinedWorld(slider);
        int x2 = x + 7 + rand.nextInt(2);
        int y2 = y - 1;
        int z2 = z + 7 + rand.nextInt(2);
        world.setBlockAndMetadata(x2, y2, z2, AetherBlocks.chestDungeonLocked.id, 2);
        x2 = x + 20;
        y2 = y;
        z2 = z + 2;
        if (this.isBoxSolid(world, x2, y2, z2, 12, 12, 12)) {
            return true;
        }
        this.setBlocks(this.wallBlockID1, this.wallBlockID2, 20);
        this.addHollowBox(world, rand, x2, y2, z2, 12, 12, 12);
        this.setBlocks(this.corridorBlockID2, this.corridorBlockID1, 5);
        this.addSquareTube(world, rand, x2 - 5, y2, z2 + 3, 6, 6, 6, 0);
        for (int p = x2 + 2; p < x2 + 10; p += 3) {
            for (int q = z2 + 2; q < z2 + 10; q += 3) {
                world.setBlock(p, y, q, AetherBlocks.stoneCarvedTrap.id);
            }
        }
        ++this.n;
        this.generateNextRoom(world, rand, x2, y2, z2);
        this.generateNextRoom(world, rand, x2, y2, z2);
        if (this.n > this.numRooms || !this.finished) {
            this.endCorridor(world, rand, x2, y2, z2);
        }
        System.out.println(x + " " + y + " " + z);
        return true;
    }

    public boolean generateNextRoom(final World world, final Random random, final int i, final int j, final int k) {
        if (this.n > this.numRooms && !this.finished) {
            this.endCorridor(world, random, i, j, k);
            return false;
        }
        final int dir = random.nextInt(4);
        int x = i;
        final int y = j;
        int z = k;
        if (dir == 0) {
            x += 16;
            z += 0;
        }
        if (dir == 1) {
            x += 0;
            z += 16;
        }
        if (dir == 2) {
            x -= 16;
            z += 0;
        }
        if (dir == 3) {
            x += 0;
            z -= 16;
        }
        if (this.isBoxSolid(world, x, y, z, 12, 8, 12)) {
            return false;
        }
        this.setBlocks(this.wallBlockID1, this.wallBlockID2, 20);
        this.setMetadata(0, 0);
        this.addHollowBox(world, random, x, y, z, 12, 8, 12);
        for (int p = x; p < x + 12; ++p) {
            for (int q = y; q < y + 8; ++q) {
                for (int r = z; r < z + 12; ++r) {
                    if (world.getBlockId(p, q, r) == this.wallBlockID1 && random.nextInt(100) == 0) {
                        world.setBlock(p, q, r, AetherBlocks.stoneCarvedLocked.id);
                    }
                }
            }
        }
        for (int p = x + 2; p < x + 10; p += 7) {
            for (int q = z + 2; q < z + 10; q += 7) {
                world.setBlock(p, j, q, AetherBlocks.stoneCarvedLocked.id);
            }
        }
        this.addPlaneY(world, random, x + 4, y + 1, z + 4, 4, 4);
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
                    world.setBlock(p2, y + 2, q2, AetherBlocks.chestSkyroot.id);
                    break;
                }
                break;
            }
        }
        this.setBlocks(this.corridorBlockID2, this.corridorBlockID1, 5);
        switch (dir) {
            case 0: {
                this.addSquareTube(world, random, x - 5, y, z + 3, 6, 6, 6, 0);
                break;
            }
            case 1: {
                this.addSquareTube(world, random, x + 3, y, z - 5, 6, 6, 6, 2);
                break;
            }
            case 2: {
                this.addSquareTube(world, random, x + 11, y, z + 3, 6, 6, 6, 0);
                break;
            }
            case 3: {
                this.addSquareTube(world, random, x + 3, y, z + 11, 6, 6, 6, 2);
                break;
            }
        }
        ++this.n;
        return this.generateNextRoom(world, random, x, y, z) && this.generateNextRoom(world, random, x, y, z);
    }

    public void endCorridor(final World world, final Random random, final int i, final int j, final int k) {
        this.replaceAir = false;
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
                while (flag && (world.getBlockId(x, y, z) == this.wallBlockID1 || world.getBlockId(x, y, z) == this.wallBlockID2 || world.getBlockId(x, y, z) == this.lockedBlockID1 || world.getBlockId(x, y, z) == this.lockedBlockID2)) {
                    if (world.getBlockId(x + 1, y, z) == this.wallBlockID1 || world.getBlockId(x + 1, y, z) == this.wallBlockID2 || world.getBlockId(x + 1, y, z) == this.lockedBlockID1 || world.getBlockId(x + 1, y, z) == this.lockedBlockID2) {
                        ++x;
                    } else {
                        flag = false;
                    }
                }
                this.setBlocks(this.corridorBlockID2, this.corridorBlockID1, 5);
                this.addPlaneX(world, random, x, y, z, 8, 6);
                this.setBlocks(0, 0, 1);
                this.addPlaneX(world, random, x, y + 1, z + 1, 6, 4);
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
                while (flag && (world.getBlockId(x, y, z) == this.wallBlockID1 || world.getBlockId(x, y, z) == this.wallBlockID2 || world.getBlockId(x, y, z) == this.lockedBlockID1 || world.getBlockId(x, y, z) == this.lockedBlockID2)) {
                    if (world.getBlockId(x, y, z + 1) == this.wallBlockID1 || world.getBlockId(x, y, z + 1) == this.wallBlockID2 || world.getBlockId(x, y, z + 1) == this.lockedBlockID1 || world.getBlockId(x, y, z + 1) == this.lockedBlockID2) {
                        ++z;
                    } else {
                        flag = false;
                    }
                }
                this.setBlocks(this.corridorBlockID2, this.corridorBlockID1, 5);
                this.addPlaneZ(world, random, x, y, z, 6, 8);
                this.setBlocks(0, 0, 1);
                this.addPlaneZ(world, random, x + 1, y + 1, z, 4, 6);
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
                while (flag && (world.getBlockId(x, y, z) == this.wallBlockID1 || world.getBlockId(x, y, z) == this.wallBlockID2 || world.getBlockId(x, y, z) == this.lockedBlockID1 || world.getBlockId(x, y, z) == this.lockedBlockID2)) {
                    if (world.getBlockId(x, y, z - 1) == this.wallBlockID1 || world.getBlockId(x, y, z - 1) == this.wallBlockID2 || world.getBlockId(x, y, z - 1) == this.lockedBlockID1 || world.getBlockId(x, y, z - 1) == this.lockedBlockID2) {
                        --z;
                    } else {
                        flag = false;
                    }
                }
                this.setBlocks(this.corridorBlockID2, this.corridorBlockID1, 5);
                this.addPlaneZ(world, random, x, y, z, 6, 8);
                this.setBlocks(0, 0, 1);
                this.addPlaneZ(world, random, x + 1, y + 1, z, 4, 6);
                --z;
            }
        }
        this.finished = true;
    }
}