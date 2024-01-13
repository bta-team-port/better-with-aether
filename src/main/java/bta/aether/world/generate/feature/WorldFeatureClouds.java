package bta.aether.world.generate.feature;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureClouds extends WorldFeature {
    private final int numBlocks;
    private final int cloudID;
    private final boolean isFlat;

    public WorldFeatureClouds(int numBlocks, int cloudID,  boolean isFlat) {
        this.numBlocks = numBlocks;
        this.cloudID = cloudID;
        this.isFlat = isFlat;
    }
    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        int x = i;
        int z = k;
        int y = j;

        int xOffset = random.nextInt(3) - 1;
        int zOffset = random.nextInt(3) - 1;

        for(int block = 0; block < numBlocks; block++) {

            x += random.nextInt(3) - 1 + xOffset;
            z += random.nextInt(3) - 1 + zOffset;

            if (this.isFlat && random.nextInt(10) == 0 || !this.isFlat && random.nextInt(10) == 0){
                y += random.nextInt(3) - 1;
            }

            for(int x1 = x; x1 < x + random.nextInt(4) + 3 * (this.isFlat ? 3 : 1); x1++ ){
                for(int z1 = z; z1 < z + random.nextInt(4) + 3 * (this.isFlat ? 3 : 1); z1++ ){
                    for(int y1 = y; y1 < y + random.nextInt(1) + 2; y1++ ) {
                        if (world.getBlockId(x1, y1, z1) == 0 && Math.abs(x1 - x) + Math.abs(y1 - y) + Math.abs(z1 - z) < 4 * (this.isFlat ? 3 : 1) + random.nextInt(2)) {
                            world.setBlockWithNotify(x1, y1 , z1, cloudID);
                        }
                    }
                }
            }
        }

        return true;
    }
}
