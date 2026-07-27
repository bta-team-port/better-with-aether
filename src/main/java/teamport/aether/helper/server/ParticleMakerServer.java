package teamport.aether.helper.server;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.net.packet.PacketAddParticle;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;

@Environment(EnvType.SERVER)
public final class ParticleMakerServer {
    private ParticleMakerServer() { }

    public static void spawnParticle(World world, String particleKey, double x, double y, double z,
                                     double motionX, double motionY, double motionZ, int data, double maxDistance) {
        MinecraftServer.getInstance().playerList.sendPacketToAllPlayersInDimension(
            new PacketAddParticle(particleKey, x, y, z, motionX, motionY, motionZ, data, maxDistance),
            world.dimension.id
        );
    }
}
