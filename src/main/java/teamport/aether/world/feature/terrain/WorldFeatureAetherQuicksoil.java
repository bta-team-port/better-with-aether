package teamport.aether.world.feature.terrain;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureAetherQuicksoil extends WorldFeature {
    private final int blockId;

    public WorldFeatureAetherQuicksoil(int blockId) {
        this.blockId = blockId;
    }

    @Override
    public boolean place(World world, Random random, int x, int y, int z) {
        for (int x1 = x - 3; x1 <= x + 4 + 1; x1++) {
            for (int z1 = z - 3; z1 <= z + 4 + 1; z1++) {
                if (world.getBlockId(x1, y, z1) == 0 && (x1 - x) * (x1 - x) + (z1 - z) * (z1 - z) < 12)
                    world.setBlock(x1, y, z1, this.blockId);
            }
        }
        return true;
    }
}
