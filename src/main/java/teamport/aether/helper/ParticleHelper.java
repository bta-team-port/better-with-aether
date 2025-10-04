package teamport.aether.helper;

import net.minecraft.core.net.packet.PacketAddParticle;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.net.PlayerList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.Random;

public class ParticleHelper {
    public static Random random = new Random();

    public static void spawnParticle(World world, String particleKey, double x, double y, double z, double motionX, double motionY, double motionZ, int data, double maxDistance) {
        if (EnvironmentHelper.isServerEnvironment()) {
            PlayerList playerList = MinecraftServer.getInstance().playerList;

            playerList.sendPacketToAllPlayersInDimension(
                    new PacketAddParticle(particleKey, x, y, z, motionX, motionY, motionZ, data, maxDistance),
                    world.dimension.id
            );
        }

        else world.spawnParticle(particleKey, x, y, z, motionX, motionY, motionZ, data, maxDistance);
    }

    public static void spawnParticle(World world, String particleKey, double x, double y, double z, double motionX, double motionY, double motionZ, int data) {
        spawnParticle(world, particleKey, x, y, z, motionX, motionY, motionZ, data, 16D);
    }

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
        double radius = bbWidth * 1.1;
        double centerY = y + bbHeight * 1.1;
        double theta = 2 * Math.PI * random.nextDouble();
        double posX = x + radius * Math.cos(theta);
        double posY = centerY + Math.min(random.nextDouble() * 0.03, 0.1);
        double posZ = z + radius * Math.sin(theta);
        double dy = random.nextDouble() * 0.05;

        ParticleHelper.spawnParticle(world, "poison", posX, posY, posZ, 0, dy, 0, 0);
    }

    public static void spawnRemedyParticle(@Nullable World world, double x, double y, double z, double bbHeight, double bbWidth) {
        double radius = bbWidth + 0.1;
        for(int i = 0; i < 36; i++){
            double theta = MathHelper.toRadians(10 * i);
            double offX = radius * Math.cos(theta);
            double offZ = radius * Math.sin(theta);
            ParticleHelper.spawnParticle(world, "remedy", x, y + bbHeight * 1.2, z, offX * 0.13, 0, offZ * 0.13, 0);
        }
    }

    public static void spawnPoisonParticlesCluster(World world, double x, double y, double z, double bbHeight, double bbWidth) {
        double radiusOuter = Math.min(bbWidth * 1.2, bbHeight * 1.2);
        double centerY = bbHeight / 1.75F;
        double theta = 0.5f * Math.PI * random.nextDouble();
        double phi = 2 * Math.PI * random.nextDouble();
        double clusterX = x + radiusOuter * Math.sin(theta) * Math.cos(phi);
        double clusterY = y + radiusOuter * Math.cos(theta) + centerY;
        double clusterZ = z + radiusOuter * Math.sin(theta) * Math.sin(phi);
        double radius = 0.4;

        for (int i = 0; i < 3; i++) {
            double thetaInner = 2 * Math.PI * random.nextDouble();
            double posX = clusterX + radius * Math.cos(thetaInner);
            double posY = clusterY + Math.min(random.nextDouble() * 0.03, 0.1);
            double posZ = clusterZ + radius * Math.sin(thetaInner);
            double dy = random.nextDouble() * 0.04;
            ParticleHelper.spawnParticle(world, "poison", posX, posY, posZ, 0, dy, 0, 0);
        }
    }

}
