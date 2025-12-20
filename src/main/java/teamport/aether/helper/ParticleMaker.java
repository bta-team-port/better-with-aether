package teamport.aether.helper;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.net.packet.PacketAddParticle;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.net.PlayerList;
import org.jspecify.annotations.Nullable;
import teamport.aether.mixin.accessors.EntityAccessor;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.Random;

public class ParticleMaker {
    private static final Random random = new Random();

    public static void spawnParticle(World world, String particleKey, double x, double y, double z, double motionX, double motionY, double motionZ, int data, double maxDistance) {
        if (EnvironmentHelper.isClientWorld()) return;

        if (EnvironmentHelper.isServerEnvironment()) {
            PlayerList playerList = MinecraftServer.getInstance().playerList;

            playerList.sendPacketToAllPlayersInDimension(
                new PacketAddParticle(particleKey, x, y, z, motionX, motionY, motionZ, data, maxDistance),
                world.dimension.id
            );

            return;
        }

        world.spawnParticle(particleKey, x, y, z, motionX, motionY, motionZ, data, maxDistance);
    }

    public static void spawnParticle(World world, String particleKey, double x, double y, double z, double motionX, double motionY, double motionZ, int data) {
        spawnParticle(world, particleKey, x, y, z, motionX, motionY, motionZ, data, 16D);
    }

    public static void spawnWhirlyParticles(World world, Entity entity, int amount, String particleKey) {
        for (int i = 0; i < amount; ++i) {
            ParticleMaker.spawnParticle(world, particleKey, entity.x, entity.y, entity.z, 0, 10, 0, 0, 72D);
        }
    }

    public static void spawnBlockBreakParticles(World world, int blockX, int blockY, int blockZ, int blockId) {
        for (int i = 0; i < 16; ++i) {
            Direction face = Direction.values()[random.nextInt(6)];

            double faceX = blockX + 0.5 + (random.nextDouble() * 0.6 - 0.3);
            double faceY = blockY + 0.5 + (random.nextDouble() * 0.6 - 0.3);
            double faceZ = blockZ + 0.5 + (random.nextDouble() * 0.6 - 0.3);

            double offX = face.getOffsetX() * (random.nextDouble() * 0.3);
            double offY = face.getOffsetY() * (random.nextDouble() * 0.3);
            double offZ = face.getOffsetZ() * (random.nextDouble() * 0.3);

            double spawnX = faceX + offX;
            double spawnY = faceY + offY;
            double spawnZ = faceZ + offZ;

            double vx = offX * 0.3 + (random.nextDouble() - 0.5) * 0.2;
            double vy = offY * 0.3 + (random.nextDouble() - 0.5) * 0.2;
            double vz = offZ * 0.3 + (random.nextDouble() - 0.5) * 0.2;

            spawnParticle(world, "block", spawnX, spawnY, spawnZ, vx, vy, vz, blockId);
        }
    }

    public static void spawnCloudParticles(World world, double x, double y, double z, double bbHeight) {
        float width = 1.0f;

        for (int i = 0; i < 20; ++i) {
            double dx = random.nextGaussian() * 0.02;
            double dy = random.nextGaussian() * 0.02;
            double dz = random.nextGaussian() * 0.02;
            ParticleMaker.spawnParticle(world,
                "snowshovel",
                x + (random.nextFloat() * width * 2.0F) - width,
                y - bbHeight + (random.nextFloat() * width),
                z + (random.nextFloat() * width * 2.0F) - width,
                dx, dy, dz, 0
            );
        }
    }

    public static void spawnSmokeParticles(Mob target) {
        for (int i = 0; i < 10; i++) {
            ParticleMaker.spawnSmokeParticles(target.world, target.x, target.y + 0.5, target.z, target.bbHeight, target.bbWidth);
        }
    }

    public static void spawnSmokeParticles(World world, double x, double y, double z, double bbHeight, double bbWidth) {
        double dx = random.nextGaussian() * 0.02;
        double dy = random.nextGaussian() * 0.02;
        double dz = random.nextGaussian() * 0.02;
        ParticleMaker.spawnParticle(world,
            "smoke",
            x + (random.nextFloat() * bbWidth * 2.0F) - bbWidth,
            y + (random.nextFloat() * bbHeight) - bbHeight,
            z + (random.nextFloat() * bbWidth * 2.0F) - bbWidth,
            dx, dy, dz, 0
        );
    }

    public static void spawnFlameParticles(World world, double x, double y, double z, double bbHeight, double bbWidth) {
        double dx = random.nextGaussian() * 0.02;
        double dy = random.nextGaussian() * 0.02;
        double dz = random.nextGaussian() * 0.02;
        ParticleMaker.spawnParticle(world, "flame",
            x + (random.nextFloat() * bbWidth * 2.0F) - bbWidth,
            y + (random.nextFloat() * bbHeight) - bbHeight,
            z + (random.nextFloat() * bbWidth * 2.0F) - bbWidth,
            dx, dy, dz, 0);
    }

    public static void spawnHeartParticles(World world, double x, double y, double z, double bbHeight, double bbWidth) {
        double dx = random.nextGaussian() * 0.02;
        double dy = random.nextGaussian() * 0.02;
        double dz = random.nextGaussian() * 0.02;
        for (int i = 0; i < 2; i++) {
            ParticleMaker.spawnParticle(world, "heart",
                x + (random.nextFloat() * bbWidth * 2.0F) - bbWidth,
                y + (random.nextFloat() * bbHeight) - bbHeight,
                z + (random.nextFloat() * bbWidth * 2.0F) - bbWidth,
                dx, dy, dz, 0);
        }
    }


