package teamport.aether.noise;

import java.util.Random;

public class Worley {

    private static final int[][] OFFSETS = {
        {-1, 1}, {0, 1}, {1, 1},
        {-1, 0}, {0, 0}, {1, 0},
        {-1, -1}, {0, -1}, {1, -1},
    };

    public static int isSeed(int x, int z, int gridSize, int seed, int values, int padding) {
        int cellX = (int) Math.floor((double) x / gridSize);
        int cellZ = (int) Math.floor((double) z / gridSize);

        int s = mix(cellX, cellZ, seed);
        Random cellSeed = new Random(s);
        int relX = padding + cellSeed.nextInt(gridSize - padding * 2);
        int relZ = padding + cellSeed.nextInt(gridSize - padding * 2);
        int distX = cellX * gridSize + relX - x;
        int distZ = cellZ * gridSize + relZ - z;

        return distX == 0 && distZ == 0 ? cellSeed.nextInt(values) : -1;
    }

    public static float sampleAt(int x, int z, int gridSize, int seed) {
        int cellX = (int) Math.floor((double) x / gridSize);
        int cellZ = (int) Math.floor((double) z / gridSize);

        float min = Float.POSITIVE_INFINITY;
        for (int i = 0; i < 9; i++) {
            min = Math.min(min, distanceFrom(x, z, cellX + OFFSETS[i][0], cellZ + OFFSETS[i][1], gridSize, seed));
        }

        return min;
    }

    public static float distanceFrom(int x, int z, int cellX, int cellZ, int gridSize, int seed) {
        int s = mix(cellX, cellZ, seed);
        Random cellSeed = new Random(s);

        int relX = cellSeed.nextInt(gridSize);
        int relZ = cellSeed.nextInt(gridSize);
        int distX = cellX * gridSize + relX - x;
        int distZ = cellZ * gridSize + relZ - z;

        return (float) distX * distX + distZ * distZ;
    }

    public static int mix(int a, int b, int c) {
        a -= b; a -= c; a ^= (c>>13);
        b -= c; b -= a; b ^= (a<<8);
        c -= a; c -= b; c ^= (b>>13);
        a -= b; a -= c; a ^= (c>>12);
        b -= c; b -= a; b ^= (a<<16);
        c -= a; c -= b; c ^= (b>>5);
        a -= b; a -= c; a ^= (c>>3);
        b -= c; b -= a; b ^= (a<<10);
        c -= a; c -= b; c ^= (b>>15);
        return c;
    }
}
