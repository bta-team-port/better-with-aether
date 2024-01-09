package bta.aether.world.generate.feature;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureQuicksoil extends WorldFeature {

    private final int blockId;
    private final int radius;

    public WorldFeatureQuicksoil(int blockId, int radius) {
        this.blockId = blockId;
        this.radius = radius;
    }
    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        world.setBlock(x, y, z, this.blockId);
        for(int x1 = x - radius; x1 <= x + radius; x1++){
            for(int z1 = z - radius; z1 <= z + radius; z1++){
                int xd = x1 - x;
                int zd = z1 - z;
                if(xd * xd + zd * zd <= radius * radius){
                    if(world.getBlockId(x1, y, z1) == 0)
                        world.setBlock(x1, y, z1, this.blockId);
                }
            }
        }
        return true;
    }
}
