package bta.aether;

import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.net.packet.Packet41EntityPlayerGamemode;
import net.minecraft.core.net.packet.Packet74GameRule;
import net.minecraft.core.net.packet.Packet9Respawn;
import net.minecraft.core.world.Dimension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.world.WorldServer;

import java.time.Duration;

public class AetherServer {
    public static void teleportNotToPortalMP(EntityPlayerMP entityplayermp, int targetDim){
        MinecraftServer server = MinecraftServer.getInstance();
        entityplayermp.timeUntilPortal = 10;
        WorldServer worldserver = server.getDimensionWorld(entityplayermp.dimension);
        Dimension lastDim = Dimension.getDimensionList().get(entityplayermp.dimension);
        Dimension newDim = Dimension.getDimensionList().get(targetDim);
        entityplayermp.dimension = targetDim;
        WorldServer worldserver1 = server.getDimensionWorld(entityplayermp.dimension);
        entityplayermp.playerNetServerHandler.sendPacket(new Packet9Respawn((byte)entityplayermp.dimension, (byte) Registries.WORLD_TYPES.getNumericIdOfItem(worldserver1.worldType)));
        worldserver.removePlayer(entityplayermp);
        entityplayermp.removed = false;
        double d = entityplayermp.x;
        double d1 = entityplayermp.z;
        double d2 = 8.0;
        entityplayermp.moveTo(d *= Dimension.getCoordScale(lastDim, newDim), entityplayermp.world.getWorldType().getMaxY() + 1, d1 *= Dimension.getCoordScale(lastDim, newDim), entityplayermp.yRot, entityplayermp.xRot);
        if (entityplayermp.isAlive()) {
            worldserver.updateEntityWithOptionalForce(entityplayermp, false);
        }
        if (entityplayermp.isAlive()) {
            worldserver1.entityJoinedWorld(entityplayermp);
            entityplayermp.moveTo(d, entityplayermp.y, d1, entityplayermp.yRot, entityplayermp.xRot);
            worldserver1.updateEntityWithOptionalForce(entityplayermp, false);
        }
        server.playerList.func_28172_a(entityplayermp);
        entityplayermp.playerNetServerHandler.teleportAndRotate(entityplayermp.x, entityplayermp.y, entityplayermp.z, entityplayermp.yRot, entityplayermp.xRot);
        entityplayermp.playerNetServerHandler.sendPacket(new Packet41EntityPlayerGamemode(entityplayermp.getGamemode().getId()));
        entityplayermp.setWorld(worldserver1);
        server.playerList.func_28170_a(entityplayermp, worldserver1);
        server.playerList.func_30008_g(entityplayermp);
        entityplayermp.playerNetServerHandler.sendPacket(new Packet74GameRule(server.getDimensionWorld(0).getLevelData().getGameRules()));
    }
}
