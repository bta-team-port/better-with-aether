package teamport.aether.particle;

import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Unique;

import java.util.Random;

public class ParticalHelper {
    public static Random random = new Random();

    public static void spawnCloudParticles(World world, double x, double y, double z, double bbHeight){
        float width = 1.0f;

        for (int i = 0; i < 20; ++i) {
            double dx = random.nextGaussian() * 0.02;
            double dy = random.nextGaussian() * 0.02;
            double dz = random.nextGaussian() * 0.02;
            world.spawnParticle(
                    "snowshovel",
                    x + (double) (random.nextFloat() * width * 2.0F) - (double) width,
                    y - bbHeight + (double) (random.nextFloat() * width),
                    z + (double) (random.nextFloat() * width * 2.0F) - (double) width,
                    dx, dy, dz, 0
            );
        }
    }

    public static void spawnSmokeParticles(World world, double x, double y, double z, double bbHeight, double bbWidth) {
        double dx = random.nextGaussian() * 0.02;
        double dy = random.nextGaussian() * 0.02;
        double dz = random.nextGaussian() * 0.02;
        world.spawnParticle(
                "smoke",
                x + (random.nextFloat() * bbWidth * 2.0F) - bbWidth,
                y + (random.nextFloat() * bbHeight) - bbHeight,
                z + (random.nextFloat() * bbWidth * 2.0F) - bbWidth,
                dx, dy, dz, 0
        );
    }

    @Unique
    public static void spawnFlameParticles(World world, double x, double y, double z, double bbHeight, double bbWidth) {
        double dx = random.nextGaussian() * 0.02;
        double dy = random.nextGaussian() * 0.02;
        double dz = random.nextGaussian() * 0.02;
        world.spawnParticle("flame",
                x + (random.nextFloat() * bbWidth * 2.0F) - bbWidth,
                y + (random.nextFloat() * bbHeight) - bbHeight,
                z + (random.nextFloat() * bbWidth * 2.0F) - bbWidth,
                dx, dy, dz, 0);
    }

}
