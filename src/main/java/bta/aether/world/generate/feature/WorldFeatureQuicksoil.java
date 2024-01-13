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
        for(int x1 = x - radius; x1 <= x + radius + 1; x1++){
            for(int z1 = z - radius; z1 <= z + radius + 1; z1++){
                    if(world.getBlockId(x1, y, z1) == 0 && (x1 - x) * (x1 - x) + (z1 - z) * (z1 - z) < radius * 4)
                        world.setBlockWithNotify(x1, y, z1, this.blockId);
                }
            }
        return true;
    }
}