    public static void spawnPoisonParticles(World world, double x, double y, double z, double bbHeight, double bbWidth) {
        double radius = bbWidth * 1.1;
        double centerY = y + bbHeight * 1.1;
        double theta = 2 * Math.PI * random.nextDouble();
        double posX = x + radius * Math.cos(theta);
        double posY = centerY + Math.min(random.nextDouble() * 0.03, 0.1);
        double posZ = z + radius * Math.sin(theta);
        double dy = random.nextDouble() * 0.05;

        ParticleMaker.spawnParticle(world, "poison", posX, posY, posZ, 0, dy, 0, 0);
    }

    public static void spawnRemedyParticle(@Nullable World world, double x, double y, double z, double bbHeight, double bbWidth) {
        double radius = bbWidth + 0.1;
        for (int i = 0; i < 36; i++) {
            double theta = MathHelper.toRadians(10.0F * i);
            double offX = radius * Math.cos(theta);
            double offZ = radius * Math.sin(theta);
            ParticleMaker.spawnParticle(world, "remedy", x, y + bbHeight * 1.2, z, offX * 0.13, 0, offZ * 0.13, 0);
        }
    }

    public static void spawnReplacementEffects(World world, int x, int y, int z) {
        for (int l = 0; l < 8; ++l) {
            double angle = Math.toRadians(l * 45.0);
            ParticleMaker.spawnParticle(world, "smoke", x + 0.5, y + 0.2, z + 0.5, -Math.cos(angle) / 20.0, 0.03, -Math.sin(angle) / 20.0, 0);
            ParticleMaker.spawnParticle(world, "largesmoke", x + Math.random(), y + .2, z + Math.random(), 0.0, 0.0, 0.0, 0);
        }
        world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x + 0.5, y + 0.5, z + 0.5, "fire.ignite", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
        world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x + 0.5F, y + 0.5F, z + 0.5F, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
    }

    public static void spawnFireSwordParticles(Mob target) {
        Random random = ((EntityAccessor) target).getRandom();
        for (int particle = 0; particle < 16; particle++) {
            double dx = target.x + (random.nextDouble() * 0.5) - 0.25;
            double dy = target.y + 1.0 + (random.nextDouble());
            double dz = target.z + (random.nextDouble() * 0.5) - 0.25;
            double motionX = (random.nextDouble() * 0.1) - 0.05;
            double motionY = (random.nextDouble() * 0.1) - 0.05;
            double motionZ = (random.nextDouble() * 0.1) - 0.05;
            spawnParticle(target.world, "flame", dx, dy, dz, motionX, motionY, motionZ, 0);
            spawnParticle(target.world, "flame", dx, dy, dz, -motionX, motionY, motionZ, 0);
            spawnParticle(target.world, "flame", dx, dy, dz, motionX, motionY, -motionZ, 0);
            spawnParticle(target.world, "flame", dx, dy, dz, -motionX, motionY, -motionZ, 0);
        }
    }

    public static void spawnHolySwordParticles(Mob target) {
        Random random = ((EntityAccessor) target).getRandom();
        for (int particle = 0; particle < 16; particle++) {
            double dx = target.x + (random.nextDouble() * 0.5) - 0.25;
            double dy = target.y + 1.0 + (random.nextDouble());
            double dz = target.z + (random.nextDouble() * 0.5) - 0.25;
            double motionX = (random.nextDouble() * 0.1) - 0.05;
            double motionY = (random.nextDouble() * 0.1) - 0.05;
            double motionZ = (random.nextDouble() * 0.1) - 0.05;
            spawnParticle(target.world, "blueflame", dx, dy, dz, motionX, motionY, motionZ, 0);
            spawnParticle(target.world, "blueflame", dx, dy, dz, -motionX, motionY, motionZ, 0);
            spawnParticle(target.world, "blueflame", dx, dy, dz, motionX, motionY, -motionZ, 0);
            spawnParticle(target.world, "blueflame", dx, dy, dz, -motionX, motionY, -motionZ, 0);
        }
    }

    public static void spawnLightningSwordParticles(Mob target) {
        Random random = ((EntityAccessor) target).getRandom();
        double startY = target.y + 2.0;
        double endY = target.y;
        int numPoints = 16;
        double currentX = target.x;
        double currentZ = target.z;
        double stepY = (startY - endY) / numPoints;

        for (int i = 0; i <= numPoints; i++) {
            double y = startY - (i * stepY);

            if (i > 0) {
                double dx = (random.nextDouble() - 0.5) * 0.8;
                double dz = (random.nextDouble() - 0.5) * 0.8;
                currentX += dx;
                currentZ += dz;
            }

            double x = currentX;
            double z = currentZ;

            double motionX = (random.nextDouble() * 0.1) - 0.05;
            double motionY = (random.nextDouble() * 0.1) - 0.05;
            double motionZ = (random.nextDouble() * 0.1) - 0.05;
            spawnParticle(target.world, "lightning", x, y, z, motionX, motionY, motionZ, 0);
            spawnParticle(target.world, "lightning", x, y, z, -motionX, motionY, motionZ, 0);
            spawnParticle(target.world, "lightning", x, y, z, motionX, motionY, -motionZ, 0);
            spawnParticle(target.world, "lightning", x, y, z, -motionX, motionY, -motionZ, 0);
        }
    }

    public static void spawnDowningBubbles(Mob target) {
        Random random = ((EntityAccessor) target).getRandom();
        for (int i = 0; i < 30; ++i) {
            double offX = random.nextFloat() - random.nextFloat();
            double offY = random.nextFloat() - random.nextFloat();
            double offZ = random.nextFloat() - random.nextFloat();
            spawnParticle(target.world, "bubble", target.x + offX, target.y + offY + 1, target.z + offZ, target.xd, target.yd, target.zd, 0);
        }
    }
}
