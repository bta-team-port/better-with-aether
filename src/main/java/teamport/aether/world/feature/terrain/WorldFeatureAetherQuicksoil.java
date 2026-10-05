package teamport.aether.world.feature.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class WorldFeatureAetherQuicksoil implements WorldFeatureInterface {
    private final Block<?> block;

    public WorldFeatureAetherQuicksoil(Block<?> block) {
        this.block = block;
    }

    @Override
    public boolean place(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc) {
        int ix = tilePosc.x();
        int iy = tilePosc.y();
        int iz = tilePosc.z();
        int radius = 3 + random.nextInt(3);
        TilePos temp = new TilePos();
        for (int x = ix - radius; x <= ix + radius; x++) {
            for (int z = iz - radius; z <= iz + radius; z++) {
                temp.set(x, iy, z);
                if (world.getBlockType(temp) == Blocks.AIR
                    && (x - ix) * (x - ix) + (z - iz) * (z - iz) < radius * radius + random.nextInt(2)
                ) {
                    world.setBlockType(temp, block);
                }
            }
        }
        return true;
    }
}
