package teamport.aether.world.feature.terrain;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class WorldFeatureAetherQuicksoil extends WorldFeature {
    private final int blockId;

    public WorldFeatureAetherQuicksoil(int blockId) {
        this.blockId = blockId;
    }

    @Override
    public boolean place(World world, @NonNull Random random, int x, int y, int z) {
        int radius = 3 + random.nextInt(3);
        for (int x1 = x - radius; x1 <= x + radius; x1++) {
            for (int z1 = z - radius; z1 <= z + radius; z1++) {
                int dx = x1 - x;
                int dz = z1 - z;
                if (dx * dx + dz * dz <= radius * radius + random.nextInt(2) && world.getBlockId(x1, y, z1) == Blocks.AIR.id()) {
                    world.setBlock(x1, y, z1, this.blockId);
                }
            }
        }
        return true;
    }
}
