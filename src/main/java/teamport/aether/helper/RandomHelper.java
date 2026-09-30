package teamport.aether.helper;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.LevelListener;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class RandomHelper {
    private RandomHelper(){}

    // the slam does not use explosions because the slider isn't immune to explosions and the explosion actor isn't passed down to hurt.
    public static void doDestroyBlockEffect(@NotNull World world, double x, double y, double z) {
        Vector3dc vec = new Vector3d(x, y , z);
        int resolution = 16;
        for (int ix = 0; ix < resolution; ix++) {
            for (int iy = 0; iy < resolution; iy++) {
                for (int iz = 0; iz < resolution; iz++) {
                    if (ix != 0 && ix != resolution - 1 && iy != 0 && iy != resolution - 1 && iz != 0 && iz != resolution - 1) {
                        continue;
                    }
                    double dx = ix / (double)(resolution - 1) * 2.0 - 1.0;
                    double dy = iy / (double)(resolution - 1) * 2.0 - 1.0;
                    double dz = iz / (double)(resolution - 1) * 2.0 - 1.0;
                    double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    dx /= length;
                    dy /= length;
                    dz /= length;
                    float power = 4.0F * (0.7F + world.rand.nextFloat() * 0.6F);
                    double rayX = vec.x();
                    double rayY = vec.y();
                    double rayZ = vec.z();
                    for (float step = 0.3F; power > 0.0F; power -= step * 0.75F) {
                        TilePos tilePos = new TilePos(MathHelper.floor(rayX), MathHelper.floor(rayY), MathHelper.floor(rayZ));
                        Block<?> block = world.getBlockType(tilePos);
                        if (block != Blocks.AIR) {
                            power -= (block.getBlastResistance(null) + 0.3F) * step;
                        }
                        RandomHelper.breakBlock(world, power, block, tilePos);
                        rayX += dx * step;
                        rayY += dy * step;
                        rayZ += dz * step;
                    }
                }
            }
        }
    }

    private static void breakBlock(@NotNull World world, float power, Block<?> block, TilePos tilePos) {
        if (power > 0.0F) {
            block.dropWithCause(world, EnumDropCause.EXPLOSION, tilePos, world.getBlockData(tilePos), world.getTileEntity(tilePos), null);
            world.playBlockEvent(tilePos, LevelListener.EVENT_BLOCK_BREAK, block.id());
            world.setBlockTypeDataNotify(tilePos, Blocks.AIR, 0);
        }
    }
}
