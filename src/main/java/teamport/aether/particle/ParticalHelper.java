package teamport.aether.particle;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.phys.AABB;
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

    public static void spawnPoisonParticles(World world, double x, double y, double z, double bbHeight, double bbWidth) {
        double radius = 0.66f;
        double theta = 2 * Math.PI * random.nextDouble();
        double phi = 2 * Math.PI * random.nextDouble();
        double centerY = bbHeight / 1.75F;
        double xPos = x + radius * Math.sin(theta) * Math.cos(phi);
        double yPos = y + radius * Math.cos(theta) + centerY;
        double zPos = z + radius * Math.sin(theta) * Math.sin(phi);
        double dy = random.nextDouble() * 0.01;
        world.spawnParticle("poison", xPos, yPos, zPos, 0, dy, 0, 0);
    }


    // TODO the particles should not appear in front of the player pov
    public static void spawnPoisonParticlesPlayer(World world, double x, double y, double z, double bbHeight, double bbWidth, Player player) {
    }

}
