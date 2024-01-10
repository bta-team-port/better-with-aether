package bta.aether.world.generate.feature;

import bta.aether.block.AetherBlocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureClouds extends WorldFeature {
    private final int numBlocks;
    private final int cloudID;

    private static final int[] angles = {35, 120, 210, 300};

    public WorldFeatureClouds(int numBlocks, int cloudID) {
        this.numBlocks = numBlocks;
        this.cloudID = cloudID;
    }
    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        double angle = Math.toRadians(angles[random.nextInt(angles.length)]);

        int xOffset = i;
        int zOffset = k;

        for(int b = 0; b < numBlocks; b++) {

            int sizeX = random.nextInt(3)+1;
            int sizeZ = random.nextInt(3)+1;
            int sizeY = random.nextInt(3)+1;

            // march in random direction
            xOffset += (int)(-(Math.cos(angle) * 4));
            zOffset += (int)(-(Math.sin(angle) * 4));

            // add random noise
            if (random.nextInt(3) == 0) {
                xOffset += random.nextInt(2);
            }

            if (random.nextInt(3) == 0) {
                zOffset += random.nextInt(2);
            }

            if (random.nextInt(3) == 0) {
                zOffset += random.nextInt(2);
            }

            for(int x1 = xOffset - sizeX; x1 < xOffset + sizeX; x1++ ){
                for(int z1 = zOffset - sizeZ; z1 < zOffset + sizeZ; z1++ ){
                    for(int y1 = j - sizeY; y1 < j + sizeY; y1++ ) {
                        if (world.getBlockId(x1, y1, z1) == 0) {
                            world.setBlock(x1, y1 , z1, cloudID);
                        }
                    }
                }
            }

            if (random.nextInt(15) == 0) {
                angle = Math.toRadians(angles[random.nextInt(angles.length)]);
            }
        }

        return true;
    }
}
