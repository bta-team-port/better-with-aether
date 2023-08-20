package bta.aether.world.generate.feature;

import bta.aether.Aether;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureClouds extends WorldFeature {
    private final int numBlocks;

    public WorldFeatureClouds(int numBlocks) {
        this.numBlocks = numBlocks;
    }
    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        int xOffset  = i;
        int zOffset  = k;
        for(int b = 0; b < numBlocks; b++){
            int sizeX = random.nextInt(2)+2;
            int sizeZ = random.nextInt(2)+2;
            int sizeY = random.nextInt(2)+2;
            xOffset += random.nextInt(3);
            zOffset += random.nextInt(3);
            for(int x1 = xOffset - sizeX; x1 < xOffset + sizeX; x1++ ){
                for(int z1 = zOffset - sizeZ; z1 < zOffset + sizeZ; z1++ ){

                    for(int y1 = j - sizeY; y1 < j + sizeY; y1++ ) {
                        world.setBlock(x1, y1, z1, Aether.aercloudWhite.id);
                    }
                }
            }
        }
        return true;
    }
}
