package teamport.aether.helper;

import net.minecraft.core.net.packet.PacketAddParticle;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.net.PlayerList;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class ParticleHelper {
    protected ParticleHelper() {}

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
}
